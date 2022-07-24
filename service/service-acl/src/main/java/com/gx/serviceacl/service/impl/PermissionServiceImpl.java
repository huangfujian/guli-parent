package com.gx.serviceacl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.gx.serviceacl.entity.Permission;
import com.gx.serviceacl.entity.RolePermission;
import com.gx.serviceacl.entity.User;
import com.gx.serviceacl.helper.MemuHelper;
import com.gx.serviceacl.helper.PermissionHelper;
import com.gx.serviceacl.mapper.PermissionMapper;
import com.gx.serviceacl.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gx.serviceacl.service.RolePermissionService;
import com.gx.serviceacl.service.UserService;
import com.gx.serviceacl.vo.PermissionVO;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-28
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;
//    @Override
//    public PermissionVO selectPermissionAll() {
//        //查询出全部的菜单数据
//        List<Permission> permissionList = baseMapper.selectList(null);
//        Map<String, PermissionVO> permissionMap = new HashMap<>(10);
//        List<PermissionVO> permissionVOList = new ArrayList<>();
//        for (Permission permission : permissionList) {
//            PermissionVO permissionVO = new PermissionVO();
//            BeanUtils.copyProperties(permission, permissionVO);
//            permissionMap.put(permission.getId(), permissionVO);
//            permissionVOList.add(permissionVO);
//        }
//        PermissionVO root = new PermissionVO();
//        //再次遍历所有的惨菜单数据
//        for (PermissionVO permissionVO : permissionVOList) {
//            String pid = permissionVO.getPid();//父ID
//            if (pid.equals("0")) {//根节点
////                permissionVO.setLevel(1);
//                root = permissionVO;
//                continue;
//            }
//            PermissionVO parent = permissionMap.get(pid);//父元素
//            List<PermissionVO> childrenList = parent.getChildren();//获取子元素集合
//            //等于null就创建一个新的
//            if (childrenList == null) {
//                childrenList = new ArrayList<>();
//                parent.setChildren(childrenList);
//            }
//            childrenList.add(permissionVO);
//        }
//        return root;
//    }
    /**
     * 递归删除
     *
     * @param id
     */
    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        idList.add(id);
        //批量删除
        selectChildrenIds(idList, id);//批量查询
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * 保存角色分配
     *
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        rolePermissionService.remove(queryWrapper);//先将全部都进行删除
        //在进行新增
        List<RolePermission> permissionList = new ArrayList<>();
        for (String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            permissionList.add(rolePermission);
        }
        //批量新增
        rolePermissionService.saveBatch(permissionList);
    }

    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(id)) {
            //如果是超级管理员的话
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String id) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin(id)) {
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(id);
        }
        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<String> selectPermissionByRoleId(String roleId) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.orderByDesc("permission_id");
        queryWrapper.select("permission_id");
        List<RolePermission> rolePermissionsList = rolePermissionService.list(queryWrapper);
        List<String> permissionIdsList = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionsList) {
            permissionIdsList.add(rolePermission.getPermissionId());
        }
        return permissionIdsList;
    }

    private void selectChildrenIds(List<String> idList, String id) {
        //查询id的下的子id
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        queryWrapper.eq("pid", id);
        List<Permission> permissionList = baseMapper.selectList(queryWrapper);
        for (Permission permission : permissionList) {
            idList.add(permission.getId());
            selectChildrenIds(idList, permission.getId());
        }
    }

    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);
        if (null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    @Override
    public List<PermissionVO> selectPermissionAll() {
        //1 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //2 把查询所有菜单list集合按照要求进行封装
        List<PermissionVO> resultList = bulidPermission(permissionList);
        return resultList;
    }

    //把返回所有菜单list集合进行封装的方法
    public List<PermissionVO> bulidPermission(List<Permission> permissionList) {

        //创建list集合，用于数据最终封装
        List<PermissionVO> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for (Permission permission : permissionList) {
            PermissionVO permissionNode = new PermissionVO();
            BeanUtils.copyProperties(permission, permissionNode);
            //得到顶层菜单 pid=0菜单
            if ("0".equals(permissionNode.getPid())) {
                //设置顶层菜单的level是1
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode, permissionList));
            }
        }
        return finalNode;
    }

    private PermissionVO selectChildren(PermissionVO permissionNode, List<Permission> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<PermissionVO>());
        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (Permission permission : permissionList) {
            PermissionVO it = new PermissionVO();
            BeanUtils.copyProperties(permission, it);
            //判断 id和pid值是否相同
            if (permissionNode.getId().equals(it.getPid())) {
                //把父菜单的level值+1
                int level = permissionNode.getLevel() + 1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<PermissionVO>());
                }
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it, permissionList));
            }
        }
        return permissionNode;
    }

}
