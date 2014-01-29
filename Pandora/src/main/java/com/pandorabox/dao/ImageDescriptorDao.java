package com.pandorabox.dao;

import com.pandorabox.domain.ImageDescriptor;

public interface ImageDescriptorDao extends GenericDataAccessor<ImageDescriptor, Integer> {

	public ImageDescriptor getImageByName(String name);
}
