package cn.com.signheart.component.core.core;


import cn.com.signheart.common.exception.DefaultException;
import cn.com.signheart.component.core.core.menu.MenuMgr;

import java.sql.SQLException;

public class FrameLoader {
    private static FrameLoader loaderInstance;

    public FrameLoader() {
    }

    public static synchronized FrameLoader getInstance() throws SQLException {
        if(loaderInstance == null) {
            loaderInstance = new FrameLoader();
        }

        return loaderInstance;
    }

    public void loadFrame() throws Exception,DefaultException {
        ConfigMgr.loadConfig();
        PlatFormContext.setPlatFormId(ConfigMgr.get("sys","platformId"));
        PlatFormContext.setInnerPlatFormId(ConfigMgr.get("sys", "innerplatfromId"));
        PlatFormContext.parseAppPathInfo();
        PlatFormContext.parseAppCfg();
        ComponentMgr.checkComponentInit();
        MenuMgr.loadMenuList();
        PermissionMgr.loadPermission();
    }

}
