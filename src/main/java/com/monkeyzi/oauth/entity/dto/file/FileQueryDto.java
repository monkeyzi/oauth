package com.monkeyzi.oauth.entity.dto.file;

import com.monkeyzi.oauth.base.BaseQueryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "文件列表查询")
public class FileQueryDto extends BaseQueryDto {

    @ApiModelProperty(value = "文件夹Id",required = true)
    private String folderId;

    @ApiModelProperty(value = "开始时间")
    private String  queryStartTime;

    @ApiModelProperty(value = "结束时间")
    private String  queryEndTime;

    @ApiModelProperty(value = "上传人")
    private String createUser;

    @ApiModelProperty(value = "文件存储位置")
    private String fileLocation;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    private List<String> folderIds;

}
