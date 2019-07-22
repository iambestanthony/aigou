package com.mmall.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JayJ on 2018/8/30.
 **/
@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @RequestMapping("/get.do")
    @ResponseBody
    public String get(){
        return "{'id':'2133213'}";
    }
}
