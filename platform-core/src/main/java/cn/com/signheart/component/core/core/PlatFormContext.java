package cn.com.signheart.component.core.core;

import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.FileUtil;
import cn.com.signheart.common.util.StringUtil;
import cn.com.signheart.common.util.xml.JDomXMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by ao.ouyang on 15-11-5.
 */
public class PlatFormContext {
    private static final transient Logger logger = LoggerFactory.getLogger(PlatFormContext.class);

    private static ApplicationContext springContext;

    private static String currentIP;

    private static String platVer;

    private static String platFormId;

    private static String innerPlatFormId;

    private static JDomXMLUtil appCfgXml;

    private static String componentRootPath;

    private static String appRootPath;

    private static String innerComponentRootPath;

    private static String innerAppRootPath;

    private static String classRootPath;

    public PlatFormContext(){}

    public static void parseAppPathInfo() {
        parseAppPathInfo(Thread.currentThread().getContextClassLoader().getResource("/").getPath());
    }

    public static void parseAppPathInfo(String path) {
        if(AssertUtil.isEmpty(path)) {
            path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        }

        String tempPath = path.replaceAll("\\%20", " ");
        componentRootPath = tempPath + FileUtil.buildFilePath(new String[]{platFormId, "component"});
        classRootPath = tempPath;
        appRootPath = tempPath.replaceAll("WEB-INF[/\\\\]classes[/\\\\]", "");
        appRootPath = StringUtil.replace(appRootPath, FileUtil.buildFilePath(new String[]{"WEB-INF", "classes"}), "");
        if(platFormId.equals(innerPlatFormId)){
            innerAppRootPath=appRootPath;
            innerComponentRootPath=componentRootPath;
        }else{
            innerComponentRootPath=componentRootPath.replace(platFormId,innerPlatFormId);
            innerAppRootPath=appRootPath.replace(platFormId,innerPlatFormId);
        }
    }

    public static void parseAppCfg() {
        try {
            appCfgXml = new JDomXMLUtil(FileUtil.buildFilePath(new String[]{classRootPath, "appConfig.xml"}));
            System.out.println("根路径为:" + classRootPath);
        } catch (Exception var1) {
            System.out.println("根路径为:" + classRootPath);
            logger.error("装载平台配置出错，无法找到配置文件", var1);
        }

    }

    public static void setSpringContext(ApplicationContext springContext) {
        PlatFormContext.springContext = springContext;
    }

    public static String getComponentRootPath() {
        return componentRootPath;
    }

    public static void setComponentRootPath(String componentRootPath) {
        PlatFormContext.componentRootPath = componentRootPath;
    }

    public static String getAppRootPath() {
        return appRootPath;
    }

    public static void setAppRootPath(String appRootPath) {
        PlatFormContext.appRootPath = appRootPath;
    }

    public static String getInnerComponentRootPath() {
        return innerComponentRootPath;
    }

    public static void setInnerComponentRootPath(String innerComponentRootPath) {
        PlatFormContext.innerComponentRootPath = innerComponentRootPath;
    }

    public static String getInnerAppRootPath() {
        return innerAppRootPath;
    }

    public static void setInnerAppRootPath(String innerAppRootPath) {
        PlatFormContext.innerAppRootPath = innerAppRootPath;
    }

    public static String getClassRootPath() {
        return classRootPath;
    }

    public static void setClassRootPath(String classRootPath) {
        PlatFormContext.classRootPath = classRootPath;
    }

    public static ApplicationContext getSpringContext() {
        return springContext;
    }

    public static String getCurrentIP() {
        return currentIP;
    }

    public static void setCurrentIP(String currentIP) {
        PlatFormContext.currentIP = currentIP;
    }

    public static String getPlatVer() {
        return platVer;
    }

    public static void setPlatVer(String platVer) {
        PlatFormContext.platVer = platVer;
    }

    public static String getPlatFormId() {
        return platFormId;
    }

    public static void setPlatFormId(String platFormId) {
        PlatFormContext.platFormId = platFormId;
    }

    public static String getInnerPlatFormId() {
        return innerPlatFormId;
    }

    public static void setInnerPlatFormId(String innerPlatFormId) {

        PlatFormContext.innerPlatFormId = innerPlatFormId;
    }

    public static JDomXMLUtil getAppCfgXml() {
        return appCfgXml;
    }

    public static void setAppCfgXml(JDomXMLUtil appCfgXml) {
        PlatFormContext.appCfgXml = appCfgXml;
    }

}
