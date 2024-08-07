package com.mzl.insta360demo.controller;

import com.mzl.insta360demo.base.Response;
import com.mzl.insta360demo.entity.User;
import com.mzl.insta360demo.infrastructure.mq.producer.ProducerService;
import com.mzl.insta360demo.outgoing.rabbitmq1.config.MyRabbitmqTestMqConfig;
import com.mzl.insta360demo.outgoing.rabbitmq1.event.MyRabbitmqTestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
//@EnableBinding(value = {MyRabbitmqTestSender.class})
public class MqController {

    private static final Logger log = LoggerFactory.getLogger(MqController.class);

    @Resource
    private ProducerService producerService;

//    @Autowired
//    private MyRabbitmqTestSender myRabbitmqTestSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

//    @PostMapping("/myRabbitMqTest")
//    public Response<String> myRabbitMqTest(){
//        String message = MyRabbitmqTestEvent.buildMessage(100L, "normal", 10);
//        myRabbitmqTestSender.getSender().send(MessageBuilder.withPayload(message).build());
//        log.info("我的RabbitMQ测试生产者发送了消息...message={}", message);
//
//        return Response.success("发送消息成功");
//    }

    @PostMapping("/myRabbitMqTest")
    public Response<String> myRabbitMqTest(){
        String message = MyRabbitmqTestEvent.buildMessage(100L, "normal", 10);
        rabbitTemplate.convertAndSend(MyRabbitmqTestMqConfig.MY_RABBITMQ_TEST_QUEUE, message);
        log.info("我的RabbitMQ测试生产者发送了消息...message={}", message);

        return Response.success("发送消息成功");
    }

}