package com.ningmeng.manage_media.controller;

import com.ningmeng.framework.domain.media.request.QueryMediaFileRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.manage_media.service.MediaFileService;
import com.ningmeng.manage_media.service.MediaUploadService;
import modiaApi.MediaFileControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 周周 on 2020/3/5.
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi{

    @Autowired
    MediaFileService mediaFileService;
    @Autowired
    MediaUploadService mediaUploadService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,
                                         @PathVariable("size") int size,
                                         QueryMediaFileRequest queryMediaFileRequest) {
        return mediaFileService.findList(page,size,queryMediaFileRequest);
    }
}
