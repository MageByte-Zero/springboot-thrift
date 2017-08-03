package testDemo;

import com.zero.Application;
import com.zero.proxy.ThriftClientProxy;
import com.zero.thrift.protocol.response.TGetBankCardListResult;
import com.zero.thrift.protocol.service.ThriftBankCardService;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)// 指定spring-boot的启动类
public class BankCardTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ThriftClientProxy thriftClientProxy;

    /**
     * 测试连接池方式湖区socket
     */
    @Test
    public void testClient() {
        ThriftBankCardService.Iface client = (ThriftBankCardService.Iface) thriftClientProxy.getClient(ThriftBankCardService.class);
        try {
            TGetBankCardListResult bankCardList = client.getBankCardList();
            logger.info(bankCardList.toString());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}