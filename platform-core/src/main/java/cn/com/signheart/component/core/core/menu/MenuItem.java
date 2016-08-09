
package cn.com.signheart.component.core.core.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.TreeObject;
import org.apache.shiro.SecurityUtils;
import org.jdom.Element;

public class MenuItem implements Cloneable {
    private String icon;
    private String menuName;
    private String link;
    private List<String> roles = new ArrayList();
    private List<MenuItem> childrenMenus = new ArrayList();
    private Boolean independently;
    private MenuItem parent = null;
    private Boolean canShow = Boolean.valueOf(false);

    public MenuItem() {
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public void addChild(MenuItem itemObj) {
        this.childrenMenus.add(itemObj);
    }

    public Boolean getIndependently() {
        return this.independently;
    }

    public void setIndependently(Boolean independently) {
        this.independently = independently;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<MenuItem> getChildrenMenus() {
        return this.childrenMenus;
    }

    public void setChildrenMenus(List<MenuItem> childrenMenus) {
        this.childrenMenus = childrenMenus;
    }

    public MenuItem getParent() {
        return this.parent;
    }

    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    public Boolean getCanShow() {
        return this.canShow;
    }

    public void setCanShow(Boolean canShow) {
        this.canShow = canShow;
        if(this.parent != null && !this.parent.getCanShow().booleanValue()) {
            this.parent.setCanShow(Boolean.valueOf(true));
        }

    }

    public void initFromElem(Element ele) {
        this.setMenuName(ele.getAttributeValue("name"));
        this.setLink(ele.getAttributeValue("link"));
        this.setIcon(ele.getAttributeValue("icon"));
        this.setIndependently(Boolean.valueOf(Boolean.parseBoolean(ele.getAttributeValue("independently"))));
        String roles = ele.getAttributeValue("permission");
        if(!AssertUtil.isEmpty(roles)) {
            this.setRoles(Arrays.asList(roles.split("\\,")));
        }

    }

    public boolean canBeAccess() {
        if(this.roles.size() < 1) {
            return true;
        } else {
            if(SecurityUtils.getSubject().getPrincipal() !=null) {
                Iterator i$ = this.roles.iterator();

                while(i$.hasNext()) {
                    String role = (String)i$.next();
                    if(SecurityUtils.getSubject().isPermitted(role)){
                        return true;
                    }
                }

                return false;
            } else {
                return false;
            }
        }
    }
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TreeObject converToTreeObject() {
        TreeObject obj = new TreeObject();
        obj.setTitle(this.getMenuName());
        obj.setTitleurl(this.getLink());
        obj.setIcon(this.getIcon());
        Iterator i$ = this.childrenMenus.iterator();

        while(i$.hasNext()) {
            MenuItem child = (MenuItem)i$.next();
            obj.addChild(child.converToTreeObject());
        }

        return obj;
    }

    protected MenuItem clone() throws CloneNotSupportedException {
        MenuItem menuItem = new MenuItem();
        menuItem.setIcon(this.getIcon());
        menuItem.setCanShow(this.getCanShow());
        menuItem.setIndependently(this.independently);
        menuItem.setLink(this.link);
        menuItem.setMenuName(this.menuName);
        menuItem.setParent(this.parent);
        menuItem.setRoles(this.roles);
        if(!this.childrenMenus.isEmpty()) {
            menuItem.setChildrenMenus(new ArrayList());
            Iterator i$ = this.childrenMenus.iterator();

            while(i$.hasNext()) {
                MenuItem childrenMenu = (MenuItem)i$.next();
                menuItem.addChild(childrenMenu.clone());
            }
        }

        return menuItem;
    }
}
