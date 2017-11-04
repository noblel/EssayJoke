package com.noblel.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Noblel
 * View注解的Annotation
 * Target:
 *   FIELD:属性
 *   METHOD:方法
 *   TYPE:类
 *   CONSTRUCTOR:构造函数
 * Retention:
 * CLASS:编译时
 * RUNTIME:运行时
 * SOURCE:源码时
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    //-->@ViewById(R.id.xxx)
    int value();
}
