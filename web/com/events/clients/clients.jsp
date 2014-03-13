<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Perm" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    UserBean loggedInUserBean = new UserBean();
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
    }
    CheckPermission checkPermission = null;
    if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
        checkPermission = new CheckPermission(loggedInUserBean);
    }

    boolean canEditClient = false;
    boolean canDeleteClient = false;
    if(checkPermission!=null && checkPermission.can(Perm.EDIT_CLIENT)) {
        canEditClient = true;
    }

    if(checkPermission!=null && checkPermission.can(Perm.DELETE_CLIENT)) {
        canDeleteClient = true;
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
            <%
                if(checkPermission!=null && checkPermission.can(Perm.CREATE_NEW_CLIENT)) {
            %>
                <div class="row">
                    <div class="col-md-12">
                        <div  style="float:left"><a href="/com/events/clients/client_account.jsp" class="btn btn-filled" id="btn_new_client">
                            <i class="fa fa-plus"></i> Add a Client</a></div>
                    </div>
                </div>
            <%
                }
            %>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_client" >
                        <thead>
                        <tr role="row">
                            <th role="columnheader">Name</th>
                            <th role="columnheader"></th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_delete_client">
    <input type="hidden" id="delete_client_id" name="client_id"  value=""/>
</form>
<script id="template_client_row" type="text/x-handlebars-template">
    <tr id="row_{{client_id}}">
        <td>{{client_name}}</td>
        <td>
            <a id="edit_{{client_id}}" href="/com/events/clients/client_account.jsp?client_id={{client_id}}&client_datatype=contact_info" class="btn btn-default btn-xs"> <%=canEditClient?"<i class=\"fa fa-pencil\"></i> Edit":"View"%></a>
            &nbsp;&nbsp;
            <a id="{{client_id}}" name="delete_client" class="btn btn-default btn-xs <%=canDeleteClient?"":"disabled"%>" row={{row_num}}><i class="fa fa-trash-o"></i> Delete</a>
        </td>
    </tr>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        loadClients(populateClientList);
    });
    function loadClients(callbackmethod) {
        var actionUrl = "/proc_load_clients.aeve";
        var methodType = "POST";
        var dataString = '';
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateClientList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfClients = jsonResponseObj.num_of_clients;
                    if(varNumOfClients>0){
                        this.clientsSummaryListModel = new ClientsSummaryListModel({
                            'bb_num_of_clients' : varNumOfClients,
                            'bb_client_list_summary' : jsonResponseObj.all_client_summary ,
                            'bb_can_delete_client': jsonResponseObj.can_delete_client
                        });
                        var clientsSummaryView = new ClientsSummaryListView({model:this.clientsSummaryListModel});
                        clientsSummaryView.render();
                        $("#every_client").append(clientsSummaryView.el);
                    } else {
                        //displayMssgBoxAlert("Create a new client here.", true);
                    }
                    initializeContactUsTable();
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateClientList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientList - 2)", true);
        }
    }

    var ClientsSummaryListModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_clients: 0 ,
            bb_client_list_summary: undefined,
            bb_can_delete_client: false
        }
    });
    var ClientsSummaryListView = Backbone.View.extend({
        tagName: "tbody",
        id : "every_client_rows",
        initialize: function(){
            this.varNumOfClients = this.model.get('bb_num_of_clients');
            this.varClientListSummary = this.model.get('bb_client_list_summary');
        },
        template : Handlebars.compile( $('#template_client_row').html() ),
        events : {
            "click a[name='delete_client']" : 'assignEventHandling'
        },
        render : function() {
            for(i=0;i<this.varNumOfClients;i++){
                var varClientBean = this.varClientListSummary[i];

                var varTmpClientBean = {
                    "client_id" :varClientBean.client_id,
                    "client_name" :varClientBean.client_name,
                    "row_num" :i
                }
                var clientRow = this.template(  eval(varTmpClientBean)  );
                $(this.el).append( clientRow );
            }
        },
        assignEventHandling : function(event) {
            if(this.bb_can_delete_client == true ){
                var varTargetDeleteButton = $(event.target);

                var client_obj = {
                    client_id:  varTargetDeleteButton.attr('id'),
                    row_num:  varTargetDeleteButton.attr('row'),
                    printObj: function () {
                        return this.client_id  + ' - ' + this.row_num;
                    }
                }

                displayConfirmBox(
                        "Are you sure you want to delete this client? ",
                        "Delete Client",
                        "Yes", "No", deleteClient,client_obj);
            } else {
                displayMssgBoxAlert("You are not authorized to perform this action.", true);
            }
        }
    });
    function initializeContactUsTable(){

        objEveryClientTable =  $('#every_client').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": true,
            "aoColumns":  [
                {"bSortable": true,"sClass":"col-md-5"},
                { "bSortable": false,"sClass": "center" }
            ]
        });
    }


    function deleteClient( varClientObj ){
        $('#delete_client_id').val(varClientObj.client_id);

        var actionUrl = "/proc_delete_client.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_client").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processClientDeletion);

    }
    function processClientDeletion (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    var varIsClientyDeleted = jsonResponseObj.is_deleted;

                    if(varIsClientyDeleted){

                        $('#delete_client_id').val('');

                        var varClientId = jsonResponseObj.deleted_client_id;
                        objEveryClientTable.fnDeleteRow((objEveryClientTable.$('#row_'+varClientId))[0] );

                    } else {
                        displayMssgBoxAlert("The client was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteClient - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteClient - 2)", true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>