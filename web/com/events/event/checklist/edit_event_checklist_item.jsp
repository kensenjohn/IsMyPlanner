<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/bootstrap-switch.min.css" rel="stylesheet">
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventChecklistItemId = ParseUtil.checkNull(request.getParameter("event_checklist_item_id"));
    String sEventChecklistId = ParseUtil.checkNull(request.getParameter("event_checklist_id"));
    boolean isLoadEventChecklistItem = false;
    if(!Utility.isNullOrEmpty(sEventChecklistItemId)) {
        isLoadEventChecklistItem = true;
    }
%>
<body>
<div class="page_wrap">
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <h3>Checklist Template Item for "<span id="event_checklist_name"></span>"</h3>
                        </div>
                    </div>
                    <form class="form-horizontal" id="frm_event_checklist_item">
                        <div class="row">
                            <div class="col-xs-9">
                                <label for="event_checklist_item_name" class="form_label">Checklist Item Name:</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="event_checklist_item_name" name="event_checklist_item_name"/>
                            </div>
                            <div class="col-xs-3">
                                <label for="event_checklist_item_name" class="form_label">&nbsp;</label><br>
                                <input type="checkbox" data-size="mini" data-on-text="Done" data-off-text="Active" class="hide-page" id="update_item_status_<%=sEventChecklistItemId%>" name="update_item_status_<%=sEventChecklistItemId%>">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-3">
                                &nbsp;
                            </div>
                        </div>
                        <input type="hidden" name="event_checklist_item_id" id="event_checklist_item_id" value="<%=sEventChecklistItemId%>"/>
                        <input type="hidden" name="event_checklist_id" id="event_checklist_id" value="<%=sEventChecklistId%>"/>
                    </form>

                    <div class="row">
                        <div class="col-xs-6">
                            <h6>Checklist Tasks &nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-default btn-xs" id="btn_event_checklist_item_todo"><i class="fa fa-plus"></i> Add Task</button></h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-offset-1 col-md-offset-1 col-xs-10  col-md-10 ">
                            <form class="form-horizontal" id="frm_chklist_item_todo">
                                <div class="row">
                                    <div class="col-xs-12 col-md-12" id="div_event_checklist_item_todo">

                                    </div>
                                </div>
                                <div id="hidden_todo_ids">
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-6">
                            <button class="btn btn-default btn-xs" id="btn_save_event_checklist_item"><i class="fa fa-floppy-o"></i> Save</button>
                            &nbsp;&nbsp;&nbsp;
                            <button class="btn btn-default btn-xs" id="btn_close_checklist_item"> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script id="template_event_checklist_todo_row" type="text/x-handlebars-template">
    <div class="row" id="row_todo_{{todo_id}}">
        <div class="col-xs-9  col-md-9">
            <input type="text" class="form-control" id="checklist_item_todo_{{todo_id}}" name="checklist_item_todo_{{todo_id}}" value="{{todo_name}}"/>
        </div>
        <div class="col-xs-3 col-md-3">
            <input type="checkbox" data-size="mini" data-on-text="Done" data-off-text="Active" class="hide-page"  id="update_event_checklist_item_todo_{{todo_id}}" name="update_event_checklist_item_todo_{{todo_id}}" >
            &nbsp;&nbsp;
            <button type="button" class="btn btn-default btn-xs" id="btn_delete_checklist_item_todo_{{todo_id}}" name="delete_todo" param="{{todo_type}}"><i class="fa fa-trash-o"></i> </button>
        </div>
    </div>
    <div class="row" id="row_blank_todo_{{todo_id}}">
        <div class="col-xs-12 col-md-12">
            &nbsp;
        </div>
    </div>
</script>
<script id="template_hidden_checklist_todo_row" type="text/x-handlebars-template">
    <input type="hidden" name="event_checklist_todo_id[]" value="{{event_checklist_todo_id}}"/>
    <input type="hidden" name="todo_action_{{event_checklist_todo_id}}" value="{{todo_action}}"/>
</script>
<form id="frm_delete_event_checklist_todo">
    <input type="hidden" name="event_checklist_todo_id" id="delete_event_checklist_todo_id" value=""/>
</form>
<form id="frm_update_item_action">
    <input type="hidden" name="item_action" id="item_action" value=""/>
    <input type="hidden" name="event_checklist_item_id" id="update_event_checklist_item_id" value="<%=sEventChecklistItemId%>"/>
</form>
<form id="frm_update_todo_action">
    <input type="hidden" name="todo_action" id="todo_action" value=""/>
    <input type="hidden" name="event_checklist_todo_id" id="update_event_checklist_todo_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="/js/bootstrap-switch.min.js"></script>
<script   type="text/javascript">
    var varIsLoadLoadChecklistItem= <%=isLoadEventChecklistItem%>;
    var varEventChecklistItemId= '<%=sEventChecklistItemId%>';
    $(window).load(function() {
        $('#update_item_status_'+varEventChecklistItemId).bootstrapSwitch('size', 'mini');
        $('#update_item_status_'+varEventChecklistItemId).bootstrapSwitch('readonly', false);

        $('#update_item_status_'+varEventChecklistItemId).on('switchChange', function (e, data) {
            var $element = $(data.el);
            var value = data.value;

            if($element !=undefined && value!=undefined ) {
                if(value == false) {
                    $('#item_action').val( 'active' );
                } else {
                    $('#item_action').val( 'done' );
                }
                updateEventChecklistItemAction(getItemActionResult);
            }
        });

        $('#btn_event_checklist_item_todo').click(function(){
            var varTodoId = guidGenerator();
            var varTodoParam = 'by_client';
            var varTodoText = '';
            createTodoRow(varTodoId, varTodoParam, varTodoText, false)
        });

        if(varIsLoadLoadChecklistItem){
            loadEventChecklistItem(populateEventChecklistItem);
        }

        $('#btn_save_event_checklist_item').bind('click',function(){
            generateChecklistTodoList();
            saveChecklistTemplateItem( getSaveItemResult );
        });
    });

    function getSaveItemResult(  jsonResult  ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varEventChecklistTemplateItemBean = jsonResponseObj.event_checklist_item_bean;
                    $('#event_checklist_item_id').val( varEventChecklistTemplateItemBean.event_checklist_item_id );
                    displayAjaxOk( varResponseObj );
                } else {
                    displayMssgBoxAlert('We were unable to process your request. Please try again later. (saveItem-002)', true);
                }
            }
        } else {
            displayMssgBoxAlert('We were unable to process your request. Please try again later (saveItem-001).', true);
        }
    }

    function createTodoRow(varTodoId, varTodoParam, varTodoText, varTodoActionStatus ){


        this.eventCheckListTodoModel = new EventCheckListTodoModel({
            'bb_event_checklist_todo_id' : varTodoId,
            'bb_event_checklist_todo_param' : varTodoParam ,
            'bb_event_checklist_todo_name' : varTodoText
        });

        var eventCheckListTodoView = new EventCheckListTodoView({model:this.eventCheckListTodoModel});
        eventCheckListTodoView.render();

        $("#div_event_checklist_item_todo").append(eventCheckListTodoView.el);

        $('#update_event_checklist_item_todo_'+varTodoId).bootstrapSwitch('state', varTodoActionStatus );
        $('#update_event_checklist_item_todo_'+varTodoId).bootstrapSwitch('size', 'mini');
        $('#update_event_checklist_item_todo_'+varTodoId).bootstrapSwitch('readonly', false);


        addDeleteTodoEvent( varTodoId );
        addTodoActionEvent( varTodoId );
    }

    function saveChecklistTemplateItem( varCallback ){
        var actionUrl = "/proc_save_event_checklist_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_chklist_item_todo").serialize() +'&'+ $("#frm_event_checklist_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,varCallback);
    }


    function updateEventChecklistTodoAction( varCallback ) {
        var actionUrl = "/proc_update_event_checklist_todo_action.aeve";
        var methodType = "POST";
        var dataString = $("#frm_update_todo_action").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,varCallback);
    }
    function getTodoActionResult(  jsonResult  ){
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


    function updateEventChecklistItemAction( varCallback ) {
        var actionUrl = "/proc_update_event_checklist_item_action.aeve";
        var methodType = "POST";
        var dataString = $("#frm_update_item_action").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,varCallback);
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

    function addDeleteTodoEvent( varEventChecklistTodoId ){
        var event_checklist_todo_delete_obj = {
            event_checklist_item_todo_id:varEventChecklistTodoId
        }

        $('#btn_delete_checklist_item_todo_'+varEventChecklistTodoId).click( {param_event_checklist_todo_delete_obj:event_checklist_todo_delete_obj},function(e){
            if( $('#btn_delete_checklist_item_todo_'+varEventChecklistTodoId).attr('param') == 'by_client'){

                $('#row_blank_todo_'+varEventChecklistTodoId).remove();
                $('#row_todo_'+varEventChecklistTodoId).remove();
            } else if(  $('#btn_delete_checklist_item_todo_'+varEventChecklistTodoId).attr('param') == 'by_db'  ) {

                displayConfirmBox(
                        "Are you sure you want to delete this task?" ,
                        "Delete Event Checklist Task",
                        "Yes", "No", deleteEventChecklistTodo,e.data.param_event_checklist_todo_delete_obj);

            }

        })
    }
    function deleteEventChecklistTodo( varEventChecklistTodoObj ) {

        $('#delete_event_checklist_todo_id').val(varEventChecklistTodoObj.event_checklist_item_todo_id);

        var actionUrl = "/proc_delete_event_checklist_todo.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_event_checklist_todo").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,getDeleteResult);
    }



    function getDeleteResult( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    $('#delete_event_checklist_todo_id').val('');
                    var varIsDeleted = jsonResponseObj.is_deleted;
                    if( varIsDeleted ) {

                        var varEventChecklistTodoId = jsonResponseObj.deleted_event_checklist_todo_id;
                        $('#row_blank_todo_'+varEventChecklistTodoId).remove();
                        $('#row_todo_'+varEventChecklistTodoId).remove();
                    } else {
                        displayMssgBoxAlert("The task was not deleted. Please try again later.", true);
                    }
                }
            }
        }
    }

    var arrayChecklistTodoId =  [];
    function guidGenerator() {
        var S4 = function() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        };
        return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
    }

    function loadEventChecklistItem( callbackmethod ) {
        var actionUrl = "/proc_load_event_checklist_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_event_checklist_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateEventChecklistItem( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varEventChecklistBean = jsonResponseObj.event_checklist_bean;
                    $('#event_checklist_name').text( varEventChecklistBean.name );

                    var varNumOfEventChecklistItem = jsonResponseObj.num_of_event_checklist_items;
                    if(varNumOfEventChecklistItem>0){
                        var varAllEventChecklistItems = jsonResponseObj.event_checklist_items;

                        var varEventChecklistItemBean = varAllEventChecklistItems[0];
                        var varEventChecklistItemId = varEventChecklistItemBean.event_checklist_item_id;
                        $('#event_checklist_item_id').val(  varEventChecklistItemId  );
                        $('#event_checklist_item_name').val( varEventChecklistItemBean.name );
                        $('#update_item_status_'+varEventChecklistItemId).bootstrapSwitch('state', varEventChecklistItemBean.is_complete );

                        var varNumItemsWithTodo = jsonResponseObj.num_of_event_checklist_items_with_todos;
                        if(varNumItemsWithTodo>0){
                            var varItemsWithTodo = jsonResponseObj.items_with_todos;

                            var varAllEventChecklistItemTodos = varItemsWithTodo[varEventChecklistItemId];
                            var varNumOfTodos = varAllEventChecklistItemTodos.num_of_event_checklist_todos;
                            for(var varTodoCount = 0; varTodoCount<varNumOfTodos; varTodoCount++){
                                var varEventChecklistTodoBean = varAllEventChecklistItemTodos[varTodoCount];
                                var varTodoId = varEventChecklistTodoBean.event_checklist_todo_id;
                                var varTodoParam = 'by_db';
                                var varTodoText = varEventChecklistTodoBean.name;
                                createTodoRow(varTodoId, varTodoParam, varTodoText, varEventChecklistTodoBean.is_complete );
                            }
                        }
                    }
                }
            }
        }
    }
    function addTodoActionEvent(varTodoId){
        $('#update_event_checklist_item_todo_'+varTodoId).on('switchChange', function (e, data) {
            var $element = $(data.el);
            var value = data.value;
            if($element !=undefined && value!=undefined ) {
                if(value == false) {
                    $('#todo_action').val( 'active' );
                } else {
                    $('#todo_action').val( 'done' );
                }
                $('#update_event_checklist_todo_id').val(varTodoId);
                updateEventChecklistTodoAction(getTodoActionResult);
            }
        });
    }
    var EventCheckListTodoModel = Backbone.Model.extend({
        defaults: {
            bb_event_checklist_todo_id:'',
            bb_event_checklist_todo_param:'',
            bb_event_checklist_todo_name:''
        }
    });
    var EventCheckListTodoView = Backbone.View.extend({
        initialize: function(){
            this.varBBEventChecklistTodoId = this.model.get('bb_event_checklist_todo_id');
            this.varBBEventChecklistTodoParam = this.model.get('bb_event_checklist_todo_param');
            this.varBBEventChecklistTodoName = this.model.get('bb_event_checklist_todo_name');
        },
        template : Handlebars.compile( $('#template_event_checklist_todo_row').html() ),
        render:function(){
            var varTmpToDoBean = {
                "todo_id"  : this.varBBEventChecklistTodoId,
                "todo_type" : this.varBBEventChecklistTodoParam,
                "todo_name" : this.varBBEventChecklistTodoName
            }
            var todoRow = this.template(  eval(varTmpToDoBean)  );
            $(this.el).append( todoRow );
            //id="update_event_checklist_item_todo_{{todo_id}}"


            arrayChecklistTodoId.push( varTmpToDoBean.todo_id );
        }
    } );


    function generateChecklistTodoList(){
        $('#hidden_todo_ids').empty();
        if(arrayChecklistTodoId!=undefined){
            for ( var i = 0; i < arrayChecklistTodoId.length; i = i + 1 ) {
                var varHiddenTodoId = arrayChecklistTodoId[i];
                var varTodoAction = $("#update_event_checklist_item_todo_"+varHiddenTodoId).bootstrapSwitch('state');

                this.hiddenCheckListTodoModel = new HiddenCheckListTodoModel({
                    'bb_hidden_todo_id' : varHiddenTodoId,
                    'bb_hidden_todo_action' : varTodoAction
                });

                var hiddenCheckListTodoView = new HiddenCheckListTodoView({model:this.hiddenCheckListTodoModel});
                hiddenCheckListTodoView.render();

                $("#hidden_todo_ids").append(hiddenCheckListTodoView.el);
            }
        }

    }
    var HiddenCheckListTodoModel = Backbone.Model.extend({
        defaults: {
            bb_hidden_todo_id:'',
            bb_hidden_todo_action:''
        }
    });
    var HiddenCheckListTodoView = Backbone.View.extend({
        initialize: function(){
            this.varBBHiddenTodoId = this.model.get('bb_hidden_todo_id');
            this.varBBHiddenTodoAction = this.model.get('bb_hidden_todo_action');
        },
        template : Handlebars.compile( $('#template_hidden_checklist_todo_row').html() ),
        render:function(){
            var varTmpHiddenToDoBean = {
                "event_checklist_todo_id"  : this.varBBHiddenTodoId,
                "todo_action" : this.varBBHiddenTodoAction
            }
            var hiddenTodoRow = this.template(  eval(varTmpHiddenToDoBean)  );
            $(this.el).append( hiddenTodoRow );
        }
    });
</script>