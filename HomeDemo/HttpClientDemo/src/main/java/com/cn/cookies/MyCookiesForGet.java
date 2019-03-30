package com.cn.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * testGetWithCookies 依赖test
 * test访问接口/getCookies  ->响应Cookie信息为 testGetWithCookies访问/get/with/cookies接口的携带cookie
 */
public class MyCookiesForGet {
    private String url;
    private ResourceBundle bundle;
    private String getString;
    //用来存储cookie的
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        bundle=ResourceBundle.getBundle("application",Locale.CHINA);
        url=bundle.getString("url");
        getString=bundle.getString("test.get.with.cookies");

    }
    @Test
    public  void test() throws IOException {
        String url=bundle.getString("getCookie");
        HttpGet get=new HttpGet(this.url+url);
        System.out.println("url:"+this.url+url);
        //获得cookie信息得使用DefaultHttpClient接收实例化，不能用HttpClient
        DefaultHttpClient client=new DefaultHttpClient();
        HttpResponse response=client.execute(get);
        String result=EntityUtils.toString(response.getEntity(),"utf-8");

        this.store= client.getCookieStore();
        List<Cookie> cookies=store.getCookies();
        for(Cookie c:cookies){
            String name=c.getName();
            String value=c.getValue();
            System.out.println("cookie-name"+name);
            System.out.println("cookie-value"+value);
        }
        System.out.println(result);
    }

    /**
     * 本方法依赖test接口返回的cookie值
     * @throws IOException
     */
    @Test(dependsOnMethods = {"test"})//依赖
    public void testGetWithCookies() throws IOException {
        //访问接口地址
        String url=this.url+this.getString;
        System.out.println("url2:"+url);
        HttpGet get=new HttpGet(url);
        DefaultHttpClient client=new DefaultHttpClient();
        //将getCookies接口的响应cookie set到本次访问
            client.setCookieStore(this.store);
        //提交请求，获得响应
        HttpResponse response=client.execute(get);
        //获得请求响应码
        int statusCode=response.getStatusLine().getStatusCode();
        if(statusCode==200){
            String result=EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
    }
}
