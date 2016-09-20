package com.github.mtdp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * @desc 基础action
 * @author wangguoqing
 * @date 2016年1月4日下午2:19:12
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/")
public class BaseAction {
	
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	
	/**
	 * 每个请求都执行
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void setRequestAndResponse(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	

}
