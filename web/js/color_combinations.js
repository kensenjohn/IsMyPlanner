var modern = {
                bkg:"#ffffff",tab_bkg:"#fcfcfc",breadcrumb_bkg:"#fcfcfc", border:"#dbf1ff",
                filled_button:"#3F9CFF", filled_button_txt:"#ffffff", hover_default_button:"#3F9CFF", hover_default_button_txt:"#ffffff" ,
                default_txt:"#666"
             };
var warm_love = {bkg:"f9f5e3",tab_bkg:"f9e6d4",breadcrumb_bkg:"#f9e6d4", border:"#fdd6c9",
                filled_button:"#f09a9a", filled_button_txt:"#ffffff", hover_default_button:"#f09a9a", hover_default_button_txt:"#ffffff" ,
                default_txt:"666"
            };

function setColorCombination(varColorCombinationName){
    if(varColorCombinationName!=undefined) {
        var colorCombination = eval(varColorCombinationName);

        $('#website_color_bkg').spectrum("set", colorCombination.bkg);
        $('#website_color_tab_bkg').spectrum("set", colorCombination.tab_bkg);
        $('#website_color_breadcrumb_bkg').spectrum("set", colorCombination.breadcrumb_bkg);
        $('#website_color_border').spectrum("set", colorCombination.border);

        $('#website_color_filled_button').spectrum("set", colorCombination.filled_button);
        $('#website_color_filled_button_txt').spectrum("set", colorCombination.filled_button_txt);
        $('#website_color_default_button').spectrum("set", colorCombination.hover_default_button);
        $('#website_color_default_button_txt').spectrum("set", colorCombination.hover_default_button_txt);

        $('#website_color_default_text').spectrum("set", colorCombination.default_txt);


    }
}