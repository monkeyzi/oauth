package com.monkeyzi.oauth.entity.dto.file;

import com.monkeyzi.oauth.base.BaseQueryDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "文件列表查询")
public class FileQueryDto extends BaseQueryDto {

    private String folderId;

    private String  queryStartTime;

    private String  queryEndTime;

    private String createUser;

    private String fileLocation;

    private String fileType;

    private String fileName;

    private List<String> folderIds;

}
