package zero.mapper.master;

import com.zero.database.BaseMapper;
import com.zero.model.Merchant;

import java.util.List;

public interface MerchantMapper extends BaseMapper<Merchant> {

    List<Merchant> getMerchantInfo();

    int update(Merchant merchant);
}