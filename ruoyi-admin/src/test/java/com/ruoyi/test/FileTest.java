package com.ruoyi.test;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.test.utils.PdfUtil;
import com.ruoyi.test.utils.ThymeleafUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author DmRuoQing
 * @Create 2023/08/16 14:18
 */
@SpringBootTest
public class FileTest {

    @Test
    void test02_1() throws IOException {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> personIntroduce = new HashMap<>();
        personIntroduce.put("en", "world");
        personIntroduce.put("personName", "张三");
        personIntroduce.put("address", "杭州");
        personIntroduce.put("personAge", 23);
        personIntroduce.put("personGender", "男");
        personIntroduce.put("personalityDesc", "这个人有点懒");
        personIntroduce.put("personVocation", "Java后端");
        param.put("person", personIntroduce);
        // html转pdf
        String htmlContent = ThymeleafUtil.getThymeleafHtmlContent(ThymeleafUtil.EX_PDF, param);
        byte[] temp = PdfUtil.html2Pdf_IText(htmlContent);
        byte[] data = PdfUtil.addWaterMark("中文测试abc", temp);
        FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
    }

    @Test
    void test02_2() throws IOException {
        // 参数构造
        Map<String, Object> params = getReportParams();
        // html转pdf
        String htmlContent = ThymeleafUtil.getThymeleafHtmlContent(ThymeleafUtil.INDEX, params);
        byte[] data = PdfUtil.html2Pdf_FlyingSaucer(htmlContent);
        FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
    }

    @Test
    void test02_2_2() throws IOException {
        // 参数构造
        Map<String, Object> params = getReportParams();
        // html转pdf
        String htmlContent = ThymeleafUtil.getThymeleafHtmlContent(ThymeleafUtil.INDEX, params);
        byte[] data = PdfUtil.html2Pdf_IText(htmlContent);
        FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
    }

    private Map<String, Object> getReportParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> patient = new HashMap<>();
        patient.put("name", "张三");
        patient.put("sex", "男");
        patient.put("birthday", "2023-11-30");
        patient.put("age", 10);
        patient.put("archivesNum", "20231130172811530001");
        params.put("patient", patient);

        Map<String, Object> diagnosis = new HashMap<>();
        diagnosis.put("createTime", "2023-11-30 10:16:07");
        params.put("diagnosis", diagnosis);

        Map<String, Object> record = new HashMap<>();
        record.put("createTime", "2021-12-23 10:30:16");
        Map<String, Object> jing = new HashMap<>();
        jing.put("value", 513);
        record.put("jing", jing);
        Map<String, Object> yuan = new HashMap<>();
        yuan.put("value", 418);
        record.put("yuan", yuan);
        params.put("record", record);

        return params;
    }
}
