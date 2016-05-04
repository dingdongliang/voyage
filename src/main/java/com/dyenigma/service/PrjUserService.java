package com.dyenigma.service;

import com.dyenigma.entity.PrjUser;
import com.dyenigma.entity.User;

import java.util.List;

/**
 * tide
 * author Dyenigma
 * create 2016/5/2 18:19
 */
public interface PrjUserService extends BaseService<PrjUser> {
    List<User> getPrjUserByPrjId(String prjId);
}
