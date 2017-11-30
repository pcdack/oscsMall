package com.pcdack.oscsmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


/**
 * Created by pcdack on 17-9-9.
 *
 */
public class PropertiesUtil {
    private static Logger logger= LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties props;

    static {
        props=new Properties();
        String propsName="oscsmall.properties";
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(propsName),"utf-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }
    public static String getProperty(String key){
        String value=props.getProperty(key);
        if (org.apache.commons.lang3.StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }
    public static String getProperty(String key,String defaultValue){
        String value=props.getProperty(key);
        if (org.apache.commons.lang3.StringUtils.isBlank(value)){
            return defaultValue;
        }
        return value.trim();
    }
}
