<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/font-awesome.min.css" rel="stylesheet">
<link href="/css/bootstrap-switch.min.css" rel="stylesheet">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }
%>

<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="hide_menu" value="true"/>
    </jsp:include>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/website/event_website_editor_tab.jsp">
                            <jsp:param name="edit_event_website_pages_active" value="active"/>
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
                    <div class="panel-group" id="collapse_website_welcome">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#collapse_website_welcome" href="#collapse_welcome">
                                        <i id="welcome_collapse_icon" class="fa fa-chevron-circle-right"></i> Welcome</a>
                                    &nbsp;&nbsp;
                                    <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide"  class="hide-page" name="welcome_hide" id="welcome_hide" param="welcome">

                                </h4>
                            </div>
                            <div id="collapse_welcome" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            &nbsp;
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-8">
                                                    <form method="post" id="frm_welcome_banner"  action="/proc_upload_event_image.aeve" method="POST" enctype="multipart/form-data">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-md-5">
                                                                    <label for="welcome_banner" class="form_label">Banner</label><span class="required"> *</span>
                                                                    <input type="file" name="files[]" id="welcome_banner" class="fileinput-button btn btn-default">
                                                                </div>
                                                                <div class="col-md-2">
                                                                    <label class="form_label">&nbsp;</label>
                                                                    <div>
                                                                        <button type="button" class="btn btn-default btn-sm" id="btn_upload_banner">Upload</button>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <label class="form_label">&nbsp;</label>
                                                                    <div id="welcome_banner_progress">
                                                                        <div class="bar" style="width: 0%;"></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    &nbsp;
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-offset-1 col-md-4" id="welcome_banner_image_name">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="event_id" value="<%=sEventId%>" />
                                                        <input type="hidden" name="page_type" value="welcome" />
                                                        <input type="hidden" name="feature_type" value="<%=Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.banner_image_name%>" />
                                                    </form>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8">
                                                    &nbsp;
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8">
                                                    <form id="frm_save_welcome">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label for="welcome_caption_title" class="form_label">Title</label>
                                                                    <input type="text" name="caption_title" id="welcome_caption_title" class="form-control">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <label for="welcome_caption_tag_line" class="form_label">Tag Line </label>
                                                                    <input type="text" name="caption_tag_line" id="welcome_caption_tag_line" class="form-control">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="page_type" id="save_page_type" value="welcome"/>
                                                        <input type="hidden" name="event_id" id="save_event_id" value="<%=sEventId%>"/>
                                                    </form>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <button class="btn btn-filled save-website-page" id="save_welcome" param="welcome">Save</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#collapse_website_welcome" href="#collapse_invitation">
                                        <i id="invitation_collapse_icon" class="fa fa-chevron-circle-right"></i> Invitation</a>
                                        &nbsp;&nbsp;
                                        <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide" class="hide-page" name="invitation_hide" id="invitation_hide" param="invitation">
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse_invitation" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <form id="frm_save_invitation">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_name" class="form_label">Invitation Names</label>
                                                            <input type="text" name="invite_name" id="invitation_invite_name" class="form-control" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_text" class="form_label">Invite Text </label>
                                                            <input type="text" name="invite_text" id="invitation_invite_text" class="form-control" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_date" class="form_label">Date of Event</label>
                                                            <input type="text" name="invite_date" id="invitation_invite_date" class="form-control" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_location_name" class="form_label">Location Name</label>
                                                            <input type="text" name="invite_location_name" id="invitation_invite_location_name" class="form-control">
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_address" class="form_label">Location Address </label>
                                                            <input type="text" name="invite_address" id="invitation_invite_address" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <input type="hidden" name="page_type" id="save_page_type" value="invitation"/>
                                                <input type="hidden" name="event_id" id="save_event_id" value="<%=sEventId%>"/>
                                            </form>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <button class="btn btn-filled save-website-page" id="save_invitation" param="invitation">Save</button>
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
        </div>
    </div>
</div>
</body>
<form id="frm_load_web_page">
    <input type="hidden" id="event_id" name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_load_web_page_features">
    <input type="hidden" id="event_id" name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" id="load_page_type" name="page_type" value=""/>
</form>
<form id="frm_save_web_page">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" id="save_web_page_type" name="page_type" value=""/>
    <input type="hidden" id="save_action" name="action" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/collapse.js"></script>
<script src="/js/bootstrap-switch.min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        $('.hide-page').bootstrapSwitch('size', 'mini');
        $('.hide-page').bootstrapSwitch('readonly', false);

        $('#collapse_welcome').on('hide.bs.collapse', function () {
            toggleCollapseIcon('welcome_collapse_icon');
        })
        $('#collapse_welcome').on('show.bs.collapse', function () {
            toggleCollapseIcon('welcome_collapse_icon');
            loadWebsitePageFeatures('welcome', populateWebsitePageFeatures)
        })

        $('#collapse_invitation').on('hide.bs.collapse', function () {
            toggleCollapseIcon('invitation_collapse_icon');
        })
        $('#collapse_invitation').on('show.bs.collapse', function () {
            toggleCollapseIcon('invitation_collapse_icon');
            loadWebsitePageFeatures('invitation', populateWebsitePageFeatures)
        })

        $('.save-website-page').click(function(){
            saveWebsitePageFeatureSetting(getResult , $(this).attr('param'));
        })

        $('.hide-page').on('switchChange', function (e, data) {
            var $element = $(data.el);
            var value = data.value;

            if($element !=undefined && value!=undefined ) {
                if(value == false) {
                    $('#save_action').val( 'hide' );
                } else {
                    $('#save_action').val( 'show' );
                }

                $('#save_web_page_type').val( $element.attr('param') );
                saveWebsitePageSetting(getResult)
            } else {

            }
            // console.log( 'value = ' + data.value + ' - ' + $element.attr('param') );
        });

        loadWebsitePage(populateWebsitePages);

    });

    function toggleCollapseIcon(varIcon) {
        $('#'+varIcon).toggleClass("fa-chevron-circle-down").toggleClass("a-chevron-circle-right");
    }
    function saveWebsitePageSetting(callbackmethod ) {
        var actionUrl = "/proc_save_event_website_page.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_web_page" ).serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveWebsitePageFeatureSetting(callbackmethod , pageType) {
        var actionUrl = "/proc_save_event_website_page_features.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_"+pageType).serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadWebsitePage(callbackmethod) {
        var actionUrl = "/proc_load_website_page.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_web_page").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadWebsitePageFeatures(pageType,callbackmethod) {
        if(pageType!=undefined) {
            $('#load_page_type').val( pageType );
            var actionUrl = "/proc_load_website_page_features.aeve";
            var methodType = "POST";
            var dataString = $("#frm_load_web_page_features").serialize();
            makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
        } else {
            displayMssgBoxAlert('Oops!! We were unable to load any information for this page. Please try again later', true);
        }
    }
    var WebsitePageModel = Backbone.Model.extend({});
    var WebsitePageView = Backbone.View.extend({
        initialize: function(){
            this.varArrayOfEventWebsitePages = this.model.get('bb_arr_event_website_page');
        },
        render:function(){
            for (var key in this.varArrayOfEventWebsitePages) {
                $('#'+key+'_hide').bootstrapSwitch('state', this.varArrayOfEventWebsitePages[key].is_show );
            }
        }
    } );

    var WebsitePageFeaturesModel = Backbone.Model.extend({});
    var WebsitePageFeaturesView = Backbone.View.extend({
        initialize: function(){
            this.varPage = this.model.get('bb_event_website_page');
            this.varPageType = this.model.get('bb_page_type');
            this.varFeatures = this.model.get('bb_event_website_page_features');
            this.varImageHost = this.model.get('bb_image_host');
            this.varImageFolderLocation = this.model.get('bb_image_folder_location');
        },
        render:function(){
            for (var key in this.varFeatures) {
                var testKey = key.split('_image_name');
                if(testKey == key) {
                    $('#'+this.varPageType+'_'+key).val( this.varFeatures[key] );
                } else {
                    //welcome_banner_image_name
                    if( this.varFeatures[key]!=undefined &&  this.varFeatures[key]!='') {
                        var imagePath = this.varImageHost + '/' + this.varImageFolderLocation + '/' +  this.varFeatures[key];
                        createImage(imagePath, this.varPageType+'_'+key);
                        enableImagePreview(imagePath,this.varPageType+'_'+key);

                    }
                }

            }
        }
    });
    function generateWebsitePages( varJsonResponse ) {
        this.websitePageModel = new WebsitePageModel({
            'bb_arr_event_website_page' : varJsonResponse.event_website_pages
        });

        var webpagesView = new WebsitePageView({model:this.websitePageModel});
        webpagesView.render();
    }
    function generateWebsitePageFeatures( varJsonResponse ) {
        this.websitePageFeaturesModel = new WebsitePageFeaturesModel({
            'bb_event_website_page' : varJsonResponse.event_website_page,
            'bb_page_type' : varJsonResponse.page_type,
            'bb_event_website_page_features' : varJsonResponse.event_website_page_feature ,
            'bb_image_host' : varJsonResponse.image_host,
            'bb_image_folder_location' : varJsonResponse.image_folder_location
        });
        var webpageFeaturesView = new WebsitePageFeaturesView({model:this.websitePageFeaturesModel});
        webpageFeaturesView.render();
    }
    function populateWebsitePages(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        generateWebsitePages( jsonResponseObj );
                    }

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function populateWebsitePageFeatures(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        generateWebsitePageFeatures( jsonResponseObj );
                    }

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    $(function () {
        $('#frm_welcome_banner').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                data.context = $('#btn_upload_banner').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {
                        displayMssgBoxAlert("The banner was successfully uploaded", false);
                        $('#banner_imagename').val( varDataResult.name );
                        $('#banner_imagehost').val( varDataResult.imagehost);
                        $('#banner_foldername').val(varDataResult.foldername);
                        var imagePath = varDataResult.imagehost+'/'+varDataResult.foldername+'/'+varDataResult.name;

                        createImage(imagePath, 'welcome_banner_image_name');

                        enableImagePreview(imagePath,'welcome_banner_image_name');
                    } else {
                        displayMssgBoxAlert("Oops!! We were unable to upload the banner  . Please try again later.", true);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#welcome_banner_progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
    function createImage(imagePath, image_div){
        var varImg = $('<img>');
        varImg.addClass('img-thumbnail');
        varImg.attr('src',imagePath );

        $('#'+image_div).empty();
        $('#'+image_div).append(varImg);
    }

    function enableImagePreview( varImagePath ,varImgDivId ) {
        $( '#btn_logo_preview').unbind('click');
        if(varImagePath!='' && varImgDivId!='') {

            $( '#'+varImgDivId).click( function(){
                $.colorbox({href:varImagePath});
            });
        } else {
            $( '#btn_logo_preview').click( function(){
                displayMssgBoxAlert('Oops!! We were unable to find the logo.', true)
            });
        }

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
<jsp:include page="/com/events/common/footer_bottom.jsp"/>