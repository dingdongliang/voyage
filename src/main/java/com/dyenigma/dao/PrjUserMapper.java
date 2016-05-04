package com.dyenigma.dao;

import com.dyenigma.entity.PrjUser;

import java.util.List;

public interface PrjUserMapper extends BaseMapper<PrjUser> {
    List<PrjUser> getPrjUserByPrjId(String prjId);


}