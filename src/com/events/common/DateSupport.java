package com.events.common;

import com.events.bean.DateObject;
import com.events.common.exception.ExceptionHandler;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateSupport {
    private static final DateTimeFormatter PRETTY_DATE_2 = DateTimeFormat.forPattern(Constants.PRETTY_DATE_PATTERN_2);
    private static final DateTimeFormatter DATE_PATTERN_TZ = DateTimeFormat.forPattern(Constants.DATE_PATTERN_TZ);  // yyyy-MM-dd HH:mm:ss z

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public static Long getEpochMillis() {
        DateTime dt = new DateTime();
        return dt.getMillis();
    }

    public static String getUTCDateTime() {
        DateTimeZone zoneUTC = DateTimeZone.UTC;
        DateTime localDateTime = new DateTime();

        DateTime utcTime = localDateTime.withZone(zoneUTC);

        return DATE_PATTERN_TZ.print(utcTime);
    }

    public static DateObject convertTime(String  sFromTime, TimeZone inputFromTimeZone , String  sFromDatePattern ,  TimeZone inputToTimeZone, String sToDatePattern) {
        DateObject fromDateObject = new DateObject();
        try{
            if(!Utility.isNullOrEmpty(sFromTime) && !Utility.isNullOrEmpty(sFromDatePattern)) {
                fromDateObject = convertTime(  getDateObject(sFromTime,sFromDatePattern),inputFromTimeZone,inputToTimeZone, sToDatePattern  );
            }
        } catch(ParseException pe ) {
            appLogging.info("Parseing Exception of Date : From Date : " + ParseUtil.checkNull(sFromTime) + " pattern : " + ParseUtil.checkNull(sFromDatePattern) + ExceptionHandler.getStackTrace(pe) );
        }

        return fromDateObject;

    }

    public static DateObject convertTime(DateObject dateObject, TimeZone inputFromTimeZone ,  TimeZone inputToTimeZone, String sToDatePattern) {

        DateObject outputDateObject = new DateObject();

        DateTime fromDateTime  = new DateTime(dateObject.getYear(), dateObject.getMonth(), dateObject.getDay(), dateObject.getHour(), dateObject.getMinute(), DateTimeZone.forID(inputFromTimeZone.getID()));

        DateTime toDateTime = fromDateTime.withZone(DateTimeZone.forID(inputToTimeZone.getID()));

        final DateTimeFormatter outputFormatter = DateTimeFormat.forPattern(sToDatePattern);
        outputDateObject.setFormattedTime(outputFormatter.print(toDateTime));
        outputDateObject.setMillis( toDateTime.getMillis() );
        return outputDateObject;
    }

    public static Long getMillis(String sDate, String sPattern, String sTimeZone) {
        DateTimeZone timeZone = DateTimeZone.forID(sTimeZone);

        DateTimeFormatter formatter = DateTimeFormat.forPattern(sPattern);
        DateTime dateTime = formatter.parseDateTime(sDate);
        dateTime = dateTime.withZone(timeZone);
        return dateTime.getMillis();

    }

    public static DateObject getTimeDateObjectByZone(Long epochDate, String sTimeZone, String sPattern) {

        if(sPattern==null || "".equalsIgnoreCase(sPattern))
        {
            sPattern = Constants.DATE_PATTERN_TZ;
        }
        final DateTimeFormatter dateTimePattern = DateTimeFormat.forPattern(sPattern);

        DateTimeZone timeZone = DateTimeZone.forID(sTimeZone);

        DateTime localDateTime = new DateTime(epochDate,timeZone);
        DateObject dateObject = new DateObject();
        dateObject.setFormattedTime( dateTimePattern.print(localDateTime) );
        dateObject.setMillis( localDateTime.getMillis() );
        return dateObject;
    }


    public static String getTimeByZone(Long epochDate, String sTimeZone, String sPattern) {

        if(sPattern==null || "".equalsIgnoreCase(sPattern))
        {
            sPattern = Constants.DATE_PATTERN_TZ;
        }
        final DateTimeFormatter dateTimePattern = DateTimeFormat.forPattern(sPattern);

        DateTimeZone timeZone = DateTimeZone.forID(sTimeZone);

        DateTime localDateTime = new DateTime(epochDate,timeZone);
        return dateTimePattern.print(localDateTime);
    }

    public static String getTimeByZone(Long epochDate, String sTimeZone) {

        return getTimeByZone(epochDate, sTimeZone, Constants.DATE_PATTERN_TZ);
    }

    public static Long subtractHours(Long epochDate, Integer iNumOfHours)
    {
        DateTime srcTime = new DateTime(epochDate);
        DateTime afterMinus = srcTime.minusHours(iNumOfHours);
        return afterMinus.getMillis();
    }

    public static Long subtractTime(Long epochDate, Integer iNumOfTimeUnits, Constants.TIME_UNIT timeUnit )
    {
        // DateTime srcTime = new DateTime(epochDate);
        DateTime afterAddition = new DateTime(epochDate);
        if(Constants.TIME_UNIT.SECONDS.equals(timeUnit))
        {
            afterAddition = afterAddition.minusSeconds( iNumOfTimeUnits );
        }
        else if( Constants.TIME_UNIT.MINUTES.equals(timeUnit) )
        {
            afterAddition = afterAddition.minusMinutes( iNumOfTimeUnits );
        }
        else if( Constants.TIME_UNIT.HOURS.equals(timeUnit) )
        {
            afterAddition = afterAddition.minusHours( iNumOfTimeUnits );
        }
        return afterAddition.getMillis();
    }

    public static Long addTime(Long epochDate, Integer iNumOfTimeUnits, Constants.TIME_UNIT timeUnit )
    {
        // DateTime srcTime = new DateTime(epochDate);
        DateTime afterAddition = new DateTime(epochDate);
        if(Constants.TIME_UNIT.SECONDS.equals(timeUnit))
        {
            afterAddition = afterAddition.plusSeconds( iNumOfTimeUnits );
        }
        else if( Constants.TIME_UNIT.MINUTES.equals(timeUnit) )
        {
            afterAddition = afterAddition.plusMinutes( iNumOfTimeUnits );
        }
        else if( Constants.TIME_UNIT.HOURS.equals(timeUnit) )
        {
            afterAddition = afterAddition.plusHours( iNumOfTimeUnits );
        }
        else if( Constants.TIME_UNIT.DAYS.equals(timeUnit) )
        {
            afterAddition = afterAddition.plusDays( iNumOfTimeUnits );
        }
        else if( Constants.TIME_UNIT.MONTHS.equals(timeUnit) )
        {
            afterAddition = afterAddition.plusMonths( iNumOfTimeUnits );
        }
        else if( Constants.TIME_UNIT.YEARS.equals(timeUnit) )
        {
            afterAddition = afterAddition.plusYears( iNumOfTimeUnits );
        }
        return afterAddition.getMillis();
    }

    public static Integer getYear(Long epochDate)
    {
        DateTime srcTime = new DateTime(epochDate);
        Integer iYear = srcTime.getYear();
        return iYear;
    }

    public static Long convertToMillis (  Constants.TIME_UNIT timeUnit , Long sourceTime ) {
        Long milliseconds = 0L;
        switch(timeUnit ) {
            case SECONDS:
                milliseconds = sourceTime * 1000;
                break;
            case MINUTES:milliseconds = sourceTime * 60 * 1000;
                break;
        }
        return milliseconds ;
    }

    public static TimeZone getTimeZone( String sTimeZone ) {
        TimeZone dateTimeZone = DateTimeZone.UTC.toTimeZone();
        if(!Utility.isNullOrEmpty(sTimeZone)) {
            Constants.TIME_ZONE  timeZones = Constants.TIME_ZONE.valueOf(sTimeZone);
            dateTimeZone = DateTimeZone.forID(timeZones.getJavaTimeZone()).toTimeZone();
        }
        return dateTimeZone;
    }

    public static DateObject getDateObject(String sInputDate, String dateTimePattern) throws ParseException {

        DateObject dateObject = new DateObject();
        if(!Utility.isNullOrEmpty(sInputDate) && !Utility.isNullOrEmpty(dateTimePattern)) {
            SimpleDateFormat inputDf = new SimpleDateFormat(dateTimePattern);
            Date iInputDate = inputDf.parse(sInputDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(iInputDate);
            dateObject.setYear(  cal.get(Calendar.YEAR) );
            dateObject.setMonth( cal.get(Calendar.MONTH)+1 );
            dateObject.setDay( cal.get(Calendar.DAY_OF_MONTH) );
            dateObject.setHour(  cal.get(Calendar.HOUR_OF_DAY) );
            dateObject.setMinute(  cal.get(Calendar.MINUTE) );
        }

        return dateObject;
    }

    public static boolean isValidDate( String sInputDate, String dateTimePattern ) {
        boolean isValidDate = false;
        if( !Utility.isNullOrEmpty(sInputDate) && !Utility.isNullOrEmpty(dateTimePattern)) {
            try{
                SimpleDateFormat inputDf = new SimpleDateFormat(dateTimePattern);
                Date iInputDate = inputDf.parse(sInputDate);
                isValidDate = true;
            } catch (ParseException e) {
                isValidDate = false;
            }
        }
        return isValidDate;
    }
}
