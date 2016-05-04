package com.dyenigma.service;

import com.dyenigma.entity.PrjRole;

import java.util.List;

/**
 * tide
 * author Dyenigma
 * create 2016/5/2 17:51
 */
public interface PrjRoleService extends BaseService<PrjRole> {

    List<PrjRole> getPrjRoleByPrjId(String prjId);
}
