package com.example.ssmdemo.domain.excel;

import com.example.ssmdemo.domain.Log;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.Date;

@Component
public class ExcelUtils {

    private final static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static void createExcel() throws IOException {
        // 获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        logger.info(desktop);
        String filePath = desktop + "/template.xls";
        logger.info(filePath);


        File file = new File(filePath);
        //输出流是写入
        OutputStream outputStream = new FileOutputStream(file);
        //创建excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //新建工作表
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        //创建新行createRow
        HSSFRow row = sheet.createRow(0);
        //创建单元格，参数1是单元格位置。
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("订单号");
        row.createCell(2).setCellValue("下单时间");
        row.createCell(3).setCellValue("个数");
        row.createCell(4).setCellValue("单价");
        row.createCell(5).setCellValue("订单金额");
        row.setHeightInPoints(30); // 设置行的高度

        HSSFRow row1 = sheet.createRow(1);
        row1.setHeightInPoints(30);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("NO00001");

        // 日期格式化
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        HSSFCreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        sheet.setColumnWidth(2, 20 * 256); // 设置列的宽度

        HSSFCell cell2 = row1.createCell(2);
        cell2.setCellStyle(cellStyle2);
        cell2.setCellValue(new Date());

        row1.createCell(3).setCellValue(3);

        // 保留两位小数
        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        HSSFCell cell4 = row1.createCell(4);
        cell4.setCellStyle(cellStyle3);
        cell4.setCellValue(29.5);


        // 货币格式,设置样式格式
        HSSFCellStyle cellStyle4 = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("华文行楷");
        font.setFontHeightInPoints((short)20);
        font.setColor(Font.COLOR_RED);
        cellStyle4.setFont(font);


        HSSFCell cell5 = row1.createCell(5);
        //添加样式
        cell5.setCellStyle(cellStyle4);
        cell5.setCellFormula("D2*E2");  // 设置计算公式

        // 获取计算公式的值
        HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(workbook);
        cell5 = e.evaluateInCell(cell5);
        System.out.println(cell5.getNumericCellValue());


        workbook.setActiveSheet(0);
        //最后将封装好的excel写入输出流
        workbook.write(outputStream);
        outputStream.close();
    }

    @SneakyThrows
    public static void readExcel(){
        //获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/template.xls";
        //获取文件输入流
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
        //根据输入流解析获取excel文件数据
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(bufferedInputStream);
        //获取excel
        HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
        //获取指定的表单
        HSSFSheet sheet1 = workbook.getSheet("Sheet1");

        int lastRowNum = sheet1.getLastRowNum();

        for (Row cells : sheet1) {
            for (Cell cell : cells) {
                System.out.println(cell.getCellType());
                switch (cell.getCellType()){
                    case _NONE:
                        System.out.println("没有值");
                        break;
                    case BLANK:
                        System.out.println("空值");
                        break;
                    case ERROR:
                        System.out.println(cell.getErrorCellValue());
                        break;
                    case FORMULA:
                        System.out.println("公式");
                        break;
                    case BOOLEAN:
                        System.out.println(cell.getBooleanCellValue());
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue());
                }


            }
        }


    }



}
