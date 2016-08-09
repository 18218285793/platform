package cn.com.signheart.common.jdbc;

import cn.com.signheart.common.jdbc.util.DBUtils;
import cn.com.signheart.common.povo.IBasePO;
import cn.com.signheart.common.reflation.ClassTypeUtil;
import cn.com.signheart.common.reflation.ClassUtil;
import cn.com.signheart.common.reflation.ConvertUtil;
import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ao.ouyang on 15-11-2.
 */
public class SupportDAC implements IBaseDAC {
    private static final transient Logger logger = LoggerFactory.getLogger(SupportDAC.class);
    public static  ThreadLocal<Connection> connLocal = new ThreadLocal<>();
    private ThreadLocal<PreparedStatement> pstmLocal = new ThreadLocal();
    private ThreadLocal<Integer> pstmCount = new ThreadLocal();
    private ThreadLocal<Statement> stamLocal = new ThreadLocal();
    private ThreadLocal<Integer> stamCount = new ThreadLocal();
    private ThreadLocal<Statement> onePreStamLocal = new ThreadLocal<>();
    private ThreadLocal<Map<String, List<Integer>>> nameLocal = new ThreadLocal();
    private final Pattern namedPattern = Pattern.compile("(\\B:(\\w+)\\b)");
    private DataSource dataSource;
    @Resource
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void addBatch(String _batchSQL) throws SQLException {
        this.getStatement(false).addBatch(_batchSQL);
    }

    public void addBatchPreparedStmt() throws SQLException {
        this.getPreparedStatement(false).addBatch();
    }

    public void clearBatch() throws SQLException {
        this.getStatement(false).clearBatch();
    }

    public void clearBatchPreparedStmt() throws SQLException {
        this.getPreparedStatement(false).clearBatch();
    }

    public int[] execBatch() throws SQLException {
        int[] n;
        try {
            n = this.getStatement(true).executeBatch();
        } finally {
            this.closeAllStmt(null);
        }

        return n;
    }

    public int[] execBatchPreparedStmt() throws SQLException {
        int[] n;
        try {
            n = this.getPreparedStatement(true).executeBatch();
        } finally {
            this.closeAll(null);
        }

        return n;
    }

    @Override
    public void preparedSql(String _sql) throws SQLException {
        this.testConnection();
        logger.debug("prepared的查询语句是：" + _sql);
        String sql = this.parseNamedParames(_sql);
        if(this.pstmLocal.get() != null) {
            ((PreparedStatement)this.pstmLocal.get()).close();
            this.pstmLocal.remove();
        }

        if("select".equals(_sql.substring(0, 6).toLowerCase())) {
            this.setPreparedStatement(this.getConnection().prepareStatement(sql, 1003, 1007));
        } else {
            this.setPreparedStatement(this.getConnection().prepareStatement(sql));
        }

    }

    @Override
    public void executeSql(String var1) throws SQLException {
        this.testConnection();
        DBUtils.executeSql(this.getConnection(), var1);
    }

    @Override
    public <T> T executeQuery(Class<T> var1) throws SQLException {
        this.testConnection();
        this.testPrepared();
        ResultSet rs = null;

        Object obj;
        try {
            rs = this.getPreparedStatement(true).executeQuery();
            obj = null;

            try {
                obj = DBUtils.getObjFromResult(rs, var1);
            } catch (Exception var8) {
                logger.error(var8.getMessage());
                throw new SQLException(var8.getMessage(), var8);
            }
        } finally {
            closeAll(rs);
        }

        return (T) obj;
    }

    @Override
    public Map executeQuery(String var1) throws SQLException {
        this.testConnection();
        this.testPrepared();
        ResultSet rs = null;
        Map map = null;

        try {
            rs = getResultSet(getConnection(),var1);
            map = DBUtils.getMapFromResult(rs);
        } finally {
            closeAll(rs);
        }

        return map;
    }

    @Override
    public <T> T executeQuery(String var1, Class<T> var2) throws SQLException {
        this.testConnection();

        try {
            ResultSet rs = null;
            Object obj;
            try {
                rs = getResultSet(getConnection(), var1);
                obj = DBUtils.getObjFromResult(rs, var2);
            } finally {
                DBUtils.close(connLocal.get(), rs, onePreStamLocal.get());
            }
            return (T) obj;
        } catch (Exception var4) {
            logger.error(var4.getMessage());
            throw new SQLException(var4.getMessage());
        }
    }


    @Override
    public <T> List<T> getList(String var1, Class<T> var2) throws SQLException {
        this.testConnection();

        try {
            ResultSet rs = null;

            List list;
            try {
                rs = getResultSet(getConnection(), var1);
                list = DBUtils.getListFromResult(rs, var2);
            } finally {
                DBUtils.close(connLocal.get(),rs,onePreStamLocal.get());
            }

            return list;
        } catch (Exception var4) {
            logger.error(var4.getMessage());
            throw new SQLException(var4.getMessage());
        }
    }

    @Override
    public int executeUpdate() throws SQLException {
        this.testConnection();
        this.testPrepared();

        int var1;
        try {
            var1 = this.getPreparedStatement(true).executeUpdate();
        } finally {
            closeAll(null);
        }

        return var1;
    }

    @Override
    public Map executeQuery() throws SQLException {
        this.testConnection();
        this.testPrepared();
        ResultSet rs = null;
        Map map = null;

        try {
            rs = this.getPreparedStatement(true).executeQuery();
            map = DBUtils.getMapFromResult(rs);
        } finally {
            closeAll(rs);
        }

        return map;
    }

    @Override
    public <T> List<T> getList(Class<T> var1) throws SQLException {
        this.testConnection();
        this.testPrepared();
        ResultSet rs = null;
        List list = null;

        try {
            rs = this.getPreparedStatement(true).executeQuery();
            list = DBUtils.getListFromResult(rs, var1);
        } catch (Exception var8) {
            logger.error(var8.getMessage(), var8);
            throw new SQLException(var8.getMessage(), var8);
        } finally {
            closeAll(rs);
        }

        return list;
    }

    @Override
    public List getList() throws SQLException {
        this.testConnection();
        this.testPrepared();
        ResultSet rs = null;

        List list;
        try {
            rs = this.getPreparedStatement(true).executeQuery();
            list = DBUtils.getMapListFromResult(rs);
        } finally {
            closeAll(rs);
        }

        return list;
    }

    public void setPreValues(Object vo, boolean igronNull) throws SQLException {
        if(vo instanceof Map) {
            Iterator fields = ((Map)vo).entrySet().iterator();

            while(true) {
                Map.Entry arr$;
                do {
                    if(!fields.hasNext()) {
                        return;
                    }

                    arr$ = (Map.Entry)fields.next();
                } while(igronNull && arr$.getValue() == null);

                this.setPreValue((String)arr$.getKey(), arr$.getValue());
            }
        } else {
            Field[] var11 = ClassUtil.getField(vo.getClass(), true);
            Field[] var12 = var11;
            int len$ = var11.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field field = var12[i$];

                Object value;
                try {
                    value = field.get(vo);
                } catch (IllegalAccessException var10) {
                    throw new SQLException(var10);
                }

                if(!igronNull || value != null) {
                    this.setPreValue(field.getName(), value);
                }
            }

        }
    }

    public void setPreValue(int _index, Object _value) throws SQLException {
        this.testPrepared();
        if(null == _value) {
            this.getPreparedStatement(false).setNull(_index, 12);
        } else if(_value instanceof String) {
            String value = (String)_value;
            if(500 < value.length()) {
                StringReader reader = new StringReader(value);
                this.getPreparedStatement(false).setCharacterStream(_index, reader, value.length());
            } else {
                this.getPreparedStatement(false).setObject(_index, _value);
            }
        } else {
            this.getPreparedStatement(false).setObject(_index, _value);
        }

    }

    public SupportDAC setPreValue(String name, Object value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            if(value != null) {
                switch(ClassTypeUtil.getTypeByClass(value.getClass())) {
                    case 0:
                        this.setPreValue(e.intValue(), (Integer)value);
                        break;
                    case 1:
                        this.setPreValue(e.intValue(), (Object)((Float)value));
                        break;
                    case 2:
                        this.setPreValue(e.intValue(), (Double)value);
                        break;
                    case 3:
                        this.setPreValue(e.intValue(), (BigDecimal)value);
                        break;
                    case 4:
                        this.setPreValue(e.intValue(), (Integer)value);
                        break;
                    case 5:
                    case 8:
                    default:
                        this.setPreValue(e.intValue(), value);
                        break;
                    case 6:
                        this.setPreValue(e.intValue(), new java.util.Date(((java.sql.Date)value).getTime()));
                        break;
                    case 7:
                        this.setPreValue(e.intValue(), new java.util.Date(((Timestamp)value).getTime()));
                        break;
                    case 9:
                        this.setPreValue(e.intValue(), (Long)value);
                        break;
                    case 10:
                        this.setPreValue(e.intValue(), (java.util.Date)value);
                }
            } else {
                this.setPreNullValue(e.intValue());
            }
        }

        return this;
    }

    public void setPreValue(int _index, Object _value, int _type) throws SQLException {
        this.testPrepared();
        if(_value == null) {
            this.getPreparedStatement(false).setNull(_index, _type);
        } else if(_value instanceof String && 500 < _value.toString().length()) {
            StringReader reader = new StringReader(_value.toString());
            this.getPreparedStatement(false).setCharacterStream(_index, reader, _value.toString().length());
        } else {
            this.getPreparedStatement(false).setObject(_index, _value, _type);
        }

    }

    public SupportDAC setPreValue(String name, Object value, int type) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value, type);
        }

        return this;
    }

    public SupportDAC setPreValue(int _index, java.util.Date _value) throws SQLException {
        this.testPrepared();
        if(_value == null) {
            this.getPreparedStatement(false).setNull(_index, 91);
        } else {
            this.getPreparedStatement(false).setTimestamp(_index, new Timestamp(_value.getTime()));
        }

        return this;
    }

    public SupportDAC setPreValue(String name, java.util.Date value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public SupportDAC setPreValue(int _index, Integer _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public SupportDAC setPreValue(String name, Integer value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public SupportDAC setPreValue(int _index, Long _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public SupportDAC setPreValue(String name, Long value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public SupportDAC setPreValue(int _index, Double _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public SupportDAC setPreValue(String name, Double value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public SupportDAC setPreValue(int _index, BigDecimal _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public SupportDAC setPreValue(String name, BigDecimal value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public SupportDAC setPreValue(int _index, String _value) throws SQLException {
        this.setPreValue(_index, _value, 12);
        return this;
    }

    public SupportDAC setPreValue(String name, String value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public SupportDAC setPreNullValue(int _index) throws SQLException {
        this.testPrepared();
        this.getPreparedStatement(false).setNull(_index, 12);
        return this;
    }

    public SupportDAC setPreNullValue(String name) throws SQLException {
        List list = this.getNamedIndex(name);
        this.testPrepared();
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.getPreparedStatement(false).setNull(e.intValue(), 12);
        }

        return this;
    }

    public void setPreValues(Map<String, Object> params, boolean isIgnoreNoExistedException) throws SQLException {
        if(isIgnoreNoExistedException) {
            if(!AssertUtil.isEmpty(params)) {
                Map map = this.getNamedParams();
                HashMap newParams = new HashMap(params.size());
                Iterator i$ = params.entrySet().iterator();

                while(i$.hasNext()) {
                    Map.Entry entry = (Map.Entry)i$.next();
                    if(map.containsKey(entry.getKey())) {
                        newParams.put(entry.getKey(), entry.getValue());
                    }
                }

                this.setPreValues(newParams);
            }
        } else {
            this.setPreValues(params);
        }

    }

    public void setPreValues(Map<String, Object> params) throws SQLException {
        if(!AssertUtil.isEmpty(params)) {
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                this.setPreValue((String) entry.getKey(), entry.getValue());
            }
        }

    }

    public <T extends IBasePO> void insertData(T _dbvo) throws SQLException {
        String sql = SQLBuilder.buildInsertSQL(_dbvo);
        this.executeUpdate(_dbvo, sql, true);
    }

    public <T extends IBasePO> int executeUpdate(T _dbvo, String _sql, boolean updateAll) throws SQLException {
        this.preparedSql(_sql);

        try {
            Field[] e = ClassUtil.getFields(_dbvo.getClass());
            Field[] arr$ = e;
            int len$ = e.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field column = arr$[i$];
                if(!SQLBuilder.isSerialField(column.getName())) {
                    Object value = column.get(_dbvo);
                    if(value != null || updateAll) {
                        this.setPreValue(column.getName(), value);
                    }
                }
            }
        } catch (Exception var10) {
            logger.error(var10.getMessage());
            throw new SQLException(var10.getMessage());
        }

        return this.executeUpdate();
    }

    @Override
    public <T extends IBasePO> void updateData(T var1, boolean var2) throws SQLException {

    }

    public <T> T executeScalar(Class<T> type) throws SQLException {
        this.testPrepared();
        this.getPreparedStatement(false).setMaxRows(1);
        ResultSet rs = null;

        try {
            rs = this.getPreparedStatement(true).executeQuery();
            if(null == rs || !rs.next()) {
                Object colType1 = null;
                return (T) colType1;
            } else {
                int colType = rs.getMetaData().getColumnType(1);
                String ts3;
                switch(ClassTypeUtil.getTypeByClass(type)) {
                    case 0:
                        Integer ts6 = Integer.valueOf(rs.getInt(1));
                        return(T)  ts6;
                    case 1:
                        Float ts7 = Float.valueOf(rs.getFloat(1));
                        return(T)  ts7;
                    case 2:
                        Double ts4 = Double.valueOf(rs.getDouble(1));
                        return(T)  ts4;
                    case 3:
                    case 6:
                    case 7:
                    case 8:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    default:
                        ts3 = rs.getString(1);
                        return(T)  ts3;
                    case 4:
                        Boolean ts5 = Boolean.valueOf(rs.getBoolean(1));
                        return(T)  ts5;
                    case 5:
                        switch(colType) {
                            case 2005:
                                try {
                                    ts3 = ConvertUtil.cvStream2String(rs.getAsciiStream(1));
                                    return (T) ts3;
                                } catch (IOException var9) {
                                    throw new SQLException("从CLOB转换成String的时候，发生异常。");
                                }
                            default:
                                ts3 = rs.getString(1);
                                return(T)  ts3;
                        }
                    case 9:
                        Long ts1 = Long.valueOf(rs.getLong(1));
                        return(T)  ts1;
                    case 10:
                        Timestamp ts2 = rs.getTimestamp(1);
                        java.util.Date var5;
                        if(null == ts2) {
                            var5 = null;
                            return (T) var5;
                        }

                        var5 = new java.util.Date(ts2.getTime());
                        return (T) var5;
                    case 16:
                        InputStream ts;
                        switch(colType) {
                            case -4:
                            case -2:
                            case 2004:
                                ts = rs.getBinaryStream(1);
                                return(T)  ts;
                            case 2005:
                                ts = rs.getAsciiStream(1);
                                return (T) ts;
                            default:
                                ts = rs.getAsciiStream(1);
                                return (T) ts;
                        }
                }
            }
        } finally {
            closeAll(rs);
        }
    }

    private Statement getStatement(boolean withCount) throws SQLException {
        if(this.stamCount.get() == null) {
            this.stamCount.set(Integer.valueOf(0));
        }

        if(this.stamLocal.get() == null || ((Statement)this.stamLocal.get()).isClosed() || ((Statement)this.stamLocal.get()).getConnection() == null) {
            this.setStatement(this.getConnection().createStatement());
        }

        if(withCount) {
            this.stamCount.set(Integer.valueOf(((Integer)this.stamCount.get()).intValue() + 1));
            logger.debug(String.format("获取后线程%s的stam连接数为%d", new Object[]{String.valueOf(Thread.currentThread().getId()), this.stamCount.get()}));
        }

        return (Statement)this.stamLocal.get();
    }
    private void setStatement(Statement pstm) {
        this.stamLocal.set(pstm);
    }

    private Map<String, List<Integer>> getNamedParams() {
        Object map = (Map)this.nameLocal.get();
        if(map == null) {
            map = new ConcurrentHashMap();
            this.nameLocal.set((Map<String, List<Integer>>) map);
        }

        return (Map)map;
    }

    private List<Integer> getNamedIndex(String name) throws SQLException {
        List list = (List)this.getNamedParams().get(name);
        if(null != list && !list.isEmpty()) {
            return list;
        } else {
            throw new SQLException("不存在指定的别名参数：" + name);
        }
    }
    private void testPrepared() throws SQLException {
        if(this.getPreparedStatement(false) == null) {
            throw new SQLException("预处理语句还未准备好");
        }
    }
    private void testConnection() throws SQLException {
        if(null == this.connLocal.get()  || this.connLocal.get().isClosed()) {
            logger.debug("检测到当前连接未初始化或已关闭，尝试获取连接。");
            getConnection();
        }
    }
    public Connection getConnection() {
        try {
            connLocal.set(null != connLocal.get() && connLocal.get().isClosed() == false ? connLocal.get() : DataSourceUtils.getConnection(this.dataSource));
        } catch (SQLException e) {
            logger.debug("获取当前线程连接失败！将从数据池中获取。");
        } finally {
            connLocal.set(DataSourceUtils.getConnection(this.dataSource));
        }
        logger.debug("获取数据库连接成功。");
        return  connLocal.get();
    }
    private void setPreparedStatement(PreparedStatement pstm) {
        this.pstmLocal.set(pstm);
    }

    private PreparedStatement getPreparedStatement(boolean withCount) throws SQLException {
        if(this.pstmCount.get() == null) {
            this.pstmCount.set(Integer.valueOf(0));
        }

        if(withCount) {
            this.pstmCount.set(Integer.valueOf(((Integer)this.pstmCount.get()).intValue() + 1));
            logger.debug(String.format("获取后线程%s的prepared连接数为%d", new Object[]{String.valueOf(Thread.currentThread().getId()), this.pstmCount.get()}));
        }

        return (PreparedStatement)this.pstmLocal.get();
    }

    private String parseNamedParames(String _sql) throws SQLException {
        if(StringUtil.isNullEmpty(_sql)) {
            throw new SQLException("要执行的SQL语句为空！");
        } else {
            Matcher m = this.namedPattern.matcher(_sql);
            this.getNamedParams().clear();
            int idx = 1;

            while(m.find()) {
                this.addNamedParame(m.group(2), idx++);
            }

            return _sql.replaceAll("(\\B:(\\w+)\\b)", "?");
        }
    }
    private void addNamedParame(String key, int value) {
        Object values = (List)this.getNamedParams().get(key);
        if(null == values) {
            values = new ArrayList();
            this.getNamedParams().put(key, (List<Integer>) values);
        }

        ((List)values).add(Integer.valueOf(value));
    }

    private  void closeAll(ResultSet rs) throws SQLException {
        this.pstmCount.set(Integer.valueOf(((Integer)this.pstmCount.get()).intValue() - 1));
        Integer currCount = this.pstmCount.get();
        logger.debug(String.format("释放后线程%s的prepared连接数为%d", new Object[]{String.valueOf(Thread.currentThread().getId()), currCount}));
        if(currCount.intValue() == 0) {
            DBUtils.close(connLocal.get(), rs, this.pstmLocal.get());
            this.pstmLocal.remove();
            this.connLocal.remove();
            logger.debug(String.format("释放后线程%s的prepared连接数为0,已将其连接关闭", new Object[]{String.valueOf(Thread.currentThread().getId())}));
        } else {
            logger.info(String.format("释放后线程%s的prepared连接数为%d,仍然有人持有连接，暂不释放", new Object[]{String.valueOf(Thread.currentThread().getId()), currCount}));
        }

    }
    private  void closeAllStmt(ResultSet rs) throws SQLException {
        this.stamCount.set(Integer.valueOf(((Integer)this.stamCount.get()).intValue() - 1));
        Integer currCount = (Integer)this.stamCount.get();
        logger.debug(String.format("释放后线程%s的stam连接数为%d", new Object[]{String.valueOf(Thread.currentThread().getId()), currCount}));
        if(currCount.intValue() == 0) {
            DBUtils.close(connLocal.get(), rs, this.stamLocal.get());
            this.stamLocal.remove();
            this.connLocal.remove();
            logger.debug(String.format("释放后线程%s的stam连接数为0,已将其连接关闭", new Object[]{String.valueOf(Thread.currentThread().getId())}));
        } else {
            logger.info(String.format("释放后线程%s的stam连接数为%d,仍然有人持有连接，暂不释放", new Object[]{String.valueOf(Thread.currentThread().getId()), currCount}));
        }

    }
    private ResultSet getResultSet(Connection _conn, String _sql) throws SQLException {
        ResultSet rs = null;
        if(_sql != null && _sql.length() > 0) {
            if(null == onePreStamLocal.get()){
                onePreStamLocal.set(_conn.prepareStatement(_sql, 1003, 1007));
            }
            Statement pstm = onePreStamLocal.get();
            rs = pstm.executeQuery(_sql);
        }
        return rs;
    }
}
