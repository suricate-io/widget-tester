package io.suricate.widgetTester.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


public final class PropertiesUtils {

    /**
     * Class logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

    /**
     * Method used to load properties form string
     * @param properties the string containing properties
     * @return the properties object
     */
    public static Properties loadProperties(String properties){
        Properties ret = null;
        StringReader reader = null;
        if (StringUtils.isNotBlank(properties)) {
            try {
                reader = new StringReader(properties);
                ret = new Properties();
                ret.load(reader);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return ret;
    }


    /**
     * Method used to convert string properties to  map
     * @param properties
     * @return
     */
    public static Map<String, String> getMap(String properties){
        Map<String, String> ret = new TreeMap<>();
        Properties prop = loadProperties(properties);
        if (prop != null) {
            for (String name : prop.stringPropertyNames()) {
                ret.put(name, prop.getProperty(name));
            }
        }
        return ret;
    }

    private PropertiesUtils() {
    }
}
