package com.ningmeng.manage_cms.controller;

import cmsapi.CmsPageControllerApi;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
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
    @GetMapping("/get/{id}")
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
    public ResponseResult post(String pageId) {
        return cmsPageService.postPage(pageId);
    }
}
