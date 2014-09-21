package com.pandorabox.web.handler;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.FileDescriptor;
import com.pandorabox.domain.impl.BaseFileDescriptor;

public class FileDescriptorHandler extends DescriptorHandler<FileDescriptor> {

	private static FileDescriptorHandler instance = new FileDescriptorHandler();

	private FileDescriptorHandler() {
		super();
	}

	protected FileDescriptor buildDescriptor(String fileName, String relativeUrl) {
		FileDescriptor musicDescriptor = new BaseFileDescriptor();
		musicDescriptor.setName(fileName);
		musicDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
		musicDescriptor.setRelativePath(relativeUrl);
		String musicFullUrl = CommonConstant.HTTP + CommonConstant.MUSIC_DOMAIN
				+ relativeUrl;
		musicDescriptor.setUrl(musicFullUrl);
		return musicDescriptor;
	}

	protected FileDescriptor createDescriptor() {
		return null;
	}

	protected void setUpDescriptor(FileDescriptor musicDescriptor,
			String fileName, String relativeUrl) {
		musicDescriptor.setName(fileName);
		musicDescriptor.setBucketPath(CommonConstant.IMG_BUCKET_NAME);
		musicDescriptor.setRelativePath(relativeUrl);
		String musicFullUrl = CommonConstant.HTTP + CommonConstant.MUSIC_DOMAIN
				+ relativeUrl;
		musicDescriptor.setUrl(musicFullUrl);
	}

	public static FileDescriptorHandler getInstance() {
		return instance;
	}
}
