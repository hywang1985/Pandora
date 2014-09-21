package com.pandorabox.web.handler;


public enum DescriptorType {
	IMAGE(ImageDescriptorHandler.getInstance()), 
	FILE(FileDescriptorHandler.getInstance());

	private  DescriptorHandler handler;

	DescriptorType(DescriptorHandler handler) {
		this.handler = handler;
	}

	public DescriptorHandler getHandler() {
		return handler;
	}

}
