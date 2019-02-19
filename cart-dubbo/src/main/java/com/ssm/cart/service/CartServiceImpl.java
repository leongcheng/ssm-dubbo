package com.ssm.cart.service;

import com.ssm.cart.mapper.CartMapper;
import com.ssm.common.po.Cart;
import com.ssm.dubbo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/2/11 0011 下午 2:11
 */
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    //购物车实现
    @Override
    public List<Cart> findCartByUserId(Long userId){
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartMapper.select(cart);
    }

    //新增购物车
    @Override
    public void saveCart(Cart cart){
        //根据userId和itemId判断数据库中是否有该购物信息
       Cart cartDB = cartMapper.findCartByUI(cart);
       if(cartDB == null){
           //没有数据时直接新增
           cart.setCreated(new Date());
           cart.setUpdated(cart.getCreated());
           cartMapper.insert(cart);
       }else {
           //有数据时累加更新
           int num = cartDB.getNum() + cart.getNum();
           cartDB.setNum(num);
           cartDB.setUpdated(new Date());
           cartMapper.updateByPrimaryKeySelective(cartDB);
       }
    }

    //删除购物商品
    @Override
    public void deleteCart(Long userId,Long itemId){
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(itemId);
        cartMapper.delete(cart);
    }


    //修改购物车数量
    @Override
    public void updateNum(Long userId,Long itemId,Integer num){
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(itemId);
        cart.setNum(num);
        cart.setUpdated(new Date());
        cartMapper.updateNum(cart);
    }
}
