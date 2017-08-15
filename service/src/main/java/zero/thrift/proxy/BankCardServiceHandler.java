package zero.thrift.proxy;


import com.zero.common.ResultInfo;
import com.zero.thrift.protocol.response.TBankInfo;
import com.zero.thrift.protocol.response.TGetBankCardListResult;
import com.zero.thrift.protocol.response.TResponseStatus;
import com.zero.thrift.protocol.service.ThriftBankCardService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import zero.annotation.ThriftHandler;
import zero.model.FtcSupportBank;
import zero.service.BankCardService;
import zero.util.DozerUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThriftHandler
public class BankCardServiceHandler implements ThriftBankCardService.Iface {

    @Autowired
    private BankCardService bankCardService;

    @Override
    public TGetBankCardListResult getBankCardList() throws TException {
        TGetBankCardListResult result = new TGetBankCardListResult();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("flag", true);
        ResultInfo resultInfo = bankCardService.getSupportBankList(paramMap);
        List<FtcSupportBank> resultData = (List<FtcSupportBank>) resultInfo.getResultData();
        if (CollectionUtils.isNotEmpty(resultData)) {
            List<TBankInfo> tBankInfoList = DozerUtil.mapAsList(resultData, TBankInfo.class);
            result.setDataList(tBankInfoList);
        }
        result.setResponseStatus(new TResponseStatus(resultInfo.getCode(), resultInfo.getMsg()));
        return result;
    }
}
