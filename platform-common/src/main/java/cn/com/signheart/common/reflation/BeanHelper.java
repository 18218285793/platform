
package cn.com.signheart.common.reflation;

import cn.com.signheart.common.exception.DefaultException;
import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.StringUtil;
import cn.com.signheart.common.util.WebUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BeanHelper {
    public BeanHelper() {
    }

    public static void copyProperties(Object dist, Object source) {
        try {
            if(Map.class.isInstance(dist)) {
                map2Bean((Map)dist, source);
            } else {
                PropertyUtils.copyProperties(dist, source);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private static void copyProperties(Object dist, Object source, boolean ignoreException, String... beanField) {
        if(null != beanField && 0 < beanField.length) {
            String[] arr$ = beanField;
            int len$ = beanField.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String property = arr$[i$];

                try {
                    Object e = getPrivateProperty(source, property);
                    setPrivateProperty(dist, property, e);
                } catch (Exception var9) {
                    if(!ignoreException) {
                        throw new RuntimeException(var9);
                    }
                }
            }
        }

    }


    public static void copyFormatProperties(Object targeObj, Object sourceObj, Class sourceClass, Class targetClass, boolean overwrite) throws Exception, NoSuchFieldException, IllegalAccessException, InvocationTargetException, DefaultException {
        if(sourceObj != null) {
            if(targeObj == null) {
                targeObj = sourceObj.getClass().newInstance();
            }

            Field[] targetFields = targetClass.getDeclaredFields();
            Field[] arr$ = targetFields;
            int len$ = targetFields.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field targetField = arr$[i$];
                Field sourceField = null;
                Method targetMethod = null;

                try {
                    sourceField = sourceClass.getDeclaredField(targetField.getName());
                    if(!targetField.getType().getName().equals("java.lang.String") && !sourceField.getType().getName().equals("java.lang.String")) {
                        targetMethod = targetClass.getDeclaredMethod("set" + StringUtil.bigFirstChar(targetField.getName()), new Class[]{sourceField.getType()});
                    } else {
                        targetMethod = targetClass.getDeclaredMethod("set" + StringUtil.bigFirstChar(targetField.getName()), new Class[]{String.class});
                    }
                } catch (Exception var14) {
                    continue;
                }

                targetField.setAccessible(true);
                sourceField.setAccessible(true);
                if(sourceField.get(sourceObj) != null) {
                    if(!ClassTypeUtil.isCollection(sourceField.getType()) && !ClassTypeUtil.isMap(sourceField.getType()) && !ClassTypeUtil.isList(sourceField.getType())) {
                        if(!targetField.getType().getName().equals("java.lang.String") && !sourceField.getType().getName().equals("java.lang.String")) {
                            if(targetField.get(targeObj) == null) {
                                targetField.set(targeObj, targetField.getType().newInstance());
                            }

                            copyFormatProperties(targetField.get(targeObj), sourceField.get(sourceObj), overwrite);
                        } else {
                            Object targetValue = targetField.get(targeObj);
                            if(targetValue == null || overwrite) {
                                Object sourceValue = sourceField.get(sourceObj);
                                if(sourceValue != null) {
                                    targetMethod.invoke(targeObj, new Object[]{String.valueOf(sourceValue)});
                                }
                            }
                        }
                    } else {
                        targetField.set(targeObj, sourceField.get(sourceObj));
                    }
                } else if(overwrite) {
                    targetField.set(targeObj, (Object)null);
                }
            }
        }

        if(!"java.lang.Object".equals(sourceClass.getSuperclass().getName())) {
            copyFormatProperties(targeObj, sourceObj, sourceClass.getSuperclass(), targetClass, overwrite);
        }

        if(!"java.lang.Object".equals(targetClass.getSuperclass().getName())) {
            copyFormatProperties(targeObj, sourceObj, sourceClass, targetClass.getSuperclass(), overwrite);
        }

    }

    public static void copyFormatProperties(Object targeObj, Object sourceObj, boolean overwrite) throws Exception, NoSuchFieldException, IllegalAccessException, InvocationTargetException, DefaultException {
        copyFormatProperties(targeObj, sourceObj, sourceObj.getClass(), targeObj.getClass(), overwrite);
    }



    public static void copyProperties2(Object dist, Object source, String... ignoreSourceBeanField) {
        ArrayList fields = null;

        try {
            fields = new ArrayList(Arrays.asList(ClassUtil.getFieldName(source.getClass())));
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }

        String[] s = ignoreSourceBeanField;
        int len$ = ignoreSourceBeanField.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String e = s[i$];
            fields.remove(e);
        }

        s = new String[fields.size()];
        fields.toArray(s);
        copyProperties(dist, source, s);
    }

    public static void copyProperties(Object dist, Object source, String... beanField) {
        copyProperties(dist, source, false, beanField);
    }

    public static void copyPropertiesByIgnoreException(Object dist, Object source, String... beanField) {
        copyProperties(dist, source, true, beanField);
    }

    public static void copyBeanProperties(Object dist, Object source) {
        try {
            if(Map.class.isInstance(dist)) {
                map2Bean((Map)dist, source);
            } else {
                BeanUtils.copyProperties(dist, source);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void map2Bean(Map map, Object bean) throws Exception {
        map2Bean(map, bean, true);
    }

    public static void map2Bean(Map map, Object bean, boolean overwrite) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Set keySet = map.keySet();
        Iterator i$ = keySet.iterator();

        while(i$.hasNext()) {
            Object keyObj = i$.next();
            String key = (String)keyObj;
            Object value = map.get(key);

            try {
                Field e = bean.getClass().getDeclaredField(key);
                e.setAccessible(true);
                if(overwrite) {
                    setFieldValue(bean.getClass(), bean, e, map.get(key));
                } else if(value != null && !"".equals(value.toString())) {
                    setFieldValue(bean.getClass(), bean, e, map.get(key));
                }
            } catch (Exception var9) {
                ;
            }
        }

    }

    public static void bean2Map(Object bean, Map map) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        bean2Map(bean, map, true);
    }

    public static void bean2Map(Object bean, Map map, boolean overwrite) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class beanClass = bean.getClass();
        Field[] beanFields = beanClass.getDeclaredFields();
        if(!AssertUtil.isEmpty(beanFields)) {
            Field[] arr$ = beanFields;
            int len$ = beanFields.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field beanField = arr$[i$];
                beanField.setAccessible(true);
                Object value = beanField.get(bean);
                if(overwrite) {
                    map.put(beanField.getName(), value);
                } else if(value != null && !"".equals(value.toString())) {
                    map.put(beanField.getName(), value);
                }
            }
        }

    }

    public static void copyProperties(Object targeObj, Object sourceObj, boolean overwrite) throws IllegalAccessException, InstantiationException {
        if(sourceObj != null) {
            if(targeObj == null) {
                targeObj = sourceObj.getClass().newInstance();
            }

            if(targeObj.getClass().isInstance(sourceObj) && Map.class.isInstance(targeObj)) {
                Set keySet = ((Map)sourceObj).keySet();
                Iterator i$ = keySet.iterator();

                while(i$.hasNext()) {
                    Object keyObj = i$.next();
                    String key = (String)keyObj;
                    Object value = ((Map)sourceObj).get(key);
                    if(overwrite) {
                        ((Map)targeObj).put(key, value);
                    } else if(value != null && !"".equals(value.toString())) {
                        ((Map)targeObj).put(key, value);
                    }
                }
            } else {
                copyProperties(targeObj, sourceObj);
            }
        }

    }

    private static void setFieldValue(Class _beanCls, Object _bean, Field _field, Object _value) throws Exception {
        String fieldName = _field.getName();
        Method method = _beanCls.getDeclaredMethod("set" + StringUtil.bigFirstChar(fieldName), new Class[]{_field.getType()});
        method.setAccessible(true);
        if(_value != null) {
            switch(ClassTypeUtil.getTypeByClass(_field.getType())) {
                case -1:
                    throw new Exception(_field.getType().getName() + "类型未在平台中定义！");
                case 0:
                    _value = ConvertUtil.cvStIntg(_value.toString());
                    break;
                case 1:
                    _value = ConvertUtil.Str2Float(_value.toString());
                    break;
                case 2:
                    _value = ConvertUtil.Str2Double(_value.toString());
                    break;
                case 3:
                    _value = ConvertUtil.cvStBD(_value.toString());
                    break;
                case 4:
                    _value = ConvertUtil.Str2Bool(_value.toString());
                    break;
                case 5:
                    _value = _value.toString();
                    break;
                case 6:
                    _value = ConvertUtil.cvStDate(_value.toString());
                    break;
                case 7:
                    _value = ConvertUtil.cvStTims(_value.toString());
                case 8:
                default:
                    break;
                case 9:
                    _value = ConvertUtil.cvStLong(_value.toString());
                    break;
                case 10:
                    _value = ConvertUtil.cvStUtildate(_value.toString());
                    break;
                case 11:
                    _value = ConvertUtil.cvStByte(_value.toString());
            }

            method.invoke(_bean, new Object[]{_value});
        } else {
            method.invoke(_bean, new Object[0]);
        }

    }

    public static String getBeanFieldName(String _name) throws Exception {
        _name = StringUtil.CnvSmallChr(_name);
        if(_name.indexOf("_") > -1) {
            String[] temp = StringUtil.splitString(_name, "_");

            for(int i = 1; i < temp.length; ++i) {
                temp[i] = StringUtil.bigFirstChar(temp[i]);
            }

            _name = StringUtil.uniteArry(temp, "");
        }

        return _name;
    }

    public static String getOracleFieldName(String _name) throws Exception {
        return getOracleFieldName(_name, false);
    }

    public static String getOracleFieldName(String _name, boolean erveryBigWord) throws Exception {
        char[] nameChrs = _name.toCharArray();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < nameChrs.length; ++i) {
            if(!StringUtil.isBigChr(nameChrs[i])) {
                sb.append(StringUtil.getRefChar(nameChrs[i]));
            } else {
                if(i > 0 && (StringUtil.isSmallChr(nameChrs[i - 1]) || erveryBigWord)) {
                    sb.append("_");
                }

                sb.append(nameChrs[i]);
            }
        }

        return sb.toString();
    }

    public static String getOracleFieldName(Field field) throws Exception {
        String name = field.getName();
        return getOracleFieldName(name, false);
    }

    public static Object getBeanFromResult(ResultSet _rs, Object _obj, Class valueCls) throws Exception {
        Field[] fields = ClassUtil.getFields(valueCls);

        for(int j = 0; j < fields.length; ++j) {
            String fieldName = getOracleFieldName(fields[j].getName());

            try {
                String vtp = _rs.getString(fieldName);
                if(vtp != null) {
                    setFieldValue(valueCls, _obj, fields[j], vtp);
                }
            } catch (SQLException var8) {
                ;
            }
        }

        if(!"java.lang.Object".equals(valueCls.getSuperclass().getName())) {
            getBeanFromResult(_rs, _obj, valueCls.getSuperclass());
        }

        return _obj;
    }

    public static <T> T getBeanFromResult(ResultSet _rs, Class<T> _cls) throws Exception {
        switch(ClassTypeUtil.getTypeByClass(_cls)) {
            case 0:
                return _rs.getString(1) == null?null: (T) Integer.valueOf(_rs.getInt(1));
            case 1:
                return _rs.getString(1) == null?null: (T) Float.valueOf(_rs.getFloat(1));
            case 2:
                return _rs.getString(1) == null?null:(T)Double.valueOf(_rs.getDouble(1));
            case 3:
                return _rs.getString(1) == null?null:(T)_rs.getBigDecimal(1);
            case 4:
                return _rs.getString(1) == null?null:(T)Boolean.valueOf(_rs.getBoolean(1));
            case 5:
                return _rs.getString(1) == null?null:(T)_rs.getString(1);
            case 6:
                return _rs.getString(1) == null?null:(T)_rs.getDate(1);
            case 7:
                return _rs.getString(1) == null?null:(T)_rs.getTimestamp(1);
            case 8:
            default:
                return (T)getBeanFromResult(_rs, _cls.newInstance(), _cls);
            case 9:
                return _rs.getString(1) == null?null:(T)Long.valueOf(_rs.getLong(1));
            case 10:
                return _rs.getString(1) == null?null:(T)ConvertUtil.cvStUtildate(_rs.getString(1));
        }
    }

    public static Object getBeanFromResult(ResultSet _rs, String _clsName) throws Exception {
        return getBeanFromResult(_rs, Class.forName(_clsName));
    }

    public static Object repairForm(HttpServletRequest request, Object form) throws Exception {
        if(form != null) {
            Field[] formFields = ClassUtil.getFields(form.getClass());

            for(int i = 0; i < formFields.length; ++i) {
                Object fieldValue = formFields[i].get(form);
                if(fieldValue != null && ClassTypeUtil.isNumberType(fieldValue.getClass()) && WebUtil.ParmIsNull(request.getParameter(formFields[i].getName()))) {
                    formFields[i].set(form, (Object)null);
                }
            }
        }

        return form;
    }

    public static <T> T getBeanFromRequest(HttpServletRequest _request, Class<T> _beanCls) throws Exception {
        return (T) ObjectUtil.getValueFromReq(_request, _beanCls.newInstance());
    }

    public static Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException, NoSuchFieldException {
        Field field;
        try {
            field = object.getClass().getDeclaredField(propertyName);
        } catch (NoSuchFieldException var4) {
            field = object.getClass().getSuperclass().getDeclaredField(propertyName);
        }

        field.setAccessible(true);
        return field.get(object);
    }

    public static void setPrivateProperty(Object object, String propertyName, Object newValue) throws IllegalAccessException, NoSuchFieldException {
        Field field;
        try {
            field = object.getClass().getDeclaredField(propertyName);
        } catch (NoSuchFieldException var5) {
            field = object.getClass().getSuperclass().getDeclaredField(propertyName);
        }

        int modifier = field.getModifiers();
        if(!Modifier.isFinal(modifier)) {
            field.setAccessible(true);
            field.set(object, newValue);
            field.setAccessible(false);
        }
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class[] types = new Class[params.length];

        for(int method = 0; method < params.length; ++method) {
            types[method] = params[method].getClass();
        }

        Method var5 = object.getClass().getDeclaredMethod(methodName, types);
        var5.setAccessible(true);
        return var5.invoke(object, params);
    }

    public static Object invokePrivateMethod(Object object, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokePrivateMethod(object, methodName, new Object[0]);
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object param) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokePrivateMethod(object, methodName, new Object[]{param});
    }
}
