package com.ruoyi.test.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Map;

@Slf4j
public class PdfUtil {
    // 原始记录的Excel模板
    public static final String EX_PDF = "ExPdf.html";

    /**
     * 获取模板内容
     *
     * @param paramMap          模板参数
     * @return 结果
     */
    public static String getTemplateContent(String templateName, Map<String, Object> paramMap) {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        ClassTemplateLoader loader = new ClassTemplateLoader(
                PdfUtil.class, File.separator+ "template" + File.separator
        );
        Writer out = new StringWriter();
        try {
            configuration.setTemplateLoader(loader);
            Template template = configuration.getTemplate(templateName, "UTF-8");
            template.process(paramMap, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return out.toString();
    }

    /**
     * HTML 转 PDF
     *
     * @param content html内容
     * @return PDF字节数组
     */
    public static byte[] html2Pdf(String content) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setCharset("UTF-8");
            FontProvider fontProvider = new FontProvider();
            fontProvider.addSystemFonts();
            converterProperties.setFontProvider(fontProvider);
            HtmlConverter.convertToPdf(content, outputStream, converterProperties);
        } catch (Exception e) {
            log.error("生成 PDF 失败, {0}", e);
        }
        return outputStream.toByteArray();
    }

    public static byte[] addWaterMark(String waterMarkText, byte[] pdfFileBytes) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // 原PDF文件
            PdfReader reader = new PdfReader(pdfFileBytes);
            // 输出的PDF文件内容
            PdfStamper stamper = new PdfStamper(reader, outputStream);
            // 字体 win系统里复制出来的ttc为组合字体(即: 常规 粗体 斜体等(其余的复制出来被我删了)), 所以需要0来标记使用哪个, 此处使用常规字体; ttf为单一字体, 就不需要,0了
            BaseFont baseFont = BaseFont.createFont("/fonts/msyh.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            PdfGState gs = new PdfGState();
            // 设置透明度
            gs.setFillOpacity(0.2f);
            gs.setStrokeOpacity(0.3f);
            // 间隔距离
            int interval = 30;
            // 水印的宽高
            int textH = 50;
            int textW = 80;
            Rectangle pageRect = null;
            int totalPage = reader.getNumberOfPages() + 1;

            for (int i = 1; i < totalPage; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                // 内容上层
                PdfContentByte content = stamper.getOverContent(i);
                content.beginText();
                // 字体添加透明度
                content.setGState(gs);
                // 添加字体大小等
                content.setFontAndSize(baseFont, 18);
                for (int height = interval + textH; height < pageRect.getHeight(); height = height + textH * 3) {
                    for (int width = interval + textW; width < pageRect.getWidth() + textW; width = width + textW * 2) {
                        // 添加范围
                        content.showTextAligned(Element.ALIGN_LEFT, waterMarkText, width - textW, height - textH, 50);
                    }
                }
                content.endText();
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}

