package com.ruoyi.common.enums;

/**
 * 数据脱敏策略
 *
 * @Author DmRuoQing
 * @Create 2023/10/24 14:19
 */
public enum DesensitizeType {
    /**
     * 用户ID
     */
    USER_ID,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 邮箱
     */
    EMAIL,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 中国大陆车牌号
     */
    CAR_LICENSE,
    /**
     * 银行卡号
     */
    BANK_CARD,
    /**
     * IPv4地址
     */
    IPV4,
    /**
     * IPv6地址
     */
    IPV6,
    /**
     * 定义了一个first_mask的规则，只显示第一个字符。
     */
    FIRST_MASK,
    /**
     * 自定义类型
     */
    CUSTOM;
}
