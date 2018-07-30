package cn.wzy.annotation;

import java.lang.annotation.*;

/**
 * Create by Wzy
 * on 2018/7/30 10:02
 * 不短不长八字刚好
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MGColName {
    String value() default "";
}
