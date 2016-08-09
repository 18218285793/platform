
package cn.com.signheart.common.reflation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

public class ClassUtil {
    private static WeakHashMap FMP = new WeakHashMap();
    private static WeakHashMap MMP = new WeakHashMap();
    public static String[] containerList = new String[]{"javax.swing.JRootPane", "javax.swing.JPanel", "javax.swing.JScrollPane", "javax.swing.JLayeredPane"};

    public ClassUtil() {
    }

    private static void parseClass(String ClassName) throws Exception {
        Class cls = Class.forName(ClassName);
        parseClass(cls);
    }

    public static Field[] getField(Class clz, boolean includeSuper) {
        ArrayList fieldList = new ArrayList();
        Field[] fields = clz.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field field = var7[var5];
            field.setAccessible(true);
        }

        Collections.addAll(fieldList, fields);
        if(includeSuper && clz.getSuperclass() != null && !"java.lang.Object".equals(clz.getSuperclass().getName())) {
            Collections.addAll(fieldList, getField(clz.getSuperclass(), true));
        }

        return (Field[])fieldList.toArray(new Field[fieldList.size()]);
    }

    private static synchronized void parseClass(Class cls) throws Exception {
        Field[] field = getField(cls, false);
        Method[] method = cls.getDeclaredMethods();

        int i;
        for(i = 0; i < field.length; ++i) {
            field[i].setAccessible(true);
        }

        for(i = 0; i < method.length; ++i) {
            method[i].setAccessible(true);
        }

        FMP.put(cls.getName(), field);
        MMP.put(cls.getName(), method);
    }

    public static Field[] getFields(String ClassName) throws Exception {
        if(FMP.get(ClassName) == null) {
            parseClass(ClassName);
        }

        return (Field[])FMP.get(ClassName);
    }

    public static String[] getFieldName(String ClassName) throws Exception {
        if(FMP.get(ClassName) == null) {
            parseClass(ClassName);
        }

        Field[] field = (Field[])FMP.get(ClassName);
        String[] rs = new String[field.length];

        for(int i = 0; i < field.length; ++i) {
            rs[i] = field[i].getName();
        }

        return rs;
    }

    public static Field[] getFields(Class cls) throws Exception {
        if(FMP.get(cls.getName()) == null) {
            parseClass(cls);
        }

        return (Field[])FMP.get(cls.getName());
    }

    public static String[] getFieldName(Class cls) throws Exception {
        if(FMP.get(cls.getName()) == null) {
            parseClass(cls);
        }

        Field[] field = (Field[])FMP.get(cls.getName());
        String[] rs = new String[field.length];

        for(int i = 0; i < field.length; ++i) {
            rs[i] = field[i].getName();
        }

        return rs;
    }

    public static Method[] getMethods(String ClassName) throws Exception {
        if(MMP.get(ClassName) == null) {
            parseClass(ClassName);
        }

        return (Method[])MMP.get(ClassName);
    }

    public static String[] getMethodName(String ClassName) throws Exception {
        if(MMP.get(ClassName) == null) {
            parseClass(ClassName);
        }

        Method[] field = (Method[])MMP.get(ClassName);
        String[] rs = new String[field.length];

        for(int i = 0; i < field.length; ++i) {
            rs[i] = field[i].getName();
        }

        return rs;
    }

    public static Method[] getMethods(Class cls) throws Exception {
        if(MMP.get(cls.getName()) == null) {
            parseClass(cls);
        }

        return (Method[])MMP.get(cls.getName());
    }

    public static String[] getMethodName(Class cls) throws Exception {
        if(MMP.get(cls.getName()) == null) {
            parseClass(cls);
        }

        Method[] field = (Method[])MMP.get(cls.getName());
        String[] rs = new String[field.length];

        for(int i = 0; i < field.length; ++i) {
            rs[i] = field[i].getName();
        }

        return rs;
    }

    public static Field getField(String ClassName, String FieldName) throws Exception {
        Field[] field = getFields(ClassName);

        for(int i = 0; i < field.length; ++i) {
            if(field[i].getName().equals(FieldName)) {
                return field[i];
            }
        }

        return null;
    }

    public static Field getField(Class cls, String FieldName) throws Exception {
        Field[] field = getFields(cls);

        for(int i = 0; i < field.length; ++i) {
            if(field[i].getName().equals(FieldName)) {
                return field[i];
            }
        }

        return null;
    }

    public static Field getField(Class cls, String fieldName, boolean searchInSuper) throws Exception {
        Field beanField = getField(cls, fieldName);
        if(beanField == null && searchInSuper && cls.getSuperclass() != null) {
            beanField = getField(cls.getSuperclass(), fieldName);
        }

        return beanField;
    }

    public static Method getMethod(String ClassName, String MethodName, String[] ParTypes) throws Exception {
        Method[] method = getMethods(ClassName);
        boolean find = true;

        for(int i = 0; i < method.length; ++i) {
            if(method[i].getName().equals(MethodName)) {
                Class[] parType = method[i].getParameterTypes();
                if(parType.length == ParTypes.length) {
                    for(int j = 0; j < parType.length; ++j) {
                        if(parType[j].getName().equals(ParTypes[j])) {
                            find = true;
                        } else {
                            find = false;
                        }
                    }

                    if(find) {
                        return method[i];
                    }
                }
            }
        }

        return null;
    }

    public static Method getMethod(Class cls, String MethodName, String[] ParTypes) throws Exception {
        Method[] method = getMethods(cls);
        boolean find = true;

        for(int i = 0; i < method.length; ++i) {
            if(method[i].getName().equals(MethodName)) {
                Class[] parType = method[i].getParameterTypes();
                if(parType.length == ParTypes.length) {
                    for(int j = 0; j < parType.length; ++j) {
                        if(parType[j].getName().equals(ParTypes[j])) {
                            find = true;
                        } else {
                            find = false;
                        }
                    }

                    if(find) {
                        return method[i];
                    }
                }
            }
        }

        return null;
    }

    public static Method getMethod(String ClassName, String MethodName, Class[] ParTypes) throws Exception {
        Method[] method = getMethods(ClassName);
        boolean find = true;

        for(int i = 0; i < method.length; ++i) {
            if(method[i].getName().equals(MethodName)) {
                Class[] parType = method[i].getParameterTypes();
                if(parType.length == ParTypes.length) {
                    for(int j = 0; j < parType.length; ++j) {
                        if(parType[j].getName().equals(ParTypes[j].getName())) {
                            find = true;
                        } else {
                            find = false;
                        }
                    }

                    if(find) {
                        return method[i];
                    }
                }
            }
        }

        return null;
    }

    public static Method getMethod(Class cls, String MethodName, Class[] ParTypes) throws Exception {
        Method[] method = getMethods(cls);
        boolean find = true;

        for(int i = 0; i < method.length; ++i) {
            if(method[i].getName().equals(MethodName)) {
                Class[] parType = method[i].getParameterTypes();
                if(parType.length == ParTypes.length) {
                    for(int j = 0; j < parType.length; ++j) {
                        if(parType[j].getName().equals(ParTypes[j].getName())) {
                            find = true;
                        } else {
                            find = false;
                        }
                    }

                    if(find) {
                        return method[i];
                    }
                }
            }
        }

        return null;
    }

    public static List<Field> getFieldByAnnotation(Class targetClass, Class... annotationClass) {
        ArrayList fieldList = new ArrayList();
        Field[] fields = getField(targetClass, true);
        boolean containered = false;
        Field[] var8 = fields;
        int var7 = fields.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            Field field = var8[var6];
            Class[] var12 = annotationClass;
            int var11 = annotationClass.length;

            for(int var10 = 0; var10 < var11; ++var10) {
                Class annotationClz = var12[var10];
                if(field.getAnnotation(annotationClz) != null) {
                    containered = fieldList.add(field);
                }

                if(containered) {
                    break;
                }
            }

            containered = false;
        }

        return fieldList;
    }

    public static List<Field> getFieldByAnnotation(Class targetClass, boolean includeSupr, Class... annotationClass) {
        ArrayList fieldList = new ArrayList();
        Field[] fields = getField(targetClass, includeSupr);
        boolean containered = false;
        Field[] var9 = fields;
        int var8 = fields.length;

        for(int var7 = 0; var7 < var8; ++var7) {
            Field field = var9[var7];
            Class[] var13 = annotationClass;
            int var12 = annotationClass.length;

            for(int var11 = 0; var11 < var12; ++var11) {
                Class annotationClz = var13[var11];
                if(field.getAnnotation(annotationClz) != null) {
                    containered = fieldList.add(field);
                }

                if(containered) {
                    break;
                }
            }

            containered = false;
        }

        return fieldList;
    }
}
