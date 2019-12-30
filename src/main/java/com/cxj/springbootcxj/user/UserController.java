package com.cxj.springbootcxj.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : ChuXianJie
 * @date : 2019-12-28 17:49
 */

@RequestMapping("user")
@ControllerAdvice
public class UserController {
    private static final Log logger = LogFactory.getLog(UserController.class);

    @RequestMapping("sayHello")
    public void sayHello(){
        logger.info("---logger---:hello world!");
        System.out.println("hello world!");
    }
}
