package zero.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import zero.annotation.ThriftInteface;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThriftTcpServer {

    private Logger logger = LoggerFactory.getLogger(ThriftTcpServer.class);

    private AtomicBoolean init = new AtomicBoolean(false);

    private TServer server;

    private ApplicationContext applicationContext;

    @Value("${thrift.port}")
    private int port;

    private int selectorThreads = getProcessNum();

    private int workerThreads = getProcessNum() * 10;

    /**
     * selector线程等待请求队列，业务方是期望快速返回的，服务端繁忙时客户端也不会一直等下去，所以不需设置太多
     */
    @Value("${thrift.acceptQueueSizePerThread}")
    private int acceptQueueSizePerThread;

    /***
     * 协议类型，in (binary,compact,json)
     */
    @Value("${thrift.protocol}")
    private String protocol;

    protected void init() {
        try {
            if (init.get()) {
                return;
            }
            if (!init.compareAndSet(false, true)) {
                return;
            }
            //多线程非阻塞IO服务transport
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
            //支持多接口的处理器
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            //注册服务
            registerService(processor);
            //对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送
            TFramedTransport.Factory transportFactory = new TFramedTransport.Factory();
            //默认是2个selector线程，5个工作线程
            TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
            tArgs.transportFactory(transportFactory);
            tArgs.protocolFactory(chooseProtocol());
            tArgs.processor(processor);
            tArgs.selectorThreads(selectorThreads);//selector线程只处理对accept的连接的读写事件轮询，除非并发量极大时可以适度调大此值，否则太大会浪费资源
            tArgs.workerThreads(workerThreads);//工作线程池的大小，根据实际统计数据决定
            tArgs.acceptQueueSizePerThread(acceptQueueSizePerThread);//selector线程等待请求队列，业务方是期望快速返回的，服务端繁忙时客户端也不会一直等下去，所以不需设置太多
            //多线程非阻塞IO服务模式，兼顾资源使用与高性能
            server = new TThreadedSelectorServer(tArgs);
            //当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，当系统执行完这些钩子后，jvm才会关闭
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.stop();
                }
            }));
            logger.info("thrift server init success");
        } catch (Exception e) {
            logger.error("thriftTcpServer init error", e);
            throw new RuntimeException(e);
        }

    }

    private int getProcessNum() {
        return Runtime.getRuntime().availableProcessors();
    }


    private TProtocolFactory chooseProtocol() {
        TProtocolFactory protocolFactory;
        if ("json".equals(protocol)) {
            protocolFactory = new TJSONProtocol.Factory();
        } else if ("compact".equals(protocol)) {
            protocolFactory = new TCompactProtocol.Factory();
        } else {
            //默认binary
            protocolFactory = new TBinaryProtocol.Factory();
        }
        return protocolFactory;
    }

    protected void registerService(TMultiplexedProcessor processor) throws Exception {
        Pattern pattern = Pattern.compile("^(.+)\\$Iface$");
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ThriftInteface.class);
        logger.info("find ThriftService: {}", beans.keySet());
        for (String beanName : beans.keySet()) {
            Object bean = beans.get(beanName);
            Class<?> beanClass = AopUtils.getTargetClass(bean);
            for (Class<?> interfaceClazz : beanClass.getInterfaces()) {
                String interfaceName = interfaceClazz.getName();//HelloWordService.Iface
                Matcher matcher = pattern.matcher(interfaceName);
                if (matcher.find()) {
                    String interfaceClass = matcher.group(1);//HelloWordService
                    TProcessor serviceProcess = (TProcessor) Class.forName(interfaceClass + "$Processor")
                            .getDeclaredConstructor(interfaceClazz).newInstance(bean);
                    processor.registerProcessor(interfaceClass, serviceProcess);
                    logger.info("register thrift service:{}", interfaceClass);
                }
            }

        }

    }


    public void start() {
        init();
        server.setServerEventHandler(new ThriftServerEventHandler());
        server.serve();
        logger.info("soa tcp server start at port[ + {} + ]...", port);
    }

    public void stop() {
        if (server != null) {
            server.stop();
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSelectorThreads(int selectorThreads) {
        this.selectorThreads = selectorThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }

    public void setAcceptQueueSizePerThread(int acceptQueueSizePerThread) {
        this.acceptQueueSizePerThread = acceptQueueSizePerThread;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}