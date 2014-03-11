<%@ page import="com.events.common.ParseUtil" %>
<!--[if lte IE 9]>
    <script type="text/javascript" src="/s/html5shiv.js"></script>
    <![endif]-->

    <!-- analytics stuff over here -->
    <jsp:include page="analytics.jsp"/>
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/color/modern_blue.css">
    <link rel="stylesheet" type="text/css" href="/css/messi.css">
    <link rel="stylesheet" type="text/css" href="/css/messi_styled.css">

    <%
       if( session.getAttribute("SUBDOMAIN_COLORS") != null ) {
           String vendorOverRideColorCss = (String) session.getAttribute("SUBDOMAIN_COLORS");

            %><style type="text/css"><%=ParseUtil.checkNull(vendorOverRideColorCss)%></style><!--Overriding Vendor CSS --><%
       }
    %>
</head>