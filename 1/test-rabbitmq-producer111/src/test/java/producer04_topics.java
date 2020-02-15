import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

/**
 * Created by 周周 on 2020/2/14.
 */
public class producer04_topics {
    //发送邮件
    private static final String QUEUE_INFORM_EMAIL="queue_inform_email";
    //发送短信
    private static final String QUEUE_INFORM_SMS="queue_inform_sms";
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
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);

            //email
            for(int i = 0; i<5;i++){
                //消息发布方法
                String manage = "小明你好，你的邮件";
                System.out.println("send:"+manage+",时间:"+new Date());
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.email",null,manage.getBytes());

            }
            //短信
            for(int i = 0; i<5;i++){
                //消息发布方法
                String manage = "小明你好，你的邮件";
                System.out.println("send:"+manage+",时间:"+new Date());
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.sms",null,manage.getBytes());

            }
            //同时发送短信和邮件
            for(int i=0;i<5;i++){
                String message = "sms and email inform to user"+i;
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.sms.email",null,message.getBytes());
                System.out.println("send:"+message+",时间:"+new Date());

            }
            //非空判断
            channel.close();;
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}
