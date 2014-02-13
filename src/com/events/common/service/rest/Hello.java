package com.events.common.service.rest;

import com.sun.jersey.api.view.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/13/14
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */

@Path("/hello")
public class Hello {
    @GET
    @Produces("text/plain")
    public String hello() {

        return "Hello, World";
    }

}
