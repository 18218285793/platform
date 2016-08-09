package cn.com.signheart.component.core.user.service;


import cn.com.signheart.common.platformbase.IBaseService;
import cn.com.signheart.component.core.user.model.TbUserPO;

import java.util.Map;
import java.util.Set;

/**
 * Created by ao.ouyang on 15-11-12.
 */
public interface IUserService extends IBaseService {

    /**
     * 查找用户
     * @return
     * @throws Exception
     */
    TbUserPO searchUser(String userName)throws Exception;

    /**
     * 创建用户
     * @return
     * @throws Exception
     */
    void createUser(Map inputMap)throws Exception;
    /**
     * 查询用户角色
     * @param userName
     * @return
     * @throws Exception
     */
    Set<String> searchUserRole(String userName) throws Exception;


    /**
     * 查询用户对应权限
     * @param userName
     * @return
     * @throws Exception
     */
    Set<String> searchUserPermissions(String userName) throws Exception;

}
