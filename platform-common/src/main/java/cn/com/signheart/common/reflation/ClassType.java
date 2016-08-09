
package cn.com.signheart.common.reflation;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ClassType {
    public static final Class stringType = String.class;
    public static final Class dateType = Date.class;
    public static final Class booleanType;
    public static final Class ObjectType;
    public static final Class sqlTimeType;
    public static final Class bigDecimalType;
    public static final Class integerType;
    private static ClassType me;

    private ClassType() {
    }

    public static ClassType getInstance() {
        if(me == null) {
            me = new ClassType();
        }

        return me;
    }

    static {
        booleanType = Boolean.TYPE;
        ObjectType = Object.class;
        sqlTimeType = Timestamp.class;
        bigDecimalType = BigDecimal.class;
        integerType = Integer.class;
    }
}
