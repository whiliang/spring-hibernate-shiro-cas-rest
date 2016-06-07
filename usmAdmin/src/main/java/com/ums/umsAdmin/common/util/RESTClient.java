package com.ums.umsAdmin.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class RESTClient {
	private final static RestTemplate template = new RestTemplate();
	private final static ObjectMapper mapper = new ObjectMapper();

	private final static String url;
	
	private RESTClient() {}
	
	static {
		url = ConfigFileUtil.getValue("config.properties", "RestServiceUrl");
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getPermissionsName(List<String> roles)
			throws IOException {
		String rolesStr = mapper.writeValueAsString(roles);
		String permissionsNameStr = template.getForObject(url
				+ "umsAdmin/getRestfulPermissions/{roles}", String.class, rolesStr);	
		ArrayList<String> list = mapper.readValue(permissionsNameStr, ArrayList.class);
		return list;
	}
}
