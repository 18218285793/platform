package cn.com.signheart.component.core.core;


import cn.com.signheart.common.exception.ConfigLoadExcetion;
import cn.com.signheart.component.core.core.model.TbPlatFormConfig;
import cn.com.signheart.component.core.core.service.IPlatFormCfgService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ao.ouyang on 16-1-11.
 */
public class ConfigMgr {

    private static Map<String, String[]> customerConfigMap = new HashMap();


    public static void loadConfig() throws  SQLException {
        IPlatFormCfgService platFormCfgService = (IPlatFormCfgService)PlatFormContext.getSpringContext().getBean("platFormCfgService");
        List cfgList = platFormCfgService.listAllCfg();
        Iterator i$ = cfgList.iterator();

        while(i$.hasNext()) {
            TbPlatFormConfig platFormConfig = (TbPlatFormConfig)i$.next();
            customerConfigMap.put(platFormConfig.getComponentName() + "\\" + platFormConfig.getConfigName(), new String[]{platFormConfig.getConfigId(), platFormConfig.getConfigValue()});
        }
    }

    public static String get(String componentName, String cfgName) throws ConfigLoadExcetion {
        String[] rs =  (String[])customerConfigMap.get(componentName + "\\" + cfgName);
        if(rs == null) {
            throw new ConfigLoadExcetion(100001001, componentName + "下找不到名为" + cfgName + "的配置项");
        } else {
            return rs[1];
        }
    }

    public static void  main(String[] args){
    }

}
