package com.ruoyi.test.domain;

import com.ruoyi.common.annotation.Desensitize;
import com.ruoyi.common.enums.DesensitizeType;
import lombok.Data;

@Data
public class Patient {

    private Long id;

    @Desensitize(type = DesensitizeType.CUSTOM, start = 1, end = 1, maskingChar = "$")
    private String name;

    @Desensitize(type = DesensitizeType.MOBILE_PHONE)
    private String phoneNumber;

    private String idCard;
}
