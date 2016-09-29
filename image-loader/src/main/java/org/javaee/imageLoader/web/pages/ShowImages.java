package org.javaee.imageLoader.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.javaee.imageLoader.model.applicationServices.ApplicationServices;
import org.javaee.imageLoader.model.uploadedImage.UploadedImage;

import java.util.List;

public class ShowImages {

	@Property
	private List <UploadedImage> allImages;
	
	@Property
	private UploadedImage image;
	
	@Inject
	ApplicationServices appServices;
	
	void setupRender() {
		
		allImages = appServices.getAllImages();
	}
}
