package com.ums.umsAdmin.common.exception;

import com.ums.umsAdmin.common.util.LocaleMessageHelper;


public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 9036811995607802896L;
	private String errorCode;
	private String errorMsg;

	public BusinessException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMsg = LocaleMessageHelper.getMessage(errorCode, null);
	}

	public BusinessException(String errorCode, String errorMsg) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
