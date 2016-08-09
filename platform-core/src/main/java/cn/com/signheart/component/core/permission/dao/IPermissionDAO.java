
package cn.com.signheart.component.core.permission.dao;


import cn.com.signheart.common.platformbase.IBaseDao;
import cn.com.signheart.component.core.permission.model.PermissionPO;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface IPermissionDAO extends IBaseDao {
    List<String> listPermisionByUserId(String var1) throws SQLException;

    int countByCode(String var1) throws SQLException;

    List<String> listAllPermissionCode() throws SQLException;


    List<PermissionPO> listPermisionByComponent(String var1) throws SQLException;

    void updateByCode(PermissionPO var1) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, SQLException;
}
