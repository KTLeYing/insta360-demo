//package com.mzl.insta360demo.messagehandler;
//
//import com.alibaba.fastjson.JSON;
//import com.mzl.insta360demo.outgoing.rabbitmq.consumer.MyRabbitmqTestConsumer;
//import com.mzl.insta360demo.outgoing.rabbitmq.event.MyRabbitmqTestEvent;
//import com.rabbitmq.client.Channel;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//import java.util.Objects;
//
///**
// * @Description: RabbitMq测试处理消费者
// * @Author: mzl
// */
//@Component
//@EnableBinding(value = {MyRabbitmqTestConsumer.class})
//public class MyRabbitMqTestHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(MyRabbitMqTestHandler.class);
//
//    @StreamListener(MyRabbitmqTestConsumer.CHANNEL)
//    public void receive(Message<String> message,
//                        @Header(name = AmqpHeaders.CHANNEL, required = false) Channel channel,
//                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
//
//        if (Objects.isNull(message) || StringUtils.isEmpty(message.getPayload())) {
//            log.error("RabbitMq测试处理消费者接受到空消息...");
//            channel.basicReject(deliveryTag, false);
//            return;
//        }
//
//        String messagePayload = message.getPayload();
//        log.info("RabbitMq测试处理消费者接受到了消息, message={}", messagePayload);
//
//        MyRabbitmqTestEvent myRabbitmqTestEvent = JSON.parseObject(messagePayload, MyRabbitmqTestEvent.class);
//        log.info("RabbitMq测试处理消费者正在处理消息, event={}", myRabbitmqTestEvent);
//
//        // 休息5s，模拟消息处理核心逻辑
//        Thread.sleep(5000);
//
//        log.info("RabbitMq测试处理消费者处理完成");
//
//        channel.basicAck(deliveryTag, false);
//    }
//
//}