package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.test.domain.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestApi {

    @GetMapping("/info")
    public R<Patient> getPatient() {
        Patient patient = new Patient();

        patient.setName("奥特曼");
        patient.setPhoneNumber("16666666666");

        return R.ok(patient);
    }
}
