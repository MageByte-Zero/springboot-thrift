package zero.service;


import zero.model.Merchant;

import java.util.List;

/**
 * Created by jianqing.li on 2017/7/5.
 */
public interface MerchantService {

    List<Merchant> getAll(Merchant merchant);

    int update(Merchant merchant);
}
