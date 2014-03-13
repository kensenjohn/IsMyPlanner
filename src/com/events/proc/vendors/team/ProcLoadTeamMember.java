package com.events.proc.vendors.team;

import com.amazonaws.util.json.JSONArray;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.bean.users.permissions.UserRolesBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import com.events.users.permissions.AccessUserRoles;
import com.events.users.permissions.UserRolePermission;
import com.events.vendors.AccessVendors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/2/14
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadTeamMember  extends HttpServlet {
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) ) {
                    String sUserId = ParseUtil.checkNull(loggedInUserBean.getUserId());

                    String sTeamMemberUserId = ParseUtil.checkNull(request.getParameter("user_id"));
                    if(!Utility.isNullOrEmpty(sTeamMemberUserId )) {
                        UserRequestBean userRequestBean = new UserRequestBean();
                        userRequestBean.setUserId( sTeamMemberUserId );

                        AccessUsers accessUsers = new AccessUsers();
                        UserBean userBean = accessUsers.getUserById( userRequestBean );
                        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
                            UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );
                            userBean.setUserInfoBean(userInfoBean);

                            AccessUserRoles accessUserRoles = new AccessUserRoles();
                            ArrayList<UserRolesBean> arrUserRolesBean = accessUserRoles.getUserRolesByUserId( userBean );
                            if(arrUserRolesBean!=null && !arrUserRolesBean.isEmpty() ) {
                                JSONObject jsonUserRole = new JSONObject();
                                Integer iNumOfRoles = 0 ;
                                for(UserRolesBean userRolesBean : arrUserRolesBean ) {
                                    jsonUserRole.put(iNumOfRoles.toString() , userRolesBean.toJson());
                                    iNumOfRoles++;
                                }
                                jsonUserRole.put("total_roles", iNumOfRoles);
                                jsonResponseObj.put("user_role" , jsonUserRole );
                            }
                            jsonResponseObj.put("user_bean" , userBean.toJson());

                            boolean isTeamMemberCurrentlyLoggedIn = false;
                            if(userBean.getUserId().equalsIgnoreCase(loggedInUserBean.getUserId() )) {
                                isTeamMemberCurrentlyLoggedIn = true;
                            }
                            jsonResponseObj.put("is_team_member_logged_in" , isTeamMemberCurrentlyLoggedIn );

                            Text okText = new OkText("The team members were loaded successfully.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;


                        } else {
                            appLogging.info("Team Member does not exists : " + ParseUtil.checkNull(sTeamMemberUserId) );
                            Text errorText = new ErrorText("Oops!! We were unable to load information about the team member. Please try again later.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                    } else {
                        appLogging.info("Invalid Team Member Id : " + ParseUtil.checkNull(sTeamMemberUserId) );
                        Text errorText = new ErrorText("Oops!! Please select a valid team member and try again.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadVendorLandingPage - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadVendorLandingPage - 001)","err_mssg") ;
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
