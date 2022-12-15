package com.xymtop.Util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class MyCookie {

    public  static  void setCookie(HttpServletResponse response, String name, String value){
        // 设置 name 和 url cookie
        Cookie cname = new Cookie(name,
                value);
        // 设置cookie过期时间为24小时。
        cname.setMaxAge(60*60*24);
        // 在响应头部添加cookie
        response.addCookie( cname );
    }

    public static  String getCookie(HttpServletRequest request , String name) throws UnsupportedEncodingException {

        Cookie[] cookies = null;
        String res = null;
        // 获取 cookies 的数据,是一个数组
        cookies = request.getCookies();
        if(cookies.length==0){
            res = null;
        }else {
            for (int i = 0;i<cookies.length;i++){
                if(cookies[i].getName().equals(name)){
                    res = cookies[i].getValue();
                }
            }
        }

        return  res;
    }
}
