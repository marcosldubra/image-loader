package org.javaee.imageLoader.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Layout {
	@Property
    @Parameter(required = true, defaultPrefix = "message")
    private String pageTitle;
}