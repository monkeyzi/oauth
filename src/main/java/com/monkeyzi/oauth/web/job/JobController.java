package com.monkeyzi.oauth.web.job;

import com.monkeyzi.oauth.annotation.RateLimiter;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/job")
public class JobController {

    @ResponseBody
    @RequestMapping(value = "/tt")
    @RateLimiter(limit =3,timeout = 1000)
    public R dd(){
        throw new BusinessException(ErrorCodeEnum.RS304);
        //return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/t2")
    public R t(){
        return R.ok();
    }
}
