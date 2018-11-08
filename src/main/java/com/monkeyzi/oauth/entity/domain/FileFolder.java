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
@Table(name = "m_file_folder")
@Alias(value = "fileFolder")
@ApiModel(value = "文件夹")
public class FileFolder extends BaseEntity {

    @ApiModelProperty(value = "文件夹名")
    private String  folderName;

    @ApiModelProperty(value = "文件夹描述")
    private String  description;

    @ApiModelProperty(value = "状态")
    private String  status;

    @ApiModelProperty(value = "文件夹父级")
    private String  parentId;

    @ApiModelProperty(value = "文件夹所属层级")
    private String  level;

}
