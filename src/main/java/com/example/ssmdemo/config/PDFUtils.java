package com.example.ssmdemo.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
/**
 * itext 生成复杂PDF
 * @author ZMH
 * @date 2022/9/15 17:29
 */
public class PDFUtils {

    //边框边距
    private static final Float TOTAL_LEFT_AND_RIGHT_SPACING = 0F;

    private static final String PDF_SUFFIX = ".pdf";

    private static final String local_report_path = "/Users/zhangmeihui/Downloads";
    /**
     * 创建报告PDF
     * @author ZMH
     * @date 2022/9/15 11:37
     * @param jsonObject
     * @return String
     */
    public String createPDF(JSONObject jsonObject) {
        // 创建文件及相关目录
        String pdfName = jsonObject.get("uuid") + PDF_SUFFIX;
        String outPath = local_report_path + File.separator + pdfName;
        JSONArray jsonFileData = jsonObject.getJSONArray("data");
        File file = new File(outPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Document document = new Document();
        try {
            // 创建PdfWriter对象
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outPath));
            // 设置每行的间距
            writer.setInitialLeading(30);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        // 设置文档属性
        // 作者
        document.addAuthor("Zhang Mei Hui");
        // 创建日期
        document.addCreationDate();
        // 创建关键字
        document.addKeywords("");
        // 创建生产商，自动使用iText
        document.addProducer();
        // 创建程序
        document.addCreator("ZhangMeiHui");
        // 标题
        document.addTitle(jsonObject.getString("questiontype"));
        // 主题
        document.addSubject("测评报告");
        // 打开文档
        document.open();


        /**
         * 标题字体样式
         */
        JSONObject fontJson = new JSONObject();
        fontJson.put("fontSize", 13F);//字体大小
        fontJson.put("fontColor", "255,255,255");//颜色
        fontJson.put("bold", true);//加粗
        fontJson.put("italic", false);//斜体
        fontJson.put("underline", false);//下划线
        fontJson.put("italicAndDeleteLine", false);//斜体加删除线
        Font titleFont = createFont(fontJson);

        /**
         * 标题左侧图片
         */
        JSONObject imgJson = new JSONObject();
        imgJson.put("imgFilePath", "/Users/zhangmeihui/Downloads/imspp.jpg");
        imgJson.put("alignment", "left");
        Image img = createImg(imgJson);
        img.scaleAbsolute(20, 20);
        img.setBackgroundColor( new BaseColor(109,109,109));

        //报告文件主要内容
        JSONObject reportInfo = jsonFileData.getJSONObject(0);
        PdfPTable titleTable = createTable(new float[]{30,480});
        //获取所有单元格背景颜色
        titleTable.getDefaultCell().setBackgroundColor(new BaseColor (109,109,109));
        //获取所有单元格无边框
        titleTable.getDefaultCell().setBorder(0);
        titleTable.setSpacingAfter(5F);
        titleTable.addCell(img);
        PdfPCell titleCell = createCell("创新和知识产权管理体系国际标准达标量化测评", titleFont, Element.ALIGN_CENTER);
        titleCell.setBorder(0);
        titleTable.addCell(titleCell);

        /**
         * 报告主体字体样式
         */
        JSONObject fontJson1 = new JSONObject();
        fontJson1.put("fontSize", 8F);//字体大小
        fontJson1.put("fontColor", "0,0,0");//颜色
        fontJson1.put("bold", true);//加粗
        fontJson1.put("italic", false);//斜体
        fontJson1.put("underline", false);//下划线
        fontJson1.put("italicAndDeleteLine", false);//斜体加删除线
        Font font1 = createFont(fontJson1);

        /**
         * 标头字体样式
         */
        JSONObject tableHeaderJson = new JSONObject();
        tableHeaderJson.put("fontSize", 8F);//字体大小
        tableHeaderJson.put("fontColor", "0,0,0");//颜色
        tableHeaderJson.put("bold", true);//加粗
        tableHeaderJson.put("italic", false);//斜体
        tableHeaderJson.put("underline", false);//下划线
        tableHeaderJson.put("italicAndDeleteLine", false);//斜体加删除线
        Font tableHeader = createFont(tableHeaderJson);
        tableHeader.setColor(new BaseColor(255, 255, 255));


        Paragraph companyInfo = new Paragraph(
                "企业规模:    "+reportInfo.getString("company_guimo")+"        企业是否上市:    "+reportInfo.getString("company_isshangshi")+"\n" +
                        "统一信用代码:    "+reportInfo.getString("company_code")+"        所属领域:    "+reportInfo.getString("company_lingyu"));
        companyInfo.setFont(font1);
        //设置上空
        companyInfo.setSpacingBefore(0F);
        //explanation.setAlignment(Element.ALIGN_CENTER);
        //右空边距
        companyInfo.setIndentationLeft(TOTAL_LEFT_AND_RIGHT_SPACING);
        companyInfo.setIndentationRight(TOTAL_LEFT_AND_RIGHT_SPACING);
        //行间距
        companyInfo.setLeading(20F);
        //首行缩进
        companyInfo.setFirstLineIndent(0F);
        //试图将一个段落放在同一页中，该方法并不是始终有效
        companyInfo.setKeepTogether(true);


        Paragraph explanation = new Paragraph("感谢信任并完成《创新管理与知识产权国际标准测评（ISO56005/56002）》。" +
                "该系统依托领先创新评判模型，对标创新管理国际标准，参考国内知识产权工作特点和具体情况，以及从资本视角对创新和知识产权考评要求，" +
                "借助大数据逻辑推理和人工智能算法推算，可用于初步判断企业的创新及知识产权管理现状和创新与知识产权融合管理能力。" +
                "被测企业可通过测评，发现其在12个维度50个单项的直接和间接计算指标与“标准参考值”对比的量化优势和短板，从而可以聚集资源、精准补短，从创新与知识产权的维度来增强企业的核心竞争力，" +
                "为实现数字化转型升级、成为知识产权优势示范企业、专精特新小巨人、融资和上市等资质和资本需求奠定基础。" +
                "测评体系从管理导向和结果导向两个维度测评企业创新与知识产权融合管理的能力和效果 。");
        explanation.setFont(font1);
        //设置上空
        explanation.setSpacingBefore(-5F);
        //explanation.setAlignment(Element.ALIGN_CENTER);
        //右空边距
        explanation.setIndentationLeft(TOTAL_LEFT_AND_RIGHT_SPACING);
        explanation.setIndentationRight(TOTAL_LEFT_AND_RIGHT_SPACING);
        //行间距
        explanation.setLeading(10F);
        //首行缩进
        explanation.setFirstLineIndent(16F);
        //试图将一个段落放在同一页中，该方法并不是始终有效
        explanation.setKeepTogether(true);

        Paragraph explanation1 = new Paragraph("管理导向测评，主要参考ISO56005-2020《创新管理 知识产权管理 指南》（简称“ISO56005”），同时融入ISO560002-2019《创新管理体系 指南 》（简称“ISO56002”）,结合细分领域的客观现状，量化评判企业在创新组织管理、知识产权、战略方向、创新行动、创新支持和系统方法等六个维度及28个单项的强度，并根据每个单项在整体测评中的权重，生成单项得分。报告中每个单项都标注了与国际标准对应的章节，以方便企业对照标准进行改进；测评数据100%摘自企业自填《调查表》，由企业自行对数据的真实性、可靠性负责。 \n" +
                "结果导向测评，主要根据ISO56000系列国际标准中创新管理“价值实现”的基本原则，同时参照ISO56005“知识产权管理应为所有相关方创造价值”的基本准则。指标设计以资本闭环和创新提升维度，通过对标科创板、创业板和北交所的上市分，不设下限。");
        explanation1.setFont(font1);
        //设置上空
        explanation1.setSpacingBefore(0F);
        //explanation.setAlignment(Element.ALIGN_CENTER);
        //右空边距
        explanation1.setIndentationLeft(TOTAL_LEFT_AND_RIGHT_SPACING);
        explanation1.setIndentationRight(TOTAL_LEFT_AND_RIGHT_SPACING);
        //行间距
        explanation1.setLeading(10F);
        //首行缩进
        explanation1.setFirstLineIndent(16F);
        //试图将一个段落放在同一页中，该方法并不是始终有效
        explanation1.setKeepTogether(true);

        Paragraph explanation2 = new Paragraph("本测评满分为100分。分数段80分以上说明企业可能已经超过了创新和知识产权管理体系国际标准初级要求，或可在ISO56005国际标准分级评价中位于第二级至第三级水平，分数段60-80分说明企业可能达到了创新和知识产权管理国际标准初级要求，" +
                "或可在ISO56005国际标准分级评价中位于第一级至第二级水平；分数段40-59分，说明企业距离ISO56005国际标准尚有差距，经过系列提升，" +
                "有望达到创新和知识产权管理体系初级版国际标准；" +
                "分数段20-39分，说明距离达标创新 和知识产权管理体系初级版国际标准还有较大差距；20分以下分数段，说明创新和综合实力还处于比较初级阶段，" +
                "需要通过较长期准备，方能达到创新和知识产权管理体系初级版国际标准。");
        explanation2.setFont(font1);
        //设置上空
        explanation2.setSpacingBefore(5F);
        //explanation.setAlignment(Element.ALIGN_CENTER);
        //右空边距
        explanation2.setIndentationLeft(TOTAL_LEFT_AND_RIGHT_SPACING);
        explanation2.setIndentationRight(TOTAL_LEFT_AND_RIGHT_SPACING);
        //行间距
        explanation2.setLeading(10F);
        //首行缩进
        explanation2.setFirstLineIndent(16F);
        //试图将一个段落放在同一页中，该方法并不是始终有效
        explanation2.setKeepTogether(true);

        Paragraph explanation3 = new Paragraph("需要特别说明的是，本套测评是通过数据收集、实景分析、逻辑设置，精准画像等步骤推算生成的一套指标体系和参考值，参考值按《国民经济行业分类（GB/T 4754—2017）》97个 行业定义细分参考值及对比体系，以体现对不同行业企业的普适性。" +
                "本测评仅提供基于统计和算法的量化分析，缺少来自专业人士的定性分析，因此仅适用于对各个发展阶段创新企业的创新和知识产权能力做初步筛选和评判，不能做为创新管理与知识产权国际标准标准符合性对标的最终依据。" +
                "企业在获得报告的基础上，宜以ISO56005国际标准内容为基础，认真对标企业创新与知识产权工作管理现状，寻找差距并制定改进计划，通过国际标准贯标流程来改进和提升企业创新与知识产权管理能力。");
        explanation3.setFont(font1);
        //设置上空
        explanation3.setSpacingBefore(0F);
        explanation3.setSpacingAfter(10F);
        //explanation.setAlignment(Element.ALIGN_CENTER);
        //右空边距
        explanation3.setIndentationLeft(TOTAL_LEFT_AND_RIGHT_SPACING);
        explanation3.setIndentationRight(TOTAL_LEFT_AND_RIGHT_SPACING);
        //行间距
        explanation3.setLeading(10F);
        //首行缩进
        explanation3.setFirstLineIndent(16F);
        //试图将一个段落放在同一页中，该方法并不是始终有效
        explanation3.setKeepTogether(true);

        Paragraph explanation4 = new Paragraph("以上是示范测基础版报告，如贵司希望测评在上市、融资、专精特新等其它项目的的达标现状，或审阅收费版派富高级版测评报告《科创属性深度分析及提升建议》，请发送邮" +
                "件info@paif.com。《深度分析》将针对贵司在16个维度和88个单项的量化对比情况，详细解读并提出个性化提升整改建议，报告要点请见附件说明。");
        explanation4.setFont(font1);
        //设置上空
        explanation4.setSpacingBefore(10F);
        explanation4.setSpacingAfter(0F);
        //explanation.setAlignment(Element.ALIGN_CENTER);
        //右空边距
        explanation4.setIndentationLeft(TOTAL_LEFT_AND_RIGHT_SPACING);
        explanation4.setIndentationRight(TOTAL_LEFT_AND_RIGHT_SPACING);
        //行间距
        explanation4.setLeading(10F);
        //首行缩进
        explanation4.setFirstLineIndent(16F);
        //试图将一个段落放在同一页中，该方法并不是始终有效
        explanation4.setKeepTogether(true);

        //测评评价
        PdfPTable evaluateTable = createTable(new float[]{20,40,10,440});
        evaluateTable.setSpacingAfter(10F);
        evaluateTable.addCell(createCell("测评得分",font1));
        evaluateTable.addCell(createCell(reportInfo.getString("score"),font1));
        evaluateTable.addCell(createCell("简短快评",font1));
        evaluateTable.addCell(createCell(reportInfo.getString("summary_preview"),font1));

        //详细信息
        JSONObject result_josn = reportInfo.getJSONObject("result_josn");
        PdfPTable table = createTable(new float[]{120, 160, 40, 160, 100});
        PdfPTable subtable = new PdfPTable(new float[]{80});
        subtable.setTotalWidth(80);
        subtable.setLockedWidth(true);
        subtable.setHorizontalAlignment(Element.ALIGN_CENTER);
        subtable.getDefaultCell().setBorder(0);
        PdfPCell yes = createCell("yes", tableHeader, Element.ALIGN_CENTER);
        yes.setBorder(0);
        subtable.addCell(yes).setRowspan(7);
        //table.addCell(createCell("测评单项", font, Element.ALIGN_LEFT, 6, false));
        table.addCell(createCell("测评单项", tableHeader, Element.ALIGN_CENTER));
        table.addCell(createCell("单项测评结果", tableHeader, Element.ALIGN_CENTER));
        table.addCell(createCell("优劣量化比例", tableHeader, Element.ALIGN_CENTER));
        table.addCell(createCell("提升方案及服务产品要点", tableHeader, Element.ALIGN_CENTER));
        table.addCell(createCell("对标索引", tableHeader, Element.ALIGN_CENTER));
        int size = result_josn.size();

        for (int i = 0; i < size; i++) {
            JSONObject tableJson = result_josn.getJSONObject(i + "");
            table.addCell(createCell(tableJson.getString("title"), font1, Element.ALIGN_LEFT, 5, true));
            JSONArray info = tableJson.getJSONArray("info");
            for (Object o : info) {
                JSONObject cellJson = (JSONObject) o;
                int size1 = cellJson.size();
                for (int j = 0; j < size1; j++) {
                    if (j == 0) {
                        PdfPCell cell = createCell(cellJson.getString(j + ""), font1);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        table.addCell(cell);
                    } else {
                        table.addCell(createCell(cellJson.getString(j + ""), font1));
                    }
                }
                table.addCell(subtable);
            }
        }

        try {
            document.add(titleTable);
            document.add(companyInfo);
            document.add(explanation);
            document.add(explanation1);
            document.add(explanation2);
            document.add(explanation3);
            document.add(evaluateTable);
            document.add(table);
            document.add(explanation4);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        return pdfName;
    }

    public Font createFont(JSONObject jsonObject){
        // 方式三：使用iTextAsian.jar中的字体
        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(baseFont);
        // 设置字体大小
        font.setSize(jsonObject.getFloat("fontSize"));
        // 设置字体颜色
        String fontColor = jsonObject.getString("fontColor");
        String[] color = fontColor.split(",");
        font.setColor(
                new BaseColor(
                        Integer.parseInt(color[0]),
                        Integer.parseInt(color[1]),
                        Integer.parseInt(color[2])
                )
        );
        // 设置类型，加粗
        if (jsonObject.getBoolean("bold")) {
            font.setStyle(Font.BOLD);
        }
        // 设置类型，倾斜
        if (jsonObject.getBoolean("italic")) {
            font.setStyle(Font.ITALIC);
        }
        // 设置类型，下划线
        if (jsonObject.getBoolean("underline")) {
            font.setStyle(Font.UNDERLINE);
        }
        // 设置类型，可组合，倾斜+删除线
        if (jsonObject.getBoolean("italicAndDeleteLine")) {
            font.setStyle(Font.ITALIC | Font.STRIKETHRU);
        }
        // 设置类型，为正常
        font.setStyle(Font.NORMAL);
        return font;
    }


    @SneakyThrows
    public Image createImg(JSONObject jsonObject) {
        String img = jsonObject.getString("imgFilePath");
        // 添加本地图片
        Image image1 = Image.getInstance(img);
        // 设置绝对位置，以左下角为原点
        //image1.setAbsolutePosition(50f, 700f);
        String alignment = jsonObject.getString("alignment");
        // 设置居中
        if (alignment.equals("middle")) {
            image1.setAlignment(Image.MIDDLE);
            // 设置居左
        } else if (alignment.equals("left")) {
            image1.setAlignment(Image.LEFT);
            // 设置居右
        } else if (alignment.equals("right")) {
            image1.setAlignment(Image.RIGHT);
            // 设置文字绕图形显示
        } else if (alignment.equals("textWrap")) {
            image1.setAlignment(Image.TEXTWRAP);
            // 设置图形作为文字的背景显示
        } else if (alignment.equals("underlying")) {
            image1.setAlignment(Image.UNDERLYING);
        }
        // 也可以综合上面使用
        //image1.setAlignment(Image.RIGHT | Image.TEXTWRAP);
        // 按百分比缩放
        //image1.scalePercent(50);
        // 根据宽高分别缩放
        //image1.scalePercent(50, 70);
        // 设置到绝对大小
        //image1.scaleAbsolute(30, 30);
        return image1;
    }

    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(520);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 创建单元格(指定字体)
     *
     * @param value
     * @param font
     * @return
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..）
     *
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBackgroundColor(new BaseColor(109, 109, 109));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        //跨列
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        //行高
        cell.setFixedHeight(15);
        cell.setPadding(3.0f);
        if (!boderFlag) {
            //cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(0.0f);
        } else if (boderFlag) {
            //cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(0.0f);
        }
        cell.setBackgroundColor(new BaseColor(199, 199, 199));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }

    /**
     * 将文件转化为字符串
     * @param fileName
     * @return
     */
    public static String getStringByFile(String fileName) {
        File file = new File(fileName);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line = null;
            while (((line = br.readLine()) != null)) {
                sb.append(line);
            }
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        PDFUtils pdfUtil = new PDFUtils();

        String stringByFile = pdfUtil.getStringByFile("/Users/zhangmeihui/Downloads/0E81E8FD8F7345AF849E963E37080638.json");
        JSONObject pdfjson = JSONObject.parseObject(stringByFile);
        pdfjson.put("uuid", 11111);
        pdfjson.put("questiontype", "ISO_9000");
        pdfUtil.createPDF(pdfjson);

    }
}

