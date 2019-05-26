package com.monkeyzi.oauth.entity.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.persistence.Table;

/**
 * @author: 高yg
 * @date: 2018/12/11 20:50
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@Alias(value = "monkey")
@Table(name = "monkey")
@ToString
public class Monkey {

    private String id;

    private String name;
}
