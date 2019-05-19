package com.ss.vv.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpsRequest {

    /**
     * 返回成功状态码
     */
    private static final int SUCCESS_CODE = 200;

    public static String httpsRequest(String requestUrl, String requestMethod) throws Exception {
        StringBuffer buffer = null;
            //创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = {new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            ;
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }

        return buffer.toString();
    }

    public static JSONObject sendGet(String url, List<NameValuePair> nameValuePairList) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = new SSLClient();
        CloseableHttpResponse response = null;
        try{

            client = HttpClients.createDefault();

            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.addParameters(nameValuePairList);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity,"UTF-8");

                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return jsonObject;
                }
            }else{

            }
        }catch (Exception e){

        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    public static HttpEntity sendGetTodownMusic(String url, List<NameValuePair> nameValuePairList,String name) throws Exception {
        JSONObject jsonObject = null;
        CloseableHttpClient client = new SSLClient();
        CloseableHttpResponse response = null;
        try{

            client = HttpClients.createDefault();

            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.addParameters(nameValuePairList);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                IOUtils.download("D:" + name + ".mp3",entity.getContent());
                return entity;
            }else{

            }
        }catch (Exception e){

        } finally {
            response.close();
            client.close();
        }
        return null;
    }


    public static List<NameValuePair> getParams(Object[] params, Object[] values){
        /**
         * 校验参数合法性
         */
        boolean flag = params.length>0 && values.length>0 &&  params.length == values.length;
        if (flag){
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for(int i =0; i<params.length; i++){
                nameValuePairList.add(new BasicNameValuePair(params[i].toString(),values[i].toString()));
            }
            return nameValuePairList;
        }else{
        }
        return null;
    }




    public static Map getHttpsHeader(String requestUrl, String requestMethod) throws Exception {
        StringBuffer buffer = null;
        //创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("SSL");
        TrustManager[] tm = {new MyX509TrustManager()};
        //初始化
        sslContext.init(null, tm, new java.security.SecureRandom());

        //获取SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        URL url = new URL(requestUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod(requestMethod);
        //设置当前实例使用的SSLSoctetFactory
        conn.setSSLSocketFactory(ssf);
        conn.connect();
        //读取服务器端返回的内容
       Map map = conn.getHeaderFields();

        return map;
    }

}
