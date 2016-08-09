package cn.com.signheart.component.sys.sysuser.model;


import cn.com.signheart.common.povo.IBasePO;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public class TbUserRoleRefPO implements IBasePO {

    private String refId;
    private String userId;
    private String roleId;

    public TbUserRoleRefPO() {
    }

    public String getRefId() {
        return this.refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String _getTableName() {
        return "tb_user_role_ref";
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
}
