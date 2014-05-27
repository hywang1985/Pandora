package com.pandorabox.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pandorabox.common.EFileType;
import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.Article;
import com.pandorabox.domain.FileDescriptor;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseArticle;
import com.pandorabox.domain.impl.BaseFileDescriptor;
import com.pandorabox.domain.impl.BaseImageDescriptor;
import com.pandorabox.domain.impl.BaseLayoutDescriptor;
import com.pandorabox.domain.impl.BaseTag;
import com.pandorabox.domain.impl.BaseUser;
import com.pandorabox.exception.PandoraException;
import com.pandorabox.service.ArticleService;
import com.pandorabox.service.LayoutService;
import com.pandorabox.service.upyun.UpYunService;

@Controller
@RequestMapping("/article")
/**
 * 通过这个控制器可以对文章进行操作，采用Restful设计控制器的API
 * */
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UpYunService upService;
	
	@Autowired 
	private LayoutService layoutService;
	
	private static Pattern filePattern = Pattern.compile("^/\\w*/");

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
		articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try{
			List<Article> articlesByPage = articleService.getArticlesByPage(Integer.parseInt(startIndex), CommonConstant.ARTICLE_LOAD_COUNT);
			articles.put("data", articlesByPage);
		} catch (Exception e) {
			articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return articles;
	}
	
	
	
	 
	 private boolean containsTag(Article article,String tagValue){
		boolean found = false;
		 for(Tag tag: article.getTags()){
			 if(tagValue.equals(tag.getValue())){
				 found = true;
				 break;
			 }
		 }
		 return found;
	 }

	// /** 删除单篇文章 */
	// @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	// public ModelAndView deleteArticle(@PathVariable int id,
	// HttpServletRequest request, HttpServletResponse response) {
	// return new ModelAndView();
	// }

	/** 新增单篇文章,REST JSON Service
	 * @throws IOException 
	 * */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> addArticle(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
		Article article = null;
		LayoutBehavior layoutBehavior = null;
		try {
			User author = getSessionUser(request);
			if (author == null) {
				author = new BaseUser();
				author.setUsername("fakeUser");
				// throw new NoUserException();
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
				List<ImageDescriptor> uploadedImages = handleFiles(
						imagesInfoJsonString, false, EFileType.IMAGE);
				String musicInfoJsonString = request
						.getParameter(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY);
				List<FileDescriptor> uploadedMusics = handleFiles(
						musicInfoJsonString, false, EFileType.FILE);
				String tagText = request
						.getParameter(CommonConstant.ARTICLE_TAGS_KEY);
				// 处理标签，前端校验，确保传过来的值符合要求
				if (tagText != null && !"".equals(tagText)) {
					if (!tagText.contains(",")) {
						Tag t = new BaseTag();
						t.setValue(tagText);
						article.getTags().add(t);
					} else {
						String[] tags = tagText.split(",");
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
					layoutBehavior = layoutService.getLayoutByName(articleLayout);
					if(layoutBehavior==null){
						layoutBehavior = new BaseLayoutDescriptor();
						layoutBehavior.setName(articleLayout);
					}
					article.setLayoutBehavior(layoutBehavior);
				}
				
				article.setAuthor(author);
				article.setTitle(title);
				article.setText(content);
				if(uploadedImages!=null){
					article.getImages().addAll(uploadedImages);
				}
				if(uploadedMusics!=null){
					article.getFiles().addAll(uploadedMusics);
					// 处理选中音乐参数
					String musicSelectedIndex = request
							.getParameter(CommonConstant.MUSIC_SELECTED_INDEX_KEY);
					setMusicSelected(article, uploadedMusics, musicSelectedIndex);
				}
				author.getArticles().add(article);
				int id = articleService.addArticle(article);
				resultMap.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
				resultMap.put("URL", "/article/" + id);
				resultMap.put("data",article);
			}
		} catch (Exception e) {
			throw new PandoraException(e);
		}
		return resultMap;
	}




	private void setMusicSelected(Article article,
			List<FileDescriptor> uploadedMusics, String musicSelectedIndex) {
		if(musicSelectedIndex!=null && !"".equals(musicSelectedIndex) ){
			for(int i=0;i<uploadedMusics.size();i++){
				if( Integer.parseInt(musicSelectedIndex)==i){
					FileDescriptor music = article.getFiles().get(i);
					music.setSelected(true);
					article.setPickedMusicIndex(i);
					break;
				}
			}
		}
	}
	
	 /** 更新单篇文章
	  * REST JSON Service 
	  * */
	 @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	 @ResponseBody
	 public Object updateArticle(@PathVariable int id,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", "FAIL");
		Article article = null;
		LayoutBehavior layoutBehavior = null;
		try {
			User author = getSessionUser(request);
			if (author == null) {
				author = new BaseUser();
				author.setUsername("fakeUser");
				// throw new NoUserException();
			}
			if (author != null) {
				article = articleService.getArticleById(id);
				String title = request
						.getParameter(CommonConstant.ARTICLE_TITLE_KEY);
				String content = request
						.getParameter(CommonConstant.ARTICLE_CONTENT_KEY);
				// 处理上传成功的图片的信息
				String addedImgsInfo = request
						.getParameter(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY);
				List<ImageDescriptor> uploadedImages = handleFiles(
						addedImgsInfo, false, EFileType.IMAGE);
				// 处理需要删除的图片
				String deletedImgInfo = request
						.getParameter(CommonConstant.ARTICLE_DELETED_IMAGES_KEY);
				List<Integer> deletedImages = handleFiles(deletedImgInfo, true,
						EFileType.IMAGE);
				Iterator<ImageDescriptor> existImgs = article.getImages()
						.iterator();
				String imgOperator = CommonConstant.bucketAuthorizations.get(
						CommonConstant.IMG_BUCKET_NAME).get(
						CommonConstant.BUCKET_OPERATOR_KEY);
				String imgOperatorPwd = CommonConstant.bucketAuthorizations
						.get(CommonConstant.IMG_BUCKET_NAME).get(
								CommonConstant.BUCKET_OPERATOR_CLEAR_PWD_KEY);
				upService.setBucketName(CommonConstant.IMG_BUCKET_NAME);
				upService.setUserName(imgOperator);
				upService.setPassword(imgOperatorPwd);

				if (deletedImages != null) {
					for (int toDelete : deletedImages) {
						while (existImgs.hasNext()) {
							ImageDescriptor img = existImgs.next();
							int imgId = img.getImageId();
							if (imgId == toDelete) {
								upService.deleteFile(img.getRelativePath());
								existImgs.remove();
								break;
							}
						}
					}
				}
				// 处理上传成功的音乐的信息
				String musicInfoJsonString = request
						.getParameter(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY);
				List<FileDescriptor> uploadedMusics = handleFiles(
						musicInfoJsonString, false, EFileType.FILE);
				// 处理要删除的音乐的信息
				String deletedMusicInfo = request
						.getParameter(CommonConstant.ARTICLE_DELETED_MUSIC_KEY);
				List<Integer> deleteddMusics = handleFiles(deletedMusicInfo,
						true, EFileType.FILE);
				Iterator<FileDescriptor> existFiless = article.getFiles()
						.iterator();
				String fileOperator = CommonConstant.bucketAuthorizations.get(
						CommonConstant.FILE_BUCKET_NAME).get(
						CommonConstant.BUCKET_OPERATOR_KEY);
				String fileOperatorPwd = CommonConstant.bucketAuthorizations
						.get(CommonConstant.FILE_BUCKET_NAME).get(
								CommonConstant.BUCKET_OPERATOR_CLEAR_PWD_KEY);
				upService.setBucketName(CommonConstant.IMG_BUCKET_NAME);
				upService.setUserName(fileOperator);
				upService.setPassword(fileOperatorPwd);

				if (deletedImages != null) {
					for (int toDelete : deleteddMusics) {
						while (existFiless.hasNext()) {
							FileDescriptor file = existFiless.next();
							int fileId = file.getFileId();
							if (fileId == toDelete) {
								upService.deleteFile(file.getRelativePath());
								existFiless.remove();
								break;
							}
						}
					}
				}
				String tagText = request
						.getParameter(CommonConstant.ARTICLE_TAGS_KEY);
				// 处理标签，前端校验，确保传过来的值符合要求
				if (tagText != null && !"".equals(tagText)) {
					if (!tagText.contains(",")) { // 用逗号分隔多个标签
						if (!containsTag(article, tagText)) {
							Tag t = new BaseTag();
							t.setValue(tagText);
							article.getTags().add(t);
						}
					} else {
						String[] tags = tagText.split(",");

						for (String tag : tags) {
							if (!containsTag(article, tag)) {
								Tag t = new BaseTag();
								t.setValue(tag);
								article.getTags().add(t);
							}
						}
					}
				}

				String articleLayout = request
						.getParameter(CommonConstant.ARTICLE_LAYOUT_KEY);

				if (articleLayout != null && !"".equals(articleLayout)) {
					layoutBehavior = layoutService.getLayoutByName(articleLayout);
					if(layoutBehavior==null){
						layoutBehavior = new BaseLayoutDescriptor();
						layoutBehavior.setName(articleLayout);
					}
					article.setLayoutBehavior(layoutBehavior);
				}
				article.setTitle(title);
				article.setText(content);
				if (uploadedImages != null) {
					article.getImages().addAll(uploadedImages);
				}
				if (uploadedMusics != null) {
					article.getFiles().addAll(uploadedMusics);
					// 处理选中音乐参数
					String musicSelectedIndex = request
							.getParameter(CommonConstant.MUSIC_SELECTED_INDEX_KEY);
					setMusicSelected(article, uploadedMusics,
							musicSelectedIndex);
				}
				articleService.updateArticle(article);
				result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
				result.put("URL", "/article/" + id);
			}
		} catch (Exception e) {
			throw new PandoraException(e);
		}
		return result;
	}
	
	/**
	 * 删除单篇文章
	 **/
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object deleteArticle(@PathVariable int id,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
			User author = getSessionUser(request);
			if (author == null) {
				author = new BaseUser();
				author.setUsername("fakeUser");
				// throw new NoUserException();
			}
			Article article = articleService.getArticleById(id);
			Iterator<ImageDescriptor> imgIt = article.getImages().iterator();
			while (imgIt.hasNext()) {
				ImageDescriptor img = imgIt.next();
				upService.deleteFile(img.getRelativePath());

			}
			articleService.removeArticle(id);
			result.put(CommonConstant.DELETED_KEY, id);
		} catch (Exception e) {
			result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return result;
	}

	
	/**
	 * 将包含已经上传成功的文件的信息封装返回
	 * @param fileInfoJsonString 需要处理的文件信息
	 * @param isDelete 文件是否做删除操作
	 * @param EFileType 文件类型
	 * @throws IOException
	 * */
	private List handleFiles(String fileInfoJsonString,boolean isDelete,EFileType fileType) {
		List handledResult = null;
		JSONArray imagesInfos = JSONArray.fromObject(fileInfoJsonString);
		Iterator it = imagesInfos.iterator();
		if(!isDelete){
			while(it.hasNext()){
				String name = null;
				JSONObject info = JSONObject.fromObject(it.next());
				String relativeUrl = info.getString(CommonConstant.URL_KEY);
				//从类似于/user/abc.pic这样的路径下通过正则表达式找到文件名abc.pic
				name = filePattern.split(relativeUrl)[1];
				
				switch (fileType) {
				case FILE:
					if (handledResult == null) {
						handledResult = new ArrayList<FileDescriptor>();
					}
					
					FileDescriptor musicDescriptor = new BaseFileDescriptor();
					musicDescriptor.setName(name);
					musicDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
					musicDescriptor.setRelativePath(relativeUrl);
					String musicFullUrl = CommonConstant.HTTP+CommonConstant.FILE_BUCKET_NAME+CommonConstant.DEFAULT_UPYUN_DOMAIN_SUFFIX+relativeUrl;
					musicDescriptor.setUrl(musicFullUrl);
					// imageDescriptor.setFileSecret(fileSecret)
					handledResult.add(musicDescriptor);
					break;

				case IMAGE:
					if (handledResult == null) {
						handledResult = new ArrayList<ImageDescriptor>();
					}
					ImageDescriptor imageDescriptor = new BaseImageDescriptor();
					imageDescriptor.setName(name);
					imageDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
					imageDescriptor.setRelativePath(relativeUrl);
					String imageFullUrl = CommonConstant.HTTPS+CommonConstant.IMG_BUCKET_NAME+CommonConstant.DEFAULT_UPYUN_DOMAIN_SUFFIX+relativeUrl;
					imageDescriptor.setUrl(imageFullUrl);
					// imageDescriptor.setFileSecret(fileSecret)
					handledResult.add(imageDescriptor);
					break;
				}
			}
		}else{
			while(it.hasNext()){
				Integer descriptorId = Integer.parseInt((String)it.next());
				if (handledResult == null) {
					handledResult = new ArrayList<Integer>();
				}
				handledResult.add(descriptorId);
			}
		}
		return handledResult;
	}
}
