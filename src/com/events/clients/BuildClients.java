package com.events.clients;


import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.clients.EditClientException;
import com.events.common.exception.users.EditUserException;
import com.events.common.exception.users.EditUserInfoException;
import com.events.data.clients.BuildClientData;
import com.events.data.users.BuildUserData;
import com.events.users.BuildUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/17/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildClients {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ClientResponseBean createClient(ClientRequestBean clientRequestBean) throws EditClientException {
        ClientResponseBean clientResponseBean = new ClientResponseBean();
        int iNumOfRecords = 0;
        if(clientRequestBean!=null) {


            // generating a simple client bean
            ClientBean clientBean = new ClientBean();
            clientBean.setClientId(Utility.getNewGuid());

            UserRequestBean userRequestBean = clientRequestBean.getUserRequestBean();
            userRequestBean.setParentId(clientBean.getClientId());

            BuildUsers buildUsers = new BuildUsers();

            //generating a simple bean with client contact details
            UserInfoBean userInfoBean = buildUsers.generateNewUserInfoBean( userRequestBean );
            userRequestBean.setUserInfoId( userInfoBean.getUserInfoId() );

            //generating a simple bean with client user details
            UserBean userBean = buildUsers.generateNewUserBean( userRequestBean );
            userBean.setUserType(Constants.USER_TYPE.CLIENT);
            userBean.setParentId( clientBean.getClientId() );

            try {

                buildUsers.createUserInfo(userInfoBean);
                buildUsers.createUser(userBean);

                clientBean.setClientName(clientRequestBean.getClientName());
                clientBean.setUserBeanId( userBean.getUserId() );
                clientBean.setCorporateClient(clientRequestBean.isCorporateClient());
                clientBean.setVendorId(clientRequestBean.getVendorId());
                clientBean.setLead( clientRequestBean.isLead() );

                BuildClientData buildClientData = new BuildClientData();
                iNumOfRecords = buildClientData.insertClientData(clientBean);
                if(iNumOfRecords<=0) {
                    appLogging.error("Client record was not created  ");
                    throw new EditClientException();
                }  else {
                    appLogging.info("Creating Client RecordClient Id " );
                    clientResponseBean.setUserId(userBean.getUserId());
                    clientResponseBean.setUserInfoId(userBean.getUserInfoId());
                    clientResponseBean.setClientId(clientBean.getClientId());
                }

            } catch (EditUserInfoException e) {
                appLogging.error("Exception occurred while creating UserInfo record for Client : " + ExceptionHandler.getStackTrace(e));
                throw new EditClientException();
            } catch (EditUserException e) {
                appLogging.error("Exception occurred while creating User record for Client : " + ExceptionHandler.getStackTrace(e));
                throw new EditClientException();
            }


        }
        return  clientResponseBean;
    }

    public ClientResponseBean saveClient( ClientRequestBean clientRequestBean ) throws EditClientException {
        ClientResponseBean clientResponseBean = new ClientResponseBean();
        Integer iNumOfRecords = 0;
        if(clientRequestBean!=null && !"".equalsIgnoreCase(clientRequestBean.getClientId()) ) {
            clientResponseBean = updateClient(clientRequestBean);
        } else {
            clientResponseBean = createClient(clientRequestBean);
        }
        return clientResponseBean;
    }

    public ClientResponseBean updateClient(ClientRequestBean clientRequestBean) throws EditClientException {
        ClientResponseBean clientResponseBean = new ClientResponseBean();
        int iNumOfRecords = 0;
        if(clientRequestBean!=null) {

            UserRequestBean userRequestBean = clientRequestBean.getUserRequestBean();
            BuildUsers buildUsers = new BuildUsers();

            //generating a simple bean with client contact details
            UserInfoBean userInfoBean = buildUsers.generateExistingUserInfoBean(userRequestBean);
            userInfoBean.setUserInfoId( userRequestBean.getUserInfoId() );

            //generating a simple bean with client user details
            UserBean userBean = buildUsers.generateExistingUserBean(userRequestBean);
            userBean.setUserId( userRequestBean.getUserId() );

            try {
                buildUsers.updateUserInfo(userInfoBean);
                buildUsers.updateUser(userBean);
                appLogging.info("Client Id userinfo bean: " + userInfoBean.getUserInfoId() + " emailk : " + userInfoBean.getEmail() );

                // generating a simple client bean
                ClientBean clientBean = new ClientBean();
                clientBean.setClientId(clientRequestBean.getClientId());
                clientBean.setClientName(clientRequestBean.getClientName());
                clientBean.setUserBeanId( userBean.getUserId() );
                clientBean.setCorporateClient(clientRequestBean.isCorporateClient());
                clientBean.setVendorId(clientRequestBean.getVendorId());
                clientBean.setLead( clientRequestBean.isLead() );

                BuildClientData buildClientData = new BuildClientData();
                iNumOfRecords = buildClientData.updateClientData(clientBean);
                if(iNumOfRecords<=0) {
                    appLogging.error("Client record was not updated");
                    throw new EditClientException();
                } else {
                    appLogging.info("Client Id records update complete "  );
                    clientResponseBean.setUserId(userBean.getUserId());
                    clientResponseBean.setUserInfoId(userBean.getUserInfoId());
                    clientResponseBean.setClientId(clientBean.getClientId());
                }
            } catch (EditUserInfoException e) {
                appLogging.error("Exception occurred while updating UserInfo record for Client : " + ExceptionHandler.getStackTrace(e));
                throw new EditClientException();
            } catch (EditUserException e) {
                appLogging.error("Exception occurred while updating User record for Client : " + ExceptionHandler.getStackTrace(e));
                throw new EditClientException();
            }
        }
        return  clientResponseBean;
    }

    public boolean deleteClient(ClientRequestBean clientRequestBean) {
        boolean isSuccess = false;
        if(clientRequestBean!=null && !Utility.isNullOrEmpty(clientRequestBean.getClientId())) {
            ClientBean clientBean = new ClientBean();
            clientBean.setClientId( clientRequestBean.getClientId() );
            BuildClientData buildClientData = new BuildClientData();
            Integer iNumOfRowsDeleted = buildClientData.deleteClientData(clientBean);
            if(iNumOfRowsDeleted>0) {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    public boolean updateLeadStatusForClient( ClientRequestBean clientRequestBean){
        boolean isSuccess = false;
        if(clientRequestBean!=null && !Utility.isNullOrEmpty(clientRequestBean.getClientId())) {
            BuildClientData buildClientData = new BuildClientData();
            Integer iNumOfRowsUpdated = buildClientData.updateLeadStatusOfClient(clientRequestBean);
            if(iNumOfRowsUpdated>0) {
                isSuccess = true;
            }
        }
        return isSuccess;
    }
}
