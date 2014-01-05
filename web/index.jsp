<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="/css/flexslider.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
  <div class="page_wrap">
      <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
      </jsp:include>
      <jsp:include page="/com/events/common/menu_bar.jsp">
          <jsp:param name="home_active" value="currently_active"/>
      </jsp:include>
      <div>
          <div class="flexslider">
              <ul class="slides">
                  <li>
                      <img src="http://dummyimage.com/1600x500/541b54/ffffff.png&text=Picture+1" />
                      <div class="container  hidden-phone ">
                          <div class="slide-caption hidden-phone bottom-left">
                              <h1 style="color: #ffffff;">Event Plans</h1>
                          </div>
                      </div>
                  </li>
                  <li>
                      <img src="http://dummyimage.com/1600x500/541b54/ffffff.png&text=Picture+2" />
                  </li>
                  <li>
                      <img src="http://dummyimage.com/1600x500/541b54/ffffff.png&text=Picture+3" />
                  </li>
                  <li>
                      <img src="http://dummyimage.com/1600x500/541b54/ffffff.png&text=Picture+4" />
                  </li>
              </ul>
          </div>
      </div>
      <div class="container">
          <div>
              <h1>H1 - Lead me show me the way</h1>
              <h2>H2 - Lead me show me the way</h2>
              <h3>H3 - Lead me show me the way</h3>
              <h4>H4 - Lead me show me the way</h4>
              <h5>H5 - Lead me show me the way</h5>
              <h6>H6 - Lead me show me the way</h6>
          </div>
      </div>
      <div class="container">
          <h1>Buttons</h1>
          <div class="row">
              <div class="col-lg-offset-1 col-sm-pull-1">
                  <!-- Standard button -->
                  <button type="button" class="btn btn-default">Default</button>  <br>   <br>
                  <button type="button" class="btn  btn-filled">Filled</button>  <br>   <br>
                  <button type="button" class="btn  btn-filled btn-lg">Filled Large</button>
              </div>
          </div>
      </div>

  </div>
  </body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.flexslider-min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        $('.flexslider').flexslider({
            animation: "slide"
        });
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
</html>