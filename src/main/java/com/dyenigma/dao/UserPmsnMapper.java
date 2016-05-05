package com.dyenigma.dao;

import com.dyenigma.entity.UserPmsn;

import java.util.List;

public interface UserPmsnMapper extends BaseMapper<UserPmsn> {
    List<UserPmsn> findAllByUserId(String userId);
    
    int delByPmsnId(String pmsnId);
    
    int delByUserId(String userId);
}