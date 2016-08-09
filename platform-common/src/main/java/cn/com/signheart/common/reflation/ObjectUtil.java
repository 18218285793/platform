
package cn.com.signheart.common.reflation;


import cn.com.signheart.common.util.AssertUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ObjectUtil {
    public ObjectUtil() {
    }

    public static Object invokMeth(Object obj, String MethName) throws Exception {
        if(obj == null) {
            return null;
        } else {
            Object parObj = null;

            try {
                return findMeth(obj.getClass(), MethName).invoke(obj, (Object[])parObj);
            } catch (Exception var4) {
                throw var4;
            }
        }
    }

    public static Method findMeth(Class Cls, String MethName) {
        try {
            Method[] e = ClassUtil.getMethods(Cls);
            if(!AssertUtil.isEmpty(e)) {
                for(int i = 0; i < e.length; ++i) {
                    if(e[i].getName().equals(MethName)) {
                        return e[i];
                    }
                }
            }

            return null;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static void setValue(Object obj, String name, Object value) throws Exception {
        Field field = ClassUtil.getField(obj.getClass(), name);
        if(field != null && !Modifier.isFinal(field.getModifiers())) {
            field.set(obj, value);
        } else {
            throw new Exception(obj.getClass().getName() + "中没有" + name + "字段！");
        }
    }

    public static Object getValue(Object obj, String name) throws Exception {
        Field field = ClassUtil.getField(obj.getClass(), name);
        if(field != null) {
            return field.get(obj);
        } else {
            throw new Exception(obj.getClass().getName() + "中没有" + name + "字段！");
        }
    }

    public static Object getValue(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
        return null != obj && null != field?field.get(obj):null;
    }

    public static Object getValueFromReq(HttpServletRequest request, Object valueObj) throws Exception {
        try {
            Field[] ex = ClassUtil.getFields(valueObj.getClass());

            for(int i = 0; i < ex.length; ++i) {
                String obj = request.getParameter(ex[i].getName());
                if(obj != null && !Modifier.isFinal(ex[i].getModifiers())) {
                    setValue(valueObj, ex[i].getName(), obj);
                }
            }

            return valueObj;
        } catch (Exception var5) {
            var5.printStackTrace();
            throw var5;
        }
    }
}
