package com.mzl.insta360demo.outgoing.rabbitmq1.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: RabbitMQ测试队列配置
 * @Author: mzl
 */
@Configuration
public class MyRabbitmqTestMqConfig {

    /**
     * 正常队列
     */
    public static final String MY_RABBITMQ_TEST_QUEUE = "my_rabbitmq_test_queue";

    /**
     * 正常交换机
     */
    public static final String MY_RABBITMQ_TEST_EXCHANGE = "my_rabbitmq_test_exchange";

    /**
     * 死信队列
     */
    public static final String DEAD_MY_RABBITMQ_TEST_QUEUE = "dead_my_rabbitmq_test_queue";

    /**
     * 死信交换机
     */
    public static final String DLX = "DLX";

    /**
     * 正常队列，绑定到死信交换机
     *
     * @return 队列
     */
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable(MY_RABBITMQ_TEST_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", DEAD_MY_RABBITMQ_TEST_QUEUE)
                .build();
    }

    /**
     * 正常交换机
     *
     * @return 交换机
     */
    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(MY_RABBITMQ_TEST_EXCHANGE);
    }

    /**
     * 死信队列
     *
     * @return 队列
     */
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(DEAD_MY_RABBITMQ_TEST_QUEUE)
                .withArgument("x-message-ttl", 604800000)
                .build();
    }

    /**
     * 死信交换机
     *
     * @return 交换机
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DLX);
    }

    /**
     * 正常队列绑定到正常交换机
     *
     * @return 绑定器
     */
    @Bean
    public Binding normalBinding() {
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with(MY_RABBITMQ_TEST_QUEUE);
    }

    /**
     * 死信队列绑定到死信交换机
     *
     * @return 绑定
     */
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(DEAD_MY_RABBITMQ_TEST_QUEUE);
    }

}