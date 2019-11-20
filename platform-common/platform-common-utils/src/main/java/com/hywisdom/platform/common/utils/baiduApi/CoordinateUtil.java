package com.hywisdom.platform.common.utils.baiduApi;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuboo
 * @date 2019-11-05 15:09
 */
public class CoordinateUtil {
    /**
     * Baidu地图通过地址获取经纬度
     */
    public static String  getLngAndLat(String address) {
        String location = "";
        address = address.replace(" ", "");
        /** ak :百度第三方接口的  API key*/
        String urlStr = "http://api.map.baidu.com/geocoding/v3/?address=" + address +
                "&output=json&ak=NFg01R4l3MyzANcrqQvenWRQmUNLrxRo";
        try {
            String json = loadJSON(urlStr);
            JSONObject obj = JSONObject.parseObject(json);
            if (obj.get("status").toString().equals("0")) {
                double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
                double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
                location = lng +","+lat;

            } else {
                System.out.println("未找到相匹配的经纬度！");
            }
        } catch (Exception e) {
            System.out.println("未找到相匹配的经纬度，请检查地址！");
        }

        return location;
    }

    public static String loadJSON(String urlStr) {
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

}