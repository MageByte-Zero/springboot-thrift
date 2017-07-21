package zero.service.impl;

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

    @Autowired
    private FtcBankCardMapper ftcBankCardMapper;

    @Override
    public List<FtcSupportBank> getSupportBankList(Map<String, Object> param) {
        return ftcBankCardMapper.getSupportBankList(param);
    }
}
