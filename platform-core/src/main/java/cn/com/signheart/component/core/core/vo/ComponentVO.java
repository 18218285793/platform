package cn.com.signheart.component.core.core.vo;


import cn.com.signheart.common.util.AssertUtil;
import org.jdom.Element;

public class ComponentVO {
    private String componentName;
    private Float lastVersion;
    private Boolean enabled;

    public ComponentVO() {
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Float getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(Float lastVersion) {
        this.lastVersion = lastVersion;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void initFromXml(Element cpEle) {
        this.componentName = cpEle.getAttributeValue("name");
        String ver = cpEle.getAttributeValue("lastVer");
        if(ver != null) {
            String[] status = ver.split("\\.");
            if(status.length > 2) {
                for(int i = 0; i < status.length; ++i) {
                    String s = status[i];
                    if(i == 1) {
                        ver = ver + ".";
                    }

                    ver = ver + s;
                }
            }
        }

        if(!AssertUtil.isEmpty(ver)) {
            this.lastVersion = Float.valueOf(ver);
        }

        String var6 = cpEle.getAttributeValue("enabled");
        this.enabled = Boolean.valueOf(var6 == null || Boolean.parseBoolean(var6));
    }

    public boolean equals(Object obj) {
        return obj == null?false:this.getComponentName().equals(((ComponentVO)obj).getComponentName());
    }
}
