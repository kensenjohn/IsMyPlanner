<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<style>
    #sortable_chk_list { list-style-type: none;}
</style>
<%
    String sChecklistId = ParseUtil.checkNull(request.getParameter("checklist_id"));
    boolean isLoadChecklist = false;
    if(!Utility.isNullOrEmpty(sChecklistId)){
        isLoadChecklist = true;
    }
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
        <jsp:param name="event_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">CheckList Template</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_checklist_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <form class="form-horizontal" id="frm_save_checklist">
                        <div class="row">
                            <div class="col-md-8">
                                <label for="checklist_name" class="form_label">Checklist Name:</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="checklist_name" name="checklist_name"/>
                            </div>
                        </div>
                        <div class="row" style="display: none;" id="div_checklist_templates">
                            <div class="col-md-8">
                                <label for="checklist_name" class="form_label">Checklist Template:</label>
                                <select class="form-control" id="checklist_templates" name="checklist_template_id">
                                    <option value="">Select a Template</option>
                                </select>
                            </div>
                        </div>
                        <input type="hidden" name="checklist_id" id="checklist_id" value="<%=sChecklistId%>"/>
                        <input type="hidden" name="event_id" id="event_id" value="<%=sEventId%>"/>
                    </form>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <button class="btn btn-filled" id="btn_save_checklist"><i class="fa fa-floppy-o"></i> Save</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row" style="display:none;" id="div_add_checklist_item">
                        <div class="col-md-4">
                            <button class="btn btn-default btn-xs" id="btn_add_checklist_item"><i class="fa fa-plus"></i> Add New Checklist Item</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8">
                            <ul id="sortable_chk_list">

                            </ul>
                        </div>
                    </div>
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
<form id="frm_load_checklist">
    <input type="hidden" name="checklist_id" value="<%=sChecklistId%>"/>
</form>
<form id="frm_load_checklist_template_items">
    <input type="hidden" name="checklist_template_id" id="load_checklist_template_id" value=""/>
</form>
<script id="template_checklist_template_item" type="text/x-handlebars-template">
    <li class="sort_tracker" id="sort_tracker_item_{{item_sequence_number}}" param="{{checklist_template_item_id}}">
        <div class="row chk_list_row" id="row_item_{{checklist_template_item_id}}" param="item{{item_number}}">
            <div class="col-xs-6">
                <span class="chk_list_name">{{item_name}}</span> <span id="div_icon_{{checklist_template_item_id}}" style="display:none"> &nbsp;&nbsp;&nbsp; <i class="fa fa-chevron-down icon_item_details" id="icon_item_{{checklist_template_item_id}}" param="item_{{checklist_template_item_id}}"></i> </span>
            </div>
            <div class="col-xs-12" id="chk_list_details_item_{{checklist_template_item_id}}" style="display:none">
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5">
                        <span style="font-weight: bold;">Todo</span>
                    </div>
                </div>
                <div id="checklist_template_todo_list_{{checklist_template_item_id}}">

                </div>
            </div>
        </div>
    </li>
</script>
<script id="template_checklist_template_item_todo" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-xs-offset-1 col-xs-11">
            <span>{{checklist_template_item_todo_name}}</span>
        </div>
    </div>
</script>
<script id="template_checklist_template_option" type="text/x-handlebars-template">
    <option id="{{checklist_template_id}}" value="{{checklist_template_id}}">{{checklist_template_name}}</option>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    var varIsLoadLoadChecklist = <%=isLoadChecklist%>;
    $(window).load(function() {
        if(varIsLoadLoadChecklist){
            loadChecklist(populateChecklist);
        }else {
            loadChecklistTemplatesSummary(populateChecklistTemplateDropDown);
        }

        $('#btn_save_checklist').bind('click',function(event){
            saveChecklistTemplate(getResult);
        });
    });
    function loadChecklist( callbackmethod ) {
        var actionUrl = "/proc_load_event_checklist.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_checklist").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadChecklistTemplatesSummary( callbackmethod ) {
        var actionUrl = "/proc_load_event_checklist_templates_summary.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_checklist").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadChecklistTemplateAndItems( callbackmethod ) {
        var actionUrl = "/proc_load_checklist_template.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_checklist_template_items").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveChecklistTemplate( callbackmethod ) {
        var actionUrl = "/proc_save_event_checklist.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_checklist").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    /*var varChecklistTemplateBean = jsonResponseObj.checklist_template_bean;
                    $('#checklist_template_id').val( varChecklistTemplateBean.checklist_template_id );
                    $('#div_add_checklist_item').show();

                    createAddItemEvent( varChecklistTemplateBean.checklist_template_id );*/
                }
            }
        }
    }

    function populateChecklistTemplateDropDown(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varNumOfAllChecklistTemplates = jsonResponseObj.num_of_checklist_templates;
                    if(varNumOfAllChecklistTemplates>0){
                        $("#div_checklist_templates").show();
                        var varAllChecklistTemplates = jsonResponseObj.all_checklist_templates;

                        for( var varTemplateCount = 0; varTemplateCount<varNumOfAllChecklistTemplates; varTemplateCount++ ){
                            var varChecklistTemplate = varAllChecklistTemplates[ varTemplateCount ];
                            generateTemplateOption( varChecklistTemplate.checklist_template_id , varChecklistTemplate.name);
                        }

                        $('#checklist_templates').bind('change',function( event ){
                            $("#sortable_chk_list").empty();
                            var varSelectedChecklistTemplate = $('#checklist_templates').val();

                            if(varSelectedChecklistTemplate!=''){
                                $('#load_checklist_template_id').val(  varSelectedChecklistTemplate  )
                                loadChecklistTemplateAndItems( populateTemplateItems );
                            }
                        });
                    }
                }
            }
        }
    }

    function generateTemplateOption(varChecklistTemplateId, varChecklistTemplateName ){
        $('<option>').attr({
            id: varChecklistTemplateId,
            value:varChecklistTemplateId
        }).appendTo('#checklist_templates');
        $('#'+varChecklistTemplateId).text(varChecklistTemplateName);

    }

    function populateTemplateItems( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varChecklistTemplateBean = jsonResponseObj.checklist_template_bean;

                    var varNumberOfItems = jsonResponseObj.num_of_checklist_template_items;
                    if(varNumberOfItems>0){
                        var varNumOfItemWithTodos = jsonResponseObj.num_of_checklist_template_items_with_todos;
                        var varItemsWithTodos = '';
                        if(varNumOfItemWithTodos>0){
                            varItemsWithTodos = jsonResponseObj.items_with_todos
                        }


                        var varAllChecklisTemplateItems = jsonResponseObj.checklist_template_items;
                        for( var varCount = 0; varCount<varNumberOfItems; varCount++ ){

                            var varChecklistTemplateItemBean = varAllChecklisTemplateItems[varCount];
                            var varChecklistTemplateItemId = varChecklistTemplateItemBean.checklist_template_item_id;
                            if(varChecklistTemplateItemBean!=undefined){
                                this.checklistTemplateItemModel = new ChecklistTemplateItemModel({
                                    'bb_item_sequence_number' : varCount,
                                    'bb_checklist_template_item_id' : varChecklistTemplateItemId,
                                    'bb_checklist_template_item_name' : varChecklistTemplateItemBean.name
                                });
                                var checklistTemplateItemView = new ChecklistTemplateItemView({model:this.checklistTemplateItemModel});
                                checklistTemplateItemView.render();
                                $("#sortable_chk_list").append(checklistTemplateItemView.el);


                                if( varItemsWithTodos!='' && varItemsWithTodos!=undefined){
                                    var varItemTodoList =  varItemsWithTodos[ varChecklistTemplateItemId ];

                                    if(varItemTodoList!=undefined){
                                        this.checklistTemplateTodoModel = new ChecklistTemplateTodoModel({
                                            'bb_item_todo_list' : varItemTodoList,
                                            'bb_num_of_todos' : varItemTodoList.num_of_checklist_template_todos,
                                            bb_checklist_template_item_id: varChecklistTemplateItemId
                                        });

                                        var checklistTemplateTodoView = new ChecklistTemplateTodoView({model:this.checklistTemplateTodoModel});
                                        checklistTemplateTodoView.render();
                                        $("#checklist_template_todo_list_"+varChecklistTemplateItemId).append(checklistTemplateTodoView.el);
                                    }
                                }
                            }
                        }
                        createIconEvents();
                    }
                }
            }
        }
    }

    var ChecklistTemplateItemModel = Backbone.Model.extend({
        defaults: {
            bb_item_sequence_number:undefined,
            bb_checklist_template_item_id: undefined,
            bb_checklist_template_item_name: undefined
        }
    });
    var ChecklistTemplateItemView = Backbone.View.extend({
        initialize: function(){
            this.varBBItemSequenceNumber = this.model.get('bb_item_sequence_number');
            this.varBBChecklistTemplateId = this.model.get('bb_checklist_template_item_id');
            this.varBBChecklistTemplateName = this.model.get('bb_checklist_template_item_name');
        },
        template : Handlebars.compile( $('#template_checklist_template_item').html() ),
        render : function() {
            var varTmpChecklistTemplateItemBean = {
                "item_sequence_number" : this.varBBItemSequenceNumber,
                "checklist_template_item_id"  : this.varBBChecklistTemplateId ,
                "item_name"  : this.varBBChecklistTemplateName
            }
            var checklistTemplateItemRow = this.template(  eval( varTmpChecklistTemplateItemBean )  );
            $(this.el).append( checklistTemplateItemRow );
        }
    });

    var ChecklistTemplateTodoModel = Backbone.Model.extend({
        defaults: {
            bb_item_todo_list:undefined,
            bb_num_of_todos: 0,
            bb_checklist_template_item_id: ''
        }
    });
    var ChecklistTemplateTodoView = Backbone.View.extend({
        initialize: function(){
            this.varBBItemTodoList = this.model.get('bb_item_todo_list');
            this.varBBNumOfTodos = this.model.get('bb_num_of_todos');
            this.varBBTodoChecklistTemplateItemId = this.model.get('bb_checklist_template_item_id');
        },
        template : Handlebars.compile( $('#template_checklist_template_item_todo').html() ),
        render : function() {

            if(this.varBBNumOfTodos>0){
                var varChecklistTemplateItemId = this.varBBTodoChecklistTemplateItemId;
                $('#chk_list_details_item_'+varChecklistTemplateItemId).show();
                $('#div_icon_'+varChecklistTemplateItemId).show();
                for( var varTodoCount = 0; varTodoCount<this.varBBNumOfTodos; varTodoCount++ ){
                    var varTodoBean = this.varBBItemTodoList[varTodoCount];
                    if(varTodoBean!=undefined) {
                        var varTmpTodoBean = {
                            "checklist_template_item_todo_name": varTodoBean.name
                        }

                        var checklistTodoRow = this.template(  eval( varTmpTodoBean )  );
                        $(this.el).append( checklistTodoRow );
                    }
                }
            }


        }
    });

    function createIconEvents(){
        $('.icon_item_details').click(function(event){
            var varIconId= event.target.id;
            toggleCollapseIcon(varIconId);

            var varCheckListId = $('#'+varIconId).attr('param');
            openCheckListDetail( varCheckListId )
        });
    }
    function openCheckListDetail( varCheckListId ){
        $('#row_'+varCheckListId).toggleClass("chk_list_row_selected").toggleClass("chk_list_row");
        $('#chk_list_details_'+varCheckListId).slideToggle( 'fast', function() {

        });
    }
    function toggleCollapseIcon(varIcon) {
        $('#'+varIcon).toggleClass("fa-chevron-down").toggleClass("fa-chevron-right");
    }

</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>