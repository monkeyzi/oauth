package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.FileFolder;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FolderDto;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.service.FileFolderService;
import com.monkeyzi.oauth.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class FileFolderServiceImpl extends BaseServiceImpl<FileFolder> implements FileFolderService {



    @Override
    public int createFolder(FolderDto folderDto, LoginAuthDto loginAuthUser) {
        ModelMapper modelMapper=new ModelMapper();
        FileFolder fileFolder=modelMapper.map(folderDto,FileFolder.class);
        //查询父文件夹存在不存在
        FileFolder pf=selectByKey(fileFolder.getParentId());
        if (pf==null){
            throw new BusinessException(ErrorCodeEnum.FU504);
        }
        fileFolder.setUpdateInfo(loginAuthUser);
        return super.saveSelective(fileFolder);
    }

    @Override
    public int editFolder(FolderDto folderDto, LoginAuthDto loginAuthUser) {
        ModelMapper modelMapper=new ModelMapper();
        FileFolder fileFolder=modelMapper.map(folderDto,FileFolder.class);
        FileFolder pf=selectByKey(folderDto.getId());
        if (pf==null){
            throw new BusinessException(ErrorCodeEnum.FU503);
        }
        fileFolder.setUpdateInfo(loginAuthUser);
        return super.updateByPrimaryKey(fileFolder);
    }

    @Override
    public List<TreeDto> queryFolder(LoginAuthDto loginAuthDto) {
        //超管角色可以查看全部
        List<FileFolder> fileFolderList;
        //所有的用户新建文件夹的时候层级1的父亲都是 0
        FileFolder fileFolder=super.selectByKey(GlobalConstant.Sys.SYS_FOLDER_ID);
        if (loginAuthDto.getRoleList()!=null&&loginAuthDto.getRoleList().
                contains(GlobalConstant.Sys.SYS_SUPER_ADMIN_ROEL_ID)){
            fileFolderList=super.selectAll();
        }else {
            FileFolder folder=new FileFolder();
            folder.setCreateBy(loginAuthDto.getUserName());
            fileFolderList=select(folder);
        }
        //递归成树形结构
        ModelMapper modelMapper=new ModelMapper();
        List<TreeDto> dtoList=modelMapper.map(fileFolderList,new TypeToken<List<TreeDto>>() {}.getType());
        return TreeUtils.getTree(dtoList,fileFolder.getId());
    }
}
