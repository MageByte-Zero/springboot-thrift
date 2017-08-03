package zero.thrift.proxy;


import com.zero.thrift.protocol.response.TGetBankCardListResult;
import com.zero.thrift.protocol.service.ThriftBankCardService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import zero.annotation.ThriftHandler;
import zero.model.FtcSupportBank;
import zero.service.BankCardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThriftHandler
public class BankCardServiceHandler implements ThriftBankCardService.Iface {

    @Autowired
    private BankCardService bankCardService;

    @Override
    public TGetBankCardListResult getBankCardList() throws TException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("flag", true);
        List<FtcSupportBank> bankList = bankCardService.getSupportBankList(paramMap);
        return null;
    }
}
