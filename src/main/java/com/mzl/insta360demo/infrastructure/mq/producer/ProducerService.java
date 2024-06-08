package com.mzl.insta360demo.infrastructure.mq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: ProducerService
 * @Description: 生产者服务
 * @Author: mzl
 * @CreateDate: 2023/12/20 11:54
 * @Version: 1.0
 */
@Service
@Slf4j
public class ProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 生产者发送消息
     * @param message 消息体
     */
    public void sendMsg(String message) {
        rocketMQTemplate.convertAndSend("test-topic", message);
        log.info("生产者发送的消息为: {}", message);
    }

    /**
     * 生产者发送消息
     * @param message 消息体
     */
    public void sendObjectMsg(Object message) {
        rocketMQTemplate.convertAndSend("test-topic", message);
        String jsonMessage = JSON.toJSONString(message);
        log.info("生产者发送的消息为: {}", jsonMessage);
    }

}