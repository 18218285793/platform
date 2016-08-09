package cn.com.signheart.common.jdbc;

import cn.com.signheart.common.povo.IBasePO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBaseDAC {

    void addBatch(String var1) throws SQLException;

    void addBatchPreparedStmt() throws SQLException;

    int[] execBatch() throws SQLException;

    int[] execBatchPreparedStmt() throws SQLException;

    void clearBatch() throws SQLException;

    void clearBatchPreparedStmt() throws SQLException;

    void preparedSql(String var1) throws SQLException;

    void executeSql(String var1) throws SQLException;

    <T> T executeQuery(Class<T> var1) throws SQLException;

    Map executeQuery(String var1) throws SQLException;

    <T> T executeQuery(String var1, Class<T> var2) throws SQLException;

    <T> List<T> getList(String var1, Class<T> var2) throws SQLException;

    int executeUpdate() throws SQLException;

    Map executeQuery() throws SQLException;

    <T> List<T> getList(Class<T> var1) throws SQLException;

    List getList() throws SQLException;

    void setPreValue(int var1, Object var2) throws SQLException;

    IBaseDAC setPreValue(String var1, Object var2) throws SQLException;

    void setPreValue(int var1, Object var2, int var3) throws SQLException;

    IBaseDAC setPreValue(String var1, Object var2, int var3) throws SQLException;

    IBaseDAC setPreValue(int var1, Date var2) throws SQLException;

    IBaseDAC setPreValue(String var1, Date var2) throws SQLException;

    IBaseDAC setPreValue(int var1, Integer var2) throws SQLException;

    IBaseDAC setPreValue(String var1, Integer var2) throws SQLException;

    IBaseDAC setPreValue(int var1, Long var2) throws SQLException;

    IBaseDAC setPreValue(String var1, Long var2) throws SQLException;

    IBaseDAC setPreValue(int var1, Double var2) throws SQLException;

    IBaseDAC setPreValue(String var1, Double var2) throws SQLException;

    IBaseDAC setPreValue(int var1, BigDecimal var2) throws SQLException;

    IBaseDAC setPreValue(String var1, BigDecimal var2) throws SQLException;

    IBaseDAC setPreValue(int var1, String var2) throws SQLException;

    IBaseDAC setPreValue(String var1, String var2) throws SQLException;

    IBaseDAC setPreNullValue(int var1) throws SQLException;

    IBaseDAC setPreNullValue(String var1) throws SQLException;

    void setPreValues(Map<String, Object> var1, boolean var2) throws SQLException;

    void setPreValues(Map<String, Object> var1) throws SQLException;



    <T extends IBasePO> void insertData(T var1) throws SQLException;

    <T extends IBasePO> void updateData(T var1, boolean var2) throws SQLException;

}
