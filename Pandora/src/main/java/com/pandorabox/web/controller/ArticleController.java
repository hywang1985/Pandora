package com.pandorabox.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.Article;
import com.pandorabox.domain.FileDescriptor;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseArticle;
import com.pandorabox.domain.impl.BaseTag;
import com.pandorabox.exception.NoUserException;
import com.pandorabox.exception.PandoraException;
import com.pandorabox.service.ArticleService;
import com.pandorabox.service.LayoutService;
import com.pandorabox.service.upyun.UpYunService;
import com.pandorabox.web.handler.DescriptorType;

@Controller
@RequestMapping("/article")
/**
 * 通过这个控制器可以对文章进行操作，采用Restful设计控制器的API
 * */
public class ArticleController extends BaseController {

	private static final String ID_MAPPING_EXPRESSION = "/{id}";

	private static final String DYLOAD_ROOT_PATH = "/dyload";
	
	private static final String DYLOAD_PREVIOUS_PATH = "/dyload/previous";
	
	private static final String DYLOAD_NEXT_PATH = "/dyload/next";
	
	private static final String DYLOAD_RANDOM_PATH = "/dyload/random";

	private static final String ARTICLE_URL_PREFIX = "/article/";
	
	private static Logger logger = Logger.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;

	@Autowired
	private UpYunService upService;
	
	@Autowired
	private LayoutService layoutService;
	

	/**
	 * 显示单篇文章
	 */
	@RequestMapping(value = ID_MAPPING_EXPRESSION)
	public ModelAndView showArticle(@PathVariable int id) {
		Article article = articleService.getArticleById(id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CommonConstant.RAN_MAIN_PAGE_NAME);
		final User author = article.getAuthor();
		JSONObject authorObject = filterOutCirculatedArticles(author);
		JSONObject articleDataObject = filterOutCirculatedAuthor(article);
		articleDataObject.put(CommonConstant.AUTHOR_DATA_OBJECT_KEY,
				authorObject);
		mav.addObject(CommonConstant.ARTICLE_DATA_OBJECT_KEY, articleDataObject);
		return mav;
	}


	private JSONObject filterOutCirculatedAuthor(Article article) {
		JsonConfig filterUserconfig = new JsonConfig();
		filterUserconfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {

				if (CommonConstant.AUTHOR_DATA_OBJECT_KEY.equals(name)) {
					return true;
				}
				return false;
			}
		});

		return JSONObject.fromObject(article, filterUserconfig);
	}


	private JSONObject filterOutCirculatedArticles(final User author) {
		JsonConfig filterArticlesconfig = new JsonConfig();
		filterArticlesconfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {

				if (CommonConstant.ARTICLES_PROPERTY_KEY.equals(name)) {
					return true;
				}
				return false;
			}
		});
		return JSONObject.fromObject(author, filterArticlesconfig);
	}
	

	/**
	 * 动态加载文章,返回JSON, ajax交互用
	 * 
	 * @Deprecated
	 * @see getRandomArticle
	 */
	@RequestMapping(value = DYLOAD_ROOT_PATH, method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public Map<String, Object> loadArticle(HttpServletRequest request) {
		logger.info("Loading articles"); ////$NON-NLS-N$
		String startIndex = request
				.getHeader(CommonConstant.ARTICLE_START_INDEX_HEADER_NAME);
		Map<String, Object> articles = new HashMap<String, Object>(
				CommonConstant.ARTICLE_LOAD_COUNT);
		articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try {
			List<Article> articlesByPage = articleService.getArticlesByPage(
					Integer.parseInt(startIndex),
					CommonConstant.ARTICLE_LOAD_COUNT);
			articles.put(CommonConstant.DATA_OBJECT_KEY, articlesByPage);
		} catch (Exception e) {
			articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return articles;
	}
	
	/**
	 * 加载前几篇文章,返回JSON, ajax交互
	 * 
	 * @Deprecated
	 * @see getRandomArticle
	 */
	@RequestMapping(value = DYLOAD_PREVIOUS_PATH, method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public Map<String, Object> loadPreviousArticles(HttpServletRequest request) {
		logger.info("Loading articles");
		String startId = request
				.getHeader(CommonConstant.START_ARTICLE_ID_HEADER_NAME);
		Map<String, Object> articles = new HashMap<String, Object>(
				CommonConstant.ARTICLE_LOAD_COUNT);
		articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try {
			List<Article> previousArticles = articleService
					.getPreviousArticles(Integer.parseInt(startId),
							CommonConstant.ARTICLE_LOAD_COUNT);
			articles.put(CommonConstant.DATA_OBJECT_KEY, previousArticles);
		} catch (Exception e) {
			articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return articles;
	}
	
	/**
	 * 加载后几篇文章,返回JSON, ajax交互用
	 * 
	 * @Deprecated
	 * @see getRandomArticle
	 */
	@RequestMapping(value = DYLOAD_NEXT_PATH, method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public Map<String, Object> loadNextArticles(HttpServletRequest request) {
		logger.info("Loading articles");
		String startId = request
				.getHeader(CommonConstant.START_ARTICLE_ID_HEADER_NAME);
		Map<String, Object> articles = new HashMap<String, Object>(
				CommonConstant.ARTICLE_LOAD_COUNT);
		articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try {
			List<Article> nextArticles = null;
			if (startId == null || CommonConstant.EMPTY_STRING.equals(startId)) {
				nextArticles = articleService.getArticlesByPage(0,
						CommonConstant.ARTICLE_LOAD_COUNT);
			} else {
				nextArticles = articleService.getNextArticles(
						Integer.parseInt(startId),
						CommonConstant.ARTICLE_LOAD_COUNT);
			}
			if (nextArticles != null) {
				articles.put(CommonConstant.DATA_OBJECT_KEY, nextArticles);
			}
		} catch (Exception e) {
			articles.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return articles;
	}
	
	@RequestMapping(value = DYLOAD_RANDOM_PATH, method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getRandomArticle(HttpServletRequest request) {
		logger.info("Retrieving random article...");
		String previousId = request.getHeader(CommonConstant.PREVIOUS_ID);
		Map<String, Object> result = new HashMap<String, Object>(CommonConstant.ARTICLE_LOAD_COUNT);
		result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		try{
			Article randomOne = articleService.getRandomArticle(Integer.parseInt(previousId));
			if(randomOne!=null){
				result.put(CommonConstant.DATA_OBJECT_KEY, randomOne);
			}
		} catch (Exception e) {
			result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
			throw new PandoraException(e);
		}
		return result;
	}
	

	/**
	 * 新增单篇文章,REST JSON Service
	 * 
	 * @throws IOException
	 * */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addArticle(HttpServletRequest request,
			@RequestParam(CommonConstant.ARTICLE_TITLE_KEY) String title,
			@RequestParam(CommonConstant.ARTICLE_CONTENT_KEY)String content,
			@RequestParam(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY)String imagesInfoJsonString,
			@RequestParam(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY)String musicInfoJsonString,
			@RequestParam(CommonConstant.ARTICLE_TAGS_KEY) String tagText,
			@RequestParam(CommonConstant.ARTICLE_LAYOUT_KEY) String articleLayout,
			@RequestParam(CommonConstant.MUSIC_SELECTED_INDEX_KEY) String musicSelectedIndex) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
		Article article = null;
		try {
			User author = getSessionUser(request);
			if (author == null) {
				throw new NoUserException();
			}
			if (author != null) {
				article = buildArticle(title, content, imagesInfoJsonString,
						musicInfoJsonString, tagText, articleLayout,
						musicSelectedIndex, author);
				author.getArticles().add(article);
				int id = articleService.addArticle(article);
				resultMap.put(CommonConstant.STATUS_KEY,
						CommonConstant.STATUS_OK);
				resultMap.put(CommonConstant.URL_DATA_OBJECT_KEY,
						ARTICLE_URL_PREFIX + id);
				resultMap.put(CommonConstant.DATA_OBJECT_KEY, article);
			}
		} catch (Exception e) {
			throw new PandoraException(e);
		}
		return resultMap;
	}


	 /** 更新单篇文章
	  * REST JSON Service 
	  * */
	 @RequestMapping(value = ID_MAPPING_EXPRESSION, method = RequestMethod.PUT)
	 @ResponseBody
	public Object updateArticle(@PathVariable int id, HttpServletRequest request,
			@RequestParam(CommonConstant.ARTICLE_TITLE_KEY) String title,
			@RequestParam(CommonConstant.ARTICLE_CONTENT_KEY)String content,
			@RequestParam(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY)String addedImgsInfo,
			@RequestParam(CommonConstant.ARTICLE_DELETED_IMAGES_KEY)String deletedImgInfo,
			@RequestParam(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY)String addedMusicInfo,
			@RequestParam(CommonConstant.ARTICLE_DELETED_MUSIC_KEY)String deletedMusicInfo,
			@RequestParam(CommonConstant.ARTICLE_TAGS_KEY) String tagText,
			@RequestParam(CommonConstant.ARTICLE_LAYOUT_KEY) String articleLayout,
			@RequestParam(CommonConstant.MUSIC_SELECTED_INDEX_KEY) String musicSelectedIndex) {
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
				// 处理上传成功的图片的信息
				List<ImageDescriptor> uploadedImages = DescriptorType.IMAGE.getHandler().convertJSONToDescriptors(addedImgsInfo);
				List<Integer> deletedImages = DescriptorType.IMAGE.getHandler().getDelectedDescriptors(deletedImgInfo);
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
				List<FileDescriptor> uploadedMusics  = DescriptorType.FILE.getHandler().convertJSONToDescriptors(addedMusicInfo);
				// 处理要删除的音乐的信息
				List<Integer> deleteddMusics =  DescriptorType.FILE.getHandler().getDelectedDescriptors(deletedMusicInfo);
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
				// 处理标签，前端校验，确保传过来的值符合要求
				if (tagText != null && !CommonConstant.EMPTY_STRING.equals(tagText)) {
					if (!tagText.contains(CommonConstant.COMMA)) { // 用逗号分隔多个标签
						if (!article.containsTag(tagText)) {
							Tag t = new BaseTag();
							t.setValue(tagText);
							article.getTags().add(t);
						}
					} else {
						String[] tags = tagText.split(CommonConstant.COMMA);

						for (String tag : tags) {
							if (!article.containsTag(tag)) {
								Tag t = new BaseTag();
								t.setValue(tag);
								article.getTags().add(t);
							}
						}
					}
				}

				layoutService.setupLayout(article, articleLayout);
				article.setTitle(title);
				article.setText(content);
				if (uploadedImages != null) {
					article.getImages().addAll(uploadedImages);
				}
				if (uploadedMusics != null) {
					article.getFiles().addAll(uploadedMusics);
					// 处理选中音乐参数
					article.setMusicSelected(uploadedMusics,
							musicSelectedIndex);
				}
				articleService.updateArticle(article);
				result.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
				result.put(CommonConstant.URL_DATA_OBJECT_KEY, ARTICLE_URL_PREFIX + id);
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
	@RequestMapping(value = ID_MAPPING_EXPRESSION, method = RequestMethod.DELETE)
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
	
	private Article buildArticle(String title, String content,
			String imagesInfoJsonString, String musicInfoJsonString,
			String tagText, String articleLayout, String musicSelectedIndex,
			User author) {
		Article article = new BaseArticle();
		// 处理标签，前端校验，确保传过来的值符合要求
		article.setupTag(tagText);
		layoutService.setupLayout(article, articleLayout);
		article.setAuthor(author);
		article.setTitle(title);
		article.setText(content);
		List<ImageDescriptor> uploadedImages = DescriptorType.IMAGE.getHandler().convertJSONToDescriptors(imagesInfoJsonString);
		List<FileDescriptor> uploadedMusics  = DescriptorType.FILE.getHandler().convertJSONToDescriptors(musicInfoJsonString);
		if (uploadedImages != null) {
			article.getImages().addAll(uploadedImages);
		}
		if (uploadedMusics != null) {
			article.getFiles().addAll(uploadedMusics);
			// 处理选中音乐参数
			article.setMusicSelected(uploadedMusics,
					musicSelectedIndex);
		}
		return article;
	}
}
