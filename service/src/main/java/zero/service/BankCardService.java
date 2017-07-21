package zero.service;


import zero.model.FtcSupportBank;

import java.util.List;
import java.util.Map;

/**
 * Created by jianqing.li on 2017/7/5.
 */
public interface BankCardService {

    List<FtcSupportBank> getSupportBankList(Map<String, Object> param);
}
