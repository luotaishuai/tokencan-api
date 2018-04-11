package com.tokencan.test.service;

import com.tokencan.test.common.TokenCanConst;
import com.tokencan.test.util.HttpUtils;
import com.tokencan.test.util.MD5Util;
import com.tokencan.test.util.ParamUtil;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author anonymity
 * @create 2018-04-11 10:47
 **/
@Service
public class TokanCanApiService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    // 获取账户信息
    public String getAccount(String apiKey, String secretKey) {
        Assert.assertNotNull("apiKey can not be null", apiKey);
        Assert.assertNotNull("secretKey can not be null", secretKey);
        try {
            Map<String, String> map = getAccountSignParam(apiKey, secretKey);
            String url = getAccountFullUrl(TokenCanConst.TOKEN_CAN_URL, TokenCanConst.ACCOUNT_URL, map);
            return HttpUtils.sendGet(url, true);
        } catch (Exception e) {
            LOG.error("get account info failed: {}", e.getMessage(), e);
        }
        return null;
    }


    public String getAllTrade(String symbol, String apiKey, String secretKey) {
        Assert.assertNotNull("symbol can not be null", symbol);
        Assert.assertNotNull("apiKey can not be null", apiKey);
        Assert.assertNotNull("secretKey can not be null", secretKey);
        try {
            return getAllTrades(symbol, apiKey, secretKey);
        } catch (Exception e) {
            LOG.error("get all trade failed: {}", e.getMessage(), e);
        }
        return null;
    }

    private String getAllTrades(String symbol, String apiKey, String secretKey) throws Exception {
        Map<String, String> map = getAllTradeSignParam(symbol, apiKey, secretKey);
        String url = getAllTradeFullUrl(TokenCanConst.TOKEN_CAN_URL, TokenCanConst.ALL_TRADE_URL, map);
        return HttpUtils.sendGet(url, true);
    }

    // 获取所有成交记录url
    private String getAllTradeFullUrl(String tokenCanUrl, String allTradeUrl, Map<String, String> map) {
        if (map.get("pageSize") == null && map.get("page") == null) {
            return tokenCanUrl + allTradeUrl
                    + "?api_key=" + map.get("api_key")
                    + "&sign=" + map.get("sign")
                    + "&symbol=" + map.get("symbol")
                    + "&time=" + map.get("time");
        }
        return tokenCanUrl + allTradeUrl
                + "?api_key=" + map.get("api_key")
                + "&page=" + map.get("page")
                + "&pageSize=" + map.get("pageSize")
                + "&sign=" + map.get("sign")
                + "&symbol=" + map.get("symbol")
                + "&time=" + map.get("time");

    }

    // 获取所有成交记录(带分页参数),签名异常
    private Map<String, String> getAllTradeSignParamPage(String symbol, String apiKey, String secretKey, Integer pageSize, Integer page) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("api_key", apiKey);
        map.put("symbol", symbol);
        map.put("pageSize", String.valueOf(pageSize));
        map.put("page", String.valueOf(page));
        String param = ParamUtil.formatParamMap(map, true);
        String sign = MD5Util.MD5(param + secretKey);
        map.put("sign", sign);
        return map;
    }

    // 获取所有成交记录签名
    private Map<String, String> getAllTradeSignParam(String symbol, String apiKey, String secretKey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("api_key", apiKey);
        map.put("symbol", symbol);
        String param = ParamUtil.formatParamMap(map, true);
        String sign = MD5Util.MD5(param + secretKey);
        map.put("sign", sign);
        return map;
    }

    // 获取账户信息签名
    private Map<String, String> getAccountSignParam(String apiKey, String secretKey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("api_key", apiKey);
        String param = ParamUtil.formatParamMap(map, true);
        String sign = MD5Util.MD5(param + secretKey);
        map.put("sign", sign);
        return map;
    }

    // 获取账户url
    private String getAccountFullUrl(String hostUrl, String funUrl, Map<String, String> argMap) {
        return hostUrl + funUrl + "?api_key=" + argMap.get("api_key") + "&time=" + argMap.get("time") + "&sign=" + argMap.get("sign");
    }


}
