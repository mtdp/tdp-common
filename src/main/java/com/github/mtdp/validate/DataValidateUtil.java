package com.github.mtdp.validate;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mtdp.util.StringUtil;
/**
 * 
 *
 * @Description 参数校验工具类
 * @author gqwang
 * @date 2015年12月5日上午9:12:39
 *
 */
public class DataValidateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DataValidateUtil.class);
	
	/**
	 * 校验参数的工具方法
	 * @param obj
	 * @return
	 */
	public static DataValidateResult validate(Object obj){
		if(obj == null){
			new DataValidateResult(false, "校验参数不能为空");
		}
		//获取obj所有的属性
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields){
			//获取添加DataValidata注解的属性
			if(field.isAnnotationPresent(DataValidate.class)){
				DataValidate v = field.getAnnotation(DataValidate.class);
				//修改反射访问权限
				field.setAccessible(true);
				//读取注解的属性
				if(!v.isCheck()){
					return new DataValidateResult(true, "不需要校验");
				}
				DataValidateResult result = validateField(v, field, obj);
				if(!result.isResp()){
					//校验失败
					return result;
				}
			}
		}
		return new DataValidateResult(true, "校验通过");
	}

	
	public static DataValidateResult validateField(DataValidate v,Field field,Object obj){
		StringBuffer sb = new StringBuffer();
		String clazzName = obj.getClass().getSimpleName();
		try{
			//读取obj对应属性的值
			Object propVal = field.get(obj);
			//要检验
			if(!v.isNull()){
				//不能为空
				if(propVal == null){
					sb.append("参数对象[").append(clazzName).append("]的属性[").append(field.getName())
					.append("]校验失败,不能为NULL");
					String errorMsg = v.errorMsg();
					return new DataValidateResult(false,StringUtil.isBlank(errorMsg) ? sb.toString() : errorMsg);
				}
			}
			if(!v.isBlank()){
				//不能为空字符串 ""
				if(StringUtil.isBlank(propVal.toString())){
					sb.append("参数对象[").append(clazzName).append("]的属性[").append(field.getName())
					.append("]校验失败,不能为空字符串");
					String errorMsg = v.errorMsg();
					return new DataValidateResult(false,StringUtil.isBlank(errorMsg) ? sb.toString() : errorMsg);
				}
			}
			if(v.minLength() != -1 && v.minLength() > 0 && String.class.equals(propVal.getClass())){
				if(propVal.toString().length() < v.minLength()){
					sb.append("参数对象[").append(clazzName).append("]的属性[").append(field.getName())
					.append("]校验失败,该属性值是[").append(propVal).append("]最小长度不能小于").append(v.minLength());
					String errorMsg = v.errorMsg();
					return new DataValidateResult(false,StringUtil.isBlank(errorMsg) ? sb.toString() : errorMsg);
				}
			}
			if(v.maxLength() != -1 && v.maxLength() > 0 && String.class.equals(propVal.getClass())){
				if(propVal.toString().length() > v.maxLength()){
					sb.append("参数对象[").append(clazzName).append("]的属性[").append(field.getName())
					.append("]校验失败,该属性值是[").append(propVal).append("]最小长度不能大于").append(v.maxLength());
					String errorMsg = v.errorMsg();
					return new DataValidateResult(false,StringUtil.isBlank(errorMsg) ? sb.toString() : errorMsg);
				}
			}
			//校验正则表达式
			if(StringUtil.isNotBlank(v.regExp()) && String.class.equals(propVal.getClass())){
				Pattern pattern = Pattern.compile(v.regExp());
				Matcher matcher = pattern.matcher(propVal.toString());
				if(!matcher.matches()){
					sb.append("参数对象[").append(clazzName).append("]的属性[").append(field.getName())
					.append("]校验失败,该属性值是[").append(propVal).append("]校验正则表达式[").append(v.maxLength()).append("]失败");
					String errorMsg = v.errorMsg();
					return new DataValidateResult(false,StringUtil.isBlank(errorMsg) ? sb.toString() : errorMsg);
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("参数异常",e);
		} catch (IllegalAccessException e) {
			logger.error("访问参数权限异常",e);
		}
		return new DataValidateResult(true, "校验通过");
	}
}
