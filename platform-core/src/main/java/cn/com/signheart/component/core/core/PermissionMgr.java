
package cn.com.signheart.component.core.core;

import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.xml.JDomXMLUtil;
import cn.com.signheart.component.core.core.vo.ComponentVO;
import cn.com.signheart.component.core.permission.model.PermissionPO;
import cn.com.signheart.component.core.permission.service.IPermissionService;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.springframework.beans.BeansException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PermissionMgr {
    static Logger log = Logger.getLogger(PermissionMgr.class);
    static Map<String, PermissionPO> permissionMap = new ConcurrentHashMap();

    public PermissionMgr() {
    }

    public static void clearCache() throws Exception{
        permissionMap.clear();
    }

    public static void loadPermission() throws Exception{
        Iterator i$ = ComponentMgr.commpantsVOList.iterator();

        while(i$.hasNext()) {
            ComponentVO component = (ComponentVO)i$.next();
            PermissionMgr.initPermission(component.getComponentName());
        }

        cn.com.signheart.component.core.core.PermissionMgr.clearCache();
    }

    public static void initPermission(String compoentName) throws Exception{
        JDomXMLUtil permXML = ComponentMgr.readComponentXML(compoentName, "permission.xml");
        if(permXML != null) {
            IPermissionService permissionService = null;

            try {
                permissionService = (IPermissionService)PlatFormContext.getSpringContext().getBean("permissionService");
            } catch (BeansException var10) {
                log.warn("注意：没有找到名为permissionService的BEAN，组件的权限初始化失败");
                return;
            }

            permissionService.clearPermissionByComponent(compoentName);
            if(permissionMap.isEmpty()) {
                List permEle = permissionService.loadAllPermission();
                if(permEle != null) {
                    Iterator arr$ = permEle.iterator();

                    while(arr$.hasNext()) {
                        PermissionPO len$ = (PermissionPO)arr$.next();
                        permissionMap.put(len$.getPermissionCode(), len$);
                    }
                }
            }

            Element[] var11 = permXML.getElements("perm");
            if(var11 != null && var11.length > 0) {
                Element[] var12 = var11;
                int var13 = var11.length;

                for(int i$ = 0; i$ < var13; ++i$) {
                    Element element = var12[i$];
                    if(!permissionMap.containsKey(element.getAttributeValue("code"))) {
                        PermissionPO var14 = new PermissionPO();
                        var14.setPermissionCode(element.getAttributeValue("code"));
                        var14.setPermissionName(element.getAttributeValue("name"));
                        var14.setComponent(compoentName);
                        var14.setModelName(element.getAttributeValue("model"));
                        var14.setTargetType(0);
                        if(!AssertUtil.isEmpty(var14.getPermissionCode())) {
                            permissionService.addPermission(var14);
                        }
                    } else {
                        boolean update = false;
                        PermissionPO permission = (PermissionPO)permissionMap.get(element.getAttributeValue("code"));
                        if(AssertUtil.isEmpty(permission.getComponent()) && !compoentName.equals(permission.getComponent())) {
                            permission.setComponent(compoentName);
                            update = true;
                        }

                        if(AssertUtil.isEmpty(permission.getModelName()) || !element.getAttributeValue("model").equals(permission.getModelName())) {
                            permission.setModelName(element.getAttributeValue("model"));
                            update = true;
                        }

                        if(!permission.getPermissionName().equals(element.getAttributeValue("name"))) {
                            permission.setPermissionName(element.getAttributeValue("name"));
                            update = true;
                        }
                        permission.setTargetType(0);
                        update = true;
                        if(update) {
                            permissionService.updaePermission(permission);
                        }
                    }
                }
            }
        }

    }
}
