var modern = {
                bkg:"#ffffff",highlighted:"#3F9CFF",text:"#666666", nav_breadcrumb_tab_bkg:"#FCFCFC", border:"#dbf1ff",
                filled_button:"#3F9CFF", filled_button_txt:"#ffffff",plain_button:"#ffffff", plain_button_txt:"#333333"
             };
var warm_love = {
                bkg:"#f9f5e3",highlighted:"#f09a9a",text:"#666666", nav_breadcrumb_tab_bkg:"#f9e6d4", border:"#fdd6c9",
                filled_button:"#f09a9a", filled_button_txt:"#ffffff",plain_button:"#ffffff", plain_button_txt:"#f09a9a"
            };


function setupColorPanel() {
    /** Website Colors */
    $('#collapse_website_colors').collapse( 'hide');
    $('#collapse_website_colors').on('hide.bs.collapse', function () {
        $('#website_color_icon').removeClass("glyphicon glyphicon-collapse-down");
        $('#website_color_icon').addClass("glyphicon glyphicon-collapse-right");
    })
    $('#collapse_website_colors').on('show.bs.collapse', function () {
        $('#website_color_icon').removeClass("glyphicon glyphicon-collapse-right");
        $('#website_color_icon').addClass("glyphicon glyphicon-collapse-down");
    })
    $(".pick-a-color").spectrum({
        showInput: true,
        allowEmpty:false
    });
    setColorCombination('modern')
    $('#btn_website_color_preview').click(function(){
        var colorSelectedParams = $('#frm_website_colors').serialize();
        $.colorbox({ width:"100%", height:"95%", iframe:true,href:"preview/preview_colors.jsp?"+colorSelectedParams});
    });
    $('#website_color_combination').change(function() {
        setColorCombination(  $('#website_color_combination').val()  );
    });
    $('#btn_website_color_save').change(function() {
        saveColorCombination( );
    });
    $('#btn_website_color_publish').change(function() {
        publishColorCombination( );
    });
}
function saveColorCombination() {

}
function saveColorCombination() {

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