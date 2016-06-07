package com.ums.umsAdmin.common.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public final class ConfigFileUtil {
	public static String getValue(String fileName, String key){
		Resource res = new ClassPathResource(fileName);
		String value = "";
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(res);
			value = props.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
}
