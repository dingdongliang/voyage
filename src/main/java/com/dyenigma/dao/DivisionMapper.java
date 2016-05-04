package com.dyenigma.dao;

import com.dyenigma.entity.Division;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DivisionMapper extends BaseMapper<Division> {

    /**
     * Description: 更新组织信息为过期
     * Name:updateById
     * Author:Dyenigma
     * Time:2016/4/23 10:44
     * param:[id]
     * return:int
     */
    int updateById(String id);

    /**
     * Description: 获取所有可添加子项的组织信息
     * Name:findSuperOrgan
     * Author:Dyenigma
     * Time:2016/4/23 10:44
     * param:[]
     * return:List<Division>
     */
    List<Division> findSuperOrgan(String coId);


    /**
     * Description: 查找某个公司下面的所有组织信息
     * Name:findByCompId
     * Author:Dyenigma
     * Time:2016/4/23 10:44
     * param:[companyId]
     * return:List<Division>
     */
    List<Division> findByCompId(@Param("coId") String coId);

}