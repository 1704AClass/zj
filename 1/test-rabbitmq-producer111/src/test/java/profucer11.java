import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

/**
 * Created by 周周 on 2020/2/14.
 */
public class profucer11 {
    private  static final String QUEUE = "helloword";
    public static void main(String[] args){
        try {
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
            //声明队列 如果rabbit没有此队列中自动创建
            channel.queueDeclare(QUEUE,true,false,false,null);
            /**
             * 消息发布方法
             */
            String manage = "小明你好";
            System.out.println("send:"+manage+",时间"+new Date());
            channel.basicPublish("",QUEUE,null,manage.getBytes());

        }catch (Exception e){

        }
    }
}
