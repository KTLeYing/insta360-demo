package com.mzl.insta360demo.messagehandler2;

import com.alibaba.fastjson.JSON;
import com.mzl.insta360demo.outgoing.rocketmq.RocketMessageTopic;
import com.mzl.insta360demo.outgoing.rocketmq.event.MyRocketmqTestEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 创建订单完成通知处理消费者
 * @Author: mzl
 * consumeMode：消费者的消费模式，CONCURRENTLY(并发消费): 多个线程并发消费，无序；ORDERLY(顺序消费): 单个线程顺序消费，有序【默认CONCURRENTLY】
 * messageModel：CLUSTERING(集群模式): 一条消息只能被一个消费者实例消费，适合负载均衡；BROADCASTING(广播模式): 每个消费者实例都会收到消息, 即一条消息可以被每个消费者实例处理【默认CLUSTERING】
 */
@Component
@RocketMQMessageListener(topic = RocketMessageTopic.CREATE_ORDER_FINISH_NOTICE_TOPIC, consumerGroup = "create-order-finish-notice-consumer-group"
        , consumeMode = ConsumeMode.CONCURRENTLY, messageModel = MessageModel.CLUSTERING)
public class CreateOrderFinishNoticeHandler implements RocketMQListener<String> {

    private static final Logger log = LoggerFactory.getLogger(CreateOrderFinishNoticeHandler.class);

    @Override
    public void onMessage(String message) {
        if (StringUtils.isEmpty(message)) {
            log.error("创建订单完成通知处理消费者接受到空消息...");
            return;
        }

        log.info("创建订单完成通知处理消费者接受到了消息, message={}", message);

        MyRocketmqTestEvent myRocketmqTestEvent = JSON.parseObject(message, MyRocketmqTestEvent.class);
        log.info("创建订单完成通知处理消费者正在处理消息, event={}", myRocketmqTestEvent);

        // 休息5s，模拟消息处理核心逻辑
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("创建订单完成通知处理消费者处理失败...message={}", message);
        }

        log.info("创建订单完成通知处理消费者处理完成");
    }

}