package com.xymtop.Ann;

import com.xymtop.Annotation.Permission;
import com.xymtop.Config.PermissionType;
import com.xymtop.Util.ClassUtil;
import com.xymtop.Util.MyCookie;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName : PermissionImp
 * @Description : 权限注解的实现
 * @Author : 肖叶茂
 * @Date: 2022/12/13  15:26
 */
public class PermissionImp {
    private Map<Method,PermissionType> PermissionMap = new HashMap<>();

//    获取到所有加了权限的方法
    private void getAnnotation() throws Exception {
        Set<Class<?>> res = ClassUtil.getClasses("com.xymtop.Controller");
        for(Class s : res){
            Method[] methods = s.getMethods();
            for(Method method:methods){
//                    获取方法执行的函数是哪个
                if(method.isAnnotationPresent(Permission.class)){
                    PermissionType type =  ( (Permission) method.getAnnotation(Permission.class)).type();
                    this.PermissionMap.put(method,type);
                }
            }
        }
    }

    private   Boolean check(Method method, HttpServletRequest request) throws UnsupportedEncodingException {
        Boolean flag = true;

//        是否包含该权限
        if(PermissionMap.containsKey(method)){
            PermissionType type = PermissionMap.get(method);

            if(type == PermissionType.ADMIN){
//管理员权限

            }else if(type == PermissionType.LOGIN){
//                用户登录权限
                if(MyCookie.getCookie(request,"userid") == null){
                    flag = false;
                }
            }else if(type == PermissionType.STUDENT){

//                学生权限

            }else if(type == PermissionType.TEACHER){
//教师权限
            }
        }

        return  flag;
    }

//    运行
    public Boolean run(Method method,HttpServletRequest request) throws Exception {
        getAnnotation();
       return check(method,request);
    }
}
