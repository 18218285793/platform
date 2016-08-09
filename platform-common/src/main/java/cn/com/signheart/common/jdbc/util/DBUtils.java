package cn.com.signheart.common.jdbc.util;

import cn.com.signheart.common.reflation.BeanHelper;
import cn.com.signheart.common.util.ExtMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ao.ouyang on 15-11-2.
 */
public class DBUtils {
    private static final transient Log log = LogFactory.getLog(DBUtils.class);

    public static void executeSql(Connection _conn, String _sql) throws SQLException {
        if(_sql != null && _sql.length() > 0) {
            Statement stm = null;

            try {
                stm = _conn.createStatement();
                stm.execute(_sql);
            } finally {
                close(_conn,null,stm);
            }
        }

    }
    public static <T> T getObjFromResult(ResultSet _rs, Class<T> _cls) throws Exception {
        return _rs.next()? BeanHelper.getBeanFromResult(_rs, _cls):null;
    }
    public static Map getMapFromResult(ResultSet _rs) throws SQLException {
        ExtMap returnMap = new ExtMap();
        if(_rs.next()) {
            ResultSetMetaData rsmd = _rs.getMetaData();

            for(int i = 1; i <= rsmd.getColumnCount(); ++i) {
                String name = rsmd.getColumnName(i);
                returnMap.put(name, _rs.getObject(name));
            }
        }

        return returnMap;
    }
    public static <T> List<T> getListFromResult(ResultSet _rs, Class<T> _cls) throws SQLException {
        ArrayList returnList = new ArrayList();

        while(_rs.next()) {
            try {
                returnList.add(BeanHelper.getBeanFromResult(_rs, _cls));
            } catch (Exception var4) {
                log.error(var4);
                throw new SQLException(var4.getMessage(), var4);
            }
        }

        return returnList;
    }
    public static List getMapListFromResult(ResultSet _rs) throws SQLException {
        ArrayList returnList = new ArrayList();

        while(_rs.next()) {
            ExtMap returnMap = new ExtMap();
            ResultSetMetaData rsmd = _rs.getMetaData();

            for(int i = 1; i <= rsmd.getColumnCount(); ++i) {
                String name = rsmd.getColumnName(i);
                returnMap.put(name, _rs.getObject(name));
            }

            returnList.add(returnMap);
        }

        return returnList;
    }

    public static void close(Connection _conn, PreparedStatement _pstm, ResultSet _rs) {
        close(_conn, _rs, new Statement[]{_pstm});
    }

    public static void close(Connection _conn, ResultSet _rs, Statement... _stms) {
        if(_rs != null) {
            try {
                _rs.close();
                _rs = null;
            } catch (SQLException var11) {
                log.warn("警告：关闭结果集时发生异常" + var11.getMessage());
            }
        }

        if(null != _stms && 0 < _stms.length) {
            Statement[] isConnClosed = _stms;
            int e = _stms.length;

            for(int i$ = 0; i$ < e; ++i$) {
                Statement st = isConnClosed[i$];
                if(null != st) {
                    try {
                        st.close();
                        st = null;
                    } catch (SQLException var10) {
                        log.warn("警告：关闭活动语句时发生异常" + var10.getMessage());
                    }

                    st = null;
                }
            }

            _stms = null;
        }

        if(_conn != null) {
            boolean var12 = false;

            try {
                var12 = _conn.isClosed();
                _conn = null;
                var12 = true;
            } catch (SQLException var9) {
                ;
            }

            if(!var12) {
                try {
                    _conn.close();
                } catch (SQLException var8) {
                    log.warn("警告：关闭连接中发生异常" + var8.getMessage());
                }
            }

            _conn = null;
        }

    }

}
