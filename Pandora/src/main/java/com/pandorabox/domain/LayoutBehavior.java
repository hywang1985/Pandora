package com.pandorabox.domain;

/**
 * LayoutBehavior对象用来描述布局行为，目前只有水平布局和居中布局两种
 * @author hywang
 * */
public interface LayoutBehavior {
	
	/**布局的名称*/
	public String getName();
	public void setName(String name);
	
	/**布局对应的CSS文件相对于ApplicationContext的路径*/
	public String getRelativeCSSPath();
	public String setRelativeCSSPath(String relativeCSSPath);
}
