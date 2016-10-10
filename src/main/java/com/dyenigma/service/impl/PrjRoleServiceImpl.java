package com.dyenigma.service.impl;

import com.dyenigma.dao.PrjRoleMapper;
import com.dyenigma.entity.PrjRole;
import com.dyenigma.service.PrjRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * tide
 * author Dyenigma
 * create 2016/5/2 17:52
 */
@Transactional
@Service("prjRoleService")
public class PrjRoleServiceImpl extends BaseServiceImpl<PrjRole> implements PrjRoleService {
    @Autowired
    protected PrjRoleMapper prjRoleMapper;

    @Override
    public List<PrjRole> getPrjRoleByPrjId(String prjId) {
        return prjRoleMapper.findAllByPrjId(prjId);
    }
}
