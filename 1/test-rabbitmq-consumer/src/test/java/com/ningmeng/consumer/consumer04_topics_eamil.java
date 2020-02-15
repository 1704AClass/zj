package com.ningmeng.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by 周周 on 2020/2/14.
 */
public class consumer04_topics_eamil {
    //发送邮件
    private static final String QUEUE_INFORM_EMAIL="queue_inform_email";
    //topics类型的交换器
    private static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";

    public static void main(String[] args){
        try {
            //创建舒适化连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            //浏览器管理页面使用端口 15672 , 后台使用端口 5672
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            //默认虚拟机名称为 "/",虚拟机相当于一个独立的服务器
            Connection connection = factory.newConnection();

            //生产者之间建立连接
            //每个连接可以创建多个通道，每隔通道代表一个会话任务
            Channel channel = connection.createChannel();

            //声明队列
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);

            //交换机声明
            channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);

            //交换机和队列绑定
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_TOPICS_INFORM,"inform.#.email.#");


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
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}
