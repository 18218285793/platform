package cn.com.signheart.common.povo;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * Created by ao.ouyang on 15-11-2.
 */
public class IBaseVO implements Serializable {

    public String toJsonString() {
        JSONObject obj = JSONObject.fromObject(this);
        return obj.toString();
    }
}
