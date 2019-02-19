package com.ssm.dubbo.service;

import com.ssm.common.po.User;

/**
 * Created by Administrator on 2019/2/11 0011 下午 9:17
 */
public interface UserService {

    public boolean findcheckUser(String param, Integer type);

    public void saveUser(User user);

    public String findUserByUP(User user);



}
