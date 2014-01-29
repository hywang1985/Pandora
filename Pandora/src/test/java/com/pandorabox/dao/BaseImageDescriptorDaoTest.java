package com.pandorabox.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.AbstractPandoraTransactionalTest;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.impl.BaseImageDescriptor;

public class BaseImageDescriptorDaoTest extends
		AbstractPandoraTransactionalTest {

	private static String GET_IMAGE_BY_NAME = "from ImageDescriptor image where image.name like ?";
	@Autowired
	private ImageDescriptorDao imageDao;

	@Test
	public void addImage() {
		ImageDescriptor image = new BaseImageDescriptor();
		image.setBucketPath("Pandora");
		image.setFileSecret("bac");
		image.setName("demo.jpg");
		image.setRelativePath("/demopic/demo.jpg");
		imageDao.save(image);
	}

	@Test
	public void testGetImageByName() {
		ImageDescriptor retrieved = null;
		List result = imageDao.find(GET_IMAGE_BY_NAME, "demo.jpg");
		if(result!=null && !result.isEmpty()){
			retrieved = (ImageDescriptor)result.get(0);
		}
		Assert.assertNotNull("No image retrieved!",retrieved);
	}
}
