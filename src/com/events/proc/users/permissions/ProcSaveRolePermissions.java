package com.events.proc.users.permissions;

import com.events.bean.users.UserBean;
import com.events.bean.users.permissions.*;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Perm;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.permissions.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/30/14
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveRolePermissions extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {
                    String sRoleId = ParseUtil.checkNull(request.getParameter("role_id"));

                    if(!Utility.isNullOrEmpty(sRoleId)) {
                        boolean isRoleEditable = true;
                        UserRolePermissionRequestBean userRolePermRequest = new UserRolePermissionRequestBean();
                        userRolePermRequest.setRoleId( sRoleId);

                        AccessRoles accessRoles = new AccessRoles();
                        RolesBean currentRoleBean  = accessRoles.getRoleById(userRolePermRequest);

                        if(currentRoleBean!=null && !currentRoleBean.isSiteAdmin()) {
                            AccessUserRoles accessUserRoles = new AccessUserRoles();
                            ArrayList<UserRolesBean> arrUserRolesBean = accessUserRoles.getUserRolesByUserId( loggedInUserBean );
                            if(arrUserRolesBean!=null && !arrUserRolesBean.isEmpty() ) {
                                for(UserRolesBean userRolesBean : arrUserRolesBean ) {
                                    if(sRoleId.equalsIgnoreCase(userRolesBean.getRoleId())) {
                                        isRoleEditable = false; // Site Admin Role. cannot be edited.
                                        break;
                                    }
                                }
                            }
                        } else {
                            isRoleEditable = false; // Site Admin Role. cannot be edited.
                        }

                        CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
                        if( isRoleEditable && checkPermission.can(Perm.EDIT_ROLE_PERMISSION ) ) {



                            String sRoleName = ParseUtil.checkNull(request.getParameter("roleName"));
                            String[] sParamChecked = request.getParameterValues("perm_checkbox");
                            if( Utility.isNullOrEmpty(sRoleName)) {
                                Text errorText = new ErrorText("Please select a valid role name.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            } else if (sParamChecked == null || (sParamChecked!=null && sParamChecked.length <= 0) ) {
                                Text errorText = new ErrorText("Please assign at least one permission to the role.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }  else  {
                                Constants.USER_TYPE loggedInUserType = loggedInUserBean.getUserType();

                                userRolePermRequest.setUserType(loggedInUserType);
                                userRolePermRequest.setRoleName( sRoleName );
                                userRolePermRequest.setParentId( ParseUtil.checkNull(loggedInUserBean.getParentId()) );

                                ArrayList<String> arrPermissionId = new ArrayList<String>();
                                for(String paramCheck : sParamChecked ) {
                                    arrPermissionId.add( paramCheck );
                                }
                                userRolePermRequest.setArrPermissionId( arrPermissionId );





                                UserRolePermission userRolePermission = new UserRolePermission();
                                RolesBean roleBean  = userRolePermission.saveRolePersmissions( userRolePermRequest );

                                if( roleBean!=null && !Utility.isNullOrEmpty(roleBean.getRoleId()) ) {
                                    jsonResponseObj.put("role_id",roleBean.getRoleId() );
                                    Text okText = new OkText("Your changes were saved successfully.","status_mssg") ;
                                    arrOkText.add(okText);
                                    responseStatus = RespConstants.Status.OK;
                                } else {
                                    Text errorText = new ErrorText("Oops!! We were unable to complete your request. Please try again later.(saveRole - 004)","err_mssg") ;
                                    arrErrorText.add(errorText);

                                    responseStatus = RespConstants.Status.ERROR;
                                }
                            }
                        } else {
                            appLogging.error("No Permission to View Role Permission : " + sRoleId + " - " + ParseUtil.checkNullObject(loggedInUserBean) );
                            Text errorText = new ErrorText("Oops!! Please make sure you are authorized to execute this action.(saveRole - 003)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request. Please use a valid role id.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveRole - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveRole - 001)","err_mssg") ;
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