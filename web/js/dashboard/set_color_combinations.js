var modern = {
                bkg:"#ffffff",highlighted:"#3F9CFF",text:"#666666", nav_breadcrumb_tab_bkg:"#FCFCFC", border:"#dbf1ff",
                filled_button:"#3F9CFF", filled_button_txt:"#ffffff",plain_button:"#ffffff", plain_button_txt:"#333333"
             };
var warm_love = {
                bkg:"#f9f5e3",highlighted:"#f09a9a",text:"#666666", nav_breadcrumb_tab_bkg:"#f9e6d4", border:"#fdd6c9",
                filled_button:"#f09a9a", filled_button_txt:"#ffffff",plain_button:"#ffffff", plain_button_txt:"#f09a9a"
            };


function setupColorPanel(varColorPallete) {
    /** Website Colors */
    $('#collapse_website_colors').collapse( 'hide');
    $('#collapse_website_colors').on('hide.bs.collapse', function () {
        $('#website_color_icon').removeClass("fa-chevron-circle-down");
        $('#website_color_icon').addClass("fa-chevron-circle-right");
    })
    $('#collapse_website_colors').on('show.bs.collapse', function () {
        $('#website_color_icon').removeClass("fa-chevron-circle-right");
        $('#website_color_icon').addClass("fa-chevron-circle-down");
    })
    $(".pick-a-color").spectrum({
        showInput: true,
        allowEmpty:false
    });
    if(varColorPallete == 'pre_load_default_colors') {
        setColorCombination('modern')
    }

    $('#btn_website_color_preview').click(function(){
        var colorSelectedParams = $('#frm_website_colors').serialize();
        $.colorbox({ width:"100%", height:"95%", iframe:true,href:"preview/preview_colors.jsp?"+colorSelectedParams});
    });
    $('#website_color_combination').change(function() {
        setColorCombination(  $('#website_color_combination').val()  );
    });
    $('#btn_website_color_save').click(function() {
        save_publish_ColorCombination( getResult, 'save' );
    });
    $('#btn_website_color_publish').click(function() {
        save_publish_ColorCombination( getResult, 'publish' );
    });
}
function save_publish_ColorCombination( callbackmethod, varAction ) {
    $('#color_vendorwebsite_id').val( vendorWebsiteId );
    $('#color_vendor_id').val( vendorId );
    $('#website_color_panel_action').val(varAction);
    var actionUrl = "/proc_save_publish_vendor_website_colors.aeve";
    var methodType = "POST";
    var dataString = $('#frm_website_colors').serialize();
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

                $('#color_vendorwebsite_id').val( jsonResponseObj.vendorwebsite_id ) ;
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

function setColorCombination(varColorCombinationName){
    if(varColorCombinationName!=undefined) {
        var colorCombination = eval(varColorCombinationName);

        $('#website_color_bkg').spectrum("set", colorCombination.bkg);
        $('#website_color_highlighted').spectrum("set", colorCombination.highlighted);
        $('#website_color_text').spectrum("set", colorCombination.text);
        $('#website_color_nav_bread_tab_bkg').spectrum("set", colorCombination.nav_breadcrumb_tab_bkg);

        $('#website_color_border').spectrum("set", colorCombination.border);
        $('#website_color_filled_button').spectrum("set", colorCombination.filled_button);
        $('#website_color_filled_button_txt').spectrum("set", colorCombination.filled_button_txt);
        $('#website_color_plain_button').spectrum("set", colorCombination.plain_button);
        $('#website_color_plain_button_txt').spectrum("set", colorCombination.plain_button_txt);
    }
}