function setupFooterPanel() {
    $('#collapse_footer').collapse( 'hide');
    $('#collapse_footer').on('hide.bs.collapse', function () {
        $('#footer_icon').removeClass("fa-chevron-circle-down");
        $('#footer_icon').addClass("fa-chevron-circle-right");
    })
    $('#collapse_footer').on('show.bs.collapse', function () {
        $('#footer_icon').removeClass("fa-chevron-circle-right");
        $('#footer_icon').addClass("fa-chevron-circle-down");
    })


    tinymce.init({
        selector: "#about_us_content",
        theme: "modern",
        plugins: [
            "advlist autolink link lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "save table contextmenu directionality emoticons template paste textcolor uploadimage"
        ],
        toolbar1: "preview | insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link uploadimage"
    });

    tinymce.init({
        selector: "#contact_content",
        theme: "modern",
        plugins: [
            "advlist autolink link lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "save table contextmenu directionality emoticons template paste textcolor uploadimage"
        ],
        toolbar1: "preview | insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link uploadimage"
    });

    tinymce.init({
        selector: "#privacy_content",
        theme: "modern",
        plugins: [
            "advlist autolink link lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "save table contextmenu directionality emoticons template paste textcolor uploadimage"
        ],
        toolbar1: "preview | insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link uploadimage"
    });

    console.log('JS length : ' + tinymce.editors.length  + ' - ' + tinymce.get('about_us_content'));
    for(i=0; i < tinymce.editors.length; i++){
        //tinymce.get(tinymce.editors[i].id).setContent(value);
        console.log('id = ' + tinymce.editors[i].id);
    }

    $('#btn_footer_about_us_save').click(function() {
        tinyMCE.triggerSave();
        savePublish_FooterContent( getFooterLayoutResult, 'about_us', 'frm_saved_footer_about_us' );
    });

    $('#btn_footer_contact_save').click(function() {
        tinyMCE.triggerSave();
        savePublish_FooterContent( getFooterLayoutResult, 'contact', 'frm_saved_footer_contact' );
    });

    $('#btn_footer_privacy_save').click(function() {
        tinyMCE.triggerSave();
        savePublish_FooterContent( getFooterLayoutResult, 'privacy', 'frm_saved_footer_privacy' );
    });
    $('#btn_footer_facebook_save').click(function() {
        savePublish_FooterContent( getFooterLayoutResult, 'facebook', 'frm_saved_footer_facebook' );
    });
    $('#btn_footer_twitter_save').click(function() {
        savePublish_FooterContent( getFooterLayoutResult, 'twitter', 'frm_saved_footer_twitter' );
    });
    $('#btn_footer_pinterest_save').click(function() {
        savePublish_FooterContent( getFooterLayoutResult, 'pinterest', 'frm_saved_footer_pinterest' );
    });




    $('#btn_footer_followus_save').click(function() {
        save_publish_LandingPage( getLandingPageLayoutResult, 'save' );
    });
    $('.footer-hide-feature').bootstrapSwitch('size', 'mini');
    $('.footer-hide-feature').bootstrapSwitch('readonly', false);

    $('.footer-hide-feature').on('switchChange', function (e, data) {
        var $element = $(data.el);
        var value = data.value;

        if($element !=undefined && value!=undefined ) {
            if(value == false) {
                $('#save_footer_feature_action').val( 'hide' );
            } else {
                $('#save_footer_feature_action').val( 'show' );
            }

            $('#save_footer_feature_type').val( 'show_footer_' + $element.attr('param') );
            showHide_FooterFeatureSetting(getFooterLayoutResult,'show_hide');
        }
    });
}
function setGeneralFooterParams(varAction){
    $('#footer_vendorwebsite_id').val( vendorWebsiteId );
    $('#footer_vendor_id').val( vendorId );
    $('#website_footer_panel_action').val(varAction);
}
function showHide_FooterFeatureSetting( callbackmethod, varAction ) {
    setGeneralFooterParams(varAction);
    var actionUrl = "/proc_show_hide_vendor_feature.aeve";
    var methodType = "POST";
    var dataString = $('#frm_save_footer_features').serialize();
    dataString = dataString + '&' + $('#frm_footer').serialize();
    console.log('dataString : ' + dataString);
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);

}
function savePublish_FooterContent( callbackmethod, varAction , varFormId ) {
    setGeneralFooterParams(varAction);
    var actionUrl = "/proc_save_publish_vendor_footer.aeve";
    var methodType = "POST";
    var dataString = $('#'+varFormId).serialize();
    dataString = dataString + '&' + $('#frm_footer').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);

}
function getFooterLayoutResult(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                $('#footer_vendorwebsite_id').val( jsonResponseObj.vendorwebsite_id ) ;
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