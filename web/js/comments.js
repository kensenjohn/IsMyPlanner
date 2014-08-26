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

function saveComment( callbackmethod ) {
    var actionUrl = "/proc_save_comment.aeve";
    var methodType = "POST";
    var dataString = $("#frm_comment").serialize();
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
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
                $('#comment_section').append(varCommentContent);

                tinymce.get(tinymce_editor_id).setContent('');
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
        }
    } else {
        displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
    }
}