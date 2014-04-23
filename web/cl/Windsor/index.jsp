<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.event.website.*" %>
<%@ page import="com.events.event.website.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.events.bean.event.EventRequestBean" %>
<%@ page import="com.events.bean.common.FeatureBean" %>
<%@ page import="com.events.common.feature.FeatureType" %>
<%@ page import="com.events.common.feature.Feature" %>
<%@ page import="com.events.bean.upload.UploadBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    String sEventId = ParseUtil.checkNull( request.getParameter("ewi"));
    WebsiteThemeBean websiteThemeBean = new WebsiteThemeBean();
    WebsiteColorBean websiteColorBean = new WebsiteColorBean();
    WebsiteFontBean websiteFontBean = new WebsiteFontBean();
    HashMap<Constants.EVENT_WEBSITE_PAGETYPE , EventWebsitePageBean > hmEventWebsitePage = new HashMap<Constants.EVENT_WEBSITE_PAGETYPE, EventWebsitePageBean>();
    if(!Utility.isNullOrEmpty(sEventId)) {
        EventWebsiteRequestBean eventWebsiteRequestBean = new EventWebsiteRequestBean();
        eventWebsiteRequestBean.setEventId(sEventId);

        AccessEventWebsite accessEventWebsite = new AccessEventWebsite();
        EventWebsiteBean eventWebsiteBean = accessEventWebsite.getEventWebsite(eventWebsiteRequestBean);

        if(eventWebsiteBean!=null && !Utility.isNullOrEmpty(eventWebsiteBean.getEventWebsiteId())) {
            AllWebsiteThemeRequestBean allWebsiteThemeRequestBean = new AllWebsiteThemeRequestBean();
            allWebsiteThemeRequestBean.setWebsiteThemeId(  eventWebsiteBean.getWebsiteThemeId() );

            AccessWebsiteThemes accessWebsiteThemes = new AccessWebsiteThemes();
            websiteThemeBean =  accessWebsiteThemes.getWebsiteTheme(allWebsiteThemeRequestBean);
            if(websiteThemeBean == null ){
                websiteThemeBean = new WebsiteThemeBean();
            }

            eventWebsiteRequestBean.setWebsiteColorId( eventWebsiteBean.getWebsiteColorId() );
            AccessWebsiteColor accessWebsiteColor = new AccessWebsiteColor();
            websiteColorBean = accessWebsiteColor.getWebsiteColor( eventWebsiteRequestBean );

            eventWebsiteRequestBean.setWebsiteFontId( eventWebsiteBean.getWebsiteFontId() );
            AccessWebsiteFont accessWebsiteFont = new AccessWebsiteFont();
            websiteFontBean = accessWebsiteFont.getWebsiteFont(eventWebsiteRequestBean);

            AccessEventWebsitePage accessEventWebsitePage = new AccessEventWebsitePage();
            hmEventWebsitePage = accessEventWebsitePage.getHashEventWebsitePage(eventWebsiteBean) ;
        }
    }

    final String sThemeName = ParseUtil.checkNull(websiteThemeBean.getName());
    final String sColorCssName = ParseUtil.checkNull(websiteColorBean.getColorCssName());
    final String sFontName = ParseUtil.checkNull(websiteFontBean.getFontCssName() );

    EventWebsitePageBean welcomeEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.welcome);
    if(welcomeEventWebsitePageBean == null ){
        welcomeEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean invitationEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.invitation);
    if(invitationEventWebsitePageBean == null ){
        invitationEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean couplesEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.couples);
    if(couplesEventWebsitePageBean == null ){
        couplesEventWebsitePageBean = new EventWebsitePageBean();
    }
    EventWebsitePageBean bridesmaidEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.bridesmaids);
    if(bridesmaidEventWebsitePageBean == null ){
        bridesmaidEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean groomsmenEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.groomsmen);
    if(groomsmenEventWebsitePageBean == null ){
        groomsmenEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean ceremonyEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.ceremony);
    if(ceremonyEventWebsitePageBean == null ){
        ceremonyEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean receptionEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.reception);
    if(receptionEventWebsitePageBean == null ){
        receptionEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean travelEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.travel);
    if(travelEventWebsitePageBean == null ){
        travelEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean hotelsEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.hotels);
    if(hotelsEventWebsitePageBean == null ){
        hotelsEventWebsitePageBean = new EventWebsitePageBean();
    }


    EventWebsitePageBean registryEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.registry);
    if(registryEventWebsitePageBean == null ){
        registryEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean rsvpEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.rsvp);
    if(rsvpEventWebsitePageBean == null ){
        rsvpEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean contactUsEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.contactus);
    if(contactUsEventWebsitePageBean == null ){
        contactUsEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventRequestBean eventRequestBean = new EventRequestBean();
    eventRequestBean.setEventId(sEventId);

    FeatureBean featureBean = new FeatureBean();
    featureBean.setEventId(sEventId );
    featureBean.setFeatureType(FeatureType.image_location);

    Feature feature = new Feature();
    featureBean = feature.getFeature(featureBean);
    final String imageFolder = featureBean.getValue();
    final String imageHost =  Utility.getImageUploadHost();
    final String bucket =  Utility.getS3Bucket();

    final String sImagePath = imageHost + "/" + bucket + "/" + imageFolder;

    EventWebsitePageFeature eventWebsitePageFeature = new EventWebsitePageFeature();
    String sCeremonyAddress = Constants.EMPTY;
    String sReceptionAddress = Constants.EMPTY;

    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( welcomeEventWebsitePageBean.getEventWebsitePageId() );
    EventWebsitePageFeatureBean bannerImageFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.banner_image_name );
    EventWebsitePageFeatureBean captionTitleFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.caption_title );
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="Description" content="Callseat.com is used to RSVP and to provide seating information for guests, by wedding planners, brides, grooms." />
    <meta name="author" content="Smarasoft Inc" />
    <link rel="icon"  type="image/png" href="/img/favicon.png">

    <title>Wedding Website</title>

    <!--[if lte IE 9]>
    <script type="text/javascript" src="/js/modernizr.custom.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/color/modern_blue.css">
    <link rel="stylesheet" type="text/css" href="/css/messi.css">
    <link rel="stylesheet" type="text/css" href="/css/messi_styled.css">>
    <link rel="stylesheet" type="text/css" href="/css/flexslider.css">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/cl/<%=sThemeName%>/css/windsor_style.css">
    <link rel="stylesheet" type="text/css" href="/cl/<%=sThemeName%>/css/windsor_font_<%=sFontName%>">
    <link rel="stylesheet" type="text/css" href="/cl/<%=sThemeName%>/css/windsor_color_<%=sColorCssName%>">
</head>
<body>
<div class="page_wrap" >
    <div class="container header_container">
        <div class="content_format">
            <header id="header">
                <nav class="navbar navbar-default" role="navigation">
                    <div class="container-fluid">
                        <!-- Brand and toggle get grouped for better mobile display -->
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="#"><%=ParseUtil.checkNull(captionTitleFeature.getValue())%></a>
                        </div>

                        <!-- Collect the nav links, forms, and other content for toggling -->
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav  navbar-right">
                                <%
                                    if(welcomeEventWebsitePageBean.isShow() || invitationEventWebsitePageBean.isShow() ) {
                                %>
                                <li class="dropdown">
                                    <a href="#welcome" class="dropdown-toggle" data-toggle="dropdown">Welcome<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <% if(welcomeEventWebsitePageBean.isShow()) {%><li><a href="#welcome">Welcome</a></li><%}%>
                                        <% if(invitationEventWebsitePageBean.isShow()) {%><li><a href="#invitation">Invitation</a></li><%}%>
                                    </ul>
                                </li>
                                <%
                                    }
                                %>
                                <%
                                    if(couplesEventWebsitePageBean.isShow() || bridesmaidEventWebsitePageBean.isShow() || groomsmenEventWebsitePageBean.isShow() ) {
                                %>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">About<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <% if(couplesEventWebsitePageBean.isShow()) {%><li><a href="#about_the_couple">The Couple</a></li><%}%>
                                        <% if(bridesmaidEventWebsitePageBean.isShow()) {%><li><a href="#bridesmaid">Bride's Maids</a></li><%}%>
                                        <% if(groomsmenEventWebsitePageBean.isShow()) {%><li><a href="#groomsmen">Groom's Men</a></li><%}%>
                                    </ul>
                                </li>
                                <%
                                    }
                                %>
                                <%
                                    if(ceremonyEventWebsitePageBean.isShow() || receptionEventWebsitePageBean.isShow()) {
                                %>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">The Event<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <% if(ceremonyEventWebsitePageBean.isShow()) {%><li><a href="#ceremony">Ceremony</a></li><%}%>
                                        <% if(receptionEventWebsitePageBean.isShow()) {%><li><a href="#reception">Reception</a></li><%}%>
                                    </ul>
                                </li>
                                <%
                                    }
                                %>
                                <%
                                    if(travelEventWebsitePageBean.isShow() || hotelsEventWebsitePageBean.isShow()) {
                                %>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Guest Lobby<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <% if(travelEventWebsitePageBean.isShow()) {%><li><a href="#travel">Travel</a></li><%}%>
                                        <% if(hotelsEventWebsitePageBean.isShow()) {%><li><a href="#hotels">Hotels</a></li><%}%>
                                    </ul>
                                </li>
                                <%
                                    }
                                %>
                                <% if(registryEventWebsitePageBean.isShow()) {%><li><li><a href="#registry">Registry</a></li><%}%>
                                <% if(rsvpEventWebsitePageBean.isShow()) {%><li><li><a href="#rsvp">RSVP</a></li><%}%>
                                <% if(contactUsEventWebsitePageBean.isShow()) {%><li><li><a href="#contact_us">Contact Us</a></li><%}%>
                            </ul>
                        </div><!-- /.navbar-collapse -->
                    </div><!-- /.container-fluid -->
                </nav>
            </header>
        </div>
    </div>
    <%
        if(welcomeEventWebsitePageBean.isShow()) {

            EventWebsitePageFeatureBean captionTagLineFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.caption_tag_line );

    %>
    <div class="container">
        <div class="content_format" style="padding-bottom:30px">
            <div class="row" style="padding-top:450px;padding-bottom:350px;">
                <% if(  (captionTitleFeature!=null && !Utility.isNullOrEmpty(captionTitleFeature.getValue())) ||
                        (captionTagLineFeature!=null && !Utility.isNullOrEmpty(captionTagLineFeature.getValue()))   ) { %>
                    <div class="col-xs-12" style="	background: rgba(0, 0, 0, 0.3);	-ms-filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#4C000000, endColorstr=#4C000000);	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#4C000000, endColorstr=#4C000000);	color: #ffffff;">
                        <% if(captionTitleFeature!=null) { %>
                        <h1> <div style="text-align: center;font-size: 85px;color: #ffffff;"><%=ParseUtil.checkNull(captionTitleFeature.getValue())%></div></h1>
                        <%} %>
                        <%if(captionTagLineFeature!=null) { %>
                        <h5> <div style="text-align: center;font-size: 60px;color: #ffffff;"><%=ParseUtil.checkNull(captionTagLineFeature.getValue())%></div> </h5>
                        <%} %>
                    </div>
                <%} %>
            </div>
        </div>
    </div>
    <!-- Welcome Ends here -->
    <%
        }
    %>
    <div class="container">
        <div class="content_format" >
            <% if(invitationEventWebsitePageBean.isShow()) {
                HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmInvitationMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( invitationEventWebsitePageBean.getEventWebsitePageId() );
                EventWebsitePageFeatureBean inviationNameFeature = hmInvitationMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_name );
                EventWebsitePageFeatureBean inviationTextFeature = hmInvitationMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_text );
                EventWebsitePageFeatureBean inviationDateFeature = hmInvitationMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_date );
                EventWebsitePageFeatureBean inviationLocationFeature = hmInvitationMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_location_name );
                EventWebsitePageFeatureBean inviationAddressFeature = hmInvitationMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_address );
            %>
                <div class="invitation-block"  style="background-color: #FFFFFF	">
                    <% if(inviationNameFeature!=null && !Utility.isNullOrEmpty(inviationNameFeature.getValue())) { %>
                    <div class="row">
                        <div class="col-md-12">
                            <h1><div style="text-align: center"><%=inviationNameFeature.getValue()%></div></h1>
                        </div>
                    </div>
                    <%}%>
                    <% if(inviationTextFeature!=null && !Utility.isNullOrEmpty(inviationTextFeature.getValue())) { %>
                    <div class="row">
                        <div class="col-md-12">
                            <div style="text-align: center"><p><%=inviationTextFeature.getValue()%></p></div>
                        </div>
                    </div>
                    <%}%>
                    <% if(inviationDateFeature!=null && !Utility.isNullOrEmpty(inviationDateFeature.getValue())) { %>
                    <div class="row">
                        <div class="col-md-12">
                            <h2><div style="text-align: center"><%=inviationDateFeature.getValue()%></div></h2>
                        </div>
                    </div>
                    <%}%>
                    <% if(inviationLocationFeature!=null && !Utility.isNullOrEmpty(inviationLocationFeature.getValue())) { %>
                    <div class="row">
                        <div class="col-md-12">
                            <div style="text-align: center"><p>at</p></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <h2><div style="text-align: center"><%=inviationLocationFeature.getValue()%></div></h2>
                        </div>
                    </div>
                    <%}%>
                    <% if(inviationAddressFeature!=null && !Utility.isNullOrEmpty(inviationAddressFeature.getValue())) { %>
                    <div class="row">
                        <div class="col-md-12">
                            <div style="text-align: center"><p><%=inviationAddressFeature.getValue()%></p></div>
                        </div>
                    </div>
                    <%}%>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        &nbsp;
                    </div>
                </div>
                <div class="row"  style="padding-top: 30px">
                    <div class="col-md-12">
                        &nbsp;
                    </div>
                </div>
            <!-- Invitation Ends here -->
            <%}%>
            <% if(couplesEventWebsitePageBean.isShow() ) {

                EventPartyRequest eventPartyPartner1Request = new EventPartyRequest();
                eventPartyPartner1Request.setEventWebsiteId(couplesEventWebsitePageBean.getEventWebsiteId());
                eventPartyPartner1Request.setEventPartyType(Constants.EVENT_PARTY_TYPE.BRIDE);

                AccessEventParty accessEventParty = new AccessEventParty();
                EventPartyBean eventPartyPartner1Bean = accessEventParty.getEventPartyByTypeAndWebsite(eventPartyPartner1Request);

                String sPartner1Name = Constants.EMPTY;
                String sPartner1Description = Constants.EMPTY;
                String sPartner1Image = Constants.EMPTY;
                String sFaceBookPartner1 = Constants.EMPTY;
                String sTwitterPartner1 = Constants.EMPTY;
                String sPinterestPartner1 = Constants.EMPTY;

                if(eventPartyPartner1Bean!=null && !Utility.isNullOrEmpty(eventPartyPartner1Bean.getEventPartyId())) {
                    sPartner1Name = ParseUtil.checkNull(eventPartyPartner1Bean.getName());
                    sPartner1Description = ParseUtil.checkNull(eventPartyPartner1Bean.getDescription());

                    UploadBean uploadBean = accessEventParty.getEventPartyImage( eventPartyPartner1Bean );


                    if(uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId())){
                        sPartner1Image = sImagePath + "/" + uploadBean.getFilename();
                    }

                    AccessSocialMedia accessSocialMedia = new AccessSocialMedia();
                    eventPartyPartner1Request.setEventPartyId( eventPartyPartner1Bean.getEventPartyId());
                    HashMap<Constants.SOCIAL_MEDIA_TYPE, SocialMediaBean> hashPartner1SocialMediaBean =  accessSocialMedia.getHashSocialMedia(eventPartyPartner1Request);


                    if(hashPartner1SocialMediaBean!=null && !hashPartner1SocialMediaBean.isEmpty()) {
                        SocialMediaBean facebookSocialMedia = hashPartner1SocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.FACEBOOK);
                        if(facebookSocialMedia!=null ) {
                            sFaceBookPartner1 = ParseUtil.checkNull(facebookSocialMedia.getUrl());
                        }

                        SocialMediaBean twitterSocialMedia = hashPartner1SocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.TWITTER);
                        if(twitterSocialMedia!=null ) {
                            sTwitterPartner1 = ParseUtil.checkNull(twitterSocialMedia.getUrl());
                        }


                        SocialMediaBean pinteresSocialMedia = hashPartner1SocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.PINTEREST);
                        if(pinteresSocialMedia!=null ) {
                            sPinterestPartner1 = ParseUtil.checkNull(pinteresSocialMedia.getUrl());
                        }
                    }
                }
            %>
            <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px">
                <div class="col-md-12">
                    <div class="row" id="about_the_couple">
                        <div class="col-md-offset-1  col-md-11">
                            <div class="row">
                                <div class="col-md-12  page-title">
                                    <h1>The Couple</h1>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-offset-1 col-md-4"  style="text-align: center">
                            <%if(!Utility.isNullOrEmpty(sPartner1Image)) { %>
                                <div class="row">
                                    <div class="col-md-12">
                                        <img src="<%=sPartner1Image%>" alt="Bride" class="img-circle">
                                    </div>
                                </div>
                            <%}%>
                            <%if(!Utility.isNullOrEmpty(sPartner1Name)) { %>
                                <div class="row">
                                    <div class="col-md-12">
                                        <h4><%=sPartner1Name%></h4>
                                    </div>
                                </div>
                            <%}%>
                            <div class="row">
                                <div class="col-md-12">
                                    <h5>
                                        <%if(!Utility.isNullOrEmpty(sFaceBookPartner1)) { %>
                                        <a  href="<%=sFaceBookPartner1%>" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                                        <%}%>
                                        <%if(!Utility.isNullOrEmpty(sTwitterPartner1)) { %>
                                        <a  href="<%=sTwitterPartner1%>" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                                        <%}%>
                                        <%if(!Utility.isNullOrEmpty(sPinterestPartner1)) { %>
                                        <a  href="<%=sPinterestPartner1%>" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                                        <%}%>
                                    </h5>
                                </div>
                            </div>
                            <%if(!Utility.isNullOrEmpty(sPartner1Description)) { %>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p style="text-align: justify">
                                            <%=sPartner1Description%>
                                        </p>
                                    </div>
                                </div>
                            <%}%>
                        </div>
                        <%
                            EventPartyRequest eventPartyPartner2Request = new EventPartyRequest();
                            eventPartyPartner2Request.setEventWebsiteId(couplesEventWebsitePageBean.getEventWebsiteId());
                            eventPartyPartner2Request.setEventPartyType(Constants.EVENT_PARTY_TYPE.GROOM);

                            EventPartyBean eventPartyPartner2Bean = accessEventParty.getEventPartyByTypeAndWebsite(eventPartyPartner2Request);

                            String sPartner2Name = Constants.EMPTY;
                            String sPartner2Description = Constants.EMPTY;
                            String sPartner2Image = Constants.EMPTY;
                            String sFaceBookPartner2 = Constants.EMPTY;
                            String sTwitterPartner2 = Constants.EMPTY;
                            String sPinterestPartner2 = Constants.EMPTY;

                            if(eventPartyPartner2Bean!=null && !Utility.isNullOrEmpty(eventPartyPartner2Bean.getEventPartyId())) {
                                sPartner2Name = ParseUtil.checkNull(eventPartyPartner2Bean.getName());
                                sPartner2Description = ParseUtil.checkNull(eventPartyPartner2Bean.getDescription());

                                UploadBean uploadBean = accessEventParty.getEventPartyImage( eventPartyPartner2Bean );


                                if(uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId())){
                                    sPartner2Image = sImagePath + "/" + uploadBean.getFilename();
                                }

                                AccessSocialMedia accessSocialMedia = new AccessSocialMedia();
                                eventPartyPartner2Request.setEventPartyId( eventPartyPartner2Bean.getEventPartyId());
                                HashMap<Constants.SOCIAL_MEDIA_TYPE, SocialMediaBean> hashPartner2SocialMediaBean =  accessSocialMedia.getHashSocialMedia(eventPartyPartner2Request);


                                if(hashPartner2SocialMediaBean!=null && !hashPartner2SocialMediaBean.isEmpty()) {
                                    SocialMediaBean facebookSocialMedia = hashPartner2SocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.FACEBOOK);
                                    if(facebookSocialMedia!=null ) {
                                        sFaceBookPartner2 = ParseUtil.checkNull(facebookSocialMedia.getUrl());
                                    }

                                    SocialMediaBean twitterSocialMedia = hashPartner2SocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.TWITTER);
                                    if(twitterSocialMedia!=null ) {
                                        sTwitterPartner2 = ParseUtil.checkNull(twitterSocialMedia.getUrl());
                                    }


                                    SocialMediaBean pinteresSocialMedia = hashPartner2SocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.PINTEREST);
                                    if(pinteresSocialMedia!=null ) {
                                        sPinterestPartner2 = ParseUtil.checkNull(pinteresSocialMedia.getUrl());
                                    }
                                }
                            }

                        %>
                        <div class="col-md-offset-1 col-md-4"   style="text-align: center">
                            <%if(!Utility.isNullOrEmpty(sPartner2Image)) { %>
                                <div class="row">
                                    <div class="col-md-12">
                                        <img src="<%=sPartner2Image%>" alt="Groom" class="img-circle">
                                    </div>
                                </div>
                            <%}%>
                            <%if(!Utility.isNullOrEmpty(sPartner2Name)) { %>
                                <div class="row">
                                    <div class="col-md-12">
                                        <h4><%=sPartner2Name%></h4>
                                    </div>
                                </div>
                            <%}%>
                            <div class="row">
                                <div class="col-md-12">
                                    <h6>
                                        <%if(!Utility.isNullOrEmpty(sFaceBookPartner2)) { %>
                                        <a  href="<%=sFaceBookPartner2%>" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                                        <%}%>
                                        <%if(!Utility.isNullOrEmpty(sTwitterPartner2)) { %>
                                        <a  href="<%=sTwitterPartner2%>" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                                        <%}%>
                                        <%if(!Utility.isNullOrEmpty(sPinterestPartner2)) { %>
                                        <a  href="<%=sPinterestPartner2%>" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                                        <%}%>
                                    </h6>
                                </div>
                            </div>
                            <%if(!Utility.isNullOrEmpty(sPartner2Description)) { %>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p style="text-align: justify">
                                            <%=sPartner2Description%>
                                        </p>
                                    </div>
                                </div>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row"  style="padding-top: 30px">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>

            <!-- Couples Ends here -->
            <%}%>

            <% if(bridesmaidEventWebsitePageBean.isShow() ) {
                ArrayList<Constants.EVENT_PARTY_TYPE> arrEventPartyType = new ArrayList<Constants.EVENT_PARTY_TYPE>();
                arrEventPartyType.add(Constants.EVENT_PARTY_TYPE.BRIDESMAID);
                arrEventPartyType.add(Constants.EVENT_PARTY_TYPE.MAIDOFHONOR);


                EventPartyRequest eventPartyBridesMaidRequest = new EventPartyRequest();
                eventPartyBridesMaidRequest.setEventWebsiteId(couplesEventWebsitePageBean.getEventWebsiteId());
                eventPartyBridesMaidRequest.setArrEventPartyType( arrEventPartyType );

                AccessEventParty accessEventParty = new AccessEventParty();
                ArrayList<EventPartyBean> arrEventPartyBean =  accessEventParty.getEventPartyListByTypeAndWebsite(eventPartyBridesMaidRequest);

            %>
            <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
                <div class="col-md-12">
                    <div class="row" id="bridesmaid">
                        <div class="col-xs-11 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-12  page-title">
                                    <h1>Bride's Maids</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%

                        if(arrEventPartyBean!=null && !arrEventPartyBean.isEmpty() ) {

                            HashMap<String,UploadBean> hmUploadBean = accessEventParty.getEventPartyImage( arrEventPartyBean );

                            Integer iColumnCount = 0;
                            for(EventPartyBean eventPartyBean : arrEventPartyBean) {

                                String sBridesMaidName = ParseUtil.checkNull(eventPartyBean.getName());
                                String sBridesMaidDescription = ParseUtil.checkNull(eventPartyBean.getDescription());

                                String sBridesMaidImage = Constants.EMPTY;
                                UploadBean uploadBean = hmUploadBean.get( eventPartyBean.getEventPartyId() );
                                if(uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId())){
                                    sBridesMaidImage = sImagePath + "/" + uploadBean.getFilename();
                                }

                                EventPartyRequest eventPartyRequest = new EventPartyRequest();
                                eventPartyRequest.setEventPartyId(eventPartyBean.getEventPartyId() );
                                eventPartyRequest.setEventWebsiteId( eventPartyBean.getEventWebsiteId() );
                                eventPartyRequest.setEventPartyType( eventPartyBean.getEventPartyType() );
                                AccessSocialMedia accessSocialMedia = new AccessSocialMedia();
                                HashMap<Constants.SOCIAL_MEDIA_TYPE, SocialMediaBean> hashBridesMaidSocialMediaBean =  accessSocialMedia.getHashSocialMedia(eventPartyRequest);


                                String sFaceBookBridesMaid = Constants.EMPTY;
                                String sTwitterBridesMaid = Constants.EMPTY;
                                String sPinterestBridesMaid = Constants.EMPTY;
                                if(hashBridesMaidSocialMediaBean!=null && !hashBridesMaidSocialMediaBean.isEmpty()) {
                                    SocialMediaBean facebookSocialMedia = hashBridesMaidSocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.FACEBOOK);
                                    if(facebookSocialMedia!=null ) {
                                        sFaceBookBridesMaid = ParseUtil.checkNull(facebookSocialMedia.getUrl());
                                    }

                                    SocialMediaBean twitterSocialMedia = hashBridesMaidSocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.TWITTER);
                                    if(twitterSocialMedia!=null ) {
                                        sTwitterBridesMaid = ParseUtil.checkNull(twitterSocialMedia.getUrl());
                                    }


                                    SocialMediaBean pinteresSocialMedia = hashBridesMaidSocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.PINTEREST);
                                    if(pinteresSocialMedia!=null ) {
                                        sPinterestBridesMaid = ParseUtil.checkNull(pinteresSocialMedia.getUrl());
                                    }
                                }

                                if(iColumnCount == 0 ){
                    %>
                                    <div class="row">
                    <%
                                }

                    %>
                                        <div class="col-md-offset-1 col-md-3"  style="text-align: center">
                                            <%if(!Utility.isNullOrEmpty(sBridesMaidImage)) { %>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <img src="<%=sBridesMaidImage%>" alt="Bride's Maid" class="img-thumbnail">
                                                </div>
                                            </div>
                                            <%}%>
                                            <%if(!Utility.isNullOrEmpty(sBridesMaidName)) { %>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <h4><%=sBridesMaidName%></h4>
                                                </div>
                                            </div>
                                            <%}%>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <h5>
                                                        <%if(!Utility.isNullOrEmpty(sFaceBookBridesMaid)) { %>
                                                        <a href="<%=sFaceBookBridesMaid%>" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                                                        <%}%>
                                                        <%if(!Utility.isNullOrEmpty(sTwitterBridesMaid)) { %>
                                                        <a href="<%=sTwitterBridesMaid%>" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                                                        <%}%>
                                                        <%if(!Utility.isNullOrEmpty(sPinterestBridesMaid)) { %>
                                                        <a href="<%=sPinterestBridesMaid%>" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                                                        <%}%>
                                                    </h5>
                                                </div>
                                            </div>
                                            <%if(!Utility.isNullOrEmpty(sBridesMaidDescription)) { %>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <p style="text-align: justify">
                                                        <%=sBridesMaidDescription%>
                                                    </p>
                                                </div>
                                            </div>
                                            <%}%>
                                        </div>
                            <%
                                iColumnCount++;
                                if(iColumnCount == 3){
                                    iColumnCount = 0;
                            %>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            &nbsp;
                                        </div>
                                    </div>
                    <%
                            }
                        }
                        if(iColumnCount <2 ) {
                    %>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                <%
                        }

                    }

                %>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row"  style="padding-top: 30px">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
        <!-- BrideMaids Ends here -->
        <%}%>
        <% if(groomsmenEventWebsitePageBean.isShow() ) {
            ArrayList<Constants.EVENT_PARTY_TYPE> arrEventPartyType = new ArrayList<Constants.EVENT_PARTY_TYPE>();
            arrEventPartyType.add(Constants.EVENT_PARTY_TYPE.GROOMSMAN);
            arrEventPartyType.add(Constants.EVENT_PARTY_TYPE.BESTMAN);


            EventPartyRequest eventPartyGroomsManRequest = new EventPartyRequest();
            eventPartyGroomsManRequest.setEventWebsiteId(couplesEventWebsitePageBean.getEventWebsiteId());
            eventPartyGroomsManRequest.setArrEventPartyType( arrEventPartyType );

            AccessEventParty accessEventParty = new AccessEventParty();
            ArrayList<EventPartyBean> arrEventPartyBean =  accessEventParty.getEventPartyListByTypeAndWebsite(eventPartyGroomsManRequest);

        %>
        <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px">
            <div class="col-md-12">
                <div class="row" id="groomsmen">
                    <div class="col-xs-11 col-xs-offset-1">
                        <div class="row">
                            <div class="col-md-12  page-title">
                                <h1>Groom's Men</h1>
                            </div>
                        </div>

                    </div>
                </div>
                <%

                    if(arrEventPartyBean!=null && !arrEventPartyBean.isEmpty() ) {

                        HashMap<String,UploadBean> hmUploadBean = accessEventParty.getEventPartyImage( arrEventPartyBean );

                        Integer iColumnCount = 0;
                        for(EventPartyBean eventPartyBean : arrEventPartyBean) {

                            String sGroomsManName = ParseUtil.checkNull(eventPartyBean.getName());
                            String sGroomsManDescription = ParseUtil.checkNull(eventPartyBean.getDescription());

                            String sGroomsManImage = Constants.EMPTY;
                            UploadBean uploadBean = hmUploadBean.get( eventPartyBean.getEventPartyId() );
                            if(uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId())){
                                sGroomsManImage = sImagePath + "/" + uploadBean.getFilename();
                            }

                            EventPartyRequest eventPartyRequest = new EventPartyRequest();
                            eventPartyRequest.setEventPartyId(eventPartyBean.getEventPartyId() );
                            eventPartyRequest.setEventWebsiteId( eventPartyBean.getEventWebsiteId() );
                            eventPartyRequest.setEventPartyType( eventPartyBean.getEventPartyType() );
                            AccessSocialMedia accessSocialMedia = new AccessSocialMedia();
                            HashMap<Constants.SOCIAL_MEDIA_TYPE, SocialMediaBean> hashGroomsManSocialMediaBean =  accessSocialMedia.getHashSocialMedia(eventPartyRequest);


                            String sFaceBookGroomsMan = Constants.EMPTY;
                            String sTwitterGroomsMan = Constants.EMPTY;
                            String sPinterestGroomsMan = Constants.EMPTY;
                            if(hashGroomsManSocialMediaBean!=null && !hashGroomsManSocialMediaBean.isEmpty()) {
                                SocialMediaBean facebookSocialMedia = hashGroomsManSocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.FACEBOOK);
                                if(facebookSocialMedia!=null ) {
                                    sFaceBookGroomsMan = ParseUtil.checkNull(facebookSocialMedia.getUrl());
                                }

                                SocialMediaBean twitterSocialMedia = hashGroomsManSocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.TWITTER);
                                if(twitterSocialMedia!=null ) {
                                    sTwitterGroomsMan = ParseUtil.checkNull(twitterSocialMedia.getUrl());
                                }


                                SocialMediaBean pinteresSocialMedia = hashGroomsManSocialMediaBean.get(Constants.SOCIAL_MEDIA_TYPE.PINTEREST);
                                if(pinteresSocialMedia!=null ) {
                                    sPinterestGroomsMan = ParseUtil.checkNull(pinteresSocialMedia.getUrl());
                                }
                            }

                            if(iColumnCount == 0 ){
                %>
                                <div class="row">   <div class="col-xs-offset-1 col-xs-3"  style="text-align: center">
                    <%
                            } else {
                     %>
                                    <div class="col-xs-3"  style="text-align: center">
                     <%
                            }

                    %>

                        <%if(!Utility.isNullOrEmpty(sGroomsManImage)) { %>
                        <div class="row">
                            <div class="col-xs-12">
                                <img src="<%=sGroomsManImage%>" alt="Bride's Maid" class="img-thumbnail">
                            </div>
                        </div>
                        <%}%>
                        <%if(!Utility.isNullOrEmpty(sGroomsManName)) { %>
                        <div class="row">
                            <div class="col-xs-12">
                                <h4><%=sGroomsManName%></h4>
                            </div>
                        </div>
                        <%}%>
                        <div class="row">
                            <div class="col-xs-12">
                                <h5>
                                    <%if(!Utility.isNullOrEmpty(sFaceBookGroomsMan)) { %>
                                    <a href="<%=sFaceBookGroomsMan%>" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                                    <%}%>
                                    <%if(!Utility.isNullOrEmpty(sTwitterGroomsMan)) { %>
                                    <a href="<%=sTwitterGroomsMan%>" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                                    <%}%>
                                    <%if(!Utility.isNullOrEmpty(sPinterestGroomsMan)) { %>
                                    <a href="<%=sPinterestGroomsMan%>" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                                    <%}%>
                                </h5>
                            </div>
                        </div>
                        <%if(!Utility.isNullOrEmpty(sGroomsManDescription)) { %>
                        <div class="row">
                            <div class="col-xs-12">
                                <p style="text-align: justify">
                                    <%=sGroomsManDescription%>
                                </p>
                            </div>
                        </div>
                        <%}%>
                    </div>
                    <%
                        iColumnCount++;
                        if(iColumnCount == 3){
                            iColumnCount = 0;
                    %>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        &nbsp;
                    </div>
                </div>
                <%
                        }
                    }
                    if(iColumnCount <2 ) {
                %>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <%
                    }

                }

            %>

            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                &nbsp;
            </div>
        </div>
        <div class="row"  style="padding-top: 30px">
            <div class="col-xs-12">
                &nbsp;
            </div>
        </div>
        <!-- GroomsMen Ends here -->
        <%}%>

    <% if(ceremonyEventWebsitePageBean.isShow() ) {
        HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmCeremonyMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( ceremonyEventWebsitePageBean.getEventWebsitePageId() );
        EventWebsitePageFeatureBean ceremonyDayFeature = hmCeremonyMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_day );
        EventWebsitePageFeatureBean ceremonyTimeFeature = hmCeremonyMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_time );
        EventWebsitePageFeatureBean ceremonyTimeZoneFeature = hmCeremonyMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_timezone );
        String sCeremonyDay = Constants.EMPTY;
        if(ceremonyDayFeature!=null) {
            sCeremonyDay = ParseUtil.checkNull(ceremonyDayFeature.getValue());
        }
        String sCeremonyTime = Constants.EMPTY;
        if(ceremonyTimeFeature!=null) {
            sCeremonyTime = ParseUtil.checkNull(ceremonyTimeFeature.getValue());
            if(ceremonyTimeZoneFeature!=null&& !Utility.isNullOrEmpty(ceremonyTimeZoneFeature.getValue())){
                sCeremonyTime = sCeremonyTime +" " + Constants.TIME_ZONE.valueOf(ParseUtil.checkNull(ceremonyTimeZoneFeature.getValue())).getTimeZoneDisplay();
            }
        }


        EventWebsitePageFeatureBean ceremonyAddressFeature = hmCeremonyMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_address );
        EventWebsitePageFeatureBean ceremonyShowMapFeature = hmCeremonyMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_showmap );
        EventWebsitePageFeatureBean ceremonyInstructionFeature = hmCeremonyMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_instruction );

    %>
        <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
            <div class="col-md-12">
                <div class="row" id="ceremony">
                    <div class="col-xs-11 col-xs-offset-1">
                        <div class="row">
                            <div class="col-md-12  page-title">
                                <h1>Ceremony</h1>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row" >
                    <% if(!Utility.isNullOrEmpty(sCeremonyTime) || !Utility.isNullOrEmpty(sCeremonyDay)) { %>
                    <div class="col-xs-offset-1 col-xs-3">
                        <h6>Date and Time</h6>
                        <p><%=sCeremonyDay%> <%=sCeremonyTime%></p>
                    </div>
                    <%}%>
                    <% if(ceremonyAddressFeature!=null) {

                        sCeremonyAddress =  ceremonyAddressFeature.getValue();
                    %>
                    <div class="col-xs-5">
                        <h6>Address</h6>
                        <p><%=ceremonyAddressFeature.getValue()%></p>
                    </div>
                    <%}%>
                </div>
                <div class="row" >
                    <div class="col-md-5">
                    </div>
                </div>
                <% if(ceremonyInstructionFeature!=null && !Utility.isNullOrEmpty( ceremonyInstructionFeature.getValue() )) { %>
                <div class="row" >
                    <div class="col-xs-11 col-xs-offset-1">
                        <h6>Instructions</h6>
                        <p><%=ceremonyInstructionFeature.getValue()%></p>
                    </div>
                </div>
                <div class="row" >
                    <div class="col-md-5">
                    </div>
                </div>
                <%}%>
                <% if(ceremonyShowMapFeature!=null && ParseUtil.sTob( ceremonyShowMapFeature.getValue())) {
                %>

                <div class="row">
                    <div class="col-xs-11 col-xs-offset-1">
                        <div id="googlemaps_ceremony" class="google-map google-map-full" style = "height: 300px"></div>
                    </div>
                </div>
                <%}%>

            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                &nbsp;
            </div>
        </div>
        <div class="row"  style="padding-top: 30px">
            <div class="col-md-12">
                &nbsp;
            </div>
        </div>
    <!-- Ceremony Ends here -->
    <%}%>
    <% if(receptionEventWebsitePageBean.isShow() ) {
        HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmReceptionMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( receptionEventWebsitePageBean.getEventWebsitePageId() );
        EventWebsitePageFeatureBean receptionDayFeature = hmReceptionMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_day );
        EventWebsitePageFeatureBean receptionTimeFeature = hmReceptionMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_time );
        EventWebsitePageFeatureBean receptionTimeZoneFeature = hmReceptionMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_timezone );
        String sReceptionDay = Constants.EMPTY;
        if(receptionDayFeature!=null) {
            sReceptionDay = ParseUtil.checkNull(receptionDayFeature.getValue());
        }
        String sReceptionTime = Constants.EMPTY;
        if(receptionTimeFeature!=null) {
            sReceptionTime = ParseUtil.checkNull(receptionTimeFeature.getValue());
            if(receptionTimeZoneFeature!=null && !Utility.isNullOrEmpty(receptionTimeZoneFeature.getValue())){
                sReceptionTime = sReceptionTime +" " + Constants.TIME_ZONE.valueOf(ParseUtil.checkNull(receptionTimeZoneFeature.getValue())).getTimeZoneDisplay();
            }
        }


        EventWebsitePageFeatureBean receptionAddressFeature = hmReceptionMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_address );
        EventWebsitePageFeatureBean receptionShowMapFeature = hmReceptionMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_showmap );
        EventWebsitePageFeatureBean receptionInstructionFeature = hmReceptionMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_instruction );


    %>
            <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
                <div class="col-md-12">
                    <div class="row" id="reception">
                        <div class="col-xs-11 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-12  page-title">
                                    <h1>Reception</h1>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row" >
                        <% if(!Utility.isNullOrEmpty(sReceptionTime) || !Utility.isNullOrEmpty(sReceptionDay)) { %>
                        <div class="col-xs-offset-1  col-xs-3">
                            <h6>Date and Time</h6>
                            <p><%=sReceptionDay%> <%=sReceptionTime%></p>
                        </div>
                        <%}%>
                        <% if(receptionAddressFeature!=null) {
                            sReceptionAddress =  ParseUtil.checkNull(receptionAddressFeature.getValue());
                        %>
                        <div class="col-xs-5">
                            <h6>Address</h6>
                            <p><%=sReceptionAddress%></p>
                        </div>
                        <%}%>
                    </div>
                    <div class="row" >
                        <div class="col-md-5">
                        </div>
                    </div>
                    <% if(receptionInstructionFeature!=null && !Utility.isNullOrEmpty( receptionInstructionFeature.getValue() )) { %>
                    <div class="row" >
                        <div class="col-xs-offset-1  col-xs-11">
                            <h6>Instructions</h6>
                            <p><%=receptionInstructionFeature.getValue()%></p>
                        </div>
                    </div>
                    <div class="row" >
                        <div class="col-xs-5">
                        </div>
                    </div>
                    <%}%>

                    <% if(receptionShowMapFeature!=null && ParseUtil.sTob( receptionShowMapFeature.getValue())) {
                    %>
                    <div class="row">
                        <div class="col-xs-offset-1  col-xs-11">
                            <div id="googlemaps_reception" class="google-map google-map-full" style = "height: 300px"></div>
                        </div>
                    </div>

                    <%}%>

                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row" style="padding-top: 30px">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
    <!-- Reception Ends here -->
    <%}%>
    <% if(travelEventWebsitePageBean.isShow() ) {

    %>
            <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
                <div class="col-md-12">
                    <div class="row" id="travel">
                        <div class="col-xs-11 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-12  page-title">
                                    <h1>Travel</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" >
                        <div class="col-xs-offset-1  col-xs-8">
                            <h6><i class="fa fa-plane"></i> Search Flights On</h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1  col-xs-3">
                            <a href="https://www.hipmunk.com/" class="btn btn-default" target ="_blank"> Hipmunk&nbsp;<i class="fa fa-external-link"></i></a>
                        </div>
                        <div class="col-xs-3">
                            <a href="https://www.priceline.com/" class="btn btn-default" target ="_blank"> Priceline.com&nbsp;<i class="fa fa-external-link"></i></a>
                        </div>
                        <div class="col-xs-3">
                            <a href="https://www.travelocity.com/" class="btn btn-default" target ="_blank"> Travelocity&nbsp;<i class="fa fa-external-link"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row" style="padding-top: 30px">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
    <!-- Travel Ends here -->
    <%}%>

    <% if(hotelsEventWebsitePageBean.isShow() ) {
        EventHotelRequest eventHotelRequest = new EventHotelRequest();
        eventHotelRequest.setEventWebsiteId( hotelsEventWebsitePageBean.getEventWebsiteId() );

        AccessEventHotels accessEventHotels = new AccessEventHotels();
        ArrayList<EventHotelsBean> arrEventHotelsBean =  accessEventHotels.getEventHotelByWebsite(eventHotelRequest);


    %>
            <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
                <div class="col-md-12">
                    <div class="row" id="hotels">
                        <div class="col-xs-11 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-12  page-title">
                                    <h1>Hotels</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" >
                        <div class="col-xs-offset-1 col-xs-8">
                            <h6>Reserve rooms in these hotels to get discounts.</h6>
                        </div>
                    </div>
                    <%
                        if(arrEventHotelsBean!=null && !arrEventHotelsBean.isEmpty() ){
                            Integer iColumnCount = 0;
                            for(EventHotelsBean eventHotelsBean : arrEventHotelsBean )  {

                                if(iColumnCount == 0) {
                                %> <div class="row"><div class="col-xs-4 col-xs-offset-1 "> <%
                                } else {
                                %> <div class="col-xs-4"> <%
                                }

                %>

                                    <div class="row">
                                        <div class="col-xs-12">
                                            <a href="<%=eventHotelsBean.getUrl()%>" target="_blank">  <p><%=eventHotelsBean.getName()%></p> </a>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <a href="tel:<%=eventHotelsBean.getPhone()%>"><i class="fa fa-phone"></i> &nbsp;<%=eventHotelsBean.getPhone()%></a>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <%=eventHotelsBean.getAddress()%>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <a href="http://maps.google.com/?q=<%=eventHotelsBean.getAddress()%>" target="_blank"><i class="fa fa-map-marker"></i> &nbsp;Directions</a>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            &nbsp;
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <%=eventHotelsBean.getInstructions()%>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            &nbsp;
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <a href="<%=eventHotelsBean.getUrl()%>" class="btn btn-default" target ="_blank"> Website&nbsp;<i class="fa fa-external-link"></i></a>
                                        </div>
                                    </div>
                                </div>
                    <%
                        iColumnCount++;
                        if(iColumnCount == 3) {
                            iColumnCount = 0;
                            %></div> <%
                            }

                        }

                    if(iColumnCount < 3) {
                    %></div>
                        <%
                            }
                    }
                %>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <div class="row" style="padding-top: 30px">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
    <!-- Hotels Ends here -->
    <%}%>


    <% if(registryEventWebsitePageBean.isShow() ) {

        EventRegistryRequest eventRegistryRequest = new EventRegistryRequest();
        eventRegistryRequest.setEventWebsiteId( registryEventWebsitePageBean.getEventWebsiteId() );

        AccessEventRegistry accessEventRegistry = new AccessEventRegistry();
        ArrayList<EventRegistryBean> arrEventRegistryBean =  accessEventRegistry.getEventRegistryByWebsite(eventRegistryRequest);
    %>
                <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
                    <div class="col-md-12">
                        <div class="row" id="registry">
                            <div class="col-xs-11 col-xs-offset-1">
                                <div class="row">
                                    <div class="col-xs-12  page-title">
                                        <h1>Registry</h1>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                            if(arrEventRegistryBean!=null && !arrEventRegistryBean.isEmpty()) {
                                Integer iColumnCount = 0;
                                for(EventRegistryBean eventRegistryBean : arrEventRegistryBean ){
                                    String sRegistryURL = ParseUtil.checkNull(eventRegistryBean.getUrl());
                                    if(!Utility.isNullOrEmpty(sRegistryURL) &&  ( !sRegistryURL.startsWith("http://") || !sRegistryURL.startsWith("https://"))  ){
                                        sRegistryURL = "https://"+sRegistryURL;
                                    }
                                    if(iColumnCount == 0) {
                                        %><div class="row"><div class="col-xs-4 col-xs-offset-1"><%
                                    } else {
                                        %><div class="col-xs-4"><%
                                    }
                    %>


                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <a href="<%=sRegistryURL%>" class="btn btn-default" target="_blank"> <%=eventRegistryBean.getName()%>&nbsp;<i class="fa fa-external-link"></i> </a>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    &nbsp;
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <%=eventRegistryBean.getInstructions()%>
                                                </div>
                                            </div>
                                        </div>
                        <%
                            iColumnCount++;
                            if(iColumnCount == 3) {
                                iColumnCount = 0;
                        %>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                &nbsp;
                                            </div>
                                        </div>
                        <%
                                }
                            }

                            if(iColumnCount < 3) {
                        %></div>
                        <div class="row">
                            <div class="col-xs-12">
                                &nbsp;
                            </div>
                        </div>
                        <%
                                }
                        }
                    %>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <div class="row" style="padding-top: 30px">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
        <!-- Registry Ends here -->
        <%}%>

        <% if(rsvpEventWebsitePageBean.isShow() ) {

        %>
        <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
            <div class="col-md-12">
                <div class="row" id="rsvp">
                    <div class="col-xs-11 col-xs-offset-1">
                        <div class="row">
                            <div class="col-md-12  page-title">
                                <h1>RSVP</h1>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        &nbsp;
                    </div>
                </div>
                <div class="row" >
                    <div class="col-xs-8  col-xs-offset-1">
                        <form id="frm_guest_rsvp">
                            <div class="row">
                                <div class="col-xs-6">
                                    <label for="rsvpFirstName" class="form_label">First Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="rsvpFirstName" name="rsvpFirstName" placeholder="First Name">
                                </div>
                                <div class="col-xs-6">
                                    <label for="rsvpLastName" class="form_label">Last Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="rsvpLastName" name="rsvpLastName" placeholder="First Name ">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <label for="rsvpEmail" class="form_label">Email</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="rsvpEmail" name="rsvpEmail" placeholder="Email">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6">
                                    <label for="rsvpIWillYouAttend" class="form_label">Will You Attend?</label><span class="required"> *</span> <br>
                                    <input type="radio"  id="rsvpIWillYouAttend"  name="rsvpWillYouAttend" value="Yes"> &nbsp;&nbsp;Yes</input> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                                    <input type="radio" name="rsvpWillYouAttend" value="No"> &nbsp;&nbsp;No</input>
                                </div>
                                <div class="col-xs-6">
                                    <label for="rsvpNumOfGuests" class="form_label">Number of Guests</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="rsvpNumOfGuests" name="rsvpNumOfGuests" placeholder="Number of Guests">
                                </div>
                            </div>
                            <input type="hidden" name="event_id" value="<%=sEventId%>"/>
                        </form>
                        <button type="button" class="btn btn-filled" id="btn_submit_rsvp">Submit RSVP</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                &nbsp;
            </div>
        </div>
        <div class="row" style="padding-top: 30px">
            <div class="col-xs-12">
                &nbsp;
            </div>
        </div>
        <!-- RSVP Ends here -->
        <%}%>

        <% if(contactUsEventWebsitePageBean.isShow() ) {

            EventContactUsRequest eventContactUsRequest = new EventContactUsRequest();
            eventContactUsRequest.setEventWebsiteId( contactUsEventWebsitePageBean.getEventWebsiteId() );

            AccessEventContactUs accessEventContactUs = new AccessEventContactUs();
            ArrayList<EventContactUsBean> arrEventContactUsBean =  accessEventContactUs.getEventContactUsByWebsite(eventContactUsRequest);

            if(arrEventContactUsBean!=null &&  !arrEventContactUsBean.isEmpty()) {

            }
        %>
        <div class="row"  style="background-color: #FFFFFF;padding-bottom: 20px;padding-right: 30px" >
            <div class="col-md-12">
                <div class="row" id="contact_us">
                    <div class="col-xs-11 col-xs-offset-1">
                        <div class="row">
                            <div class="col-xs-12  page-title">
                                <h1>Contact Us</h1>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <h6>Please feel free to contact us if you have any questions</h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-11">
                        <%
                            if(arrEventContactUsBean!=null && !arrEventContactUsBean.isEmpty()  ) {
                                for(EventContactUsBean eventContactUsBean : arrEventContactUsBean ) {
                                    String sName = ParseUtil.checkNull(eventContactUsBean.getName());
                                    String sEmail = ParseUtil.checkNull(eventContactUsBean.getEmail());
                                    String sPhone = ParseUtil.checkNull(eventContactUsBean.getPhone());

                        %>
                        <%if(!Utility.isNullOrEmpty(sName)) {%>
                        <div class="row">
                            <div class="col-xs-12">
                                <h6><%=sName%></h6>
                            </div>
                        </div>
                        <%}%>
                        <%if(!Utility.isNullOrEmpty(sEmail)) {%>
                        <div class="row">
                            <div class="col-xs-2">
                                <p><i class="fa fa-envelope-o"></i>&nbsp;Email:</p>
                            </div>
                            <div class="col-md-10">
                                <p><a href="mailto:regi@test.com"><%=sEmail%></a></p>
                            </div>
                        </div>
                        <%}%>
                        <%if(!Utility.isNullOrEmpty(sPhone)) {%>
                        <div class="row">
                            <div class="col-xs-2">
                                <p><i class="fa fa-phone"></i> Phone:</p>
                            </div>
                            <div class="col-xs-10">
                                <p><a href="tel:<%=sPhone%>"><%=sPhone%></a></p>
                            </div>
                        </div>
                        <%}%>
                        <div class="row">
                            <div class="col-xs-2">
                                &nbsp;
                            </div>
                        </div>
                        <%
                                }
                            }
                        %>

                    </div>
                </div>
            </div>
        </div>
        <!-- Contact Us Ends here -->
        <%}%>


        </div>
    </div>
</div>
</body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="/js/jquery-1.10.2.min.js">\x3C/script>')</script>
<script src="/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-backstretch/2.0.4/jquery.backstretch.min.js"></script>
<script src="//maps.google.com/maps/api/js?sensor=true"></script>
<script src="/js/jquery.gmap.min.js"></script>
<script   type="text/javascript">
    var varCeremonyAddress = '<%=sCeremonyAddress%>';
    var varReceptionAddress = '<%=sReceptionAddress%>';
    $(window).load(function() {
        $.backstretch("<%=sImagePath+"/"+bannerImageFeature.getValue() %>");
        $('.dropdown-toggle').dropdown();
        $('#googlemaps_ceremony').gMap({
            maptype: 'ROADMAP',
            scrollwheel: false,
            zoom: 13,
            markers: [
                {
                    address: varCeremonyAddress, // Your Adress Here
                    html: '',
                    popup: false
                }
            ]
        });
        $('#googlemaps_reception').gMap({
            maptype: 'ROADMAP',
            scrollwheel: false,
            zoom: 13,
            markers: [
                {
                    address: varReceptionAddress, // Your Adress Here
                    html: '',
                    popup: false
                }
            ]
        });

        $('#btn_submit_rsvp').click(function(){
            saveRsvp( getResult )
        });
    });

    function saveRsvp( callbackmethod ) {
        var actionUrl = "/proc_save_guest_rsvp.aeve";
        var methodType = "POST";
        var dataString = $("#frm_guest_rsvp").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                }
                displayAjaxOk(varResponseObj);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
</html>