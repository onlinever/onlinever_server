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
	/** 输入参数无效 */
	public static final Integer INPUT_PARAM_INVALID = 60002001;
	/** 用户已存在 */
	public static final Integer USER_ALREADY_EXIST = 60002002;
	/** 缺少必须的参数 */
	public static final Integer REQUIRED_PARAMETER_MISSING = 60002003;
	/** 邮箱验证失败 */
	public static final Integer EMAIL_VALIDATION_FAIL = 60002004;
	/** 手机验证失败 */
	public static final Integer MOBILE_VALIDATION_FAIL = 60002005;
	/** 密码错误 */
	public static final Integer PASSWORD_VALIDATION_FAIL = 60002006;
	/** 用户不存在 */
	public static final Integer USER_DOES_NOT_EXIST = 60002007;
	
	
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
