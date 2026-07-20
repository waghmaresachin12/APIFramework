package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties = new Properties();

    static {
        //Load class with classname.class.getClassLoader().getResourceAsStream
        //static block which will be called before main method and class will get loaded with config.properties file.
        //this approach is more faster than 'fileinputstream', you can use fileinputstream but it takes time
        InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties");
        if (input != null) {
            try {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //Now above will be saved in memory somewhere and it takes less memory only and this will be available everywhere in any class


    // now we need getter and setter
    public static String get(String key){
        return properties.getProperty(key).trim();
    }

    public static void set(String key, String value){
        properties.setProperty(key, value);
    }

}
