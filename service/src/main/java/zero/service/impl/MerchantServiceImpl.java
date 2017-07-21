package zero.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import zero.mapper.master.MerchantMapper;
import zero.model.Merchant;
import zero.service.MerchantService;

import java.util.List;

/**
 * Created by jianqing.li on 2017/7/5.
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Cacheable(value = "Cacheable")
    @Override
    public List<Merchant> getAll(Merchant merchant) {
        if (merchant.getPage() != null && merchant.getRows() != null) {
            PageHelper.startPage(merchant.getPage(), merchant.getRows());
        }
        return merchantMapper.selectAll();
    }

    @CacheEvict(value = { "Cacheable"}, allEntries = true)
    @Override
    public int update(Merchant merchant) {
        return merchantMapper.update(merchant);
    }
}
