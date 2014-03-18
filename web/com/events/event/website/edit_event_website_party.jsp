<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.event.website.AccessEventParty" %>
<%@ page import="com.events.bean.event.website.EventPartyRequest" %>
<%@ page import="com.events.bean.event.website.EventPartyBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.events.event.website.AccessSocialMedia" %>
<%@ page import="com.events.bean.event.website.SocialMediaBean" %>
<%@ page import="com.events.bean.upload.UploadBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.events.bean.upload.UploadRequestBean" %>
<%@ page import="com.events.common.UploadFile" %>
<%@ page import="com.events.bean.upload.UploadResponseBean" %>
<%@ page import="com.events.bean.event.EventRequestBean" %>
<%@ page import="com.events.event.AccessEvent" %>
<%@ page import="com.events.bean.common.FeatureBean" %>
<%@ page import="com.events.common.feature.FeatureType" %>
<%@ page import="com.events.common.Utility" %><%
    String sPartyType = ParseUtil.checkNull(request.getParameter("party"));
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    String sEventPartyId = ParseUtil.checkNull(request.getParameter("event_party_id"));
    String sEventWebsiteId = ParseUtil.checkNull(request.getParameter("event_website_id"));
    String sUploadId = Constants.EMPTY;
    String sImagePath = Constants.EMPTY;

    EventPartyRequest eventPartyRequest = new EventPartyRequest();
    eventPartyRequest.setEventPartyId(sEventPartyId);

    AccessEventParty accessEventParty = new AccessEventParty();
    EventPartyBean eventPartyBean = accessEventParty.getEventParty( eventPartyRequest );

    sUploadId = ParseUtil.checkNull( eventPartyBean.getUploadId() );
    if(!Utility.isNullOrEmpty(sUploadId)){
        UploadRequestBean uploadRequestBean = new UploadRequestBean();
        uploadRequestBean.setUploadId( sUploadId );

        UploadFile uploadFile = new UploadFile();
        UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo(uploadRequestBean) ;

        if(uploadResponseBean!=null && !Utility.isNullOrEmpty(uploadResponseBean.getUploadId()))  {
            EventRequestBean eventRequestBean = new EventRequestBean();
            eventRequestBean.setEventId( sEventId );

            AccessEvent accessEvent = new AccessEvent();
            FeatureBean featureBean = accessEvent.getFeatureValue( eventRequestBean, FeatureType.image_location);

            String sImageFolderLocation = ParseUtil.checkNull(featureBean.getValue() );

            sImagePath = Utility.getImageUploadHost() + "/" + sImageFolderLocation + "/" + uploadResponseBean.getUploadBean().getFilename();

        }

    }


    AccessSocialMedia accessSocialMedia = new AccessSocialMedia();
    ArrayList<SocialMediaBean> arrSocialMediaBean =  accessSocialMedia.getSocialMedia( eventPartyRequest );
    String sFacebookUrl = Constants.EMPTY;
    String sTwitterUrl = Constants.EMPTY;
    String sPinterestUrl = Constants.EMPTY;
    if(arrSocialMediaBean!=null && !arrSocialMediaBean.isEmpty()){
        for(SocialMediaBean socialMediaBean : arrSocialMediaBean ){
            String sUrl =  ParseUtil.checkNull(socialMediaBean.getUrl()  );
            Constants.SOCIAL_MEDIA_TYPE socialMediaType = socialMediaBean.getSocialMediaType();
            if( Constants.SOCIAL_MEDIA_TYPE.FACEBOOK.equals(socialMediaType)) {
                sFacebookUrl =  sUrl;
            } else if( Constants.SOCIAL_MEDIA_TYPE.TWITTER.equals(socialMediaType)) {
                sTwitterUrl =  sUrl;
            }  else if( Constants.SOCIAL_MEDIA_TYPE.PINTEREST.equals(socialMediaType)) {
                sPinterestUrl =  sUrl;
            }
        }
    }

    ArrayList<Constants.EVENT_PARTY_TYPE> arrEventParty = new ArrayList<Constants.EVENT_PARTY_TYPE>();
    if("bm".equalsIgnoreCase(sPartyType)){
        arrEventParty.add(Constants.EVENT_PARTY_TYPE.BRIDESMAID);
        //arrEventParty.add(Constants.EVENT_PARTY_TYPE.MAIDOFHONOR);
    } else if("gm".equalsIgnoreCase(sPartyType)){
        arrEventParty.add(Constants.EVENT_PARTY_TYPE.GROOMSMAN);
        //arrEventParty.add(Constants.EVENT_PARTY_TYPE.BESTMAN);
    }

%>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<body>
<div class="page_wrap">
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-9">
                    <form method="post" id="frm_party_image"  action="/proc_upload_partner_image.aeve" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-5">
                                    <label for="party_image" class="form_label">Photo</label>
                                    <input type="file" name="files[]" id="party_image" class="fileinput-button btn btn-default">
                                </div>
                                <div class="col-md-2">
                                    <label class="form_label">&nbsp;</label>
                                    <div>
                                        <button type="button" class="btn btn-default btn-sm" id="btn_upload_party_image">Upload</button>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label class="form_label">&nbsp;</label>
                                    <div id="party_image_progress">
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
                                <div class="col-md-offset-1 col-md-4" id="party_image_name">
                                    <img class="img-thumbnail" src="<%=sImagePath%>">
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="event_id" value="<%=sEventId%>" />
                        <input type="hidden" name="page_type" value="couples" />
                        <input type="hidden" name="party" value="party" />
                    </form>
                    <form id="frm_save_party">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="event_party_type" class="form_label">Role </label>
                                    <select class="form-control" id="event_party_type"  name="event_party_type">
                                        <%
                                            for (Constants.EVENT_PARTY_TYPE eventPartyType : arrEventParty) {
                                        %>
                                        <option <%=ParseUtil.checkNull(eventPartyBean.getEventPartyType().toString()).equalsIgnoreCase(eventPartyType.toString())?"selected":""%> value="<%=eventPartyType.toString()%>"><%=eventPartyType.getText()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="partner_name" class="form_label">Name</label>
                                    <input type="text" name="partner_name" id="partner_name" class="form-control" value="<%=ParseUtil.checkNull(eventPartyBean.getName())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="partner_facebook" class="form_label">Facebook URL </label>
                                    <input type="text" name="partner_facebook" id="partner_facebook" class="form-control" value="<%=sFacebookUrl%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="couples_partner1_twitter" class="form_label">Twitter URL </label>
                                    <input type="text" name="partner_twitter" id="couples_partner1_twitter" class="form-control" value="<%=sTwitterUrl%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="couples_partner1_pinterest" class="form_label">Pinterest URL </label>
                                    <input type="text" name="partner_pinterest" id="couples_partner1_pinterest" class="form-control" value="<%=sPinterestUrl%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="couples_partner1_description" class="form_label">Description </label>
                                    <input type="text" name="partner_description" id="couples_partner1_description" class="form-control"  value="<%=ParseUtil.checkNull(eventPartyBean.getDescription())%>" >
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="event_party_id" id="save_event_party_id"value="<%=sEventPartyId%>"/>
                        <input type="hidden" name="event_website_id" id="save_event_website_id" value="<%=sEventWebsiteId%>"/>
                        <input type="hidden" name="event_id" value="<%=sEventId%>"/>
                        <input type="hidden" name="upload_id" id="party_image_upload_id" value="<%=sUploadId%>"/>
                    </form>
                </div>
            </div>

            <div class="row">
                <div class="col-md-9">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-9">
                    <button class="btn btn-filled" id="save_event_party" param="event_party">Save</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        parent.$.colorbox.resize({
            innerWidth:$('body').width(),
            innerHeight:$('body').height()
        });
        $('#save_event_party').click(function(){
            saveWebsitePageFeaturePartySettings(populateEventParty);
        });
    });
    function saveWebsitePageFeaturePartySettings(callbackmethod ) {
        var actionUrl = "/proc_save_event_website_features_party.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_party").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateEventParty(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varEventPartyBean = jsonResponseObj.event_party_bean;
                    $('#save_event_party_id').val(varEventPartyBean.event_party_id);
                    $('#save_event_website_id').val(varEventPartyBean.event_website_id);

                }
                displayAjaxOk(varResponseObj);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }


    $(function () {
        $('#frm_party_image').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                data.context = $('#btn_upload_party_image').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {
                        displayMssgBoxAlert("The image was successfully uploaded", false);
                        var imagePath = varDataResult.imagehost+'/'+varDataResult.foldername+'/'+varDataResult.name;
                        $('#party_image_upload_id').val(varDataResult.upload_image.upload_id);
                        createImage(imagePath, 'party_image_name');

                        enableImagePreview(imagePath,'party_image_name');
                    } else {
                        displayMssgBoxAlert("Oops!! We were unable to upload the image. Please try again later.", true);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#party_image_progress .bar').css(
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

</script>
</html>