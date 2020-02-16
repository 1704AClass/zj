package com.ningmeng.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 周周 on 2020/2/14.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class rabbitmq {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendByTopics(){
        for(int i=0;i<5;i++){
            String message="sms email inform to user";

            System.out.println("sendMessageis:"+message+",");
        }
    }
}
