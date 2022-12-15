# Rest

#### 介绍
Rest框架，基于sevelet的一个小型后端接口框架，适合一些只能用原生servelet写接口的环境

#### 软件架构
> 该框架基于原生的servelet来开发，可以实现一些小型的只能用原生servelet写前后端分离后端接口的一个框架。

> 即下即用，把代码拉下去就可以跑

> 基于注解，方便操作

> 可扩展性强


#### 使用说明

1. 检查过滤器配置，过滤器配置应该为ApplicationFilter类
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- 过滤器声明 -->
    <filter>
        <!-- 过滤器的名称 -->
        <filter-name>App</filter-name>
        <!-- 过滤器的完整类名 -->
        <filter-class>com.xymtop.ApplicationFilter</filter-class>

    </filter>

    <!-- 过滤器映射 -->
    <filter-mapping>
        <!-- 过滤器名称 -->
        <filter-name>App</filter-name>
        <!-- 过滤器URL映射 -->
<!--        全部接管才能有效的达到拦截器的要求-->
        <url-pattern>*</url-pattern>
    </filter-mapping>
</web-app>
```

2. 创建控制器
```java
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

```
> 注意控制器创建的路径，如果路径不一样请在com.xymtop.Config下面的AppConfig下面修改
```java
package com.xymtop.Config;

/**
 * @ClassName : AppConfig
 * @Description : APP全局配置
 * @Author : 肖叶茂
 * @Date: 2022/12/16  0:58
 */
public class AppConfig {
    
//    这里是扫描控制器的包
    public static String  PACKAGE = "com.xymtop.Controller";
}

```


3. 创建控制器执行方法
```java
//    注意：这里必须要传参HttpServletRequest和HttpServletResponse
@Rest
@Mapping(url = "index")
public ResoultJson<String> get(HttpServletRequest request, HttpServletResponse response){
        return new ResoultJson<>(200);
}
```

> @Rest的参数类型列表
```java
//返回的数据类型
public enum RestType {
//    文本类型
    TEXT,
//    json格式数据
    JSON,
//    文件
    FILE,
//    不接管
    NONE
}
```

> 数据库对象操作示例
```java
    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // System.out.println((User) Dao.GetBean("select * from user where username= 1",
        // User.class.getCanonicalName()));
        // User u = new User();
        // u.id = "10005";
        // u.phone = "xxx";
        // u.password= "123";
        // u.username = "1";
        // u.address="456";

        // 下面是函数的用法
        // 1. 在数据库中更新一个对象对应的数据

        // System.out.println(Dao.Update(u, "user"));

        // 2.使用sql来查询多条数据
        // System.out.println(Service.Get("select * from
        // user",User.class.getCanonicalName()).get(0));

        // 3.获取多条和对象相关的数据
        // System.out.println(Service.GetList(u, "user"));

        // Service.InsertObject(u, "user");

        // Bianqian b = new Bianqian("1","1","2","1");
        // Service.DelObject(b, "bianqian");

    }
```

> Cookie操作
```java
//获取cookie
String getCookie(HttpServletRequest request , String name)
//设置cookie
setCookie(HttpServletResponse response, String name, String value)
```


> 时间戳
```java
public class Time {
    public  static  String getUnixTime(){
        Date date = new Date();//获取当前的日期
        Long unix =  System.currentTimeMillis();
       String str =  unix.toString();
        return  str;
    }

    public  static  String getTimeByUnix(String unix){
        Long timestamp =Long.valueOf(unix);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timestamp));
    }
}

```
# Good Night !!! 
