package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 周周 on 2020/2/21.
 */
public interface CoursePicRepository extends JpaRepository<CoursePic,String>{

    //删除成功返回1否则返回0
    long deleteByCourseid(String courseId);
}
