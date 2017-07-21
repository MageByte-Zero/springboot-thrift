package testDemo;

import com.zero.Application;
import com.zero.proxy.ThriftClientProxy;
import com.zero.thrift.protocol.request.TUserParam;
import com.zero.thrift.protocol.service.UserService;
import com.zero.util.SpringContextUtils;
import org.apache.thrift.TException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)// 指定spring-boot的启动类
public class ClientTest {

    public void testClient() {
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        ThriftClientProxy thriftClientProxy = (ThriftClientProxy) applicationContext.getBean(ThriftClientProxy.class);
        UserService.Iface client = (UserService.Iface)thriftClientProxy.getClient(UserService.class);
        TUserParam userParam = new TUserParam();
        userParam.setId("16");
        try {
            System.out.println(client.userInfo(userParam));
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}