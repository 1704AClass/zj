package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 周周 on 2020/3/5.
 */
public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia,String>{

    List<TeachplanMedia> findByCourseId(String courseId);
}
