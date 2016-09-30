package org.javaee.imageLoader.web.components;

import java.net.URL;
import java.util.Locale;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.annotations.SetupRender;

import static org.javaee.imageLoader.model.util.NamesHandler.*;

public class ImagesRow {
	@Parameter(required = true)
	@Property
	private Long uploadedImageId;
	
	@Property
	private String imageTitle;
	
	@Parameter(required = true)
	@Property
	private String imageName;
	
	@Property
	private String imageName100;
	
	@Property
	private String imageName300;
	
	@Inject
	AssetSource assetSource;
	
	@Inject
	private Locale locale;
	
	@SetupRender
    void initializeValues (){
		imageTitle = removeFileNameFormat(imageName);
		imageName100 = removeFileNameFormat(imageName) + "100" 
				+ "." + getImageFormat(imageName);
		
		imageName300 = removeFileNameFormat(imageName) + "300" 
				+ "." + getImageFormat(imageName);
	}
}