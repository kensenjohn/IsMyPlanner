<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="none" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">FAQ and How it Works?</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-8" id="div_faq_categories_questions">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script id="template_faq_category_row" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-12">
            &nbsp;
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <h4>{{faq_category_name}}</h4>
        </div>
    </div>
</script>
<script id="template_faq_question_row" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-12">
            <h6><a href="/com/events/common/faqsupport/faqanswer.jsp?faq_question_id={{faq_question_id}}">{{faq_question}}</a></h6>
        </div>
    </div>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        loadFaqCategoryQuestions( populateCategoryQuestions);
    });
    function loadFaqCategoryQuestions(callbackmethod) {
        var actionUrl = "/proc_load_faq_categories_questions.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_budget_category").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateCategoryQuestions(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varNumOfCategories = jsonResponseObj.num_of_faq_categories;
                        if(varNumOfCategories>0){
                            var varFaqCategoriesAndQuesList = jsonResponseObj.faq_categories_question;
                            for( var varFaqCategoryCount = 0; varFaqCategoryCount<varNumOfCategories;varFaqCategoryCount++ ){
                                var varFaqCategory = varFaqCategoriesAndQuesList[varFaqCategoryCount];
                                this.faqCategoriesModel = new FaqCategoriesModel({
                                    'bb_faq_category' : varFaqCategory
                                });
                                var faqCategoriesListView = new FaqCategoriesListView({model:this.faqCategoriesModel});
                                faqCategoriesListView.render();
                                $("#div_faq_categories_questions").append(faqCategoriesListView.el);


                                var varNumOfFaqQuestions = varFaqCategoriesAndQuesList['num_of_faq_questions_'+varFaqCategory.support_faq_category_id];
                                if(varNumOfFaqQuestions>0){
                                    var varFaqQuesAndAnswersList = varFaqCategoriesAndQuesList['faq_question_answers_'+varFaqCategory.support_faq_category_id ];
                                    for( var varFaqQuestionCount = 0; varFaqQuestionCount<varNumOfFaqQuestions;varFaqQuestionCount++ ){
                                        var varFaqQuestion = varFaqQuesAndAnswersList[varFaqQuestionCount];
                                        this.faqQuestionModel = new FaqQuestionModel({
                                            'bb_faq_question' : varFaqQuestion
                                        });
                                        var faqQuestionView = new FaqQuestionView({model:this.faqQuestionModel});
                                        faqQuestionView.render();
                                        $("#div_faq_categories_questions").append(faqQuestionView.el);

                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    var FaqCategoriesModel = Backbone.Model.extend({
        defaults: {
            bb_faq_category: undefined
        }
    });
    var FaqCategoriesListView = Backbone.View.extend({
        initialize: function(){
            this.varBBFaqCategory = this.model.get('bb_faq_category');
        },
        template : Handlebars.compile( $('#template_faq_category_row').html() ),
        render : function() {
            var varTmpFaqCategoryBean = {
                "faq_category_name"  : this.varBBFaqCategory.category_name
            }
            var faqCategoryRow = this.template(  eval(varTmpFaqCategoryBean)  );
            $(this.el).append( faqCategoryRow );
        }
    });

    var FaqQuestionModel = Backbone.Model.extend({
        defaults: {
            bb_faq_question: undefined
        }
    });
    var FaqQuestionView = Backbone.View.extend({
        initialize: function(){
            this.varBBFaqQuestion = this.model.get('bb_faq_question');
        },
        template : Handlebars.compile( $('#template_faq_question_row').html() ),
        render : function() {
            var varTmpFaqQuestionBean = {
                "faq_question"  : this.varBBFaqQuestion.question,
                "faq_question_id" : this.varBBFaqQuestion.support_faq_ques_ans_id
            }
            var faqQuestionRow = this.template(  eval(varTmpFaqQuestionBean)  );
            $(this.el).append( faqQuestionRow );
        }
    });
</script>

<jsp:include page="/com/events/common/footer_bottom.jsp"/>
