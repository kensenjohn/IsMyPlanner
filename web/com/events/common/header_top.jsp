<%@ page import="com.events.common.Configuration" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.Utility" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="Description" content="An event planners platform to manage clients, events, vendors and team" />
    <meta name="Keywords" content=" event planner, eventplanner,  wedding planners, weddingplanners, phone rsvp, email rsvp, online rsvp, smara,smarasoft,samarasoft inc smarasoft mobile,smarasoft home,smarasoft
	company,,wedding seating,wedding rsvp, business seating,business rsvp,invitation rsvp,guest seating,
	seating by phone,rsvp phone,rsvp by phone,guest," />
    <meta name="author" content="Smarasoft Inc" />

    <meta property="og:image" content="/img/logo.png"/>
    <meta property="og:title" content="An Event Planner's Marketing and Management Platform "/>
    <meta property="og:site_name" content="This Is My Planner"/>

        <%
    Configuration appConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    String sProductName = com.events.common.ParseUtil.checkNull( appConfig.get("product_name")) +" " ;
	String sPageTitle = sProductName +  com.events.common.ParseUtil.checkNull(request.getParameter("page_title"));

    if(Utility.isNullOrEmpty(sPageTitle) ) {
        sPageTitle = "ismylanner.com";
    }
%>
    <title><%=sPageTitle%></title>