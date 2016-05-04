package com.dyenigma.dao;

import com.dyenigma.entity.UserPost;

import java.util.List;

public interface UserPostMapper extends BaseMapper<UserPost> {

    List<UserPost> findByPostId(String postId);

    List<UserPost> findAllByUserId(String userId);
}