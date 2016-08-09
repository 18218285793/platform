package cn.com.signheart.common.jdbc;

import cn.com.signheart.common.jdbc.util.DBUtils;
import cn.com.signheart.common.povo.IBasePO;
import cn.com.signheart.common.reflation.ClassTypeUtil;
import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ao.ouyang on 15-11-5.
 */
public class QuickDAC implements IBaseDAC {
    private static final transient Log log = LogFactory.getLog(QuickDAC.class);
    private static ThreadLocal<Connection> connThread = new ThreadLocal();
    private static final Pattern namedPattern = Pattern.compile("(\\B:(\\w+)\\b)");
    private final Map<String, List<Integer>> namedParams = new HashMap();

    private ResultSet rs;

    private PreparedStatement pstm;
    private Statement stam;


    public QuickDAC(Connection _conn) throws SQLException {
        Connection conn = getConnection();
        if(conn == null || conn.isClosed()) {
            connThread.set(_conn);
        }

    }

    public static Connection getConnection() {
        return (Connection)connThread.get();
    }


    @Override
    public void addBatch(String var1) throws SQLException {
        if(this.stam == null) {
            this.stam = getConnection().createStatement(1005, 1008);
        }

        this.stam.addBatch(var1);
    }

    @Override
    public void addBatchPreparedStmt() throws SQLException {

    }

    @Override
    public int[] execBatch() throws SQLException {
        int[] n = this.stam.executeBatch();
        DBUtils.close(getConnection(),null,stam);
        return n;
    }

    @Override
    public int[] execBatchPreparedStmt() throws SQLException {
        return new int[0];
    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public void clearBatchPreparedStmt() throws SQLException {

    }
    private String parseNamedParames(String _sql) throws SQLException {
        if(StringUtil.isNullEmpty(_sql)) {
            throw new SQLException("要执行的SQL语句为空！");
        } else {
            Matcher m = namedPattern.matcher(_sql);
            this.namedParams.clear();
            int idx = 1;

            while(m.find()) {
                this.addNamedParame(m.group(2), idx++);
            }

            return _sql.replaceAll("(\\B:(\\w+)\\b)", "?");
        }
    }
    private void addNamedParame(String key, int value) {
        Object values = (List)this.namedParams.get(key);
        if(null == values) {
            values = new ArrayList();
            this.namedParams.put(key, (List<Integer>) values);
        }

        ((List)values).add(Integer.valueOf(value));
    }
    @Override
    public void preparedSql(String var1) throws SQLException {
        testConnection();
        String sql = this.parseNamedParames(var1);
        this.pstm = getConnection().prepareStatement(sql);

    }

    @Override
    public void executeSql(String var1) throws SQLException {

    }

    @Override
    public <T> T executeQuery(Class<T> var1) throws SQLException {
        return null;
    }

    @Override
    public Map executeQuery(String var1) throws SQLException {
        try {
            Map map;
            testConnection();
            this.testPrepared();
            rs = this.pstm.executeQuery();
             map = DBUtils.getMapFromResult(rs);
            return map;
        }finally {
            DBUtils.close(getConnection(),pstm,rs);
        }

    }

    @Override
    public <T> T executeQuery(String var1, Class<T> var2) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> getList(String var1, Class<T> var2) throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate() throws SQLException {
        try {
            testConnection();
            this.testPrepared();
            int i = this.pstm.executeUpdate();
            return i;
        }finally {
            DBUtils.close(getConnection(), pstm, rs);
        }
    }

    @Override
    public Map executeQuery() throws SQLException {
        try {
            testConnection();
            this.testPrepared();
            ResultSet rs = this.pstm.executeQuery();
            Map map = DBUtils.getMapFromResult(rs);
            return map;
        }finally {
            DBUtils.close(getConnection(),pstm,rs);
        }
    }

    @Override
    public <T> List<T> getList(Class<T> var1) throws SQLException {
        try {
            testConnection();
            this.testPrepared();
             rs = this.pstm.executeQuery();

            List list;
            try {
                list = DBUtils.getListFromResult(rs, var1);
            } catch (Exception var5) {
                log.error(var5.getMessage());
                throw new SQLException(var5.getMessage());
            }
            return list;
        }finally {
            DBUtils.close(getConnection(), pstm, rs);
        }
    }

    @Override
    public List getList() throws SQLException {
        try {
            testConnection();
            this.testPrepared();
            rs = this.pstm.executeQuery();
            List list = DBUtils.getMapListFromResult(rs);
            return  list;
        }finally {
            DBUtils.close(getConnection(),pstm,rs);
        }
    }

    public void setPreValue(int _index, Object _value) throws SQLException {
        this.testPrepared();
        if(null == _value) {
            this.pstm.setNull(_index, 12);
        } else if(_value instanceof String) {
            String value = (String)_value;
            if(500 < value.length()) {
                StringReader reader = new StringReader(value);
                this.pstm.setCharacterStream(_index, reader, value.length());
            } else {
                this.pstm.setObject(_index, _value);
            }
        } else {
            this.pstm.setObject(_index, _value);
        }

    }

    public QuickDAC setPreValue(String name, Object value) throws SQLException {
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
                        this.setPreValue(e.intValue(), (Integer)value);
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
            this.pstm.setNull(_index, _type);
        } else if(_value instanceof String && 500 < _value.toString().length()) {
            StringReader reader = new StringReader(_value.toString());
            this.pstm.setCharacterStream(_index, reader, _value.toString().length());
        } else {
            this.pstm.setObject(_index, _value, _type);
        }

    }

    public QuickDAC setPreValue(String name, Object value, int type) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value, type);
        }

        return this;
    }

    public QuickDAC setPreValue(int _index, java.util.Date _value) throws SQLException {
        this.testPrepared();
        if(_value == null) {
            this.pstm.setNull(_index, 91);
        } else {
            this.pstm.setTimestamp(_index, new Timestamp(_value.getTime()));
        }

        return this;
    }

    public QuickDAC setPreValue(String name, java.util.Date value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public QuickDAC setPreValue(int _index, Integer _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public QuickDAC setPreValue(String name, Integer value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public QuickDAC setPreValue(int _index, Long _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public QuickDAC setPreValue(String name, Long value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public QuickDAC setPreValue(int _index, Double _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public QuickDAC setPreValue(String name, Double value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public QuickDAC setPreValue(int _index, BigDecimal _value) throws SQLException {
        this.setPreValue(_index, _value, 2);
        return this;
    }

    public QuickDAC setPreValue(String name, BigDecimal value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public QuickDAC setPreValue(int _index, String _value) throws SQLException {
        this.setPreValue(_index, _value, 12);
        return this;
    }

    public QuickDAC setPreValue(String name, String value) throws SQLException {
        List list = this.getNamedIndex(name);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.setPreValue(e.intValue(), value);
        }

        return this;
    }

    public QuickDAC setPreNullValue(int _index) throws SQLException {
        this.testPrepared();
        this.pstm.setNull(_index, 12);
        return this;
    }

    public QuickDAC setPreNullValue(String name) throws SQLException {
        List list = this.getNamedIndex(name);
        this.testPrepared();
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Integer e = (Integer)i$.next();
            this.pstm.setNull(e.intValue(), 12);
        }

        return this;
    }

    public void setPreValues(Map<String, Object> params, boolean isIgnoreNoExistedException) throws SQLException {
        throw new SQLException("请改用SupportDAC.setPreValues(Map,boolean方法");
    }

    public void setPreValues(Map<String, Object> params) throws SQLException {
        if(!AssertUtil.isEmpty(params)) {
            this.testPrepared();
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                this.setPreValue((String)entry.getKey(), entry.getValue());
            }
        }

    }

    @Override
    public <T extends IBasePO> void insertData(T var1) throws SQLException {

    }

    @Override
    public <T extends IBasePO> void updateData(T var1, boolean var2) throws SQLException {

    }

    private static void testConnection() throws SQLException {
        if(getConnection() == null || getConnection().isClosed()) {
            throw new SQLException("连接未初始化或已关闭");
        }
    }

    private void testPrepared() throws SQLException {
        if(this.pstm == null) {
            throw new SQLException("预处理语句还未准备好");
        }
    }

    private List<Integer> getNamedIndex(String name) throws SQLException {
        List list = (List)this.namedParams.get(name);
        if(null != list && !list.isEmpty()) {
            return list;
        } else {
            throw new SQLException("不存在指定的别名参数：" + name);
        }
    }



    public static void setBegin() throws SQLException {
        beginTransaction();
    }

    public static void beginTransaction() throws SQLException {
        Connection conn = getConnection();
        conn = getConnection();
        ((Connection)connThread.get()).setAutoCommit(false);

    }

    public static void commit() throws SQLException {
        commit(true);
    }

    public static void commit(boolean destory) throws SQLException {
            testConnection();
            Connection conn = getConnection();
            conn.commit();
            conn.setAutoCommit(true);

    }

    public static boolean rollbackQuietly() {
            try {
                testConnection();
                Connection e = getConnection();
                e.rollback();
                e.setAutoCommit(true);
                e.close();
                return true;
            } catch (SQLException var1) {
                log.error(var1.getMessage(), var1);
                return false;
            }

    }


}
