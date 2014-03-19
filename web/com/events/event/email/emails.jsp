<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link rel="stylesheet" href="/css/font-awesome.min.css">
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
        <jsp:param name="event_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Edit Event Emails - <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <%if(loadEventInfo) {%>
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_emails_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <button  type="button" class="btn  btn-filled" id="btn_create_email">
                        <span>Create New Email</span>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_event_email_table" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-3" role="columnheader">Name</th>
                            <th class="sorting  col-md-3" role="columnheader">Scheduled Send Time</th>
                            <th class="sorting" role="columnheader">Status</th>
                            <th class="sorting" role="columnheader">Send Rule</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>

                        <tbody role="alert" id="every_eventemail_rows">
                        </tbody>
                    </table>
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
<form id="frm_create_email" method="POST" action="/com/events/event/email/edit_email.jsp">
    <input type="hidden" name="event_id" value="<%=sEventId%>">
</form>
<form id="frm_load_all_eventemail">
    <input type="hidden" name="event_id" value="<%=sEventId%>">
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        $('#btn_create_email').click(function(){
            $("#frm_create_email").submit();
        });
        loadEventEmail(populateEventEmailList);
    });
    function loadEventEmail(callbackmethod) {
        var actionUrl = "/proc_load_all_eventemails.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_all_eventemail").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateEventEmailList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfEventEmail = jsonResponseObj.num_of_eventemails;
                    if(varNumOfEventEmail>0){
                        processEventEmailList(varNumOfEventEmail, jsonResponseObj.every_eventemail );
                    }
                    initializeTable();

                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }

    function processEventEmailList(varNumOFEventEmails, everyEventEmailList) {
        for(i=0;i<varNumOFEventEmails;i++){
            var varEveryEventEmailBean = everyEventEmailList[i];
            var rowEveryEventEmail= $('<tr id="row_'+varEveryEventEmailBean.eventemail_id+'" ></tr>');
            rowEveryEventEmail.append(
                    '<td>'+varEveryEventEmailBean.eventemail_name+'</td>'+
                            '<td>'+varEveryEventEmailBean.send_date+'</td>' +
                            '<td>'+varEveryEventEmailBean.status+'</td>'+
                            '<td>'+varEveryEventEmailBean.send_rule+'</td>'+
                            '<td  class="center" >'+
                            '<a href="/com/events/event/email/edit_email.jsp?event_id='+varEveryEventEmailBean.event_id+'&eventemail_id='+varEveryEventEmailBean.eventemail_id+'" class="btn btn-default btn-xs"><i class="fa fa-pencil"></i> Edit</a>'+
                            '&nbsp;&nbsp;&nbsp;'+
                            '<a id="'+varEveryEventEmailBean.eventemail_id+'" class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</a>'+'</td>');
            $('#every_eventemail_rows').append(rowEveryEventEmail);


            // Adding Click Event Functionality for event's delete button.
            var eventemail_obj = {
                event_id: varEveryEventEmailBean.event_id,
                eventemail_name: varEveryEventEmailBean.eventemail_name,
                eventemail_id: varEveryEventEmailBean.eventemail_id,
                row_num: i,
                printObj: function () {
                    return this.event_id + ' ' + this.eventemail_name + ' ' + this.eventemail_id + ' row : ' + this.row_num;
                }
            }
            $('#'+varEveryEventEmailBean.eventemail_id).click({param_eventemail_obj:eventemail_obj},function(e){
                displayConfirmBox(
                        "Are you sure you want to delete - " + e.data.param_eventemail_obj.eventemail_name ,
                        "Delete Email",
                        "Yes", "No", deleteEventEmail,e.data.param_eventemail_obj)
            });
        }
    }
    function deleteEventEmail(varEventEmailObj) {
        $('#event_id').val(varEventEmailObj.event_id);
        $('#eventemail_id').val(varEventEmailObj.eventemail_id);
        deleteEventEmails(processEventEmailDeletion);
    }
    function deleteEventEmails(callbackmethod) {
        var actionUrl = "/proc_delete_eventemail.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_event").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function processEventEmailDeletion(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varIsEventDeleted = jsonResponseObj.is_deleted;
                    if(varIsEventDeleted){
                        $('#eventemail_id').val('');
                        var varDeletedEventEmailId = jsonResponseObj.deleted_eventemail_id;
                        $('#row_'+varDeletedEventEmailId).remove();
                    } else {
                        displayMssgBoxAlert("The event was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }

    function initializeTable(){
        objEveryEventEmailTable =  $('#every_event_email_table').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "aoColumns": [
                null,
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>