package com.events.proc.vendors.team;

import com.events.bean.users.*;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.users.EditUserException;
import com.events.common.exception.users.EditUserInfoException;
import com.events.common.exception.users.ManagePasswordException;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import com.events.users.BuildUsers;
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
 * Date: 1/31/14
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveTeamMember  extends HttpServlet {
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

                    VendorRequestBean vendorRequestBean = new VendorRequestBean();
                    vendorRequestBean.setUserId(sUserId);

                    AccessVendors accessVendors = new AccessVendors();
                    VendorBean vendorBean = accessVendors.getVendorByUserId(vendorRequestBean);

                    if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {
                        String sTeamMemberUserId = ParseUtil.checkNull(request.getParameter("user_id"));
                        String sTeamMemberUserInfoId = ParseUtil.checkNull(request.getParameter("userinfo_id"));
                        String sTeamMemberFirstName = ParseUtil.checkNull(request.getParameter("team_memberFirstName"));
                        String sTeamMemberLastName = ParseUtil.checkNull(request.getParameter("team_memberLastName"));
                        String sTeamMemberEmail = ParseUtil.checkNull(request.getParameter("team_memberEmail"));
                        String sTeamMemberCellPhone = ParseUtil.checkNull(request.getParameter("team_memberCellPhone"));
                        String sTeamMemberPhone = ParseUtil.checkNull(request.getParameter("team_memberWorkPhone"));
                        String sTeamMemberRole = ParseUtil.checkNull(request.getParameter("team_memberRole"));

                        boolean isError = false;
                        if( Utility.isNullOrEmpty(sTeamMemberEmail) || Utility.isNullOrEmpty(sTeamMemberFirstName)  || Utility.isNullOrEmpty(sTeamMemberRole) ) {
                            Text errorText = new ErrorText("Please fill in all the required fields","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                            isError = true;
                        } else {

                            UserRequestBean userRequestBean = new UserRequestBean();
                            userRequestBean.setUserId( sTeamMemberUserId );
                            userRequestBean.setUserInfoId( sTeamMemberUserInfoId );
                            userRequestBean.setEmail( sTeamMemberEmail );
                            userRequestBean.setParentId( vendorBean.getVendorId() );
                            userRequestBean.setUserType( Constants.USER_TYPE.VENDOR );
                            userRequestBean.setFirstName(sTeamMemberFirstName);
                            userRequestBean.setLastName(sTeamMemberLastName);
                            userRequestBean.setCellPhone(sTeamMemberCellPhone);
                            userRequestBean.setWorkPhone(sTeamMemberPhone);
                            userRequestBean.setPlanner(true);

                            ArrayList<String> arrRoleId = new ArrayList<String>();
                            arrRoleId.add(sTeamMemberRole);
                            userRequestBean.setArrRoleId(arrRoleId );


                            //Check to see if email has been used previously
                            AccessUsers accessUsers = new AccessUsers();
                            UserBean tmpExistingUserBean = accessUsers.getUserByEmail(userRequestBean);

                            if(Utility.isNullOrEmpty(sTeamMemberUserId)) {
                                // This indicates the team member needs to be added.

                                if(tmpExistingUserBean!=null && !Utility.isNullOrEmpty(tmpExistingUserBean.getUserId())
                                        && tmpExistingUserBean.getUserType() == Constants.USER_TYPE.VENDOR ) {
                                    Text errText = new ErrorText("This email address is already exists. Please use  a different email address.","err_mssg") ;
                                    arrErrorText.add(errText);

                                    responseStatus = RespConstants.Status.ERROR;
                                    isError = true;
                                } else {
                                    PasswordRequestBean passwordRequestBean = new PasswordRequestBean();
                                    passwordRequestBean.setPassword(Utility.getNewGuid());
                                    userRequestBean.setPasswordRequestBean(passwordRequestBean);

                                    BuildUsers buildUsers = new BuildUsers();
                                    try {
                                        UserBean userBean = buildUsers.createTeamMember(userRequestBean);
                                        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
                                            userRequestBean.setUserId(userBean.getUserId());
                                            ForgotPasswordBean forgotPasswordBean = buildUsers.createNewTeamMemberPasswordUserRequest(vendorBean ,userRequestBean);

                                            if(forgotPasswordBean!=null && !Utility.isNullOrEmpty(forgotPasswordBean.getForgotPasswordId())){

                                                jsonResponseObj.put("user_id",userBean.getUserId() ) ;
                                                jsonResponseObj.put("userinfo_id",userBean.getUserInfoId())  ;
                                            }  else {
                                                appLogging.error("Could not send Email for Password and Login to Team Member" );
                                                isError = true;
                                            }
                                        } else {
                                            appLogging.error("Could not create User " );
                                            isError = true;
                                        }
                                    } catch (EditUserException e) {
                                        appLogging.error("Could not create User " + ExceptionHandler.getStackTrace(e));
                                        isError = true;
                                    } catch (EditUserInfoException e) {
                                        appLogging.error("Could not create User Info Data " + ExceptionHandler.getStackTrace(e));
                                        isError = true;
                                    } catch (ManagePasswordException e) {
                                        appLogging.error("Creation of Password failed " + ExceptionHandler.getStackTrace(e));
                                        isError = true;
                                    }
                                }

                            } else {
                                if(tmpExistingUserBean!=null && !Utility.isNullOrEmpty(tmpExistingUserBean.getUserId())
                                        && tmpExistingUserBean.getUserType() == Constants.USER_TYPE.VENDOR && !tmpExistingUserBean.getUserId().equalsIgnoreCase(sTeamMemberUserId) ) {
                                    Text errText = new ErrorText("This email address is already exists. Please use  a different email address.","err_mssg") ;
                                    arrErrorText.add(errText);

                                    responseStatus = RespConstants.Status.ERROR;
                                    isError = true;

                                    // This indicates that another account with same email address exists.
                                    // This will ignore the condition where it returns the team members own userbean(userid matches so ignore)
                                } else {
                                    BuildUsers buildUsers = new BuildUsers();
                                    try {
                                        UserBean userBean = buildUsers.updateTeamMemberUserDetails(userRequestBean);
                                        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
                                            jsonResponseObj.put("user_id",userBean.getUserId() ) ;
                                            jsonResponseObj.put("userinfo_id",userBean.getUserInfoId() ) ;
                                        } else {
                                            appLogging.error("Could not update User " );
                                            isError = true;
                                        }
                                    } catch (EditUserException e) {
                                        appLogging.error("Could not create User " + ExceptionHandler.getStackTrace(e));
                                        isError = true;
                                    } catch (EditUserInfoException e) {
                                        appLogging.error("Could not create User Info Data " + ExceptionHandler.getStackTrace(e));
                                        isError = true;
                                    } catch (ManagePasswordException e) {
                                        appLogging.error("Creation of Password failed " + ExceptionHandler.getStackTrace(e));
                                        isError = true;
                                    }
                                }
                            }

                            if(!isError) {
                                Text okText = new OkText("You team member was successfully saved.","status_mssg") ;
                                arrOkText.add(okText);

                                responseStatus = RespConstants.Status.OK;
                            } else {
                                appLogging.error("An error occurred while creating a new team member." + ParseUtil.checkNullObject(userRequestBean));
                                Text errText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.","err_mssg") ;
                                arrErrorText.add(errText);

                                responseStatus = RespConstants.Status.ERROR;
                            }

                        }

                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveTeamMember - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveTeamMember - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveTeamMember - 001)","err_mssg") ;
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
