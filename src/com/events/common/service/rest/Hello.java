package com.events.common.service.rest;


import com.events.common.ParseUtil;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/13/14
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */

@Path("/hello/{firstname}")
public class Hello {
    @GET
    @Produces("text/html")
    public Response hello(@PathParam("firstname") String firstname ) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username",firstname);

        List<String> l = new ArrayList<String>();
        l.add("light saber");
        l.add("fremen clothes");

        map.put("items", l);

        System.out.println("map = " + map);
        Viewable viewable = new Viewable("/index.mustache", map);
        System.out.println("viewable.isTemplateNameAbsolute()  " + ParseUtil.checkNullObject(viewable)
                + " - " + ParseUtil.checkNull(viewable.getTemplateName())
                + " - " + ParseUtil.checkNull(viewable.getModel() != null ? viewable.getModel().toString() : "No Model")
                );
        return Response.ok( viewable ).build();
        //return new Viewable("/index.template", map);
    }

    /*@GET
    @Produces("text/plain")
    public String helloFirstName(@PathParam("firstname") String firstname ) {

        return "Hello, First Name Done : " + firstname;
    }*/

}
