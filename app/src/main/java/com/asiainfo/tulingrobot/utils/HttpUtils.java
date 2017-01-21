package com.asiainfo.tulingrobot.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 封编写一个工具类实现消息的发送和接收到
 */

public class HttpUtils {

    private  static  final  String URL = "http://www.tuling123.com/openapi/api";

    //a71baa7aac614586b9af311639db50cc 小木箱的API_KEY
    //534dc342ad15885dffc10d7b5f813451 hyman
    private  static  final  String API_KEY = "534dc342ad15885dffc10d7b5f813451";
    private static int TIME_REQUEST = 5 * 1000;;

    public static String doGet(String msg){

        String result = "";
        InputStream is = null;
        ByteArrayOutputStream baos =null;

                String url = setParams(msg);

        try {

            java.net.URL urlNet = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
            conn.setReadTimeout(TIME_REQUEST);
            conn.setConnectTimeout(TIME_REQUEST);
            conn.setRequestMethod("GET");

            is = conn.getInputStream();
            int len = -1;

            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();

            while ((len = is.read(buf))!=-1){

                baos.write(buf,0,len);

            }

            byte[] resultBytes = baos.toByteArray();
            result = new String(resultBytes);



        } catch (Exception e) {

        }finally {

            if (baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 为URL专门设置参数
     */

    private static String setParams(String msg) {

        String url = null;
        try {
            url = URL + "?key="+API_KEY+"&info="+ URLEncoder.encode(msg,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }
}
