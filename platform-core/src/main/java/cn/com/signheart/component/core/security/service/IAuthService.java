package cn.com.signheart.component.core.security.service;


import cn.com.signheart.common.platformbase.IBaseService;
import cn.com.signheart.component.core.security.model.TbPermissmionPO;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.security.model.TbRolePermissionRefPO;

import java.util.List;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public interface IAuthService extends IBaseService {


    /**
     * 查询系统所有权限
     * @return
     * @throws Exception
     */
    List<TbPermissmionPO> searchSysPermission() throws Exception;

    /**
     * 查询角色与权限关联
     * @return
     * @throws Exception
     */
    List<TbRolePermissionRefPO> searchRolePremRefList()throws Exception;

    /**
     * 查询系统角色
     * @return
     * @throws Exception
     */
    List<TbRolePO> searchRoleList()throws Exception;


}
