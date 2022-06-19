package com.example.ssmdemo.Controller;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 阿帕奇 POI 导出excel入口
 * @author ZMH
 * @date 2022/6/15 17:30
 */

@Controller
@RequestMapping("/excel")
public class ExportController {

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        String[] tableHandler = {"id", "name", "sex", "age", "phoneNumber"};
        List<String[]> tableData = new ArrayList<>();
        tableData.add(new String[]{"00001", "张三", "男", "12", "110"});
        tableData.add(new String[]{"00002", "李四", "男", "14", "120"});
        tableData.add(new String[]{"00003", "王五", "男", "23", "12135"});
        tableData.add(new String[]{"00004", "赵六", "男", "42", "12163"});
        tableData.add(new String[]{"00005", "宋七", "男", "12", "11086"});
        tableData.add(new String[]{"00006", "老八", "男", "24", "119"});
        tableData.add(new String[]{"00007", "老九", "男", "19", "10000"});
        tableData.add(new String[]{"00008", "法外狂徒", "男", "81", "10010"});
        tableData.add(new String[]{"00009", "小三", "女", "18", "666"});
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1 = workbook.createSheet("Sheet1");
        //字体样式
        HSSFFont font = workbook.createFont();
        font.setFontName("");
        font.setBold(true);
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setFontHeightInPoints((short) 50);
        //标题字体样式
        HSSFFont font1 = workbook.createFont();
        font1.setFontHeightInPoints((short) 16);
        //表体字体样式
        HSSFFont font2 = workbook.createFont();
        font1.setFontHeightInPoints((short) 16);
        //头部综合样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //标头样式
        HSSFCellStyle cellStyle1 =workbook.createCellStyle();
        //设置边框防止背景颜色覆盖边框
        cellStyle1.setBorderLeft(BorderStyle.THIN);//普通线性边框
        cellStyle1.setBorderRight(BorderStyle.THIN);
        cellStyle1.setBorderBottom(BorderStyle.THIN);
        cellStyle1.setBorderTop(BorderStyle.THIN);
        cellStyle1.setFont(font1);
        //标题样式
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle1.setFont(font2);

        sheet1.setColumnWidth(0, 20 * 256); // 设置列的宽度
        sheet1.setColumnWidth(1, 20 * 256); // 设置列的宽度
        sheet1.setColumnWidth(2, 20 * 256); // 设置列的宽度
        sheet1.setColumnWidth(3, 20 * 256); // 设置列的宽度
        sheet1.setColumnWidth(4, 20 * 256); // 设置列的宽度
        //将第一行的前五个单元格合并成一个单元格
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        HSSFRow row = sheet1.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("通讯录");

        //循环填充标头数据
        HSSFRow row1 = sheet1.createRow(1);
        for (int i = 0; i < tableHandler.length; i++) {
            HSSFCell cell1 = row1.createCell(i);
            //填充类型
            cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //填充前景色
            cellStyle1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
            cell1.setCellStyle(cellStyle1);
            cell1.setCellValue(tableHandler[i]);
        }

        //循环填充表体数据
        for (int i = 0; i < tableData.size(); i++) {
            HSSFRow row2 = sheet1.createRow(i + 2);
            String[] cellData = tableData.get(i);
            for (int j = 0; j < cellData.length; j++) {
                HSSFCell cell1 = row2.createCell(j);
                cell1.setCellStyle(cellStyle2);
                cell1.setCellValue(cellData[j]);
            }
        }

        try {
            OutputStream outputStream = response.getOutputStream();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=通讯录.xls");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}