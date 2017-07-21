package com.zero.client;

import com.zero.proxy.ThriftClientProxy;
import com.zero.util.SpringContextUtils;
import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;

public class ClientTest {
    public static void main(String[] args) throws TException {
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        ThriftClientProxy thriftClientProxy = (ThriftClientProxy) applicationContext.getBean(ThriftClientProxy.class);
//        HelloWorld.Iface client = (HelloWorld.Iface)thriftClientProxy.getClient(HelloWorld.class);
//        System.out.println(client.sayHello("Jack"));
    }  
}  