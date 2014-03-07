<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp"/>
    <jsp:include page="/com/events/common/menu_bar.jsp"/>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">My Account</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-9">
                    <div class="row">
                        <div class="col-md-12" id="div_account_contact_info">

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12" id="div_button_save_contact_info">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script  id="template_my_account_contact_info" type="text/x-handlebars-template">
    <div class="form-group">
        <div class="row">
            <div class="col-md-12">
                <label for="email" class="form_label">Email</label><span class="required"> *</span>
                <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="{{email}}">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-md-6">
                <label for="first_name" class="form_label">First Name</label><span class="required"> *</span>
                <input type="text" class="form-control" id="first_name" name="first_name" placeholder="First Name" value="{{first_name}}">
            </div>
            <div class="col-md-6">
                <label for="last_name" class="form_label">Last Name</label><span class="required"> *</span>
                <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Last Name" value="{{last_name}}">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-md-12">
                <label for="company" class="form_label">Company Name</label>
                <input type="text" class="form-control" id="company" name="company" placeholder="Company Name" value="{{company}}">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-md-6">
                <label for="cell_phone" class="form_label">Cell Phone</label>
                <input type="tel" class="form-control" id="cell_phone" name="cell_phone" placeholder="Cell Phone" value="{{cell_phone}}">
            </div>
            <div class="col-md-6">
                <label for="phone_num" class="form_label">Phone</label>
                <input type="tel" class="form-control" id="phone_num" name="phone_num" placeholder="Phone" value="{{phone_num}}">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-md-6">
                <label for="address1" class="form_label">Address 1</label>
                <input type="text" class="form-control" id="address1" name="address1" placeholder="Address 1" value="{{address1}}">
            </div>
            <div class="col-md-6">
                <label for="address2" class="form_label">Address 2</label>
                <input type="text" class="form-control" id="address2" name="address2" placeholder="Address 2" value="{{address2}}">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-md-6">
                <label for="city" class="form_label">City</label>
                <input type="text" class="form-control" id="city" name="city" placeholder="City" value="{{city}}">
            </div>
            <div class="col-md-6">
                <label for="state" class="form_label">State</label>
                <input type="text" class="form-control" id="state" name="state" placeholder="State" value="{{state}}">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-md-6">
                <label for="zipcode" class="form_label">Postal Code</label>
                <input type="text" class="form-control" id="zipcode" name="zipcode" placeholder="Postal Code" value="{{zipcode}}">
            </div>
            <div class="col-md-6">
                <label for="country" class="form_label">Country</label>
                <input type="text" class="form-control" id="country" name="country" placeholder="Country" value="{{country}}">
            </div>
        </div>
    </div>
    <input type="hidden" name="userinfo_id" value="{{userinfo_id}}">
    <input type="hidden" name="user_id" value="{{user_id}}">
    <input type="hidden" name="user_type" value="{{user_type}}">
</script>
<script  id="template_button_save_my_account_contact_info" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-6">
            <button class="btn btn-filled" id="save_account_contact_info"  >Save</button>
        </div>
    </div>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        loadMyAccount(populateMyAccountContact);
    });
    function loadMyAccount(callbackmethod) {
        var actionUrl = "/proc_load_my_account_contact.aeve";
        var methodType = "POST";
        var dataString = ''
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveMyAccountContactInfo(callbackmethod) {
        //console.log( $("#frm_my_account_contact_info").serialize() )
        var actionUrl = "/proc_save_my_account_contact.aeve";
        var methodType = "POST";
        var dataString = $("#frm_my_account_contact_info").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    console.log('Is Saved' + jsonResponseObj.is_saved );
                    if( jsonResponseObj.is_saved == true ) {
                        displayMssgBoxAlert("Contact Information was successfully saved.", false);
                    } else {
                        displayMssgBoxAlert("We were unable to process your request. Please try again later. (saveAccountContact - 3)", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (saveAccountContact - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (saveAccountContact - 2)", true);
        }
    }
    function populateMyAccountContact(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varUserBean = jsonResponseObj.user_bean;
                    if(varUserBean!=undefined){
                        //processEventList(varNumOfEvents, jsonResponseObj.every_event );
                        //displayMssgBoxAlert("User My Account Contact Information.", true);

                        this.myAccountContactInfoModel = new MyAccountContactInfoModel({
                            'bb_user_bean' : varUserBean,
                            'bb_user_info_bean' : varUserBean.user_info_bean
                        });

                        var myAccountContactInfoView = new MyAccountContactInfoView({model:this.myAccountContactInfoModel});
                        myAccountContactInfoView.render();
                        $('#div_account_contact_info').append( myAccountContactInfoView.el );

                        var saveAccountContactInfoView  = new SaveAccountContactInfoView();
                        saveAccountContactInfoView.render();
                        $('#div_button_save_contact_info').append( saveAccountContactInfoView.el );


                    } else {
                        //displayMssgBoxAlert("Create a new client here.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }
    var SaveAccountContactInfoModel = Backbone.Model.extend({});
    var SaveAccountContactInfoView = Backbone.View.extend({

        events : {
            "click #save_account_contact_info" : 'assignEventHandling'
        },
        template: Handlebars.compile( $('#template_button_save_my_account_contact_info').html() ),
        render:function(){
            var saveButton = this.template( );
            $(this.el).append( saveButton );
        },
        assignEventHandling : function(event) {
            saveMyAccountContactInfo(getResult)
        }
    });

    var MyAccountContactInfoModel = Backbone.Model.extend({});
    var MyAccountContactInfoView = Backbone.View.extend({
        initialize: function(){
            this.varUserBean = this.model.get('bb_user_bean');
            this.varUserInfoBean = this.model.get('bb_user_info_bean');
        },
        tagName : "form",
        id : "frm_my_account_contact_info",
        template : Handlebars.compile( $('#template_my_account_contact_info').html() ),
        render: function() {
            var varUserAccountContactInfo = {
                "userinfo_id" : this.varUserInfoBean.userinfo_id ,
                "user_id" : this.varUserBean.user_id ,
                "user_type" : this.varUserBean.user_type  ,
                "first_name" : this.varUserInfoBean.first_name ,
                "last_name" : this.varUserInfoBean.last_name ,
                "address1" : this.varUserInfoBean.address1 ,
                "address2" : this.varUserInfoBean.address2 ,
                "city" : this.varUserInfoBean.city ,
                "state" : this.varUserInfoBean.state ,
                "country" : this.varUserInfoBean.country ,
                "email" : this.varUserInfoBean.email ,
                "cell_phone" : this.varUserInfoBean.cell_phone ,
                "phone_num" : this.varUserInfoBean.phone_num ,
                "company" : this.varUserInfoBean.company ,
                "zipcode" : this.varUserInfoBean.zipcode
            }
            $(this.el).append( this.template( eval(varUserAccountContactInfo)  ) );
        }
    });

</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
