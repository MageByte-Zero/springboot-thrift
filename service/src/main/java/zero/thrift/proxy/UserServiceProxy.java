package zero.thrift.proxy;

import com.zero.thrift.protocol.request.TUserParam;
import com.zero.thrift.protocol.response.TUserResult;
import com.zero.thrift.protocol.service.UserService;
import org.apache.thrift.TException;
import zero.annotation.ThriftInteface;

import java.util.HashMap;
import java.util.Map;

@ThriftInteface
public class UserServiceProxy implements UserService.Iface {
    @Override
    public TUserResult userInfo(TUserParam request) throws TException {
        TUserResult userResult = new TUserResult();
        userResult.setCode("OK");
        Map<String, String> map = new HashMap<>();
        map.put("age", "26");
        map.put("userName", "李健青");
        userResult.setParams(map);
        return userResult;
    }
}
