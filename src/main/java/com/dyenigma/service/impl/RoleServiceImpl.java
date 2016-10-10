package com.dyenigma.service.impl;

import com.dyenigma.dao.PermissionMapper;
import com.dyenigma.dao.PostRoleMapper;
import com.dyenigma.dao.PrjRoleMapper;
import com.dyenigma.dao.RoleMapper;
import com.dyenigma.dao.RolePmsnMapper;
import com.dyenigma.dao.UserRoleMapper;
import com.dyenigma.entity.BaseDomain;
import com.dyenigma.entity.Permission;
import com.dyenigma.entity.PostRole;
import com.dyenigma.entity.PrjRole;
import com.dyenigma.entity.Role;
import com.dyenigma.entity.RolePmsn;
import com.dyenigma.entity.UserRole;
import com.dyenigma.service.RoleService;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.PageUtil;
import com.dyenigma.utils.StringUtil;
import com.dyenigma.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * topic
 * author: dyenigma
 * create: 2016/4/8 9:23
 */
@Transactional
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    protected RoleMapper roleMapper;
    @Autowired
    protected PermissionMapper permissionMapper;
    @Autowired
    protected RolePmsnMapper rolePmsnMapper;
    @Autowired
    protected PostRoleMapper postRoleMapper;
    @Autowired
    protected PrjRoleMapper prjRoleMapper;
    @Autowired
    protected UserRoleMapper userRoleMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public List<Role> findAllRoleList(PageUtil pageUtil) {
        LOGGER.info("开始查找用户信息,分页显示");
        List<Role> roleList = roleMapper.findAllByPage(pageUtil);
        return roleList;
    }

    @Override
    public Long getCount(Map<String, Object> paramMap) {
        LOGGER.info("开始查找用户信息的总条数");
        return baseMapper.getCount(paramMap);
    }

    /**
     * 新增和修改角色
     * param role
     * return
     */
    @Override
    public boolean persistenceRole(Role role) {
        String userId = Constants.getCurrendUser().getUserId();

        if (StringUtil.isEmpty(role.getRoleId())) {
            BaseDomain.createLog(role, userId);
            role.setStatus(Constants.PERSISTENCE_STATUS);
            role.setRoleId(UUIDUtils.getUUID());
            baseMapper.insert(role);

            // 这里设置新增用户的默认权限,首先获取所有的默认且有效的权限
            List<Permission> pList = permissionMapper.findAllDefault();
            //然后逐一添加进映射表
            for (Permission permission : pList) {
                RolePmsn rolePmsn;
                rolePmsn = new RolePmsn();
                rolePmsn.setStatus(Constants.PERSISTENCE_STATUS);
                BaseDomain.createLog(rolePmsn, userId);
                rolePmsn.setPmsnId(permission.getPmsnId());
                rolePmsn.setRoleId(role.getRoleId());
                rolePmsn.setRpId(UUIDUtils.getUUID());
                rolePmsnMapper.insert(rolePmsn);
            }
        } else {
            BaseDomain.editLog(role, userId);
            baseMapper.updateByPrimaryKeySelective(role);
        }
        return true;
    }

    @Override
    public boolean delRole(String id) {

        //删除角色权限关联
        List<RolePmsn> rpList = rolePmsnMapper.findAllByRoleId(id);
        for (RolePmsn rolePmsn : rpList) {
            baseMapper.deleteByPrimaryKey(rolePmsn.getRpId());
        }

        //删除角色岗位关联
        List<PostRole> prList = postRoleMapper.findAllByRoleId(id);
        for (PostRole postRole : prList) {
            baseMapper.deleteByPrimaryKey(postRole.getPrId());
        }

        //删除角色用户关联
        List<UserRole> urList = userRoleMapper.findAllByRoleId(id);
        for (UserRole userRole : urList) {
            baseMapper.deleteByPrimaryKey(userRole.getUrId());
        }

        //删除项目角色关联
        List<PrjRole> prjRoles = prjRoleMapper.findAllByRoleId(id);
        for (PrjRole prjRole : prjRoles) {
            baseMapper.deleteByPrimaryKey(prjRole.getPrId());
        }

        //删除角色
        Role role = baseMapper.selectByPrimaryKey(id);
        role.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
        return baseMapper.updateByPrimaryKey(role) > 0;
    }
}
