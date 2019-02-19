package com.ssm.sso.mapper;

import com.ssm.common.mapper.SysMapper;
import com.ssm.common.po.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/2/11 0011 下午 8:06
 */
public interface UserMapper extends SysMapper<User> {

    int findCheckUser(@Param("cloumn") String cloumn, @Param("param") String param);

    User findUserByUP(User user);
}
