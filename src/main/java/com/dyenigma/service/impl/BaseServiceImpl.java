package com.dyenigma.service.impl;

import com.dyenigma.dao.BaseMapper;
import com.dyenigma.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    protected BaseMapper<T> baseMapper;

    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    @Override
    public List<T> findAll() {
        return baseMapper.findAll();
    }

    @Override
    public Long getCount(Map<String, Object> paramMap) {
        return baseMapper.getCount(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(T t) {
        return baseMapper.insertSelective(t);
    }

    @Override
    public T selectByPrimaryKey(String id) {

        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return baseMapper.updateByPrimaryKey(t);
    }

    /**
     * Description: 设置某个记录无效
     * Name:invalidByPrimaryKey
     * Author:dyenigma
     * Time:2016/4/27 9:02
     * param:[id]
     * return:boolean
     */
    @Override
    public int invalidByPrimaryKey(String id) {
        return baseMapper.invalidByPrimaryKey(id);
    }
}
