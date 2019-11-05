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
	
	public List<T> list();
	
	public T get(K id);
	
	public int save(T t);
	
	public int deleteById(K id);
	
	public int update(T t);


}
