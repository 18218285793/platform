
package cn.com.signheart.component.core.permission.service.impl;

import cn.com.signheart.component.core.permission.dao.IPermissionDAO;
import cn.com.signheart.component.core.permission.model.PermissionPO;
import cn.com.signheart.component.core.permission.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {
    @Resource
    IPermissionDAO permissionDAO;

    public PermissionServiceImpl() {
    }

    @Override
    public void addPermission(PermissionPO var1) throws Exception {

    }

    @Override
    public void clearPermissionByComponent(String var1) {

    }

    @Override
    public List<PermissionPO> listPermissionByComponent(String var1) throws SQLException {
        return null;
    }

    @Override
    public List<PermissionPO> getPermissionByName(String var1) {
        return null;
    }

    @Override
    public PermissionPO getPermissionByCode(String var1) {
        return null;
    }

    @Override
    public List<String> listAllPermission() throws SQLException {
        return null;
    }

    @Override
    public List<PermissionPO> loadAllPermission() throws Exception {
        return null;
    }

    @Override
    public void updaePermission(PermissionPO var1) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

    }


}
