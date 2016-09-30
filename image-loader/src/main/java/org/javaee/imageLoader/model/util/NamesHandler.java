package org.javaee.imageLoader.model.util;

public class NamesHandler {
	
	public static final String PERMITTED_FORMAT = "image";
	public static final String IMAGES_DIRECTORY = "src/main/webapp/images/";
	
	public static String getImageFormat(String imagePath) {
    	String [] parts = imagePath.split("\\.");
    	
    	String format = (parts.length >= 2) ? parts[1] : ".jpeg";
    	
    	return format;
    }
	
	public static String addFileNameFormat(String fileName, String fileType) {
    	String[] parts;
    	String extension = null;
    	
    	if (fileType.contains("/")) {
    		parts = fileType.split("/");
    		extension = parts[1];
    	} else if (fileType.contains(".")) {
    		parts = fileType.split("\\.");
    		extension = parts[1];
    	} else {
    		return fileName;
    	}
    	    	
    	return fileName + "." + extension;
    }
    
    public static String removeFileNameFormat(String fileName) {
    	String[] parts = fileName.split("\\.");;
    	
    	return parts[0];
    }
}
