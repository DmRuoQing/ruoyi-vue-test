package com.ruoyi.test.utils;

import com.ruoyi.common.constant.Constants;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.util.Map;

public class ThymeleafUtil {
    public static final String EX_PDF = "ExPdf.html";
    public static final String INDEX = "index.html";

    /**
     * 用于获取 Thymeleaf 模板引擎
     *
     * @return 模板引擎
     */
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

    /**
     * 通过模板将参数回填到html
     *
     * @param templateName 模板名 - 该类所在的模块的resources下的 /template/pdf/ 中
     * @param params 参数
     * @return html文本
     */
    public static String getThymeleafHtmlContent(String templateName, Map<String,Object> params) {
        TemplateEngine templateEngine = init();

        // context为模板替换的入参
        Context context = new Context();
        context.setVariables(params);

        // 处理模板 - 回填值
        return templateEngine.process(templateName, context);
    }
}