package com.monkeyzi.oauth.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.Mfile;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FileQueryDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService extends BaseService<Mfile> {
    /**
     * 上传文件
     * @param file
     */
    void uplaodFile(MultipartFile file, String folderId, LoginAuthDto loginAuthDto);

    /**
     * 根据文件夹查询该文件夹下的文件
     * @param fileQueryDto
     * @param loginAuthUser
     * @return
     */
    PageInfo queryFileByFolder(FileQueryDto  fileQueryDto, LoginAuthDto loginAuthUser);
}
