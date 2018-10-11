package com.monkeyzi.oauth.web;

import com.monkeyzi.oauth.common.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/job")
public class JobController {

    @ResponseBody
    @RequestMapping(value = "/tt")
    public R dd(){
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/t2")
    public R t(){
        return R.ok();
    }
}
