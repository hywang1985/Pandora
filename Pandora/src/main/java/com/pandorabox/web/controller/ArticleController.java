package com.pandorabox.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.pandorabox.exception.NoUserException;
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

	private static final String AUTHOR_PROPERTY_KEY = "author";
	
	private static final String ARTICLES_PROPERTY_KEY = "articles";
	
	private static Logger logger = Logger.getLogger(ArticleController.class);
	@Autowired
	private ArticleService articleService;

	@Autowired
	private UpYunService upService;
	
	@Autowired 
	private LayoutService layoutService;
	

	 /** 显示单篇文章 */
	 @RequestMapping(value = "/{id}")
	 public ModelAndView showArticle(@PathVariable int id,
	 HttpServletRequest request, HttpServletResponse response) {
		 Article article = articleService.getArticleById(id);
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("ran");
		 JsonConfig filterUserconfig = new JsonConfig();
		 filterUserconfig.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	             
				if (AUTHOR_PROPERTY_KEY.equals(name)) {
	                  return true;
	              }
	              return false;
	           }
	       });
		 
		 JsonConfig filterArticlesconfig = new JsonConfig();
		 filterArticlesconfig.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	             
				if (ARTICLES_PROPERTY_KEY.equals(name)) {
	                  return true;
	              }
	              return false;
	           }
	       });
	     JSONObject authorObject = JSONObject.fromObject(article.getAuthor(),filterArticlesconfig);
		 JSONObject articleData = JSONObject.fromObject(article,filterUserconfig);
		 articleData.put(AUTHOR_PROPERTY_KEY, authorObject);
		 mav.addObject("article", articleData);
		 return  mav;
	 }

	 /** 动态加载文章,返回JSON, ajax交互用 
	  * @Deprecated 
	  * @see getRandomArticle
	  * */
	@RequestMapping(value = "/dyload", method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public Map<String,Object> loadArticle(HttpServletRequest request) {
		logger.info("Loading articles");
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
	
	/** 加载前几篇文章,返回JSON, ajax交互用 
	 * @Deprecated 
	 * @see getRandomArticle
	 * */
	@RequestMapping(value = "/dyload/previous", method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public Map<String,Object> loadPreviousArticles(HttpServletRequest request) {
		logger.info("Loading articles");
		String startId = request.getHeader(CommonConstant.START_ARTICLE_ID_HEADER_NAME);
		Map<String, Object> articles = new HashMap<String, Object>(CommonConstant.ARTICLE_LOAD_COUNT);
		articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try{
			List<Article> previousArticles = articleService.getPreviousArticles(Integer.parseInt(startId), CommonConstant.ARTICLE_LOAD_COUNT);
			articles.put("data", previousArticles);
		} catch (Exception e) {
			articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return articles;
	}
	
	/** 加载后几篇文章,返回JSON, ajax交互用
	 * @Deprecated 
	 * @see getRandomArticle
	 * */
	@RequestMapping(value = "/dyload/next", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> loadNextArticles(HttpServletRequest request) {
		logger.info("Loading articles");
		String startId = request.getHeader(CommonConstant.START_ARTICLE_ID_HEADER_NAME);
		Map<String, Object> articles = new HashMap<String, Object>(CommonConstant.ARTICLE_LOAD_COUNT);
		articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try{
			List<Article> nextArticles = null;
			if(startId==null || "".equals(startId)){
				nextArticles = articleService.getArticlesByPage(0, CommonConstant.ARTICLE_LOAD_COUNT);
			}else{
				nextArticles = articleService.getNextArticles(Integer.parseInt(startId), CommonConstant.ARTICLE_LOAD_COUNT);
			}
			if(nextArticles!=null){
				articles.put("data", nextArticles);
			}
		} catch (Exception e) {
			articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return articles;
	}
	
	@RequestMapping(value = "/dyload/random", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getRandomArticle(HttpServletRequest request) {
		logger.info("Retrieving random article...");
		String previousId = request.getHeader(CommonConstant.PREVIOUS_ID);
		Map<String, Object> result = new HashMap<String, Object>(CommonConstant.ARTICLE_LOAD_COUNT);
		result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try{
			Article randomOne = articleService.getRandomArticle(Integer.parseInt(previousId));
			if(randomOne!=null){
				result.put("data", randomOne);
			}
		} catch (Exception e) {
			result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return result;
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
				 throw new NoUserException();
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
				 throw new NoUserException();
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

				if (deleteddMusics != null) {
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
				result.put("imgs", article.getImages());
				result.put("mscs", article.getFiles());
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
				 throw new NoUserException();
			}
			Article article = articleService.getArticleById(id);
			Iterator<ImageDescriptor> imgIt = article.getImages().iterator();
			while (imgIt.hasNext()) {
				ImageDescriptor img = imgIt.next();
				upService.deleteFile(img.getRelativePath());

			}
			//为了避免StaleObjectStateException，需要手动从user对象中移除删除的文章
			List existArticles = author.getArticles();
			if(existArticles.contains(article)){
				existArticles.remove(article);
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
				String fileName = null;
				JSONObject info = JSONObject.fromObject(it.next());
				String relativeUrl = info.getString(CommonConstant.URL_KEY);
				//从类似于/user/abc.pic这样的路径下通过正则表达式找到文件名abc.pic
				String[] splited = StringUtils.split(relativeUrl, "/");
				if(splited.length>1){
					fileName = splited[splited.length-1];
				}else{
					fileName = relativeUrl;
				}
				
				switch (fileType) {
				case FILE:
					if (handledResult == null) {
						handledResult = new ArrayList<FileDescriptor>();
					}
					
					FileDescriptor musicDescriptor = new BaseFileDescriptor();
					musicDescriptor.setName(fileName);
					musicDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
					musicDescriptor.setRelativePath(relativeUrl);
					String musicFullUrl = CommonConstant.HTTP+CommonConstant.MUSIC_DOMAIN+relativeUrl;
					musicDescriptor.setUrl(musicFullUrl);
					// imageDescriptor.setFileSecret(fileSecret)
					handledResult.add(musicDescriptor);
					break;

				case IMAGE:
					if (handledResult == null) {
						handledResult = new ArrayList<ImageDescriptor>();
					}
					ImageDescriptor imageDescriptor = new BaseImageDescriptor();
					imageDescriptor.setName(fileName);
					imageDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
					imageDescriptor.setRelativePath(relativeUrl);
					String imageFullUrl = CommonConstant.HTTP+CommonConstant.IMAGE_DOMAIN+relativeUrl;
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
