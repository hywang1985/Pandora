package com.pandorabox.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.User;
import com.pandorabox.exception.PandoraException;
import com.pandorabox.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object> logIn(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
		//通过weibo uid寻找已经绑定的user，如果不存在，创建新的user
		try{
			String StringWeiboUser = request.getParameter(CommonConstant.WEIBO_USER_KEY);
			JSONObject weiboUserJSONObject = JSONObject.fromObject(StringWeiboUser);
			int weiboUid = weiboUserJSONObject.getInt(CommonConstant.WEIBO_UID_KEY);
			String relativeProfileUrl = weiboUserJSONObject.getString(CommonConstant.WEIBO_PROFILE_URL_KEY);
			StringBuilder weiboUserFullUrl = new StringBuilder(CommonConstant.HTTP);
			weiboUserFullUrl.append(CommonConstant.WEIBO_DOMAIN).append("/").append(relativeProfileUrl);
			String screenName = weiboUserJSONObject.getString(CommonConstant.SCREEN_NAME_KEY);
			User user = userService.bindWeiboUser(weiboUid, screenName,weiboUserFullUrl.toString());
			//放user到session,以便调用
			setSessionUser(request, user);
			resultMap.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
			resultMap.put(CommonConstant.USER_KEY, user);
		}catch(Exception e){
			throw new PandoraException(e);
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  logOut(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_FAIL);
		try {
			removeSessionUser(request);
			resultMap.put(CommonConstant.STATUS_KEY, CommonConstant.STATUS_OK);
		} catch (Exception e) {
			throw new PandoraException(e);
		}
		return resultMap;
	}
	
}
