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
    String sChecklistId = ParseUtil.checkNull(request.getParameter("event_checklist_id"));
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
    <input type="hidden" name="checklist_id" id="load_event_checklist_id" value="<%=sChecklistId%>"/>
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
<script id="template_event_checklist_item" type="text/x-handlebars-template">
    <li class="sort_tracker" id="sort_tracker_event_checklist_item_{{item_sequence_number}}" param="{{event_checklist_item_id}}">
        <div class="row chk_list_row" id="row_event_checklist_item_{{event_checklist_item_id}}" param="event_checklist_item{{item_number}}">
            <div class="col-xs-6">
                <h5><input type="checkbox" id="update_checklist_item_{{event_checklist_item_id}}" name="event_checklist_item_status" value="{{event_checklist_item_id}}">&nbsp;&nbsp;<span class="chk_list_name">{{event_checklist_item_name}}</span> &nbsp;&nbsp;&nbsp; <i class="fa fa-chevron-right icon_item_details" id="icon_event_checklist_item_{{event_checklist_item_id}}" param="item_{{event_checklist_item_id}}"></i> </h5>
            </div>
            <div class="col-xs-6">
                <h5><button class="btn btn-default btn-xs" id="edit_event_checklist_item_{{event_checklist_item_id}}"><i class="fa fa-pencil"></i> Edit</button>
                &nbsp;&nbsp;&nbsp;
                <button class="btn btn-default btn-xs" id="delete_event_checklist_item_{{event_checklist_item_id}}"><i class="fa fa-trash-o"></i> Delete</button></h5>
            </div>
            <div class="col-xs-12" id="chk_list_details_item_{{event_checklist_item_id}}"  style="display:none">
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5">
                        <span style="font-weight: bold;">Tasks</span>
                    </div>
                </div>
                <div id="event_checklist_todo_list_{{event_checklist_item_id}}">

                </div>
            </div>
        </div>
    </li>
</script>
<script id="template_event_checklist_item_todo" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-xs-offset-1 col-xs-11">
            <span>{{event_checklist_item_todo_name}}</span>
        </div>
    </div>
</script>

<form id="frm_delete_event_checklist_item">
    <input type="hidden" id="delete_event_checklist_id" name="event_checklist_id"/>
    <input type="hidden" id="delete_event_checklist_item_id" name="event_checklist_item_id"/>
    <input type="hidden" id="delete_event_checklist_item_row_number" name="event_checklist_item_row_number"/>
</form>
<form id="frm_sort_event_checklist_item">
    <input type="hidden" name="event_checklist_id" id="sort_event_checklist_id" value="<%=sChecklistId%>"/>.
    <input type="hidden" name="num_of_items" id="sort_num_of_items" value=""/>
</form>
<form id="frm_update_item_action">
    <input type="hidden" name="item_action" id="item_action" value=""/>
    <input type="hidden" name="event_checklist_item_id" id="update_event_checklist_item_id" value=""/>
</form>
<script id="template_checklist_item_sequence" type="text/x-handlebars-template">
    <input type="hidden" name="{{sort_sequence_number}}" class="hidden_item_sequence" value="{{event_checklist_item_id}}"/>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script src="/js/jquery.ui.touch-punch.min.js"></script>
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

                    var varEventChecklistBean = jsonResponseObj.event_checklist_bean;
                    var varEventChecklistId =  varEventChecklistBean.event_checklist_id;
                    $('#event_checklist_id').val( varEventChecklistId );
                    $('#load_event_checklist_id').val( varEventChecklistId );
                    if(varIsLoadLoadChecklist == false ){
                        varIsLoadLoadChecklist = true;
                        $("#sortable_chk_list").empty();
                        $("#div_checklist_templates").hide();
                        activateAddItemButton( varEventChecklistId );
                        loadChecklist(populateChecklist);
                    }

                }
            }
        }
    }
    function activateAddItemButton(varEventChecklistId){
        $('#div_add_checklist_item').show();
        $('#btn_add_checklist_item').unbind('click');
        $('#btn_add_checklist_item').bind('click',function(){
            $.colorbox({
                href:'edit_event_checklist_item.jsp?event_checklist_id='+varEventChecklistId,
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    $("#sortable_chk_list").empty();
                    loadChecklist(populateChecklist);
                }});
        });
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
    function populateChecklist( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    // var varNumOfAllChecklistTemplates = jsonResponseObj.num_of_checklist_templates;
                    var varEventChecklistBean = jsonResponseObj.event_checklist_bean;
                    var varEventChecklistId = varEventChecklistBean.event_checklist_id;
                    $('#checklist_name').val( varEventChecklistBean.name );

                    var varNumberOfItems = jsonResponseObj.num_of_event_checklist_items;
                    if(varNumberOfItems>0){
                        var varNumOfItemWithTodos = jsonResponseObj.num_of_event_checklist_items_with_todos;
                        var varItemsWithTodos = '';
                        if(varNumOfItemWithTodos>0){
                            varItemsWithTodos = jsonResponseObj.items_with_todos
                        }
                    }


                    var varEventChecklisItems = jsonResponseObj.event_checklist_items;
                    for( var varCount = 0; varCount<varNumberOfItems; varCount++ ){

                        var varEventChecklistItemBean = varEventChecklisItems[varCount];
                        var varEventChecklistItemId = varEventChecklistItemBean.event_checklist_item_id;
                        if(varEventChecklistItemBean!=undefined){
                            this.eventChecklistTemplateItemModel = new EventChecklistTemplateItemModel({
                                'bb_event_checklist_item_sequence_number' : varCount,
                                'bb_event_checklist_item_id' : varEventChecklistItemId,
                                'bb_event_checklist_item_name' : varEventChecklistItemBean.name
                            });
                            var eventChecklistTemplateItemView = new EventChecklistTemplateItemView({model:this.eventChecklistTemplateItemModel});
                            eventChecklistTemplateItemView.render();
                            $("#sortable_chk_list").append(eventChecklistTemplateItemView.el);

                            if(varEventChecklistItemBean.is_complete){
                                $('#update_checklist_item_'+varEventChecklistItemId).prop('checked', true )
                            }

                            createEditItemEvent(varEventChecklistItemBean.event_checklist_id,varEventChecklistItemBean.event_checklist_item_id);
                            addEventChecklistItemDeleteClickEvent(varEventChecklistItemBean.event_checklist_id,varEventChecklistItemBean.event_checklist_item_id, varCount);
                            updateEventChecklistItemAction(varEventChecklistItemBean.event_checklist_item_id, getItemActionResult );

                            if( varItemsWithTodos!='' && varItemsWithTodos!=undefined){
                                var varItemTodoList =  varItemsWithTodos[ varEventChecklistItemId ];

                                if(varItemTodoList!=undefined){

                                    this.eventChecklistTodoModel = new EventChecklistTodoModel({
                                        'bb_item_todo_list' : varItemTodoList,
                                        'bb_num_of_todos' : varItemTodoList.num_of_event_checklist_todos
                                    });

                                    var eventChecklistTodoView = new EventChecklistTodoView({model:this.eventChecklistTodoModel});
                                    eventChecklistTodoView.render();
                                    $("#event_checklist_todo_list_"+varEventChecklistItemId).append(eventChecklistTodoView.el);
                                }
                            }
                        }

                    }

                    createIconEvents();
                    activateAddItemButton(varEventChecklistId);
                    $( "#sortable_chk_list" ).sortable( {
                        stop: function( event, ui ) {
                            finalizeSortChecklist( true );
                        }
                    });
                }
            }
        }
    }

    function updateEventChecklistItemAction( varEventChecklistItemId , varCallback ) {

        $('#update_checklist_item_'+varEventChecklistItemId).bind('change', function(event){

            console.log( 'update_checklist_item_:'+ $('#update_checklist_item_'+varEventChecklistItemId).val()  )

            var varIsChecked = $('#update_checklist_item_'+varEventChecklistItemId).prop('checked');
            console.log( 'varIsChecked:'+ varIsChecked );
            if( varIsChecked == true ){
                $('#item_action').val('done');
            } else {
                $('#item_action').val('active');
            }
            console.log( 'item_action:'+ $('#item_action').val() );
            $('#update_event_checklist_item_id').val( varEventChecklistItemId );
            var actionUrl = "/proc_update_event_checklist_item_action.aeve";
            var methodType = "POST";
            var dataString = $("#frm_update_item_action").serialize();
            makeAjaxCall(actionUrl,dataString,methodType,varCallback);
        })

    }
    function getItemActionResult(  jsonResult  ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varIsActionUpdated = jsonResponseObj.is_action_updated;
                    if( varIsActionUpdated ) {

                    } else {
                        var varCurrentAction = jsonResponseObj.current_action;
                        if(varCurrentAction == 'active'){

                        } else if(varCurrentAction == 'done'){

                        }
                        displayMssgBoxAlert("We were unable to process your request. Please refresh and try again later.", true);
                    }
                }
            }
        }
    }
    function addEventChecklistItemDeleteClickEvent(varEventChecklistId,varEventChecklistItemId, varRowNumber) {
        var event_checklist_item_delete_obj = {
            event_checklist_id: varEventChecklistId,
            event_checklist_item_id: varEventChecklistItemId,
            event_checklist_item_row_number:varRowNumber
        }
        $('#delete_event_checklist_item_'+varEventChecklistItemId).click({param_event_checklist_item_delete_obj:event_checklist_item_delete_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this item?" ,
                    "Delete Checklist Item",
                    "Yes", "No", deleteEventChecklistItem,e.data.param_event_checklist_item_delete_obj);
        });
    }

    function deleteEventChecklistItem( varEventChecklistItemObj){
        $('#delete_event_checklist_id').val(varEventChecklistItemObj.event_checklist_id);
        $('#delete_event_checklist_item_id').val(varEventChecklistItemObj.event_checklist_item_id);
        $('#delete_event_checklist_item_row_number').val(varEventChecklistItemObj.event_checklist_item_row_number);

        var actionUrl = "/proc_delete_event_checklist_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_event_checklist_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processEventChecklistItemDeletion);
    }
    function processEventChecklistItemDeletion (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined){
                        var varIsEventChecklistItemDeleted = jsonResponseObj.is_deleted;

                        if(varIsEventChecklistItemDeleted){
                            var varEventChecklistItemId = jsonResponseObj.deleted_event_checklist_item_id;
                            $('#delete_event_checklist_id').val('');
                            $('#delete_event_checklist_item_id').val('');
                            $('#delete_event_checklist_item_row_number').val('');

                            var varEventChecklistItemRowNum = jsonResponseObj.row_number;
                            $('#sort_tracker_event_checklist_item_'+varEventChecklistItemRowNum).remove();

                        } else {
                            displayMssgBoxAlert("The checklist item was not deleted. Please try again later.", true);
                        }
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteRegistry - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteRegistry - 2)", true);
        }
    }

    function createEditItemEvent(varEventChecklistId, varEventChecklistItemId ){
        $('#edit_event_checklist_item_'+varEventChecklistItemId).unbind('click');
        $('#edit_event_checklist_item_'+varEventChecklistItemId).bind('click',function(){
            $.colorbox({
                href:'edit_event_checklist_item.jsp?event_checklist_id='+varEventChecklistId+'&event_checklist_item_id='+varEventChecklistItemId,
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    $("#sortable_chk_list").empty();
                    loadChecklist(populateChecklist);
                }});
        });
    }

    var EventChecklistTodoModel = Backbone.Model.extend({
        defaults: {
            bb_item_todo_list:undefined,
            bb_num_of_todos: 0
        }
    });
    var EventChecklistTodoView = Backbone.View.extend({
        initialize: function(){
            this.varBBEventChecklistItemTodoList = this.model.get('bb_item_todo_list');
            this.varBBEventChecklistNumOfTodos = this.model.get('bb_num_of_todos');
        },
        template : Handlebars.compile( $('#template_event_checklist_item_todo').html() ),
        render : function() {

            if(this.varBBEventChecklistNumOfTodos>0){
                for( var varTodoCount = 0; varTodoCount<this.varBBEventChecklistNumOfTodos; varTodoCount++ ){
                    var varTodoBean = this.varBBEventChecklistItemTodoList[varTodoCount];

                    var varTmpTodoBean = {
                        "event_checklist_item_todo_id": varTodoBean.event_checklist_todo_id,
                        "event_checklist_item_todo_name": varTodoBean.name
                    }

                    var checklistTodoRow = this.template(  eval( varTmpTodoBean )  );
                    $(this.el).append( checklistTodoRow );
                }
            }


        }
    });

    var EventChecklistTemplateItemModel = Backbone.Model.extend({
        defaults: {
            bb_event_checklist_item_sequence_number:undefined,
            bb_event_checklist_item_id: undefined,
            bb_event_checklist_item_name: undefined
        }
    });
    var EventChecklistTemplateItemView = Backbone.View.extend({
        initialize: function(){
            this.varBBEventChecklistItemSequenceNumber = this.model.get('bb_event_checklist_item_sequence_number');
            this.varBBEventChecklistItemId = this.model.get('bb_event_checklist_item_id');
            this.varBBEventChecklistItemName = this.model.get('bb_event_checklist_item_name');
        },
        template : Handlebars.compile( $('#template_event_checklist_item').html() ),
        render : function() {
            var varTmpEventChecklistItemBean = {
                "item_sequence_number" : this.varBBEventChecklistItemSequenceNumber,
                "event_checklist_item_id"  : this.varBBEventChecklistItemId ,
                "event_checklist_item_name"  : this.varBBEventChecklistItemName
            }
            var eventChecklistItemRow = this.template(  eval( varTmpEventChecklistItemBean )  );
            $(this.el).append( eventChecklistItemRow );
        }
    });

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


    var arrFinalSortedChkList = Array();
    function finalizeSortChecklist( varExecuteSortDB){
        var chkListElement = $('#sortable_chk_list').find('.sort_tracker');

        var arrSortedChkList = Array();
        $('#sortable_chk_list .sort_tracker').each(function(){
            arrSortedChkList.push($(this).attr('id').replace('sort_tracker_event_checklist_item_', ''));
        })

        var resetSortArray = false;
        for (var trackArrayIndex = 0; trackArrayIndex < arrSortedChkList.length; trackArrayIndex++ ) {
            if( arrFinalSortedChkList[trackArrayIndex] != arrSortedChkList[trackArrayIndex]){
                resetSortArray = true;
            }
        }
        if(resetSortArray && varExecuteSortDB){
            arrFinalSortedChkList = arrSortedChkList;

            $('.hidden_item_sequence').remove();
            for (var trackArrayIndex = 0; trackArrayIndex < arrFinalSortedChkList.length; trackArrayIndex++ ) {
                if( arrFinalSortedChkList[trackArrayIndex] != arrFinalSortedChkList[trackArrayIndex]){
                    resetSortArray = true;
                }
                this.sortChecklistTemplateItemModel = new SortChecklistTemplateItemModel({
                    'bb_sort_sequence_number' : arrFinalSortedChkList[trackArrayIndex],
                    'bb_event_checklist_item_id' : $('#sort_tracker_event_checklist_item_' + trackArrayIndex ).attr('param')
                });

                var sortChecklistTemplateItemView = new SortChecklistTemplateItemView({model:this.sortChecklistTemplateItemModel});
                sortChecklistTemplateItemView.render();
                $("#frm_sort_event_checklist_item").append(sortChecklistTemplateItemView.el);
            }
            $('#sort_num_of_items').val( arrFinalSortedChkList.length );
            sortEventChecklistItem( getSortResult )
        }
    }
    //
    var SortChecklistTemplateItemModel = Backbone.Model.extend({
        defaults: {
            bb_sort_sequence_number:undefined,
            bb_event_checklist_item_id: undefined
        }
    });
    var SortChecklistTemplateItemView = Backbone.View.extend({
        initialize: function(){
            this.varBBSortSequenceNumber = this.model.get('bb_sort_sequence_number');
            this.varBBEventChecklistId = this.model.get('bb_event_checklist_item_id');
        },
        template : Handlebars.compile( $('#template_checklist_item_sequence').html() ),
        render : function() {
            var varTmpSortItemBean = {
                "sort_sequence_number" : this.varBBSortSequenceNumber,
                "event_checklist_item_id"  : this.varBBEventChecklistId
            }
            var inputHiddenItemRow = this.template(  eval( varTmpSortItemBean )  );
            $(this.el).append( inputHiddenItemRow );
        }
    });

    function sortEventChecklistItem( callbackmethod ) {
        var actionUrl = "/proc_sort_event_checklist_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_sort_event_checklist_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getSortResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {

                }
            }
        }
    }


</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>