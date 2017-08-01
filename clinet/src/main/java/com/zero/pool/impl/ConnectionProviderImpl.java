package com.zero.pool.impl;

import com.zero.pool.ConnectionProvider;
import com.zero.proxy.ThriftPoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 连接池实现
 */
@Component
public class ConnectionProviderImpl implements ConnectionProvider, InitializingBean, DisposableBean {

    /**
     * 服务的IP地址
     */
//    @Value("${pool.serviceIp}")
    private String serviceIP = "10.3.20.124";
    /**
     * 服务的端口
     */
//    @Value("${pool.port}")
    private int servicePort = 8899;
    /**
     * 连接超时配置
     */
//    @Value("${pool.conTimeOut}")
    private int conTimeOut = 20000;
    /**
     * 可以从缓存池中分配对象的最大数量
     */
//    @Value("${pool.maxActive}")
    private int maxActive = 10;
    /**
     * 缓存池中最大空闲对象数量
     */
//    @Value("${pool.maxIdle}")
    private int maxIdle = 10;
    /**
     * 缓存池中最小空闲对象数量
     */
//    @Value("${pool.minIdle}")
    private int minIdle = 5;
    /**
     * 阻塞的最大数量
     */
//    @Value("${pool.maxWait}")
    private long maxWait = 10;

    /**
     * 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法
     */
//    @Value("${pool.testOnBorrow}")
    private boolean testOnBorrow = true;
//    @Value("${pool.testOnReturn}")
    private boolean testOnReturn = true;
//    @Value("${pool.testWhileIdle}")
    private boolean testWhileIdle = true;

    /**
     * 对象缓存池
     */
    private ObjectPool<TTransport> objectPool = null;

    @Override
    public TSocket getConnection() {
        try {
            // 从对象池取对象  
            TSocket socket = (TSocket) objectPool.borrowObject();
            return socket;
        } catch (Exception e) {
            throw new RuntimeException("error getConnection()", e);
        }
    }

    @Override
    public void returnCon(TSocket socket) {
        try {
            // 将对象放回对象池  
            objectPool.returnObject(socket);
        } catch (Exception e) {
            throw new RuntimeException("error returnCon()", e);
        }
    }

    @Override
    public void destroy() throws Exception {
        try {
            objectPool.close();
        } catch (Exception e) {
            throw new RuntimeException("erorr destroy()", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 对象池  
        objectPool = new GenericObjectPool<TTransport>();
        //  
        ((GenericObjectPool<TTransport>) objectPool).setMaxActive(maxActive);
        ((GenericObjectPool<TTransport>) objectPool).setMaxIdle(maxIdle);
        ((GenericObjectPool<TTransport>) objectPool).setMinIdle(minIdle);
        ((GenericObjectPool<TTransport>) objectPool).setMaxWait(maxWait);
        ((GenericObjectPool<TTransport>) objectPool).setTestOnBorrow(testOnBorrow);
        ((GenericObjectPool<TTransport>) objectPool).setTestOnReturn(testOnReturn);
        ((GenericObjectPool<TTransport>) objectPool).setTestWhileIdle(testWhileIdle);
        ((GenericObjectPool<TTransport>) objectPool).setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
        // 设置factory  
        ThriftPoolableObjectFactory thriftPoolableObjectFactory = new ThriftPoolableObjectFactory(serviceIP, servicePort, conTimeOut);
        objectPool.setFactory(thriftPoolableObjectFactory);
    }

}