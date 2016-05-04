package com.dyenigma.dao;

import com.dyenigma.entity.PrjRole;

import java.util.List;

public interface PrjRoleMapper extends BaseMapper<PrjRole> {
    List<PrjRole> findAllByPrjId(String prjId);
}