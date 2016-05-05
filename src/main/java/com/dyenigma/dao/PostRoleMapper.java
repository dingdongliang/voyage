package com.dyenigma.dao;

import com.dyenigma.entity.PostRole;

import java.util.List;

public interface PostRoleMapper extends BaseMapper<PostRole> {

    List<PostRole> findAllByPostId(String postId);

    List<PostRole> findAllByRoleId(String roleId);

}