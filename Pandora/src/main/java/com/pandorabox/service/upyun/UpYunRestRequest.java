package com.pandorabox.service.upyun;

public interface UpYunRestRequest {
	
	public static final String HTTP_METHOD_POST = "post";
	
	public static final String HTTP_METHOD_GET = "get";
	
	public static final String HTTP_METHOD_PUT = "put";
	
	public static final String HTTP_METHOD_DELETE = "delete";
	
	/**
	 * 返回请求需要的签名
	 * */
	public String getAuthorization();
	
}
