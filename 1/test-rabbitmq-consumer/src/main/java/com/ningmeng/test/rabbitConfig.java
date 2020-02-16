package com.ningmeng.test;

import org.mapstruct.Qualifier;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 周周 on 2020/2/14.
 */
@Configuration
public class rabbitConfig {

    //交换器名字
    public static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
    //队列名字
    public static final String QUEUE_INFORM_SMS="queue_inform_sms";
    //队列email
    public static final String QUEUE_INFORM_BMAIL="queue_inform_email";

    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
         return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_INFORM_BMAIL)
    public Queue QUEUE_INFORM_SMS(){
        Queue queue = new Queue(QUEUE_INFORM_BMAIL);
        return queue;
    }

    /**
     *
     绑定队列到交换机 */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange,
                                            @Qualifier(QUEUE_INFORM_SMS) Queue queue) {
        
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
    }

        @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange,
                                            @Qualifier(QUEUE_INFORM_BMAIL) Queue queue) {
        
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
    }
}
