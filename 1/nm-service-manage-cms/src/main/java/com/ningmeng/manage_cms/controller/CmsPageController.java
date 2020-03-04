package com.ningmeng.manage_cms.controller;

import cmsapi.CmsPageControllerApi;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.domain.cms.response.CmsPostPageResult;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 周周 on 2020/2/11.
 */
@RestController
@RequestMapping("/cms")
public class CmsPageController implements CmsPageControllerApi{

    @Autowired
    private CmsPageService cmsPageService;


    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int
                size,QueryPageRequest queryPageRequest){
         return cmsPageService.findList(page,size,queryPageRequest);
        }

        //添加方法
    @Override
    @PostMapping("/add")
    public ResponseResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    //根据id查询
    @Override
    @GetMapping("/findById/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return cmsPageService.findById(id);
    }

    //修改
    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody CmsPage cmsPage) {
        return cmsPageService.update(cmsPage);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return cmsPageService.postPage(pageId);
    }

    @Override
    @PostMapping("add1")
    public ResponseResult add1(CmsPage cmsPage) {
        return cmsPageService.add1(cmsPage);
    }

    @Override
    @DeleteMapping("/delete1/{id}")
    public ResponseResult delete1(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }

    @Override
    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return cmsPageService.postPageQuick(cmsPage);
    }

    //页面物理路径=站点物理路径+页面名称
    @GetMapping("/preview/{id}")
    public String preview(@PathVariable("id") String cmsPageId) {
       /* try {
             //执行经阿嚏华
            String pageHtml = cmsPageService.preview(cmsPageId);
            //通过response对象将内容输出

        } catch (Exception e) {
            System.out.println("失败");
        }*/
       return null;
    }
}
