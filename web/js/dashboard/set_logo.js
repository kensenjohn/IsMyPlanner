function setupLogoPanel() {
    $('#collapse_logo').collapse( 'hide');
    $('#collapse_logo').on('hide.bs.collapse', function () {
        $('#logo_icon').removeClass("fa-chevron-circle-down");
        $('#logo_icon').addClass("fa-chevron-circle-right");
    })
    $('#collapse_logo').on('show.bs.collapse', function () {
        $('#logo_icon').removeClass("fa-chevron-circle-right");
        $('#logo_icon').addClass("fa-chevron-circle-down");
    })

    enablePreviewOfLogo();

    $('#btn_logo_save').click(function() {
        save_publish_Logo( getLogoResult, 'save' );
    });
    $('#btn_logo_publish').click(function() {
        save_publish_Logo( getLogoResult, 'publish' );
    });
}
function save_publish_Logo( callbackmethod, varAction ) {
    $('#logo_vendorwebsite_id').val( vendorWebsiteId );
    $('#logo_vendor_id').val( vendorId );
    $('#website_logo_panel_action').val(varAction);
    var actionUrl = "/proc_save_publish_vendor_website_logo.aeve";
    var methodType = "POST";
    var dataString = $('#frm_website_logo').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}
function getLogoResult(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                $('#logo_vendorwebsite_id').val( jsonResponseObj.vendorwebsite_id ) ;
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

function enablePreviewOfLogo( ) {
    var varImageName = $('#logo_imagename').val();
    $( '#btn_logo_preview').unbind('click');
    if(varImageName!='') {
        var varLinkToImage = $('#logo_imagehost').val()  + "/" + $('#logo_foldername').val() + "/" + varImageName;

        $( '#btn_logo_preview').click( function(){
            $.colorbox({href:varLinkToImage});
        });
        $( '#logo_image_name').click( function(){
            $.colorbox({href:varLinkToImage});
        });
    } else {
        $( '#btn_logo_preview').click( function(){
            displayMssgBoxAlert('Oops!! We were unable to find the logo.', true)
        });
        $( '#logo_image_name').click( function(){
            displayMssgBoxAlert('Oops!! We were unable to find the logo.', true)
        });
    }

}

$(function () {
    $('#frm_logo').fileupload({
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
                    $('#logo_imagename').val( varDataResult.name );
                    $('#logo_imagehost').val( varDataResult.imagehost);
                    $('#logo_foldername').val(varDataResult.foldername);

                    var imagePath = varDataResult.imagehost+'/'+varDataResult.bucket+'/'+varDataResult.foldername+'/'+varDataResult.name;
                    createLogoImage(imagePath, 'logo_image_name');
                    enablePreviewOfLogo();
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

function createLogoImage(imagePath, image_div){
    var varImg = $('<img>');
    varImg.addClass('img-thumbnail');
    varImg.attr('src',imagePath );

    $('#'+image_div).empty();
    $('#'+image_div).append(varImg);
}