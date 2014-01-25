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
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel-group" id="collapse_logo">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#collapse_website_colors" href="#collapse_logo_body">
                                                <span id="logo_icon" class="glyphicon glyphicon-expand"></span> Logo
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse_logo_body" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <!-- Panel Body Content Here -->
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <form method="post" id="frm_logo"  action="/proc_upload_image.aeve" method="POST" enctype="multipart/form-data">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-md-7">
                                                                    <label for="landingPageLogo" class="form_label">Logo</label><span class="required"> *</span>
                                                                    <input type="file" name="files[]" id="landingPageLogo" class="fileinput-button btn btn-default">
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <label class="form_label">&nbsp;</label>
                                                                    <div>
                                                                        <button type="button" class="btn btn-default btn-sm" id="btn_upload_logo">Upload Image</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div id="logo_progress">
                                                                        <div class="bar" style="width: 0%;"></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    &nbsp;
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-2">
                                                    <button type="button" class="btn btn-default btn-sm" id="btn_logo_preview">Preview</button>
                                                </div>
                                                <div class="col-md-2">
                                                    <button type="button" class="btn btn-default btn-sm" id="btn_logo_save">Save</button>
                                                </div>
                                                <div class="col-md-3">
                                                    <button type="button" class="btn btn-default btn-sm" id="btn_logo_publish">Publish (To Public Site)</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-12" >
                            <div class="panel-group" id="collapse_landingpage_layout">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#collapse_website_colors" href="#collapse_landingpage_layout_body">
                                                <span id="landingpage_layout_icon" class="glyphicon glyphicon-expand"></span> Layout and Content
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse_landingpage_layout_body" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <!-- Panel Body Content Here -->
                                            <div class="row">
                                                <div class="col-md-6">

                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <form method="post" id="frm_landingpage_layout">
                                                                <div class="form-group">
                                                                    <div class="row">
                                                                        <div class="col-md-12">
                                                                            <label for="landingPageTheme" class="form_label">Landing Layout Design</label><span class="required"> *</span>
                                                                            <select class="form-control" id="landingPageTheme" name="landingPageTheme">
                                                                                <option value="simple_landingpage">Modern Landing Page</option>
                                                                                <option value="simple_landingpage_socialmedia">Modern Landing Page with Social Media</option>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <form method="post" id="frm_landing_page_picture"  action="/proc_upload_image.aeve" method="POST" enctype="multipart/form-data">
                                                                <div class="form-group">
                                                                    <div class="row">
                                                                        <div class="col-md-7">
                                                                            <label for="landingPagePicture" class="form_label">Landing Page Picture</label><span class="required"> *</span>
                                                                            <input type="file" name="files[]" id="landingPagePicture" class="fileinput-button btn btn-default">
                                                                        </div>
                                                                        <div class="col-md-3">
                                                                            <label for="landingPageTheme" class="form_label">&nbsp;</label>
                                                                            <div>
                                                                                <button type="button" class="btn btn-default btn-sm" id="btn_upload_landingpage_pic">Upload Image</button>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-md-2" id="div_preview_pic" style="display:none;">
                                                                            <label class="form_label">&nbsp;</label>
                                                                            <div>
                                                                                <button type="button" class="btn btn-default btn-sm" id="btn_preview_pic">Preview</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-md-12">
                                                                            <div id="picture_progress">
                                                                                <div class="bar" style="width: 0%;"></div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-md-12">
                                                                            &nbsp;
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            &nbsp;
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6"  style="text-align: center;">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div>
                                                                <img id="theme_img" src="/img/theme_thmb/simple_landingpagephoto.png">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-10">
                                                    <form method="post" id="frm_landing_page_socialmedia">
                                                        <div class="form-group" id="social_media_feed" style = "display:none;">
                                                            <div class="row">
                                                                <div class="col-md-11">
                                                                    <label for="landingPage_facebook" class="form_label">Facebook URL</label><span class="required"> *</span>
                                                                    <textarea type="textarea"  rows="3" class="form-control" id="landingPage_facebook" name="landingPage_facebook" placeholder="Facebook Feed"></textarea>
                                                                </div>
                                                                <div class="col-md-1">
                                                                    <label for="landingPageTheme" class="form_label">&nbsp;</label>
                                                                    <div>
                                                                        <button type="button" class="btn btn-default btn-sm" id="btn_preview_facebook">Preview Feed</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    &nbsp;
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-11">
                                                                    <label for="landingPage_pinterest" class="form_label">Pinterest URL</label><span class="required"> *</span>
                                                                    <textarea type="textarea"  rows="3" class="form-control" id="landingPage_pinterest" name="landingPage_pinterest" placeholder="Pinterest Feed"></textarea>
                                                                </div>
                                                                <div class="col-md-1">
                                                                    <label for="landingPageTheme" class="form_label">&nbsp;</label>
                                                                    <div>
                                                                        <button type="button" class="btn btn-default btn-sm" id="btn_preview_pinterest">Preview Feed</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-2">
                                                    <button type="button" class="btn btn-default btn-sm" id="btn_landing_page_preview">Preview</button>
                                                </div>
                                                <div class="col-md-2">
                                                    <button type="button" class="btn btn-default btn-sm" id="btn_landing_page_save">Save</button>
                                                </div>
                                                <div class="col-md-3">
                                                    <button type="button" class="btn btn-default btn-sm" id="btn_landing_page_publish">Publish (To Public Site)</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
        </div>
    </div>
</div>
</body>
<form id="frm_load_vendor_landingpage">

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
    $(window).load(function() {
        loadVendorLandingPageInfo(populateVendorLandingPage);

        jQuery.cachedScript = function( url, options ) {
            // Allow user to set any option except for dataType, cache, and url
            options = $.extend( options || {}, {
                dataType: "script",
                cache: false,
                url: url
            });
            // Use $.ajax() since it is more flexible than $.getScript
            // Return the jqXHR object so we can chain callbacks
            return jQuery.ajax( options );
        };
        // Usage
        $.cachedScript( "/js/color_combinations.js" ).done(function( script, textStatus ) {
            setupColorPanel();
        });

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
    $(function () {
        $('#frm_landing_page_logo').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                data.context = $('#btn_upload_logo').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {
                        displayMssgBoxAlert("The logo was successfully uploaded", false);
                        enablePreviewOfImage( 'preview_logo' , varDataResult.imagehost, varDataResult.foldername, varDataResult.name );
                        $("#landingpage_logo").val( varDataResult.name );
                    } else {
                        displayMssgBoxAlert("Oops!! We were unable to upload the logo. Please try again later.", true);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#logo_progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
    $(function () {
        $('#frm_landing_page_picture').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                data.context = $('#btn_upload_landingpage_pic').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {
                        displayMssgBoxAlert("The picture was successfully uploaded", false);
                        enablePreviewOfImage( 'preview_pic' , varDataResult.imagehost, varDataResult.foldername, varDataResult.name );
                        $("#landingpage_picture").val( varDataResult.name );
                    } else {
                        displayMssgBoxAlert("Oops!! We were unable to upload the picture. Please try again later.", true);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#picture_progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
