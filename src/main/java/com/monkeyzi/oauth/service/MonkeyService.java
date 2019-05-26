package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.Monkey;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/12/11 20:53
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MonkeyService extends BaseService<Monkey> {

    int insertToDb(Monkey mm);

}
