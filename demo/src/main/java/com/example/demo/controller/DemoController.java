package com.example.demo.controller;

import com.example.demo.dto.DemoDTO;
import com.example.demo.server.DemoServer;
import com.example.demo.util.ResponseResult;
import com.example.demo.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author  chris
 * @className DemoController
 * @date
 * @description:
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoServer demoServer;

    //@ApiOperation(value = "测试动态sql解析")
    @RequestMapping(value = "/testSql",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> testSql(@RequestBody DemoDTO data){
        LOGGER.info("入参：{}",data.getStuName());
        try{
            return demoServer.TestSql(data);
        }catch (Exception e){
            LOGGER.error("测试动态sql解析 error",e);
            return ResultUtils.wrapResult("-1","测试动态SQL解析失败");
        }
    }
}
