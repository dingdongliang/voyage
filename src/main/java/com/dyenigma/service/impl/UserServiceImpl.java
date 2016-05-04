package com.dyenigma.service.impl;

import com.dyenigma.entity.*;
import com.dyenigma.model.TreeModel;
import com.dyenigma.service.UserService;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.PageUtil;
import com.dyenigma.utils.StringUtil;
import com.dyenigma.utils.UUIDUtils;
import com.dyenigma.utils.security.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Transactional
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
    public List<User> findAll() {
        LOGGER.debug("run the users findall");
        return userMapper.findAll();
    }

    @Override
    public User getUserByName(String name) {

        return userMapper.findByName(name);
    }

    @Override
    public boolean persistenceUser(User user) {
        String userId = Constants.getCurrendUser().getUserId();

        if (StringUtil.isEmpty(user.getUserId())) {
            BaseDomain.createLog(user, userId);
            user.setPassword(Md5Utils.hash(Constants.DEFAULT_PASSWORD));
            user.setStatus(Constants.PERSISTENCE_STATUS);
            user.setUserId(UUIDUtils.getUUID());
            userMapper.insert(user);
        } else {
            BaseDomain.editLog(user, userId);
            userMapper.updateByPrimaryKeySelective(user);
        }
        return true;
    }

    /**
     * 分页查询用户信息
     * param pageUtil
     * return
     * param pageUtil
     */
    @Override
    public List<User> allUserByPage(PageUtil pageUtil) {
        LOGGER.info("开始查找用户信息,分页显示");
        return userMapper.findAllByPage(pageUtil);
    }

    @Override
    public boolean delUser(String userId) {
        List<UserRole> urList = userRoleMapper.findAllByUserId(userId);
        //TODO
        return userMapper.deleteByPrimaryKey(userId) > 0;
    }


    /**
     * Description: 获取所有可添加用户的岗位，按树状结构展示
     * stepByStep: 传递过来公司与部门树
     * 1.循环判断该树中哪个节点是部门节点
     * 2.然后查找该节点的下属岗位
     * 3.添加到节点内
     * Name:getPostList
     * Author:Dyenigma
     * Time:2016/4/27 23:24
     * param:[list]
     * return:java.util.List<com.dyenigma.model.TreeModel>
     */
    @Override
    public List<TreeModel> getPostList(List<TreeModel> list) {
        //递归处理树结构，添加岗位信息
        postToTree(list);
        return list;
    }

    //添加岗位数据到公司-部门树模型下（第一步处理方法：判断是否有子节点）
    private void postToTree(List<TreeModel> list) {
        for (TreeModel treeModel : list) {
            List<TreeModel> children = treeModel.getChildren();
            //有子节点递归，没有子节点不参与递归，在每个部门节点处理岗位信息的嫁接
            if (children == null || children.size() == 0) {
                addPost(treeModel, children);
            } else {
                List<TreeModel> childList = treeModel.getChildren();
                //内层循环，添加节点
                postToTree(childList);
                addPost(treeModel, children);
            }
        }
    }

    //添加岗位数据到公司-部门树模型下（第二步处理方法：判断是否是部门节点）
    private void addPost(TreeModel treeModel, List<TreeModel> children) {
        //判断是否是部门节点
        if ("DIV".equals(treeModel.getPid())) {
            //获取每个节点的id，即部门id
            String divId = treeModel.getId();
            //获取该部门下属的所有岗位
            List<Post> postList = postMapper.findPostByDiv(divId);
            //把岗位加入部门节点内
            treeModel.setChildren(addPostToDiv(postList, children));
        }
    }

    //添加岗位数据到公司-部门树模型下（第三步处理方法：把岗位加入部门节点内）
    private List<TreeModel> addPostToDiv(List<Post> list, List<TreeModel> childList) {
        if (childList == null) {
            childList = new ArrayList<>();
        }
        for (Post post : list) {
            TreeModel tm = new TreeModel();
            tm.setId(post.getPostId() + "");
            tm.setIconCls(Constants.DEFAULT_ICON);
            tm.setPid("POST");
            tm.setText(post.getPostName());
            tm.setState(Constants.TREE_STATUS_OPEN);
            childList.add(tm);
        }
        return childList;
    }

    /**
     * Description: 按用户ID集合查询用户信息，分页，用于获取岗位、部门、公司的所有用户信息
     * Name:findUserByPage
     * Author:dyenigma
     * Time:2016/4/28 10:20
     * param:[pageUtil, idList]
     * return:java.util.List<com.dyenigma.entity.User>
     */
    @Override
    public List<User> findUserByPage(PageUtil pageUtil, Set<String> idList) {
        return userMapper.findUserByPage(pageUtil, idList);
    }
}
