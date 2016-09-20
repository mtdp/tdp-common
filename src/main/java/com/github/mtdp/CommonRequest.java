package com.github.mtdp;

import java.io.Serializable;
import java.util.Map;
/**
 * 
 *
 * @Description 接口请求基础类
 * @author gqwang
 * @date 2016年1月5日上午9:13:14
 *
 */
public class CommonRequest implements Serializable {
	
	private static final long serialVersionUID = -5640419593832368776L;
	
	private Map<Object,Object> extData;

	public Map<Object, Object> getExtData() {
		return extData;
	}

	public void setExtData(Map<Object, Object> extData) {
		this.extData = extData;
	}
	
}
