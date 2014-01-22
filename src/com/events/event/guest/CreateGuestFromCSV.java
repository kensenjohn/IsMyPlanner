package com.events.event.guest;

import au.com.bytecode.opencsv.CSVReader;
import com.events.bean.event.guest.*;
import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.common.*;
import com.events.data.event.guest.BuildGuestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/22/14
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateGuestFromCSV {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    String fileUploadLocation = applicationConfig.get(Constants.FILE_UPLOAD_LOCATION);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public CreateGuestFromCSVResponseBean isFileTooBig( CreateGuestFromCSVRequestBean createGuestFromCSVRequestBean ) {
        CreateGuestFromCSVResponseBean createGuestFromCSVResponseBean = new CreateGuestFromCSVResponseBean();
        if(createGuestFromCSVRequestBean!=null && !Utility.isNullOrEmpty(createGuestFromCSVRequestBean.getUploadId())
                && !Utility.isNullOrEmpty(createGuestFromCSVRequestBean.getEventId())  ){
            UploadBean uploadBean = getUploadBean(createGuestFromCSVRequestBean);
            if( uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId())) {
                String sFileName = uploadBean.getFilename();
                String sFolderPath = uploadBean.getPath();

                List guestListEntries = readCSV(fileUploadLocation+"/"+sFolderPath + "/" + sFileName ) ;
                if(guestListEntries!=null && guestListEntries.isEmpty() ) {
                    if(guestListEntries.size() > 150){

                        createGuestFromCSVResponseBean.setFileTooBig(true);
                        createGuestFromCSVResponseBean.setMessage("Large number of guests.");
                    }
                    createGuestFromCSVResponseBean.setFileExists(true);
                }
                createGuestFromCSVResponseBean.setGuestListEntries( guestListEntries );
            }
        }
        return createGuestFromCSVResponseBean;
    }

    private UploadBean getUploadBean( CreateGuestFromCSVRequestBean createGuestFromCSVRequestBean  ) {
        UploadBean uploadBean = new UploadBean();
        if(createGuestFromCSVRequestBean!=null && !Utility.isNullOrEmpty(createGuestFromCSVRequestBean.getUploadId()) ) {
            UploadRequestBean uploadRequestBean = new UploadRequestBean();
            uploadRequestBean.setUploadId( createGuestFromCSVRequestBean.getUploadId() );
            UploadFile uploadFile = new UploadFile();
            UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo(uploadRequestBean);

            if(uploadResponseBean!=null && !Utility.isNullOrEmpty(uploadResponseBean.getUploadId())) {
                uploadBean = uploadResponseBean.getUploadBean();
            }
        }
        return uploadBean;
    }

    private List readCSV(String sFilePathName) {
        List guestListEntries = null;
        if(!Utility.isNullOrEmpty(sFilePathName)) {
            try {
                CSVReader csvReader =  new CSVReader(new FileReader(sFilePathName), ';');
                guestListEntries = csvReader.readAll();
            } catch (FileNotFoundException e) {
                appLogging.error("Guest CSV does not exists : " + sFilePathName );
            } catch (IOException e) {
                appLogging.error("Could not read Guest CSV : " + sFilePathName );
            }
        }
        return guestListEntries;
    }

    public ArrayList<GuestsFromCSVBean> processGuestList(List listGuestData) {
        ArrayList<GuestsFromCSVBean> arrGuestFromCSVBean = new ArrayList<GuestsFromCSVBean>();
        if(listGuestData!=null && !listGuestData.isEmpty()) {
            for (int i = 0; i < listGuestData.size(); i++) {
                String[] strGuestDetailsArray = (String[])listGuestData.get(i);

                if(i == 0  && "Group Name".equalsIgnoreCase(strGuestDetailsArray[0])) {
                    continue; // Ignore first row because it is the header
                }

                //Keeping Track of Row Number
                GuestsFromCSVBean guestsFromCSVBean = new GuestsFromCSVBean();
                guestsFromCSVBean.setRowNumber( i );

                // Creating a new Guest Group for each row
                GuestGroupBean guestGroupBean = new GuestGroupBean();
                guestGroupBean.setGroupName( strGuestDetailsArray[0]);
                guestGroupBean.setGuestGroupId( Utility.getNewGuid() );

                //Creating a new Event Guest Group Data for each row
                EventGuestGroupBean eventGuestGroupBean = new EventGuestGroupBean();
                eventGuestGroupBean.setEventGuestGroupId(Utility.getNewGuid());
                eventGuestGroupBean.setGuestGroupId(guestGroupBean.getGuestGroupId());
                eventGuestGroupBean.setInvitedSeats(strGuestDetailsArray[1]);
                eventGuestGroupBean.setRsvpSeats(strGuestDetailsArray[2]);
                if(ParseUtil.sToI(eventGuestGroupBean.getRsvpSeats()) > 0 ){
                    eventGuestGroupBean.setHasResponded(true);
                }


                ArrayList<GuestBean> arrGuestBean = new ArrayList<GuestBean>();
                ArrayList<GuestGroupPhoneBean> arrGuestGroupPhoneBean = new ArrayList<GuestGroupPhoneBean>();
                ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = new ArrayList<GuestGroupEmailBean>();
                ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = new ArrayList<GuestGroupAddressBean>();

                //Creating the Primary Guest
                GuestBean primaryGuestBean = new GuestBean();
                primaryGuestBean.setGuestId( Utility.getNewGuid() );
                primaryGuestBean.setGuestGroupId( guestGroupBean.getGuestGroupId() );

                GuestGroupAddressBean primaryGuestGroupAddressBean = new GuestGroupAddressBean();
                primaryGuestGroupAddressBean.setGuestGroupAddressId( Utility.getNewGuid());
                primaryGuestGroupAddressBean.setPrimaryContact(true);
                primaryGuestGroupAddressBean.setGuestGroupId(guestGroupBean.getGuestGroupId());
                primaryGuestGroupAddressBean.setGuestId(primaryGuestBean.getGuestId());
                for (int j = 3; (j < strGuestDetailsArray.length && j<=14 ) ; j++) {
                    String sPrimaryGuestCol =  ParseUtil.checkNull(strGuestDetailsArray[j]);
                    if(!Utility.isNullOrEmpty(sPrimaryGuestCol)) {
                        switch(j) {
                            case 3: primaryGuestBean.setFirstName(sPrimaryGuestCol); break;
                            case 4: primaryGuestBean.setLastName(sPrimaryGuestCol); break;
                            case 5: primaryGuestBean.setCompany(sPrimaryGuestCol); break;

                            // Primary Email Address
                            case 6: arrGuestGroupEmailBean.add( generateGuestGroupEmailBean(sPrimaryGuestCol,true, primaryGuestBean) );
                                break;

                            // Primary Phone Number
                            case 7:
                            case 8: arrGuestGroupPhoneBean.add( generateGuestGroupPhoneBean(sPrimaryGuestCol,true, primaryGuestBean) );
                                break;

                            // Primary Mail Address (Physical Address)
                            case 9: primaryGuestGroupAddressBean.setAddress1( sPrimaryGuestCol); break;
                            case 10: primaryGuestGroupAddressBean.setAddress2( sPrimaryGuestCol); break;
                            case 11: primaryGuestGroupAddressBean.setCity( sPrimaryGuestCol); break;
                            case 12: primaryGuestGroupAddressBean.setState( sPrimaryGuestCol); break;
                            case 13: primaryGuestGroupAddressBean.setCountry( sPrimaryGuestCol); break;
                            case 14: primaryGuestGroupAddressBean.setZipcode( sPrimaryGuestCol ); break;
                        }
                    }
                }
                arrGuestBean.add(primaryGuestBean);
                arrGuestGroupAddressBean.add(primaryGuestGroupAddressBean);


                //Creating the Non Primary Guests that may have been included
                if(strGuestDetailsArray.length>15) {
                    GuestBean guestBean = new GuestBean();
                    for (int j = 15; j < strGuestDetailsArray.length; j++) {
                        String sGuestCol =  ParseUtil.checkNull(strGuestDetailsArray[j]);
                        if(!Utility.isNullOrEmpty(sGuestCol))  {
                            int iColNum = (j-14)%4;
                            if(iColNum == 1) {
                                guestBean = new GuestBean();
                                guestBean.setGuestGroupId( guestGroupBean.getGuestGroupId() );
                                guestBean.setGuestId( Utility.getNewGuid() );
                                arrGuestBean.add(guestBean);
                            }

                            switch(iColNum) {
                                case 1: guestBean.setFirstName( sGuestCol ); break;
                                case 2: guestBean.setLastName( sGuestCol ); break;
                                case 3: arrGuestGroupEmailBean.add( generateGuestGroupEmailBean(sGuestCol,false,guestBean) );
                                    break;
                                case 0: arrGuestGroupPhoneBean.add( generateGuestGroupPhoneBean(sGuestCol,false,guestBean) );
                                    break;
                            }
                        }
                    }
                }

                guestsFromCSVBean.setGuestGroupBean( guestGroupBean );
                guestsFromCSVBean.setEventGuestGroupBean( eventGuestGroupBean );
                guestsFromCSVBean.setArrGuestBean( arrGuestBean );
                guestsFromCSVBean.setArrGuestGroupAddressBean( arrGuestGroupAddressBean);
                guestsFromCSVBean.setArrGuestGroupEmailBean( arrGuestGroupEmailBean);
                guestsFromCSVBean.setArrGuestGroupPhoneBean( arrGuestGroupPhoneBean );

                arrGuestFromCSVBean.add(guestsFromCSVBean);
            }
        }
        return arrGuestFromCSVBean;
    }

    public void create(ArrayList<GuestsFromCSVBean> arrGuestFromCSVBean, String sEventId) {
        if(arrGuestFromCSVBean!=null && !arrGuestFromCSVBean.isEmpty()) {
            for(GuestsFromCSVBean guestsFromCSVBean : arrGuestFromCSVBean ) {
                GuestGroupBean guestGroupBean = guestsFromCSVBean.getGuestGroupBean();
                EventGuestGroupBean eventguestGroupBean = guestsFromCSVBean.getEventGuestGroupBean();
                ArrayList<GuestBean> arrGuestBean = guestsFromCSVBean.getArrGuestBean();
                ArrayList<GuestGroupPhoneBean> arrGuestGroupPhoneBean = guestsFromCSVBean.getArrGuestGroupPhoneBean();
                ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = guestsFromCSVBean.getArrGuestGroupEmailBean();
                ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = guestsFromCSVBean.getArrGuestGroupAddressBean();

                Integer numOfGuestBeanRowsInserted = 0;
                if( arrGuestBean!=null && !arrGuestBean.isEmpty() ) {
                    numOfGuestBeanRowsInserted = createGuestBean( arrGuestBean );
                }
                appLogging.info("numOfGuestBeanRowsInserted : " +numOfGuestBeanRowsInserted );

                Integer numOfGuestEmailRowsInserted = 0;
                if( arrGuestBean!=null && !arrGuestGroupEmailBean.isEmpty() ) {
                    numOfGuestEmailRowsInserted = createGuestGroupEmailBean( arrGuestGroupEmailBean );
                }
                appLogging.info("numOfGuestEmailRowsInserted : " +numOfGuestEmailRowsInserted );

                Integer numOfGuestPhoneRowsInserted = 0;
                if( arrGuestBean!=null && !arrGuestGroupPhoneBean.isEmpty() ) {
                    numOfGuestPhoneRowsInserted = createGuestGroupPhoneBean( arrGuestGroupPhoneBean );
                }
                appLogging.info("numOfGuestPhoneRowsInserted : " +numOfGuestPhoneRowsInserted );

                Integer numOfGuestAddressRowsInserted = 0;
                if( arrGuestBean!=null && !arrGuestGroupAddressBean.isEmpty() ) {
                    numOfGuestAddressRowsInserted = createGuestGroupAddressBean( arrGuestGroupAddressBean );
                }
                appLogging.info("numOfGuestAddressRowsInserted : " +numOfGuestAddressRowsInserted );

                Integer iNumOfEventGuests = createEventGuestGroup( eventguestGroupBean , sEventId  );
                appLogging.info("iNumOfEventGuests : " +iNumOfEventGuests );
                Integer iNumOfGuestGroups = createGuestGroup( guestGroupBean );
                appLogging.info("iNumOfGuestGroups : " +iNumOfGuestGroups );
            }
        }
    }

    private Integer createGuestGroup(GuestGroupBean guestGroupBean ) {
        Integer numOfRowsInserted = 0 ;
        if( guestGroupBean!=null && !Utility.isNullOrEmpty(guestGroupBean.getGuestGroupId())) {
            GuestRequestBean guestRequestBean = new GuestRequestBean();
            guestRequestBean.setGuestGroupId( guestGroupBean.getGuestGroupId() );
            guestRequestBean.setGuestGroupName( guestGroupBean.getGroupName() );

            BuildGuestData buildGuestData = new BuildGuestData();
            numOfRowsInserted = numOfRowsInserted +buildGuestData.insertGuestGroup( guestRequestBean );

            appLogging.info("guest group : " +numOfRowsInserted + " - " +  guestGroupBean );

        }
        return numOfRowsInserted;
    }
    private Integer createEventGuestGroup(EventGuestGroupBean eventGuestGroupBean, String sEventId ){
        Integer numOfRowsInserted = 0 ;
        //EVENTGUESTGROUPID,FK_GUESTGROUPID,FK_EVENTID,     TOTAL_INVITED_SEATS,RSVP_SEATS,WILL_NOT_ATTEND," +
        // "HAS_RESPONDED,CREATEDATE,HUMANCREATEDATE
        if( eventGuestGroupBean!=null && !Utility.isNullOrEmpty(eventGuestGroupBean.getEventGuestGroupId()) && !Utility.isNullOrEmpty(sEventId) ) {
            BuildGuestData buildGuestData = new BuildGuestData();

            GuestRequestBean guestRequestBean = new GuestRequestBean();
            guestRequestBean.setEventGuestGroupId(eventGuestGroupBean.getEventGuestGroupId() );
            guestRequestBean.setGuestGroupId( eventGuestGroupBean.getGuestGroupId() );
            guestRequestBean.setEventId( sEventId );
            guestRequestBean.setInvitedSeats( ParseUtil.sToI(eventGuestGroupBean.getInvitedSeats()) );
            guestRequestBean.setRsvpSeats( ParseUtil.sToI(eventGuestGroupBean.getRsvpSeats()) );
            guestRequestBean.setHasResponded( eventGuestGroupBean.getHasResponded() );
            guestRequestBean.setNotAttending( false );

            numOfRowsInserted = numOfRowsInserted + buildGuestData.insertEventGuestGroup(guestRequestBean);

            appLogging.info("Inserting event  group : " +numOfRowsInserted + " - " +  eventGuestGroupBean );

        }
        return numOfRowsInserted;
    }
    private Integer createGuestGroupEmailBean( ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean ) {
        Integer numOfRowsInserted = 0 ;
        if( arrGuestGroupEmailBean!=null && !arrGuestGroupEmailBean.isEmpty() ) {
            BuildGuestData buildGuestData = new BuildGuestData();
            for(GuestGroupEmailBean guestGroupEmailBean : arrGuestGroupEmailBean ) {
                GuestRequestBean guestRequestBean = new GuestRequestBean();
                guestRequestBean.setGuestGroupEmailId( guestGroupEmailBean.getguestGroupEmailId() );
                guestRequestBean.setGuestGroupId( guestGroupEmailBean.getGuestGroupId() );
                guestRequestBean.setGuestId( guestGroupEmailBean.getGuestId() );
                guestRequestBean.setEmail( guestGroupEmailBean.getemailId() );
                guestRequestBean.setPrimaryContactInfo( guestGroupEmailBean.isPrimaryContact() );
                numOfRowsInserted = numOfRowsInserted + buildGuestData.insertGuestEmail( guestRequestBean );

                appLogging.info("Inserting email : " +numOfRowsInserted + " - " +  guestGroupEmailBean );
            }
        }
        return numOfRowsInserted;
    }
    private Integer createGuestGroupPhoneBean( ArrayList<GuestGroupPhoneBean> arrGuestGroupPhoneBean ) {
        Integer numOfRowsInserted = 0 ;
        if( arrGuestGroupPhoneBean!=null && !arrGuestGroupPhoneBean.isEmpty() ) {
            BuildGuestData buildGuestData = new BuildGuestData();
            for(GuestGroupPhoneBean guestGroupPhoneBean : arrGuestGroupPhoneBean ) {
                GuestRequestBean guestRequestBean = new GuestRequestBean();
                guestRequestBean.setGuestGroupPhoneId( guestGroupPhoneBean.getGuestGroupPhoneId() );
                guestRequestBean.setGuestGroupId( guestGroupPhoneBean.getGuestGroupId() );
                guestRequestBean.setGuestId( guestGroupPhoneBean.getGuestId() );
                guestRequestBean.setPrimaryContactInfo( guestGroupPhoneBean.isPrimaryContact() );
                numOfRowsInserted = numOfRowsInserted + buildGuestData.insertGuestPhone( guestRequestBean , guestGroupPhoneBean.getPhoneNumber() );


                appLogging.info("Inserting phone : " +numOfRowsInserted + " - " +  guestGroupPhoneBean );
            }
        }
        return numOfRowsInserted;
    }
    private Integer createGuestGroupAddressBean( ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean ) {
        Integer numOfRowsInserted = 0 ;
        if( arrGuestGroupAddressBean!=null && !arrGuestGroupAddressBean.isEmpty() ) {
            BuildGuestData buildGuestData = new BuildGuestData();
            for(GuestGroupAddressBean guestGroupAddressBean : arrGuestGroupAddressBean ) {
                GuestRequestBean guestRequestBean = new GuestRequestBean();
                guestRequestBean.setGuestGroupAddressId( guestGroupAddressBean.getGuestGroupAddressId() );
                guestRequestBean.setGuestGroupId( guestGroupAddressBean.getGuestGroupId() );
                guestRequestBean.setGuestId( guestGroupAddressBean.getGuestId() );
                guestRequestBean.setAddress1(guestGroupAddressBean.getAddress1());
                guestRequestBean.setAddress2(guestGroupAddressBean.getAddress2());
                guestRequestBean.setCity(guestGroupAddressBean.getCity());
                guestRequestBean.setState(guestGroupAddressBean.getState());
                guestRequestBean.setCountry(guestGroupAddressBean.getCountry());
                guestRequestBean.setZipCode(guestGroupAddressBean.getZipcode());
                guestRequestBean.setPrimaryContactInfo(guestGroupAddressBean.isPrimaryContact());
                numOfRowsInserted = numOfRowsInserted + buildGuestData.insertGuestAddress(guestRequestBean);

                appLogging.info("Inserting address : " +numOfRowsInserted + " - " +  guestGroupAddressBean );

            }

        }
        return numOfRowsInserted;
    }
    private Integer createGuestBean(ArrayList<GuestBean> arrGuestBean ) {
        Integer numOfRowsInserted = 0 ;
        if( arrGuestBean!=null && !arrGuestBean.isEmpty() ) {
            BuildGuestData buildGuestData = new BuildGuestData();
            for(GuestBean guestBean : arrGuestBean ) {
                GuestRequestBean guestRequestBean = new GuestRequestBean();
                guestRequestBean.setGuestGroupId( guestBean.getGuestGroupId() );
                guestRequestBean.setGuestId( guestBean.getGuestId() );
                guestRequestBean.setFirstName(guestBean.getFirstName());
                guestRequestBean.setLastName(guestBean.getLastName());
                guestRequestBean.setCompany(guestBean.getCompany());
                numOfRowsInserted = numOfRowsInserted + buildGuestData.insertGuest(guestRequestBean);
                appLogging.info("Inserting guest : " +numOfRowsInserted + " - " +  guestBean );
            }
        }
        return numOfRowsInserted;
    }

    private GuestGroupPhoneBean generateGuestGroupPhoneBean(String sPhone, boolean isPrimary, GuestBean guestBean) {
        GuestGroupPhoneBean guestGroupPhoneBean = new GuestGroupPhoneBean();
        guestGroupPhoneBean.setGuestGroupPhoneId( Utility.getNewGuid());
        guestGroupPhoneBean.setGuestGroupId(guestBean.getGuestGroupId());
        guestGroupPhoneBean.setGuestId( guestBean.getGuestId() );
        guestGroupPhoneBean.setPhoneNumber( sPhone );
        guestGroupPhoneBean.setPrimaryContact(isPrimary);
        return guestGroupPhoneBean;
    }

    private GuestGroupEmailBean generateGuestGroupEmailBean(String sEmail, boolean isPrimary, GuestBean guestBean) {
        GuestGroupEmailBean guestGroupEmailBean = new GuestGroupEmailBean();
        guestGroupEmailBean.setguestGroupEmailId( Utility.getNewGuid() );
        guestGroupEmailBean.setGuestGroupId( guestBean.getGuestGroupId() );
        guestGroupEmailBean.setGuestId(guestBean.getGuestId());
        guestGroupEmailBean.setemailId(sEmail);
        guestGroupEmailBean.setPrimaryContact(isPrimary);
        return guestGroupEmailBean;
    }
}
