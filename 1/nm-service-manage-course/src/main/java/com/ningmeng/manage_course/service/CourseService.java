package com.ningmeng.manage_course.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.response.CmsPostPageResult;
import com.ningmeng.framework.domain.course.*;
import com.ningmeng.framework.domain.course.ext.CourseInfo;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.domain.course.response.CourseCode;
import com.ningmeng.framework.domain.course.response.CoursePublishResult;
import com.ningmeng.framework.domain.course.response.CourseView;
import com.ningmeng.framework.exception.CustomExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_course.client.CmsPageClient;
import com.ningmeng.manage_course.dao.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by 周周 on 2020/2/19.
 */
@Service
public class CourseService {

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanRepository teachplanRepository;
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired   //课程
    CourseMapper courseMapper;
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    CourseMarketRepository courseMarketRepository;
    @Autowired
    CmsPageClient cmsPageClient;
    @Autowired
    CoursePubRepository coursePubRepository;
    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;

    

    //保存媒资信息
    public ResponseResult savemedia(TeachplanMedia teachplanMedia) {
        if(teachplanMedia == null){
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        //课程计划
        String teachplanId = teachplanMedia.getTeachplanId();
        //查询课程计划
        Optional<Teachplan> optional = teachplanRepository.findById(teachplanId);
        if(!optional.isPresent()){
            CustomExceptionCast.cast(CourseCode.COURSE_MEDIS_NAMEISNULL);
        }
       Teachplan teachplan = optional.get();
       //只允许为叶子结点课程计划选择视频
       String grade = teachplan.getGrade();
       if(StringUtils.isEmpty(grade) || !grade.equals("3")){
           CustomExceptionCast.cast(CourseCode.COURSE_PUBLISH_CDETAILERROR);
       }
       TeachplanMedia one = null;
       Optional<TeachplanMedia> teachplanMediaOptional = teachplanMediaRepository.findById(teachplanId);
       if(!teachplanMediaOptional.isPresent()){
           one = new TeachplanMedia();
       }else{
           one = teachplanMediaOptional.get();
       }//保存媒资信息与课程计划信息
       one.setTeachplanId(teachplanId);
       one.setCourseId(teachplanMedia.getCourseId());
       one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
       one.setMediaId(teachplanMedia.getMediaId());
       one.setMediaUrl(teachplanMedia.getMediaUrl());
       teachplanMediaRepository.save(one);
       return new ResponseResult(CommonCode.SUCCESS);
    }

    //保存CoursePub
    public CoursePub saveCoursePub(String id, CoursePub coursePub){
        /*if(StringUtils.isNotEmpty(id)){
            CustomExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }*/
        CoursePub coursePubNew = null;
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(id);
        if(coursePubOptional.isPresent()){
            coursePubNew = coursePubOptional.get();
        }
        if(coursePubNew == null){
            coursePubNew = new CoursePub();
        }
        BeanUtils.copyProperties(coursePub,coursePubNew);
        //设置主键
        coursePubNew.setId(id);
        //更新时间戳为最新时间
        coursePub.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY‐MM‐dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePub.setPubTime(date);
        coursePubRepository.save(coursePub);
        return coursePub;
    }

    //创建coursePub对象
    private CoursePub createCoursePub(String id){
        CoursePub coursePub = new CoursePub();
        coursePub.setId(id);   //先同步id
        // /基础信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if(courseBaseOptional == null){
            CourseBase courseBase = courseBaseOptional.get();
            BeanUtils.copyProperties(courseBase, coursePub);
        }
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic, coursePub);
        }
        //课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
        if(marketOptional.isPresent()){
            CourseMarket courseMarket = marketOptional.get();
            BeanUtils.copyProperties(courseMarket, coursePub);
        }
        //课程计划
        TeachplanNode teachplanNode = teachplanMapper.findTeachplanList(id);
        //将课程计划转成json
        String teachplanString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(teachplanString);
        return coursePub;
    }

    //课程发布
    @Transactional
    public CoursePublishResult publish(String courseId){
        //课程信息
        CourseBase one = this.findCourseBaseById(courseId);
        //发布课程详情页面
        CmsPostPageResult cmsPostPageResult = publish_page(courseId);
        if(!cmsPostPageResult.isSuccess()){
            CustomExceptionCast.cast(CommonCode.FAIL);
         }
        //更新课程状态
        CourseBase courseBase = saveCoursePubState(courseId);
        //更新课程状态
        this.saveCoursePubState(courseId);
        // 更新索引库
        CoursePub coursePub = createCoursePub(courseId);
        this.saveCoursePub(courseId,coursePub);

        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }
    //更新课程发布状态
    private CourseBase saveCoursePubState(String courseId){
        CourseBase courseBase = this.findCourseBaseById(courseId);
        //更新发布状态
        courseBase.setStatus("202002");
        CourseBase save = courseBaseRepository.save(courseBase);
        return save;
    }
    //发布课程正式页面
    public CmsPostPageResult publish_page(String courseId){
        CourseBase one = this.findCourseBaseById(courseId);
        // /发布课程预览页面
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);
        //课程预览站点
        // 模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(courseId+".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+courseId);
        //发布页面
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        return cmsPostPageResult;
    }

    //根据Id查询课程基本信息
    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if(baseOptional.isPresent()){
           CourseBase courseBase = baseOptional.get();
           return courseBase;
        }
        CustomExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        return null;
    }
    //课程预览
    public CoursePublishResult preview(String courseId){
        CourseBase one = this.findCourseBaseById(courseId);
        //发布课程预览页面
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);
       //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(courseId+".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+courseId);
        // /远程请求cms保存页面信息
        ResponseResult cmsPageResult = cmsPageClient.add(cmsPage);
        if(!cmsPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        //页面id
        String pageId = cmsPageResult.getMessage();
        CmsPage cmsPage1 = JSON.parseObject(pageId,CmsPage.class);
        if(cmsPage1==null){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        //页面url
        String pageUrl = previewUrl+cmsPage1.getPageId();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    //课程视图查询
    public CourseView getCoruseView(String id) {
        CourseView courseView = new CourseView();
        //查询课程基本信息
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            courseView.setCourseBase(courseBase);
        }
        //查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if(courseMarketOptional.isPresent()){
            CourseMarket courseMarket = courseMarketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }
        // /查询课程图片信息
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            courseView.setCoursePic(picOptional.get());
        }//查询课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.findTeachplanList(id);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    //删除课程图片
    @Transactional
    public ResponseResult deleteCoursePic(String courseId){
        //执行删除，返回1表示删除成功，返回0表示删除失败
        coursePicRepository.deleteById(courseId);
       /* if(result == 0){
            return new ResponseResult(CommonCode.FAIL);
        }*/
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //根据课程id查询课程图片
    public CoursePic findCoursepic(String courseId) {

        return coursePicRepository.findById(courseId).get();
    }

    //添加课程图片
    @Transactional
    public ResponseResult saveCoursePic(String courseId,String pic){
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        CoursePic coursePic = null;
        if(picOptional.isPresent()){
            coursePic = picOptional.get();
        }
        // /没有课程图片则新建对象
        if(coursePic == null){
            coursePic = new CoursePic();
        }
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        //保存课程图片
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

      public TeachplanNode findTeachplanList(String courseId){
          TeachplanNode list = teachplanMapper.findTeachplanList(courseId);
          return list;
      }

      //获取课程根节点，如果没有就添加根节点
    public String getTeachplanRoot(String courseId){
        //校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        CourseBase courseBase = optional.get();
        //取出课程计划根节点
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(teachplanList ==null || teachplanList.size()==0){
            //新增一个节点
            Teachplan teachplanRoot = new Teachplan();
            teachplanRoot.setCourseid(courseId);
            teachplanRoot.setPname(courseBase.getName());
            teachplanRoot.setParentid("0");
            teachplanRoot.setGrade("1");//1级
            teachplanRoot.setStatus("0");//未发布
            teachplanRepository.save(teachplanRoot);
            return teachplanRoot.getId();
        }
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();
    }

     //添加课程计划
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan){
        //校验课程id 和课程计划名称
        if(teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseid()) ||
                StringUtils.isEmpty(teachplan.getPname())){
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        //取出课程id
        String courseid = teachplan.getCourseid();
        //取出父节点
        String parentid = teachplan.getParentid();
        if(StringUtils.isEmpty(parentid)){
            //如果父节点为空则获取根节点
            parentid = getTeachplanRoot(courseid);
        }
        //取出父节点信息
        Optional<Teachplan> teachplanOptional = teachplanRepository.findById(parentid);
        if(!teachplanOptional.isPresent()){
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        //父节点
        Teachplan teachplanParent = teachplanOptional.get();
        //父节点级别
        String parentGrade = teachplanParent.getGrade();
        //设置父节点
        teachplan.setParentid(parentid);
        teachplan.setStatus("0");//未发布
        //子节点的级别 根据父节点来判断
        if(parentGrade.equals("1")){
            teachplan.setGrade("2");
        }else if(parentGrade.equals("2")){
            teachplan.setGrade("3");
        }
        //设置课程id
        teachplan.setCourseid(teachplanParent.getCourseid());
        teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //根据公司id查询课程列表
    @Transactional
    public QueryResponseResult findCourseListPage(int page,int pagesize,String companyid){
        ///做异常处理
        if(companyid == null || "".equals(companyid)){
            //通用的异常处理 都是父子及关系
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        PageHelper.startPage(page, pagesize);
        //这中间不能有sql语句
        Page<CourseInfo> pageAll = courseMapper.findCourseListPage(companyid);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(pageAll.getResult());
        queryResult.setTotal(pageAll.getTotal());
         return  new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
