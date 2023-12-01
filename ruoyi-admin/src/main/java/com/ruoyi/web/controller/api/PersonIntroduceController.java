package com.ruoyi.web.controller.api;

import com.ruoyi.test.domain.PersonIntroduce;
import com.ruoyi.test.service.PersonIntroduceService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PersonIntroduceController {
    @Autowired
    private PersonIntroduceService personIntroduceService;

    @GetMapping("/exPdf")
    public String exPdfPersonIntroduce() throws TemplateException, IOException {
        PersonIntroduce personIntroduce = new PersonIntroduce();
        personIntroduce.setPersonName("张三");
        personIntroduce.setAddress("杭州");
        personIntroduce.setPersonAge(23);
        personIntroduce.setPersonGender("男");
        personIntroduce.setPersonalityDesc("这个人有点懒");
        personIntroduce.setPersonVocation("Java后端");
        return personIntroduceService.exPersonIntroduce(personIntroduce);
    }
}
