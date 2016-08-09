package cn.com.signheart.component.core.core.service;


import cn.com.signheart.component.core.core.model.TbPlatFormConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ao.ouyang on 16-1-11.
 */
public interface IPlatFormCfgService {

    /**
     * 查询所有配置
     * @return
     * @throws SQLException
     */
    List<TbPlatFormConfig> listAllCfg() throws SQLException;
}
