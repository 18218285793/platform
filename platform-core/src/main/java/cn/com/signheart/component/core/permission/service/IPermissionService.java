package cn.com.signheart.component.core.permission.service;



import cn.com.signheart.component.core.permission.model.PermissionPO;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface IPermissionService {

    void addPermission(PermissionPO var1) throws Exception;

    void clearPermissionByComponent(String var1);

    List<PermissionPO> listPermissionByComponent(String var1) throws SQLException;

    List<PermissionPO> getPermissionByName(String var1);

    PermissionPO getPermissionByCode(String var1);


    List<String> listAllPermission() throws SQLException;

    List<PermissionPO> loadAllPermission() throws Exception;

    void updaePermission(PermissionPO var1) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;
}
