package org.javaee.imageLoader.model.applicationServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.javaee.modelUtil.exceptions.InstanceNotFoundException;

import java.util.Calendar;
import java.util.List;

import org.javaee.imageLoader.model.modification.Modification;
import org.javaee.imageLoader.model.modification.ModificationDao;
import org.javaee.imageLoader.model.modification.Modification.ModificationType;
import org.javaee.imageLoader.model.uploadedImage.UploadedImage;
import org.javaee.imageLoader.model.uploadedImage.UploadedImageDao;

@Service("applicationServices")
@Transactional
public class ApplicationServicesImplementation implements ApplicationServices {

	@Autowired
	UploadedImageDao uploadedImageDao;
	
	@Autowired
	ModificationDao modificationDao;
	
	public UploadedImage uploadImage(String imageName, String source) throws DuplicatedImageNameException {
		try {
			uploadedImageDao.getImageByName(imageName);
			throw new DuplicatedImageNameException(imageName);
		} catch (InstanceNotFoundException e) {
			UploadedImage image = new UploadedImage (imageName, source);
			
			uploadedImageDao.save(image);
			
			return image;
		}
	}

	// TODO: refactor the if condition -> Not throw InstanceNotFound
	public UploadedImage updateImage(long imageId, String newImageName, String newSource) 
		throws DuplicatedImageNameException, InstanceNotFoundException {
		UploadedImage image = null;
		
		try {
			image = uploadedImageDao.getImageByName(newImageName);
			
			if (image.getUploadedImageId() != imageId) {
				throw new DuplicatedImageNameException(newImageName);
			} else {
				throw new InstanceNotFoundException(UploadedImage.class.getName(), newImageName);
			}
			
		} catch (InstanceNotFoundException e) {
			String currentImageName = null;
			String currentImageSource = null;
			String modificationDescription = null;
			
			image = uploadedImageDao.find(imageId);
			currentImageName = image.getImageName();
			currentImageSource = image.getSource();
			
			if (currentImageName != newImageName) {
				image.setImageName(newImageName);

				modificationDescription = currentImageName + " -> " + newImageName;
				createModification(modificationDescription, ModificationType.NAME, image);
			}

			if (currentImageSource != newSource) {
				image.setSource(newSource);

				modificationDescription = currentImageSource + " -> " + newSource;
				createModification(modificationDescription, ModificationType.SOURCE, image);
			}
			
			uploadedImageDao.save(image);
			
			return image;
		}
	}
	
	public void deleteImage(long imageId)
		throws InstanceNotFoundException {
		
		uploadedImageDao.remove(imageId);		
	}
	
	@Transactional(readOnly = true)
	public List <UploadedImage> findImagesByName(String name, int startIndex, int count) {
		
		return uploadedImageDao.findImagesByName(name, startIndex, count);
	}
	
	@Transactional(readOnly = true)
	public List<UploadedImage> getAllImages() {
		return uploadedImageDao.getAllImages();
	}

	
	private Modification createModification(String description, ModificationType type, UploadedImage image) {
		Calendar modificationDate = Calendar.getInstance();
		
		Modification modification = new Modification (description, modificationDate,
			type, image);
		
		modificationDao.save(modification);
		
		return modification;
	}

	@Override
	public UploadedImage findUploadedImage(long uploadedImageId) throws InstanceNotFoundException {
		return uploadedImageDao.find(uploadedImageId);
	}
}
