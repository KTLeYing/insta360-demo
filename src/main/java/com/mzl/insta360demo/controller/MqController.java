package com.mzl.insta360demo.controller;

import com.mzl.insta360demo.base.Response;
import com.mzl.insta360demo.entity.User;
import com.mzl.insta360demo.infrastructure.mq.producer.ProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: RocketMQController
 * @Description: 消息队列控制器
 * @Author: mzl
 * @CreateDate: 2023/12/15 2:47
 * @Version: 1.0
 */
@RestController
@RequestMapping("/mq")
public class MqController {

    @Resource
    private ProducerService producerService;

    @PostMapping("/sendMsg")
    public Response<String> sendMsg(String message){
        producerService.sendMsg(message);

        return Response.success("发送消息成功");
    }

    @PostMapping("/sendObjectMsg")
    public Response<String> sendObjectMsg(@RequestBody User user){
        producerService.sendObjectMsg(user);

        return Response.success("发送消息成功");
    }

}