package com.pandorabox.cons;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 整个应用通用的常量
 */
public class CommonConstant {
	/**
	 * 用户对象放到Session中的键名称
	 */
	public static final String USER_CONTEXT = "USER_CONTEXT";
	
	public static final String USER_KEY = "user";

	/**
	 * 将登录前的URL放到Session中的键名称
	 */
	public static final String LOGIN_TO_URL = "toUrl";

	public static final String ARTICLE_TITLE_KEY = "title";

	public static final String ARTICLE_CONTENT_KEY = "content";

	public static final String ARTICLE_LAYOUT_KEY = "layout";

	public static final String ARTICLE_COMMITTED_IMAGES_KEY = "addedImgs";
	
	public static final String ARTICLE_DELETED_IMAGES_KEY = "delImgs";
	
	public static final String ARTICLE_TAGS_KEY = "tags";

	public static final String ARTICLE_COMMITTED_MUSIC_KEY = "addedMscs";
	
	public static final String ARTICLE_DELETED_MUSIC_KEY = "delMscs";

	public static final String URL_KEY = "url";

	public static final String DEFAULT_UPYUN_DOMAIN_SUFFIX = ".b0.upaiyun.com";

	public static final String DEFAULT_UPYUN_DOMAIN = "http://v0.api.upyun.com";

	/** 又拍根目录 */
	public static final String DIR_ROOT = "/";

	/** 图片Bucket名称 */
	public static final String IMG_BUCKET_NAME = "pandora001";

	/** 文件Bucket名称 */
	public static final String FILE_BUCKET_NAME = "pandora002";
	
	/** 又拍云绑定的图片域名*/
	public static final String IMAGE_DOMAIN = "image.ransanity.com";
	
	/** 又拍云绑定的音乐域名*/
	public static final String MUSIC_DOMAIN = "music.ransanity.com";

	public static final String HTTP_METHOD_HEADER_NAME = "http_method_name";
	
	public static final String HTTP_CONTENT_LENGTH_HEADER_NAME = "content-length";

	public static final String HTTP_URI_HEADER_NAME = "uri";

	public static final String HTTP_BUCKET_TYPE_HEADER_NAME = "bucket_type";
	
	public static final String MUSIC_SELECTED_INDEX_KEY= "musicSelected";
	
	//上线以后用/{random32}{.suffix}
	public static final String COMMON_SAVE_KEY = "/{random32}{.suffix}";
	
	public static final String ARTICLE_START_INDEX_HEADER_NAME= "startIndex";
	
	public static final String START_ARTICLE_ID_HEADER_NAME= "startId";
	
	public static final int ARTICLE_LOAD_COUNT= 1;
	
	public static final String HTTP = "http://";
	
	public static final String HTTPS = "https://";

	public static final String BUCKET_OPERATOR_KEY = "bucket_operator";
	
	public static final String BUCKET_OPERATOR_CLEAR_PWD_KEY= "operator_clr_passwd";
	
	public static final String BUCKET_OPERATOR_MD5_PWD_KEY= "operator_md5_passwd";
	
	public static final String STATUS_KEY = "status";
	
	public static final String DELETED_KEY = "deleted";
	
	public static final String STATUS_OK = "OK";
	
	public static final String STATUS_FAIL = "FAIL";
	
	public static final String IMAGE_SNAPSHOT_SUFFIX="!snapshot";
	
	public static final String WEIBO_USER_KEY = "weibo_user";

	public static final String WEIBO_UID_KEY = "id";
	
	public static final String WEIBO_PROFILE_URL_KEY = "profile_url";
	
	public static final String WEIBO_DOMAIN = "www.weibo.com";
	
	public static final String SCREEN_NAME_KEY = "screen_name";
	
	public static final String PREVIOUS_ID = "previousId";
	
	public static Map<String,Map<String,String>> bucketAuthorizations = new HashMap<String,Map<String,String>>();
	
	
	static {
		Map<String, String> bucketEntry1 = new HashMap<String,String>();
		bucketEntry1.put(BUCKET_OPERATOR_KEY, "tester001");
		bucketEntry1.put(BUCKET_OPERATOR_CLEAR_PWD_KEY, "tester001");
		bucketEntry1.put(BUCKET_OPERATOR_MD5_PWD_KEY, DigestUtils.md5Hex("tester001"));
		Map<String, String> bucketEntry2 = new HashMap<String,String>();
		bucketEntry2.put(BUCKET_OPERATOR_KEY, "pmusicadmin");
		bucketEntry2.put(BUCKET_OPERATOR_CLEAR_PWD_KEY,"Music123");
		bucketEntry2.put(BUCKET_OPERATOR_MD5_PWD_KEY, DigestUtils.md5Hex("Music123"));
		bucketAuthorizations.put("pandora001", bucketEntry1);
		bucketAuthorizations.put("pandora002", bucketEntry2);
	}
	
	private CommonConstant(){
		
	}
}
