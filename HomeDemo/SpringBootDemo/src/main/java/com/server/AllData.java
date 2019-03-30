package com.server;

import com.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@Api(value = "/",description = "这是自己post请求")
public class AllData {
    //写一个get请求，需要携带cookie和参数访问，访问参数的格式为json(使用实例User)
    //响应返回cookie
    @RequestMapping(value = "/getAllData",method = RequestMethod.GET)
    @ApiOperation(value = "登录成功后",httpMethod = "GET")
    public String getAllData(HttpServletRequest request,HttpServletResponse response,@RequestBody User user){
        Cookie[] cookies=request.getCookies();
        if(!user.getUserName().equals("wangshiheng")){
            Cookie cookie=new Cookie("status","002");
            response.addCookie(cookie);
            return "访问失败，密码错误";
        }
        if(!Objects.isNull(cookies)){
            for(Cookie c:cookies){
                //return c.toString();
                System.out.println("cookie-name:"+c.getName());
                System.out.println("cookie-value"+c.getValue());
            }
        }
        Cookie cookie=new Cookie("status","001");
        response.addCookie(cookie);
        return  "成功";
    }
}
