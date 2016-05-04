package com.dyenigma.dao;

import com.dyenigma.entity.User;
import com.dyenigma.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper extends BaseMapper<User> {

    /**
     * Description: 好名字自己会解释
     * Name:findByName
     * Author:Dyenigma
     * Time:2016/4/23 10:49
     * param:[name]
     * return:com.dyenigma.entity.User
     */
    User findByName(String name);

    /**
     * Description: 好名字自己会解释
     * Name:findAllByPage
     * Author:Dyenigma
     * Time:2016/4/23 10:49
     * param:[pageUtil]
     * return:java.util.List<com.dyenigma.entity.User>
     */
    List<User> findAllByPage(PageUtil pageUtil);

    /**
     * Description: 按用户ID集合查询用户信息，分页，用于获取岗位、部门、公司的所有用户信息
     * Name:findUserByPage
     * Author:dyenigma
     * Time:2016/4/28 10:20
     * param:[pageUtil, idList]
     * return:java.util.List<com.dyenigma.entity.User>
     */
    List<User> findUserByPage(@Param("pageUtil") PageUtil pageUtil, @Param("idSet") Set<String> idSet);


    /**
     * Description: 按用户ID集合查询用户信息，分页，用于获取岗位、部门、公司的所有用户信息
     * Name:findUserByPage
     * Author:dyenigma
     * Time:2016/4/28 10:20
     * return:java.util.List<com.dyenigma.entity.User>
     */
    List<User> findUserByList(@Param("idSet") Set<String> idSet);


}