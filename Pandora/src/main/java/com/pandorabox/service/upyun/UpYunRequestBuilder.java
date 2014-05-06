package com.pandorabox.service.upyun;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 用来构建往又拍云传送文件或图片的请求， 由于请求需要许多参数，所以Builder采用 Builder Pattern
 */
public class UpYunRequestBuilder {

	private static final String BUCKET_KEY = "bucket";

	private static final String SAVE_KEY = "save-key";

	private static final String EXPIRATION_KEY = "expiration";

	private static final String ALLOW_FILE_TYPE_KEY = "allow-file-type";

	private static final String CONTENT_LENGTH_RANGE_KEY = "content-length-range";

	private static final String CONTENT_MD5_KEY = "content-md5";

	private static final String CONTENT_SECRET_KEY = "content-secret";

	private static final String CONTENT_TYPE_KEY = "content-type";

	private static final String IMAGE_WIDTH_RANGE_KEY = "image-width-range";

	private static final String IMAGE_HEIGHT_RANGE_KEY = "image-height-range";

	private static final String NOTIFY_URL_KEY = "notify-url";

	private static final String RETURN_URL_KEY = "return-url";

	// 仅支持图片类空间,可以再搭配其他 x-gmkerl-* 参数对图片进行处理
	private static final String X_GMKERL_THUMBNAIL_KEY = "x-gmkerl-thumbnail";

	private static final String X_GMKERL_TYPE_KEY = "x-gmkerl-type";

	private static final String X_GMKERL_VALUE_KEY = "x-gmkerl-value";

	private static final String X_GMKERL_QUALITY_KEY = "x-gmkerl-quality";

	private static final String X_GMKERL_UNSHARP_KEY = "x-gmkerl-unsharp";

	private static final String X_GMKERL_ROTATE_KEY = "x-gmkerl-rotate";

	private static final String X_GMKERL_CROP_KEY = "x-gmkerl-crop";

	private static final String X_GMKERL_EXIF_SWITCH_KEY = "x-gmkerl-exif-switch";

	private static final String EXT_PARAM_KEY = "ext-param";
	
	private String domain = "http://v0.api.upyun.com";
	
	private String formApiSecret = null;
	
	//REST请求需要的部分
	private static final String HTTP_METHOD_NAME_KEY = "http-method";
	
	private static final String HTTP_REQUEST_CONTENT_LENGTH_KEY = "content-length";
	
	private static final String HTTP_REQUEST_URI_KEY = "uri";
	
	private static final String BUCKET_OPERATOR_KEY = "bucket_operator";
	
	private static final String BUCKET_OPERATOR_PWD_KEY= "operator_passwd";
	
	private Map<String, Object> requestOptions = new HashMap<String, Object>();
	
	private static Map<String,Map<String,String>> bucketAuthorizations = new HashMap<String,Map<String,String>>();

	
	
	static {
		Map<String, String> bucketEntry1 = new HashMap<String,String>();
		bucketEntry1.put(BUCKET_OPERATOR_KEY, "tester001");
		bucketEntry1.put(BUCKET_OPERATOR_PWD_KEY, DigestUtils.md5Hex("tester001"));
		Map<String, String> bucketEntry2 = new HashMap<String,String>();
		bucketEntry2.put(BUCKET_OPERATOR_KEY, "pmusicadmin");
		bucketEntry2.put(BUCKET_OPERATOR_PWD_KEY, DigestUtils.md5Hex("Music123"));
		bucketAuthorizations.put("pandora001", bucketEntry1);
		bucketAuthorizations.put("pandora002", bucketEntry2);
	}
	public UpYunRequestBuilder setDomain(String domain){
		this.domain = domain;
		return this;
	}
	
	public UpYunRequestBuilder setFormApiSecret(String formApiSecret){
		this.formApiSecret = formApiSecret;
		return this;
	}
	
	public UpYunRequestBuilder setBucket(String value) {
		requestOptions.put(BUCKET_KEY, value);
		return this;
	}

	public UpYunRequestBuilder setSaveKey(String value) {
		requestOptions.put(SAVE_KEY, value);
		return this;
	}

	public UpYunRequestBuilder setExpiration(long value) {
		requestOptions.put(EXPIRATION_KEY, value);
		return this;
	}

	/**
	 * @param value 文件类型限制
	 * 根据文件扩展名限制上传文件
	 * 如 jpg,gif,png 表示仅允许上传扩展名为 jpg,gif,png 三种类型的文件
	 */
	public UpYunRequestBuilder setAllowFileType(String[] value) {
		requestOptions.put(ALLOW_FILE_TYPE_KEY, value);
		return this;
	}
	/**
	 * @param range 文件大小限制
	 * 格式：最小值,最大值，如 102400,1024000
	 * 单位：Bytes
	 * 含义：仅允许上传 100Kb～1Mb 的文件
	 * */
	public UpYunRequestBuilder setContentLengthRange(int[] range) {
		requestOptions.put(CONTENT_LENGTH_RANGE_KEY, range);
		return this;
	}
	/**
	 * @param value 文件校验码
	 * 根据上传文件的内容进行 md5 校验后得到的数值
	 * */
	public UpYunRequestBuilder setContentMD5(String value) {
		requestOptions.put(CONTENT_MD5_KEY, value);
		return this;
	}
	/**
	 * @param value 原图访问密钥
	 * */
	public UpYunRequestBuilder setContentSecret(String value) {
		requestOptions.put(CONTENT_SECRET_KEY, value);
		return this;
	}
	/**
	 * @param value 文件类型 
	 * 上传文件时，允许用户强行指定该文件的文件类型；
	 * 默认情况下，系统会通过文件扩展名自动识别
	 * */
	public UpYunRequestBuilder setContentType(String value) {
		requestOptions.put(CONTENT_TYPE_KEY, value);
		return this;
	}
	/**
	 * @param range 图片宽度限制
	 * 格式：最小值,最大值，如 0,1024
	 * 单位：像素
	 * 含义：仅允许上传宽度在 0～1024px 范围的图片文件
	 * 注意：非图片文件使用该参数时，将直接返回“不是图片”的错误
	 * */
	public UpYunRequestBuilder setImageWidthRange(int[] range) {
		requestOptions.put(IMAGE_WIDTH_RANGE_KEY, range);
		return this;
	}
	/**
	 * @param 图片高度限制
	 * 格式：最小值,最大值，如 0,1024
	 * 单位：像素
	 * 含义：仅允许上传高度在 0～1024px 范围的图片文件
	 * 注意：非图片文件使用该参数时，将直接返回“不是图片”的错误
	 * */
	public UpYunRequestBuilder setImageHeightRange(int[] range) {
		requestOptions.put(IMAGE_HEIGHT_RANGE_KEY, range);
		return this;
	}
	/**
	 * @param url 异步回调 url
	 * 表单上传完成后，云存储服务端主动把上传结果 POST 到该 URL
	 * 需要确保公网可以正常访问该 URL
	 * */
	public UpYunRequestBuilder setNotifyURL(String url) {
		requestOptions.put(NOTIFY_URL_KEY, url);
		return this;
	}
	/**
	 * @param url 同步跳转 url
	 * 表单上传完成后，使用 http 302 的方式跳转到该 URL
	 * */
	public UpYunRequestBuilder setReturnURL(String url) {
		requestOptions.put(RETURN_URL_KEY, url);
		return this;
	}
	/**
	 * @param value 缩略图版本名称
	 * 仅支持图片类空间
	 * 可以再搭配其他 x-gmkerl-* 参数对图片进行处理
	 * */
	public UpYunRequestBuilder setXGmkerlThumbnail(String value) {
		requestOptions.put(X_GMKERL_THUMBNAIL_KEY, value);
		return this;
	}
	/**
	 * @param value 缩略类型
	 * fix_width 等
	 * */
	public UpYunRequestBuilder setXGmkerlType(String value) {
		requestOptions.put(X_GMKERL_TYPE_KEY, value);
		return this;
	}
	/**
	 * @param value 缩略类型对应的参数值
	 * */
	public UpYunRequestBuilder setXGmkerlValue(String value) {
		requestOptions.put(X_GMKERL_VALUE_KEY, value);
		return this;
	}
	/**
	 * @param value 缩略图质量
	 * 图片压缩质量，默认 95
	 * */
	public UpYunRequestBuilder setXGmkerlQuality(int value) {
		requestOptions.put(X_GMKERL_QUALITY_KEY, value);
		return this;
	}
	/**
	 * @param value 图片锐化
	 * 是否进行锐化处理，默认锐化（true）
	 * */
	public UpYunRequestBuilder setXGmkerlUnsharp(boolean value) {
		requestOptions.put(X_GMKERL_UNSHARP_KEY, value);
		return this;
	}
	/**
	 * @param value 图片旋转
	 * 只接受"auto"，"90"，"180"，"270"四种参数，其他参数都视为错误
	 * */
	public UpYunRequestBuilder setXGmkerlRotate(String value) {
		requestOptions.put(X_GMKERL_ROTATE_KEY, value);
		return this;
	}
	/**
	 * @param value 图片裁剪
	 * 格式：x,y,width,height
	 * 要求：x >= 0 && y >=0 && width > 0 && height > 0 且必须是正整型
	 * */
	public UpYunRequestBuilder setXGmkerlRotate(int[] value) {
		requestOptions.put(X_GMKERL_CROP_KEY, value);
		return this;
	}
	/**
	 * @param value 是否保留exif信息
	 * 参数值：true (保留 EXIF 信息) 
	 * 1.仅搭配"破坏性处理"的参数使用时有效，其他处理均无效；
	 * 2."破坏性处理"包括裁剪(x-gmkerl-crop)、缩略类型(x-gmkerl-type)、
	 * 自定义版本(x-gmkerl-thumbnail)。
	 * */
	public UpYunRequestBuilder setXGmkerlRotate(boolean value) {
		requestOptions.put(X_GMKERL_EXIF_SWITCH_KEY, value);
		return this;
	}
	/**
	 * @param value 额外参数
	 * 仅支持 UTF-8 格式，最长不超过255个字节
	 * */
	public UpYunRequestBuilder setExtParam(String value) {
		requestOptions.put(EXT_PARAM_KEY, value);
		return this;
	}
	/**
	 * @param  method http请求的方法名，POST/PUT/DELETE/GET
	 * */
	public UpYunRequestBuilder setHttpMethodName(String method) {
		requestOptions.put(HTTP_METHOD_NAME_KEY, method);
		return this;
	}
	
	/**
	 * @param length 请求内容长度，但不包括Headers部分
	 * 若发起的是 PUT、POST 等上传请求时，则必须设置该参数
	 * */
	public UpYunRequestBuilder setHttpRequestContentLength(int length) {
		requestOptions.put(HTTP_REQUEST_CONTENT_LENGTH_KEY, length);
		return this;
	}
	
	/**
	 * @param uri 请求路径，
	 * 必须符合 http 协议标准：包含中文名称或特殊字符的文件名（或目录），需进行 urlencode 处理
	 * @throws UnsupportedEncodingException 
	 * */
	public UpYunRequestBuilder setHttpRequestURI(String uri) throws UnsupportedEncodingException {
		requestOptions.put(HTTP_REQUEST_URI_KEY, URLEncoder.encode(uri, "UTF-8"));
		return this;
	}
	
	/**
	 * 构建表单API请求
	 * */
	public final UpYunFormRequest buildFormRequest(){
		String requestURL = domain+"/"+requestOptions.get(BUCKET_KEY);
		JSONObject jsonOptions = JSONObject.fromObject(requestOptions);
		String policy =  Base64.encodeBase64String(jsonOptions.toString().getBytes());
		String signature = DigestUtils.md5Hex(policy + "&" + formApiSecret);
		UpYunFormRequest request = new UpYunFormRequestImpl(signature,policy,requestURL);
		return request;
	}
	
	/**
	 * 构建REST请求
	 * */
	public final UpYunRestRequest buildHttpRestRequest(){
		String bucket = (String) requestOptions.get(BUCKET_KEY);
		String bucketOperator = bucketAuthorizations.get(bucket).get(BUCKET_OPERATOR_KEY);
		String bucketOperatorPwd = bucketAuthorizations.get(bucket).get(BUCKET_OPERATOR_PWD_KEY);
		String uri = (String) requestOptions.get(HTTP_REQUEST_URI_KEY);
		String httpMethodName = (String) requestOptions.get(HTTP_METHOD_NAME_KEY);
		int httpRequestContentLength = (Integer) requestOptions.get(HTTP_REQUEST_CONTENT_LENGTH_KEY);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String dateString = sdf.format(now);
		String signature = DigestUtils.md5Hex(httpMethodName + "&" + uri
				+ "&" + dateString + "&" + httpRequestContentLength + "&"
				+ bucketOperatorPwd);
		String authorization = "UpYun " + bucketOperator + ":" + signature;
		UpYunRestRequest request = new UpYunHttpRestRequestImpl(authorization);
		return request;
	}
	
	/**
	 * 用来向又拍云发送通过表单的文件上传请求
	 * */
	private static class UpYunFormRequestImpl implements UpYunFormRequest {

		private String signature;
		private String policy;
		private String requestURL;

		private UpYunFormRequestImpl(String signature, String policy,
				String requestURL) {
			this.signature = signature;
			this.policy = policy;
			this.requestURL = requestURL;
		}
		
		public String getSignature() {
			return signature;
		}

		public String getRequestURL() {
			return requestURL;
		}

		public String getPolicy() {
			return policy;
		}

	}
	
	/**
	 * 用来向又拍云发送调用Http Rest API的请求
	 * */
	static class UpYunHttpRestRequestImpl implements UpYunRestRequest{

		private String authorization;
		
		private UpYunHttpRestRequestImpl(String authorization) {
			this.authorization = authorization;
		}

		public String getAuthorization() {
			return authorization;
		}

		public void setAuthorization(String authorization) {
			this.authorization = authorization;
		}
		

	}
}
