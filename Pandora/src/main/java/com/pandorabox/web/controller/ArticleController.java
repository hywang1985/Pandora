package com.pandorabox.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.Article;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseImageDescriptor;
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

	/** 根目录 */
	private static final String DIR_ROOT = "/";
	
	/**Bucket名称*/
	private static final String BUCKET_NAME = "pandora001";
	
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

	/** 新增单篇文章 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addArticle(HttpServletRequest request, Article article) {
		ModelAndView view = new ModelAndView();
		User author = buildAuthor(request);
		String title = request.getParameter(CommonConstant.ARTICLE_TITLE_KEY);
		String content = request
				.getParameter(CommonConstant.ARTICLE_CONTENT_KEY);
		List<MultipartFile> requestImages = getRequestImages(request);
		try {
			List<ImageDescriptor> uploadedImages = handleImages(requestImages
					.toArray(new MultipartFile[0]));
			article.setAuthor(author);
			article.setTitle(title);
			article.setText(content);
			article.getImages().addAll(uploadedImages);
			articleService.addArticle(article);
			view.addObject("article", article);
			view.setViewName("showArticle");
		} catch (IOException e) {
			logger.error(e);
		} catch (Exception e){
			logger.error(e);
		}
		return view;
	}

	private User buildAuthor(HttpServletRequest request) {
		String authorName = request.getParameter(CommonConstant.AUTHOR_KEY);
		User user = new BaseUser();
		user.setUsername(authorName);
		return user;
	}

	private List<MultipartFile> getRequestImages(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> images = new ArrayList<MultipartFile>();
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile file = multipartRequest.getFile(key);
			if (file.getOriginalFilename().length() > 0) {
				images.add(file);
			}
		}
		return images;
	}

	/**
	 * 负责处理请求中的图片 1. 上传图片到又拍云上，返回图片元信息 2. 封装成ImageDescriptor返回
	 * 
	 * @throws IOException
	 * */
	private List<ImageDescriptor> handleImages(MultipartFile[] images)
			throws IOException {
		List<ImageDescriptor> uploadedImages = null;
		for (MultipartFile image : images) {
			if(uploadedImages==null){
				uploadedImages = new ArrayList<ImageDescriptor>();
			}
			String relativePath = DIR_ROOT + image.getName();
			upService.writeFile(relativePath, image.getBytes(),
					true);
			ImageDescriptor imageDescriptor = new BaseImageDescriptor();
			imageDescriptor.setName(image.getName());
			imageDescriptor.setBucketPath(BUCKET_NAME);
			imageDescriptor.setRelativePath(relativePath);
//			imageDescriptor.setFileSecret(fileSecret)
			uploadedImages.add(imageDescriptor);
		}
		return uploadedImages;
	}
}
