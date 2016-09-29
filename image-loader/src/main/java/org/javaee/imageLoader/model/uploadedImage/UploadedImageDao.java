package org.javaee.imageLoader.model.uploadedImage;

import java.util.List;

import org.javaee.modelUtil.dao.GenericDao;
import org.javaee.modelUtil.exceptions.InstanceNotFoundException;

public interface UploadedImageDao extends GenericDao<UploadedImage, Long>{

	public UploadedImage getImageByName(String name) 
		throws InstanceNotFoundException;
	
	public List<UploadedImage> findImagesByName(String name, int startIndex, int count);
	
	public int getNumberOfFindImagesByName(String name);

	public List<UploadedImage> getAllImages();
}
