package com.dyenigma.dao;

import com.dyenigma.entity.Post;

import java.util.List;

public interface PostMapper extends BaseMapper<Post> {
    /**
     * Description: 根据部门ID查询该部门下所有的岗位信息
     * Name:findPostByDiv
     * Author:dyenigma
     * Time:2016/4/27 8:38
     * param:[divId]
     * return:java.util.List<com.dyenigma.entity.Post>
     */
    List<Post> findPostByDiv(String divId);
}