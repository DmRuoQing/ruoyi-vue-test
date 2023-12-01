package com.ruoyi.test.domain;

import lombok.Data;

@Data
public class PersonIntroduce {
    //名称
    private String personName ;
    //年龄
    private Integer personAge ;
    //性格描述
    private String personalityDesc;
    //性别
    private String personGender;
    //职业
    private String personVocation;
    //现居地址
    private String address;
    //创建时间
    private String createTime;
}
