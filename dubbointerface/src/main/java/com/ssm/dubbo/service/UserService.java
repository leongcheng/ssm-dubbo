package com.ssm.dubbo.service;

import com.ssm.common.po.User;

/**
 * 用户注册
 * Created by Administrator on 2019/2/11 0011 下午 3:02
 */
public interface UserService {

    public boolean findcheckUser(String param, Integer type);

    public void saveUser(User user);
}
