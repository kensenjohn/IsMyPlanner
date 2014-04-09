package com.events.common;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utility {
    public static String getNewGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static Integer getRandomInteger(Integer iLimit) {

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(iLimit);
        return randomInt;
    }

    public static boolean isNullOrEmpty(String sText) {
        boolean isNullOrEmpty = true;
        if( sText!=null && !"".equalsIgnoreCase(sText)) {
            isNullOrEmpty = false;
        }
        return isNullOrEmpty;
    }

    public static String getHumanFormattedNumber(String sTelephoneNum )
    {
        return getHumanFormattedNumber(sTelephoneNum, "US");
    }

    public static String getHumanFormattedNumber(String sTelephoneNum, String sCountryCode)
    {
        String sHumanFormattedPhones = "";
        if(sTelephoneNum!=null && !"".equalsIgnoreCase(sTelephoneNum) && sCountryCode!=null && !"".equalsIgnoreCase(sCountryCode))
        {
            try {
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                com.google.i18n.phonenumbers.Phonenumber.PhoneNumber apiPhoneNumber = phoneUtil.parse(sTelephoneNum, sCountryCode);
                sHumanFormattedPhones = phoneUtil.format(apiPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            }
            catch(Exception e)
            {
                sHumanFormattedPhones = "";
            }
        }
        return sHumanFormattedPhones;
    }
    public static StringBuilder dumpRequestParameters(HttpServletRequest request) {
        StringBuilder strRequestParams = new StringBuilder();
        if(request!=null){
            Map<String,String[]> mapRequest = request.getParameterMap();
            for(Map.Entry<String,String[]> mapEntrysetRequest : mapRequest.entrySet()) {
                strRequestParams.append( mapEntrysetRequest.getKey() ).append("=[");
                String[] arrRequestValue = mapEntrysetRequest.getValue();
                if(arrRequestValue!=null && arrRequestValue.length > 0 ) {
                    boolean isFirst = true;
                    for(String requestVal : arrRequestValue ) {
                        if(!isFirst) {
                            strRequestParams.append("|");
                        }
                        strRequestParams.append(requestVal);
                        isFirst = false;
                    }
                }
                strRequestParams.append("]\n");
            }
        }
        return strRequestParams;
    }

    public static String getImageUploadHost() {
        Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
        String imageHost = ParseUtil.checkNull(applicationConfig.get(Constants.IMAGE_HOST));
        return imageHost;
    }

    public static String getS3Bucket() {
        Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
        String s3Bucket = ParseUtil.checkNull(applicationConfig.get(Constants.AMAZON.S3_BUCKET.getPropName()));
        return s3Bucket;
    }

    public static String getFileUploadHost() {
        Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
        String fileHost = ParseUtil.checkNull(applicationConfig.get(Constants.FILE_HOST));
        return fileHost;
    }


    public static String generateRandomPassword(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public static String getGivenName(String firstName, String lastName) {
        String sGivenName = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(firstName)){
            sGivenName = sGivenName + firstName;
        }

        if(!Utility.isNullOrEmpty(lastName)){
            sGivenName = sGivenName + " " + lastName;
        }
        return  ParseUtil.checkNull(sGivenName);
    }

    public static String createSiteDomainUrl(String sSubDomain){
        Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
        String APPLICATION_DOMAIN = applicationConfig.get(Constants.APPLICATION_DOMAIN);
        String APPLICATION_PROTOCOL = applicationConfig.get(Constants.PROP_LINK_PROTOCOL,"https");

        String sHomeUrl = APPLICATION_DOMAIN;
        if(Utility.isNullOrEmpty(sSubDomain) && !"localhost".equalsIgnoreCase(APPLICATION_DOMAIN) ){
            sSubDomain = "www";
        }
        if(!Utility.isNullOrEmpty(sSubDomain)){
            sHomeUrl = sSubDomain+"."+APPLICATION_DOMAIN;
        }
        sHomeUrl = APPLICATION_PROTOCOL+"://"+sHomeUrl;
        return sHomeUrl;
    }

}
