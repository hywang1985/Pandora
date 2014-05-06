package com.pandorabox.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.Article;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseArticle;
import com.pandorabox.domain.impl.BaseImageDescriptor;
import com.pandorabox.domain.impl.BaseLayoutDescriptor;
import com.pandorabox.domain.impl.BaseTag;
import com.pandorabox.domain.impl.BaseUser;
import com.pandorabox.service.ArticleService;
import com.pandorabox.service.upyun.UpYunService;

@Controller
@RequestMapping("/article")
/**
 * 通过这个控制器可以对文章进行操作，采用Restful设计控制器的API
 * */
public class ArticleController extends BaseController {

	private static Logger logger = Logger.getLogger(ArticleController.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	UpYunService upService;

	// /** 显示单篇文章 */
	// @RequestMapping(value = "/{id}")
	// public ModelAndView showArticle(@PathVariable int id,
	// HttpServletRequest request, HttpServletResponse response) {
	// return new ModelAndView();
	// }
	//
	// /** 　列表显示所有文章　 */
	// @RequestMapping
	// public ModelAndView listArticle(HttpServletRequest request,
	// HttpServletResponse response) {
	// return new ModelAndView();
	// }

	/** 动态加载文章,返回JSON, ajax交互用 */
	@RequestMapping(value = "/dyload", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> loadArticle(HttpServletRequest request) {
		String startIndex = request.getHeader(CommonConstant.ARTICLE_START_INDEX_HEADER_NAME);
		Map<String, Object> articles = new HashMap<String, Object>(CommonConstant.ARTICLE_LOAD_COUNT);
		articles.put("data", articleService.getArticlesByPage(Integer.parseInt(startIndex), CommonConstant.ARTICLE_LOAD_COUNT));
		articles.put("success", "true");
		return articles;
	}
	
	// @RequestMapping(method = RequestMethod.GET)
	// public void feedBack(HttpServletRequest request, HttpServletResponse
	// response) {
	// String title = request.getParameter("title");
	// System.out.println("title: "+title);
	// String client_123 = request.getParameter("ext-param");
	// System.out.println("exparam: "+client_123);
	// }

	//
	// /** 更新单篇文章 */
	// @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	// public ModelAndView updateArticle(@PathVariable int id,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// return new ModelAndView();
	// }

	// /** 删除单篇文章 */
	// @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	// public ModelAndView deleteArticle(@PathVariable int id,
	// HttpServletRequest request, HttpServletResponse response) {
	// return new ModelAndView();
	// }

	/** 新增单篇文章 
	 * @throws IOException */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Article addArticle(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Article article = null;
		LayoutBehavior layoutBehavior = null;
		User author = getSessionUser(request);
		if (author == null) {
			author = new BaseUser();
			author.setUsername("fakeUser");
			//throw new NoUserException();
		}
		if (author != null) {
			article = new BaseArticle();
			String title = request
					.getParameter(CommonConstant.ARTICLE_TITLE_KEY);
			String content = request
					.getParameter(CommonConstant.ARTICLE_CONTENT_KEY);
			// 处理上传成功的图片的信息
			String imagesInfoJsonString = request
					.getParameter(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY);
			// 处理上传成功的音乐的信息
			List<ImageDescriptor> uploadedImages = handleImages(imagesInfoJsonString);
			String musicInfoJsonString = request
					.getParameter(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY);
			String musicFullUrl = handleMusic(musicInfoJsonString);
			String tagText = request.getParameter(CommonConstant.ARTICLE_TAGS_KEY);
			//处理标签，前端校验，确保传过来的值符合要求
			if(tagText!=null && !"".equals(tagText)){
				if(!tagText.contains(",")){
					Tag t = new BaseTag();
					t.setValue(tagText);
					article.getTags().add(t);
				}else{
					String[] tags= tagText.split(","); 
					
					for (String tag : tags) {
						Tag t = new BaseTag();
						t.setValue(tag);
						article.getTags().add(t);
					}
				}
			}
			
			
			String articleLayout = request
					.getParameter(CommonConstant.ARTICLE_LAYOUT_KEY);

			if (articleLayout != null && !"".equals(articleLayout)) {

				if (LayoutBehavior.HORIZONTAL_LAYOUT_NAME
						.equalsIgnoreCase(articleLayout)) {
					layoutBehavior = new BaseLayoutDescriptor();
					article.setLayoutBehavior(layoutBehavior);
				} else {
					layoutBehavior = new BaseLayoutDescriptor();
					layoutBehavior.setName(LayoutBehavior.CENTER_LAYOUT_NAME);
					layoutBehavior.setRelativeCSSPath(LayoutBehavior.DEFAULT_CENTER_RELATIVE_CSS_PATH);
				}
			}
			article.setAuthor(author);
			article.setTitle(title);
			article.setText(content);
			article.getImages().addAll(uploadedImages);
			article.setMusicURL(musicFullUrl);
			author.getArticles().add(article);
			articleService.addArticle(article);
		}
		return article;
	}


	/** 新增单篇文章 */
	// @RequestMapping(method = RequestMethod.POST)
	// public ModelAndView addArticle(HttpServletRequest request,
	// HttpServletResponse response) {
	// ModelAndView view = new ModelAndView();
	// Article article = new BaseArticle();
	// User author = getSessionUser(request);
	// if (author == null) {
	// author = new BaseUser();
	// author.setUsername("fakeUser");
	// }
	// if (author != null) {
	// String title = request
	// .getParameter(CommonConstant.ARTICLE_TITLE_KEY);
	// String content = request
	// .getParameter(CommonConstant.ARTICLE_CONTENT_KEY);
	// List<MultipartFile> requestImages = getRequestImages(request);
	// try {
	// List<ImageDescriptor> uploadedImages = handleImages(requestImages
	// .toArray(new MultipartFile[0]));
	// article.setAuthor(author);
	// article.setTitle(title);
	// article.setText(content);
	// article.getImages().addAll(uploadedImages);
	// articleService.addArticle(article);
	// view.addObject("article", article);
	// view.setViewName("showArticle");
	// } catch (IOException e) {
	// logger.error(e);
	// } catch (Exception e) {
	// logger.error(e);
	// }
	// }
	// return view;
	// }


	/**
	 * 负责处理请求中的图片 将包含已经上传成功的图片的信息封装成ImageDescriptor返回
	 * 
	 * @throws IOException
	 * */
	private List<ImageDescriptor> handleImages(String imagesInfoJsonString) {
		List<ImageDescriptor> uploadedImages = null;
		JSONObject imagesInfo = JSONObject.fromObject(imagesInfoJsonString);
		String relativeUrl = imagesInfo.getString(CommonConstant.URL_KEY);
		if (uploadedImages == null) {
			uploadedImages = new ArrayList<ImageDescriptor>();
		}
		ImageDescriptor imageDescriptor = new BaseImageDescriptor();
		// imageDescriptor.setName(image.getName());
		imageDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
		 imageDescriptor.setRelativePath(relativeUrl);
		 String fullUrl = "http://"+CommonConstant.IMG_BUCKET_NAME+CommonConstant.DEFAULT_UPYUN_DOMAIN_SUFFIX+relativeUrl;
		 imageDescriptor.setUrl(fullUrl);
		// imageDescriptor.setFileSecret(fileSecret)
		uploadedImages.add(imageDescriptor);
		return uploadedImages;
	}
	
	private String handleMusic(String musicInfoJsonString){
		JSONObject imagesInfo = JSONObject.fromObject(musicInfoJsonString);
		String relativeUrl = imagesInfo.getString(CommonConstant.URL_KEY);
		return "http://"+CommonConstant.FILE_BUCKET_NAME+CommonConstant.DEFAULT_UPYUN_DOMAIN_SUFFIX+relativeUrl;
	}
}
