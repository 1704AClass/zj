package mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by 周周 on 2020/2/14.
 */
@Component
public class receiveHandler {

    @RabbitListener(queues = )
}
