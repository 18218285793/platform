package cn.com.signheart.common.povo;

import java.io.Serializable;

/**
 * Created by ao.ouyang on 15-11-2.
 */
public interface IBasePO extends Serializable {

    String _getTableName();

    String _getPKColumnName();

    String _getPKValue();

    void _setPKValue(Object var1);
}
