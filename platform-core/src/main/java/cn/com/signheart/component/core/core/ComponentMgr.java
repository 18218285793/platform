package cn.com.signheart.component.core.core;

import cn.com.signheart.common.exception.ConfigLoadExcetion;
import cn.com.signheart.common.util.xml.JDomXMLUtil;
import cn.com.signheart.component.core.core.vo.ComponentVO;
import org.apache.commons.io.IOUtils;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ao.ouyang on 16-1-11.
 */
public class ComponentMgr {

    private static final transient Logger logger = LoggerFactory.getLogger(ComponentMgr.class);

    public static List<ComponentVO> commpantsVOList = new ArrayList();

    public ComponentMgr() {
    }

    static {
        try {
            commpantsVOList=ComponentMgr.getComponentList();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } catch (ConfigLoadExcetion configLoadExcetion) {
            logger.error(configLoadExcetion.getMessage(),configLoadExcetion);
        }
    }

    public static List<ComponentVO> getComponentList() throws Exception, ConfigLoadExcetion {
        Element[] cpEles = PlatFormContext.getAppCfgXml().getElements("commpants.commpant");
        if(cpEles != null && cpEles.length >= 1) {
            ArrayList cpList = new ArrayList(cpEles.length);
            Element[] arr$ = cpEles;
            int len$ = cpEles.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Element cpEle = arr$[i$];
                ComponentVO vo = new ComponentVO();
                vo.initFromXml(cpEle);
                if(cpList.contains(vo)) {
                    throw new ConfigLoadExcetion(100001003, "平台的appConfig.xml里存在重复的组件配置:" + vo.getComponentName());
                }

                cpList.add(vo);
            }

            return cpList;
        } else {
            throw new ConfigLoadExcetion(100001002, "没有为平台设置必须的配置");
        }
    }

    public static void checkComponentInit() throws Exception{
        /*commpantsVOList = this.getComponentList();
        Iterator i$ = commpantsVOList.iterator();

        while(i$.hasNext()) {
            ComponentVO component = (ComponentVO)i$.next();
            logger.debug("开始检查组件:" + component.getComponentName());
            if(!component.isDisabled()) {
                boolean updateStatu;
                if(component.notInstalled()) {
                    updateStatu = ComponentMgr.installCpt(component.getComponentName());
                    if(updateStatu) {
                        mustRestart = true;
                    }
                }

                if(component.shouldBeUpdate()) {
                    updateStatu = ComponentMgr.updateCpt(component.getComponentName());
                }

                (new FrameDAC()).runSQL(component.getComponentName(), false);
            }
        }*/

    }

    public static JDomXMLUtil readComponentXML(String componentName, String fileName) {
        InputStream webXMLFile = ComponentMgr.class.getClassLoader().getResourceAsStream(PlatFormContext.getPlatFormId().replace(".", "/") + "/component/" + componentName + "/resource/" + fileName);
        if(webXMLFile==null){
            webXMLFile = ComponentMgr.class.getClassLoader().getResourceAsStream(PlatFormContext.getInnerPlatFormId().replace(".", "/") + "/component/" + componentName + "/resource/" + fileName);
        }
        if(webXMLFile == null) {
            return null;
        } else {
            Object var4;
            try {
                JDomXMLUtil e = new JDomXMLUtil(webXMLFile);
                return e;
            } catch (Exception var8) {
                logger.error(componentName + "组件下的" + fileName + "读入错误", var8);
                var4 = null;
            } finally {
                IOUtils.closeQuietly(webXMLFile);
            }

            return (JDomXMLUtil)var4;
        }
    }


}
