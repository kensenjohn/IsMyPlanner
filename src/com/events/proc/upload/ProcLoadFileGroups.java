package com.events.proc.upload;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.common.files.*;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.clients.AccessClients;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.files.AccessSharedFiles;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/1/14
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadFileGroups  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
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

                    SharedFilesRequestBean sharedFilesRequestBean = new SharedFilesRequestBean();
                    AccessUsers accessUser = new AccessUsers();
                    SharedFilesResponseBean sharedFilesResponseBean = new SharedFilesResponseBean();
                    ParentTypeBean parentTypeBean = accessUser.getParentTypeBeanFromUser( loggedInUserBean );
                    AccessSharedFiles accessSharedFiles = new AccessSharedFiles();
                    if(parentTypeBean!=null) {


                        if( parentTypeBean.isUserAVendor() ) {
                            sharedFilesRequestBean.setVendorId( parentTypeBean.getVendorId() );
                            sharedFilesResponseBean = accessSharedFiles.getVendorsSharedFileGroup(sharedFilesRequestBean);
                        } else if ( parentTypeBean.isUserAClient() ) {
                            sharedFilesRequestBean.setClientId(parentTypeBean.getClientdId());
                            sharedFilesResponseBean = accessSharedFiles.getClientsSharedFileGroup(sharedFilesRequestBean);
                        }
                    }

                    if(sharedFilesResponseBean!=null && sharedFilesResponseBean.getArrSharedFilesGroupBean()!=null && !sharedFilesResponseBean.getArrSharedFilesGroupBean().isEmpty() ) {
                        ArrayList<SharedFilesGroupBean>  arrSharedFilesGroupBean = sharedFilesResponseBean.getArrSharedFilesGroupBean();
                        if(arrSharedFilesGroupBean!=null && !arrSharedFilesGroupBean.isEmpty()){
                            JSONObject jsonFileGroup = new JSONObject();
                            JSONObject jsonFileClients = new JSONObject();
                            for(SharedFilesGroupBean sharedFilesGroupBean : arrSharedFilesGroupBean ) {
                                sharedFilesRequestBean.setSharedFilesGroupId( sharedFilesGroupBean.getSharedFilesGroupId() );

                                ArrayList<SharedFilesBean> arrSharedFilesBean = accessSharedFiles.getSharedFiles( sharedFilesRequestBean );

                                if(arrSharedFilesBean!=null){
                                    jsonFileGroup.put( sharedFilesGroupBean.getSharedFilesGroupId() , arrSharedFilesBean.size() );
                                }

                                ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean = accessSharedFiles.getSharedFilesViewers(sharedFilesRequestBean);
                                if(arrSharedFilesViewersBean!=null && !arrSharedFilesViewersBean.isEmpty()){
                                    JSONObject jsonClientObject = new JSONObject();
                                    Integer iNumOfClients = 0;
                                    for(SharedFilesViewersBean sharedFilesViewersBean : arrSharedFilesViewersBean ){
                                        String sClientId = ParseUtil.checkNull(sharedFilesViewersBean.getParentId());

                                        ClientRequestBean clientRequestBean = new ClientRequestBean();
                                        clientRequestBean.setClientId( sClientId );

                                        AccessClients accessClients = new AccessClients();
                                        ClientBean clientBean = accessClients.getClient( clientRequestBean );

                                        if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getClientId())) {
                                            jsonClientObject.put( ParseUtil.iToS(iNumOfClients) , ParseUtil.checkNull( clientBean.getClientName() ) );
                                            iNumOfClients++;
                                        }
                                    }
                                    jsonFileClients.put( sharedFilesGroupBean.getSharedFilesGroupId()+"_count", iNumOfClients );
                                    jsonFileClients.put(  sharedFilesGroupBean.getSharedFilesGroupId(), jsonClientObject );
                                }

                            }
                            jsonResponseObj.put("file_count_obj", jsonFileGroup);
                            jsonResponseObj.put("file_group_client_obj", jsonFileClients);
                        }
                        JSONObject jsonFilesGroupBean = accessSharedFiles.getJsonSharedFilesGroups(sharedFilesResponseBean.getArrSharedFilesGroupBean());
                        if(jsonFilesGroupBean!=null){
                            Integer iNumOfFilesGroups = jsonFilesGroupBean.optInt("num_of_files_groups");
                            if(iNumOfFilesGroups>0){
                                jsonResponseObj.put( "file_group_bean" , jsonFilesGroupBean);
                            }
                            jsonResponseObj.put("num_of_files_groups", iNumOfFilesGroups);
                        }
                    }

                    Text okText = new OkText("The file groups were loaded","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveShareFile - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadWES - 001)","err_mssg") ;
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

