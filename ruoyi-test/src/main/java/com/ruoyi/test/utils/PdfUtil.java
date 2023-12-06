package com.ruoyi.test.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * pdf文件工具类 - 对css3的支持都不是很好
 *
 * @Author DmRuoQing
 * @Create 2023/12/01 15:58
 */
public class PdfUtil {

    /**
     * HTML转PDF
     * 使用 iTextPDF 库 - 仍存在样式问题
     *
     * @param htmlContent html内容
     * @return PDF字节数组
     */
    public static byte[] html2Pdf_IText(String htmlContent) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // 创建 PdfWriter 关联到输出流
            PdfWriter writer = new PdfWriter(outputStream);
            // PdfDocument 用于创建和管理 PDF 文件，而 Document 用于向 PDF 添加内容。
            PdfDocument pdf = new PdfDocument(writer);
            // 创建一个 Document 并关联到 pdfDocument
            Document document = new Document(pdf, PageSize.A4);

            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setCharset("UTF-8");
            FontProvider fontProvider = new FontProvider();
            // TODO 使用已有字体
            fontProvider.addSystemFonts();
            converterProperties.setFontProvider(fontProvider);
            HtmlConverter.convertToPdf(htmlContent, document.getPdfDocument(), converterProperties);
//            HtmlConverter.convertToPdf(htmlContent, outputStream, converterProperties);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("生成 PDF 失败: " + e.getMessage());
        }
        return outputStream.toByteArray();
    }

    /**
     * HTML转PDF
     * 使用 Flying Saucer 库 - 仍存在样式问题
     *
     * @param htmlContent html内容
     * @return PDF字节数组
     */
    public static byte[] html2Pdf_FlyingSaucer(String htmlContent) {
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
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 给pdf文件增加水印
     *
     * @param waterMarkText 水印文字
     * @param pdfFileBytes pdf字节文件
     * @return 新的pdf字节文件
     */
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