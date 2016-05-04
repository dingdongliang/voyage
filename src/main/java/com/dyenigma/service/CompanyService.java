package com.dyenigma.service;

import com.dyenigma.dao.BaseMapper;
import com.dyenigma.entity.Company;
import com.dyenigma.model.TreeModel;
import com.dyenigma.utils.PageUtil;

import java.util.List;

/**
 * topic
 * author Dyenigma
 * create 2016/4/4 14:23
 */
public interface CompanyService extends BaseMapper<Company> {

    /**
     * Description: 获取所有的公司名称和ID
     * Name:getAllCoName
     * Author:dyenigma
     * Time:2016/4/26 10:19
     * param:[]
     * return:java.util.List<com.dyenigma.model.DataList>
     */
    List<TreeModel> getAllCoName();

    /**
     * Description: 查找所有的公司信息
     * Name:findComp
     * Author:dyenigma
     * Time:2016/4/22 11:59
     * param:[pageUtil]
     * return:java.util.List<com.dyenigma.entity.Company>
     */
    List<Company> findComp(PageUtil pageUtil);


    /**
     * Description: 根据ID删除公司信息
     * Name:delComp
     * Author:dyenigma
     * Time:2016/4/22 11:59
     * param:[compId]
     * return:boolean
     */
    boolean delComp(String compId);


    /**
     * Description: 持久化公司信息，根据ID判断是insert还是update
     * Name:persistenceComp
     * Author:dyenigma
     * Time:2016/4/22 11:59
     * param:[company]
     * return:boolean
     */
    boolean persistenceComp(Company company);

    /**
     * Description: 查询某个公司下所有的公司信息，包含下属多级公司
     * Name:AllCoById
     * Author:dyenigma
     * Time:2016/4/28 11:10
     * param:[coId]
     * return:java.util.List<com.dyenigma.entity.Company>
     */
    List<Company> AllCoById(String coId);
}
