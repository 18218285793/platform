package cn.com.signheart.component.core.security.dao;


import cn.com.signheart.common.platformbase.IBaseDao;
import cn.com.signheart.component.core.security.model.TbPermissmionPO;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.security.model.TbRolePermissionRefPO;

import java.util.List;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public interface IAuthDao extends IBaseDao {


    /**
     * 查询权限信息
     * @return
     * @throws Exception
     */
    List<TbPermissmionPO> searchSysPermission() throws Exception;

    /**
     * 查询角色权限信息
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
