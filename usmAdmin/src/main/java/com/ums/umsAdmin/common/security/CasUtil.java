package com.ums.umsAdmin.common.security;

import java.util.ArrayList;
import java.util.List;

import org.jasig.cas.client.authentication.AttributePrincipal;

public class CasUtil {
	public static String getStringAttribute(AttributePrincipal principal, String attribute){
		String attributeStr = (String)principal.getAttributes().get(attribute);
		List<String> attributeList = convertToList(attributeStr);
		return attributeList.get(0);
	}
	
	private static List<String> convertToList(String str) {
		List<String> list = new ArrayList<String>();
		boolean flag = str.contains(",");
		if (flag) {
			str = str.substring(1, str.length() - 1);
			String[] strs = str.split(",");
			if (strs != null && strs.length > 0) {
				for (int i = 0; i < strs.length; i++) {
					list.add(strs[i]);
				}
			}
		}
		else
			list.add(str);
		return list;
	}
}
