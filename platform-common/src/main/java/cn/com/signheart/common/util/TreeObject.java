
package cn.com.signheart.common.util;

import java.util.ArrayList;
import java.util.List;

public class TreeObject {
    String icon;
    String title;
    String titleurl;
    List<TreeObject> nodes = new ArrayList();

    public TreeObject() {
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleurl() {
        return this.titleurl;
    }

    public void setTitleurl(String titleurl) {
        this.titleurl = titleurl;
    }

    public List<TreeObject> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<TreeObject> nodes) {
        this.nodes = nodes;
    }

    public void addChild(TreeObject treeObject) {
        this.nodes.add(treeObject);
    }
}
