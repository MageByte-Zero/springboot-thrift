package zero.thrift;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import zero.util.SpringContextUtils;

@Component
public class ThriftTcpServerStart implements InitializingBean {

//    public static void main(String[] args) throws Exception {
//
//
//        int port = 8200;
//
//        try {
//
//            port = Integer.parseInt(args[0]) > 1000 ? Integer.parseInt(args[0]) : port;
//
//        } catch (Exception ignored) {
//        }
//
//
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-soa.xml");
//
//        ThriftTcpServer server = new ThriftTcpServer();
//
//        server.setApplicationContext(applicationContext);
//
//        server.setPort(port);
//
//        server.start();
//
//
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        ThriftTcpServer server = new ThriftTcpServer();
        server.setApplicationContext(applicationContext);
        server.start();
    }
}