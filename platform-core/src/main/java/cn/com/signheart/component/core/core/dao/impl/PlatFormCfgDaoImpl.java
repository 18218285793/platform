package cn.com.signheart.component.core.core.dao.impl;

import cn.com.signheart.common.platformbase.BaseDAOImpl;
import cn.com.signheart.component.core.core.dao.IPlatFormCfgDao;
import cn.com.signheart.component.core.core.model.TbPlatFormConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ao.ouyang on 16-1-11.
 */
@Repository("platFormCfgDao")
public class PlatFormCfgDaoImpl extends BaseDAOImpl implements IPlatFormCfgDao {
    @Override
    public List<TbPlatFormConfig> getAllCfg() throws SQLException {
        return super.getList("select * from tb_platform_config order by component_name",TbPlatFormConfig.class);
    }
}
