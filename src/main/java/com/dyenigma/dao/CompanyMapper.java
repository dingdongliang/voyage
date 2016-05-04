package com.dyenigma.dao;

import com.dyenigma.entity.Company;
import com.dyenigma.utils.PageUtil;

import java.util.List;

public interface CompanyMapper extends BaseMapper<Company> {
    /**
     * Description: 分页查询公司信息
     * Name:findAllByPage
     * Author:Dyenigma
     * Time:2016/4/23 10:43
     * param:[pageUtil]
     * return:java.util.List<com.dyenigma.entity.Company>
     */
    List<Company> findAllByPage(PageUtil pageUtil);

    /**
     * Description:  根据父ID查询公司信息
     * Name:selectByPrntId
     * Author:dyenigma
     * Time:2016/4/28 11:13
     * param:[prntId]
     * return:java.util.List<com.dyenigma.entity.Company>
     */
    List<Company> selectByPrntId(String prntId);
}