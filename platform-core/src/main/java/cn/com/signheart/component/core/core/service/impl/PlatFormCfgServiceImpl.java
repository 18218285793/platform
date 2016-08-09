package cn.com.signheart.component.core.core.service.impl;

import cn.com.signheart.component.core.core.dao.IPlatFormCfgDao;
import cn.com.signheart.component.core.core.model.TbPlatFormConfig;
import cn.com.signheart.component.core.core.service.IPlatFormCfgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ao.ouyang on 16-1-11.
 */
@Service("platFormCfgService")
public class PlatFormCfgServiceImpl implements IPlatFormCfgService {

    @Resource
    private IPlatFormCfgDao platFormCfgDao;

    @Override
    public List<TbPlatFormConfig> listAllCfg() throws SQLException {
        return platFormCfgDao.getAllCfg();
    }
}
