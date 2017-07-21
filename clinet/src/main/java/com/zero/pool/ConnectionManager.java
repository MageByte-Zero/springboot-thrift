package com.zero.pool;

import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 连接池管理 
 */  
@Service
public class ConnectionManager {  
  
    /** 日志记录器 */  
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /** 保存local对象 */  
    ThreadLocal<TSocket> socketThreadSafe = new ThreadLocal<TSocket>();
  
    /** 连接提供池 */  
    @Autowired
    private ConnectionProvider connectionProvider;  
  
    public TSocket getSocket() {  
        TSocket socket = null;  
        try {  
            socket = connectionProvider.getConnection();  
            socketThreadSafe.set(socket);  
            return socketThreadSafe.get();  
        } catch (Exception e) {  
            logger.error("error ConnectionManager.invoke()", e);  
        } finally {  
            connectionProvider.returnCon(socket);  
            socketThreadSafe.remove();  
        }  
        return socket;  
    }  
  
}  