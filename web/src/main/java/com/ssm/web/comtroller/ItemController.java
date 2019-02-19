package com.ssm.web.comtroller;

import com.ssm.common.po.Item;
import com.ssm.common.po.ItemDesc;
import com.ssm.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台系统
 * Created by Administrator on 2019/2/11 0011 上午 10:45
 */
@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    public String findItemById(@PathVariable Long itemId, Model model){
        Item item = itemService.findItemById(itemId);
        model.addAttribute("item",item);
        ItemDesc itemDesc = itemService.findItemDescById(itemId);
        model.addAttribute("itemDesc",itemDesc);
        //跳转到商品展示页面
        return "item";
    }
}
