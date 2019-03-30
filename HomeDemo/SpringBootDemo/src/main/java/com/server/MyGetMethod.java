package com.server;

import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value="/",description = "这是我全部的get方法")
public class MyGetMethod {
    /**
     * 返回cookie信息
     * @param response
     * @return
     */
    @RequestMapping(value = "/getCookie",method =RequestMethod.GET)
    @ApiOperation(value="通过这个方法可以获取到cookie",httpMethod = "GET")
    public String getCookie(HttpServletResponse response){
        //HttpServletRequest 请求信息类
        //HttpServletResponse 响应信息类
        Cookie cookie=new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你，返回cookie信息成功";
    }

    /**
     * 实现一个要求客户端携带cookie访问
     */
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value="这是一个需要携带cookie访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(Objects.isNull(cookies)){
            return "你必须携带cookie来访问";
        }
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("login")&& cookie.getValue().equals("true")){
                return "这是一个携带cookie的请求";
            }
        }
        return "你必须携带cookie来访问";
    }

    /**
     * eg1:
     * 一个需要携带参数才能访问的请求
     * url:key=value&key=value
     * 模拟获取商品列表
     * http://localhost:8888/get/with/param?start=1&end=3
     */
    @RequestMapping(value="/get/with/param",method= RequestMethod.GET)
    @ApiOperation(value="这是一个需要携带参数才能访问方法1",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,@RequestParam Integer end){
        Map<String,Integer> myList=new HashMap<>();
        myList.put("鞋子",400);
        myList.put("黄瓜",30);
        return  myList;
    }
    /**
     * eg2:
     * 一个需要携带参数才能访问的请求
     * url:ip:port/get/with/parm/10/20
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value="这是一个需要携带参数才能访问方法2",httpMethod = "GET")
    public Map myList(@PathVariable Integer start, @PathVariable Integer end){
        Map<String,Integer> myList=new HashMap<>();
        myList.put("鞋子",400);
        myList.put("黄瓜",30);
        return  myList;
    }
}
