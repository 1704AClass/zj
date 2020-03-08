package com.ningmeng.learning.controller;

import com.ningmeng.framework.domain.learning.response.GetMediaResult;
import com.ningmeng.learning.service.LearningService;
import learningApi.CourseLearningControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 周周 on 2020/3/8.
 */
@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi{

    @Autowired
    LearningService learningService;

    @Override
    @GetMapping("/getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getmedia(@PathVariable("courseId") String courseId,
                                    @PathVariable("teachplanId") String teachplanId) {
        return learningService.getMedia(courseId,teachplanId);
    }
}
