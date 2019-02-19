package com.ssm.web.service;

import com.ssm.common.po.Item;
import com.ssm.common.po.ItemDesc;

/**
 * Created by Administrator on 2019/2/11 0011 上午 11:58
 */
public interface ItemService {

    Item findItemById(Long itemId);

    ItemDesc findItemDescById(Long itemId);
}
