package com.cn.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    private ResourceBundle bundle;
    private String url;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        this.bundle=ResourceBundle.getBundle("application",Locale.CHINA);
        this.url=bundle.getString("url");
    }
    @Test
    public void getCookies() throws IOException {
        //创建HttpGet对象，传入目标网络地址
        HttpGet get=new HttpGet(url+bundle.getString("getCookie"));
        //获得cookie信息得使用DefaultHttpClient接收实例化，不能用HttpClient
        DefaultHttpClient client=new DefaultHttpClient();
        //发起请求
        HttpResponse response= client.execute(get);
        //获得response中返回的值text
        String responseStr=EntityUtils.toString(response.getEntity(),"utf-8");
        //获得返回的cookie信息
        this.store=client.getCookieStore();

    }

    @Test(dependsOnMethods = {"getCookies"})
    public void postCookie() throws IOException {
        String uri=bundle.getString("test.post.with.cookies");
        //拼接测试地址
        String testUrl=this.url+uri;
        //创建一个post对象，引入访问地址
        HttpPost post=new HttpPost(testUrl);
        //创建一个client,用来执行请求
        DefaultHttpClient client=new DefaultHttpClient();
        //添加参数
        JSONObject parm=new JSONObject();
        parm.put("name","wangshiheng");
        parm.put("age","18");
        //设置请求头信息,设置header信息
        post.setHeader("content-type","application-json");
        //将参数信息添加到方法中
        StringEntity entity=new StringEntity(parm.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象进行响应结果存储
        String reslut;
        //设置Cookie信息
        client.setCookieStore(this.store);
        //执行请求
        HttpResponse response=client.execute(post);
        //获取响应结果
        reslut=EntityUtils.toString(response.getEntity(),"utf-8");

        //处理结果，判断返回结果是否符合预期,将返回的字符串转换成json
        JSONObject reslutJson=new JSONObject(reslut);
        System.out.println(reslutJson);
        String name=(String)reslutJson.get("wangshiheng");
        String age=(String) reslutJson.get("status");
        Assert.assertEquals("success",name);
        Assert.assertEquals("1",age);
    }
}
