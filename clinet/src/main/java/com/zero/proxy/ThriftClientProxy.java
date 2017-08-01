package com.zero.proxy;

import com.zero.pool.ConnectionManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
public class ThriftClientProxy {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConnectionManager connectionManager;
  
    public ConnectionManager getConnectionManager() {  
        return connectionManager;  
    }  
    public void setConnectionManager(ConnectionManager connectionManager) {  
        this.connectionManager = connectionManager;  
    }  
    public Object getClient(Class clazz) {  
        Object result = null;  
        try {
            //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送
            TTransport transport = new TFramedTransport(connectionManager.getSocket());
//            TTransport transport = connectionManager.getSocket();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, clazz.getName());
            Class client = Class.forName(clazz.getName() + "$Client");  
            Constructor con = client.getConstructor(TProtocol.class);
            result = con.newInstance(mp);
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException", e);
        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException", e);
        } catch (SecurityException e) {
            logger.error("SecurityException", e);
        } catch (InstantiationException e) {
            logger.error("InstantiationException", e);
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException", e);
        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgumentException", e);
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException", e);
        }  
        return result;  
    }  
}  