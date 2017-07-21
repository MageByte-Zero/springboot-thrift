package test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zero.Application;
import com.zero.mapper.master.MerchantMapper;
import com.zero.model.FtcSupportBank;
import com.zero.model.Merchant;
import com.zero.service.BankCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)// 指定spring-boot的启动类
//@SpringApplicationConfiguration(classes = Application.class)// 1.4.0 前版本
public class SpringBootJdbcTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private BankCardService bankCardService;

    @Test
    public void testMybatis() {
        //分页查询
        PageHelper.startPage(1, 2, true);
        //分页时，实际返回的结果list类型是Page<E>，如果想取出分页信息，需要强制转换为Page<E>
        List<Merchant> list = merchantMapper.getMerchantInfo();
        PageInfo<Merchant> pageInfo = new PageInfo<>(list);
        log.info("pageInfo:{}, list={}", pageInfo, list);
    }

    @Test
    public void testSlave() {
        //分页查询
        PageHelper.startPage(1, 2, true);
        //分页时，实际返回的结果list类型是Page<E>，如果想取出分页信息，需要强制转换为Page<E>
        Map<String, Object> param = new HashMap<>();
        param.put("flag", true);
        List<FtcSupportBank> supportBankList = bankCardService.getSupportBankList(param);
        PageInfo<FtcSupportBank> pageInfo = new PageInfo<>(supportBankList);

        log.info("pageInfo:{}, list={}", pageInfo, supportBankList);
        List<Merchant> list = merchantMapper.getMerchantInfo();

    }


}