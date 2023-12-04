package com.ruoyi.test.domain;

import com.ruoyi.common.annotation.Desensitize;
import com.ruoyi.common.enums.DesensitizeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Patient", description = "患者信息")
@Data
public class Patient {

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("姓名")
    @Desensitize(type = DesensitizeType.CUSTOM, start = 1, end = 1, maskingChar = "$")
    private String name;

    @ApiModelProperty("手机号")
    @Desensitize(type = DesensitizeType.MOBILE_PHONE)
    private String phoneNumber;

    @ApiModelProperty("身份证号")
    private String idCard;
}
