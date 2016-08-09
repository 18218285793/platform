package cn.com.signheart.component.sys.sysuser.service.impl;

import cn.com.signheart.common.platformbase.BaseServiceImpl;
import cn.com.signheart.component.core.security.model.TbPermissmionPO;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.security.system.AuthMgr;
import cn.com.signheart.component.core.security.util.PasswordHelper;
import cn.com.signheart.component.core.user.dao.IUserDao;
import cn.com.signheart.component.sys.sysuser.dao.ISysUserDao;
import cn.com.signheart.component.sys.sysuser.model.TbUserPO;
import cn.com.signheart.component.sys.sysuser.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ao.ouyang on 15-11-12.
 */
@Service("sysuserService")
public class SysUserServiceImpl extends BaseServiceImpl implements ISysUserService {

    @Resource
    private ISysUserDao sysuserDao;


}
