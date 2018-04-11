package com.tokencan.test.main;

import com.tokencan.test.service.TokanCanApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author anonymity
 * @create 2018-04-11 11:06
 **/
@RestController
@RequestMapping
public class TestController {
    @Resource
    private TokanCanApiService tokanCanApiService;

    @GetMapping(value = "/account")
    public String account(@RequestParam String apiKey, @RequestParam String secretKey) {
        return tokanCanApiService.getAccount(apiKey, secretKey);
    }

    @GetMapping(value = "/allTrade")
    public String allTrade(@RequestParam String symbol, @RequestParam String apiKey, @RequestParam String secretKey) {
        return tokanCanApiService.getAllTrade(symbol, apiKey, secretKey);
    }

}
