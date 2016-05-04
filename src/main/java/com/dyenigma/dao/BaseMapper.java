package com.dyenigma.dao;


import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    int deleteByPrimaryKey(String id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    /**
     * Description: 设置某个记录无效
     * Name:invalidByPrimaryKey
     * Author:dyenigma
     * Time:2016/4/27 9:02
     * param:[id]
     * return:int
     */
    int invalidByPrimaryKey(String id);

    List<T> findAll();

    Long getCount(Map<String, Object> paramMap);

}
