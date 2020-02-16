package com.ningmeng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.domain.cms.response.CmsPageResult;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_cms.config.RabbitmqConfig;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by 周周 on 2020/2/12.
 */
@Service
public class CmsPageService {
   @Autowired
    private CmsPageRepository cmsPageRepository;
   @Autowired
   private RabbitTemplate rabbitTemplate;

   //发布页面方法
    public ResponseResult postPage(String pageId){
        boolean flag=createHtml();
        if(!flag){
            /*CustomExceptionCast.cast(CommonCode.FAIL);*/
            System.out.println("操作失败");
        }
        //查询数据库
        CmsPage cmsPage=this.findById(pageId);
        if(cmsPage==null){
            System.out.println("操作失败");
        }
        Map<String,String> mapMsg=new HashMap<>();
        mapMsg.put("pageId",pageId);
        String msg= JSON.toJSONString(mapMsg);
        //获取站点id作为routingkey
        String siteId=cmsPage.getSiteId();
        //发送json
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,msg);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //创建静态页面
    private boolean createHtml(){
        //成功
        System.out.println("执行页面静态化程序，保存静态化文件完成.......");
        return true;
    }

   //删除方法
    public ResponseResult delete(String id){
        CmsPage one = this.findById(id);
        if(one!=null){
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

   //修改方法
    public CmsPageResult update(CmsPage cmsPage){
        //先查询对象是否存在，不存在提示失败，存在进行修改
        CmsPage cmsPage1=this.findById(cmsPage.getPageId());
        if(cmsPage1!=null){
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,null);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

   //根据id查找
    public CmsPage findById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

   //添加方法
    public CmsPageResult add(CmsPage cmsPage){
        CmsPage cmsPage1 = cmsPageRepository.save(cmsPage);
        if(cmsPage1==null){
            cmsPage.setPageId(null);///设置自动增加id
            cmsPageRepository.save(cmsPage);
            //返回结果
            CmsPageResult cmsPageResult=new CmsPageResult(CommonCode.SUCCESS,cmsPage);
            return  cmsPageResult;
        }
        return new CmsPageResult(CommonCode.SUCCESS,null);
    }

   //根据条件查询全部数据
   public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
       if(queryPageRequest == null){
           queryPageRequest = new QueryPageRequest();
       }
       //分页查询
       if(page <= 0){
           page = 1;
       }
       page = page -1 ;
       PageRequest pageRequest = PageRequest.of(page,size);

       //构建条件够起
       CmsPage cmsPage=new CmsPage();
       ExampleMatcher exampleMatcher=ExampleMatcher.matching();
       if(queryPageRequest.getPageAliase()!=null){
            exampleMatcher = exampleMatcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
       }
       if(queryPageRequest.getSiteId()!=null){
           cmsPage.setSiteId(queryPageRequest.getSiteId());
       }
       if(queryPageRequest.getTemplateId()!=null){
          cmsPage.setTemplateId(queryPageRequest.getTemplateId());
       }
       //构建条件
       Example<CmsPage> example=Example.of(cmsPage,exampleMatcher);
       Page<CmsPage> listAll = cmsPageRepository.findAll(example,pageRequest);
       //封装返回条件
       QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
       cmsPageQueryResult.setList(listAll.getContent());
       cmsPageQueryResult.setTotal(listAll.getTotalElements());
       //封装返回对象
       QueryResponseResult queryResponseResult=new QueryResponseResult(CommonCode.SUCCESS,cmsPageQueryResult);
       return queryResponseResult;
   }
}
