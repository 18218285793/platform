package cn.com.signheart.component.core.user.dao.impl;

import cn.com.signheart.common.platformbase.BaseDAOImpl;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.user.dao.IUserDao;
import cn.com.signheart.component.core.user.model.TbUserPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ao.ouyang on 15-11-12.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDAOImpl implements IUserDao {


    @Override
    public TbUserPO addUser(TbUserPO tbUserPO) throws Exception {
        return null;
    }

    @Override
    public List<TbRolePO> searchUserRole(String userName) throws Exception {
        super.preparedSql("select tb2.* from tb_user_role_ref tb1 INNER JOIN  tb_platform_role tb2 on tb1.ROLE_ID = tb2.ROLE_ID " +
                " where tb1.USER_ID = (select USER_ID FROM tb_platform_user  where USER_NAME = '"+userName+"')");
        return super.getList(TbRolePO.class);
    }

    @Override
    public TbUserPO searchUser(String userName) throws Exception {
        String sql = "select * from tb_platform_user where USER_NAME = :userName";
        super.preparedSql(sql);
        super.setPreValue("userName",userName);
        List<TbUserPO> userPOs = super.getList(TbUserPO.class);
        if(userPOs.size()<=0){
            return null;
        }
        return userPOs.get(0);

    }
}
