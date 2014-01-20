package com.events.common.security;

import com.events.bean.security.DataSecurityRequestBean;
import com.events.common.*;
import com.events.common.exception.PropertyFileException;
import com.events.json.ErrorText;
import com.events.json.RespConstants;
import com.events.json.RespObjectProc;
import com.events.json.Text;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 6:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataSecurityChecker {
    Configuration unsafeWordFilterConfig = Configuration.getInstance(Constants.UNSAFE_INPUT_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public static boolean isStringSafe(String sUserInput , DataSecurityRequestBean dataSecurityRequestBean ) throws PropertyFileException {
        boolean isUserInputUnSafe = false;
        if( !"".equalsIgnoreCase(ParseUtil.checkNull(sUserInput)) && dataSecurityRequestBean!=null)
        {
            sUserInput = ESAPI.encoder().canonicalize(sUserInput);      // We use thi
            sUserInput = sUserInput.toLowerCase();
            // Glossary of terms
            // Unsafe Words : If an input is identified as Unsafe word we do not process it further. Mark input as unsafe.
            // Potential Unsafe Words : These input may or may not be unsafe. We run a separate REGEX test to make sure whether they are unsafe.

            ArrayList<String> arrUnsafeWordsInUserInput = identifyInputWordsFromList(sUserInput, dataSecurityRequestBean.getDefiniteUnsafeWords());
            if( arrUnsafeWordsInUserInput!=null && !arrUnsafeWordsInUserInput.isEmpty() ) {
                isUserInputUnSafe = true; // An Unsafe word was detected.
            } else {
                // Check if any Insecure words were used. These words should be tested again with a regex.
                ArrayList<String> arrPotentialUnsafeWordsInUserInput = identifyInputWordsFromList(sUserInput, dataSecurityRequestBean.getPotentialUnsafeWords()) ;
                if( didRegexDetectUnsafeWord(sUserInput, arrPotentialUnsafeWordsInUserInput) ) {
                    isUserInputUnSafe = true; // regex determined that the insercure word used was Unsafe. So input is marked as Unsafe.
                }

            }
        }
        return isUserInputUnSafe;
    }


    public static DataSecurityRequestBean bootstrapDataSecurityRequestBean()  throws PropertyFileException {
        Configuration configUnsafeWordFilter = (new DataSecurityChecker()).unsafeWordFilterConfig;
        Constants.PROP_UNSAFE_WORD_FILTER propIsFilterEnabled = Constants.PROP_UNSAFE_WORD_FILTER.IS_FILTER_ENABLED;
        boolean isFilterEnabled =  ParseUtil.sTob( configUnsafeWordFilter.get(propIsFilterEnabled.getUnsafeWordFilterPropKey(),propIsFilterEnabled.getDefaultValue()) )  ;

        DataSecurityRequestBean dataSecurityRequestBean = new DataSecurityRequestBean();
        if( isFilterEnabled ) {
            dataSecurityRequestBean =  loadDataSecurityRequestBean();
        }
        dataSecurityRequestBean.setFilterEnabled(isFilterEnabled);
        return dataSecurityRequestBean;
    }

    public static  DataSecurityRequestBean loadDataSecurityRequestBean()   throws PropertyFileException  {
        return loadDataSecurityRequestBean((new DataSecurityChecker()).unsafeWordFilterConfig);
    }
    public static  DataSecurityRequestBean loadDataSecurityRequestBean(Configuration configUnsafeWordFilter)   throws PropertyFileException  {
        DataSecurityRequestBean dataSecurityRequestBean = new DataSecurityRequestBean();
        dataSecurityRequestBean.setPotentialUnsafeWords(getPotentialUnsafeWords());
        dataSecurityRequestBean.setDefiniteUnsafeWords(getDefiniteUnsafeWords());
        dataSecurityRequestBean.setCreateTime(DateSupport.getEpochMillis());
        dataSecurityRequestBean.setDataInObjectOld(false);

        if( configUnsafeWordFilter!=null ) {
            Constants.PROP_UNSAFE_WORD_FILTER propReloadInterval = Constants.PROP_UNSAFE_WORD_FILTER.RELOAD_FILTER_PARAM_INTERVAL_IN_MINS;
            dataSecurityRequestBean.setReloadFilterParamIntervalInMins( ParseUtil.sToL( configUnsafeWordFilter.get(propReloadInterval.getUnsafeWordFilterPropKey(),propReloadInterval.getDefaultValue())) );
        }
        return dataSecurityRequestBean;
    }

    public static boolean isDataSecurityRequestBeanOld (DataSecurityRequestBean dataSecurityRequestBean) {
        return isDataSecurityRequestBeanOld(dataSecurityRequestBean, (new DataSecurityChecker()).unsafeWordFilterConfig);
    }

    public static boolean isDataSecurityRequestBeanOld (DataSecurityRequestBean dataSecurityRequestBean , Configuration configUnsafeWordFilter ) {
        boolean isDataSecurityRequestBeanOld = false;
        if( (configUnsafeWordFilter!=null && configUnsafeWordFilter.wasModified()) || dataSecurityRequestBean ==null ||
                (dataSecurityRequestBean!=null && (DateSupport.getEpochMillis() - dataSecurityRequestBean.getCreateTime()) > DateSupport.convertToMillis(Constants.TIME_UNIT.MINUTES,dataSecurityRequestBean.getReloadFilterParamIntervalInMins()))) {
            isDataSecurityRequestBeanOld = true;
        }
        return isDataSecurityRequestBeanOld;
    }

    private static ArrayList<String> getPotentialUnsafeWords() throws PropertyFileException {
        return getDelimitedWordsFromConfig(Constants.PROP_UNSAFE_WORD_FILTER.POTENTIAL_UNSAFE_WORD, Constants.PROP_UNSAFE_WORD_FILTER.DELIMITER);
    }

    private static ArrayList<String> getDefiniteUnsafeWords() throws PropertyFileException {
        return getDelimitedWordsFromConfig(Constants.PROP_UNSAFE_WORD_FILTER.DEFINITE_UNSAFE_WORD, Constants.PROP_UNSAFE_WORD_FILTER.DELIMITER);
    }

    private static ArrayList<String> getDelimitedWordsFromConfig(Constants.PROP_UNSAFE_WORD_FILTER propUnsafeWordFilter , Constants.PROP_UNSAFE_WORD_FILTER propDelimiter ) throws PropertyFileException {
        ArrayList<String> arrWordsFromConfig = new ArrayList<String>();
        if(propUnsafeWordFilter!=null && propDelimiter!=null) {
            DataSecurityChecker dataSecurityChecker = new DataSecurityChecker();
            String sDelimitedWords = ParseUtil.checkNull( dataSecurityChecker.unsafeWordFilterConfig.get(propUnsafeWordFilter.getUnsafeWordFilterPropKey()) );
            if( !"".equalsIgnoreCase(sDelimitedWords)) {
                String sDelimiter =  ParseUtil.checkNull( dataSecurityChecker.unsafeWordFilterConfig.get(propDelimiter.getUnsafeWordFilterPropKey()) );
                StringTokenizer strUnsafeWordsFromConfig = new StringTokenizer(sDelimitedWords, sDelimiter , false);
                while(strUnsafeWordsFromConfig!=null && strUnsafeWordsFromConfig.hasMoreElements() ) {
                    arrWordsFromConfig.add( strUnsafeWordsFromConfig.nextElement().toString().toLowerCase() );
                }
            } else {
                appLogging.error("Conf file " + Constants.UNSAFE_INPUT_PROP + " does not have data for Parameter :" + propUnsafeWordFilter.getUnsafeWordFilterPropKey() );
                throw new PropertyFileException("Conf file " + Constants.UNSAFE_INPUT_PROP + " does not have data for Parameter :" + propUnsafeWordFilter.getUnsafeWordFilterPropKey() +". Please check file.");
            }
        } else {
            appLogging.error("Please use a valid config parameter to be read.");
        }
        return arrWordsFromConfig;
    }


    private static ArrayList<String> identifyInputWordsFromList ( String sInput , ArrayList<String> arrWords ) {
        ArrayList<String> arrWordsFromInput = new ArrayList<String>();
        if( !Utility.isNullOrEmpty(sInput) && arrWords !=null && !arrWords.isEmpty() ) {
            for(String sEachWord : arrWords  ) {
                if (sInput.indexOf( sEachWord ) > -1) {
                    arrWordsFromInput.add( sEachWord );
                }
            }
        }
        return arrWordsFromInput;
    }

    private static boolean didRegexDetectUnsafeWord(String sInput , ArrayList<String> arrInsecureWords ) {
        boolean isUnsafeWordDetected = false;
        if(arrInsecureWords!=null && !arrInsecureWords.isEmpty()) {
            DataSecurityChecker dataSecurityChecker = new DataSecurityChecker();
            Iterator<String> iteratorInsercureWord = arrInsecureWords.iterator();
            while( iteratorInsercureWord.hasNext() && isUnsafeWordDetected == false ) { // if an unsafe word is detected, come out of the For loop and deliver the result.
                String sEachInsecureWord = ParseUtil.checkNull(iteratorInsercureWord.next());
                String sRegexOfInsecureWord = ParseUtil.checkNull(dataSecurityChecker.unsafeWordFilterConfig.get("regex." + sEachInsecureWord));
                if( sRegexOfInsecureWord!=null && !"".equalsIgnoreCase(sRegexOfInsecureWord) ) {
                    Pattern patternOfInsecureWord = Pattern.compile( sRegexOfInsecureWord  ,Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                    Matcher matchInsecureWord = patternOfInsecureWord.matcher(sInput);
                    isUnsafeWordDetected = matchInsecureWord.find();
                    appLogging.info("Regex : " + sRegexOfInsecureWord + " sInput : " + sInput + " isUnsafe : " + isUnsafeWordDetected);
                } else {
                    isUnsafeWordDetected = true; // Could not load Regex pattern for a potential insecure work. Mark it as unsafe
                    appLogging.error("Could not load Regex from Conf file. Marked it as unsafe. Regex : " + sRegexOfInsecureWord + " sInput : " + sInput + " isUnsafe : " + isUnsafeWordDetected);
                }
            }
        }
        return isUnsafeWordDetected ;
    }

    public static boolean isInsecureInputResponse(HttpServletRequest request) {
        return ParseUtil.sTob((ParseUtil.checkNullObject(request.getAttribute(Constants.INSECURE_PARAMS_ERROR))));
    }

    public static RespObjectProc getInsecureInputResponse(String sClassName) {
        appLogging.error("Insecure parameters used - " + ParseUtil.checkNull(sClassName) );
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        Text errorText = new ErrorText("Please use valid input parameters.","account_num") ;
        arrErrorText.add(errorText);

        RespObjectProc responseObject = new RespObjectProc();
        responseObject.setErrorMessages(arrErrorText);
        responseObject.setOkMessages(arrOkText);
        responseObject.setResponseStatus(responseStatus);

        return responseObject;
    }
}
