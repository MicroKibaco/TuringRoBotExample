package com.asiainfo.tulingrobot.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.asiainfo.tulingrobot.bean.ChatMessage;
import com.asiainfo.tulingrobot.bean.Result;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 编写一个工具类实现消息的发送和接收
 */

public class HttpUtils {

    private  static  final  String URL = "http://www.tuling123.com/openapi/api";

    //a71baa7aac614586b9af311639db50cc 小木箱的API_KEY
    //534dc342ad15885dffc10d7b5f813451 hyman
    private  static  final  String API_KEY = "534dc342ad15885dffc10d7b5f813451";
    private static int TIME_REQUEST = 5 * 1000;;


    /**
     * 发送一个消息,得到的返回的信息
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage (String msg){

        ChatMessage chatMessage = new ChatMessage();

        String jsonRes = doGet(msg);
        Gson gson = new Gson();
        Result result =  null;

        try {

            Result json = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(json.getText());//网络请求失败 报空指针异常

        } catch (JsonSyntaxException e) {

            chatMessage.setMsg("服务器繁忙,请稍后再试!!!");
        }

        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);

        return chatMessage;
    }

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
