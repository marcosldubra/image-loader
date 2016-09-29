package org.javaee.imageLoader.web.components;

import java.net.URL;
import java.util.Locale;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

public class ImagesRow {
	@Parameter(required = true)
	@Property
	private String imageName;
	
	@Parameter(required = true)
	@Property
	private Long uploadedImageId;
	
	@Inject
	AssetSource assetSource;
	
	@Inject
	private Locale locale;
	
	/*public String getImageUrl () {
		URL assetUrl = assetSource.getContextAsset("/" + imageName, locale).toURL();
		assetSource.getContextAsset("", locale).getResource().getPath();
		assetSource.getContextAsset("", locale).
	    return assetUrl.toString();
	}*/
}