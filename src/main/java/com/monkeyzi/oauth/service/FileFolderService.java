package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.FileFolder;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FolderDto;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;

import java.util.List;

public interface FileFolderService extends BaseService<FileFolder> {

    /**
     * 创建文件夹
     * @param folderDto
     * @param loginAuthUser
     * @return
     */
    int createFolder(FolderDto folderDto, LoginAuthDto loginAuthUser);

    /**
     * 修改文件夹
     * @param folderDto
     * @param loginAuthUser
     * @return
     */
    int editFolder(FolderDto folderDto, LoginAuthDto loginAuthUser);

    /**
     * 查询文件夹树
     * @param loginAuthDto
     * @return
     */
    List<TreeDto> queryFolder(LoginAuthDto loginAuthDto);
}
