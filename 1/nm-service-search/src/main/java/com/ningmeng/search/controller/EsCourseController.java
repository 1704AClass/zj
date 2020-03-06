package com.ningmeng.search.controller;

import com.ningmeng.framework.domain.course.CoursePub;
import com.ningmeng.framework.domain.search.CourseSearchParam;
import com.ningmeng.framework.model.response.QueryResponseResult;
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
}
