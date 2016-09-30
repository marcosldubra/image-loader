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
	
	public void onValidateFromUploadForm() throws Exception{
        try {
        	String fileType = uploadedFile.getContentType();        	
        	
        	String formatedFileName = addFileNameFormat(fileName, fileType);
        	
        	File serverFile = new File(IMAGES_DIRECTORY + formatedFileName);
        	
        	if (!fileType.contains(PERMITTED_FORMAT)) {
        		uploadForm.recordError(messages.get("permittedFormat"));
        		return;
        	} 
        	
        	if(serverFile.exists()) {
        		uploadForm.recordError(messages.get("alreadyExists"));
        		return;
        	}
        	
        	uploadedFile.write(serverFile);
        	
        	try {
            	resizeImage(IMAGES_DIRECTORY + formatedFileName);
        	} catch (Exception e) {
        		uploadForm.recordError(messages.get("generalResizeException"));
        	}
        	
        	appServices.uploadImage(formatedFileName, IMAGES_DIRECTORY);
            
        } catch (DuplicatedImageNameException e) {
            uploadForm.recordError(messages.get("duplicateNameException"));
        }
    }

    public Object onUploadException(FileUploadException e) {
        uploadForm.recordError(messages.get("generalException"));
        return this;
    }
}
