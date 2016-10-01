package org.javaee.imageLoader.test.model.applicationServices;

import static org.javaee.imageLoader.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.javaee.imageLoader.test.model.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.tapestry5.upload.services.UploadedFile;
import org.javaee.imageLoader.model.applicationServices.ApplicationServices;
import org.javaee.imageLoader.model.applicationServices.DuplicatedImageNameException;
import org.javaee.imageLoader.model.applicationServices.ImageNotResizedException;
import org.javaee.imageLoader.model.modification.ModificationDao;
import org.javaee.imageLoader.model.uploadedImage.UploadedImage;
import org.javaee.imageLoader.model.uploadedImage.UploadedImageDao;
import org.javaee.modelUtil.exceptions.InstanceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class ApplicationServiceTest {

	@Autowired
	UploadedImageDao imageDao;
	
	@Autowired
	ModificationDao modificationDao;
	
	@Autowired
	ApplicationServices appServices;
	
	private String imageName;
	private String source;
	private UploadedImage image = null;
	private UploadedFile uploadedFile = null;
	
	@Before
	public void setUp() throws DuplicatedImageNameException, ImageNotResizedException {
		imageName = "image1.png";
		source = "/images/";
		
		image = appServices.uploadImage(imageName, uploadedFile);
	}
	
	@Test
	@Ignore
	public void uploadImageTest() throws DuplicatedImageNameException, ImageNotResizedException {
		UploadedImage foundImage = null;
		boolean found;
		
		try {
			foundImage = imageDao.find(image.getUploadedImageId());
			found = true;
		} catch (InstanceNotFoundException e) {
			found = false;
		}
		
		assertTrue (found);
		assertEquals (image.getUploadedImageId(), foundImage.getUploadedImageId());
	}
	
	@Test(expected = DuplicatedImageNameException.class)
	@Ignore
	public void uploadImageDuplicateNameTest() throws DuplicatedImageNameException, ImageNotResizedException {
		appServices.uploadImage(imageName, uploadedFile);
		appServices.uploadImage(imageName, uploadedFile);
	}
	
	@Test
	@Ignore
	public void updateImageTest() throws DuplicatedImageNameException, 
	InstanceNotFoundException, ImageNotResizedException {
		String newImageName = "new Name";
		String newSource = "/new/Source/";
		
		appServices.updateImage(image.getUploadedImageId(), imageName, source);
		
		assertEquals(image.getImageName(), imageName);
		assertEquals(image.getSource(), source);
		
		appServices.updateImage(image.getUploadedImageId(), newImageName, source);
		
		assertEquals(image.getImageName(), newImageName);
		assertEquals(image.getSource(), source);
		
		appServices.updateImage(image.getUploadedImageId(), newImageName, newSource);
		
		assertEquals(image.getImageName(), newImageName);
		assertEquals(image.getSource(), newSource);
	}
	
	@Test(expected = DuplicatedImageNameException.class)
	@Ignore
	public void updateImageDuplicateNameTest() throws DuplicatedImageNameException, 
	InstanceNotFoundException, ImageNotResizedException {
		String otherImageName = "new Name";
		
		appServices.uploadImage(otherImageName, uploadedFile);
		
		appServices.updateImage(image.getUploadedImageId(), otherImageName, source);
	}
	
	@Test(expected = InstanceNotFoundException.class)
	@Ignore
	public void updateImageNotFoundTest() throws DuplicatedImageNameException, 
	InstanceNotFoundException, ImageNotResizedException {
		appServices.uploadImage("otherName", uploadedFile);
		String otherImageName = "new Name";
				
		appServices.updateImage(-1, otherImageName, source);
	}
	
	@Test
	@Ignore
	public void deleteImageTest() throws InstanceNotFoundException, 
	DuplicatedImageNameException, ImageNotResizedException {
		String otherImageName = "otherName";
		UploadedImage otherImage = appServices.uploadImage (otherImageName, uploadedFile);
		boolean deleted = false;
		
		appServices.deleteImage(image.getUploadedImageId());
		
		try {
			imageDao.find(image.getUploadedImageId());
		} catch (InstanceNotFoundException e) {
			deleted = true;
		}
		
		assertTrue (deleted);
		
		imageDao.find(otherImage.getUploadedImageId());
	}
	
	@Test(expected = InstanceNotFoundException.class)
	@Ignore
	public void deleteImageNotFoundTest() throws DuplicatedImageNameException, 
	InstanceNotFoundException, ImageNotResizedException {
		appServices.uploadImage("other name", uploadedFile);
				
		appServices.deleteImage(-1);
	}
	
	@Test
	@Ignore
	public void findImagesByNameTest() throws DuplicatedImageNameException, ImageNotResizedException {
		String imageKeyName = "test";
		int startIndex = 0;
		int count = 5;
		int totalImagesFound = 3;
		 
		UploadedImage imageFound = appServices.uploadImage(imageKeyName, uploadedFile);
		UploadedImage imageFound2 = appServices.uploadImage(imageKeyName + "aa", uploadedFile);
		UploadedImage imageFound3 = appServices.uploadImage("ll" + imageKeyName, uploadedFile);
		
		List <UploadedImage> imagesFound = appServices.
				findImagesByName(imageKeyName, startIndex, count);
		
		assertEquals (imagesFound.size(), totalImagesFound);
		assertEquals (imagesFound.get(0).getUploadedImageId(), imageFound3.getUploadedImageId());
		assertEquals (imagesFound.get(1).getUploadedImageId(), imageFound.getUploadedImageId());
		assertEquals (imagesFound.get(2).getUploadedImageId(), imageFound2.getUploadedImageId());
		
		count = 2;
		imagesFound = appServices.
				findImagesByName(imageKeyName, startIndex, count);
		
		assertEquals (imagesFound.size(), count);
		assertEquals (imagesFound.get(0).getUploadedImageId(), imageFound3.getUploadedImageId());
		assertEquals (imagesFound.get(1).getUploadedImageId(), imageFound.getUploadedImageId());

		startIndex = 2;
		count = 1;
		imagesFound = appServices.
				findImagesByName(imageKeyName, startIndex, count);
		
		assertEquals (imagesFound.size(), count);
		assertEquals (imagesFound.get(0).getUploadedImageId(), imageFound2.getUploadedImageId());
	}
	
	@Test
	@Ignore
	public void getAllImagesTest() throws DuplicatedImageNameException, ImageNotResizedException {
		String otherName = "Other";
		//int startIndex = 0;
		//int count = 5;
		int totalImagesFound = 4;
		 
		UploadedImage imageFound = appServices.uploadImage(otherName, uploadedFile);
		UploadedImage imageFound2 = appServices.uploadImage(otherName + "aa", uploadedFile);
		UploadedImage imageFound3 = appServices.uploadImage("ll" + otherName, uploadedFile);
		
		List <UploadedImage> imagesFound = appServices.getAllImages();
		
		assertEquals (imagesFound.size(), totalImagesFound);
		assertEquals (imagesFound.get(0).getUploadedImageId(), image.getUploadedImageId());
		assertEquals (imagesFound.get(1).getUploadedImageId(), imageFound3.getUploadedImageId());
		assertEquals (imagesFound.get(2).getUploadedImageId(), imageFound.getUploadedImageId());
		assertEquals (imagesFound.get(3).getUploadedImageId(), imageFound2.getUploadedImageId());
		
	}
	
	@Test
	@Ignore
	public void findUploadedImageTest() throws InstanceNotFoundException {
		UploadedImage upImage = appServices.findUploadedImage(image.getUploadedImageId());
		
		assertEquals(image.getUploadedImageId(), upImage.getUploadedImageId());
	}
	
	@Test(expected=InstanceNotFoundException.class)
	@Ignore
	public void findUploadedImageNotFoundTest() throws InstanceNotFoundException {
		appServices.findUploadedImage(-1);
	}
}
