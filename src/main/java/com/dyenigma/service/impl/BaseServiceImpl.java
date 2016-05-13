package com.dyenigma.service.impl;

import com.dyenigma.dao.*;
import com.dyenigma.service.BaseService;
import com.dyenigma.utils.GenericsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

    protected BaseMapper<T> baseMapper;
    @Autowired
    protected CompanyMapper companyMapper;
    @Autowired
    protected DivisionMapper divisionMapper;
    @Autowired
    protected PermissionMapper permissionMapper;
    @Autowired
    protected PostMapper postMapper;
    @Autowired
    protected PostRoleMapper postRoleMapper;
    @Autowired
    protected PrjRoleMapper prjRoleMapper;
    @Autowired
    protected PrjUserMapper prjUserMapper;
    @Autowired
    protected ProjectMapper projectMapper;
    @Autowired
    protected RoleMapper roleMapper;
    @Autowired
    protected RolePmsnMapper rolePmsnMapper;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected UserPmsnMapper userPmsnMapper;
    @Autowired
    protected UserPostMapper userPostMapper;
    @Autowired
    protected UserRoleMapper userRoleMapper;
    @Autowired
    protected QuestionMapper questionMapper;

    @SuppressWarnings("rawtypes")
    private Class clazz = null;

    public BaseServiceImpl() {
        clazz = GenericsUtils.getSuperClassGenricType(this.getClass(), 0);
    }

    /**
     * 这个方法会在构造函数和spring依赖注入之后执行
     * <p>
     * param throws Exception 设定文件
     * return void 返回类型
     * Title: init
     * Description: 通过反射来实例化baseMapper
     */
    @PostConstruct
    public void init() throws Exception {
        String objName = clazz.getSimpleName();
        String objMapperName = objName.substring(0, 1).toLowerCase() + objName.substring(1)
                + "Mapper";
        Field mapperNameField = this.getClass().getSuperclass().getDeclaredField(objMapperName);
        Object object = mapperNameField.get(this);
        Field baseMapperNameField = this.getClass().getSuperclass().getDeclaredField("baseMapper");
        baseMapperNameField.set(this, object);

    }

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
