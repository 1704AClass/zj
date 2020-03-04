package com.ningmeng.manage_course.client;

import com.ningmeng.framework.client.NmServiceList;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.response.CmsPostPageResult;
import com.ningmeng.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by 周周 on 2020/2/23.
 */
@FeignClient(value = NmServiceList.nm_SERVICE_MANAGE_CMS)
public interface CmsPageClient {

    @GetMapping("/cms/findById/{id}")
    public CmsPage findById(@PathVariable("id") String id);

    @PostMapping("/cms/add")
    public ResponseResult add(@RequestBody CmsPage cmsPage);

    @PostMapping("/cms/page/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);

}
