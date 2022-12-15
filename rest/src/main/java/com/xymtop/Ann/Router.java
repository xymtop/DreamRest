package com.xymtop.Ann;

import com.xymtop.Annotation.Controller;
import com.xymtop.Annotation.Mapping;
import com.xymtop.Annotation.Rest;
import com.xymtop.Config.AppConfig;
import com.xymtop.Config.RestType;
import com.xymtop.Exception.NotFoundType;
import com.xymtop.Server.ResoultJson;
import com.xymtop.Server.ServerJson;
import com.xymtop.Util.ClassUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName : Router
 * @Description : 路由实现
 * @Author : 肖叶茂
 * @Date: 2022/12/13  0:22
 */
public class Router {
    //    这里是路由定义
    private Map<String,Class> RouteMap = new HashMap<>();
    private  Map<String,Method> MethodMap = new HashMap<>();
    private  Map<Method,RestType>  RestMap = new HashMap<>();

//    权限控制
    private  PermissionImp permissionImp = new PermissionImp();

    public   void  getRouter(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]>params =   ((HttpServletRequest) request).getParameterMap();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String mapKey = entry.getKey();
            String[] mapValue = entry.getValue();
            System.out.println(mapKey);
            for(String temp :mapValue){
                System.out.println(temp);
            }
        }
        //        运行路由管理
        runRouter();
        //        解析一下URL
        String[] parts = request.getRequestURI().split("/");
        String router = parts[parts.length-2];
        String fun = parts[parts.length-1];

        System.out.println("router: "+router+"  fun:" + fun);


        System.out.println("router"+RouteMap);
        System.out.println("MethodMap"+MethodMap);
        System.out.println("RestMap"+RestMap);

//        判断是否有对应参数
        if(RouteMap.containsKey(router) && MethodMap.containsKey(fun)) {

            try {
             Class  clazz = Class.forName((RouteMap.get(router).getCanonicalName()) );
                Object clazzObj = clazz.newInstance();

                //                获取到返回的类型
                Method method = clazzObj.getClass().getMethod(MethodMap.get(fun).getName(),HttpServletRequest.class,HttpServletResponse.class);


                if(permissionImp.run(method,request)) {
                    RestType res = RestMap.get(method);
                    if (method != null && res != null) {
                        //                获取到返回值
                        Class<?> returnclass = method.getReturnType();
                        System.out.println("method " + method);


//                  判断有没有返回参数
                        if (returnclass.getCanonicalName().equals("void")) {

//                      没有参数的处理办法
                            ServerJson.returnJson(response, new ResoultJson<>(500, "", "函数没有返回值"));
                            return;
                        } else {
                            Class c = Class.forName(returnclass.getCanonicalName());

                            Object obj = c.newInstance();

//                  运行函数然后返回内容

                            obj = method.invoke(clazzObj, request, response);


//                  传递返回内容
                            Rest(response, obj, res);
                        }
                    } else if (res == null && method != null) {
//                    如果没有确定返回的类型，就不接管返回结果
                        MethodMap.get(fun).invoke(clazzObj, request, response);
                    } else {
                        ServerJson.returnJson(response, new ResoultJson<>(500));
                    }
                }else {
                    ServerJson.returnJson(response, new ResoultJson<>(500,"","没有权限"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                ServerJson.returnJson(response, new ResoultJson<>(500, e.toString()));
            }
        }else {
            ServerJson.returnJson(response, new ResoultJson<>(404));
        }
    }



//    扫描包然后判断注解是否存在
    private void runRouter(){

        try {
            Set<Class<?>> res = ClassUtil.getClasses(AppConfig.PACKAGE);
            for(Class s : res){
//             判断是否含有控制器注解
                Boolean flag = s.isAnnotationPresent(Controller.class);

                if(flag){
                    String str = ((Controller) (s.getAnnotation(Controller.class))).router();
                    this.RouteMap.put(str,s);
                }

                Method[] methods = s.getMethods();
                for(Method method:methods){
//                    获取方法执行的函数是哪个
                    if(method.isAnnotationPresent(Mapping.class)){
                        String url =  ( (Mapping) method.getAnnotation(Mapping.class)).url();
                        this.MethodMap.put(url,method);
                    }

//                    获取到函数应该返回给前端的类型
                    if(method.isAnnotationPresent(Rest.class)){
                        RestType type = ((Rest) method.getAnnotation(Rest.class)).type();
                        this.RestMap.put(method,type);
                    }
                }


            }
//            System.out.println(RouteMap.toString());
//            System.out.println(MethodMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    这里返回具体的内容
      private void Rest(HttpServletResponse response,Object obj,RestType type) throws NotFoundType {
         if(type == RestType.JSON) {
//             字符串返回json
//                返回对象
             ServerJson.returnJson(response,obj);
        }else  if(type == RestType.TEXT) {
             ServerJson.renderText(response, obj.toString());
         }else {
            throw  new NotFoundType();
        }
      }
}
