package com.mzl.insta360demo.outgoing.rabbitmq1.event;//package com.mzl.insta360demo.outgoing.rabbitmq.event;

import com.alibaba.fastjson.JSON;

/**
 * @Description: RabbitMQ测试消息事件
 * @Author: mzl
 */
public class MyRabbitmqTestEvent {

    private Long userId;

    private String type;

    private Integer num;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public static String buildMessage(Long userId, String type, Integer num){
        MyRabbitmqTestEvent myRabbitmqTestEvent = new MyRabbitmqTestEvent();
        myRabbitmqTestEvent.setUserId(userId);
        myRabbitmqTestEvent.setType(type);
        myRabbitmqTestEvent.setNum(num);

        return JSON.toJSONString(myRabbitmqTestEvent);
    }

    @Override
    public String toString() {
        return "MyRabbitmqTestEvent{" +
                "userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", num=" + num +
                '}';
    }
}