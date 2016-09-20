package com.github.mtdp.persistence;

import java.util.List;

/**
 * 
 *
 * @Description mybatis mapper基类
 * @author gqwang
 * @date 2016年1月26日下午4:16:46
 *
 */
public interface BaseMapper<T,K> {
	
	public List<T> gets();
	
	public T get(K id);
	
	public int add(T t);
	
	public int del(K id);
	
	public int update(T t);


}
