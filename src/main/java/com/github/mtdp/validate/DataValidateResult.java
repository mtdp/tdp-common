package com.github.mtdp.validate;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 *
 * @Description 参数校验返回结果
 * @author gqwang
 * @date 2015年12月5日上午9:12:19
 *
 */
public class DataValidateResult implements Serializable{
	private static final long serialVersionUID = 1096531477760058256L;

	private boolean isResp;
	
	private String errorCode;
	
	private String errorMsg;
	
	private Throwable throwable;
	
	public DataValidateResult(){}
	
	public DataValidateResult(boolean isResp,String errorMsg){
		this.isResp = isResp;
		this.errorMsg = errorMsg;
	}
	
	public DataValidateResult(boolean isResp,String errorCode,String errorMsg){
		this.isResp = isResp;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public boolean isResp() {
		return isResp;
	}

	public void setResp(boolean isResp) {
		this.isResp = isResp;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	 
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
