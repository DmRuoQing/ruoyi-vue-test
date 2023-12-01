package com.ruoyi.test.service;

import com.ruoyi.test.domain.PersonIntroduce;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @Author DmRuoQing
 * @Create 2023/6/1
 */
public interface PersonIntroduceService {

    String exPersonIntroduce(PersonIntroduce personIntroduce) throws IOException, TemplateException;
}
