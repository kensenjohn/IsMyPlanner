package com.events.common.service.rest;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/14/14
 * Time: 7:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyApplication  extends ResourceConfig {
    /*
    public MyApplication() {
        // Resources.
        packages(FormResource.class.getPackage().getName());

        // Providers.
        register(MustacheMvcFeature.class);
        register(JacksonFeature.class);

        // Properties.
        property(MustacheMvcFeature.TEMPLATE_BASE_PATH, "/mustache");
    }
     */
    public MyApplication() {
        packages(Hello.class.getPackage().getName());

        // MVC.
        register(MustacheMvcFeature.class);
        // Properties.
        property(MustacheMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp");

        System.out.println("MyApplication.MyApplication");
    }
}
