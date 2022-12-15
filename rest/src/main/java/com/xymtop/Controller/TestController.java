package com.xymtop.Controller;

import com.xymtop.Annotation.Controller;
import com.xymtop.Annotation.Mapping;
import com.xymtop.Annotation.Rest;
import com.xymtop.Server.ResoultJson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : TestController
 * @Description : 示例控制器
 * @Author : 肖叶茂
 * @Date: 2022/12/16  0:41
 */

//注意，控制器类必须继承HttpServlet

@Controller(router = "index")
public class TestController extends HttpServlet {

//    注意：这里必须要传参HttpServletRequest和HttpServletResponse
    @Rest
    @Mapping(url = "index")
    public ResoultJson<String> get(HttpServletRequest request, HttpServletResponse response){
        return new ResoultJson<>(200);
    }
}
