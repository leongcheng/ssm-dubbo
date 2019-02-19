package com.ssm.web.comtroller;

import com.ssm.common.po.User;
import com.ssm.common.vo.SysResult;
import com.ssm.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 注册用户
 * Created by Administrator on 2019/2/11 0011 下午 2:37
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping("/{module}")
    public String module(@PathVariable String module){
        return module;
    }

    /**
     * 完成用户信息校验
     * url:http://sso.jt.com/user/check/{param}/{type}
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public MappingJacksonValue findCheckUser(@PathVariable String param,
                                             @PathVariable Integer type,String callback){
        //true 表示数据存在  false表示数据不存在
       boolean flag = userService.findCheckUser(param,type);
        SysResult result = SysResult.oK(flag);
        MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }

    //用户注册
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user){
        try {
            userService.saveUser(user);
            return SysResult.oK();
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"注册失败,请重新注册");
    }

    //实现用户登录
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult findUserByUP(User user, HttpServletResponse response){
        try {
            String token = userService.findUserByUP(user);
            //检验返回值是否为空
            if(StringUtils.isEmpty(token)){
                return SysResult.build(201,"用户名和密码错误");
            }
            Cookie cookie = new Cookie("JT_TICKET", token);
            cookie.setMaxAge(3600 * 24 * 7);//7天超时
            cookie.setPath("/");//cookie所有者
            response.addCookie(cookie);//写入浏览器中
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"用户名和密码错误");
    }

    //用户信息回显时根据token数据进行数据的回显
    @RequestMapping("/query/{token}")
    public MappingJacksonValue findUserByToken(@PathVariable String token,String callback){
        String userJSON = jedisCluster.get(token);
        MappingJacksonValue jacksonValue = null;
        if(StringUtils.isEmpty(userJSON)){
             jacksonValue =
                    new MappingJacksonValue(SysResult.build(201, "用户名密码不正确"));
        }else {
             jacksonValue = new MappingJacksonValue(SysResult.oK(userJSON));
        }
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }

    //退出登录
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse responsere){
        //1.获取用户的JT_TICKET值
        Cookie[] cookies = request.getCookies();
        String token = null;
        for(Cookie cookie : cookies){

            if("JT_TICKET".equals(cookie.getName())){
                 token = cookie.getValue();break;
            }
        }
        if(!StringUtils.isEmpty(token)){
            //数据不为null
            jedisCluster.del(token);
            //删除cookie
            Cookie cookie = new Cookie("JT_TICKET", "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            responsere.addCookie(cookie);
        }
        //重定向到商城首页
        return "redirect:/index.html";
    }
}
