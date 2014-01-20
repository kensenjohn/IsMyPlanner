<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<div class="page_wrap">
    <div class="container">
        <div class="content_format">
            <form  method="post" id="frm_insert_image">
                <div class="row">
                    <div class="col-md-12">
                        <label for="imageSource" class="form_label">Source</label>
                        <input type="text" class="form-control" id="imageSource" name="image_source" placeholder="Link to an image on the internet">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        &nbsp;
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                        <button  type="button" class="btn  btn-filled" id="btn_insert_image">
                            <span> Insert</span>
                        </button>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="col-md-3">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    &nbsp;
                </div>
            </div>
            <div  class="form-group">
            <form id="fileupload" action="/proc_upload_image.aeve" method="POST" enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-4">
                        <label for="imageSource" class="form_label">Upload And Insert</label>
                        <input type="file" name="files[]" class="fileinput-button btn btn-default">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        &nbsp;
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2" >
                        <div id="btn_upload">

                        </div>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="col-md-3">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div id="progress">
                        <div class="bar" style="width: 0%;"></div>
                    </div>
                </div>
            </div>

            </div>
            <div class="row">
                <div class="col-md-3">
                    &nbsp;
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        var parentWin = (!window.frameElement && window.dialogArguments) || opener || parent || top;
        var parentEditor = parentWin.my_namespace_activeEditor;
        var parentFrame = parentWin.my_namespace_activeFrame;

        $('#btn_insert_image').click(function(){
            insertImage( $('#imageSource').val() );
        });
    });
    function insertImage( varImageUrlSource){
        if(varImageUrlSource!=undefined && varImageUrlSource!='') {
            var parentWin = (!window.frameElement && window.dialogArguments) || opener || parent || top;
            var parentEditor = parentWin.my_namespace_activeEditor;
            parentEditor.execCommand('mceInsertRawHTML', false, '<p><img src="'+varImageUrlSource+'" alt="" /></p>');
            parentEditor.windowManager.close(this);
        } else {
            displayMssgBoxAlert('Please use a valid image source.', true);
        }

    }
    $(function () {
        $('#fileupload').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                $('#btn_upload').empty();
                var varUploadButton = $('<button/>').attr("id","upload_button").addClass("btn btn-filled").appendTo($('#btn_upload'));
                $("#upload_button").text('Upload and Insert');

                data.context = varUploadButton.click(function () {
                    data.context = $('<p/>').text('Uploading...').replaceAll($(this));
                    data.submit();
                });

            },
            done: function (e, data) {
                data.context.text('Upload Complete.');
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];
                    if(varDataResult!=undefined && varDataResult.success) {
                        var varLinkToImage = varDataResult.imagehost  + "/" + varDataResult.foldername + "/" + varDataResult.name;
                        insertImage(varLinkToImage);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
</script>