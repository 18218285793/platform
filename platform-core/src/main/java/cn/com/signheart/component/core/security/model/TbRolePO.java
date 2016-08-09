//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.com.signheart.component.core.security.model;

import cn.com.signheart.common.povo.IBasePO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

public class TbRolePO implements IBasePO {
    private String roleId;
    private String roleName;
    private String roleDescription;
    private Integer status;

    public TbRolePO() {
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return this.roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String _getTableName() {
        return "tb_platform_role";
    }

    public String _getPKColumnName() {
        return "roleId";
    }

    public String _getPKValue() {
        return this.roleId;
    }

    public void _setPKValue(Object o) {
        this.roleId = String.valueOf(o);
    }



}
