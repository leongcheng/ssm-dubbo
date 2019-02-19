package com.ssm.web.comtroller;

import com.ssm.common.po.Cart;
import com.ssm.common.po.User;
import com.ssm.common.vo.SysResult;
import com.ssm.dubbo.service.CartService;
import com.ssm.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2019/2/11 0011 下午 9:21
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //跳转到购物车展现页面
    @RequestMapping("/show")
    public String show(Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("JT_USER");
        Long userId = user.getId();
        List<Cart> cartList = cartService.findCartByUserId(userId);
        //将cartList数据保存到request对象中
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    //实现购物车新增
    @RequestMapping("/add/{itemId}")
    public String saceCart(@PathVariable Long itemId,Cart cart){
        //根据userId和itemId判断数据库中是否有该购物信息
        Long userId = UserThreadLocal.get().getId();
        cart.setItemId(itemId);
        cart.setUserId(userId);
        cartService.saveCart(cart);
        //重定向到购物车展现页面
        return "redirect:/cart/show.html";
    }

    //删除商品
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId){
        Long userId = UserThreadLocal.get().getId();
        cartService.deleteCart(userId,itemId);
        return "redirect:/cart/show.html"; //重定向到列表页面
    }

    //更新购物车数量
    public SysResult updateNum(@PathVariable Long itemId,@PathVariable Integer num){
        try {
            Long userId = UserThreadLocal.get().getId();
            cartService.updateNum(userId,itemId,num);
            return SysResult.oK();
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"商品修改失败");
    }

}
