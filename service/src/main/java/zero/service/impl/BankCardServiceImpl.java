package zero.service.impl;

import com.zero.common.ResultInfo;
import com.zero.common.exception.ExceptionEnum;
import com.zero.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zero.mapper.slave.FtcBankCardMapper;
import zero.model.FtcSupportBank;
import zero.service.BankCardService;

import java.util.List;
import java.util.Map;

/**
 * Created by jianqing.li on 2017/7/5.
 */
@Service
public class BankCardServiceImpl implements BankCardService {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FtcBankCardMapper ftcBankCardMapper;

    @Override
    public ResultInfo getSupportBankList(Map<String, Object> param) throws ServiceException {
        ResultInfo resultInfo = new ResultInfo();
        try {
            List<FtcSupportBank> supportBankList = ftcBankCardMapper.getSupportBankList(param);
            int i = 1 / 0;
            return ExceptionEnum.SUCCESS.buildResultVO(resultInfo, supportBankList);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionEnum.FAIL);
        }

    }
}
