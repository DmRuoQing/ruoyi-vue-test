package com.ruoyi.common.annotation.serializer;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.ruoyi.common.annotation.Desensitize;
import com.ruoyi.common.enums.DesensitizeType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class DesensitizeSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private DesensitizeType type;
    private Integer start;
    private Integer end;
    private String maskingChar;

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String value = String.valueOf(object);
        switch (type) {
            // userId
            case USER_ID:
                jsonGenerator.writeString(String.valueOf(DesensitizedUtil.userId()));
                break;
            // 中文名
            case CHINESE_NAME:
                jsonGenerator.writeString(DesensitizedUtil.chineseName(value));
                break;
            // 身份证号
            case ID_CARD:
                jsonGenerator.writeString(DesensitizedUtil.idCardNum(value, 1, 2));
                break;
            // 座机
            case FIXED_PHONE:
                jsonGenerator.writeString(DesensitizedUtil.fixedPhone(value));
                break;
            // 手机号
            case MOBILE_PHONE:
                jsonGenerator.writeString(DesensitizedUtil.mobilePhone(value));
                break;
            // 地址
            case ADDRESS:
                jsonGenerator.writeString(DesensitizedUtil.address(value, 8));
                break;
            // 邮箱
            case EMAIL:
                jsonGenerator.writeString(DesensitizedUtil.email(value));
                break;
            // 密码
            case PASSWORD:
                jsonGenerator.writeString(DesensitizedUtil.password(value));
                break;
            // 中国大陆车牌号
            case CAR_LICENSE:
                jsonGenerator.writeString(DesensitizedUtil.carLicense(value));
                break;
            // 银行卡
            case BANK_CARD:
                jsonGenerator.writeString(DesensitizedUtil.bankCard(value));
                break;
            case IPV4:
                jsonGenerator.writeString(DesensitizedUtil.ipv4(value));
                break;
            case IPV6:
                jsonGenerator.writeString(DesensitizedUtil.ipv6(value));
                break;
            // 只显示第一个字符
            case FIRST_MASK:
                jsonGenerator.writeString(DesensitizedUtil.firstMask(value));
                break;
            // 自定义
            case CUSTOM:
                jsonGenerator.writeString(CharSequenceUtil.replace(value, start, value.length() - end, maskingChar));
                break;
            default:
                break;
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            // 判断数据类型是否为String类型
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                // 获取定义的注解
                Desensitize desensitize = beanProperty.getAnnotation(Desensitize.class);
                // 为null
                if (desensitize == null) {
                    desensitize = beanProperty.getContextAnnotation(Desensitize.class);
                }
                // 不为null
                if (desensitize != null) {
                    // 创建定义的序列化类的实例并且返回，入参为注解定义的type,开始位置，结束位置。
                    return new DesensitizeSerializer(
                            desensitize.type(),
                            desensitize.start(),
                            desensitize.end(),
                            desensitize.maskingChar()
                    );
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
