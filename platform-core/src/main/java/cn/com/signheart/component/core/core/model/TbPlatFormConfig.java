//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.com.signheart.component.core.core.model;


import cn.com.signheart.common.povo.IBasePO;

public class TbPlatFormConfig implements IBasePO {
    private String configId;
    private String componentName;
    private String modelCode;
    private String configName;
    private String configValue;

    public TbPlatFormConfig() {
    }

    public String getConfigId() {
        return this.configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getModelCode() {
        return this.modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getConfigName() {
        return this.configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return this.configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String _getTableName() {
        return "tb_platform_config";
    }

    public String _getPKColumnName() {
        return "configId";
    }

    public String _getPKValue() {
        return this.configId;
    }

    public void _setPKValue(Object value) {
        this.configId = (String)value;
    }
}
