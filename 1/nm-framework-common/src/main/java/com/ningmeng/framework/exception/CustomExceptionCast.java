package com.ningmeng.framework.exception;

import com.ningmeng.framework.model.response.ResultCode;

/**
 * Created by 周周 on 2020/2/20.
 */
public class CustomExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
