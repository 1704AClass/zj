import com.rabbitmq.client.*;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by 周周 on 2020/2/14.
 */
public class producer05_header {
    //发送邮件
    private static final String QUEUE_INFORM_EMAIL="queue_inform_email";
    //发送短信
    private static final String QUEUE_INFORM_SMS="queue_inform_sms";
    //heards类型的交换器
    private static final String EXCHANGE_HEARDS_INFORM="exchange_heards_inform";

    public static void main(String[] args){

        //发送邮件
        Map<String,Object> headers_email = new Hashtable<String,Object>();
        headers_email.put("inform_type","email");
        //发送短信
        Map<String,Object> headers_sms = new Hashtable<String,Object>();
        headers_sms.put("inform_type","sms");

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

            //交换机声明
            channel.exchangeDeclare(EXCHANGE_HEARDS_INFORM, BuiltinExchangeType.HEADERS);

            //和队列绑定交换机
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_HEARDS_INFORM,"",headers_email);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_HEARDS_INFORM,"",headers_sms);

            //email
            for(int i = 0; i<5;i++){
                //消息发布方法
                Map<String,Object> headers = new Hashtable<String,Object>();
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                headers.put("inform_type","email");
                properties.headers(headers);
                String manage = "小明你好，你的邮件";
                System.out.println("send:"+manage+",时间:"+new Date());
                channel.basicPublish(EXCHANGE_HEARDS_INFORM,"",properties.build(),manage.getBytes());

            }
            //短信
            for(int i = 0; i<5;i++){
                //消息发布方法
                Map<String,Object> headers = new Hashtable<String,Object>();
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                headers.put("inform_type","sms");
                properties.headers(headers);
                String manage = "小明你好，你的邮件";
                System.out.println("send:"+manage+",时间:"+new Date());
                channel.basicPublish(EXCHANGE_HEARDS_INFORM,"",properties.build(),manage.getBytes());

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
