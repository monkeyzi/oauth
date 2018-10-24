package com.monkeyzi.oauth.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "唯一标识ID")
    private String id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标志 默认0")
    private Integer delFlag;

    @Transient
    @JsonIgnore
    public boolean isNew(){
        return StringUtils.isBlank(this.id);
    }



    /**
     * Sets update info.
     *
     * @param user the user
     */
    @Transient
    @JsonIgnore
    public void setUpdateInfo(LoginAuthDto user) {

        if (isNew()) {
            this.createBy = user.getUserName();
            this.createTime = (this.updateTime = new Date());
            this.updateBy =user.getUserName();
            this.delFlag=0;
        }
        this.createBy = user.getUserName();
        this.updateTime = new Date();
    }
}
