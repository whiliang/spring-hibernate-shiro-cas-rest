package com.ums.umsRestService.common.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PaxExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		if (ex instanceof BusinessException) {
			ModelAndView mv = new ModelAndView();
			String errorCode = ((BusinessException) ex).getErrorCode();
			String errorMsg = ((BusinessException) ex).getErrorMsg();
			responseJosn(response, errorCode, errorMsg);
			return mv;

		} else {
			ModelAndView mv = new ModelAndView();
			responseJosn(response, "Invoke format error",
					"Request rest service error,"
							+ "please contact system administrator.");
			return mv;
		}
	}

	private void responseJosn(HttpServletResponse response, String errorCode,
			String errorMsg) {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("busiStatus", "failure");
		hashMap.put("errorCode", errorCode);
		hashMap.put("errorMsg", errorMsg);
		ObjectMapper mapper = new ObjectMapper();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
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
