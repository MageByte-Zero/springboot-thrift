package zero.service;


import com.zero.common.ResultInfo;
import com.zero.common.exception.ServiceException;

import java.util.Map;

/**
 * Created by jianqing.li on 2017/7/5.
 */
public interface BankCardService {

    ResultInfo getSupportBankList(Map<String, Object> param) throws ServiceException;
}
