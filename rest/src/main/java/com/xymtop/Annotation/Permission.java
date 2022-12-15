package com.xymtop.Annotation;

import com.xymtop.Config.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//权限管理的注解
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Permission {
    PermissionType type() default PermissionType.LOGIN;
}
