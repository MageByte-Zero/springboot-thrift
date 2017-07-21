package com.zero.proxy;

import com.zero.pool.ConnectionManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
            TTransport transport = connectionManager.getSocket();
            TProtocol protocol = new TBinaryProtocol(transport);
            Class client = Class.forName(clazz.getName() + "$Client");  
            Constructor con = client.getConstructor(TProtocol.class);
            result = con.newInstance(protocol);  
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