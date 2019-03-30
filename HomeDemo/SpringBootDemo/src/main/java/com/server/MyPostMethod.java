package com.server;


import com.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/",description = "这是我全部的post请求")
@RequestMapping(value = "/v")

public class MyPostMethod {
    //这是我设置的全局变量
    private Cookie cookie;

    //用户登录成功，获取到cookie,然后再访问别的接口
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录成功后，获得cookie",httpMethod = "POST")
    public  String login(HttpServletResponse response,
                         @RequestParam(value = "userName",required = true) String userName,
                         @RequestParam(value="passWord",required = true) String passWord){
        if (userName.equals("wangshiheng")&& passWord.equals("123456")){
                cookie=new Cookie("login","hahaha");
                response.addCookie(cookie);
            System.out.println("cookie-uname:"+cookie.getName());
            System.out.println("cookie-value:"+cookie.getValue());

            return "恭喜成功了";
        }
        return  "用户名或密码错误";
    }

    /**
     * 验证cookie是否通过，通过返回List
     */
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String  getUserList(HttpServletRequest request,
                            @RequestBody User user){
        //获取cookies
        Cookie[] cookies=request.getCookies();
        //验证cookies
        User user1=null;
        for (Cookie c:cookies){
            if(c.getName().equals("login")
                    && c.getValue().equals("true")
                    && user.getUserName().equals("wangshiheng")
                    && user.getPassWord().equals("123456")){
                user1=new User();
                user1.setUserName("zhangshan");
                user1.setAge("18");
                user1.setSex("men");
        return user1.toString();
    }
}

        return "数据不合法";
    }

}
