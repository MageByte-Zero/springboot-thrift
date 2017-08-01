package testDemo;

import com.zero.Application;
import com.zero.proxy.ThriftClientProxy;
import com.zero.thrift.protocol.request.TCompanyParam;
import com.zero.thrift.protocol.request.TUserParam;
import com.zero.thrift.protocol.response.TCompanyResult;
import com.zero.thrift.protocol.response.TUserResult;
import com.zero.thrift.protocol.service.UserService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)// 指定spring-boot的启动类
public class ClientTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ThriftClientProxy thriftClientProxy;

    /**
     * 测试连接池方式湖区socket
     */
    @Test
    public void testClient() {
        UserService.Iface client = (UserService.Iface) thriftClientProxy.getClient(UserService.class);
        TUserParam userParam = new TUserParam();
        userParam.setId("16");
        try {
            TUserResult userResult = client.userInfo(userParam);
            logger.info(userResult.toString());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    /**
     * TMultiplexedProtocol端口复用客户端测试
     */
    @Test
    public void testThriftClient() {
        //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送
        TTransport transport = new TFramedTransport(new TSocket("10.3.20.124", 8899, 20000));
        // 协议要和服务端一致
        //HelloTNonblockingServer
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "com.zero.thrift.protocol.service.UserService");
        //HelloTHsHaServer
        ////使用二进制协议
        //TProtocol protocol = new TBinaryProtocol(transport);
        UserService.Client client = new UserService.Client(mp);

        TUserParam userParam = new TUserParam();
        userParam.setId("16");
        TUserResult userResult = null;
        try {
            transport.open();
            userResult = client.userInfo(userParam);
        } catch (TException e) {
            e.printStackTrace();
        }
        logger.info("result : " + userResult);
        //关闭资源
        transport.close();
    }

    /**
     * 测试testDozer
     */
    @Test
    public void testDozer() {
        UserService.Iface client = (UserService.Iface) thriftClientProxy.getClient(UserService.class);
        TCompanyParam tCompanyParam = new TCompanyParam();
        tCompanyParam.setUserName("helow");
        try {
            TCompanyResult companyResult = client.getCompanyUserList(tCompanyParam);
            logger.info(companyResult.toString());
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}