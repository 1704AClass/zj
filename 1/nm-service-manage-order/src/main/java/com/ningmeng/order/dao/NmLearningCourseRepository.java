package com.ningmeng.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 周周 on 2020/3/18.
 */
public interface NmLearningCourseRepository extends JpaRepository<NmLearningCourseRepository,String>{

    //根据用户和课程查询选课记录，用于判断是否添加选课
    //NmLearningCourse findNmLearningCourseByUserIdAndCourseId(String userId, String courseId);
}
