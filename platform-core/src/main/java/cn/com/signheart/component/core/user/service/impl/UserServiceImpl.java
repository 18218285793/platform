package cn.com.signheart.component.core.user.service.impl;

import cn.com.signheart.common.platformbase.BaseServiceImpl;
import cn.com.signheart.component.core.security.model.TbPermissmionPO;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.security.system.AuthMgr;
import cn.com.signheart.component.core.security.util.PasswordHelper;
import cn.com.signheart.component.core.user.dao.IUserDao;
import cn.com.signheart.component.core.user.model.TbUserPO;
import cn.com.signheart.component.core.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ao.ouyang on 15-11-12.
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override
    public TbUserPO searchUser(String userName) throws Exception {
        return userDao.searchUser(userName);
    }

    @Override
    public void createUser(Map inputMap) throws Exception {
        TbUserPO userPO = new TbUserPO();
        userPO.setUserId(UUID.randomUUID().toString());
        userPO.setUserName((String)inputMap.get("userName"));
        userPO.setPassword((String)inputMap.get("password"));
        userPO.setSalt(System.currentTimeMillis()+"");
        PasswordHelper.encryptPassword(userPO);
        userDao.insert(userPO);
    }

    @Override
    public Set<String> searchUserRole(String userName) throws Exception {
        List<TbRolePO> tbRolePOs = userDao.searchUserRole(userName);
        if(tbRolePOs.size()<=0){
            return  null;
        }
        HashSet set = new HashSet();
        for (TbRolePO tbRolePO : tbRolePOs){
            set.add(tbRolePO.getRoleName());
        }

        return set;
    }

    @Override
    public Set<String> searchUserPermissions(String userName) throws Exception {
        List<TbRolePO> tbRolePOs = userDao.searchUserRole(userName);
        if(tbRolePOs.size()<=0){
            return  null;
        }
        HashSet set = new HashSet();
        for (TbRolePO tbRolePO : tbRolePOs){
            List<TbPermissmionPO> permissmionPOs = AuthMgr.getRolePermission(tbRolePO.getRoleName());
            for(TbPermissmionPO permissmionPO : permissmionPOs){
                set.add(permissmionPO.getPermissionCode());
            }
        }
        return set;
    }


}
