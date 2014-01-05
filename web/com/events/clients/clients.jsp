<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>

<%
    boolean loadSingleClientContactInfo = false;
    boolean isShowClientContactInfo = false;
    boolean isShowClientEvents = false;
    String sClientId = ParseUtil.checkNull(request.getParameter("clientid"));
    String sClientDataType = ParseUtil.checkNull(request.getParameter("clientdatatype"));

    if( !"".equalsIgnoreCase(sClientId) && "contact_info".equalsIgnoreCase(sClientDataType)) {
        isShowClientContactInfo = true;
        loadSingleClientContactInfo = true;
    } else if(!"".equalsIgnoreCase(sClientId) && "event_info".equalsIgnoreCase(sClientDataType)) {
        isShowClientEvents = true;
    }
    if( ("".equalsIgnoreCase(sClientId) || "".equalsIgnoreCase(sClientDataType)) ) {
        isShowClientContactInfo = true;
    }
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="client_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Clients</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="use_left_sidebar col-md-9 no_left_padding">
                <h4><span id="client_name_title">New Client</span></h4>
                <div id="tabs">


                        <%
                            if(isShowClientContactInfo) {
                        %>
                                <jsp:include page="/com/events/clients/client_tab.jsp">
                                    <jsp:param name="client_contact_info_active" value="active"/>
                                </jsp:include>
                        <%
                            } else if (isShowClientEvents) {
                        %>
                                <jsp:include page="/com/events/clients/client_tab.jsp">
                                    <jsp:param name="client_events_active" value="active"/>
                                </jsp:include>
                        <%
                            }
                        %>

                    <div class="tab-container">

                        <div id="tab1" class="tab-content" style="display: block;">
                            <div style="padding-top:15px;">
                                <%
                                    if(isShowClientContactInfo) {
                                %>
                                        <jsp:include page="/com/events/clients/client_contact_form.jsp"/>
                                <%
                                    } else if (isShowClientEvents) {
                                %>
                                        <jsp:include page="/com/events/clients/client_events.jsp"/>
                                <%
                                    }
                                %>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <aside class="sidebar left-sidebar col-md-3">
                <div class="row">
                    <div>
                        <h4>
                            <div  style="float:left"><button type="button" class="btn btn-filled" id="btn_new_client">
                                <span class="glyphicon glyphicon-user"></span> Add a Client</button></div>
                        </h4>
                    </div>
                </div>
                <div class="row">
                    <div id="div_client_list" class="unordered_menu">
                    </div>
                </div>
            </aside>
        </div>
    </div>
</div>
</body>
<form id="frm_new_client" action="/com/events/clients/clients.jsp">
</form>
<form id="frm_load_client">
    <input type="hidden"  id="clientid" name="clientid" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/clients/clientcontactinfo.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        $('#btn_save_client').click(function(){
            saveClient(getResult);
        });
        $('#btn_new_client').click(function(){
            $('#frm_new_client').submit();
        });
        var varLoadSingleClientContactDetail = <%=loadSingleClientContactInfo%>;
        if(varLoadSingleClientContactDetail) {
            var varClientId = '<%=sClientId%>';
            var varClientDataType = '<%=sClientDataType%>';
            loadClientDetail(varClientId, varClientDataType , populateClientDetail);
        }
        loadClients(populateClientList);
    });

    function loadClients(callbackmethod) {
        var actionUrl = "/proc_load_clients.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_client").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateClientList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                var varIsMessageExist = varResponseObj.is_message_exist;
                if(varIsMessageExist == true) {
                    var jsonResponseMessage = varResponseObj.messages;
                    var varArrErrorMssg = jsonResponseMessage.error_mssg;
                    displayMssgBoxMessages(varArrErrorMssg, true);
                }

            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfClients = jsonResponseObj.num_of_clients;
                    if(varNumOfClients>0){
                        processClientListSummary(varNumOfClients,jsonResponseObj.all_client_summary);
                    }
                    else {
                        //displayMssgBoxAlert("Create a new client here.", true);
                    }

                }
            } else {
                displayMssgBoxAlert("Please try again later (populateClientList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientList - 2)", true);
        }
    }
    function processClientListSummary(varNumOfClients,clientSummaryList) {
        var varUnorderClientList = $('<ul></ul>');
        for(i=0;i<varNumOfClients;i++){
            var varClientBean = clientSummaryList[i];
            //displayMssgBoxAlert('client name :' + varClientBean.client_name , false);
            varUnorderClientList.append('<li><a href=\"/com/events/clients/clients.jsp?clientid='+varClientBean.client_id+'&clientdatatype=contact_info\">'+varClientBean.client_name+'</a></li>');
        }
        $('#div_client_list').append(varUnorderClientList);
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>