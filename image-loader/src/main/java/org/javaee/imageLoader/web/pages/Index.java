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

public class Index {
	private final String PERMITTED_TYPE = "image";
	public static final String DIRECTORY = "src/main/webapp/images/";
	
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
        try {
        	//String uploadedFileName = uploadedFile.getFileName();
        	String fileType = uploadedFile.getContentType();        	
        	
        	File serverFile = new File(DIRECTORY + fileName);
        	
        	if (!fileType.contains(PERMITTED_TYPE)) {
        		uploadForm.recordError(messages.get("permittedType"));
        		return;
        	} 
        	
        	if(serverFile.exists()) {
        		uploadForm.recordError(messages.get("alreadyExists"));
        		return;
        	}
        	
        	uploadedFile.write(serverFile);
        	
        	appServices.uploadImage(fileName, DIRECTORY);
            
        } catch (DuplicatedImageNameException e) {
            uploadForm.recordError(messages.get("duplicateNameException"));
        }
    }

    public Object onUploadException(FileUploadException e) {
        uploadForm.recordError(messages.get("generalException"));
        return this;
    }
}
