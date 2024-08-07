//package com.mzl.insta360demo.outgoing.rabbitmq.consumer;
//
//import com.mzl.insta360demo.outgoing.rabbitmq.RabbitMessageChannel;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.messaging.SubscribableChannel;
//
///**
// * @Description: RabbitMQ测试消费者
// * @Author: mzl
// */
//public interface MyRabbitmqTestConsumer {
//
//    String CHANNEL = RabbitMessageChannel.MY_RABBITMQ_TEST_INPUT;
//
//    /**
//     * 获取消费者
//     *
//     * @return
//     */
//    @Input(CHANNEL)
//    SubscribableChannel input();
//
//}