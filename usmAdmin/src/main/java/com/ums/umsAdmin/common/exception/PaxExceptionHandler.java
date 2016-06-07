package com.ums.umsAdmin.common.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaxExceptionHandler implements HandlerExceptionResolver {
	private String defaultErrorPage = "/public/error";
	private String accessDeniedPage = "/public/accessDenied";

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		ModelAndView mv = null;
		Map<String, String> hashMap = null;
		ObjectMapper mapper = new ObjectMapper();  
		
		if (ex instanceof BusinessException) {
			if (isJsonRequest(request)) {
				mv = new ModelAndView();
				String errorCode = ((BusinessException)ex).getErrorCode();
				String errorMsg = ((BusinessException)ex).getErrorMsg();
				hashMap = new HashMap<String, String>();  
				hashMap.put("busiStatus", "failure");
				hashMap.put("errorCode", errorCode);
				hashMap.put("errorMsg", errorMsg);
				//response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				try {
					String json = mapper.writeValueAsString(hashMap);  
					response.getWriter().write(json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return mv;
			} else {
				mv = new ModelAndView(defaultErrorPage);
				String errorCode = ((BusinessException) ex).getErrorCode();
				mv.addObject("errorCode", errorCode);
				return mv;
			}

		}else if(ex instanceof UnauthorizedException){
			if(isJsonRequest(request)){
				hashMap = new HashMap<String, String>();  
				hashMap.put("busiStatus", "failure");
				hashMap.put("errorCode", "403");
				hashMap.put("errorMsg", "You have no authorized.");
				String json;
				try {
					json = mapper.writeValueAsString(hashMap);
					response.getWriter().write(json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			}else
				mv = new ModelAndView(accessDeniedPage);
			return mv;
		}
		return null;
	}

	private boolean isJsonRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null
				&& requestType.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		return false;
	}

}
