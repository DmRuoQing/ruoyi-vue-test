package com.ruoyi.test.service.impl;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.test.domain.PersonIntroduce;
import com.ruoyi.test.service.PersonIntroduceService;
import com.ruoyi.test.utils.PdfUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PersonIntroduceServiceImpl implements PersonIntroduceService {

    @Override
    public String exPersonIntroduce(PersonIntroduce personIntroduce) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("person", personIntroduce);
        // html转pdf
        String templateContent = PdfUtil.getTemplateContent(PdfUtil.EX_PDF, param);
        byte[] resources = PdfUtil.html2Pdf(templateContent);
        // 添加水印
        byte[] data = PdfUtil.addWaterMark("中文测试abc", resources);
        return FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
    }
}
