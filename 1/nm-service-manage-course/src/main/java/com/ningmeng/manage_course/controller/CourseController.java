package com.ningmeng.manage_course.controller;

import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.domain.course.Teachplan;
import com.ningmeng.framework.domain.course.TeachplanMedia;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.domain.course.response.CoursePublishResult;
import com.ningmeng.framework.domain.course.response.CourseView;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_course.service.CourseService;
import courseapi.CourseControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 周周 on 2020/2/19.
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi{

    @Autowired
    private CourseService courseService;

    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseService.getCoruseView(id);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }

    @Override    //发布课程
    @PostMapping("/publish/{id}")
    public CoursePublishResult publish(@PathVariable("id") String id) {
        return courseService.publish(id);
    }

    @Override
    @PostMapping("/savemedia")
    public ResponseResult savemedia(@RequestBody TeachplanMedia teachplanMedia) {
        return courseService.savemedia(teachplanMedia);
    }


    @Override
    @GetMapping("/teachplan/findTeachplanList/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId){
        System.out.println(courseId);
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }
    //分页查询课程列表
    @Override
    @GetMapping("/course/findCourseListPage/{page}/{size}")
    public QueryResponseResult findCourseListPage(@PathVariable("page") int page,@PathVariable("size") int pagesize, String id) {
        return courseService.findCourseListPage(page,pagesize,id);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam(value = "courseId",required = true) String courseId,
                                       @RequestParam(value = "pic",required = true) String pic) {
       //保存课程图片
        return courseService.saveCoursePic(courseId,pic);
    }

    @Override
    @GetMapping("/coursepic/findCoursePic/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursepic(courseId);
    }

    @Override
    @DeleteMapping("/coursepic/deleteCoursePic/{courseId}")
    public ResponseResult deleteCoursePic(@PathVariable("courseId") String courseId) {
        System.out.println(courseId);
        return courseService.deleteCoursePic(courseId);
    }


}
