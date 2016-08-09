package cn.com.signheart.component.core.user.model;


import cn.com.signheart.common.povo.IBasePO;

import java.util.Date;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public class TbUserPO implements IBasePO {

    String userId;
    String userName;
    String password;
    String salt;
    Integer status;
    Date lastLoginTime;
    Integer isInternal;

    public TbUserPO() {
    }


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt(){
        return userName + salt;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getIsInternal() {
        return this.isInternal;
    }

    public void setIsInternal(Integer isInternal) {
        this.isInternal = isInternal;
    }

    public String _getTableName() {
        return "tb_platform_user";
    }

    public String _getPKColumnName() {
        return "userId";
    }

    public String _getPKValue() {
        return this.userId;
    }

    public void _setPKValue(Object value) {
        this.userId = String.valueOf(value);
    }
}
