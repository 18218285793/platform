package cn.com.signheart.component.core.user.dao;


import cn.com.signheart.common.platformbase.IBaseDao;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.user.model.TbUserPO;

import java.util.List;

/**
 * Created by ao.ouyang on 15-11-12.
 */
public interface IUserDao extends IBaseDao {

    /**
     * 添加用户
     * @param tbUserPO
     * @return
     * @throws Exception
     */
    TbUserPO addUser(TbUserPO tbUserPO) throws Exception;


    /**
     * 查询用户角色
     * @param userName
     * @return
     * @throws Exception
     */
    List<TbRolePO> searchUserRole(String userName) throws Exception;

    /**
     * 查找用户
     * @return
     * @throws Exception
     */
    TbUserPO searchUser(String userName)throws Exception;

}
