package com.pandorabox.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class PandoraExceptionHandler extends SimpleMappingExceptionResolver {

	private String ajaxErrorView;
	private boolean ajaxShowTechMessage;
	
	private static Logger logger = Logger.getLogger(PandoraExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object o, Exception e) {
		logger.error(e);
		if (isAjax(request)) {
			ExceptionInfo exInfo = ExceptionInfo.valueOf(e.getClass());
			String exceptionMessage = exInfo.getMessage();
			if (ajaxShowTechMessage)
				exceptionMessage += "\n" + getExceptionMessage(e);
			ModelAndView m = new ModelAndView(ajaxErrorView);
			JSONObject responseText = new JSONObject();
			responseText.put("code", "\"" + exInfo.getCode() + "\"");
			responseText.put("message", "\"" + exceptionMessage + "\"");
			m.addObject("exceptionMessage", responseText.toString());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return m;
		} else {
			return super.resolveException(request, response, o, e);
		}
	}

	private String getExceptionMessage(Throwable e) {
		String message = "";
		while (e != null) {
			message += e.getMessage() + "\n";
			e = e.getCause();
		}
		return message;
	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	public String getAjaxErrorView() {
		return ajaxErrorView;
	}

	public void setAjaxErrorView(String ajaxErrorView) {
		this.ajaxErrorView = ajaxErrorView;
	}

	public boolean isAjaxShowTechMessage() {
		return ajaxShowTechMessage;
	}

	public void setAjaxShowTechMessage(boolean ajaxShowTechMessage) {
		this.ajaxShowTechMessage = ajaxShowTechMessage;
	}
}
