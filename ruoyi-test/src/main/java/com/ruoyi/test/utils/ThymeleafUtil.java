package com.ruoyi.test.utils;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import com.ruoyi.common.constant.Constants;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ThymeleafUtil {
    public static final String EX_PDF = "ExPdf.html";
    public static final String EX_PDF_THYMELEAF = "ExPdf-thymeleaf.html";
    public static final String INDEX = "index.html";

    private static TemplateEngine init() {
        // 设置参数
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("template/pdf/");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding(Constants.UTF8);

        // 参数回填并实例化引擎
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    public static String getThymeleafHtmlContent(String templateName, Map<String,Object> params) {
        TemplateEngine templateEngine = init();

        // context为模板替换的入参
        Context context = new Context();
        context.setVariables(params);

        // 处理模板 - 回填值
        return templateEngine.process(templateName, context);
    }

    public static byte[] exportPdfFile(String templateName, Map<String, Object> params) {
        String htmlContent = getThymeleafHtmlContent(templateName, params);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ITextRenderer renderer = new ITextRenderer();
            // 中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("fonts/msyh.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return outputStream.toByteArray();
    }
}
