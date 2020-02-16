package com.ningmeng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 周周 on 2020/2/16.
 * 启动类，自动运行
 */
@Configuration
public class rabbitmqConfig {
    //队列bean的名称
    public static final String QUEUE_CMS_POSTPAGE="queue_cms_postpage";
    //交换机名称
    public static final String EX_ROUTING_CMS_POSTPAGE="ex_routing_cms_postpage";

    @Value("${ningmeng.mq.queue}")
    public String queue_cms_postpage_name;
    @Value("${ningmeng.mq.routingkey}")
    public String routingkey;

    //交换机routin 类型
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EX_ROUTING_CMS_POSTPAGE(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();

    }
    //队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE(){
        //队列名称
        Queue queue = new Queue(queue_cms_postpage_name);
        return queue;
    }
    //绑定交换机和队列
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange,
                                            @Qualifier(QUEUE_CMS_POSTPAGE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(routingkey).noargs();
    }
}
