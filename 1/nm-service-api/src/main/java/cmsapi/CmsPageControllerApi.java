package cmsapi;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by 周周 on 2020/2/11.
 * 1.约束controller 2.向外发布接口使用
 */
@Api(value = "CMS页面管理接口",description = "提供页面管理接口")
public interface CmsPageControllerApi {
    @ApiOperation("分页方法查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",defaultValue = "1",required = true,paramType = "path",
                    dataType = "int"),
            @ApiImplicitParam(name = "size",defaultValue = "10",required = true,paramType = "path",
            dataType = "int")
    })
    public QueryResponseResult findList(int page,int size,QueryPageRequest queryPageRequest);

    //添加
    @ApiOperation("页面添加")
    public ResponseResult add(CmsPage cmsPage);

    //根据id查询
    @ApiOperation("id查询页面")
    public CmsPage findById(String id);

    //修改
    @ApiOperation("修改页面")
    public ResponseResult update(CmsPage cmsPage);

    //删除
    @ApiOperation("通过id删除页面")
    public ResponseResult delete(String id);
}
