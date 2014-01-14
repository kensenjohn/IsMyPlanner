<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="/js/messi.min.js"></script>
<script type="text/javascript">
    $('#btn_create_event').click(function(){
        $('#frm_create_event').submit();
    });
    function makeAjaxCall(actionUrl,dataString,methodType,callBackMethod) {
        $.ajax({
            url: actionUrl ,
            type: methodType ,
            dataType: "json",
            data: dataString ,
            success: callBackMethod,
            error:function(a,b,c) {
                displayMssgBoxAlert('Oops!! Something went wrong. Please try again later (ajx-007) - ' + a.responseText + ' = ' + b + " = " + c, true);
            }
        });
    }
    function displayMssgBoxAlert(varMessage, isError) {
        var varTitle = 'Status';
        var varType = 'info';
        if(isError) {
            varTitle = 'Error';
            varType = 'error';
        } else {
            varTitle = 'Status';
            varType = 'info';
        }

        if(varMessage!='') {
            new Messi(varMessage,{title:'', titleClass: varType, buttons: [{id: 0, label: 'Close', val: 'X'}]});
        }
    }

    function displayMssgBoxMessages(varArrMessages, isError) {
        if(varArrMessages!=undefined) {
            var varMssg = '';
            var isFirst = true;
            if(varArrMessages instanceof Array){
                for(var i = 0; i<varArrMessages.length; i++) {
                    if(isFirst == false) {
                        varMssg = varMssg + '\n';
                    }
                    varMssg = varMssg + varArrMessages[i].text;
                }
            }
            if(varMssg!='' && varMssg!=undefined) {
                displayMssgBoxAlert(varMssg,isError);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (alrt-007)',isError);
            }
        }
    }
    function displayConfirmBox(varMessage, varTitle, yesButtonLabel, noButtonLabel, yesCallBackMethod , yesParamVal, noCallBackMethod , noParamVal) {
        if(yesButtonLabel == '') {
            yesButtonLabel = 'Yes';
        }
        if(noButtonLabel == '') {
            noButtonLabel = 'No';
        }
        new Messi(varMessage,
                {
                    title: varTitle,
                    titleClass: 'warning' ,
                    buttons: [
                            {id: 0, label: yesButtonLabel, val: 'Y'},
                            {id: 1, label: noButtonLabel, val: 'N'}
                    ],
                    callback: function(val) {
                            if(val == 'Y' && yesCallBackMethod!=undefined) {
                                yesCallBackMethod(yesParamVal)
                            } else if( val == 'N' && noCallBackMethod!=undefined ) {
                                noCallBackMethod(noParamVal)
                            }

                        }
                }
        );
    }

    function displayAjaxError(varResponseObj) {
        var varIsMessageExist = varResponseObj.is_message_exist;
        if(varIsMessageExist == true) {
            var jsonResponseMessage = varResponseObj.messages;
            var varArrErrorMssg = jsonResponseMessage.error_mssg;
            if(varArrErrorMssg!=undefined ) {
                displayMssgBoxMessages(varArrErrorMssg, true);
            } else {
                displayMssgBoxMessages('Oops!! We were unable to process your request. Please try again later.(procCGC 001)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later.(procCGC 002)', true);
        }
    }
</script>