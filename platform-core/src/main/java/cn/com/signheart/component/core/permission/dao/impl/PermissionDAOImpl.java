
package cn.com.signheart.component.core.permission.dao.impl;

import cn.com.signheart.common.platformbase.BaseDAOImpl;
import cn.com.signheart.component.core.permission.dao.IPermissionDAO;
import cn.com.signheart.component.core.permission.model.PermissionPO;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Repository("permissionDAO")
public class PermissionDAOImpl extends BaseDAOImpl implements IPermissionDAO {
    @Override
    public List<String> listPermisionByUserId(String var1) throws SQLException {
        return null;
    }

    @Override
    public int countByCode(String var1) throws SQLException {
        return 0;
    }

    @Override
    public List<String> listAllPermissionCode() throws SQLException {
        return null;
    }

    @Override
    public List<PermissionPO> listPermisionByComponent(String var1) throws SQLException {
        return null;
    }

    @Override
    public void updateByCode(PermissionPO var1) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, SQLException {

    }
}
