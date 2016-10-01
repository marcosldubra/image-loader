package org.javaee.imageLoader.model.util;

public class NamesHandler {
	
	public static final String PERMITTED_FORMAT = "image";
	public static final String IMAGES_DIRECTORY = "/images/";
	
	public static String getImageFormat(String imagePath) {	
    	
    	String findDot = imagePath.substring(imagePath.length() - 4, imagePath.length());
    	String format = null;
    	
    	if (findDot.contains(".")) {
    		format = imagePath.substring(imagePath.length() - 3, imagePath.length());
		}
    	else {
    		format = imagePath.substring(imagePath.length() - 4, imagePath.length());
    	}
    	
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
    	String findDot = fileName.substring(fileName.length() - 4, fileName.length());
    	
    	if (findDot.contains(".")) {
    		return fileName.substring(0, fileName.length() - 4);
    	}
    	else {
    		return fileName.substring(0, fileName.length() - 5);
    	}
    }
}
