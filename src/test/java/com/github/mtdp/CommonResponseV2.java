package com.github.mtdp;

import java.io.Serializable;
/**
 * 
 *
 * @Description 接口处理结果返回基础类 v2
 * @author wangguoqing
 * @date 2016年7月18日上午11:02:43
 *
 */
public class CommonResponseV2<T> implements Serializable{

	private static final long serialVersionUID = -1306504516838279717L;
	
	/**接口返回的code**/
	private String code;
	/**返回的错误提示**/
	private String error;
	/**返回的提示信息**/
	private String info;
	/**接口返回的数据**/
	private T data;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
