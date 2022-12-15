package com.xymtop.Controller;


import com.xymtop.Annotation.Controller;
import com.xymtop.Annotation.Mapping;
import com.xymtop.Config.CaptchaType;
import com.xymtop.Util.MyCookie;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName : CaptchaController
 * @Description : 验证码控制器
 * @Author : 肖叶茂
 * @Date: 2022/12/13  14:05
 */
@Controller(router = "captcha")
public class CaptchaController extends HttpServlet {


    @Mapping(url = "getImgCaptcha")
//    返回图片验证码
    public void getImgCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");  //设置类型
        String randomString = "xymtop";
        CaptchaType type = CaptchaType.calculation;

//        随机取验证码类型
        int m = (int) (Math.random()*10);
        if(m<=3){
            type = CaptchaType.letter;
        }else  if(m>3&&m<6){
            type = CaptchaType.number;
        }



//        字母
        if(type == CaptchaType.letter){

                //先定义取值范围
                String chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMabcdefghijklmnopqrstuvwxyz";
                StringBuffer value = new StringBuffer();
                for (int i = 0; i < 4; i++) {
                    value.append(chars.charAt((int)(Math.random() * 75)));
                }
                randomString = value.toString();

        }

        if(type == CaptchaType.number){
            String chars = "0123456789";
            StringBuffer value = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                value.append(chars.charAt((int)(Math.random() * 10)));
            }
            randomString = value.toString();
        }


//        储存到cookie中
        MyCookie.setCookie(response,"captcha",randomString);


        if(type == CaptchaType.calculation){
            int x = (int) (Math.random()*100);
            int y = (int) (Math.random()*100);

            randomString = String.valueOf(x)+"+"+String.valueOf(y)+"=?";

//        储存到cookie中
            MyCookie.setCookie(response,"captcha",String.valueOf(x+y));
        }


        int width=100,height=30;  //设置验证码的宽高
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.fillRect(0, 0, width, height);  //此行缺少，图片背景变为黑色
        g.setColor(Color.BLUE);
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
        g.drawString(randomString, 20, 20);
        //输出验证码图片
        ImageIO.write(bi, "JPG", response.getOutputStream());
    }



}
