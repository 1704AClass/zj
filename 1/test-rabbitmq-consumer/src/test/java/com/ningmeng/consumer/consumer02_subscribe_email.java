package com.ningmeng.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by 周周 on 2020/2/14.
 */
public class consumer02_subscribe_email {
    //发送邮件
    private static final String QUEUE_INFORM_EMAIL="queue_inform_eamil";
    //fanout类型的交换器
    private static final String EXCHANG_FANOUT_INFORM="exchang_fanout_inform";

    public static void main(String[] args){
        try {
            //创建初始化连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            //浏览器管理页面使用端口 15672 , 后台使用端口 5672
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            //默认虚拟机名称为 "/",虚拟机相当于一个独立的服务器

            //创建连接
            Connection connection = factory.newConnection();
            //每个连接可以创建多个通道，每个通道代表一个会话任务
            Channel channel = connection.createChannel();
            //声明队列 如果rabbit中没有此队列就自动创建
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);

            //交换机声明
            channel.exchangeDeclare(EXCHANG_FANOUT_INFORM, BuiltinExchangeType.FANOUT);

            //交换机和队列板顶
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANG_FANOUT_INFORM,"");

            //消费消息方法
            Consumer consumer = new DefaultConsumer(channel){

                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    //交换机
                    String exchange = envelope.getExchange();
                    String roukey = envelope.getRoutingKey();
                    //消息id
                    long deliveryTag = envelope.getDeliveryTag();
                    String str = new String(body,"utf-8");
                    System.out.println("receivemessage.."+str);
                }
            };
            //监听交换机
            channel.basicConsume(QUEUE_INFORM_EMAIL,true,consumer);
        }catch (Exception e){

        }
    }
}
