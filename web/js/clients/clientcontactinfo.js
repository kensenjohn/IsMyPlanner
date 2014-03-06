
function saveClient( callbackmethod) {
    var actionUrl = "/proc_save_clients.aeve";
    var methodType = "POST";
    var dataString = $("#frm_save_clients").serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}

function loadClientDetail(varClientId, varClientDataType , callbackmethod) {
    var actionUrl = "/proc_get_client_details.aeve";
    var methodType = "POST";
    var dataString = 'client_id=' + varClientId + '&client_datatype='+varClientDataType;
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}

function populateClientMinimum(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                processClientMinimumInfo(jsonResponseObj.client_data);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientDetail - 1)", true);
        }
    } else {
        displayMssgBoxAlert("Please try again later (populateClientDetail - 2)", true);
    }
}
function populateClientDetail(jsonResult) {

    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                processClientDetail(jsonResponseObj.client_data, jsonResponseObj.user_data, jsonResponseObj.user_info_data );
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientDetail - 1)", true);
        }
    } else {
        displayMssgBoxAlert("Please try again later (populateClientDetail - 2)", true);
    }
}

function getResult(jsonResult)
{
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                processClientResponse(jsonResponseObj.client_response);
            }
        } else {
            alert("Please try again later 1.");
        }
    } else {
        alert("Please try again later 3.");
    }
}
function processClientResponse(clientResponse) {
    if(clientResponse!=undefined) {
        var varClientId = clientResponse.client_id;
        $('#client_id').val(varClientId);
        $('#client_datatype').val('contact_info');
        $('#userId').val(clientResponse.user_id);
        $('#userInfoId').val(clientResponse.userinfo_id);
        $('#client_name_title').text( $('#clientName').val());

        $('#load_client_id').val(varClientId);
        $('#load_client_datatype').val('contact_info');

        //loadClients(populateClientList);
        displayMssgBoxAlert('Your changes were successfully updated.(loading)', false);
        $('#frm_load_client').submit();

    } else {
        displayMssgBoxAlert('Oops!! Something went wrong. Please try again later. (client_response)', false);
    }
}
function processClientMinimumInfo(varClientBean) {
    if(varClientBean!=undefined) {
        setClientNameTitle( varClientBean.client_name );
    }
}
function setClientNameTitle( varTitle) {
    if(varTitle!=undefined && varTitle!=''){
        $('#client_name_title').text( ' - ' + varTitle );
    } else {
        $('#client_name_title').text( '' ) ;
    }
}
function processClientDetail(varClientBean,varUserBean, varUserInfoBean) {
    if(varClientBean!=undefined) {
        setClientNameTitle( varClientBean.client_name );
        $('#clientName').val( varClientBean.client_name);

        if( varClientBean.is_coporate_client == true ){
            $('#isCorporateClient').prop('checked', true);
        } else {
            $('#isCorporateClient').prop('checked', false);
        }
        $('#client_id').val( varClientBean.client_id);
    }
    if(varUserBean!=undefined) {
        $('#userId').val( varUserBean.user_id);
    }
    if(varUserInfoBean!=undefined) {
        $('#clientFirstName').val( varUserInfoBean.first_name);
        $('#clientLastName').val( varUserInfoBean.last_name);
        $('#clientEmail').val( varUserInfoBean.email);
        $('#clientCompanyName').val( varUserInfoBean.company);
        $('#clientCellPhone').val( varUserInfoBean.cell_phone);
        $('#clientWorkPhone').val( varUserInfoBean.phone_num);
        $('#clientAddress1').val( varUserInfoBean.address1);
        $('#clientAddress2').val( varUserInfoBean.address3);
        $('#clientCity').val( varUserInfoBean.city);
        $('#clientState').val( varUserInfoBean.state);
        $('#clientPostalCode').val( varUserInfoBean.zipcode);
        $('#clientCountry').val( varUserInfoBean.country);
        $('#userInfoId').val( varUserInfoBean.userinfo_id);
    }
}