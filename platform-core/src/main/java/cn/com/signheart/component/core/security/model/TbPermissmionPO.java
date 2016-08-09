package cn.com.signheart.component.core.security.model;


import cn.com.signheart.common.povo.IBasePO;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public class TbPermissmionPO implements IBasePO {


        String permissionId;
        String permissionCode;
        String permissionName;
        Integer targetType;
        String memo;
        String modelName;
        String component;

        public TbPermissmionPO() {
        }

        public String getPermissionId() {
            return this.permissionId;
        }

        public void setPermissionId(String permissionId) {
            this.permissionId = permissionId;
        }

        public String getPermissionCode() {
            return this.permissionCode;
        }

        public void setPermissionCode(String permissionCode) {
            this.permissionCode = permissionCode;
        }

        public String getPermissionName() {
            return this.permissionName;
        }

        public void setPermissionName(String permissionName) {
            this.permissionName = permissionName;
        }

        public Integer getTargetType() {
            return this.targetType;
        }

        public void setTargetType(Integer targetType) {
            this.targetType = targetType;
        }

        public String getMemo() {
            return this.memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getModelName() {
            return this.modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getComponent() {
            return this.component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public String _getTableName() {
            return "tb_platform_permission";
        }

        public String _getPKColumnName() {
            return "permissionId";
        }

        public String _getPKValue() {
            return this.permissionId;
        }

        public void _setPKValue(Object value) {
            this.permissionId = String.valueOf(value);
        }

}
