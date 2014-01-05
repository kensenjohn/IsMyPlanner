package com.events.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class Configuration {
    // instance table for this object
    static private HashMap<String, Configuration> htConfigInstancess = new HashMap<String, Configuration>();

    // configuration file path for this config
    private String sConfigFilePath = "";
    private File fh;
    private long lastModified = 0;

    // hashtable storing the configuration
    private HashMap<String, String> config = new HashMap<String, String>();

    // logger
    Logger configLogging = LoggerFactory.getLogger(Constants.CONFIG_LOGS);

    public Configuration(String sPath)
    {
        sConfigFilePath = sPath;
        try
        {
            fh = new File(sConfigFilePath);
            lastModified = fh.lastModified();
        } catch (Exception e)
        {
            // hosed
        }

        getConfig();
    }

    /*
     * Return new or existing instance of the configurator
     */
    static synchronized public Configuration getInstance(String sConfigFile)
    {
        Configuration instance;

        if ((instance = htConfigInstancess.get(sConfigFile)) == null)
        {
            instance = new Configuration(sConfigFile);
            htConfigInstancess.put(sConfigFile, instance);
        }

        return instance;
    }

    /*
     * getConfig(String sConfigFile) - Return a hashmap of configuration file
     * data for this given instance
     */
    public synchronized HashMap<String, String> getConfig()
    {
        String value;
        String key;
        FileInputStream fis;
        Properties pConfig = new Properties();

        try
        {
            fis = new FileInputStream(fh);
            pConfig.load(fis);
            fis.close();
        } catch (Exception e)
        {
			/*
			 * logger.log(logger.CRIT, "getConfig()",
			 * "Cannot load configuration file: " + sConfigFilePath, sMySource);
			 */
            configLogging.info("Cannot load configuration file: " + sConfigFilePath);
        }

        Enumeration eConfigList = pConfig.keys();
        while (eConfigList.hasMoreElements())
        {
            key = (String) eConfigList.nextElement();

            if (key.indexOf("#") == -1)
            {
                value = pConfig.getProperty(key, "");

                // enter the config data into local config storage
                config.put(key.trim(), value.trim());
            }
        }

        return config;
    }

    /*
     * get()
     *
     * Get a value from the configuration file.
     */
    public String get(String key) {
        return get(key, Constants.EMPTY);
    }

    public String get(String key, String sDefault ) {
        String rv = Constants.EMPTY;
        // if the file has been modified since the last get access, reload
        // its contents before proceeding.
        long modifiedNow = fh.lastModified();
        if (modifiedNow > lastModified) {
            getConfig();
            lastModified = modifiedNow;
        }
        if(!"".equalsIgnoreCase(ParseUtil.checkNull(key) )) {
            rv = ParseUtil.checkNull(config.get(key));
            if( rv == null || "".equalsIgnoreCase(rv) ) { // if result is empty, send back default
                rv = ParseUtil.checkNull(sDefault);
            }
        }
        return rv;
    }

    public boolean wasModified () {
        return (fh.lastModified()  > lastModified ) ;
    }

    /*
     * reload()
     *
     * Reload configuration file.
     */
    public boolean reload()
    {
        getConfig();
        return true;
    }
}
