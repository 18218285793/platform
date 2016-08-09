package cn.com.signheart.common.jdbc;


import cn.com.signheart.common.annotation.Sequence;
import cn.com.signheart.common.povo.IBasePO;
import cn.com.signheart.common.reflation.BeanHelper;
import cn.com.signheart.common.reflation.ClassUtil;
import cn.com.signheart.common.util.AssertUtil;

import java.lang.reflect.Field;
import java.sql.SQLException;

/**
 * Created by ao.ouyang on 15-11-12.
 */
public class SQLBuilder {

    public static String buildInsertSQL(IBasePO _dbvo) throws SQLException {
        StringBuilder sbFields = new StringBuilder();
        StringBuilder sbValues = new StringBuilder();

        Field[] columns;
        try {
            columns = ClassUtil.getFields(_dbvo.getClass());
        } catch (Exception var7) {
            throw new SQLException(var7.getMessage());
        }

        if(!AssertUtil.isEmpty(columns)) {
            for(int sb = 0; sb < columns.length; ++sb) {
                if(!isSerialField(columns[sb].getName()) && !columns[sb].isAnnotationPresent(Sequence.class)) {
                    try {
                        sbFields.append(BeanHelper.getOracleFieldName(columns[sb]));
                    } catch (Exception var6) {
                        throw new SQLException(var6.getMessage());
                    }

                    if(sb != columns.length - 1) {
                        sbFields.append(",");
                    }

                    sbValues.append(":").append(columns[sb].getName());
                    if(sb != columns.length - 1) {
                        sbValues.append(",");
                    }
                }
            }
        }

        StringBuilder var8 = new StringBuilder("insert into ");
        var8.append(_dbvo._getTableName()).append("(");
        var8.append(sbFields).append(") values(").append(sbValues).append(")");
        return var8.toString();
    }

    public static boolean isSerialField(String fieldName) {
        return "serialVersionUID".equalsIgnoreCase(fieldName);
    }
}
