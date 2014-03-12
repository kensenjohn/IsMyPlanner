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
      <div class="container">
          <div style="background-image: url('http://d33np9n32j53g7.cloudfront.net/assets/new/home-hero-a4778806742a55871d57663035e77b64.jpg');height: 447px;margin-left: 0;margin-right: 0;background-position: 50% 0;">

          </div>
          <!--<div class="flexslider">
              <ul class="slides">
                  <li>
                      <img src="http://www.smarasoft.com/img/slider/slider1.jpg" />
                      <div class="container hidden-sm ">
                          <div class="slide-caption bottom-left">
                              <h1 style="color: #ffffff;">An Event Planner's Management Portal</h1>
                              <p>Manage your team, clients and vendors from one place.</p>
                          </div>
                      </div>
                  </li>
                  <li>
                      <img src="http://www.smarasoft.com/img/slider/slider2.jpg" />
                      <div class="container hidden-sm">
                          <div class="slide-caption hidden-sm bottom-left">
                              <h1 style="color: #ffffff;">Communcate Effeciently and Quickly</h1>
                              <p>Notifications, Reminders, Emails for you and your clients to be in touch.</p>
                          </div>
                      </div>
                  </li>
                  <li>
                      <img src="http://www.smarasoft.com/img/slider/slider3.jpg" />
                      <div class="container  hidden-sm ">
                          <div class="slide-caption hidden-sm bottom-left">
                              <h1 style="color: #ffffff;">Tools For You And Your Clients</h1>
                              <p>Customizable event planning tools</p>
                          </div>
                      </div>
                  </li>
              </ul>
          </div> -->
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
            animation: "slide",
            smoothHeight: true
        });
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
</html>