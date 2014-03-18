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
    if(welcomeEventWebsitePageBean == null ){
        invitationEventWebsitePageBean = new EventWebsitePageBean();
    }

    EventWebsitePageBean couplesEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.couples);
    if(welcomeEventWebsitePageBean == null ){
        welcomeEventWebsitePageBean = new EventWebsitePageBean();
    }
    EventWebsitePageBean bridesmaidEventWebsitePageBean = hmEventWebsitePage.get(Constants.EVENT_WEBSITE_PAGETYPE.bridesmaids);
    if(welcomeEventWebsitePageBean == null ){
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

    final String sImagePath = imageHost + "/" + imageFolder + "/";

    EventWebsitePageFeature eventWebsitePageFeature = new EventWebsitePageFeature();
    String sCeremonyAddress = Constants.EMPTY;
    String sReceptionAddress = Constants.EMPTY;
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="Description" content="Callseat.com is used to RSVP and to provide seating information for guests, by wedding planners, brides, grooms." />
    <meta name="author" content="Smarasoft Inc" />
    <link rel="icon"  type="image/png" href="/img/favicon.png">

    <title>Wedding Website</title>

    <!--[if lte IE 9]>
    <script type="text/javascript" src="/s/html5shiv.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/color/modern_blue.css">
    <link rel="stylesheet" type="text/css" href="/css/flexslider.css">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/cl/<%=sThemeName%>/css/elegant_style.css">
    <link rel="stylesheet" type="text/css" href="/cl/<%=sThemeName%>/css/elegant_font_<%=sFontName%>">
    <link rel="stylesheet" type="text/css" href="/cl/<%=sThemeName%>/css/elegant_color_<%=sColorCssName%>">
</head>

<body>
<div class="page_wrap">
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
                        <a class="navbar-brand" href="#">Julie And Simon</a>
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

        HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( welcomeEventWebsitePageBean.getEventWebsitePageId() );
        EventWebsitePageFeatureBean captionTitleFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.caption_title );
        EventWebsitePageFeatureBean captionTagLineFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.caption_tag_line );
        EventWebsitePageFeatureBean bannerImageFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.banner_image_name );

%>
<div>
    <div class="flexslider">
        <ul class="slides">
            <li>
                <% if(bannerImageFeature!=null) {  %>
                        <img src="<%=sImagePath+bannerImageFeature.getValue() %>" />
                <%} %>

                <div class="container  hidden-phone ">
                    <div class="slide-caption hidden-phone bottom-left">
                        <% if(captionTitleFeature!=null) { %>
                                <h1><%=ParseUtil.checkNull(captionTitleFeature.getValue())%></h1>
                        <%} %>
                        <%if(captionTagLineFeature!=null) { %>
                                <h5><%=ParseUtil.checkNull(captionTagLineFeature.getValue())%></h5>
                        <%} %>

                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<!-- Welcome Ends here -->
<%}%>
<div class="container">
<div class="content_format">
<% if(invitationEventWebsitePageBean.isShow()) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( invitationEventWebsitePageBean.getEventWebsitePageId() );

    EventWebsitePageFeatureBean inviationNameFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_name );
    EventWebsitePageFeatureBean inviationTextFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_text );
    EventWebsitePageFeatureBean inviationDateFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_date );
    EventWebsitePageFeatureBean inviationLocationFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_location_name );
    EventWebsitePageFeatureBean inviationAddressFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.invite_address );
%>
<div class="invitation-block">
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
<!-- Invitation Ends here -->
<%}%>
<% if(couplesEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( couplesEventWebsitePageBean.getEventWebsitePageId() );


%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="about_the_couple">
            <div class="col-md-12 page-title">
                <h1>The Couple</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-1 col-md-4"  style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/be5e5e/FFFFFF.png&text=Bride" alt="Bride" class="img-circle">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Julie Miller</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h5>
                            <i class="fa fa-facebook"></i>&nbsp;&nbsp;
                            <i class="fa fa-twitter"></i>&nbsp;&nbsp;
                            <i class="fa fa-pinterest"></i>&nbsp;&nbsp;
                        </h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            I throw myself down among the tall grass by the trickling stream; and, as I lie close to the earth,
                            a thousand unknown plants are noticed by me: when I hear the buzz of the little world among the stalks, and grow familiar with
                            the countless indescribable forms of the insects and flies, then I feel the presence of the Almighty, who formed us in his own image,
                            and the breath.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-4"   style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/be5e5e/FFFFFF.png&text=Groom" alt="Groom" class="img-circle">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Simone DeGaule</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h6>
                            <i class="fa fa-facebook"></i>&nbsp;&nbsp;
                            <i class="fa fa-twitter"></i>&nbsp;&nbsp;
                            <i class="fa fa-pinterest"></i>&nbsp;&nbsp;
                        </h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart.
                            I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine.
                            I am so happy, my dear friend, so absorbed in the exquisite sense of mere tranquil existence, that I neglect my talents.
                            I should be incapable of drawing a single stroke at the present moment;
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div> <!-- Couples Ends here -->
<%}%>
<% if(bridesmaidEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( couplesEventWebsitePageBean.getEventWebsitePageId() );


%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="bridesmaid">
            <div class="col-md-12 page-title">
                <h1>Bride's Maids</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-1 col-md-3"  style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/ffffff/be5e5e.png&text=Helen+Evans" alt="Bride's Maid" class="img-thumbnail">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Helen Evans</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h5>
                            <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                        </h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            O my friend -- but it is too much for my strength -- I sink under the weight of the splendour of these visions!
                            A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-3"  style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/ffffff/be5e5e.png&text=Harriet+T.+Wein" alt="Harriet T. Wein" class="img-thumbnail">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Harriet T. Wein</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h5>
                            <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                        </h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            I am so happy, my dear friend, so absorbed in the exquisite sense of mere tranquil existence, that I neglect my talents.
                            I should be incapable of drawing a single stroke at the present moment; and yet I feel that I never was a greater artist than now.
                            When, while the lovely valley teems with vapour around me, and the meridian sun strikes the upper surface of the impenetrable foliage of
                            my trees, and but a few stray gleams steal into the inner sanctuary,
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-3"  style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/ffffff/be5e5e.png&text=Rochel+C.+Devries" alt="BRochel C. Devries" class="img-thumbnail">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Rochel C. Devries</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h5>
                            <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                        </h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            When, while the lovely valley teems with vapour around me, and the meridian sun strikes the upper surface of the impenetrable
                            foliage of my trees, and but a few stray gleams steal into the inner sanctuary, I throw myself down among the tall grass by the
                            trickling stream; and, as I lie close to the earth, a thousand unknown plants are noticed by me: when I hear the buzz of the
                            little world among the stalks, and grow familiar with the countless indescribable forms of the insects and flies, then I feel
                            the presence of the Almighty
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<!-- BrideMaids Ends here -->
<%}%>
<% if(groomsmenEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( couplesEventWebsitePageBean.getEventWebsitePageId() );


%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="groomsmen">
            <div class="col-md-12 page-title">
                <h1>Groom's Men</h1>
            </div>
        </div>

        <div class="row">
            <div class="col-md-offset-1 col-md-3"  style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/ffffff/be5e5e.png&text=Erik+Mahler" alt="Erik Mahler" class="img-thumbnail">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Erik Mahler</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h5>
                            <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                        </h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will
                            give you a complete account of the system, and expound the actual teachings of the great explorer of the truth,
                            the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure,
                            but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-3"  style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/ffffff/be5e5e.png&text=Isaias+Bisrat" alt="Isaias Bisrat" class="img-thumbnail">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Isaias Bisrat</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h5>
                            <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                        </h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms
                            of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue;
                            and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking
                            from toil and pain. These cases are perfectly simple and easy to distinguish.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-3"  style="text-align: center">
                <div class="row">
                    <div class="col-md-12">
                        <img src="http://dummyimage.com/300x300/ffffff/be5e5e.png&text=Adam+Bruun" alt="Adam F. Bruun" class="img-thumbnail">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4>Adam F. Bruun</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h5>
                            <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-twitter"></i></a>&nbsp;&nbsp;
                            <a href="#" target="_blank"><i class="fa fa-pinterest"></i></a>&nbsp;&nbsp;
                        </h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <p style="text-align: justify">
                            If several languages coalesce, the grammar of the resulting language is more simple and regular than that of the individual languages.
                            The new common language will be more simple and regular than the existing European languages. It will be as simple as Occidental;
                            in fact, it will be Occidental. To an English person, it will seem like simplified English, as a skeptical Cambridge friend of mine
                            told me what Occidental is. The European languages are members of the same family. Their separate existence is a myth.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<!-- Groomsmen Ends here -->
<%}%>
<% if(ceremonyEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( ceremonyEventWebsitePageBean.getEventWebsitePageId() );
    EventWebsitePageFeatureBean ceremonyDayFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_day );
    EventWebsitePageFeatureBean ceremonyTimeFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_time );
    EventWebsitePageFeatureBean ceremonyTimeZoneFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_timezone );
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


    EventWebsitePageFeatureBean ceremonyAddressFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_address );
    EventWebsitePageFeatureBean ceremonyShowMapFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_showmap );
    EventWebsitePageFeatureBean ceremonyInstructionFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.ceremony_instruction );

%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="ceremony">
            <div class="col-md-12 page-title">
                <h1>Ceremony</h1>
            </div>
        </div>
        <div class="row" >
            <% if(!Utility.isNullOrEmpty(sCeremonyTime) || !Utility.isNullOrEmpty(sCeremonyDay)) { %>
                <div class="col-md-3">
                    <h6>Date and Time</h6>
                    <p><%=sCeremonyDay%> <%=sCeremonyTime%></p>
                </div>
            <%}%>
            <% if(ceremonyAddressFeature!=null) {

                sCeremonyAddress =  ceremonyAddressFeature.getValue();
            %>
                <div class="col-md-5">
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
            <div class="col-md-12">
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
            <div class="col-md-12">
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
<!-- Ceremony Ends here -->
<%}%>
<% if(receptionEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( receptionEventWebsitePageBean.getEventWebsitePageId() );
    EventWebsitePageFeatureBean receptionDayFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_day );
    EventWebsitePageFeatureBean receptionTimeFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_time );
    EventWebsitePageFeatureBean receptionTimeZoneFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_timezone );
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


    EventWebsitePageFeatureBean receptionAddressFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_address );
    EventWebsitePageFeatureBean receptionShowMapFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_showmap );
    EventWebsitePageFeatureBean receptionInstructionFeature = hmMultipleFeatures.get( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.reception_instruction );


%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="reception">
            <div class="col-md-12 page-title">
                <h1>Reception</h1>
            </div>
        </div>
        <div class="row" >
            <% if(!Utility.isNullOrEmpty(sReceptionTime) || !Utility.isNullOrEmpty(sReceptionDay)) { %>
            <div class="col-md-3">
                <h6>Date and Time</h6>
                <p><%=sReceptionDay%> <%=sReceptionTime%></p>
            </div>
            <%}%>
            <% if(receptionAddressFeature!=null) {
                sReceptionAddress =  ParseUtil.checkNull(receptionAddressFeature.getValue());
            %>
            <div class="col-md-5">
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
            <div class="col-md-12">
                <h6>Instructions</h6>
                <p><%=receptionInstructionFeature.getValue()%></p>
            </div>
        </div>
        <div class="row" >
            <div class="col-md-5">
            </div>
        </div>
        <%}%>

        <% if(receptionShowMapFeature!=null && ParseUtil.sTob( receptionShowMapFeature.getValue())) {
        %>
        <div class="row">
            <div class="col-md-12">
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
<!-- Reception Ends here -->
<%}%>
<% if(travelEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( couplesEventWebsitePageBean.getEventWebsitePageId() );


%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="travel">
            <div class="col-md-12 page-title">
                <h1>Travel</h1>
            </div>
        </div>
        <div class="row" >
            <div class="col-md-8">
                <h6><i class="fa fa-plane"></i> Search Flights On</h6>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <a href="http://www.hipmunk.com/" class="btn btn-default" target ="_blank"> Hipmunk&nbsp;<i class="fa fa-external-link"></i></a>
            </div>
            <div class="col-md-3">
                <a href="http://www.priceline.com/" class="btn btn-default" target ="_blank"> Priceline.com&nbsp;<i class="fa fa-external-link"></i></a>
            </div>
            <div class="col-md-3">
                <a href="http://www.travelocity.com/" class="btn btn-default" target ="_blank"> Travelocity&nbsp;<i class="fa fa-external-link"></i></a>
            </div>
        </div>
    </div>
</div>
<div class="row">
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
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="hotels">
            <div class="col-md-12 page-title">
                <h1>Hotels</h1>
            </div>
        </div>
        <div class="row" >
            <div class="col-md-8">
                <h6>Reserve rooms in these hotels to get discounts.</h6>
            </div>
        </div>
        <%
            if(arrEventHotelsBean!=null && !arrEventHotelsBean.isEmpty() ){
                Integer iColumnCount = 0;
                for(EventHotelsBean eventHotelsBean : arrEventHotelsBean )  {

                    if(iColumnCount == 0) {
                        %> <div class="row"> <%
                    }

        %>
                    <div class="col-md-4">
                        <div class="row">
                            <div class="col-md-12">
                                <a href="<%=eventHotelsBean.getUrl()%>" target="_blank">  <p><%=eventHotelsBean.getName()%></p> </a>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <a href="tel:<%=eventHotelsBean.getPhone()%>"><i class="fa fa-phone"></i> &nbsp;<%=eventHotelsBean.getPhone()%></a>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                               <%=eventHotelsBean.getAddress()%>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <a href="http://maps.google.com/?q=<%=eventHotelsBean.getAddress()%>" target="_blank"><i class="fa fa-map-marker"></i> &nbsp;Directions</a>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <%=eventHotelsBean.getInstructions()%>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
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
            }
        %>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
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
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="registry">
            <div class="col-md-12 page-title">
                <h1>Registry</h1>
            </div>
        </div>
        <%
            if(arrEventRegistryBean!=null && !arrEventRegistryBean.isEmpty()) {
                Integer iColumnCount = 0;
                for(EventRegistryBean eventRegistryBean : arrEventRegistryBean ){
                    if(iColumnCount == 0) {
                        %><div class="row"><%
                    }
        %>

                            <div class="col-md-4">
                                <div class="row">
                                    <div class="col-md-12">
                                        <a href="<%=eventRegistryBean.getUrl()%>" class="btn btn-default" target="_blank"> <%=eventRegistryBean.getName()%>&nbsp;<i class="fa fa-external-link"></i> </a>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <%=eventRegistryBean.getInstructions()%>
                                    </div>
                                </div>
                            </div>
        <%
                    iColumnCount++;
                    if(iColumnCount == 3) {
                        iColumnCount = 0;
                        %></div><%
                    }
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
<!-- Registry Ends here -->
<%}%>
<% if(rsvpEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( couplesEventWebsitePageBean.getEventWebsitePageId() );


%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="rsvp">
            <div class="col-md-12 page-title">
                <h1>RSVP</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                &nbsp;
            </div>
        </div>
        <div class="row" >
            <div class="col-md-8">
                <form>
                    <div class="row">
                        <div class="col-md-6">
                            <label for="rsvpFirstName" class="form_label">First Name</label><span class="required"> *</span>
                            <input type="text" class="form-control" id="rsvpFirstName" name="rsvpFirstName" placeholder="First Name">
                        </div>
                        <div class="col-md-6">
                            <label for="rsvpLastName" class="form_label">Last Name</label><span class="required"> *</span>
                            <input type="text" class="form-control" id="rsvpLastName" name="rsvpLastName" placeholder="First Name ">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label for="rsvpEmail" class="form_label">Email</label><span class="required"> *</span>
                            <input type="text" class="form-control" id="rsvpEmail" name="rsvpEmail" placeholder="Email">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <label for="rsvpIWillYouAttend" class="form_label">Will You Attend?</label><span class="required"> *</span> <br>
                            <input type="radio"  id="rsvpIWillYouAttend"  name="rsvpWillYouAttend" value="Yes"> &nbsp;&nbsp;Yes</input> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                            <input type="radio" name="rsvpWillYouAttend" value="No"> &nbsp;&nbsp;No</input>
                        </div>
                        <div class="col-md-6">
                            <label for="rsvpNumOfGuests" class="form_label">Number of Guests</label><span class="required"> *</span>
                            <input type="text" class="form-control" id="rsvpNumOfGuests" name="rsvpNumOfGuests" placeholder="Number of Guests">
                        </div>
                    </div>
                </form>
                <button type="button" class="btn btn-filled" id="btn_submit_rsvp">Submit RSVP</button>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<!-- RSVP Ends here -->
<%}%>
<% if(contactUsEventWebsitePageBean.isShow() ) {
    HashMap<Constants.EVENT_WEBSITE_PAGE_FEATURETYPE,EventWebsitePageFeatureBean> hmMultipleFeatures = eventWebsitePageFeature.getHashMultipleFeatures( couplesEventWebsitePageBean.getEventWebsitePageId() );


%>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="row" id="contact_us">
            <div class="col-md-12 page-title">
                <h1>Contact Us</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h6>Feel free to contact us if you have any questions</h6>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-1 col-md-12">
                <div class="row">
                    <div class="col-md-2">
                        <h6>Julie Miller</h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-1">
                        <p><i class="fa fa-envelope-o"></i>&nbsp;Email:</p>
                    </div>
                    <div class="col-md-11">
                        <p><a href="mailto:regi@test.com">julie@test.com</a></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-1">
                        <p><i class="fa fa-phone"></i> Phone:</p>
                    </div>
                    <div class="col-md-11">
                        <p><a href="tel:2144219111">(214)421-9111</a></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                        &nbsp;
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                        <h6>Simone DeFaulle</h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-1">
                        <p><i class="fa fa-envelope-o"></i>&nbsp;Email:</p>
                    </div>
                    <div class="col-md-11">
                        <p><a href="mailto:regi@test.com">simone@test.com</a></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-1">
                        <p><i class="fa fa-phone"></i> Phone:</p>
                    </div>
                    <div class="col-md-11">
                        <p><a href="tel:3124219111">(312)421-9111</a></p>
                    </div>
                </div>
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
<script src="http://www.openlayers.org/api/OpenLayers.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.flexslider-min.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script src="http://www.smarasoft.com/js/jquery.gmap.min.js"></script>
<script   type="text/javascript">
    var varCeremonyAddress = '<%=sCeremonyAddress%>';
    var varReceptionAddress = '<%=sReceptionAddress%>';
    $(window).load(function() {
        $('.flexslider').flexslider({
            animation: "slide"
        });
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
    });
</script>
</html>