package com.mzl.insta360demo.outgoing.rocketmq.event;//package com.mzl.insta360demo.outgoing.rabbitmq.event;

import com.alibaba.fastjson.JSON;

/**
 * @Description: RocketMQ测试消息事件
 * @Author: mzl
 */
public class MyRocketmqTestEvent {

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
        MyRocketmqTestEvent myRocketmqTestEvent = new MyRocketmqTestEvent();
        myRocketmqTestEvent.setUserId(userId);
        myRocketmqTestEvent.setType(type);
        myRocketmqTestEvent.setNum(num);

        return JSON.toJSONString(myRocketmqTestEvent);
    }

    @Override
    public String toString() {
        return "MyRocketmqTestEvent{" +
                "userId=" + userId +
                ", type='" + type + '\'' +
                ", num=" + num +
                '}';
    }
}