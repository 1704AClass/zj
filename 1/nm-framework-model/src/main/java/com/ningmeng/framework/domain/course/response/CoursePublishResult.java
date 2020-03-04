package com.ningmeng.framework.domain.course.response;

import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by 周周 on 2020/2/25.
 */
@Data
@ToString
@NoArgsConstructor
public class CoursePublishResult extends ResponseResult{

    String previewUrl;
    public CoursePublishResult(ResultCode resultCode, String previewUrl) {
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
