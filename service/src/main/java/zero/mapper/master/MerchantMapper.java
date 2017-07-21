package zero.mapper.master;


import zero.database.BaseMapper;
import zero.model.Merchant;

import java.util.List;

public interface MerchantMapper extends BaseMapper<Merchant> {

    List<Merchant> getMerchantInfo();

    int update(Merchant merchant);
}