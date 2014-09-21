package com.pandorabox.domain.assist;

import java.util.List;

import com.pandorabox.domain.ImageDescriptor;

public interface ImageContainer {

	public List<ImageDescriptor> getImages();

	public void setImages(List<ImageDescriptor> images);
}
