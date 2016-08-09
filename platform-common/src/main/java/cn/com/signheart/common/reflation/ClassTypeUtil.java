
package cn.com.signheart.common.reflation;

import java.sql.Timestamp;
import java.util.*;

public class ClassTypeUtil {
    public static final int INTEGER = 0;
    public static final int FLOAT = 1;
    public static final int DOUBLE = 2;
    public static final int BIGDECIMAL = 3;
    public static final int BOOLEAN = 4;
    public static final int STRING = 5;
    public static final int DATE_SQL = 6;
    public static final int TIMESTAMP = 7;
    public static final int SYBASETIME = 8;
    public static final int LONG = 9;
    public static final int DATE_UTIL = 10;
    public static final int Byte = 11;
    public static final int COLLECTION = 12;
    public static final int LIST = 13;
    public static final int MAP = 14;
    public static final int SET = 15;
    public static final int INPUTSTREAM = 16;
    private static final HashMap map = new HashMap();

    static {
        map.put("java.lang.Integer", String.valueOf(0));
        map.put("java.lang.Float", String.valueOf(1));
        map.put("java.lang.Double", String.valueOf(2));
        map.put("java.math.BigDecimal", String.valueOf(3));
        map.put("java.lang.Boolean", String.valueOf(4));
        map.put("java.lang.String", String.valueOf(5));
        map.put("java.sql.Date", String.valueOf(6));
        map.put("java.util.Date", String.valueOf(10));
        map.put("java.sql.Timestamp", String.valueOf(7));
        map.put("com.sybase.jdbc2.tds.SybTimestamp", String.valueOf(8));
        map.put("java.lang.Long", String.valueOf(9));
        map.put("java.lang.Byte", String.valueOf(11));
        map.put("java.util.Collection", String.valueOf(12));
        map.put("java.util.List", String.valueOf(13));
        map.put("java.util.Map", String.valueOf(14));
        map.put("java.util.Set", String.valueOf(15));
        map.put("java.io.InputStream", String.valueOf(16));
    }

    public ClassTypeUtil() {
    }

    public static int getTypeByClass(Class _cls) {
        return getTypeByClassName(_cls.getName());
    }

    public static int getTypeByClass(String _clsName) {
        return getTypeByClassName(_clsName);
    }

    public static int getTypeByClassName(String _clsName) {
        String riv = (String)map.get(_clsName);
        return riv == null?-1:Integer.valueOf(riv).intValue();
    }

    public static boolean isNumberType(Class _cls) {
        return isNumberType(_cls.getName());
    }

    public static boolean isNumberType(String _clsName) {
        switch(getTypeByClass(_clsName)) {
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                return false;
            case 9:
                return true;
        }
    }

    private static boolean checkType(Class cls, Class objCls) {
        if(cls.getName().equals(objCls.getName())) {
            return true;
        } else {
            Class[] ifCls = objCls.getInterfaces();
            if(ifCls != null && ifCls.length > 0) {
                Class[] var6 = ifCls;
                int var5 = ifCls.length;

                for(int var4 = 0; var4 < var5; ++var4) {
                    Class icls = var6[var4];
                    if(icls.getName().equals(cls.getName())) {
                        return true;
                    }

                    if(icls.getSuperclass() != null) {
                        objCls = objCls.getSuperclass();
                        boolean tempBl = checkType(cls, objCls);
                        if(tempBl) {
                            return true;
                        }
                    }
                }
            }

            if(objCls.getSuperclass() != null) {
                objCls = objCls.getSuperclass();
                return checkType(cls, objCls);
            } else {
                return false;
            }
        }
    }

    public static boolean isList(Class cls) {
        return checkType(List.class, cls);
    }

    public static boolean isCollection(Class cls) {
        return checkType(Collection.class, cls);
    }

    public static boolean isMap(Class cls) {
        return checkType(Map.class, cls);
    }

    public static boolean isSet(Class cls) {
        return checkType(Set.class, cls);
    }

    public static boolean isString(Class cls) {
        return checkType(String.class, cls);
    }

    public static boolean isData(Class<? extends Object> cls) {
        return checkType(Date.class, cls) || checkType(java.sql.Date.class, cls) || checkType(Timestamp.class, cls);
    }
}
