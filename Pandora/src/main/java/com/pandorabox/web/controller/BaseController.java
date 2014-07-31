package com.pandorabox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;
import com.pandorabox.service.UserService;

/**
 * 
 * 所有Controller的基类
 * 
 */
public class BaseController {
	
	private static boolean DEBUG = true;
	private static final String DEBUG_UNAME = "fakeUser";

	@Autowired
	protected UserService userService;
	
	/**
	 * 获取保存在Session中的用户对象
	 * 
	 * @param request
	 * @return
	 */
	protected User getSessionUser(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT);
		
		//Hacked code for insert a user in local develop mode.
		if(user == null && DEBUG){
			user = new BaseUser();
			user.setUsername(DEBUG_UNAME);
			setSessionUser(request,user);
		}
		return (User) user;
	}
   
	/**
	 * 保存用户对象到Session中
	 * @param request
	 * @param user
	 */
	protected void setSessionUser(HttpServletRequest request,User user) {
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT,
				user);
	}
	
	/**
	 * 删除session中的用户对象
	 * @param request
	 * @param user
	 */
	protected void removeSessionUser(HttpServletRequest request) {
		request.getSession().removeAttribute(CommonConstant.USER_CONTEXT);
	}
	

	/**
	 * 获取基于应用程序的url绝对路径
	 * 
	 * @param request
	 * @param url
	 *            以"/"打头的URL地址
	 * @return 基于应用程序的url绝对路径
	 */
	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		Assert.hasLength(url, "url不能为空");
		Assert.isTrue(url.startsWith("/"), "必须以/打头");
		return request.getContextPath() + url;
	}
	
	
}
