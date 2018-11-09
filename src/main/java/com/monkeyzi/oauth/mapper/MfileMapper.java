package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.Mfile;
import com.monkeyzi.oauth.entity.dto.file.FileQueryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MfileMapper  extends MyMapper<Mfile> {

    /**
     * 条件查询文件列表
     * @param fileQueryDto
     * @return
     */
    List<Mfile> selectFileListByCondition(FileQueryDto fileQueryDto);
}
