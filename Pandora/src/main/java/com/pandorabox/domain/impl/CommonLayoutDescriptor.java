package com.pandorabox.domain.impl;

import com.pandorabox.domain.LayoutBehavior;

public class CommonLayoutDescriptor implements LayoutBehavior {

	private static final long serialVersionUID = 4330446575229575054L;

	private int layoutId;
	
	/*默认布局为水平布局*/
	private String name = LayoutBehavior.HORIZONTAL_LAYOUT_NAME;
	
	private String relativeCSSPath = LayoutBehavior.DEFAULT_HORIZONTAL_RELATIVE_CSS_PATH;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelativeCSSPath() {
		return relativeCSSPath;
	}

	public void setRelativeCSSPath(String relativeCSSPath) {
		this.relativeCSSPath = relativeCSSPath;
	}

	public int getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}
	
}
