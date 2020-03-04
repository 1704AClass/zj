package com.ningmeng.framework.domain.cms.response;

import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 周周 on 2020/2/27.
 */
@Data
@NoArgsConstructor
public class CmsPostPageResult extends ResponseResult{

    String pageUrl;
    public CmsPostPageResult(ResultCode resultCode, String pageUrl) {
        super(resultCode); this.pageUrl = pageUrl;
    }
}
