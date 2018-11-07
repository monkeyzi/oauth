package com.monkeyzi.oauth.web.file;

import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.annotation.RateLimiter;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "UploadController", description = "文件管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UploadController extends BaseController {

    @PostMapping(value = "/upload")
    @LogAnnotation
    @RateLimiter(limit = 2)
    public R upload(@RequestParam(required = true)MultipartFile file){


        return R.okMsg("文件上传成功");
    }
}
