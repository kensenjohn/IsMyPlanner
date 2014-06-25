<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sChecklistTemplateItemId = ParseUtil.checkNull(request.getParameter("checklist_template_item_id"));
    String sChecklistTemplateId = ParseUtil.checkNull(request.getParameter("checklist_template_id"));
    boolean isLoadChecklistTemplateItem = false;
    if(!Utility.isNullOrEmpty(sChecklistTemplateItemId)) {
        isLoadChecklistTemplateItem = true;
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
                            <h3>Checklist Template Item for "<span id="checklist_template_name"></span>"</h3>
                        </div>
                    </div>
                    <form class="form-horizontal" id="frm_chklist_template_item">
                        <div class="row">
                            <div class="col-xs-9">
                                <label for="checklist_template_item_name" class="form_label">Checklist Item Name:</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="checklist_template_item_name" name="checklist_template_item_name"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-3">
                                &nbsp;
                            </div>
                        </div>
                        <input type="hidden" name="checklist_template_item_id" id="checklist_template_item_id" value="<%=sChecklistTemplateItemId%>"/>
                        <input type="hidden" name="checklist_template_id" id="checklist_template_id" value="<%=sChecklistTemplateId%>"/>
                    </form>

                    <div class="row">
                        <div class="col-xs-6">
                            <h6>Checklist To Dos &nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-default btn-xs" id="btn_checklist_item_todo"><i class="fa fa-plus"></i> Add ToDo</button></h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-offset-1 col-md-offset-1 col-xs-10  col-md-10 ">
                            <form class="form-horizontal" id="frm_chklist_item_todo">
                                <div class="row">
                                    <div class="col-xs-12 col-md-12" id="div_chklist_item_todo">

                                    </div>
                                </div>
                                <div id="hidden_todo_ids">
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-6">
                            <button class="btn btn-default btn-xs" id="btn_save_checklist_item"><i class="fa fa-floppy-o"></i> Save</button>
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
<form id="frm_delete_checklist_template_todo">
    <input type="hidden" name="checklist_template_todo_id" id="delete_checklist_template_todo_id" value="">
</form>
<script id="template_checklist_todo_row" type="text/x-handlebars-template">
    <div class="row" id="row_todo_{{todo_id}}">
        <div class="col-xs-9  col-md-9">
            <input type="text" class="form-control" id="checklist_item_todo_{{todo_id}}" name="checklist_item_todo_{{todo_id}}" value="{{todo_name}}"/>
        </div>
        <div class="col-xs-3 col-md-3">
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
    <input type="hidden" name="checklist_template_todo_id[]" value="{{checklist_template_todo_id}}"/>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    var varLoadChecklistTemplateItem = <%=isLoadChecklistTemplateItem%>;
    $(window).load(function() {
        $('#btn_checklist_item_todo').click(function(){
            var varChecklistTemplateTodoId = guidGenerator();
            console.log('varChecklistTemplateTodoId : ' + varChecklistTemplateTodoId );

            this.checkListTodoModel = new CheckListTodoModel({
                'bb_checklist_template_todo_id' : varChecklistTemplateTodoId,
                'bb_checklist_template_todo_param' : 'by_client' ,
                'bb_checklist_template_todo_name' : ''
            });

            var checkListTodoView = new CheckListTodoView({model:this.checkListTodoModel});
            checkListTodoView.render();

            $("#div_chklist_item_todo").append(checkListTodoView.el);
            addDeleteTodoEvent( varChecklistTemplateTodoId );
        });

        $('#btn_save_checklist_item').bind('click',function(){
            generateChecklistTodoList();
            saveChecklistTemplateItem( getResult );
        });

        if(varLoadChecklistTemplateItem){
            loadChecklistTemplateItem( populateChecklistTemplateItemDetails );
        }
    });
    function saveChecklistTemplateItem( callbackmethod ) {
        var actionUrl = "/proc_save_checklist_template_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_chklist_template_item").serialize() + '&' + $("#frm_chklist_item_todo").serialize() ;
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadChecklistTemplateItem( callbackmethod ) {
        var actionUrl = "/proc_load_checklist_template_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_chklist_template_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function deleteChecklistTemplateTodo( varChecklistTemplateTodoObj ) {

        $('#delete_checklist_template_todo_id').val(varChecklistTemplateTodoObj.checklist_template_item_todo_id);

        var actionUrl = "/proc_delete_checklist_template_todo.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_checklist_template_todo").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,getDeleteResult);
    }

    function addDeleteTodoEvent( varChecklistTemplateTodoId ){
        var checklist_template_todo_delete_obj = {
            checklist_template_item_todo_id:varChecklistTemplateTodoId
        }

        $('#btn_delete_checklist_item_todo_'+varChecklistTemplateTodoId).click( {param_checklist_template_todo_delete_obj:checklist_template_todo_delete_obj},function(e){
            if( $('#btn_delete_checklist_item_todo_'+varChecklistTemplateTodoId).attr('param') == 'by_client'){

                $('#row_blank_todo_'+varChecklistTemplateTodoId).remove();
                $('#row_todo_'+varChecklistTemplateTodoId).remove();
            } else if(  $('#btn_delete_checklist_item_todo_'+varChecklistTemplateTodoId).attr('param') == 'by_db'  ) {

                displayConfirmBox(
                        "Are you sure you want to delete this Todo?" ,
                        "Delete Checklist Template Todo",
                        "Yes", "No", deleteChecklistTemplateTodo,e.data.param_checklist_template_todo_delete_obj);

            }

        })
    }

    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varChecklistTemplateItemBean = jsonResponseObj.checklist_template_item_bean;
                    $('#checklist_template_item_id').val( varChecklistTemplateItemBean.checklist_template_item_id );
                    displayAjaxOk( varResponseObj );
                }
            }
        }
    }

    function getDeleteResult( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    $('#delete_checklist_template_todo_id').val('');
                    var varIsDeleted = jsonResponseObj.is_deleted;
                    if( varIsDeleted ) {

                        var varChecklistTemplateTodoId = jsonResponseObj.deleted_checklist_Template_todo_id;
                        $('#row_blank_todo_'+varChecklistTemplateTodoId).remove();
                        $('#row_todo_'+varChecklistTemplateTodoId).remove();
                    } else {
                        displayMssgBoxAlert("The todo item was not deleted. Please try again later.", true);
                    }
                }
            }
        }
    }

    function populateChecklistTemplateItemDetails( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varChecklistTemplateBean = jsonResponseObj.checklist_template_bean;
                    $('#checklist_template_name').text( varChecklistTemplateBean.name );

                    var varChecklistTemplateItemBean = jsonResponseObj.checklist_template_item_bean;
                    $('#checklist_template_item_id').val( varChecklistTemplateItemBean.checklist_template_item_id );
                    $('#checklist_template_item_name').val( varChecklistTemplateItemBean.name );

                    var varNumOfTodos = jsonResponseObj.num_of_checklist_template_todos;
                    if(varNumOfTodos>0){
                        var varAllChecklistTemplateTodo = jsonResponseObj.all_checklist_template_todo;

                        for(var varTodoCount = 0; varTodoCount<varNumOfTodos; varTodoCount++){
                            var varChecklistTemplateTodoBean = varAllChecklistTemplateTodo[varTodoCount];

                            this.checkListTodoModel = new CheckListTodoModel({
                                'bb_checklist_template_todo_id' : varChecklistTemplateTodoBean.checklist_template_todo_id,
                                'bb_checklist_template_todo_param' : 'by_db' ,
                                'bb_checklist_template_todo_name' : varChecklistTemplateTodoBean.name
                            });

                            var checkListTodoView = new CheckListTodoView({model:this.checkListTodoModel});
                            checkListTodoView.render();

                            $("#div_chklist_item_todo").append(checkListTodoView.el);
                            addDeleteTodoEvent( varChecklistTemplateTodoBean.checklist_template_todo_id );
                        }

                    }
                }
            }
        }
    }

    function generateChecklistTodoList(){
        $('#hidden_todo_ids').empty();
        if(arrayChecklistTodoId!=undefined){
            for ( var i = 0; i < arrayChecklistTodoId.length; i = i + 1 ) {
                var varHiddenTodoId = arrayChecklistTodoId[i];


                this.hiddenCheckListTodoModel = new HiddenCheckListTodoModel({
                    'bb_hidden_todo_id' : varHiddenTodoId
                });

                var hiddenCheckListTodoView = new HiddenCheckListTodoView({model:this.hiddenCheckListTodoModel});
                hiddenCheckListTodoView.render();

                $("#hidden_todo_ids").append(hiddenCheckListTodoView.el);
            }
        }

    }
    var HiddenCheckListTodoModel = Backbone.Model.extend({
        defaults: {
            bb_hidden_todo_id:''
        }
    });
    var HiddenCheckListTodoView = Backbone.View.extend({
        initialize: function(){
            this.varBBHiddenTodoId = this.model.get('bb_hidden_todo_id');
        },
        template : Handlebars.compile( $('#template_hidden_checklist_todo_row').html() ),
        render:function(){
            var varTmpHiddenToDoBean = {
                "checklist_template_todo_id"  : this.varBBHiddenTodoId
            }
            var hiddenTodoRow = this.template(  eval(varTmpHiddenToDoBean)  );
            $(this.el).append( hiddenTodoRow );
        }
    });

    var arrayChecklistTodoId =  [];
    function guidGenerator() {
        var S4 = function() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        };
        return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
    }

    var CheckListTodoModel = Backbone.Model.extend({
        defaults: {
            bb_checklist_template_todo_id:'',
            bb_checklist_template_todo_param:'',
            bb_checklist_template_todo_name:''
        }
    });
    var CheckListTodoView = Backbone.View.extend({
        initialize: function(){
            this.varBBChecklistTemplateTodoId = this.model.get('bb_checklist_template_todo_id');
            this.varBBChecklistTemplateTodoParam = this.model.get('bb_checklist_template_todo_param');
            this.varBBChecklistTemplateTodoName = this.model.get('bb_checklist_template_todo_name');
        },
        template : Handlebars.compile( $('#template_checklist_todo_row').html() ),
        render:function(){
            var varTmpToDoBean = {
                "todo_id"  : this.varBBChecklistTemplateTodoId,
                "todo_type" : this.varBBChecklistTemplateTodoParam   ,
                "todo_name" : this.varBBChecklistTemplateTodoName
            }
            var todoRow = this.template(  eval(varTmpToDoBean)  );
            $(this.el).append( todoRow );

            arrayChecklistTodoId.push( varTmpToDoBean.todo_id );
        }
    } );
</script>