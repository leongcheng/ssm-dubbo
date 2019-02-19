package com.ssm.dubbo.service;

import com.ssm.common.po.Item;
import com.ssm.common.po.ItemDesc;
import com.ssm.common.vo.EasyUIResult;

/**
 * Created by Administrator on 2019/2/9 0009 下午 7:49
 */
public interface ItemService {

    EasyUIResult findItemByPage(int page, int rows);

    String findItemCatById(Long itemId);

    void saveItem(Item item, String desc);

    void updateItem(Item item, String desc);

    void deleteItemById(Long[] ids);

    void updateStatus(Long[] ids, int status);

    ItemDesc findItemDescById(Long itemId);
    //前台数据接受
    Item findItemById(Long itemId);
}
