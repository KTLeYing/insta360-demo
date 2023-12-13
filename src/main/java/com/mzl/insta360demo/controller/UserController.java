package com.mzl.insta360demo.controller;

import com.mzl.insta360demo.base.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2023/12/13 17:25
 * @Version: 1.0
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("/test")
    public Response<Void> test(){
        return Response.success();
    }

}