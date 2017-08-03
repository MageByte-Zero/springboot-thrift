package zero.thrift.proxy;

import com.zero.thrift.protocol.request.TCompanyParam;
import com.zero.thrift.protocol.request.TUserParam;
import com.zero.thrift.protocol.response.TCompanyResult;
import com.zero.thrift.protocol.response.TUserResult;
import com.zero.thrift.protocol.service.ThriftUserService;
import org.apache.thrift.TException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import zero.annotation.ThriftInteface;
import zero.model.Status;
import zero.model.User;
import zero.service.BankCardService;
import zero.util.DozerMapperSingleton;
import zero.vo.CompanyResultVO;

import java.util.*;

@ThriftInteface
public class UserServiceProxy implements ThriftUserService.Iface {

    @Autowired
    private BankCardService bankCardService;

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

    /**
     * 测试Dozer
     * @param request
     * @return
     * @throws TException
     */
    @Override
    public TCompanyResult getCompanyUserList(TCompanyParam request) throws TException {
        Mapper mapper = DozerMapperSingleton.getInstance();
        Status status = new Status();
        status.setMsg("状态1");
        status.setState(1);
        CompanyResultVO companyResultVO = new CompanyResultVO();
        companyResultVO.setId(2);
        companyResultVO.setCode("ZTE");
        User user = new User();
        user.setAge(23);
        user.setId(1L);
        user.setName("王世超");
        user.setStatusList(Arrays.asList(status));

        User user2 = new User();
        user2.setAge(24);
        user2.setId(2L);
        user2.setName("李剑锋");
        user2.setStatusList(Arrays.asList(status));
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        companyResultVO.setUserList(users);
        TCompanyResult companyResult = mapper.map(companyResultVO, TCompanyResult.class);
        return companyResult;
    }

}
