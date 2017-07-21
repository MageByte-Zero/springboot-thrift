package zero.service.impl;

import com.zero.mapper.slave.FtcBankCardMapper;
import com.zero.model.FtcSupportBank;
import com.zero.service.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
