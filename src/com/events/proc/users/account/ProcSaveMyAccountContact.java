package com.events.proc.users.account;

import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import com.events.users.BuildUsers;
import com.events.vendors.AccessVendors;
import com.events.vendors.BuildVendors;
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
 * Date: 3/6/14
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveMyAccountContact   extends HttpServlet {
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

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
                    String email = ParseUtil.checkNull(request.getParameter("email"));
                    String firstName = ParseUtil.checkNull(request.getParameter("first_name"));
                    String lastName = ParseUtil.checkNull(request.getParameter("last_name"));
                    String company = ParseUtil.checkNull(request.getParameter("company"));
                    String cellPhone = ParseUtil.checkNull(request.getParameter("cell_phone"));
                    String website = ParseUtil.checkNull(request.getParameter("website"));
                    String phoneNum = ParseUtil.checkNull(request.getParameter("phone_num"));
                    String address1 = ParseUtil.checkNull(request.getParameter("address1"));
                    String address2 = ParseUtil.checkNull(request.getParameter("address2"));
                    String city = ParseUtil.checkNull(request.getParameter("city"));
                    String state = ParseUtil.checkNull(request.getParameter("state"));
                    String zipcode = ParseUtil.checkNull(request.getParameter("zipcode"));
                    String country = ParseUtil.checkNull(request.getParameter("country"));
                    String userInfoId = ParseUtil.checkNull(request.getParameter("userinfo_id"));
                    String userId = ParseUtil.checkNull(request.getParameter("user_id"));
                    String userType = ParseUtil.checkNull(request.getParameter("user_type"));
                    String timeZone = ParseUtil.checkNull(request.getParameter("user_timezone"));

                    if(!Utility.isNullOrEmpty(userId) && loggedInUserBean.getUserId().equalsIgnoreCase(userId)) {
                        UserRequestBean userRequestBean = new UserRequestBean();
                        userRequestBean.setUserId( userId );
                        userRequestBean.setUserInfoId(userInfoId);
                        userRequestBean.setEmail(email);
                        userRequestBean.setFirstName(firstName);
                        userRequestBean.setLastName(lastName);
                        userRequestBean.setCompanyName(company);
                        userRequestBean.setCellPhone(cellPhone);
                        userRequestBean.setWorkPhone(phoneNum);
                        userRequestBean.setAddress1( address1 );
                        userRequestBean.setAddress2(address2);
                        userRequestBean.setCity( city);
                        userRequestBean.setState( state );
                        userRequestBean.setPostalCode( zipcode );
                        userRequestBean.setCountry( country );
                        userRequestBean.setWebsite( website );
                        userRequestBean.setTimeZone( timeZone );

                        boolean isSaveUserAccountAllowed = false;
                        AccessUsers accessUsers = new AccessUsers();
                        UserBean userBean = accessUsers.getUserByEmail( userRequestBean );
                        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())){
                            if(userId.equalsIgnoreCase( userBean.getUserId() )){
                                isSaveUserAccountAllowed = true;
                            }  else {
                                isSaveUserAccountAllowed = false;

                                Text errorText = new ErrorText("The email entered is not available. Please use a different email address.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        } else {
                            isSaveUserAccountAllowed = true;
                        }


                        if(isSaveUserAccountAllowed){
                            BuildUsers buildUsers = new BuildUsers();
                            UserInfoBean userInfoBean =  buildUsers.generateExistingUserInfoBean( userRequestBean );
                            Integer iNumOfRecords = buildUsers.updateUserInfo( userInfoBean );
                            if(iNumOfRecords>0){
                                HttpSession loggedInUserSession = request.getSession(false);

                                if(loggedInUserSession!=null){

                                    if(!Utility.isNullOrEmpty(userRequestBean.getCompanyName())){
                                        ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser( loggedInUserBean );
                                        if( parentTypeBean!=null && parentTypeBean.isUserAVendor() && parentTypeBean.getVendorBean()!=null ){
                                            VendorBean vendorBean = parentTypeBean.getVendorBean();
                                            vendorBean.setVendorName( userRequestBean.getCompanyName() );

                                            BuildVendors buildVendors = new BuildVendors();
                                            buildVendors.updateVendor( vendorBean );

                                            request.getSession().removeAttribute("SUBDOMAIN_VENDOR");
                                            request.getSession().setAttribute("SUBDOMAIN_VENDOR",vendorBean);
                                        }
                                    }

                                    loggedInUserBean.setUserInfoBean( userInfoBean );

                                    request.getSession().removeAttribute(Constants.USER_LOGGED_IN_BEAN);
                                    request.getSession().setAttribute(Constants.USER_LOGGED_IN_BEAN,loggedInUserBean);

                                }

                                jsonResponseObj.put("is_saved" , true );
                                Text okText = new OkText("User Contact Info saved","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                appLogging.info("Invalid User Info Bean for logged in User " + ParseUtil.checkNullObject(loggedInUserBean) );
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAcc - 003)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        } else {
                            responseStatus = RespConstants.Status.ERROR;
                        }

                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAcc - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAcc - 001)","err_mssg") ;
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