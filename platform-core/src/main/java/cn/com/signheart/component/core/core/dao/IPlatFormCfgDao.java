package cn.com.signheart.component.core.core.dao;


import cn.com.signheart.component.core.core.model.TbPlatFormConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ao.ouyang on 16-1-11.
 */
public interface IPlatFormCfgDao {

    /**
     * 查询所有配置
     * @return
     * @throws SQLException
     */
    public List<TbPlatFormConfig> getAllCfg() throws SQLException;
}
