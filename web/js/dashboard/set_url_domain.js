function setupUrlDomainPagePanel() {
    $('#collapse_url_domain').collapse( 'hide');
    $('#collapse_url_domain').on('hide.bs.collapse', function () {
        $('#url_domain_icon').removeClass("fa-chevron-circle-down");
        $('#url_domain_icon').addClass("fa-chevron-circle-right");
    })
    $('#collapse_url_domain').on('show.bs.collapse', function () {
        $('#url_domain_icon').removeClass("fa-chevron-circle-right");
        $('#url_domain_icon').addClass("fa-chevron-circle-down");
    })

    $('#btn_url_domain_save_publish').click(function() {
        save_publish_URLDomain( getURLDomainResult  );
    });
}

function save_publish_URLDomain( callbackmethod, varAction ) {
    $('#url_domain_vendorwebsite_id').val( vendorWebsiteId );
    $('#url_domain_vendor_id').val( vendorId );
    var actionUrl = "/proc_save_publish_vendor_website_url_domain.aeve";
    var methodType = "POST";
    var dataString = $('#frm_website_url_domain').serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}

function getURLDomainResult(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                $('#url_domain_vendorwebsite_id').val( jsonResponseObj.vendorwebsite_id ) ;
                vendorWebsiteId = jsonResponseObj.vendorwebsite_id;
                $('#preview_website_subdomain').text( jsonResponseObj.vendorwebsite_subdomain ) ;
            }
            displayAjaxOk(varResponseObj);
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
        }
    } else {
        displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
    }
}