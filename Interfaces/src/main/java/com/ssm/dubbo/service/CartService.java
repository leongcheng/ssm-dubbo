package com.ssm.dubbo.service;

import com.ssm.common.po.Cart;

import java.util.List;

/**
 * Created by Administrator on 2019/2/11 0011 下午 9:45
 */
public interface CartService {
    //购物展示
    public List<Cart> findCartByUserId(Long userId);
    //新增商品到购物车
    public void saveCart(Cart cart);
    //删除购物车商品
    public void deleteCart(Long userId,Long itemId);
    //更新购物商品
    public void updateNum(Long userId,Long itemId,Integer num);


}
