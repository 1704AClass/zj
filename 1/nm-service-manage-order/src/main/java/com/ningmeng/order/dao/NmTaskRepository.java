package com.ningmeng.order.dao;

import com.github.pagehelper.Page;
import com.ningmeng.framework.domain.task.NmTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Date;

/**
 * Created by 周周 on 2020/3/18.
 */
public interface NmTaskRepository  extends JpaRepository<NmTask,String>{

    //取出指定时间之前的记录
    Page<NmTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    //更新任务处理时间
    /*@Modifying
    @Query("update NmTask t set t.updateTime = :updateTime where t.id = :id ")
    public intupdateTaskTime(@Param(value = "id") String id, @Param(value = "updateTime") DateupdateTime);*/

    //使用乐观锁方式校验任务id和版本号是否匹配，匹配则版本号加1
    /*@Modifying
    @Query("update NmTask t set t.version = :version+1 where t.id = :id and t.version = :version")
            public int updateTaskVersion(@Param(value = "id") String id,@Param(value = "version") int version);*/


}
