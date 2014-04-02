<%@ page import="com.events.common.ParseUtil" %>



    <!-- analytics stuff over here -->
    <jsp:include page="analytics.jsp"/>
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/messi.css">
    <link rel="stylesheet" type="text/css" href="/css/messi_styled.css">
    <link rel="stylesheet" type="text/css" href="/css/color/modern_blue.css">

    <%
       if( session.getAttribute("SUBDOMAIN_COLORS") != null ) {
           String vendorOverRideColorCss = (String) session.getAttribute("SUBDOMAIN_COLORS");

            %><style type="text/css"><%=ParseUtil.checkNull(vendorOverRideColorCss)%></style><!--Overriding Vendor CSS --><%
       }
    %>
    <script type="text/javascript" src="/js/modernizr.custom.js"></script>

    <link rel="icon"  type="image/png" href="/img/favicon.png">
</head>