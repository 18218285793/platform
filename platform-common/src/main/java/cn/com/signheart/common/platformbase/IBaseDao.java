package cn.com.signheart.common.platformbase;


import cn.com.signheart.common.exception.DefaultException;
import cn.com.signheart.common.jdbc.IBaseDAC;
import cn.com.signheart.common.povo.IBasePO;

import java.sql.SQLException;

/**
 * Created by ao.ouyang on 15-11-3.
 */
public interface IBaseDao extends IBaseDAC {

    <T extends IBasePO> T insert(T var1) throws Exception, IllegalAccessException;

    <T extends IBasePO> T update(T var1, boolean var2) throws SQLException;

    <T extends IBasePO> T searchByPk(T var1) throws Exception;

    <T extends IBasePO> T searchByPk(Class<T> var1, String var2) throws Exception;

    <T extends IBasePO> void delete(T var1) throws Exception;

    <T extends IBasePO> void deleteByPk(Class<T> var1, String var2) throws Exception;

    String getSqlByID(String var1, Object var2) throws DefaultException, Exception;

    String getSqlByID(String var1) throws DefaultException;
}
