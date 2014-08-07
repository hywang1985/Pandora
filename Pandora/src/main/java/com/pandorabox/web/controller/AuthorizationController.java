package com.pandorabox.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.User;
import com.pandorabox.exception.NoUserException;
import com.pandorabox.service.upyun.UpYunFormRequest;
import com.pandorabox.service.upyun.UpYunRequestBuilder;
import com.pandorabox.service.upyun.UpYunRestRequest;

@Controller
@RequestMapping("/authorization")
public class AuthorizationController extends BaseController {

	
	private enum EBucketType{
		IMAGE("pandora001","8N09dtHrGfab5NUS7eCzmySfrtQ="),
		FILE("pandora002","80p8vGGFD6rFqYNic3amAVHTzrI=");
		
		private String bucket;
		
		private String apiFormSecret;

		private EBucketType(String bucket,String apiFormSecret) {
			this.bucket = bucket;
			this.apiFormSecret = apiFormSecret;
		}

	}
	private static Logger logger = Logger
			.getLogger(AuthorizationController.class);

	/** 返回操作HTTP REST API的授权 */
	@ResponseBody
	@RequestMapping(value="/rest", method = RequestMethod.POST)
	public Object getRestApiAuthorization(HttpServletRequest request,
			HttpServletResponse response) {
		UpYunRestRequest restRequest = null;
		try {
			String httpMethodName = request.getHeader(CommonConstant.HTTP_METHOD_HEADER_NAME);
			String contentLength = request.getHeader(CommonConstant.HTTP_CONTENT_LENGTH_HEADER_NAME);
			String uri = request.getHeader(CommonConstant.HTTP_URI_HEADER_NAME);
			String bucketType = request.getHeader(CommonConstant.HTTP_BUCKET_TYPE_HEADER_NAME);
			String bucket = EBucketType.valueOf(bucketType.toUpperCase()).bucket;
			restRequest = new UpYunRequestBuilder()
					.setBucket(bucket)
					.setHttpMethodName(httpMethodName)
					.setHttpRequestContentLength(
							Integer.parseInt(contentLength))
					.setHttpRequestURI(uri).buildHttpRestRequest();
		} catch (Exception e) {
			logger.error(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new RuntimeException();
		} 

		return restRequest == null ? null : restRequest.getAuthorization();
	}
	
	/** 返回操作表单API的policy,signature和请求URL*/
	@ResponseBody
	@RequestMapping(value="/form", method = RequestMethod.POST)
	public Object getFormApiOptions(HttpServletRequest request,
			HttpServletResponse response) {
		UpYunFormRequest formRequest = null;
		String bucketType = request
				.getParameter(CommonConstant.HTTP_BUCKET_TYPE_HEADER_NAME);
		logger.info("bucket: "+bucketType);
		String bucket = EBucketType.valueOf(bucketType.toUpperCase()).bucket;
		String formApiSecret = EBucketType.valueOf(bucketType.toUpperCase()).apiFormSecret;
		User user = getSessionUser(request);
		if (user == null) {
			throw new NoUserException();
		}
		String userName = user.getUsername();
		formRequest = new UpYunRequestBuilder().setFormApiSecret(formApiSecret)
				.setBucket(bucket)
				.setDomain(CommonConstant.DEFAULT_UPYUN_DOMAIN)
				.setExpiration(new Date().getTime() + 600)
				.setSaveKey("/" + userName + CommonConstant.COMMON_SAVE_KEY)
				.buildFormRequest();
		JSONObject options = JSONObject.fromObject(formRequest);
		return options;
	}
}
