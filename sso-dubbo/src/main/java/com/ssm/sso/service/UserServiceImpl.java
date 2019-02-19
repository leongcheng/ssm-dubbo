package com.ssm.sso.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.common.po.User;
import com.ssm.dubbo.service.UserService;
import com.ssm.sso.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

/**
 * Created by Administrator on 2019/2/11 0011 下午 3:02
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public boolean findcheckUser(String param, Integer type){
        String cloumn = null;
        switch (type){
            case 1: cloumn = "username";break;
            case 2: cloumn = "phone";break;
            case 3: cloumn = "email";break;
        }
        //获取用户记录总数
        int count = userMapper.findCheckUser(cloumn,param);
        return count == 0 ? false :true;

    }

    @Override
    public void saveUser(User user){
        //将密码加密
        String md5Hex = DigestUtils.md5Hex(user.getPassword());

        user.setEmail(user.getPhone());
        user.setPassword(md5Hex);
        user.setUsername(user.getUsername());
        user.setPhone(user.getPhone());
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        userMapper.insert(user);
    }

    @Override
    public String findUserByUP(User user){
        //1.根据用户名和密码查询数据库
        User userDB = userMapper.findUserByUP(user);
        if(userDB == null){
            //1.1如果结果为null  表示用户名或密码错误 throw
            throw new RuntimeException();
        }
        //2.生成加密token
        String token = DigestUtils.md5Hex(
                "JT_TICKET_"+ System.currentTimeMillis() + user.getUsername());
        try {
            //3.将数据 token:json 存入redis集群中
            String userJSON = objectMapper.writeValueAsString(userDB);
            jedisCluster.setex(token ,3600 *24 * 7,userJSON);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return token;
    }

}
