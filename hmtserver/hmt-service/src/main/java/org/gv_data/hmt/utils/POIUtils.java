package org.gv_data.hmt.utils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gv_data.hmt.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoliang
 * @desc 健康监控系统
 * @时间 2019-11-11 23:25
 */
public class POIUtils {

    public static boolean  subMessageExcel(List<SubMessage> list,String filepath) {
        boolean success = false;
        //1. 创建一个 Excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 创建文档摘要
        workbook.createInformationProperties();
        //3. 获取并配置文档信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("子系统状态信息");
        //文档管理员
        docInfo.setManager("CodeLamp");
        //设置公司信息
        docInfo.setCompany("www.zhaoi1ang.club");
        //4. 获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle("子系统状态信息");
        //文档作者
        summInfo.setAuthor("CodeLamp");
        // 文档备注
        summInfo.setComments("本文档由 CodeLamp 提供");
        //5. 创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("子系统状态信息");
        //设置列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 5 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        //6. 创建标题行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("队列头");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("UUID");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("系统编码");
        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("状态编码");
        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("日期");
        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("类型");
        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("标签");
        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("内容");

        for (int i = 0; i < list.size(); i++) {
            SubMessage subMessage = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(subMessage.getHeader());
            row.createCell(1).setCellValue(subMessage.getUuid());
            row.createCell(2).setCellValue(subMessage.getSystem_code());
            row.createCell(3).setCellValue(subMessage.getStatus_code());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellStyle(dateCellStyle);
            cell4.setCellValue(subMessage.getDate());
            row.createCell(5).setCellValue(subMessage.getType());
            row.createCell(6).setCellValue(subMessage.getTag());
            row.createCell(7).setCellValue(subMessage.getContent());

        }

        try {
            isChartPathExist(filepath);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            FileOutputStream output=new FileOutputStream(filepath+formatter.format(date)+"sm.xls");
            workbook.write(output);
            success = true;
        } catch (IOException e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Excel 解析成 子系统信息集合
     *
     * @param filePath
     * @return
     */
    public static List<SubMessage> excel2SubMessage(File file) throws Exception {
        List<SubMessage> list = new ArrayList<>();
        SubMessage subMessage = null;
        //用字节流的方式先读取到你想要的excel的文件
        FileInputStream fis=new FileInputStream(file);
        //解析excel
        POIFSFileSystem pSystem=new POIFSFileSystem(fis);
        //获取整个excel
        HSSFWorkbook hb=new HSSFWorkbook(pSystem);
        //获取第一个表单sheet
        HSSFSheet sheet=hb.getSheetAt(0);
        //获取第一行  从1开始
        int firstrow=sheet.getFirstRowNum()+1;
        //获取最后一行
        int lastrow=sheet.getLastRowNum();
        for (int i = firstrow; i < lastrow+1; i++) {//循环行数
            //获取哪一行i
            Row row=sheet.getRow(i);
            if (row!=null) {
                //获取这一行的第一列 从0开始
                int firstcell=   row.getFirstCellNum();
                //获取这一行的最后一列
                int lastcell=    row.getLastCellNum();
                //创建一个集合，用处将每一行的每一列数据都存入集合中
                subMessage = new SubMessage();
                for (int j = firstcell; j <lastcell; j++) {//循环列数
                    //获取第j列
                    Cell cell=row.getCell(j);
                    switch (cell.getCellType()){
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            switch (j){
                                case 0:
                                    subMessage.setHeader(cellValue);
                                    break;
                                case 1:
                                    subMessage.setUuid(cellValue);
                                    break;
                                case 2:
                                    subMessage.setSystem_code(cellValue);
                                    break;
                                case 3:
                                    subMessage.setStatus_code(cellValue);
                                    break;
                                case 4:
                                    subMessage.setDate(cellValue);
                                    break;
                                case 5:
                                    subMessage.setType(cellValue);
                                    break;
                                case 6:
                                    subMessage.setTag(cellValue);
                                    break;
                                case 7:
                                    subMessage.setContent(cellValue);
                                    break;
                            }
                            break;
                    }
                    list.add(subMessage);
                }
            }
        }
        //循环行数依次获取列数
        fis.close();
        return list;
    }
    /**
     * 判断文件夹是否存在，如果不存在则新建
     *
     * @param dirPath 文件夹路径
     */
    private static void isChartPathExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
