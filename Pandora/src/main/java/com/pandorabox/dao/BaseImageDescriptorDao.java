package com.pandorabox.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pandorabox.domain.ImageDescriptor;

@Repository("imageDao")
public class BaseImageDescriptorDao extends BaseGenericDataAccessor<ImageDescriptor, Integer>
		implements ImageDescriptorDao {

	private static final String GET_IMAGE_BY_NAME = "from ImageDescriptor image where image.name like ?";
	
	@Override
	public ImageDescriptor getImageByName(String name) {
		ImageDescriptor image = null;
		List result = find(GET_IMAGE_BY_NAME,name);
		if(result!=null && !result.isEmpty()){
			image = (ImageDescriptor)result.get(0);
		}
		return image;
	}

}
