package com.monkeyzi.oauth.entity.dto.file;

import com.monkeyzi.oauth.base.BaseQueryDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "文件列表查询")
public class FileQueryDto extends BaseQueryDto {

    private String folderId;

    private String createTime;

    private String createUser;

    private String fileLocation;

    private String fileType;

    private String fileName;

}
