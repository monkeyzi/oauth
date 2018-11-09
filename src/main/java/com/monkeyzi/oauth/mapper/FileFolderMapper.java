package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.FileFolder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FileFolderMapper extends MyMapper<FileFolder> {
}
