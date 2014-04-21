package com.events.common;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.vendors.AccessVendors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 9:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Folder {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private final String AMAZON_ACCESS_KEY = applicationConfig.get(Constants.AMAZON.ACCESS_KEY.getPropName());
    private final String AMAZON_ACCESS_SECRET = applicationConfig.get(Constants.AMAZON.SECRET_KEY.getPropName());
    private final String AMAZON_S3_BUCKET = applicationConfig.get(Constants.AMAZON.S3_BUCKET.getPropName());

    public boolean createFolderForUser(String sFolderPath) {
        boolean isFolderCreated = false;
        if( !Utility.isNullOrEmpty(sFolderPath) ) {
            try{
                File folderLocation = new File(sFolderPath);
                isFolderCreated = folderLocation.mkdir();
            } catch (Exception e) {
                appLogging.error("Was not able to create folder : sFolderPath : " + ParseUtil.checkNull(sFolderPath) );
            }
        }
        return isFolderCreated;
    }
    public boolean createS3FolderForUser(String sFolderPath, String sRandomFilename, String sUserFolderName ) throws IOException {
        boolean isFolderCreated = false;
        appLogging.info( "S3 sFolderPath : " + sFolderPath );
        if( !Utility.isNullOrEmpty(sFolderPath) ) {
            FileInputStream stream = null;
            try{
                // Set AWS access credentials
                AmazonS3Client client = new AmazonS3Client(new BasicAWSCredentials(AMAZON_ACCESS_KEY,AMAZON_ACCESS_SECRET));

                stream = new FileInputStream(sFolderPath+"/"+sRandomFilename);
                ObjectMetadata objectMetadata = new ObjectMetadata();
                PutObjectRequest putObjectRequest = new PutObjectRequest(AMAZON_S3_BUCKET, sUserFolderName+"/"+sRandomFilename, stream, objectMetadata);
                PutObjectResult putObjectResult = client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));

                appLogging.info( "putObjectResult : " + putObjectResult.getETag() );
                if(putObjectResult!=null){
                    isFolderCreated = true;
                }
            } finally{
                if(stream!=null){
                    stream.close();
                }
            }

        }
        return isFolderCreated;
    }

    public String getFolderForUser(UserBean userBean, String location) {
        String sUserFolderName = Constants.EMPTY;
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
            Constants.USER_TYPE userType = userBean.getUserType();
            String parentId = userBean.getParentId();

            String sFolderName = getFolderName(userType , parentId );
            String sFolderPath = getFolderPath(location, sFolderName);
            boolean isFolderCreated = false;
            boolean isFolderExists = isFolderExists( sFolderPath );
            if( !isFolderExists ) {
                isFolderCreated = createFolderForUser(sFolderPath);
            }

            if(isFolderCreated || isFolderExists  ) {
                sUserFolderName =  sFolderName;
            }
        }
        return sUserFolderName;
    }

    public String getFolderName(Constants.USER_TYPE userType, String parentId) {
        String sFolderName = Constants.EMPTY;
        if(userType.getType().equalsIgnoreCase(Constants.USER_TYPE.VENDOR.getType())) {
            VendorRequestBean vendorRequestBean = new VendorRequestBean();
            vendorRequestBean.setVendorId( parentId );

            AccessVendors vendor = new AccessVendors();
            VendorBean vendorBean = vendor.getVendor(vendorRequestBean);
            if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {
                sFolderName = ParseUtil.checkNull(vendorBean.getFolder()) ;
            }

        } else if(userType.getType().equalsIgnoreCase(Constants.USER_TYPE.ADMIN.getType())) {

        } else if(userType.getType().equalsIgnoreCase(Constants.USER_TYPE.CLIENT.getType())) {

        }
        return sFolderName;
    }

    public static boolean isFolderExists(String sFolderPath) {
        boolean isFolderExists = false;
        if(!Utility.isNullOrEmpty(sFolderPath)){
            try{
                File folderLocation = new File(sFolderPath);
                isFolderExists = folderLocation.exists();
            } catch (Exception e) {
                appLogging.error("Was not able to check if folder exists : sFolderPath : " + ParseUtil.checkNull(sFolderPath)  );
            }

        }
        return isFolderExists;
    }

    private String getFolderPath(String location , String sFolderName) {
        String sFolderPath= Constants.EMPTY;
        if(!Utility.isNullOrEmpty(location)) {
            sFolderPath = location;
        }

        if( !Utility.isNullOrEmpty(sFolderPath) ) {
            if(!sFolderPath.endsWith("/")) {
                sFolderPath = sFolderPath + "/";
            }
            sFolderPath = sFolderPath + sFolderName;
        }
        return sFolderPath;
    }
}
