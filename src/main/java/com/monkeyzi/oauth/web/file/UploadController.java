package com.monkeyzi.oauth.web.file;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.annotation.RateLimiter;
import com.monkeyzi.oauth.annotation.ValidateAnnotation;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FileQueryDto;
import com.monkeyzi.oauth.entity.dto.file.FolderDto;
import com.monkeyzi.oauth.entity.dto.file.ReFileDto;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.service.FileFolderService;
import com.monkeyzi.oauth.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "UploadController", description = "文件管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UploadController extends BaseController {

    @Autowired
    private FileService fileService;
    @Autowired
    private FileFolderService fileFolderService;

    @GetMapping(value = "/queryFolder")
    @ApiOperation(httpMethod = "GET",value = "查询文件夹树")
    public  R  queryFolder(){
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        List<TreeDto> list=fileFolderService.queryFolder(loginAuthDto);
        return R.ok("ok",list);
    }

    @PostMapping(value = "/upload")
    @LogAnnotation
    @RateLimiter(limit = 2)
    @ApiOperation(httpMethod = "POST",value = "上传文件")
    public R upload(@RequestParam(required = true)MultipartFile file,
                    @RequestParam(required = true)String folderId){
        log.info("上传文件的参数为 folderId={}",folderId);
        Preconditions.checkArgument(StringUtils.isNotBlank(folderId),ErrorCodeEnum.FU502.getMsg());
        fileService.uplaodFile(file,folderId,getLoginAuthUser());
        return R.okMsg("文件上传成功");
    }


    @PostMapping(value = "/createFolder")
    @LogAnnotation
    @ValidateAnnotation
    @ApiOperation(httpMethod = "POST",value = "创建文件夹")
    public R createFolder(@RequestBody @Valid FolderDto folderDto, BindingResult bindingResult){
        log.info("创建文件夹的参数为 folderDto={}",folderDto);
        int result=fileFolderService.createFolder(folderDto,getLoginAuthUser());
        return super.handleResult(result);
    }

    @PutMapping(value = "/editFolder")
    @LogAnnotation
    @ValidateAnnotation
    @ApiOperation(httpMethod = "PUT",value = "修改文件夹")
    public R editFolder(@RequestBody @Valid FolderDto folderDto, BindingResult bindingResult){
        log.info("修改文件夹的参数为 folderDto={}",folderDto);
        Preconditions.checkArgument(StringUtils.isNotBlank(folderDto.getId()),ErrorCodeEnum.FU502.getMsg());
        int result=fileFolderService.editFolder(folderDto,getLoginAuthUser());
        return super.handleResult(result);
    }


    @PostMapping(value = "/queryFileByFolder")
    @ApiOperation(httpMethod = "POST",value = "查询用户已上传文件文件")
    public R queryFileByFolder(@RequestBody FileQueryDto fileQueryDto){
        log.info("根据文件夹Id查询该文件夹下的文件的参数 fileQueryDto={}",fileQueryDto);
        Preconditions.checkArgument(StringUtils.isNotBlank(fileQueryDto.getFolderId()),ErrorCodeEnum.FU502.getMsg());
        PageInfo pageInfo=fileService.queryFileByFolder(fileQueryDto,getLoginAuthUser());
        return R.ok("查询成功",pageInfo);
    }


    @DeleteMapping(value = "/deleteFile/{ids}")
    @ApiOperation(httpMethod = "DELETE",value = "删除文件")
    @LogAnnotation
    public R deleteFile(@PathVariable String[] ids){
        log.info("删除文件的参数为 ids={}",ids);
        fileService.deleteFile(ids);
        return R.okMsg("操作成功");
    }


    @PostMapping(value = "/copyFile/{id}")
    @ApiOperation(httpMethod = "POST",value = "复制文件")
    @LogAnnotation
    public R copyFile(@PathVariable String id){
        log.info("复制文件的参数为 id={}",id);
        fileService.copyFile(id,getLoginAuthUser());
        return R.okMsg("操作成功");
    }

    @PutMapping(value = "/reNameFile")
    @ApiOperation(httpMethod = "PUT",value = "重命名文件")
    @LogAnnotation
    @ValidateAnnotation
    public R reNameFile(@RequestBody @Valid ReFileDto fileDto,BindingResult bindingResult){
        log.info("重命名文件的参数为 fileDto={}",fileDto);
        fileService.reNameFile(fileDto,getLoginAuthUser());
        return R.okMsg("操作成功");
    }











}
