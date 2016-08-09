package cn.com.signheart.common.platformbase;


import cn.com.signheart.common.exception.DefaultException;
import cn.com.signheart.common.jdbc.SupportDAC;
import cn.com.signheart.common.povo.IBasePO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Created by ao.ouyang on 15-11-3.
 */
public class BaseDAOImpl extends SupportDAC implements IBaseDao {
        private static final transient Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);

        public BaseDAOImpl() {
        }


        public <T extends IBasePO> T insert(T po) throws Exception{

                super.insertData(po);
                return po;
        }

        @Override
        public <T extends IBasePO> T update(T var1, boolean var2) throws SQLException {
                return null;
        }

        @Override
        public <T extends IBasePO> T searchByPk(T var1) throws Exception {
                return null;
        }

        @Override
        public <T extends IBasePO> T searchByPk(Class<T> var1, String var2) throws Exception {
                return null;
        }

        @Override
        public <T extends IBasePO> void delete(T var1) throws Exception {

        }

        @Override
        public <T extends IBasePO> void deleteByPk(Class<T> var1, String var2) throws Exception {

        }

        @Override
        public String getSqlByID(String var1, Object var2) throws DefaultException, Exception {
                return null;
        }

        @Override
        public String getSqlByID(String var1) throws DefaultException {
                return null;
        }

}
