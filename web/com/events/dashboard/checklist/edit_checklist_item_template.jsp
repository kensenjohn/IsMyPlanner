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
                            <h3>Checklist Item Template</h3>
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
                    <form class="form-horizontal" id="frm_chklist_item_todo">
                        <div class="row">
                            <div class="col-xs-9 col-md-9" id="div_chklist_item_todo">

                            </div>
                        </div>
                    </form>
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
<script id="template_checklist_todo_row" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-xs-9  col-md-9">
            <input type="text" class="form-control" id="checklist_item_todo_{{todo_id}}" name="checklist_item_todo_{{todo_id}}"/>
        </div>
        <div class="col-xs-3 col-md-3">
            <button class="btn btn-default btn-xs" id="btn_delete_checklist_item_todo"><i class="fa fa-trash-o"></i> </button>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-md-12">
            &nbsp;
        </div>
    </div>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    var varLoadChecklistTemplateItem = <%=isLoadChecklistTemplateItem%>;
    $(window).load(function() {
        $('#btn_checklist_item_todo').click(function(){
            console.log('Add a new todo');
            var checkListTodoView = new CheckListTodoView();
            checkListTodoView.render();

            $("#div_chklist_item_todo").append(checkListTodoView.el);
        });

        $('#btn_save_checklist_item').bind('click',function(){
            saveChecklistTemplateItem( getResult );
        });

        if(varLoadChecklistTemplateItem){
            loadChecklistTemplateItem( populateChecklistTemplateItemDetails );
        }
    });
    function saveChecklistTemplateItem( callbackmethod ) {
        var actionUrl = "/proc_save_checklist_template_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_chklist_template_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadChecklistTemplateItem( callbackmethod ) {
        var actionUrl = "/proc_load_checklist_template_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_chklist_template_item").serialize();
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
                    var varChecklistTemplateItemBean = jsonResponseObj.checklist_template_item_bean;
                    $('#checklist_template_item_id').val( varChecklistTemplateItemBean.checklist_template_item_id );
                    displayAjaxOk( varResponseObj );
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
                    var varChecklistTemplateItemBean = jsonResponseObj.checklist_template_item_bean;
                    $('#checklist_template_item_id').val( varChecklistTemplateItemBean.checklist_template_item_id );
                    $('#checklist_template_item_name').val( varChecklistTemplateItemBean.name );
                }
            }
        }
    }

    var CheckListTodoView = Backbone.View.extend({
        template : Handlebars.compile( $('#template_checklist_todo_row').html() ),
        render:function(){
            var varTmpToDoBean = {
                "todo_id"  : 'new_todo_id'
            }
            var todoRow = this.template(  eval(varTmpToDoBean)  );
            $(this.el).append( todoRow );
        }
    } );
</script>