function setupLandingPagePanel() {
    $('#collapse_landingpage_layout').collapse( 'hide');
    $('#collapse_landingpage_layout').on('hide.bs.collapse', function () {
        $('#landingpage_layout_icon').removeClass("fa-chevron-circle-down");
        $('#landingpage_layout_icon').addClass("fa-chevron-circle-right");
    })
    $('#collapse_landingpage_layout').on('show.bs.collapse', function () {
        $('#landingpage_layout_icon').removeClass("fa-chevron-circle-right");
        $('#landingpage_layout_icon').addClass("fa-chevron-circle-down");
    })

    enablePreviewOfLandingPage();
    $('#landingpage_theme').on( "change", function(){
        displayLandingPage();
    });
    displayLandingPage();

    $('#btn_landing_page_save').click(function() {
        save_publish_LandingPage( getLandingPageLayoutResult, 'save' );
    });
    $('#btn_landing_page_publish').click(function() {
        save_publish_LandingPage( getLandingPageLayoutResult, 'publish' );
    });

    $('#btn_landing_page_preview').click(function(){
        $.colorbox({width:"100%", height:"100%",iframe:true,href:"preview_vendor_landingpage.jsp?featuretype=facebook_url&vendor_website_id="+vendorWebsiteId});
    });

    $('#btn_landing_page_image_save').click( function(){
        save_publish_LandingPage_Image( getLandingPageLayoutResult, 'save_image' );
    });

    $('#btn_landing_page_greeting_save').click( function(){
        save_publish_LandingPage_Greeting( getLandingPageLayoutResult, 'save_greeting' );
    });

    $('#btn_landing_page_social_media_save').click( function(){
        save_publish_LandingPage_SocialMedia( getLandingPageLayoutResult, 'save_social_media' );
    });

    $('.layout-hide-feature').bootstrapSwitch('size', 'mini');
    $('.layout-hide-feature').bootstrapSwitch('readonly', false);

    $('.layout-hide-feature').on('switchChange', function (e, data) {
        var $element = $(data.el);
        var value = data.value;

        if($element !=undefined && value!=undefined ) {
            if(value == false) {
                $('#save_landingpage_feature_action').val( 'hide' );
            } else {
                $('#save_landingpage_feature_action').val( 'show' );
            }

            $('#save_landingpage_feature_type').val( 'show_landingpage_' + $element.attr('param') );
            showHide_LandingPageFeatureSetting(getLandingPageLayoutResult,'show_hide');
        }
    });
}

function setGeneralLandingPageParams(varAction){
    $('#landingpage_vendorwebsite_id').val( vendorWebsiteId );
    $('#landingpage_vendor_id').val( vendorId );
    $('#website_landingpage_panel_action').val(varAction);
}
function save_publish_LandingPage_SocialMedia( callbackmethod, varAction ) {
    setGeneralLandingPageParams(varAction);
    var actionUrl = "/proc_save_publish_vendor_landingpage.aeve";
    var methodType = "POST";
    var dataString = $('#frm_landingpage_socialmedia').serialize();
    dataString = dataString + '&' + $('#frm_landingpage').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);

}
function save_publish_LandingPage_Greeting( callbackmethod, varAction ) {
    setGeneralLandingPageParams(varAction);
    var actionUrl = "/proc_save_publish_vendor_landingpage.aeve";
    var methodType = "POST";
    var dataString = $('#frm_landingpage_greeting').serialize();
    dataString = dataString + '&' + $('#frm_landingpage').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);

}
function save_publish_LandingPage_Image( callbackmethod, varAction ) {
    setGeneralLandingPageParams(varAction);
    var actionUrl = "/proc_save_publish_vendor_landingpage.aeve";
    var methodType = "POST";
    var dataString = $('#frm_landingpage_image').serialize();
    dataString = dataString + '&' + $('#frm_landingpage').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);

}

function save_publish_LandingPage( callbackmethod, varAction ) {
    setGeneralLandingPageParams(varAction);
    var actionUrl = "/proc_save_publish_vendor_landingpage.aeve";
    var methodType = "POST";
    var dataString = $('#frm_landingpage_layout').serialize();
    dataString = dataString + '&' + $('#frm_landing_page_socialmedia').serialize();
    dataString = dataString + '&' + $('#frm_landingpage').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}
function showHide_LandingPageFeatureSetting( callbackmethod, varAction ) {
    setGeneralLandingPageParams(varAction);
    var actionUrl = "/proc_show_hide_vendor_feature.aeve";
    var methodType = "POST";
    var dataString = $('#frm_save_landingpage_features').serialize();
    dataString = dataString + '&' + $('#frm_landingpage').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);

}
function getLandingPageLayoutResult(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                $('#landingpage_vendorwebsite_id').val( jsonResponseObj.vendorwebsite_id ) ;
                vendorWebsiteId = jsonResponseObj.vendorwebsite_id;
            }
            displayAjaxOk(varResponseObj);
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
        }
    } else {
        displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
    }
}

function displayLandingPage() {
    /*if($('#landingpage_theme').val() == 'simple_landingpage') {
        $('#social_media_feed').hide("slow");
        $('#theme_img').attr('src', '/img/theme_thmb/simple_landingpagephoto.png');
    }
    if($('#landingpage_theme').val() == 'simple_landingpage_socialmedia') {
        $('#social_media_feed').show("slow");
        $('#theme_img').attr('src','/img/theme_thmb/simple_landingpagephoto_socialmedia.png');
    }*/
}

function enablePreviewOfLandingPage( ) {
    var varImageName = $('#landingpage_picture').val();
    $( '#btn_preview_landingpage_pic').unbind('click');
    if(varImageName!='') {
        var varLinkToImage = $('#landingpage_imagehost').val()  + "/" +  $('#landingpage_bucket').val() + "/" + $('#landingpage_foldername').val() + "/" + varImageName;

        $( '#btn_preview_landingpage_pic').click( function(){
            $.colorbox({href:varLinkToImage});
        });
    } else {
        $( '#btn_preview_landingpage_pic').click( function(){
            displayMssgBoxAlert('Oops!! We were unable to find the landing page picture.', true)
        });
    }

}

$(function () {
    $('#frm_landingpage_picture').fileupload({
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
                    $("#landingpage_picture").val( varDataResult.name );
                    $('#landingpage_imagehost').val( varDataResult.imagehost);
                    $('#llandingpage_foldername').val(varDataResult.foldername);
                    $('#llandingpage_bucket').val(varDataResult.bucket);
                    displayMssgBoxAlert("The picture was successfully uploaded", false);
                    var imagePath = varDataResult.imagehost+'/'+varDataResult.bucket+'/'+varDataResult.foldername+'/'+varDataResult.name;
                    createLandingPageImage(imagePath, 'landingpage_image_name');
                    enablePreviewOfLandingPage();
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

function createLandingPageImage(imagePath, image_div){
    var varImg = $('<img>');
    varImg.addClass('img-thumbnail');
    varImg.attr('src',imagePath );

    $('#'+image_div).empty();
    $('#'+image_div).append(varImg);
}