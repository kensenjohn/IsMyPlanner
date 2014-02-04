package com.events.proc.vendors.partner;

import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.partner.PartnerVendorBean;
import com.events.bean.vendors.partner.PartnerVendorRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.vendors.AccessVendors;
import com.events.vendors.partner.BuildPartnerVendor;
import com.sun.imageio.plugins.jpeg.JPEG;
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
 * Date: 2/4/14
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSavePartnerVendor  extends HttpServlet {
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

                    String sVendorId = ParseUtil.checkNull(request.getParameter("vendor_id"));

                    VendorBean vendorBean = new VendorBean();
                    AccessVendors accessVendors = new AccessVendors();
                    VendorRequestBean vendorRequestBean = new VendorRequestBean();
                    if(!Utility.isNullOrEmpty(sVendorId)) {
                        vendorRequestBean.setVendorId( sVendorId );
                        vendorBean = accessVendors.getVendor( vendorRequestBean );
                    } else {
                        vendorRequestBean.setUserId(sUserId);
                        vendorBean = accessVendors.getVendorByUserId(vendorRequestBean);
                    }

                    if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {

                        String sPartnerVendorId = ParseUtil.checkNull(request.getParameter("partner_vendor_id"));
                        String sPartnerId = ParseUtil.checkNull(request.getParameter("partner_id"));
                        String sPartnerVendorBusinessName = ParseUtil.checkNull(request.getParameter("partner_vendorName"));
                        String sPartnerVendorWebsite = ParseUtil.checkNull(request.getParameter("partner_vendorWebsite"));
                        String sPartnerVendorWorkPhone = ParseUtil.checkNull(request.getParameter("partner_vendorWorkPhone"));
                        String sPartnerVendorCellPhone = ParseUtil.checkNull(request.getParameter("partner_vendorCellPhone"));
                        String sPartnerVendorEmail = ParseUtil.checkNull(request.getParameter("partner_vendorEmail"));
                        String sPartnerVendorFirstName = ParseUtil.checkNull(request.getParameter("partner_vendorFirstName"));
                        String sPartnerVendorLastName = ParseUtil.checkNull(request.getParameter("partner_vendorLastName"));
                        String sPartnerVendorAddress1 = ParseUtil.checkNull(request.getParameter("partner_vendorAddress1"));
                        String sPartnerVendorAddress2 = ParseUtil.checkNull(request.getParameter("partner_vendorAddress2"));
                        String sPartnerVendorCity = ParseUtil.checkNull(request.getParameter("partner_vendorCity"));
                        String sPartnerVendorState = ParseUtil.checkNull(request.getParameter("partner_vendorState"));
                        String sPartnerVendorPostalCode = ParseUtil.checkNull(request.getParameter("partner_vendorPostalCode"));
                        String sPartnerVendoCountry = ParseUtil.checkNull(request.getParameter("partner_vendorCountry"));
                        String sPartnerVendoType = ParseUtil.checkNull(request.getParameter("partner_vendorType"));
                        String sPartnerVendoPrice = ParseUtil.checkNull(request.getParameter("partner_vendorPrice"));


                        if( Utility.isNullOrEmpty(sPartnerVendorBusinessName) || Utility.isNullOrEmpty(sPartnerVendorWorkPhone)
                                || Utility.isNullOrEmpty(sPartnerVendoType)   || Utility.isNullOrEmpty(sPartnerVendoPrice)  ) {
                            Text errorText = new ErrorText("Please fill in all the required fields","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        } else {
                            PartnerVendorRequestBean partnerVendorRequest = new PartnerVendorRequestBean();
                            partnerVendorRequest.setPartnerId( sPartnerId);
                            partnerVendorRequest.setPartnerVendorId( sPartnerVendorId);
                            partnerVendorRequest.setVendorId( vendorBean.getVendorId() );
                            partnerVendorRequest.setFirstName( sPartnerVendorFirstName);
                            partnerVendorRequest.setLastName( sPartnerVendorLastName);
                            partnerVendorRequest.setBusinessName( sPartnerVendorBusinessName);
                            partnerVendorRequest.setWebsite(sPartnerVendorWebsite);
                            partnerVendorRequest.setPhoneNum( sPartnerVendorWorkPhone);
                            partnerVendorRequest.setCellPhone(sPartnerVendorCellPhone);
                            partnerVendorRequest.setEmail(sPartnerVendorEmail);
                            partnerVendorRequest.setAddress1( sPartnerVendorAddress1);
                            partnerVendorRequest.setAddress2( sPartnerVendorAddress2 );
                            partnerVendorRequest.setCity( sPartnerVendorCity);
                            partnerVendorRequest.setState( sPartnerVendorState );
                            partnerVendorRequest.setCountry( sPartnerVendoCountry );
                            partnerVendorRequest.setZipcode( sPartnerVendorPostalCode );
                            partnerVendorRequest.setUserId( loggedInUserBean.getUserId() );
                            partnerVendorRequest.setVendorType(sPartnerVendoType);
                            partnerVendorRequest.setPrice( sPartnerVendoPrice);

                            BuildPartnerVendor buildPartnerVendor = new BuildPartnerVendor();
                            PartnerVendorBean partnerVendorBean = buildPartnerVendor.savePartnerVendor( partnerVendorRequest );
                            if(partnerVendorBean!=null && !Utility.isNullOrEmpty(partnerVendorBean.getPartnerVendorId()) &&
                                !Utility.isNullOrEmpty(partnerVendorBean.getPartnerId()) ) {

                                jsonResponseObj.put("partner_vendor_bean",partnerVendorBean.toJson());
                                Text okText = new OkText("The vendor was saved successfully.","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                appLogging.info("Unable to save the partner vendor " + partnerVendorRequest + " - " + ParseUtil.checkNullObject(partnerVendorBean) );
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(savePartnerVendor - 004)","err_mssg") ;
                                arrErrorText.add(errorText);
                                responseStatus = RespConstants.Status.ERROR;
                            }

                        }

                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(savePartnerVendor - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(savePartnerVendor - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(savePartnerVendor - 001)","err_mssg") ;
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
