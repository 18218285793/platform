
package cn.com.signheart.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExtMap extends HashMap implements Map {
    public ExtMap() {
    }

    public ExtMap(Map map) {
        super(map);
    }

    public String getString(String key) {
        Object valueObj = this.get(key);
        return valueObj == null?"":valueObj.toString();
    }

    public int getInt(String key) {
        Object valueObj = this.get(key);
        return valueObj == null?0:Integer.valueOf(valueObj.toString()).intValue();
    }

    public long getLong(String key) {
        Object valueObj = this.get(key);
        return valueObj == null?0L:Long.valueOf(valueObj.toString()).longValue();
    }

    public float getFloat(String key) {
        Object valueObj = this.get(key);
        return valueObj == null?0.0F:Float.valueOf(valueObj.toString()).floatValue();
    }

    public double getDouble(String key) {
        Object valueObj = this.get(key);
        return valueObj == null?0.0D:(double)Double.valueOf(valueObj.toString()).floatValue();
    }

    public List getList(String key) {
        Object valueObj = this.get(key);
        return valueObj == null?null:(List)valueObj;
    }

    public String getToString() {
        StringBuilder sb = new StringBuilder();
        if(!this.isEmpty()) {
            Iterator i$ = this.entrySet().iterator();

            while(i$.hasNext()) {
                Object obj = i$.next();
                Entry entry = (Entry)obj;
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(" || ");
            }
        }

        return sb.toString();
    }
}
