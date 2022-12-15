package com.xymtop.Server;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : ServerJson
 * @Description : 返回json数据
 * @Author : 肖叶茂
 * @Date: 2022/12/12  17:41
 */
public class ServerJson {
    /**
    *@Description:返回字符串类型的json数据，前端可以直接解析为json数据
    *@Parameter:[response, content]
    *@Return:java.lang.String
    *@Author:肖叶茂
    *@Date:2022/12/12
    **/
    public static String renderJson(HttpServletResponse response,String content){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        java.io.PrintWriter pw = null;
        try{
            pw = response.getWriter();
            pw.write(content);
        }catch (Exception e){
            //
        }finally{
            pw.close();
        }
        return null;
    }


    /**
    *@Description:直接返回json对象数据
    *@Parameter:[response, obj]
    *@Return:java.lang.String
    *@Author:肖叶茂
    *@Date:2022/12/12
    **/
    public static String returnJson(HttpServletResponse response,Object obj){
        return  renderJson(response,JsonParas.object2JsonString(obj));
    }


//    返回文本类型
    public static String renderText(HttpServletResponse response,String content){
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        java.io.PrintWriter pw = null;
        try{
            pw = response.getWriter();
            pw.write(content);
        }catch (Exception e){
            //
        }finally{
            pw.close();
        }
        return null;
    }



    


}
