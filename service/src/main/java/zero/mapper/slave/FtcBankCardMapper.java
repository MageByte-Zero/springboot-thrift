package zero.mapper.slave;


import com.zero.model.FtcSupportBank;

import java.util.List;
import java.util.Map;


/**
 *
 */
public interface FtcBankCardMapper {


    /**
     * 获取银行列表
     *
     * @param param-已启用 false-返回所有
     * @return 银行列表
     */
    List<FtcSupportBank> getSupportBankList(Map<String, Object> param);

}
