package com.ssm.cart.mapper;

import com.ssm.common.mapper.SysMapper;
import com.ssm.common.po.Cart;

/**
 * Created by Administrator on 2019/2/11 0011 下午 11:42
 */
public interface CartMapper extends SysMapper<Cart> {
    Cart findCartByUI(Cart cart);

    void updateNum(Cart cart);
}
