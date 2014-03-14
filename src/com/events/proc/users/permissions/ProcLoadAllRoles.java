package com.events.proc.users.permissions;

import com.events.bean.users.UserBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.bean.users.permissions.UserRolePermissionResponseBean;
import com.events.bean.users.permissions.UserRolesBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.permissions.AccessRoles;
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
 * User: root
 * Date: 1/29/14
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadAllRoles extends HttpServlet {
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

                if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {

                    VendorRequestBean vendorRequestBean = new VendorRequestBean();
                    vendorRequestBean.setUserId( loggedInUserBean.getUserId() );
                    AccessVendors accessVendors = new AccessVendors();
                    VendorBean vendorBean = accessVendors.getVendorByUserId( vendorRequestBean );

                    UserRolePermissionRequestBean userRolePermRequest = new UserRolePermissionRequestBean();
                    userRolePermRequest.setUserId( loggedInUserBean.getUserId() );
                    userRolePermRequest.setParentId( vendorBean.getVendorId() );

                    UserRolePermission userRolePermission = new UserRolePermission();
                    UserRolePermissionResponseBean userRolePermissionResponseBean = userRolePermission.loadRoleDetails(userRolePermRequest);
                    if(userRolePermissionResponseBean!=null) {
                        JSONObject jsonEveryRoleDetail = userRolePermission.loadRoleDetailsJson(userRolePermissionResponseBean.getArrEveryRoleDetailBean());

                        Integer iNumOfRoleObjs =  0;
                        if(jsonEveryRoleDetail!=null) {
                            iNumOfRoleObjs =  jsonEveryRoleDetail.optInt("num_of_roles");
                            jsonResponseObj.put("every_role",jsonEveryRoleDetail);
                        }
                        jsonResponseObj.put("num_of_roles" , ParseUtil.iToS(iNumOfRoleObjs) );

                        AccessUserRoles accessUserRoles = new AccessUserRoles();
                        ArrayList<UserRolesBean> arrUserRolesBean = accessUserRoles.getUserRolesByUserId( loggedInUserBean );
                        if(arrUserRolesBean!=null && !arrUserRolesBean.isEmpty() ) {
                            JSONObject jsonUserRole = new JSONObject();
                            Integer iNumOfRoles = 0 ;
                            for(UserRolesBean userRolesBean : arrUserRolesBean ) {
                                jsonUserRole.put(iNumOfRoles.toString() , userRolesBean.toJson());
                                iNumOfRoles++;
                            }
                            jsonUserRole.put("total_logged_in_user_roles", iNumOfRoles);
                            jsonResponseObj.put("logged_in_user_role" , jsonUserRole );
                        }


                        Text okText = new OkText("All Roles were successfully loaded","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
                    } else {
                        appLogging.info("Invalid request in Proc Page (userRolePermissionResponseBean)" + ParseUtil.checkNullObject(userRolePermissionResponseBean) );
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllRoles - 002)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllRoles - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllRoles - 001)","err_mssg") ;
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