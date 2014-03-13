package com.events.proc.users;

import com.events.bean.users.*;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import com.events.users.BuildUsers;
import com.events.users.CookieUser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLogin  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        try {
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {

                String sEmail = ParseUtil.checkNull(request.getParameter("loginEmail"));
                String sPassword = ParseUtil.checkNull(request.getParameter("loginPassword"));
                boolean isRememberMe = ParseUtil.sTob(request.getParameter("loginRememberMe"));

                //appLogging.info("Username : " + sUsername + " Password : " + sPassword );
                if("".equalsIgnoreCase(sEmail) || "".equalsIgnoreCase(sPassword)) {
                    Text errorText = new ErrorText("Please use a valid username or password","account_num") ;
                    arrErrorText.add(errorText);
                    responseStatus = RespConstants.Status.ERROR;
                } else {
                    UserRequestBean userRequestBean = new UserRequestBean();
                    userRequestBean.setEmail( sEmail );

                    AccessUsers accessUsers = new AccessUsers();
                    UserBean userBean = accessUsers.getUserByEmail(userRequestBean);
                    if(userBean!=null && !"".equalsIgnoreCase(userBean.getUserId())) {
                        PasswordRequestBean passwordRequestBean = new PasswordRequestBean();
                        passwordRequestBean.setPassword(sPassword);
                        passwordRequestBean.setUserId(userBean.getUserId());

                        userRequestBean.setPasswordRequestBean(passwordRequestBean);
                        userRequestBean.setUserId( userBean.getUserId() );

                        if( accessUsers.authenticateUser(userRequestBean) ) {
                            Text okText = new OkText("Login Successful.","err_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;

                            userRequestBean.setUserInfoId( userBean.getUserInfoId() );
                            UserInfoBean userInfoBean = accessUsers.getUserInfoFromInfoId(userRequestBean);
                            userBean.setUserInfoBean( userInfoBean );
                            HttpSession httpSession = request.getSession(true);
                            if(httpSession!=null){
                                httpSession.setAttribute(Constants.USER_LOGGED_IN_BEAN,userBean);
                            }

                            jsonResponseObj.put("pass_thru_link" , BuildUsers.getPassThroughLink(userBean));
                            if(isRememberMe) {
                                CookieRequestBean cookieRequestBean = new CookieRequestBean();
                                cookieRequestBean.setUserId( userBean.getUserId() );

                                CookieUser cookieUser = new CookieUser();
                                CookieUserResponseBean cookieUserResponseBean = cookieUser.saveCookieUser(cookieRequestBean);
                                if(cookieUserResponseBean!=null && !Utility.isNullOrEmpty(cookieUserResponseBean.getCookieUserId())){
                                    jsonResponseObj.put("cookieuser_id",cookieUserResponseBean.getCookieUserId());
                                }
                            }

                        } else {
                            appLogging.info("Invalid password used : "  + sEmail  );
                            Text errorText = new ErrorText("Please use a valid email and password to login","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                        }
                    } else {
                        appLogging.info("Invalid userBean used - " + ParseUtil.checkNullObject(userBean.toString()) );
                        Text errorText = new ErrorText("Please use a valid email and password to login","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    }

                }
            } else {
                responseObject = DataSecurityChecker.getInsecureInputResponse(this.getClass().getName() );
            }
        } catch(Exception e) {
            appLogging.error("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e));
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(001)","err_mssg") ;
            arrErrorText.add(errorText);

            responseStatus = RespConstants.Status.ERROR;
        }
        responseObject.setErrorMessages(arrErrorText);
        responseObject.setOkMessages(arrOkText);
        responseObject.setResponseStatus(responseStatus);
        responseObject.setJsonResponseObj(jsonResponseObj);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write( responseObject.getJson().toString() );
    }
}
