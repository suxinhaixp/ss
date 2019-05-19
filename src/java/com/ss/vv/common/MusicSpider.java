package com.ss.vv.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicSpider {

    private static final String URL = "https://api.mlwei.com/music/api/wy/";
    private static final String KEY = "523077333";

    public static void main(String[] args) {
        JSONObject jsonObject = MusicSpider.getTop("最新");
        List idList = (List) jsonObject.get("IdList");
        List nameList = (List) jsonObject.get("nameList") ;
        MusicSpider.downMusic((idList.get(0).toString()),nameList.get(0).toString());

    }

    public static void searchMusic() {
        String search = "someone like you";
        JSONObject result = new JSONObject();
        try {
            /**
             * 参数值
             */
            Object[] params = new Object[]{"key", "id", "type", "cache", "nu"};
            /**
             * 参数名
             */
            Object[] values = new Object[]{KEY, search, "so", 0, 1};
            /**
             * 获取参数对象
             */
            List<NameValuePair> paramsList = HttpsRequest.getParams(params, values);

            result = (JSONObject) HttpsRequest.sendGet(URL, paramsList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in HttpsRequest");
        }
        System.out.println(result.getString("Body"));
    }

    public static JSONObject getTop(String search) {
        String url = "https://api.itooi.cn/music/tencent/search";
        JSONObject result = new JSONObject();
        try {
            /**
             * 参数值
             */
            Object[] params = new Object[]{"key", "s", "limit", "offset", "type"};
            /**
             * 参数名
             */
            Object[] values = new Object[]{"579621905", search, 100, 0, "song"};
            /**
             * 获取参数对象
             */
            List<NameValuePair> paramsList = HttpsRequest.getParams(params, values);

            result = (JSONObject) HttpsRequest.sendGet(url, paramsList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in HttpsRequest");
        }
        JSONArray data = result.getJSONArray("data");
        List<String> IdList = new ArrayList<>();
        List<String> songerList = new ArrayList<>();
        List<String> picList = new ArrayList<>();
        List<String> lrcList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Object object : data) {
            JSONObject jsonObject = (JSONObject) object;
            IdList.add(jsonObject.getString("id"));
            nameList.add(jsonObject.getString("name"));
            picList.add(jsonObject.getString("pic"));
            lrcList.add(jsonObject.getString("lrc"));
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("IdList", IdList);
        resultJson.put("nameList", nameList);
        resultJson.put("picList", picList);
        resultJson.put("lrcList", lrcList);
        resultJson.put("songerList", songerList);
        return resultJson;
    }

    public static void downMusic(String Id,String name) {
        String url = "https://api.mlwei.com/music/api";
//        https://api.mlwei.com/music/api/wy/?key=523077333&cache=1&type=song&id=004PPCIB1Mq36U
        JSONObject result = new JSONObject();
        try {
            /**
             * 参数值
             */
            Object[] params = new Object[]{"key", "type", "id", "size"};
            /**
             * 参数名
             */
            Object[] values = new Object[]{KEY, "url", Id,"mp3"};
            /**
             * 获取参数对象
             */
            List<NameValuePair> paramsList = HttpsRequest.getParams(params, values);
            HttpEntity entity = HttpsRequest.sendGetTodownMusic(url,paramsList,name);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in HttpsRequest");

        }
        System.out.println(result.getString("Body"));

    }


}
