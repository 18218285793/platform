package cn.com.signheart.component.core.security.service.impl;

import cn.com.signheart.component.core.security.dao.IAuthDao;
import cn.com.signheart.component.core.security.model.TbPermissmionPO;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.security.model.TbRolePermissionRefPO;
import cn.com.signheart.component.core.security.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ao.ouyang on 15-11-4.
 */
@Service("authService")
public class AuthServiceImpl implements IAuthService {

    private static final transient Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Resource
    private IAuthDao authDao;


    @Override
    public List<TbPermissmionPO> searchSysPermission() throws Exception {
        return authDao.searchSysPermission();
    }

    @Override
    public List<TbRolePermissionRefPO> searchRolePremRefList() throws Exception {
        return authDao.searchRolePremRefList();
    }

    @Override
    public List<TbRolePO> searchRoleList() throws Exception {
        return authDao.searchRoleList();
    }
}
