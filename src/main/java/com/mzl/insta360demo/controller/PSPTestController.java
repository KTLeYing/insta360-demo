package com.mzl.insta360demo.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: PSP测试控制器
 * @Author: mzl
 */
@RestController
@RequestMapping("/pspTest")
public class PSPTestController {

    @GetMapping("/redirect")
    public void redirect(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建token响应头
        response.setHeader("X-User-Token", token);

        // 创建一个Cookie，将Token存入其中
//        Cookie cookie = new Cookie("X-User-Token", token);
//        // 设置为根路径，使得在整个域名下都有效
//        cookie.setPath("/");
//        // 设置Cookie过期时间（30天）
//        cookie.setMaxAge(30 * 24 * 60 * 60);
//        response.addCookie(cookie);

        // 重定向到目标页面
        // response.sendRedirect("https://www.baidu.com/");

        // 重定向到目标页面
        response.sendRedirect("http://localhost:9999/pspTest/view");
    }

    @GetMapping("/view")
    public String view() {
        return "success";
    }

}