package com.ssm.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.common.po.Item;
import com.ssm.common.po.ItemDesc;
import com.ssm.common.service.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Administrator on 2019/2/11 0011 上午 1:25
 */
@Service
public class CatServiceImpl implements ItemService{

    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Item findItemById(Long itemId) {
        //发起请求
        String url = "http://manage.jt.com/web/item/"+itemId;
        String result = httpClientService.doGet(url);
        Item item = null;
        try {
            item = objectMapper.readValue(result, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        //发起请求
        String url = "http://manage.jt.com/web/item/desc/"+itemId;
        String result = httpClientService.doGet(url);
        ItemDesc itemDesc = null;
        try {
            itemDesc = objectMapper.readValue(result, ItemDesc.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemDesc;
    }
}
