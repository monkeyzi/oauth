package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.FileFolder;
import com.monkeyzi.oauth.entity.domain.Mfile;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FileQueryDto;
import com.monkeyzi.oauth.entity.dto.file.ReFileDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.FileSaveEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.mapper.MfileMapper;
import com.monkeyzi.oauth.service.FileFolderService;
import com.monkeyzi.oauth.service.FileService;
import com.monkeyzi.oauth.utils.AliOssUtils;
import com.monkeyzi.oauth.utils.FileUtils;
import com.monkeyzi.oauth.utils.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FileServiceImpl extends BaseServiceImpl<Mfile> implements FileService {

    @Autowired
    private AliOssUtils aliOssUtils;
    @Autowired
    private FileFolderService fileFolderService;

    @Autowired
    private MfileMapper mfileMapper;

    /**
     * 获取当前的oss存储
     * @return
     */
    public String getCurrentOss(){
        String value=GlobalConstant.Oss.CURRENT_OSS;
        if (StringUtils.isBlank(value)){
            throw new BusinessException(ErrorCodeEnum.GL10005);
        }
        return value;
    }

    @Override
    public void uplaodFile(MultipartFile file, String folderId, LoginAuthDto loginAuthDto) {
        String originFileName=file.getOriginalFilename();
        String extName=FileUtils.getFileExtension(originFileName);
        //给文件重命名一下避免因重名被覆盖
        String reFileName=FileUtils.rnFileName(originFileName);
        //根据后缀判断存入那个文件夹分类中
        String folder=getFolder(extName);
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
            mfile.setFileName(reFileName);
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
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public PageInfo queryFileByFolder(FileQueryDto fileQueryDto, LoginAuthDto loginAuthUser) {
        FileFolder fileFolder=new FileFolder();
        List<FileFolder> fileFolderList;
        //超级管理员角色查询所有文件夹
        if (loginAuthUser.getRoles()!=null&&loginAuthUser.getRoleList().
                contains(GlobalConstant.Sys.SYS_SUPER_ADMIN_ROEL_ID)){

              fileFolder=fileFolderService.selectByKey(fileQueryDto.getFolderId());
              Preconditions.checkArgument(PublicUtil.isNotEmpty(fileFolder),ErrorCodeEnum.FU503.getMsg());
              //查询所有的文件夹
              fileFolderList=fileFolderService.selectAll();

        }else {
              //查询一下该文件夹存在不存在
              fileFolder.setId(fileQueryDto.getFolderId());
              fileFolder.setCreateBy(loginAuthUser.getUserName());
              fileFolder=fileFolderService.selectOne(fileFolder);
              Preconditions.checkArgument(PublicUtil.isNotEmpty(fileFolder),ErrorCodeEnum.FU503.getMsg());

              fileFolderList=deepFolderList(fileFolder);
              //加上自己
              fileFolderList.add(fileFolder);
        }

        List<String> fList=fileFolderList.stream()
                                         .map(a->a.getId())
                                         .distinct()
                                         .collect(Collectors.toList());
        fileQueryDto.setFolderIds(fList);
        fileQueryDto.setQueryEndTime(fileQueryDto.getQueryEndTime()+" 23:59:59");
        PageHelper.startPage(fileQueryDto.getPageNum(),fileQueryDto.getPageSize());
        List<Mfile> mfileList=mfileMapper.selectFileListByCondition(fileQueryDto);
        PageInfo pageInfo=new PageInfo(mfileList);

        return pageInfo;
    }

    @Override
    public void deleteFile(String[] ids) {
        Arrays.stream(ids).forEach(a->{
             //查询文件
            Mfile mfile=selectByKey(a);
            String extName=mfile.getFileType();
            String folder=getFolder(extName);
            if (getCurrentOss().equals(GlobalConstant.Oss.ALI_OSS)){
                 aliOssUtils.AliossDeleteFile(folder,mfile.getFileName());
            }
            //删除数据库存储的信息
            deleteByPrimaryKey(a);
        });
    }

    @Override
    public void copyFile(String id,LoginAuthDto loginAuthDto) {
        Mfile mfile=selectByKey(id);
        String extName=mfile.getFileType();
        String newName="副本_"+mfile.getFileName();
        String folder=getFolder(extName);
        String result="";
        Mfile file=new Mfile();
        if (getCurrentOss().equals(GlobalConstant.Oss.ALI_OSS)){
            result=aliOssUtils.AliossCopyFile(mfile.getFileName(),newName,folder);
            mfile.setFileLocation(FileSaveEnum.ALI_OSS_ENUM.getMsg());
        }

        file.setUpdateInfo(loginAuthDto);
        file.setId(super.generateId());
        file.setFolderName(mfile.getFolderName());
        file.setFileUrl(result);
        file.setFileName(newName);
        file.setFolderId(mfile.getFolderId());
        file.setFileSize(mfile.getFileSize());
        file.setFileType(mfile.getFileType());
        saveSelective(file);
    }

    @Override
    public void reNameFile(ReFileDto fileDto, LoginAuthDto loginAuthUser) {
        Mfile mfile=selectByKey(fileDto.getId());
        String extP=mfile.getFileType();
        String extN=FileUtils.getFileExtension(fileDto.getNewName());
        if (!Objects.equals(extN.toLowerCase(),extP.toLowerCase())){
            throw new BusinessException(ErrorCodeEnum.FU506);
        }
        String folder=getFolder(extP);
        String result="";
        if (getCurrentOss().equals(GlobalConstant.Oss.ALI_OSS)){
            result=aliOssUtils.AliossRenameFile(mfile.getFileName(),fileDto.getNewName(),folder);
        }
        mfile.setFileUrl(result);
        mfile.setUpdateInfo(loginAuthUser);
        updateByPrimaryKey(mfile);
    }

    /**
     * 获取存储的文件夹
     * @param extName  文件后缀
     * @return
     */
    public String getFolder(String extName){
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
        return folder;
    }

    /**
     *递归获取文件夹
     * @param fileFolder
     * @return
     */
    public List<FileFolder> deepFolderList(FileFolder fileFolder){
        List<FileFolder> list=Lists.newArrayList();

        // 查询该节点下的孩子
        FileFolder f=new FileFolder();
        fileFolder.setParentId(fileFolder.getId());
        fileFolder.setCreateBy(fileFolder.getCreateBy());
        List<FileFolder> fileFolderList=fileFolderService.select(f);

        for (FileFolder ff:fileFolderList){
            if (StringUtils.isBlank(ff.getParentId())){
                continue;
            }
            if (Objects.equals(ff.getParentId(),fileFolder.getId())){
                deepFolderList(ff);
                list.add(ff);
            }
        }
        return list;
    }
}
