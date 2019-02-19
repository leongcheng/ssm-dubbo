package com.ssm.web.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2019/2/10 0010 下午 4:10
 */
@Controller
public class IndexController {

    //首页访问
    @RequestMapping("/index")
    public String indexUI(){
        return "index";
    }
}
