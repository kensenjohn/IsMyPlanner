<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

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
                            <jsp:param name="edit_event_website_theme_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h3>Current Theme</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="current_theme">

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
                    <h3>Select A Theme</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="select_a_theme">

                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_event_website_theme">
    <input type="hidden" name="event_id" id="load_theme_event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_load_all_website_theme">
    <input type="hidden" name="event_id" id="load_all_theme_event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_select_event_website_theme">
    <input type="hidden" name="event_id" id="selected_event_id" value="<%=sEventId%>"/>
    <input type="hidden" name="website_theme_id" id="selected_website_theme_id" value=""/>
    <input type="hidden" name="event_website_id" id="current_event_website_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">
    var ThemeThumbnailModel = Backbone.Model.extend({});
    var ThemeThumbnailView = Backbone.View.extend({
        initialize: function(){
            this.varWebsiteThemeModel = this.model.get('bb_website_themes');
            this.varNumOfThemesModel = this.model.get('bb_num_of_website_themes');
            this.varButtonCreatorModel = this.model.get('bb_button_creator');
        },
        render:function(){

            var iNumOfCols = 0;
            var websiteThemeRow = '';
            for(i = 0; i <this.varNumOfThemesModel; i++) {
                if(iNumOfCols == 0 ) {
                    websiteThemeRow = $('<div>').addClass('row');
                    $(this.el).append(websiteThemeRow);
                }
                var websiteThemeCol = createThemeThumbnail( this.varWebsiteThemeModel[i] , this.varButtonCreatorModel )

                websiteThemeRow.append(websiteThemeCol);

                iNumOfCols++;
                if(iNumOfCols == 3) {
                    iNumOfCols = 0;
                    $(this.el).append( createBlankRow() );
                    $(this.el).append( createBlankRow() );
                }
            }
        }
    });
    var CurrentThemeThumbnailView = Backbone.View.extend({
        initialize: function(){
            this.varWebsiteThemeModel = this.model.get('bb_website_themes');
            this.varNumOfThemesModel = this.model.get('bb_num_of_website_themes');
            this.varButtonCreatorModel = this.model.get('bb_button_creator');
            this.varEventWebsiteModel = this.model.get('bb_event_website');
        },
        render:function(){
            if(this.varNumOfThemesModel == 1 ){
                this.createCurrentThemeThumbnail( this.varWebsiteThemeModel[0] )
            }
        },
        createCurrentThemeThumbnail : function(websiteTheme) {
            var websiteThemeRow = $('<div>').addClass('row');
            $(this.el).append(websiteThemeRow);

            var websiteThemeCol = createThemeThumbnail( websiteTheme , this.varButtonCreatorModel, this.varEventWebsiteModel )

            websiteThemeRow.append(websiteThemeCol);

            $(this.el).append( createBlankRow() );
            $(this.el).append( createBlankRow() );
        }
    });

    function generateThemeThumbnailModel( varAllWebsiteThemes , varNumOfThemes , buttonCreator ) {
        this.themeThumbnailModel = new ThemeThumbnailModel();
        this.themeThumbnailModel.set('bb_website_themes' , varAllWebsiteThemes );
        this.themeThumbnailModel.set('bb_num_of_website_themes' , varNumOfThemes );
        this.themeThumbnailModel.set('bb_button_creator' , buttonCreator );
    }

    function generateCurrentThemeThumbnailModel( varAllWebsiteThemes , varNumOfThemes , buttonCreator , varEventWebsite) {
        this.themeThumbnailModel = new ThemeThumbnailModel();
        this.themeThumbnailModel.set('bb_website_themes' , varAllWebsiteThemes );
        this.themeThumbnailModel.set('bb_num_of_website_themes' , varNumOfThemes );
        this.themeThumbnailModel.set('bb_button_creator' , buttonCreator );
        this.themeThumbnailModel.set('bb_event_website' , varEventWebsite );
    }

    function generateAllThemeThumbnail( ) {

        var themeThumbnailView = new ThemeThumbnailView({model:this.themeThumbnailModel});
        themeThumbnailView.render();
        return themeThumbnailView;
    }

    function generateCurrentThemeThumbnail( ) {

        var themeThumbnailView = new CurrentThemeThumbnailView({model:this.themeThumbnailModel});
        themeThumbnailView.render();
        return themeThumbnailView;
    }

    $(window).load(function() {
        loadAllThemes(populateAllThemes);
        loadEventWebsiteThemes(populateCurrentTheme);
    });

    function loadEventWebsiteThemes(callbackmethod) {
        var actionUrl = "/proc_load_event_website_theme.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_event_website_theme").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateCurrentTheme(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;

                    var varNumOfThemes = jsonResponseObj.num_of_themes;
                    if(varNumOfThemes!=undefined && varNumOfThemes>0){
                        var varWebsiteThemes = jsonResponseObj.website_themes;
                        var varEventWebsite = jsonResponseObj.event_website;
                         $('#current_event_website_id').val(varEventWebsite.event_website_id);
                        generateCurrentThemeThumbnailModel(varWebsiteThemes , varNumOfThemes, createCurrentThemeButtons , varEventWebsite);
                        var themeThumbnailView = generateCurrentThemeThumbnail();
                        $('#current_theme').html( themeThumbnailView.el );

                        createCurrentThemeButtonEvents( varWebsiteThemes[0] )
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function loadAllThemes(callbackmethod) {
        var actionUrl = "/proc_load_all_website_themes.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_all_website_theme").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }


    function populateAllThemes(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;

                    var varNumOfThemes = jsonResponseObj.num_of_themes;
                    if(varNumOfThemes!=undefined && varNumOfThemes>0){
                        var varAllWebsiteThemes = jsonResponseObj.website_themes;


                        generateThemeThumbnailModel(varAllWebsiteThemes , varNumOfThemes , createAllThemeButtons);
                        var themeThumbnailView = generateAllThemeThumbnail();
                        $('#select_a_theme').html( themeThumbnailView.el );

                        for(i = 0; i <varNumOfThemes; i++) {
                            createAllThemesButtonEvents( varAllWebsiteThemes[i] )
                        }

                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function createThemeImage( websiteTheme ) {
        var varImg = $('<img>').addClass('img-thumbnail');
        varImg.attr('src', '/com/events/event/website/static_templates/' + websiteTheme.name + '/img/' + websiteTheme.screen  );
        return varImg;
    }

    function createCurrentThemeButtons( websiteTheme , eventWebsite ) {
        return createButton('View Website','view_website_'+eventWebsite.event_website_id) ;
    }
    function createAllThemeButtons( websiteTheme ) {
        var varButtonGroup = $('<div>').addClass('btn-group');
        varButtonGroup.append( createButton('Preview','preview_'+websiteTheme.website_theme_id) );
        varButtonGroup.append( createButton('Select','select_'+websiteTheme.website_theme_id) );
        return varButtonGroup;
    }
    function createThemeName( websiteTheme ){
        var varThemeGroup = $('<h4>').append( websiteTheme.name );
        return varThemeGroup;
    }

    function createThemeThumbnailRow( varRowContents , varStyleContent ){

        var varIndividualThemeColumn = $('<div>').addClass('col-md-12');
        varIndividualThemeColumn.append( varRowContents );

        var varIndividualThemeRow = $('<div>').addClass('row');
        if(varStyleContent!=''){
            varIndividualThemeRow.attr('style',varStyleContent );
        }
        varIndividualThemeRow.append( varIndividualThemeColumn  );

        return varIndividualThemeRow;
    }

    function createThemeThumbnail(websiteTheme , createButtonForTheme , eventWebsite ){
        var varThemeName = createThemeName( websiteTheme );
        var varImg = createThemeImage(websiteTheme);
        var varButtonGroup = createButtonForTheme(websiteTheme , eventWebsite);



        var varIndividualThemeNameRow = createThemeThumbnailRow(varThemeName);
        var varIndividualThemeImgRow = createThemeThumbnailRow(varImg);
        var varIndividualThemeButtonGroupRow = createThemeThumbnailRow(varButtonGroup,'text-align:center;');


        var varColumn = $('<div>').addClass('col-md-4');
        varColumn.append(varIndividualThemeNameRow);
        varColumn.append(varIndividualThemeImgRow);
        varColumn.append( createBlankRow() );
        varColumn.append(varIndividualThemeButtonGroupRow);

        return varColumn;

    }
    function createButton(varText, varId) {
        var varButton = $('<button>').attr('type','button').addClass('btn btn-default btn-xs').attr('id',varId);
        varButton.append( varText );
        return varButton;
    }
    function createBlankRow() {
        var varBlankCol = $('<div>').addClass('col-md-12');
        varBlankCol.append( '&nbsp;' );
        var varBlankRow = $('<div>').addClass('row');
        varBlankRow.append(varBlankCol);
        return varBlankRow;
    }
    function createAllThemesButtonEvents( websiteTheme) {
        if(websiteTheme!=undefined) {
            $('#preview_'+websiteTheme.website_theme_id).click({param_web_theme_obj:websiteTheme},function(e) {
                window.open('static_templates/'+e.data.param_web_theme_obj.name+"/preview.jsp?event_id=<%=sEventId%>","preview_website_theme");
            });
            $('#select_'+websiteTheme.website_theme_id).click({param_web_theme_obj:websiteTheme},function(e) {
                $('#selected_website_theme_id').val(e.data.param_web_theme_obj.website_theme_id);
                selectEventWebsiteTheme(getResult);
            });
        }
    }
    function createCurrentThemeButtonEvents( websiteTheme) {
        if(websiteTheme!=undefined) {
            $('#view_website_'+websiteTheme.website_theme_id).click({param_web_theme_obj:websiteTheme},function(e) {
                window.open('static_templates/'+e.data.param_web_theme_obj.name+"/preview.jsp?event_id=<%=sEventId%>","preview_website_theme");
            });
        }
    }

    function selectEventWebsiteTheme(callbackmethod) {
        var actionUrl = "/proc_save_event_website_theme.aeve";
        var methodType = "POST";
        var dataString = $("#frm_select_event_website_theme").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                displayMssgBoxAlert('The theme was successfully selected.', false);
                loadEventWebsiteThemes(populateCurrentTheme);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
