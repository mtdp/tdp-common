package com.github.mtdp;

import java.io.Serializable;
/**
 * 
 *
 * @Description 接口处理结果返回基础类
 * @author gqwang
 * @date 2016年1月5日上午9:13:28
 *
 */
public class CommonResponse<T> implements Serializable {

	private static final long serialVersionUID = -3780935712080916394L;
	
	/** 返回的实体 **/
	private T respObj;
	
    /** 此次请求是否处理成功 **/
    private boolean isResp;
    /** 请求处理失败的error **/
	private String errorCode;
	/** 请求处理失败的msg **/
	private String errorMsg;
	
	public CommonResponse() {

    }
	
	public CommonResponse(boolean isResp) {
      this.isResp = isResp;
    }
  
    public CommonResponse(boolean isResp, T respObj) {
        this.isResp = isResp;
        this.respObj = respObj;
    }
  
    public CommonResponse(boolean isResp, String errorCode) {
        this.isResp = isResp;
        this.errorCode = errorCode;
    }
    
    public CommonResponse(boolean isResp, String errorCode,String errorMsg) {
      this.isResp = isResp;
      this.errorCode = errorCode;
      this.errorMsg = errorMsg;
    }

	public T getRespObj() {
		return respObj;
	}

	public void setRespObj(T respObj) {
		this.respObj = respObj;
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
}
