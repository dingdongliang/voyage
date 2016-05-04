package com.dyenigma.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {

    /**
     * Description: 根据主键删除某项数据
     * Name:deleteByPrimaryKey
     * Author:dyenigma
     * Time:2016/4/22 11:55
     * param:[id]
     * return:int
     */
    int deleteByPrimaryKey(String id);


    /**
     * Description: 全部属性插入
     * Name:insert
     * Author:dyenigma
     * Time:2016/4/22 11:55
     * param:[t]
     * return:int
     */
    int insert(T t);


    /**
     * Description: 插入时选择包含有值的属性插入
     * Name:insertSelective
     * Author:dyenigma
     * Time:2016/4/22 11:55
     * param:[t]
     * return:int
     */
    int insertSelective(T t);


    /**
     * Description: 按主键查找数据
     * Name:selectByPrimaryKey
     * Author:dyenigma
     * Time:2016/4/22 11:56
     * param:[id]
     * return:T
     */
    T selectByPrimaryKey(String id);


    /**
     * Description:按主键更新数据，个别属性更新
     * Name:updateByPrimaryKeySelective
     * Author:dyenigma
     * Time:2016/4/22 11:56
     * param:[t]
     * return:int
     */
    int updateByPrimaryKeySelective(T t);


    /**
     * Description: 按主键更新数据，全部更新
     * Name:updateByPrimaryKey
     * Author:dyenigma
     * Time:2016/4/22 11:57
     * param:[t]
     * return:int
     */
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


    /**
     * Description: 查找所有数据
     * Name:findAll
     * Author:dyenigma
     * Time:2016/4/22 11:57
     * param:[]
     * return:java.util.List<T>
     */
    List<T> findAll();


    /**
     * Description: 获取某个查询的结果条数
     * Name:getCount
     * Author:dyenigma
     * Time:2016/4/22 11:57
     * param:[paramMap]
     * return:java.lang.Long
     */
    Long getCount(Map<String, Object> paramMap);

}
