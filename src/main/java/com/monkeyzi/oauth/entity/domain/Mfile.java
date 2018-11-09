package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "m_file")
@Alias(value = "mfile")
@ApiModel(value = "文件")
public class Mfile  extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名")
    private String  fileName;

    @ApiModelProperty(value = "文件大小")
    private String  fileSize;

    @ApiModelProperty(value = "文件类型")
    private String  fileType;

    @ApiModelProperty(value = "文件存放位置  阿里云oss 七牛云")
    private String  fileLocation;

    @ApiModelProperty(value = "文件存放的文件夹的名")
    private String  folderName;

    @ApiModelProperty(value = "文件存放的文件夹对应的Id")
    private String  folderId;

    @ApiModelProperty(value = "文件访问路径")
    private String  fileUrl;

    @ApiModelProperty(value = "文件原始名")
    private String  originName;


}
