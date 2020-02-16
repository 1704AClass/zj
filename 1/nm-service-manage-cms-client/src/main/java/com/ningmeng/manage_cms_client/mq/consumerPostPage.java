package com.ningmeng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.ningmeng.manage_cms_client.service.CmsPageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 周周 on 2020/2/16.
 */
@Component
public class consumerPostPage {
    /*private static final Logger LOGGER = LoggerFactory.getLogger(consumerPostPage.class);*/

    @Autowired
    private CmsPageService cmsPageService;

      @RabbitListener(queues = {"${ningmeng.mq.queue}"})
    //是json字符串
    public void postPage(String msg){
         //解析信息
          Map map = JSON.parseObject(msg,Map.class);
          /*Logger.info(msg.toString());*/
          //页面物理路径=站点物理路径+页面物理路径+页面名称
          cmsPageService.TestRabbit((String)map.get("pageId"));

    }
}
