<%@ page import="com.events.common.Configuration" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.Utility" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="Description" content="Callseat.com is used to RSVP and to provide seating information for guests, by wedding planners, brides, grooms." />
    <meta name="Keywords" content="callseat, call seat, phone rsvp, email rsvp, online rsvp, smara,smarasoft,samarasoft inc smarasoft mobile,smarasoft home,smarasoft
	company,callseat,wedding seating,wedding rsvp, business seating,business rsvp,invitation rsvp,guest seating,
	seating by phone,rsvp phone,rsvp by phone,guest callseat, wedding planners, weddingplanners" />
    <meta name="author" content="Smarasoft Inc" />

    <link rel="icon"  type="image/png" href="/img/favicon.png">
    <link type="text/css" rel="stylesheet" href="/css/style.css" />
        <%
    Configuration appConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    String sProductName = com.events.common.ParseUtil.checkNull( appConfig.get("product_name")) +" " ;
	String sPageTitle = sProductName +  com.events.common.ParseUtil.checkNull(request.getParameter("page_title"));

    if(Utility.isNullOrEmpty(sPageTitle) ) {
        sPageTitle = "Callseat";
    }
%>
    <title><%=sPageTitle%></title>