package com.dyenigma.service.impl;

import com.dyenigma.entity.PrjRole;
import com.dyenigma.service.PrjRoleService;
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
    @Override
    public List<PrjRole> getPrjRoleByPrjId(String prjId) {
        return prjRoleMapper.findAllByPrjId(prjId);
    }
}
