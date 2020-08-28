package com.jushi.library.viewinject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控件查找注解，不需要再使用findViewById
 * <p/>
 * <pre>
 * // 使用方法
 * &#064;FindViewById(R.id.view_inject_textview)
 * private TextView textView;
 * </pre>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FindViewById {
    int value();
}
