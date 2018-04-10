package com.tokencan.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author huohuo
 * @create 2017-10-24 17:35
 **/
public class HttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    public static String sendGet(String url, boolean needLocalProxy) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = getUrlConnection(realUrl, needLocalProxy);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            // http info
//            Map<String, List<String>> map = connection.getHeaderFields();
//            for (String key : map.keySet()) {
//                LOG.debug("{} ---> {}", key, map.get(key));
//            }
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LOG.error("Get request failed: {}", e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                LOG.error("close stream failed: {}", e2.getMessage(), e2);
            }
        }
        return result;
    }

    private static URLConnection getUrlConnection(URL realUrl, boolean needProxy) throws IOException {
        if (needProxy){
            InetSocketAddress address = new InetSocketAddress("192.168.1.115", 11111);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            return realUrl.openConnection(proxy);
        } else {
            return realUrl.openConnection();
        }
    }

}
