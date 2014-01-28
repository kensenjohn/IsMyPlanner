function setupLandingPagePanel() {
    $('#collapse_landingpage_layout').collapse( 'hide');
    $('#collapse_landingpage_layout').on('hide.bs.collapse', function () {
        $('#landingpage_layout_icon').removeClass("glyphicon glyphicon-collapse-down");
        $('#landingpage_layout_icon').addClass("glyphicon glyphicon-collapse-right");
    })
    $('#collapse_landingpage_layout').on('show.bs.collapse', function () {
        $('#landingpage_layout_icon').removeClass("glyphicon glyphicon-collapse-right");
        $('#landingpage_layout_icon').addClass("glyphicon glyphicon-collapse-down");
    })

    enablePreviewOfLandingPage();
    $('#landingpage_theme').on( "change", function(){
        displayLandingPage();
    });
    displayLandingPage();

    $('#btn_landing_page_save').click(function() {
        save_publish_LandingPage( getResult, 'save' );
    });
    $('#btn_landing_page_publish').click(function() {
        save_publish_LandingPage( getResult, 'publish' );
    });

    $('#btn_landing_page_preview').click(function(){
        $.colorbox({width:"100%", height:"100%",iframe:true,href:"preview_vendor_landingpage.jsp?featuretype=facebook_url&vendor_website_id="+vendorWebsiteId});
    });
}
function save_publish_LandingPage( callbackmethod, varAction ) {
    $('#landingpage_vendorwebsite_id').val( vendorWebsiteId );
    $('#landingpage_vendor_id').val( vendorId );
    $('#website_landingpage_panel_action').val(varAction);
    var actionUrl = "/proc_save_publish_vendor_landingpage.aeve";
    var methodType = "POST";
    var dataString = $('#frm_landingpage_layout').serialize();
    dataString = dataString + '&' + $('#frm_landing_page_socialmedia').serialize();
    dataString = dataString + '&' + $('#frm_landingpage').serialize();
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
    if($('#landingpage_theme').val() == 'simple_landingpage') {
        $('#social_media_feed').hide("slow");
        $('#theme_img').attr('src', '/img/theme_thmb/simple_landingpagephoto.png');
    }
    if($('#landingpage_theme').val() == 'simple_landingpage_socialmedia') {
        $('#social_media_feed').show("slow");
        $('#theme_img').attr('src','/img/theme_thmb/simple_landingpagephoto_socialmedia.png');
    }
}

function enablePreviewOfLandingPage( ) {
    var varImageName = $('#landingpage_picture').val();
    $( '#btn_preview_landingpage_pic').unbind('click');
    if(varImageName!='') {
        var varLinkToImage = $('#landingpage_imagehost').val()  + "/" + $('#landingpage_foldername').val() + "/" + varImageName;

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
                    displayMssgBoxAlert("The picture was successfully uploaded", false);
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