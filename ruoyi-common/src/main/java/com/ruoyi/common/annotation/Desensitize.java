package com.ruoyi.common.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.annotation.serializer.DesensitizeSerializer;
import com.ruoyi.common.enums.DesensitizeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏注解
 *
 * @Author DmRuoQing
 * @Create 2023/10/24 14:17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeSerializer.class)
public @interface Desensitize {

    /**
     * 脱敏类型
     */
    DesensitizeType type() default DesensitizeType.CUSTOM;

    /**
     * 开始位置(包含)
     */
    int start() default 0;

    /**
     * 结束位置(不包含)
     */
    int end() default 0;

    /**
     * 遮罩字符
     */
    String maskingChar() default "*";
}
