package com.github.mtdp.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.HtmlUtils;

/**
 * 
 *
 * @Description 基础分页 
 * @author gqwang
 * @date 2016年3月1日下午9:03:46
 *
 */
public class BasePagination<T> implements Serializable {
    
    private static final long serialVersionUID = 2127014208693840749L;
  
    /** 默认第一页 **/
    private static int DEFAULT_FIRST_PAGE = 1;
    private static int DEFAULT_EVERY_PAGE_NUM = 20;
    private static String DIR_DESC = "desc";
    private static String DIR_ASC = "asc";
    private static String Q_DIR = "dir";
    private static String Q_SORT = "sort";
    private static String Q_START = "start";
    private static String Q_LIMIT = "limit";
    
    
    /** 每页显示数量 **/
    private int defaultEveryPageNum = DEFAULT_EVERY_PAGE_NUM;
    
    /** 当前页码 **/
    private int currentPage = DEFAULT_FIRST_PAGE;
    
    /** 排序字段 **/
    private String sort;
    
    /** desc asc **/
    private String dir;
    
    /** 查询数据的其他参数 **/
    private Map<String,Object> otherQueryParams;
    
    /** 查询返回的集合 **/
    private Collection<T> results;
    
    /** 总记录数量 **/
    private int totalRecord;
    
    private String filterWords = "[\\<\\>\\'\\\"]";
    
    
    /**
     * 获取查询条件的map
     * @return
     */
    public Map<String,Object> getQueryParams(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(Q_SORT, this.sort);
        //如果dir 不是desc 就是asc
        if(!DIR_DESC.equalsIgnoreCase(this.dir)){
            this.dir = DIR_ASC;
        }
        map.put(Q_DIR, this.dir);
        //开始位置 start
        map.put(Q_START, (this.getCurrentPage()-1) * this.defaultEveryPageNum);
        map.put(Q_LIMIT, this.defaultEveryPageNum);
        
        if(null != otherQueryParams){
            map.putAll(otherQueryParams);
        }
        //过滤特殊字符
        for(String key : map.keySet()){
            if(null == map.get(key)){
                continue;
            }
            String value = map.get(key).toString();
            if(null != value && !"".equals(value)){
                map.put(key, decodeHtmlFormat(value));
            }
        }
        return map ;
    }
    
    /**
     * decode html 编码 并去除规定的特殊字符
     * @param input
     * @return
     */
    public String decodeHtmlFormat(String input){
        return HtmlUtils.htmlUnescape(input).trim().replaceAll(filterWords, "");
    }
    
    /**
     * 子类可以通过改变每页数量
     * @param defaultEveryPageNum
     */
    public void setDefaultEveryPageNum(int defaultEveryPageNum){
        this.defaultEveryPageNum = defaultEveryPageNum;
    }
    
    /**
     * 获取下一页
     * @return
     */
    public int getNextPage(){
        return this.getCurrentPage() + 1;
    }
    
    /**
     * 上一页
     * @return
     */
    public int getBeforePage(){
        return this.getCurrentPage() - 1;
    }

    /**
     * 获取当前页码
     * @return
     */
    public int getCurrentPage() {
        //当前页大于总页数
        if(this.currentPage > this.getTotalPage()){
            this.currentPage = this.getTotalPage();
        }
        //当前页小于1    则等于1
        if(this.currentPage <= 1 ){
            this.currentPage = 1;
        }
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage > 0){
            this.currentPage = currentPage;
        }
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Map<String, Object> getOtherQueryParams() {
        return otherQueryParams;
    }

    public void setOtherQueryParams(Map<String, Object> otherQueryParams) {
        this.otherQueryParams = otherQueryParams;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return this.totalRecord == 0 ? 0 : (this.totalRecord + this.defaultEveryPageNum - 1) / this.defaultEveryPageNum;
    }
}

