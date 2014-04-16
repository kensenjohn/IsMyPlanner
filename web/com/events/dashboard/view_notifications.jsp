<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<div class="page_wrap">
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-9">
                    <h3>Notifications</h3>
                </div>
            </div>
            <div class="row"  id="unread_notifications" >
                <div class="col-md-9">
                    <h5>There are currently no Notifications</h5>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script id="template_unread_notifications" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-12">
            <span style="text-decoration: underline;font-weight: bold;">{{notification_from}}</span>&nbsp;&nbsp;{{notification_message}}
        </div>
    </div>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        loadNotifications(populateNotifications);
    });
    function loadNotifications(callbackmethod) {
        var actionUrl = "/proc_load_unread_notifications.aeve";
        var methodType = "POST";
        var dataString = '';
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateNotifications(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfUnReadNotifications = jsonResponseObj.num_of_unread_notifications;

                    if( varNumOfUnReadNotifications > 0 ) {
                        var varUnreadNotifications = jsonResponseObj.unread_notifications;

                        this.unReadNotificationModel = new UnReadNotificationModel({
                            'bb_num_of_unread_notifications' : varNumOfUnReadNotifications,
                            'bb_unread_notifications' : varUnreadNotifications
                        });
                        var unReadNotificationView = new UnReadNotificationView({model:this.unReadNotificationModel});
                        unReadNotificationView.render();
                        $('#unread_notifications').empty();
                        $('#unread_notifications').html( unReadNotificationView.el );

                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateClientList - 1)", true);                                           P
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientList - 2)", true);
        }
    }
    var UnReadNotificationModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_unread_notifications: 0 ,
            bb_unread_notifications: undefined
        }
    });
    var UnReadNotificationView = Backbone.View.extend({
        className   :   "col-md-9",
        tagName     :   "div",
        initialize  :   function(){
            this.varNumOfUnReadNotifications = this.model.get('bb_num_of_unread_notifications');
            this.varUnReadNotifications = this.model.get('bb_unread_notifications');
        },
        template    :   Handlebars.compile( $('#template_unread_notifications').html() ),
        render      :   function(){
            for(i = 0; i <this.varNumOfUnReadNotifications; i++) {
                var newUnReadNotificationsModel = {
                    "notification_from" : this.varUnReadNotifications[i].from_name ,
                    "notification_message" : this.varUnReadNotifications[i].message
                }

                var unreadNotification =  this.template(  eval(newUnReadNotificationsModel)  );

                $(this.el).append( unreadNotification );
            }
        }
    });
</script>
</html>