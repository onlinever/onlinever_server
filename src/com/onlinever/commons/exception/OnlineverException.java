package com.onlinever.commons.exception;

import com.onlinever.commons.util.ReadPropertiesUtil;

/**
 *  知道明确的错误原因的异常
 */
public class OnlineverException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public int errorCode = 400;
	public static final Integer NOT_EXIST = 404;
	public static final Integer ACCOUNT_NOT_EQUAL = 405;
	public static final Integer SUCCESSFUL = 200;
	public static final Integer UNKNOWN_ERROR = 400;
	public static final Integer SEND_MSG_ERROR = 500;
	public static final Integer APPKEY_IS_NULL = 600;
	public static final Integer METHOD_IS_NULL = 601;
	public static final Integer SOURCE_TARGET_IS_NULL = 602;
	public static final Integer SOURCE_TARGET_IS_NOT_NULL = 603;
	public static final Integer QUANTITY_IS_ILLEGAL = 604;
	public static final Integer COMPANY_IS_NULL = 606;
	public static final Integer START_IS_ILLEGAL = 607;
	public static final Integer SIZE_IS_ILLEGAL = 609;
	public static final Integer BEGINTIME_IS_ILLEGAL = 610;
	public static final Integer ENDTIME_IS_ILLEGAL = 611;
	public static final Integer SOURCE_IS_NULL = 615;
	public static final Integer TARGET_IS_NULL = 616;
	public static final Integer PARAM_IS_ILLEGAL = 700;
	
	
	public OnlineverException(int errcode) {
		super(ReadPropertiesUtil.getErrorMessage(errcode));
		this.errorCode=errcode;
	}
	
	public OnlineverException(String message) {
		super(message);
	}

	public OnlineverException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public OnlineverException(String message,int errcode) {
		super(message);
		this.errorCode=errcode;
	}

	public OnlineverException(String message, Throwable cause,int errcode) {
		super(message, cause);
	    this.errorCode=errcode;
	}
}
