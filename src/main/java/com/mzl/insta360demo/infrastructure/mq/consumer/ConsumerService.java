package com.mzl.insta360demo.infrastructure.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ConsumerService
 * @Description: 消费者服务
 * @Author: mzl
 * @CreateDate: 2023/12/15 2:36
 * @Version: 1.0
 */
@Service
@Slf4j
// @RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-consumer-group")
public class ConsumerService implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("消费者接收到的消息为: {}", message);
    }

}