package cn.com.signheart.component.core.security.model;


import cn.com.signheart.common.povo.IBasePO;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public class TbRolePermissionRefPO implements IBasePO {

    private String refId;
    private String roleId;
    private String permissionId;

    public TbRolePermissionRefPO() {
    }

    public String _getTableName() {
        return "tb_role_permission_ref";
    }

    public String _getPKColumnName() {
        return "refId";
    }

    public String _getPKValue() {
        return this.refId;
    }

    public void _setPKValue(Object o) {
        this.refId = String.valueOf(o);
    }

    public String getRefId() {
        return this.refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
