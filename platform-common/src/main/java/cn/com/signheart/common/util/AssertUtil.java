
package cn.com.signheart.common.util;


import cn.com.signheart.common.exception.DefaultException;
import cn.com.signheart.common.reflation.ClassType;
import cn.com.signheart.common.reflation.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

public class AssertUtil {
    public AssertUtil() {
    }

    public static <T> boolean isEmpty(T[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null?true:(obj instanceof String?"".equals((String)obj):(obj instanceof Number?false:false));
    }

    public static boolean isEmpty(Collection obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Map obj) {
        return obj == null || obj.isEmpty();
    }

    public static synchronized boolean objCobj(Object obj_1, Object obj_2) throws Exception {
        int n = 0;
        boolean flag = false;
        if(obj_1 == null) {
            return obj_2 == null;
        } else if(obj_2 == null) {
            return false;
        } else {
            Class obj1 = obj_1.getClass();
            Class obj2 = obj_2.getClass();
            if(obj1.equals(ClassType.stringType) && obj2.equals(ClassType.stringType)) {
                return obj_1.equals(obj_2);
            } else {
                Field[] obj1Fields = ClassUtil.getFields(obj1);
                Field[] obj2Fields = ClassUtil.getFields(obj2);
                if(obj1Fields.length != obj2Fields.length) {
                    return false;
                } else if(obj1Fields.length == 0 && obj1.equals(obj2)) {
                    return true;
                } else {
                    for(int i = 0; i < obj1Fields.length; ++i) {
                        Class obj1Type = obj1Fields[i].getType();
                        String obj1FieldName = obj1Fields[i].getName();

                        for(int j = 0; j < obj2Fields.length; ++j) {
                            Class obj2Type = obj2Fields[j].getType();
                            if(obj2Fields[j].getName().equals(obj1FieldName)) {
                                if(!obj1Type.equals(obj2Type)) {
                                    return false;
                                }

                                ++n;
                                Object tempobj2 = obj2Fields[j].get(obj_2);
                                Object tempobj1 = obj1Fields[i].get(obj_1);
                                if(!isEmpty(tempobj1)) {
                                    if(isEmpty(tempobj2)) {
                                        return false;
                                    }

                                    if(obj1Type.equals(ClassType.dateType)) {
                                        flag = tempobj1.toString().substring(0, 10).equals(tempobj2.toString().substring(0, 10));
                                    } else if(obj1Type.equals(ClassType.sqlTimeType)) {
                                        flag = tempobj1.toString().substring(0, 19).equals(tempobj2.toString().substring(0, 19));
                                    } else {
                                        flag = tempobj1.equals(tempobj2);
                                    }

                                    if(!flag) {
                                        return false;
                                    }
                                } else {
                                    if(!isEmpty(tempobj2)) {
                                        return false;
                                    }

                                    flag = true;
                                }
                            }
                        }
                    }

                    if(n != obj1Fields.length) {
                        return false;
                    } else {
                        return flag;
                    }
                }
            }
        }
    }

    public static boolean charIsNumb(int charValue) {
        return charValue >= 48 && charValue <= 57 || charValue >= 96 && charValue <= 105;
    }

    public static boolean objIsEmpty(Object obj) throws Exception {
        if(obj == null) {
            return true;
        } else {
            Field[] objFields = ClassUtil.getFields(obj.getClass());
            Field[] var5 = objFields;
            int var4 = objFields.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                Field objField = var5[var3];
                if(!isEmpty(objField.get(obj))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean objIsEmptyEx(Object obj) {
        if(obj == null) {
            return true;
        } else {
            Field[] objFields = (Field[])null;

            try {
                objFields = ClassUtil.getFields(obj.getClass());
            } catch (Exception var7) {
                return true;
            }

            if(isEmpty((Object[])objFields)) {
                return true;
            } else {
                Field[] var5 = objFields;
                int var4 = objFields.length;

                for(int var3 = 0; var3 < var4; ++var3) {
                    Field objField = var5[var3];
                    if(!Modifier.isFinal(objField.getModifiers())) {
                        try {
                            if(!isEmpty(objField.get(obj))) {
                                return false;
                            }
                        } catch (IllegalAccessException var6) {
                            return true;
                        }
                    }
                }

                return true;
            }
        }
    }

    public static boolean isFloat(String s) {
        return s.indexOf(".") > -1;
    }

    public static boolean shouldMor(String s) throws Exception {
        String[] temp = StringUtil.splitString(s, ".");
        switch(temp[1].charAt(0)) {
            case '0':
                return false;
            case '1':
                return false;
            case '2':
                return false;
            case '3':
                return false;
            case '4':
                return false;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
            default:
                throw new Exception("错误，小数点后非数字");
        }
    }

    public static boolean isNumb(String str) {
        if(isEmpty((Object)str)) {
            return false;
        } else {
            char[] chr = str.toCharArray();

            for(int i = 0; i < chr.length; ++i) {
                if(chr[i] < 48 || chr[i] > 57) {
                    return false;
                }
            }

            return true;
        }
    }

    public static void hasLength(String str, String msg) throws DefaultException {
        if(str == null || str.length() < 1) {
            throw new DefaultException(msg);
        }
    }
}
