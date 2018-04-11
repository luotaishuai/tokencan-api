package com.tokencan.test.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 参数字典码排序
 * @author anonymity
 * @create 2018-04-11 10:29
 **/
public class ParamUtil {
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
}
