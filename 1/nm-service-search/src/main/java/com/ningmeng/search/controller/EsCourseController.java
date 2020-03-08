package com.ningmeng.search.controller;

import com.ningmeng.framework.domain.course.CoursePub;
import com.ningmeng.framework.domain.course.TeachplanMediaPub;
import com.ningmeng.framework.domain.search.CourseSearchParam;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import searchApi.EsCourseControllerApi;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 周周 on 2020/3/1.
 */
@RestController
@RequestMapping("/search")
public class EsCourseController implements EsCourseControllerApi{

    @Autowired
    EsCourseService esCourseService;

    @Override
    @GetMapping("/course")
    public QueryResponseResult list(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody CourseSearchParam courseSearchParam) throws IOException {
        return esCourseService.list(page,size,courseSearchParam);
    }

    @Override   //根据id查询课程信息
    @GetMapping("/getall/{id}")
    public Map<String, CoursePub> getall(@PathVariable("id") String id) {
        return esCourseService.getall(id);
    }

    @Override
    @GetMapping("/getmedia/{teachplanId}")
    public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId) {
        //将课程计划id放在数组中，为调用service作准备
        String[] teachplanIds = new String[]{teachplanId};
        //通过service查询ES获取课程媒资信息
        QueryResponseResult mediaPubQueryResponseResult = esCourseService.getmedia(teachplanIds);
        QueryResult<TeachplanMediaPub> queryResult = mediaPubQueryResponseResult.getQueryResult();
        if(queryResult!=null && queryResult.getList()!=null && queryResult.getList().size()>0){
            //返回课程计划对应课程媒资
            return queryResult.getList().get(0);
        }return new TeachplanMediaPub();
    }
}
