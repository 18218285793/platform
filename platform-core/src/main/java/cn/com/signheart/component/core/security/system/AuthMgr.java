package cn.com.signheart.component.core.security.system;

import cn.com.signheart.component.core.core.PlatFormContext;
import cn.com.signheart.component.core.security.model.TbPermissmionPO;
import cn.com.signheart.component.core.security.model.TbRolePO;
import cn.com.signheart.component.core.security.model.TbRolePermissionRefPO;
import cn.com.signheart.component.core.security.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public class AuthMgr {
    private static final transient Logger logger = LoggerFactory.getLogger(AuthMgr.class);

    private static Map<String,List<TbPermissmionPO>> rolePermission;


    static {
        rolePermission = new ConcurrentHashMap<>();
    }


    public static List<TbPermissmionPO> getRolePermission(String roleId){
        return rolePermission.get(roleId);

    }


    public static void putRolePermission(String roleId,List<TbPermissmionPO> permissmionPOs){
        rolePermission.put(roleId,permissmionPOs);
    }

    public static void flushRolePermission(){
        rolePermission.clear();
        loadRolePermission();
    }



    public static void loadRolePermission(){
        try {
            logger.info("读取系统角色权限。");
            IAuthService authService = (IAuthService) PlatFormContext.getSpringContext().getBean("authService");
            List<TbPermissmionPO> permissmionPOs = authService.searchSysPermission();
            List<TbRolePermissionRefPO> rolePermissionRefPOs = authService.searchRolePremRefList();
            List<TbRolePO> tbRolePOs = authService.searchRoleList();
            for (TbRolePO tbRolePO : tbRolePOs) {
                for (TbRolePermissionRefPO tbRolePermissionRefPO : rolePermissionRefPOs) {
                    if(tbRolePO.getRoleId().equalsIgnoreCase(tbRolePermissionRefPO.getRoleId())){
                        ArrayList rolePermList = new ArrayList();
                        if (null != rolePermission.get(tbRolePO.getRoleName())) {
                            rolePermList = (ArrayList) rolePermission.get(tbRolePO.getRoleName());
                        }
                        for (TbPermissmionPO tbPerm : permissmionPOs) {
                            if (tbPerm.getPermissionId().equalsIgnoreCase(tbRolePermissionRefPO.getPermissionId())) {
                                rolePermList.add(tbPerm);
                            }
                        }
                        rolePermission.put(tbRolePO.getRoleName(), rolePermList);
                    }

                }
            }
            logger.info("系统角色权限已冲至内存。");
        }catch (Exception e){
            logger.info("系统角色权限缓存失败。");
            logger.error(e.getMessage(),e);
        }

    }


}
