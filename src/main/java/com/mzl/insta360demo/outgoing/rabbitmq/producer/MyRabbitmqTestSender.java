//package com.mzl.insta360demo.outgoing.rabbitmq.producer;
//
//import com.mzl.insta360demo.outgoing.rabbitmq.RabbitMessageChannel;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//
///**
// * @Description: RabbitMQ测试生产者
// * @Author: mzl
// */
//public interface MyRabbitmqTestSender {
//
//    String CHANNEL = RabbitMessageChannel.MY_RABBITMQ_TEST_OUTPUT;
//
//    /**
//     * 获取生产者
//     *
//     * @return
//     */
//    @Output(CHANNEL)
//    MessageChannel getSender();
//
//}