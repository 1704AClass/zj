package com.ningmeng.order.service;

import com.ningmeng.framework.domain.task.NmTask;
import com.ningmeng.order.dao.NmTaskRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 周周 on 2020/3/18.
 */
@Service
public class TaskService {

    @Autowired
    NmTaskRepository nmTaskRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //取出前n条任务,取出指定时间之前处理的任务
    public List<NmTask> findTaskList(Date updateTime, int n){
        //设置分页参数，取出前n 条记录
       /* Pageable pageable = new PageRequest(0, n);
        Page<NmTask> nmTasks = nmTaskRepository.findByUpdateTimeBefore(pageable,updateTime);
        return nmTasks.getContent();*/
       return null;
    }

    /**
     * //发送消息
     * @param nmTask 任务对象
     * @param ex 交换机id
     * @param routingKey
     */
    //@Transactional
   /* public void publish(NmTask nmTask,String ex,String routingKey){
        //查询任务
        Optional<NmTask> taskOptional = nmTaskRepository.findById(taskId);
        if(taskOptional.isPresent()){
            NmTasknmTask = taskOptional.get();
            //String exchange, String routingKey, Object object
            rabbitTemplate.convertAndSend(ex,routingKey,nmTask);
            //更新任务时间为当前时间
            nmTask.setUpdateTime(new Date());
            nmTaskRepository.save(nmTask);
        }*/

   /* @Transactional
    public int getTask(String taskId,int version){
        int i = nmTaskRepository.updateTaskVersion(taskId, version);
        return i;
    }*/

    //完成选课
   /* @Transactional
    public ResponseResult addcourse(String userId, String courseId, String valid, Date
            startTime, Date endTime, NmTask nmTask) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        if (StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_USERISNULL);
        }
        if (xcTask == null || StringUtils.isEmpty(xcTask.getId())) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_TASKISNULL);
        }
//查询历史任务
        Optional<NmTaskHis> optional = nmTaskHisRepository.findById(nmTask.getId());
        if (optional.isPresent()) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        NmLearningCoursenmLearningCourse =
                nmLearningCourseRepository.findNmLearningCourseByUserIdAndCourseId(userId, courseId);
        if (nmLearningCourse == null) {//没有选课记录则添加
            nmLearningCourse = new NmLearningCourse();
            nmLearningCourse.setUserId(userId);
            nmLearningCourse.setCourseId(courseId);
            nmLearningCourse.setValid(valid);
            nmLearningCourse.setStartTime(startTime);
            nmLearningCourse.setEndTime(endTime);
            nmLearningCourse.setStatus("501001");
            nmLearningCourseRepository.save(nmLearningCourse);
        } else {//有选课记录则更新日期
            nmLearningCourse.setValid(valid);
            nmLearningCourse.setStartTime(startTime);
            nmLearningCourse.setEndTime(endTime);
            nmLearningCourse.setStatus("501001");
            nmLearningCourseRepository.save(nmLearningCourse);
        }
//向历史任务表播入记录
        Optional<NmTaskHis> optional = nmTaskHisRepository.findById(nmTask.getId());
        if (!optional.isPresent()) {
//添加历史任务
            NmTaskHis nmTaskHis = new NmTaskHis();
            BeanUtils.copyProperties(nmTask, nmTaskHis);
            nmTaskHisRepository.save(nmTaskHis);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }*/
}
