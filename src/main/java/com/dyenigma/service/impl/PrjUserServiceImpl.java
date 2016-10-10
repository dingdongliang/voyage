package com.dyenigma.service.impl;

import com.dyenigma.dao.PrjUserMapper;
import com.dyenigma.dao.UserMapper;
import com.dyenigma.entity.PrjUser;
import com.dyenigma.entity.User;
import com.dyenigma.service.PrjUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * tide
 * author Dyenigma
 * create 2016/5/2 18:20
 */
@Transactional
@Service("prjUserService")
public class PrjUserServiceImpl extends BaseServiceImpl<PrjUser> implements PrjUserService {
    @Autowired
    protected PrjUserMapper prjUserMapper;
    @Autowired
    protected UserMapper userMapper;

    @Override
    public List<User> getPrjUserByPrjId(String prjId) {
        List<PrjUser> puList = prjUserMapper.getPrjUserByPrjId(prjId);
        Set<String> idList = puList.stream().map(PrjUser::getUserId).collect(Collectors.toSet());
        if (idList.size() > 0) {
            return userMapper.findUserByList(idList);
        } else {
            return null;
        }
    }
}
