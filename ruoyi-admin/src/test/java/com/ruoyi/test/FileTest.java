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
    void test02() throws IOException {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> personIntroduce = new HashMap<>();
        personIntroduce.put("personName", "张三");
        personIntroduce.put("address", "杭州");
        personIntroduce.put("personAge", 23);
        personIntroduce.put("personGender", "男");
        personIntroduce.put("personalityDesc", "这个人有点懒");
        personIntroduce.put("personVocation", "Java后端");
        param.put("person", personIntroduce);
        // html转pdf
        String templateContent = PdfUtil.getTemplateContent(PdfUtil.EX_PDF, param);
        byte[] resources = PdfUtil.html2Pdf(templateContent);
        // 添加水印
        byte[] data = PdfUtil.addWaterMark("中文测试abc", resources);
        FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
    }

    @Test
    void test02_2() throws IOException {
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
        byte[] resources = ThymeleafUtil.exportPdfFile(ThymeleafUtil.EX_PDF_THYMELEAF, param);
        // 添加水印
        byte[] data = PdfUtil.addWaterMark("中文测试abc", resources);
        FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
    }

    @Test
    void test02_3() throws IOException {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> patient = new HashMap<>();
        patient.put("name", "张三");
        param.put("patient", patient);
        // html转pdf
        byte[] resources = ThymeleafUtil.exportPdfFile(ThymeleafUtil.INDEX, param);
        // 添加水印
//        byte[] data = PdfUtil.addWaterMark("中文测试abc", resources);
//        FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
        FileUtils.writeBytes(resources, RuoYiConfig.getImportPath(), "pdf");
    }

    @Test
    void test02_4() throws IOException {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> patient = new HashMap<>();
        patient.put("name", "张三");
        param.put("patient", patient);
        // html字符串
        String htmlContent = ThymeleafUtil.getThymeleafHtmlContent(ThymeleafUtil.INDEX, param);
        // 导出
        FileUtils.writeBytes(htmlContent.getBytes(), RuoYiConfig.getImportPath(), "html");
    }

    @Test
    void test03() throws IOException {
        // TODO 不可用
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> patient = new HashMap<>();
        patient.put("name", "张三");
        param.put("patient", patient);
        // html转pdf
        String templateContent = PdfUtil.getTemplateContent(PdfUtil.INDEX, param);
        byte[] resources = PdfUtil.html2Pdf(templateContent);
        // 添加水印
        byte[] data = PdfUtil.addWaterMark("中文测试abc", resources);
        FileUtils.writeBytes(data, RuoYiConfig.getImportPath(), "pdf");
    }
}
