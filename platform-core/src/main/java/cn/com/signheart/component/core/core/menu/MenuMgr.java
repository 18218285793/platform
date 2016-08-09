
package cn.com.signheart.component.core.core.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.xml.JDomXMLUtil;
import cn.com.signheart.component.core.core.ComponentMgr;
import org.jdom.Element;
import org.springframework.util.ResourceUtils;

public class MenuMgr {
    static MenuItem rootItem = new MenuItem();

    public MenuMgr() {
    }

    public static void loadMenuList() throws Exception {
        File sysMenuFile = ResourceUtils.getFile("classpath:menu.xml");
        if(!sysMenuFile.exists()) {
            throw new FileNotFoundException("系统的菜单配置文件缺失");
        } else {
            JDomXMLUtil menuXML = new JDomXMLUtil(sysMenuFile);
            Element[] arr$ = menuXML.getElements("menu");
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Element menu = arr$[i$];
                parseMenu(rootItem, menu, menuXML);
            }

        }
    }

    private static void parseMenu(MenuItem parent, Element ele, JDomXMLUtil menuXML) throws Exception {
        String componentName = ele.getAttributeValue("ref");
        if(!AssertUtil.isEmpty(componentName)) {
            JDomXMLUtil cpXML = ComponentMgr.readComponentXML(componentName, "menu.xml");
            if(cpXML == null) {
                return;
            }

            int i$;
            if("true".equals(ele.getAttributeValue("independently"))) {
                MenuItem arr$ = new MenuItem();
                arr$.setParent(parent);
                arr$.initFromElem(ele);
                parent.addChild(arr$);
                Element[] len$ = cpXML.getElements("menu");
                i$ = len$.length;

                for(int menu = 0; menu < i$; ++menu) {
                    Element menu1 = len$[menu];
                    parseMenu(arr$, menu1, cpXML);
                }
            } else {
                if(AssertUtil.isEmpty(parent.getMenuName())) {
                    String var10 = ele.getAttributeValue("icon");
                    parent.setIcon(var10);
                    parent.setIndependently(Boolean.valueOf(false));
                }

                Element[] var11 = cpXML.getElements("menu");
                int var12 = var11.length;

                for(i$ = 0; i$ < var12; ++i$) {
                    Element var13 = var11[i$];
                    if(!AssertUtil.isEmpty(ele.getAttribute("permission"))) {
                        var13.setAttribute("permission", var13.getAttributeValue("permission") + (AssertUtil.isEmpty(var13.getAttributeValue("permission"))?"":",") + ele.getAttributeValue("permission"));
                    }

                    parseMenu(parent, var13, cpXML);
                }

                if(AssertUtil.isEmpty(parent.getMenuName())) {
                    parent.setIcon((String)null);
                    parent.setIndependently((Boolean)null);
                }
            }
        } else {
            wirteMenu(parent, ele, menuXML);
        }

    }

    private static void wirteMenu(MenuItem parent, Element ele, JDomXMLUtil menuXML) throws Exception {
        MenuItem tempCP = new MenuItem();
        tempCP.setParent(parent);
        tempCP.initFromElem(ele);
        if(parent != null) {
            if(AssertUtil.isEmpty(parent.getMenuName()) && parent.getIndependently() != null && !parent.getIndependently().booleanValue() && parent.getIcon() != null) {
                tempCP.setIcon(parent.getIcon());
                parent.setIcon((String)null);
            }

            parent.addChild(tempCP);
        }

        Element[] arr$ = menuXML.getElementsByParent(ele, "menu");
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Element menu = arr$[i$];
            parseMenu(tempCP, menu, menuXML);
        }

    }

    public static List<MenuItem> getCurrUserMenu() throws CloneNotSupportedException {
        ArrayList rList = new ArrayList();
        Iterator i$ = rootItem.getChildrenMenus().iterator();

        MenuItem menuItem;
        while(i$.hasNext()) {
            menuItem = (MenuItem)i$.next();
            rList.add(menuItem.clone());
        }

        i$ = rList.iterator();

        while(i$.hasNext()) {
            menuItem = (MenuItem)i$.next();
            check(menuItem);
        }

        filter(rList);
        return rList;
    }

    private static void check(MenuItem menuItem) {
        if(menuItem.canBeAccess()) {
            menuItem.setCanShow(Boolean.valueOf(true));
            if(menuItem.getChildrenMenus().size() > 0) {
                Iterator i$ = menuItem.getChildrenMenus().iterator();

                while(i$.hasNext()) {
                    MenuItem item = (MenuItem)i$.next();
                    check(item);
                }
            }
        } else {
            menuItem.setCanShow(Boolean.valueOf(false));
        }

    }

    private static void filter(List<MenuItem> menus) {
        Iterator mi = menus.iterator();

        while(mi.hasNext()) {
            MenuItem itemObj = (MenuItem)mi.next();
            if(!itemObj.getCanShow().booleanValue()) {
                mi.remove();
            } else if(itemObj.getChildrenMenus().size() > 0) {
                filter(itemObj.getChildrenMenus());
                if(itemObj.getChildrenMenus().isEmpty()) {
                    mi.remove();
                }
            }
        }

    }
}
