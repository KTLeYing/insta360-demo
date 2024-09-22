package com.mzl.insta360demo.messagehandler3;

import com.alibaba.fastjson.JSON;
import com.mzl.insta360demo.outgoing.kafka.KafkaTopicConfig;
import com.mzl.insta360demo.outgoing.kafka.event.MyKafkaTestEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description: Kafka测试消息队列处理器
 * @Author: mzl
 */
@Component
public class MyKafkaTestHandler {

    private static final Logger log = LoggerFactory.getLogger(MyKafkaTestHandler.class);

    @KafkaListener(topics = {KafkaTopicConfig.MY_KAFKA_TEST_TOPIC}, groupId = "kafka_test_group")
    public void receive(String message) throws InterruptedException {
        if (StringUtils.isEmpty(message)) {
            log.error("MyKafka测试处理消费者接受到空消息...");
            return;
        }
        log.info("MyKafka测试处理消费者接受到了消息, message={}", message);

        MyKafkaTestEvent myKafkaTestEvent = JSON.parseObject(message, MyKafkaTestEvent.class);
        log.info("MyKafka测试处理消费者正在处理消息, event={}", myKafkaTestEvent);

        // 休息5s，模拟消息处理核心逻辑
        Thread.sleep(5000);

        log.info("MyKafka测试处理消费者处理完成");
    }

}