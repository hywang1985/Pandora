package com.pandorabox.web.handler;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.impl.BaseImageDescriptor;

public class ImageDescriptorHandler extends DescriptorHandler<ImageDescriptor> {

	private static ImageDescriptorHandler instance = new ImageDescriptorHandler();

	private ImageDescriptorHandler() {
		super();
	}

	protected ImageDescriptor createDescriptor() {
		return new BaseImageDescriptor();
	}

	protected void setUpDescriptor(ImageDescriptor imageDescriptor,
			String fileName, String relativeUrl) {
		imageDescriptor.setName(fileName);
		imageDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
		imageDescriptor.setRelativePath(relativeUrl);
		String imageFullUrl = CommonConstant.HTTP + CommonConstant.IMAGE_DOMAIN
				+ relativeUrl;
		imageDescriptor.setUrl(imageFullUrl);
	}

	public static ImageDescriptorHandler getInstance() {
		return instance;
	}
}
