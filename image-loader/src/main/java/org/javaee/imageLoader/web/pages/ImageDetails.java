package org.javaee.imageLoader.web.pages;

import java.io.File;

import static org.javaee.imageLoader.web.pages.Index.DIRECTORY;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.javaee.imageLoader.model.applicationServices.ApplicationServices;
import org.javaee.imageLoader.model.applicationServices.DuplicatedImageNameException;
import org.javaee.imageLoader.model.uploadedImage.UploadedImage;
import org.javaee.modelUtil.exceptions.InstanceNotFoundException;

public class ImageDetails {
	
	private long uploadedImageId;
	
	private UploadedImage savedImage;
	
	@Property
	private String newFileName;
	
	
	@InjectComponent
	private Form updateForm;
	
	@Inject
	private Messages messages;
	
	@Inject
	private ApplicationServices appServices;
	
	@InjectPage
	private ShowImages showImages;
		
	public void onPrepareForRender() {
		newFileName = savedImage.getImageName();
	}
	
	public Object onValidateFromUpdateForm() {
        try {
        	//String uploadedFileName = uploadedFile.getFileName();
        	
        	File oldFile = new File(DIRECTORY + savedImage.getImageName());
        	
        	File updatedFile = new File(DIRECTORY + newFileName);
        	
        	if(updatedFile.exists()) {
        		updateForm.recordError(messages.get("alreadyExists"));
        		return this;
        	}
        	
        	oldFile.renameTo(updatedFile);
        	
        	appServices.updateImage(uploadedImageId, newFileName, savedImage.getSource());
        	        	
        	return showImages;
        	
        } catch (DuplicatedImageNameException e) {
        	updateForm.recordError(messages.get("duplicateNameException"));
        	return this;
        } catch (InstanceNotFoundException e) {
        	updateForm.recordError(messages.get("notFoundException"));
        	return this;
		}
    }

    public Object onUploadException(FileUploadException e) {
        updateForm.recordError(messages.get("generalException"));
        return this;
    }
    
    void onActivate(long uploadedImageId) {
    	this.uploadedImageId = uploadedImageId;
    	
    	try {
			savedImage = appServices.findUploadedImage(this.uploadedImageId);
		} catch (InstanceNotFoundException e) {
			savedImage = null;
		}
    }
    
    long onPassivate() {
    	return this.uploadedImageId;
    }

	public long getUploadedImageId() {
		return uploadedImageId;
	}

	public void setUploadedImageId(long uploadedImageId) {
		this.uploadedImageId = uploadedImageId;
	}
}
