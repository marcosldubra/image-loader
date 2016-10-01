package org.javaee.imageLoader.model.applicationServices;

import org.javaee.imageLoader.model.uploadedImage.UploadedImage;
import org.javaee.modelUtil.exceptions.InstanceNotFoundException;

import java.util.List;

import org.apache.tapestry5.upload.services.UploadedFile;

public interface ApplicationServices {

	public UploadedImage uploadImage (String imageName, UploadedFile uploadFile)
		throws DuplicatedImageNameException, ImageNotResizedException;
	
	public UploadedImage updateImage(long imageId, String newImageName, String source)
		throws DuplicatedImageNameException, InstanceNotFoundException;
	
	public void deleteImage(long imageId)
		throws InstanceNotFoundException;
	
	public List <UploadedImage> findImagesByName(String name, int startIndex, int count);

	public List<UploadedImage> getAllImages();

	public UploadedImage findUploadedImage(long uploadedImageId) throws InstanceNotFoundException;
	
}
