package com.pandorabox.domain;

import java.io.Serializable;

/**
 * LayoutBehavior对象用来描述布局行为，目前只有水平布局和居中布局两种
 * @author hywang
 * */
public interface LayoutBehavior extends Serializable{
	
	String HORIZONTAL_LAYOUT_NAME = "Horizontal";
	
	String VERTICAL_LAYOUT_NAME = "Vertical";
	
	String DEFAULT_HORIZONTAL_RELATIVE_CSS_PATH = "Horizontal.css";
	
	String DEFAULT_VERTICAL_RELATIVE_CSS_PATH = "Vertical.css";
	
	public int getLayoutId();
	public void setLayoutId(int layoutId);
	
	/**布局的名称*/
	public String getName();
	public void setName(String name);
	
	/**布局对应的CSS文件相对于ApplicationContext的路径*/
	public String getRelativeCSSPath();
	public void setRelativeCSSPath(String relativeCSSPath);
}
