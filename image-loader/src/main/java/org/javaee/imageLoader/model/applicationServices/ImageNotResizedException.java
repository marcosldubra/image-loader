package org.javaee.imageLoader.model.applicationServices;

@SuppressWarnings("serial")
public class ImageNotResizedException extends Exception {

	private String name;
	
	public ImageNotResizedException(String name) {
		super ("The image " + name + " cannot been resized");
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
