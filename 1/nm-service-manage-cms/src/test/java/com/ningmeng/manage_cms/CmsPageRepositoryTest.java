package com.ningmeng.manage_cms;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Created by 周周 on 2020/2/11.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest{

    @Autowired
    private CmsPageRepository cmsPageRepository;

     @Test   //查询全部
    public void findAll(){
         List<CmsPage> list = cmsPageRepository.findAll();
          for(CmsPage cmsPage:list){
              System.out.println(cmsPage.getPageName());
          }
     }

     @Test  //分页查询
    public void testFindPage(){
        int page=0;
        int size=10;
         PageRequest pageRequest =PageRequest.of(page,size);
         Page<CmsPage> all = cmsPageRepository.findAll(pageRequest);
         System.out.println(all.getContent());
     }

     //添加方法
    @Test
    public void save(){
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("1");
        cmsPage.setDataUrl("1");
        cmsPage.setPageId("1");
        //如果这里写的是Null,代表让mongo自动生成id

        cmsPageRepository.save(cmsPage);
    }

    //删除放
    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("1");
    }

    //修改
    @Test
    public void testUpdate(){
        Optional<CmsPage> optional = cmsPageRepository.findById("1");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面01");
            cmsPageRepository.save(cmsPage);
        }
    }

    @Test
    public void testFindByName(){

    }

    @Test
    public void testFindAll(){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageName",ExampleMatcher.GenericPropertyMatchers.contains());
        exampleMatcher = exampleMatcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.startsWith());
        CmsPage cmsPage=new CmsPage();

        cmsPage.setPageAliase("课程");
        cmsPage.setPageName("4");
        Example<CmsPage> example=Example.of(cmsPage,exampleMatcher);
        PageRequest pageRequest=new PageRequest(0,10);
        Page<CmsPage> pageAll = cmsPageRepository.findAll(example,pageRequest);
        System.out.println(pageAll.getContent());

    }

}
