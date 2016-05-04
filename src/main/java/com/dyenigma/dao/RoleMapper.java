package com.dyenigma.dao;

import com.dyenigma.entity.Role;
import com.dyenigma.utils.PageUtil;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * Description: 分页获取角色信息
     * Name:findAllByPage
     * Author:Dyenigma
     * Time:2016/4/23 10:47
     * param:[pageUtil]
     * return:java.util.List<com.dyenigma.entity.Role>
     */
    List<Role> findAllByPage(PageUtil pageUtil);
}