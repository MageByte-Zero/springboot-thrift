package zero.thrift;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

/**
 * 监控thriftserver生命周期，记录client请IP日志
 * Created by jianqing.li on 2017/4/27.
 */
public class ThriftServerEventHandler implements TServerEventHandler {

    private Logger log = LoggerFactory.getLogger(ThriftServerEventHandler.class);

    /**
     * 服务成功启动后执行
     */
    @Override
    public void preServe() {
    }

    /**
     * 创建Context的时候，触发
     * 在server启动后，只会执行一次
     */
    @Override
    public ServerContext createContext(TProtocol input, TProtocol output) {
        if (input != null && input.getTransport() != null) {
            Socket socket = ((TSocket) input.getTransport()).getSocket();
            log.info("[Monitor] ThriftServer Socket Info : server地址: " + socket.getLocalAddress() + " , server端口: "
                    + socket.getLocalPort() + " , client地址: " + socket.getInetAddress() + " , client端口: "
                    + socket.getPort());
        }
        return null;
    }

    /**
     * 删除Context的时候，触发
     * 在server启动后，只会执行一次
     */
    @Override
    public void deleteContext(ServerContext serverContext, TProtocol input, TProtocol output) {
    }

    /**
     * 调用RPC服务的时候触发
     * 每调用一次方法，就会触发一次
     */
    @Override
    public void processContext(ServerContext serverContext, TTransport inputTransport, TTransport outputTransport) {
//        /**
//         * 把TTransport对象转换成TSocket，然后在TSocket里面获取Socket，就可以拿到客户端IP
//         */
//        TSocket socket = (TSocket) inputTransport;
//        InetSocketAddress netSocketAddress = (InetSocketAddress) socket.getSocket().getRemoteSocketAddress();
//        log.info("method invoke ip={}:{}", netSocketAddress.getHostString(), netSocketAddress.getPort());
    }
}