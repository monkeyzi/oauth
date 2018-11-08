package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.FileFolder;
import com.monkeyzi.oauth.entity.domain.Mfile;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FileQueryDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.FileSaveEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.service.FileFolderService;
import com.monkeyzi.oauth.service.FileService;
import com.monkeyzi.oauth.utils.AliOssUtils;
import com.monkeyzi.oauth.utils.FileUtils;
import com.monkeyzi.oauth.utils.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FileServiceImpl extends BaseServiceImpl<Mfile> implements FileService {

    @Autowired
    private AliOssUtils aliOssUtils;
    @Autowired
    private FileFolderService fileFolderService;

    @Override
    public void uplaodFile(MultipartFile file, String folderId, LoginAuthDto loginAuthDto) {
        String originFileName=file.getOriginalFilename();
        String extName=FileUtils.getFileExtension(originFileName);
        //给文件重命名一下避免因重名被覆盖
        String reFileName=FileUtils.rnFileName(originFileName);
        //根据后缀判断存入那个文件夹分类中
        String folder="";
        if (GlobalConstant.Oss.IMAGE_LIST.contains(extName.toLowerCase())){
            folder=GlobalConstant.Oss.OSS_IMG;
        }else if (GlobalConstant.Oss.FILE_LIST.contains(extName.toLowerCase())){
            folder=GlobalConstant.Oss.OSS_FILE;
        }else if (GlobalConstant.Oss.VIDEO_LIST.contains(extName.toLowerCase())){
            folder=GlobalConstant.Oss.OSS_VIDEO;
        }else if (GlobalConstant.Oss.WORD_LIST.contains(extName.toLowerCase())){
            folder=GlobalConstant.Oss.OSS_WORD;
        }else {
            folder=GlobalConstant.Oss.OSS_OTHER;
        }
        //根据所选的文件夹查询文件夹信息
        FileFolder fileFolder=fileFolderService.selectByKey(folderId);
        if (fileFolder==null){
            throw new BusinessException(ErrorCodeEnum.FU503);
        }
        //判断当前使用的是什么存储
        String currentOss=GlobalConstant.Oss.ALI_OSS;
        String url="";
        try {

            Mfile mfile=new Mfile();
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            if (currentOss.equals(GlobalConstant.Oss.ALI_OSS)){
                url=aliOssUtils.AliossInputStreamUpload(inputStream,reFileName,folder);
                mfile.setFileLocation(FileSaveEnum.ALI_OSS_ENUM.getMsg());
            }
            mfile.setUpdateInfo(loginAuthDto);
            mfile.setId(super.generateId());
            mfile.setFileName(originFileName);
            mfile.setFileSize(String.valueOf(file.getSize()));
            mfile.setFileType(extName);
            mfile.setFolderId(folderId);
            mfile.setFolderName(fileFolder.getFolderName());
            mfile.setFileUrl(url);
            //保存到数据库
            super.saveSelective(mfile);
        }catch (Exception e){
            log.error("文件上传失败了",e.toString());
            throw new BusinessException(ErrorCodeEnum.FU501);
        }


    }

    @Override
    public PageInfo queryFileByFolder(FileQueryDto fileQueryDto, LoginAuthDto loginAuthUser) {
        //查询一下该文件夹存在不存在
        FileFolder fileFolder=fileFolderService.selectByKey(fileQueryDto.getFolderId());
        Preconditions.checkArgument(PublicUtil.isNotEmpty(fileFolder),ErrorCodeEnum.FU503.getMsg());
        //递归查询该文件夹下所有的文件夹

        return null;
    }
}
