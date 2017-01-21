package com.asiainfo.tulingrobot.test;

import com.asiainfo.tulingrobot.utils.HttpUtils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

    }

    @Test
    public void sendInfo() throws Exception {
        String mDoGetStr ;
        mDoGetStr = HttpUtils.doGet("好漂亮");
        System.out.println("笑话-->"+mDoGetStr);

        mDoGetStr = HttpUtils.doGet("给我讲个鬼故事");
        System.out.println("鬼故事-->"+mDoGetStr);

        mDoGetStr = HttpUtils.doGet("你好");
        System.out.println("你好-->"+mDoGetStr);

        mDoGetStr = HttpUtils.doGet("你好漂亮");
        System.out.println("你好漂亮-->"+mDoGetStr);

    }
}