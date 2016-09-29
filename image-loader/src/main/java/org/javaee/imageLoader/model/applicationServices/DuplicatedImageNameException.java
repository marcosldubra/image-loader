package org.javaee.imageLoader.model.applicationServices;

@SuppressWarnings("serial")
public class DuplicatedImageNameException extends Exception {

	private String name;
	
	public DuplicatedImageNameException(String name) {
		super ("The name " + name + " is already in use, please choose other.");
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
