package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 周周 on 2020/2/19.
 */
@Mapper
public interface TeachplanMapper {

    public TeachplanNode findTeachplanList(@Param("courseId") String courseId);
}
