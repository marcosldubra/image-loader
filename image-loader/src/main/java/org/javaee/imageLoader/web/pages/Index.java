package org.javaee.imageLoader.web.pages;

import java.io.File;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.javaee.imageLoader.model.applicationServices.ApplicationServices;
import org.javaee.imageLoader.model.applicationServices.DuplicatedImageNameException;
import org.javaee.imageLoader.model.applicationServices.ImageNotResizedException;

import static  org.javaee.imageLoader.model.util.ImageResizer.resizeImage;
import static org.javaee.imageLoader.model.util.NamesHandler.*;



public class Index {
	
	@Property
	private String fileName;
	
	@Property
	private UploadedFile uploadedFile;
	
	@InjectComponent
	private Form uploadForm;
	
	@Inject
	private Messages messages;
	
	@Inject
	ApplicationServices appServices;
	
	@InjectPage
	private ShowImages filesList;
	
	public void onValidateFromUploadForm() {
		
		String fileType = uploadedFile.getContentType();        	
    	
    	String formatedFileName = addFileNameFormat(fileName, fileType);
    	    	
    	if (!fileType.contains(PERMITTED_FORMAT)) {
    		uploadForm.recordError(messages.get("permittedFormat"));
    		return;
    	} 
		
    	try {
    		appServices.uploadImage(formatedFileName, uploadedFile);
    	} catch (DuplicatedImageNameException e) {
    		uploadForm.recordError(messages.get("duplicateNameException"));
    		return;
    	} catch (ImageNotResizedException e2) {
    		uploadForm.recordError(messages.get("generalResizeException"));
    	}
    }

    public Object onUploadException(FileUploadException e) {
        uploadForm.recordError(messages.get("generalException"));
        return this;
    }
}
