package com.events.users;

import com.events.bean.users.PasswordBean;
import com.events.bean.users.PasswordRequestBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.users.ManagePasswordException;
import com.events.data.users.ManageUserPasswordData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageUserPassword {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    private String generatePasswordHash(String sPassword) {
        String hashedPassword = Constants.EMPTY;
        try{
            hashedPassword = BCrypt.hashpw(sPassword, BCrypt.gensalt(8, getRandomSalt()) ); // http://codahale.com/how-to-safely-store-a-password/
        } catch (UnsupportedEncodingException e) {
            appLogging.error("UnsupportedEncodingException : " + ExceptionHandler.getStackTrace(e));
        } catch (NoSuchAlgorithmException e) {
            appLogging.error("NoSuchAlgorithmException : " + ExceptionHandler.getStackTrace(e));
        } catch (NoSuchProviderException e) {
            appLogging.error("NoSuchProviderException : " + ExceptionHandler.getStackTrace(e));
        }
        return hashedPassword;
    }

    private SecureRandom getRandomSalt() throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        Long randomLong = random.nextLong();

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG","SUN"); // https://www.cigital.com/justice-league-blog/2009/08/14/proper-use-of-javas-securerandom/
        secureRandom.setSeed(randomLong.toString().getBytes("us-ascii"));

        return secureRandom;

    }

    public Integer  createPassword( PasswordRequestBean passwordRequest ) throws ManagePasswordException {
        Integer iNumOfRows = 0;
        if( passwordRequest!=null && !"".equalsIgnoreCase(passwordRequest.getPassword())) {
            passwordRequest.setHashedPassword(generatePasswordHash(passwordRequest.getPassword()));
            passwordRequest.setPasswordId(Utility.getNewGuid());

            ManageUserPasswordData manageUserPasswordData = new ManageUserPasswordData();
            iNumOfRows = manageUserPasswordData.insertPassword( passwordRequest );
            if(iNumOfRows<=0) {
                appLogging.error("Creation of Password failed " + ParseUtil.checkNullObject(passwordRequest));
                throw new ManagePasswordException();
            }
        } else {
            appLogging.error("Missing Data: Password creation failed ");
            throw new ManagePasswordException();
        }
        return iNumOfRows;
    }

    public Integer updatePassword( PasswordRequestBean passwordRequest ) throws ManagePasswordException {
        Integer iNumOfRows = 0;
        if( passwordRequest!=null && !Utility.isNullOrEmpty(passwordRequest.getPassword()) && !Utility.isNullOrEmpty(passwordRequest.getUserId()) &&
                passwordRequest.getPasswordStatus()!=null ) {
            passwordRequest.setHashedPassword(generatePasswordHash(passwordRequest.getPassword()));

            ManageUserPasswordData manageUserPasswordData = new ManageUserPasswordData();
            iNumOfRows = manageUserPasswordData.updatePassword(passwordRequest);
        }
        return iNumOfRows;
    }

    public PasswordBean getUserPassword( PasswordRequestBean passwordRequest ) {
        PasswordBean passwordBean = new PasswordBean();

        if( passwordRequest!=null  && !"".equalsIgnoreCase(passwordRequest.getUserId())) {
            ManageUserPasswordData manageUserPasswordData = new ManageUserPasswordData();
            passwordBean = manageUserPasswordData.getPassword(passwordRequest);

        }
        return passwordBean;
    }

    public boolean isUserPasswordAuthenticated( PasswordRequestBean passwordRequest ) {
        boolean isAuthenticated = false;
        if( passwordRequest!=null  && !"".equalsIgnoreCase(passwordRequest.getUserId()) && !"".equalsIgnoreCase(passwordRequest.getPassword()) ) {
            PasswordBean passwordBean = getUserPassword(passwordRequest);
            if(passwordBean!=null && !"".equalsIgnoreCase(passwordBean.getPasswordId())) {
                isAuthenticated = BCrypt.checkpw(passwordRequest.getPassword(), passwordBean.getHashedPassword());
            }
        }
        return isAuthenticated;
    }

    public void sendResetPasswordLink( PasswordRequestBean passwordRequest) {
        if(passwordRequest!=null && !Utility.isNullOrEmpty(passwordRequest.getEmailAddress())) {

        }
    }
}
