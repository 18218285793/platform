package cn.com.signheart.component.core.security.dao.impl;

import cn.com.signheart.common.platformbase.BaseDAOImpl;
import cn.com.signheart.component.core.security.dao.IAuthDao;
import cn.com.signheart.component.core.security.model.TbPermissmionPO;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.security.model.TbRolePermissionRefPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ao.ouyang on 15-11-5.
 */
@Repository("authDao")
public class AuthDaoImpl extends BaseDAOImpl implements IAuthDao {

    private static final transient Logger logger = LoggerFactory.getLogger(AuthDaoImpl.class);

    @Override
    public List<TbPermissmionPO> searchSysPermission() throws Exception {
        super.preparedSql("select * from tb_platform_permission");
        return super.getList(TbPermissmionPO.class);
    }

    @Override
    public List<TbRolePermissionRefPO> searchRolePremRefList() throws Exception {
        super.preparedSql("select * from tb_role_permission_ref");
        return super.getList(TbRolePermissionRefPO.class);
    }

    @Override
    public List<TbRolePO> searchRoleList() throws Exception {
        super.preparedSql("select * from tb_platform_role");
        return super.getList(TbRolePO.class);
    }




}
