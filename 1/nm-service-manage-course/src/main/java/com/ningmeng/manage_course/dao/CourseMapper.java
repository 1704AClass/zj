package com.ningmeng.manage_course.dao;

import com.github.pagehelper.Page;
import com.ningmeng.framework.domain.course.CourseBase;
import com.ningmeng.framework.domain.course.ext.CourseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   CourseBase findCourseBaseById(String id);

   //分页查询  companyId公司id
   Page<CourseInfo> findCourseListPage(String companyId);
}
