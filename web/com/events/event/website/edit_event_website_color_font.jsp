<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/font-awesome.min.css" rel="stylesheet">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }
%>

<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="hide_menu" value="true"/>
    </jsp:include>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/website/event_website_editor_tab.jsp">
                            <jsp:param name="edit_event_website_colorfont_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h3>Colors</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="theme_colors">

                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h3>Fonts</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="theme_fonts">

                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_theme_color_font">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_save_selection">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" name="type"  id="save_sel_type"  value=""/>
    <input type="hidden" name="id"  id="save_sel_id" value=""/>
    <input type="hidden" name="event_website_id" id="save_sel_event_website_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script id="template_font_name" type="text/x-handlebars-template">

    <div class="row">

        <div class="col-md-12">

            <div class="row">

                <div class="col-md-1" style="text-align:center;">
                    <input type="radio" id="{{website_font_id}}" name="fonts" {{#if is_font_selected}} checked {{/if}}>
                </div>

                <div class="col-md-9"><img class="img-thumbnail" src="/com/events/event/website/static_templates/{{name}}/img/{{font_name}}.png">
                </div>

                <div class="col-md-2"><h4>{{font_name}}</h4>
                </div>
            </div>
        </div>
    </div>
</script>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">

    var ThemeColorFontModel = Backbone.Model.extend({
        urlRoot: '/proc_load_theme_colors_and_fonts.aeve'
    });
    var EventWebsiteModel = Backbone.Model.extend({
        defaults: {
            bb_event_website: undefined
        }
    });
    var ThemeColorsModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_colors: 0 ,
            bb_colors: undefined
        }
    });
    var ThemeFontsModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_fonts: 0 ,
            bb_fonts: undefined
        }
    });
    var ThemeColorsView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfColors = this.model.get('bb_num_of_colors');
            this.varColors = this.model.get('bb_colors');
            this.varEventWebsite = this.model.get('bb_event_website');
            this.varTheme = this.model.get('bb_theme');
        },
        render:function(){

            var iNumOfCols = 0;
            var websiteColorRow = '';
            for(i = 0; i <this.varNumOfColors; i++) {
                if(iNumOfCols == 0 ) {
                    websiteColorRow = $('<div>').addClass('row');
                    $(this.el).append(websiteColorRow);
                }
                var colorColumn =  createColorThumbnail( this.varColors[i] , this.varTheme , this.varEventWebsite )

                websiteColorRow.append(colorColumn);
                iNumOfCols++;
                if(iNumOfCols == 8) {
                    iNumOfCols = 0;
                    $(this.el).append( createBlankRow() );
                    $(this.el).append( createBlankRow() );
                }
            }
        },
        assignEventHandling : function() {
            for(i = 0; i <this.varNumOfColors; i++) {
                assignSelectionEvent( this.varColors[i].website_color_id , 'color',  this.varEventWebsite );
            }
        }

     });
    var ThemeFontsView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfFonts = this.model.get('bb_num_of_fonts');
            this.varFonts = this.model.get('bb_fonts');
            this.varEventWebsite = this.model.get('bb_event_website');
            this.varTheme = this.model.get('bb_theme');
            console.log( $('#template_font_name').html() );
        },
        events : {
            "click input[name='fonts']" : 'assignEventHandling'
        },
        template : Handlebars.compile( $('#template_font_name').html() ),
        render:function(){
            for(i = 0; i <this.varNumOfFonts; i++) {
                var websiteFontRow = $('<div>').addClass('row');
                console.log( 'this.varTheme.name : ' + this.varTheme.name);
                //var fontColumn =  createFontThumbnail( this.varFonts[i] , this.varTheme , this.varEventWebsite ) ;
                var isFontSelected = false;
                if(this.varFonts[i].website_font_id == this.varEventWebsite.website_font_id)  {
                    isFontSelected = true;
                }
                var newModel = {
                    "name" : this.varTheme.name ,
                    "website_font_id" :this.varFonts[i].website_font_id,
                    "font_name" : this.varFonts[i].font_name,
                    "is_font_selected" : isFontSelected
                }
                console.log(  newModel );
                var fontColumn = this.template(  eval(newModel)  );
                websiteFontRow.append(fontColumn);

                $(this.el).append( websiteFontRow );
                $(this.el).append( createBlankRow() );
                $(this.el).append( createBlankRow() );
            }
        },
        assignEventHandling : function(event) {
            //console.log($(event.target).val() + ' ---'+ $(event.target).attr('id'));
            $('#save_sel_event_website_id').val(this.varEventWebsite.event_website_id);
            $('#save_sel_type').val( '<%=Constants.EVENT_WEBSITE_PARAMS.font%>' );
            $('#save_sel_id').val( $(event.target).attr('id'));
            saveColorFontSelection(getResult)

            /*for(i = 0; i <this.varNumOfFonts; i++) {
                assignSelectionEvent( this.varFonts[i].website_font_id , 'font',  this.varEventWebsite );

            }*/
        }
    });

    $(window).load(function() {
        loadThemeColorsAndFonts(populateThemeColorsAndFonts);
    });
    function loadThemeColorsAndFonts(callbackmethod) {
        var actionUrl = "/proc_load_theme_colors_and_fonts.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_theme_color_font").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateThemeColorsAndFonts(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varEventWebsite = jsonResponseObj.event_website;
                    var numOfColors = jsonResponseObj.num_of_colors;
                    var numOfFonts = jsonResponseObj.num_of_fonts;
                    var varTheme = jsonResponseObj.theme;
                    var varColors = jsonResponseObj.colors;
                    var varFonts = jsonResponseObj.fonts;

                    generateColorsThumbnailModel(varColors,numOfColors,varTheme, varEventWebsite );
                    var themeColorsView = generateColorsThumbnail();
                    $('#theme_colors').html( themeColorsView.el );
                    themeColorsView.assignEventHandling();

                    generateFontsThumbnailModel(varFonts,numOfFonts,varTheme, varEventWebsite );
                    var themeFontsView = generateFontsThumbnail();
                    $('#theme_fonts').html( themeFontsView.el );
                    //themeFontsView.assignEventHandling();

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function assignSelectionEvent( varId , varType,  varEventWebsite ) {
        if(varId!=undefined && varType!=undefined ){
            $('#' + varId).click( function(){
                $('#save_sel_event_website_id').val(varEventWebsite.event_website_id);
                $('#save_sel_type').val( varType );
                $('#save_sel_id').val(varId);
                saveColorFontSelection(getResult)
            });
        }
    }
    function saveColorFontSelection(callbackmethod) {
        var actionUrl = "/proc_save_event_website_color_and_font.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_selection").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                //displayMssgBoxAlert('The theme was successfully selected.', false);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }




    function generateColorsThumbnailModel( varColors , numOfColors , varTheme,  varEventWebsite ) {
        this.themeColorsModel = new ThemeColorsModel({
            'bb_colors' : varColors,
            'bb_num_of_colors' : numOfColors,
            'bb_theme' : varTheme,
            'bb_event_website' : varEventWebsite
        });
    }
    function generateFontsThumbnailModel( varFonts , numOfFonts , varTheme,  varEventWebsite ) {
        this.themeFontsModel = new ThemeFontsModel({
            'bb_fonts' : varFonts,
            'bb_num_of_fonts' : numOfFonts,
            'bb_theme' : varTheme,
            'bb_event_website' : varEventWebsite
        });
    }
    function generateColorsThumbnail( ) {

        var themeColorsView = new ThemeColorsView({model:this.themeColorsModel});
        themeColorsView.render();
        return themeColorsView;
    } //#d9a11a - joyous yellow

    function generateFontsThumbnail( ) {

        var themeFontsView = new ThemeFontsView({model:this.themeFontsModel});
        themeFontsView.render();
        return themeFontsView;
    }

    function createFontThumbnail(font , theme,  event_website) {
        var varName =createFontName(font);
        var varImg = createFontImage(font, theme);
        var varRadioButton = createRadioButton(font.website_font_id , event_website.website_font_id, 'fonts' );

        var varImgCol = createFontCols( varImg, 9, ''  );
        var varNameCol = createFontCols( varName, 2, ''  );
        var varRadioButtonCol = createFontCols( varRadioButton, 1 , 'text-align:center;'  );
        var varFontRow = createFontRow( varNameCol , varImgCol , varRadioButtonCol );

        var varColumn = $('<div>').addClass('col-md-12');
        varColumn.append(varFontRow);
        return varColumn;
    }
    function createFontCols( varRowContent, varColSize, varStyleContent ){
        var varDivCol = $('<div>').addClass('col-md-' + varColSize );
        varDivCol.append(varRowContent);
        if(varStyleContent!=''){
            varDivCol.attr('style',varStyleContent );
        }
        return varDivCol;
    }
    function createFontRow( varNameCol , varImgCol , varRadioButtonCol, varStyleContent ){

        var varIndividualRow = $('<div>').addClass('row');
        if(varStyleContent!=''){
            varIndividualRow.attr('style',varStyleContent );
        }

        varIndividualRow.append( varRadioButtonCol  );
        varIndividualRow.append( varImgCol  );
        varIndividualRow.append( varNameCol  );

        return varIndividualRow;
    }
    function createFontImage( font , theme ) {
        var varImg = $('<img>').addClass('img-thumbnail');
        varImg.attr('src', '/com/events/event/website/static_templates/' + theme.name + '/img/' + font.font_name +'.png'  );
        return varImg;
    }
    function createFontName( font ){
        var varFontNam = $('<h4>').append( font.font_name );
        return varFontNam;
    }

    function  createColorThumbnail( color , theme,  event_website) {
        var varImg = createColorImage(color, theme);
        var varRadioButton = createRadioButton(color.website_color_id , event_website.website_color_id, 'colors' );

        var varIndividualImgRow = createRow(varImg,'text-align:center;');
        var varIndividualButtonRow = createRow(varRadioButton,'text-align:center;');

        var varColumn = $('<div>').addClass('col-md-1');
        varColumn.append(varIndividualImgRow);
        varColumn.append( createBlankRow() );
        varColumn.append(varIndividualButtonRow);

        return varColumn;
    }
    function createColorImage( color , theme ) {
        var varImg = $('<img>').addClass('img-thumbnail');
        varImg.attr('src', '/com/events/event/website/static_templates/' + theme.name + '/img/' + color.color_swatch_name  );
        return varImg;
    }
    function createRadioButton(buttonId , currentSetId, buttonName) {
        var varButton = $('<input>').attr('type','radio').attr('id',buttonId).attr('name',buttonName);
        if( buttonId == currentSetId) {
            varButton.prop('checked',true);
        }
        return varButton;
    }

    function createBlankRow() {
        var varBlankCol = $('<div>').addClass('col-md-12');
        varBlankCol.append( '&nbsp;' );
        var varBlankRow = $('<div>').addClass('row');
        varBlankRow.append(varBlankCol);
        return varBlankRow;
    }
    function createRow( varRowContents , varStyleContent ){

        var varIndividualColumn = $('<div>').addClass('col-md-12');
        varIndividualColumn.append( varRowContents );

        var varIndividualRow = $('<div>').addClass('row');
        if(varStyleContent!=''){
            varIndividualRow.attr('style',varStyleContent );
        }
        varIndividualRow.append( varIndividualColumn  );

        return varIndividualRow;
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
