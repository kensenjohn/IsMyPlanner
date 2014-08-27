tinymce.init({
    selector: "#comment_body",
    theme: "modern",
    menubar: false,
    plugins: [
        "autolink link ",
        "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
        "emoticons paste textcolor"
    ],
    toolbar1: "undo redo |  bold italic | bullist numlist | link"
});

$('#btn_save_comment').bind('click', function(){
    tinyMCE.triggerSave();
    saveComment( getCommentResult);
});

function loadComments(){
    var actionUrl = "/proc_load_comment.aeve";
    var methodType = "POST";
    var dataString = $("#frm_load_comments").serialize();
    makeAjaxCall(actionUrl,dataString,methodType,displayAllComments);
}

function saveComment( callbackmethod ) {
    var actionUrl = "/proc_save_comment.aeve";
    var methodType = "POST";
    var dataString = $("#frm_comment").serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}

function displayAllComments(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var jsonResponseObj = varResponseObj.payload;
            if(jsonResponseObj!=undefined) {
                var varNumOfComments = jsonResponseObj.num_of_comments;
                if(varNumOfComments>0){
                    var varAllComments = jsonResponseObj.all_comments;
                    for( var trackComments = 0; trackComments< varNumOfComments; trackComments++ ){
                        var varCommentBean = varAllComments[trackComments];

                        this.commentModel = new CommentModel({
                            'bb_comment_text' : varCommentBean.comment,
                            'bb_comment_username' : varCommentBean.user_name,
                            'bb_comment_date_time' : varCommentBean.formatted_human_create_date
                        });
                        var commentView = new CommentView({model:this.commentModel});
                        commentView.render();
                        $("#comment_section").append(commentView.el);
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

function getCommentResult(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var jsonResponseObj = varResponseObj.payload;
            if(jsonResponseObj!=undefined) {
                var tinymce_editor_id = 'comment_body';
                var varCommentContent = tinyMCE.get(tinymce_editor_id).getContent();


                tinymce.get(tinymce_editor_id).setContent('');

                var varCommentsBean = jsonResponseObj.comments_bean;

                this.commentModel = new CommentModel({
                    'bb_comment_text' : varCommentContent,
                    'bb_comment_username' : varCommentsBean.user_name,
                    'bb_comment_date_time' : varCommentsBean.formatted_human_create_date
                });
                var commentView = new CommentView({model:this.commentModel});
                commentView.render();
                $("#comment_section").append(commentView.el);

                //$('#comment_section').append(varCommentContent);

            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
        }
    } else {
        displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
    }
}

var CommentModel = Backbone.Model.extend({
    defaults: {
        bb_comment_text: undefined,
        bb_comment_username: undefined,
        bb_comment_date_time: undefined
    }
});
var CommentView = Backbone.View.extend({
    initialize: function(){
        this.varBBCommentText = this.model.get('bb_comment_text');
        this.varBBCommentUserName = this.model.get('bb_comment_username');
        this.varBBCommentDateTime = this.model.get('bb_comment_date_time');
    },
    template : Handlebars.compile( $('#template_comment_row').html() ),
    render : function() {
        var varTmpCommentBean = {
            "comment_text"  : this.varBBCommentText,
            "comment_username" :this.varBBCommentUserName,
            "comment_date_time" :this.varBBCommentDateTime
        }
        var commentRow = this.template(  eval(varTmpCommentBean)  );
        $(this.el).append( commentRow );
    }
});