package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.Monkey;
import com.monkeyzi.oauth.service.MonkeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: 高yg
 * @date: 2018/12/11 20:54
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class MonkeyServiceImpl extends BaseServiceImpl<Monkey> implements MonkeyService {
    @Override
    public int insertToDb(Monkey mm) {
        return super.save(mm);
    }
}
