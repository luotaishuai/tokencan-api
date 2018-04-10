package com.tokencan.test.main;

import com.tokencan.test.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author anonymity
 * @create 2018-04-10 11:11
 **/
@Service
public class TokenCanMain {
    public static final String TOKEN_CAN_URL = "https://api.tokencan.com/exchange-open-api";
    public static final String ACCOUNT_URL = "/open/api/user/account";

    public static void main(String[] args) {
        String api_key = "";
        String secret  = "";
        String time = String.valueOf(System.currentTimeMillis() / 1000);

        Map<String, String> map = new HashMap<String, String>();
        map.put("time", time);
        map.put("api_key", api_key);
        String param = formatParamMap(map, true);
        String sign = MD5(param + secret);
        String url = TOKEN_CAN_URL + ACCOUNT_URL + "?api_key=" + api_key + "&time=" + time + "&sign="+sign;
        String json = HttpUtils.sendGet(url, true);
        System.out.println(json);
    }

    // 字典序
    public static String formatParamMap(Map<String, String> paramMap, boolean keyToLower) {
        String buff = "";
        Map<String, String> tmpMap = paramMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的ASCII码从小到大排序(字典序)
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String value = item.getValue();
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + value);
                    } else {
                        buf.append(key + value);
                    }
//                    buf.append("&");
                }
            }
            buff = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buff;
    }

    // 32位md5码
    public static String MD5(String encodeStr) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(encodeStr.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
