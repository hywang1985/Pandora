package com.pandorabox.cons;

/**
 * 整个应用通用的常量
 */
public class CommonConstant {
	/**
	 * 用户对象放到Session中的键名称
	 */
	public static final String USER_CONTEXT = "USER_CONTEXT";

	/**
	 * 将登录前的URL放到Session中的键名称
	 */
	public static final String LOGIN_TO_URL = "toUrl";

	public static final String ARTICLE_TITLE_KEY = "title";

	public static final String ARTICLE_CONTENT_KEY = "content";

	public static final String ARTICLE_LAYOUT_KEY = "layout";

	public static final String ARTICLE_COMMITTED_IMAGES_KEY = "images";
	
	public static final String ARTICLE_TAGS_KEY = "tags";

	public static final String ARTICLE_COMMITTED_MUSIC_KEY = "music";

	public static final String URL_KEY = "url";

	public static final String DEFAULT_UPYUN_DOMAIN_SUFFIX = ".b0.upaiyun.com";

	public static final String DEFAULT_UPYUN_DOMAIN = "http://v0.api.upyun.com";

	/** 又拍根目录 */
	public static final String DIR_ROOT = "/";

	/** 图片Bucket名称 */
	public static final String IMG_BUCKET_NAME = "pandora001";

	/** 文件Bucket名称 */
	public static final String FILE_BUCKET_NAME = "pandora002";

	public static final String HTTP_METHOD_HEADER_NAME = "http_method_name";
	
	public static final String HTTP_CONTENT_LENGTH_HEADER_NAME = "content-length";

	public static final String HTTP_URI_HEADER_NAME = "uri";

	public static final String HTTP_BUCKET_TYPE_HEADER_NAME = "bucket_type";
	
	public static final String COMMON_SAVE_KEY = "/{random}{.suffix}";
	
	public static final String ARTICLE_START_INDEX_HEADER_NAME= "start";
	
	public static final int ARTICLE_LOAD_COUNT= 3;
}
