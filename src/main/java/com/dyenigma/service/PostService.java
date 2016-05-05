package com.dyenigma.service;

import com.dyenigma.dao.BaseMapper;
import com.dyenigma.entity.Post;
import com.dyenigma.model.TreeModel;

import java.util.List;

/**
 * Description: 岗位管理业务类
 * author  dyenigma
 * date 2016/4/27 8:25
 */
public interface PostService extends BaseMapper<Post> {

    /**
     * Description: 根据部门ID查询该部门下所有的岗位信息
     * Name:finaPostByDiv
     * Author:dyenigma
     * Time:2016/4/27 8:36
     * param:[id]
     * return:java.util.List<com.dyenigma.entity.Post>
     */
    List<Post> finaPostByDiv(String id);

    /**
     * Description: 新增或修改岗位信息
     * Name:persistencePost
     * Author:dyenigma
     * Time:2016/4/27 9:17
     * param:[post]
     * return:java.lang.Boolean
     */
    Boolean persistencePost(Post post);

    /**
     * Description: 获取所有可添加岗位的公司和部门
     * Name:getCoDivList
     * Author:dyenigma
     * Time:2016/4/27 10:02
     * param:[]
     * return:java.util.List<com.dyenigma.model.TreeModel>
     */
    List<TreeModel> getCoDivList();

    boolean delPostById(String postId);
}
