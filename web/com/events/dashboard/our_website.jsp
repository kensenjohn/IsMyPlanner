<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<meta http-equiv="x-ua-compatible" content="IE=10">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/spectrum.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="dashboard_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Dashboard - Our Website</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="our_website_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="/com/events/dashboard/panel/panel_colors.jsp"/>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="/com/events/dashboard/panel/panel_logo.jsp"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="/com/events/dashboard/panel/panel_landingpage_layout.jsp"/>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_vendor_landingpage">

</form>
<form id="frm_load_vendor_website_info">

</form>
<script type="text/javascript" async src="//assets.pinterest.com/js/pinit.js"></script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script src="/js/collapse.js"></script>
<script src="/js/spectrum.js"></script>
<script type="text/javascript">
    var vendorWebsiteId='';
    var vendorId='';
    $(window).load(function() {
        loadVendorWebsiteInfo(populateVendorWebsiteInfo);
        //loadVendorLandingPageInfo(populateVendorLandingPage);

        $('#landingPageTheme').on( "change", function(){
            displayLandingPage();
        });
        displayLandingPage();
        $('#btn_save_landingpage').click(function(){
            saveLandingPage(populateVendorLandingPage);
        });

        $('#btn_preview_pinterest').click(function(){
           $.colorbox({ width:"50%", height:"80%", iframe:true,href:"preview_socialmedia.jsp?featuretype=pinterest_url&vendor_landingpage_id="+$('#vendor_landingpage_id').val()});
        });
        $('#btn_preview_facebook').click(function(){
            $.colorbox({width:"50%", height:"80%",iframe:true,href:"preview_socialmedia.jsp?featuretype=facebook_url&vendor_landingpage_id="+$('#vendor_landingpage_id').val()});
        });
        $('#btn_preview_landingpage').click(function(){
            $.colorbox({width:"100%", height:"100%",iframe:true,href:"preview_vendor_landingpage.jsp?featuretype=facebook_url&vendor_landingpage_id="+$('#vendor_landingpage_id').val()});
        });
    });
    function displayLandingPage() {
        if($('#landingPageTheme').val() == 'simple_landingpage') {
            $('#social_media_feed').hide("slow");
            $('#theme_img').attr('src', '/img/theme_thmb/simple_landingpagephoto.png');
        }
        if($('#landingPageTheme').val() == 'simple_landingpage_socialmedia') {
            $('#social_media_feed').show("slow");
            $('#theme_img').attr('src','/img/theme_thmb/simple_landingpagephoto_socialmedia.png');
        }
    }
    function saveLandingPage( callbackmethod ) {
        var actionUrl = "/proc_save_vendor_landingpage_manager.aeve";
        var methodType = "POST";
        var dataString = $("#frm_landing_page").serialize();
        dataString = dataString + '&' + $("#frm_landing_page_socialmedia").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadVendorLandingPageInfo( callbackmethod ) {
        var actionUrl = "/proc_load_vendor_landingpage_manager.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_vendor_landingpage").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadVendorWebsiteInfo(callbackmethod) {
        var actionUrl = "/proc_load_vendor_website_info.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_vendor_website_info").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateVendorWebsiteInfo( jsonResult ) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;

                    var varVendor = jsonResponseObj.vendor;
                    if(varVendor!=undefined){
                        vendorId = varVendor.vendor_id ;
                    }
                    var varVendorWebsite = jsonResponseObj.vendor_website;
                    if(varVendorWebsite!=undefined) {
                        vendorWebsiteId = varVendorWebsite.vendorwebsite_id;
                    }

                    var varImageHost = jsonResponseObj.imagehost;
                    var varFolderName = jsonResponseObj.foldername;

                    /*=====Colors Panel======= {Start}*/
                    $('#website_color_bkg').val(jsonResponseObj.saved_bkg_color);
                    $('#website_color_highlighted').val(jsonResponseObj.saved_highlighted_color);
                    $('#website_color_text').val(jsonResponseObj.saved_text_color);
                    $('#website_color_nav_bread_tab_bkg').val(jsonResponseObj.saved_navbar_breadcrumb_tab_color);
                    $('#website_color_border').val(jsonResponseObj.saved_border_color);
                    $('#website_color_filled_button').val(jsonResponseObj.saved_filled_button_color);
                    $('#website_color_filled_button_txt').val(jsonResponseObj.saved_filled_button_text_color);
                    $('#website_color_plain_button').val(jsonResponseObj.saved_plain_button_color);
                    $('#website_color_plain_button_txt').val(jsonResponseObj.saved_plain_button_text_color);
                    cachedScript( "/js/dashboard/set_color_combinations.js" ).done(function( script, textStatus ) {
                        if(vendorWebsiteId=='') {
                            setupColorPanel('pre_load_default_colors');
                        } else {
                            setupColorPanel();
                        }

                    });
                    /*=====Colors Panel======= {End}*/

                    /*=====Logo Panel======= {Start}*/
                    var varLogo = jsonResponseObj.saved_logo;
                    if(varLogo!=undefined) {
                        $('#logo_imagename').val(varLogo);
                    }
                    $('#logo_imagehost').val(varImageHost);
                    $('#logo_foldername').val(varFolderName);
                    cachedScript( "/js/dashboard/set_logo.js" ).done(function( script, textStatus ) {
                        setupLogoPanel();
                    });
                    /*=====Logo Panel======= {End}*/


                    /*=====Landing Page Layour and Content Panel======= {Start}*/
                    var varLandingPagePhoto = jsonResponseObj.saved_landingpagephoto;
                    if(varLandingPagePhoto!=undefined) {
                        $('#landingpage_picture').val( varLandingPagePhoto );
                    }
                    $('#landingpage_imagehost').val(varImageHost);
                    $('#landingpage_foldername').val(varFolderName);

                    var varLandingPageTheme = jsonResponseObj.saved_themename;
                    if(varLandingPageTheme!=undefined) {
                        $('#landingpage_theme').val( varLandingPageTheme );
                    }

                    var varPinterestUrl = jsonResponseObj.saved_pinterest_feed_script;
                    if(varPinterestUrl!=undefined) {
                        $('#landingpage_pinterest').val( varPinterestUrl);
                    }

                    var varFacebookUrl = jsonResponseObj.saved_facebook_feed_script;
                    if(varFacebookUrl!=undefined) {
                        $('#landingpage_facebook').val( varFacebookUrl );
                    }

                    cachedScript( "/js/dashboard/set_landingpage_layout_content.js" ).done(function( script, textStatus ) {
                        setupLandingPagePanel();
                    });
                    /*=====Landing Page Layour and Content Panel======= {End}*/

                }
            }
        }
    }

    function populateVendorLandingPage(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varVendor = jsonResponseObj.vendor;
                    var varVendorLandingPage = jsonResponseObj.vendor_landingpage;
                    if(varVendor!=undefined){
                        $('#vendor_id').val( varVendor.vendor_id );
                    }
                    if(varVendorLandingPage!=undefined) {
                        $('#landingPageTheme').val( varVendorLandingPage.theme);
                        $('#vendor_landingpage_id').val( varVendorLandingPage.vendor_landingpage_id);
                    }

                    var varLogo = jsonResponseObj.logo;
                    if(varLogo!=undefined) {
                        $('#landingpage_logo').val( varLogo );
                        enablePreviewOfImage( 'preview_logo' , jsonResponseObj.imagehost, jsonResponseObj.foldername, varLogo );
                    }

                    var varLandingPagePhoto = jsonResponseObj.landingpagephoto;
                    if(varLandingPagePhoto!=undefined) {
                        $('#landingpage_picture').val( varLandingPagePhoto );
                        enablePreviewOfImage( 'preview_pic' , jsonResponseObj.imagehost, jsonResponseObj.foldername, varLandingPagePhoto );
                    }

                    var varPinterestUrl = jsonResponseObj.pinterest_url;
                    if(varPinterestUrl!=undefined) {
                        $('#landingPage_pinterest').val( varPinterestUrl);
                    }

                    var varFacebookUrl = jsonResponseObj.facebook_url;
                    if(varFacebookUrl!=undefined) {
                        $('#landingPage_facebook').val( varFacebookUrl );
                    }

                    displayLandingPage();
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }
    function enablePreviewOfImage( varPreviewButtonId , domain , folderpath, imageName ) {
        if(varPreviewButtonId!=undefined && varPreviewButtonId!='') {
            $( '#div_' + varPreviewButtonId ).show();
            var varLinkToImage = domain  + "/" + folderpath + "/" + imageName;

            $( '#btn_' + varPreviewButtonId).click( function(){
                $.colorbox({href:varLinkToImage});
            });
        }
    }


</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
