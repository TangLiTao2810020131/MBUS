package com.ets.utils;

import com.ets.common.ExcelHeader;
import com.ets.common.ExcelResources;
import com.ets.common.ExcelStyleFormat;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.xslf.usermodel.SlideLayout.BLANK;

/**
 * @author xuqiang
 * @date 2019/7/26 8:42
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static ExcelUtils eu = new ExcelUtils();

    private ExcelUtils() {
    }

    public static ExcelUtils getInstance() {
        return eu;
    }


    /**
     *
     * 通过输入流创建excel并在指定的sheet页添加列<br>
     * <p>Title:addColumn<br>
     *
     * @param stream 文件输入流
     * @param list 数据实体类
     * @param sheetName sheet页名称
     * @param clz 类类型
     * @param toAddColumnName 添加的列名
     * @param outputPath 输出路径
     * @since JDK 1.8
     */
    public void addColumn(InputStream stream, List<Object> list, String sheetName, Class clz, String toAddColumnName,
                          String outputPath) {
        try {
            Sheet sheet = null;
            Workbook wb = new XSSFWorkbook(stream);
            for(int i=0;i<wb.getNumberOfSheets();i++) {
                if(!StringUtils.equals(wb.getSheetName(i), sheetName)) {
                    continue;
                }
                sheet = wb.getSheetAt(i);
            }

            if(null == sheet) {
                output(outputPath, wb);
                return;
            }

            Row r = sheet.getRow(0);
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            CellStyle defaultTitleCellStyle = r.getCell(0).getCellStyle();
            // 添加标题
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = r.createCell(i);
                cell.setCellStyle(defaultTitleCellStyle);
                cell.setCellValue(headers.get(i).getTitle());
            }
            Object obj = null;
            CellStyle numberStyle = wb.createCellStyle();
            DataFormat numberformat = wb.createDataFormat();
            CellStyle numberStyleSplit = wb.createCellStyle();
            DataFormat numberformatSplit = wb.createDataFormat();
            XSSFCellStyle commonCs = (XSSFCellStyle) wb.createCellStyle();
            for (int i = 0; i < list.size(); i++) {
                r = sheet.getRow(i + 1);
                obj = list.get(i);
                for (int j = 0; j < headers.size(); j++) {
                    if (StringUtils.equals(headers.get(j).getTitle(), toAddColumnName)) {
                        numberStyle.setAlignment(HorizontalAlignment.LEFT);
                        numberStyleSplit.setAlignment(HorizontalAlignment.RIGHT);
                        Cell temp = r.createCell(j);
                        ExcelStyleFormat styleFormat = new ExcelStyleFormat(numberStyle, numberformat, numberStyleSplit, numberformatSplit);
                        Object cellValue = PropertyUtils.getProperty(obj, getMethodName(headers.get(j)));
                        setCellValue(temp, cellValue,styleFormat,commonCs);
                    }
                }
            }
            output(outputPath, wb);
        } catch (Exception e) {
            logger.error("添加列" + toAddColumnName + "失败", e);
        }

    }

    /**
     *
     * 根据标题获取相应的方法名称<br>
     * <p>Title:getMethodName<br>
     *
     * @param eh 表头
     * @return String    返回类型
     * @since JDK 1.8
     */
    private String getMethodName(ExcelHeader eh) {
        String mn = eh.getMethodName().substring(3);
        mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
        return mn;
    }


    /**
     *
     * 导出多sheet页<br>
     * <p>Title:handleObj2ExcelMulti<br>
     *
     * @param objs 数据实体
     * @param clz 类类型
     * @param wb workbook
     * @param sheetName  sheet页名
     * @since JDK 1.8
     */
    public void handleObj2ExcelMulti(List objs, Class clz, Workbook wb, String sheetName) {
        try {
            Sheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(18);
            if (StringUtils.isNotBlank(sheetName)) {
                wb.setSheetName(1, sheetName);
            }
            Row r = sheet.createRow(0);
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            XSSFCellStyle cs = (XSSFCellStyle) wb.createCellStyle();
            XSSFFont subLabelFont = (XSSFFont)wb.createFont();
            XSSFRichTextString sb = new XSSFRichTextString("");
            // 写标题
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = r.createCell(i);
                sb.append(headers.get(i).getTitle());
                cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cs.setAlignment(HorizontalAlignment.CENTER);
                cs.setBorderTop(BorderStyle.THIN);
                cs.setBorderBottom(BorderStyle.THIN);
                cs.setBorderLeft(BorderStyle.THIN);
                cs.setBorderRight(BorderStyle.THIN);
                subLabelFont.setFontName("Microsoft YaHei");
                // 设置字体大小
                subLabelFont.setFontHeightInPoints((short) 10);
                subLabelFont.setBold(true);
                sb.applyFont(0, sb.length(), subLabelFont);
                cell.setCellValue(sb);
                cell.setCellStyle(cs);
                sb.setString("");
            }
            // 写数据
            Object obj = null;
            CellStyle numberStyle = wb.createCellStyle();
            DataFormat numberformat = wb.createDataFormat();
            CellStyle numberStyleSplit = wb.createCellStyle();
            DataFormat numberformatSplit = wb.createDataFormat();
            XSSFFont contentFont = (XSSFFont)wb.createFont();
            XSSFCellStyle commonCs = (XSSFCellStyle) wb.createCellStyle();
            commonCs.setBorderTop(BorderStyle.THIN);
            commonCs.setBorderBottom(BorderStyle.THIN);
            commonCs.setBorderLeft(BorderStyle.THIN);
            commonCs.setBorderRight(BorderStyle.THIN);
            numberStyle.setBorderTop(BorderStyle.THIN);
            numberStyle.setBorderBottom(BorderStyle.THIN);
            numberStyle.setBorderLeft(BorderStyle.THIN);
            numberStyle.setBorderRight(BorderStyle.THIN);
            numberStyleSplit.setBorderTop(BorderStyle.THIN);
            numberStyleSplit.setBorderBottom(BorderStyle.THIN);
            numberStyleSplit.setBorderLeft(BorderStyle.THIN);
            numberStyleSplit.setBorderRight(BorderStyle.THIN);
            for (int i = 0; i < objs.size(); i++) {
                r = sheet.createRow(i + 1);
                obj = objs.get(i);
                for (int j = 0; j < headers.size(); j++) {
                    Cell temp = r.createCell(j);
                    numberStyle.setAlignment(HorizontalAlignment.LEFT);
                    numberStyleSplit.setAlignment(HorizontalAlignment.RIGHT);
                    commonCs.setAlignment(HorizontalAlignment.LEFT);
                    // 设置字体大小
                    contentFont.setFontHeightInPoints((short) 10);
                    contentFont.setBold(true);
                    ExcelStyleFormat styleFormat = new ExcelStyleFormat(numberStyle, numberformat, numberStyleSplit, numberformatSplit);
                    Object cellValue = PropertyUtils.getProperty(obj, getMethodName(headers.get(j)));
                    setCellValue(temp, cellValue,styleFormat,commonCs);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     * 处理对象转excel<br>
     * <p>Title:handleObj2Excel<br>
     *
     * @param objs 数据实体
     * @param clz 类类型
     * @param suffix 文件后缀
     * @param sheetName sheet页名
     * @return Workbook    返回类型
     * @since JDK 1.8
     */
    public Workbook handleObj2Excel(List objs, Class clz, String suffix, String sheetName) {
        Workbook wb = null;
        if (StringUtils.equalsIgnoreCase(suffix, "xls") || StringUtils.isEmpty(suffix)) {
            wb = new HSSFWorkbook();
        } else if (StringUtils.equalsIgnoreCase(suffix, "xlsx")) {
            wb = new XSSFWorkbook();
        }
        try {
            Sheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(18);
            if (StringUtils.isNotBlank(sheetName)) {
                wb.setSheetName(0, sheetName);
            }
            Row r = sheet.createRow(0);
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            XSSFCellStyle cs = (XSSFCellStyle) wb.createCellStyle();
            XSSFFont subLabelFont = (XSSFFont)wb.createFont();
            XSSFRichTextString sb = new XSSFRichTextString("");
            // 写标题
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = r.createCell(i);
                sb.append(headers.get(i).getTitle());
                cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cs.setAlignment(HorizontalAlignment.CENTER);
                cs.setBorderTop(BorderStyle.THIN);
                cs.setBorderBottom(BorderStyle.THIN);
                cs.setBorderLeft(BorderStyle.THIN);
                cs.setBorderRight(BorderStyle.THIN);
                subLabelFont.setFontName("Microsoft YaHei");
                // 设置字体大小
                subLabelFont.setFontHeightInPoints((short) 10);
                subLabelFont.setBold(true);
                sb.applyFont(0, sb.length(), subLabelFont);
                cell.setCellValue(sb);
                cell.setCellStyle(cs);
                sb.setString("");
            }
            // 写数据
            Object obj = null;
            CellStyle numberStyle = wb.createCellStyle();
            DataFormat numberformat = wb.createDataFormat();
            CellStyle numberStyleSplit = wb.createCellStyle();
            DataFormat numberformatSplit = wb.createDataFormat();
            XSSFCellStyle commonCs = (XSSFCellStyle) wb.createCellStyle();
            commonCs.setBorderTop(BorderStyle.THIN);
            commonCs.setBorderBottom(BorderStyle.THIN);
            commonCs.setBorderLeft(BorderStyle.THIN);
            commonCs.setBorderRight(BorderStyle.THIN);
            numberStyle.setBorderTop(BorderStyle.THIN);
            numberStyle.setBorderBottom(BorderStyle.THIN);
            numberStyle.setBorderLeft(BorderStyle.THIN);
            numberStyle.setBorderRight(BorderStyle.THIN);
            numberStyleSplit.setBorderTop(BorderStyle.THIN);
            numberStyleSplit.setBorderBottom(BorderStyle.THIN);
            numberStyleSplit.setBorderLeft(BorderStyle.THIN);
            numberStyleSplit.setBorderRight(BorderStyle.THIN);
            for (int i = 0; i < objs.size(); i++) {
                r = sheet.createRow(i + 1);
                obj = objs.get(i);
                for (int j = 0; j < headers.size(); j++) {
                    Cell temp = r.createCell(j);
                    numberStyle.setAlignment(HorizontalAlignment.LEFT);
                    numberStyleSplit.setAlignment(HorizontalAlignment.RIGHT);
                    commonCs.setAlignment(HorizontalAlignment.LEFT);
                    ExcelStyleFormat styleFormat = new ExcelStyleFormat(numberStyle, numberformat, numberStyleSplit, numberformatSplit);
                    Object cellValue = PropertyUtils.getProperty(obj, getMethodName(headers.get(j)));
                    setCellValue(temp, cellValue,styleFormat,commonCs);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return wb;
    }

    /**
     *
     * 设置cell数据类型<br>
     * <p>Title:setCellValue<br>
     *
     * @param temp cell
     * @param cellValue  cell值
     * @since JDK 1.8
     */
    private void setCellValue(Cell temp, Object cellValue,ExcelStyleFormat styleFormat, XSSFCellStyle commonCs) {
        if (cellValue instanceof String) {
            temp.setCellStyle(commonCs);
            temp.setCellValue((String.valueOf(cellValue)));
        } else if(cellValue instanceof Integer || cellValue instanceof BigInteger) {
            temp.setCellStyle(commonCs);
            if(StringUtils.equals(cellValue.toString(), "0")) {
                temp.setCellValue("-");
            } else {
                temp.setCellValue(Double.parseDouble(cellValue.toString()));
            }
        } else if (cellValue instanceof Double || cellValue instanceof BigDecimal) {
            temp.setCellValue(Double.parseDouble(cellValue.toString()));
            setDateFormat(temp,"#,##0.00",styleFormat.getNumberStyleSplit(),styleFormat.getNumberformatSplit());
        } else if (cellValue instanceof Date) {
            temp.setCellStyle(commonCs);
            temp.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cellValue));

        }
    }

    /**
     *
     * 设置单元格格式<br>
     * <p>Title:setDateFormat<br>
     * @param cell cell
     * @param formatStr 格式化
     * @return void    返回类型
     * @since JDK 1.8
     */
    private void setDateFormat(Cell cell, String formatStr,CellStyle style,DataFormat format) {
        cell.setCellStyle(style);
        style.setDataFormat(format.getFormat(formatStr));
    }

    /**
     *
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出<br>
     * <p>Title:exportObj2Excel<br>
     *
     * @param outPath 输出路径
     * @param objs 实体类
     * @param clz 类类型
     * @param sheetName sheet页名
     * @since JDK 1.8
     */
    public void exportObj2Excel(String outPath, List objs, Class clz, String sheetName) {
        String suffix = outPath.substring(outPath.lastIndexOf("."));
        Workbook wb = handleObj2Excel(objs, clz, suffix, sheetName);
        output(outPath, wb);
    }

    /**
     *
     * 导出到excel多个sheet<br>
     * <p>Title:exportObj2ExcelMultiSheet<br>
     *
     * @param outPath 输出路径
     * @param objs 数据实体
     * @param clz 类类型
     * @param wb workbook
     * @param sheetName  sheet页名
     * @since JDK 1.8
     */
    public void exportObj2ExcelMultiSheet(String outPath, List objs, Class clz, Workbook wb, String sheetName) {
        handleObj2ExcelMulti(objs, clz, wb, sheetName);
        output(outPath, wb);
    }

    /**
     *
     * 输出<br>
     * <p>Title:output<br>
     *
     * @param outPath 输出路径
     * @param wb  workbook
     * @since JDK 1.8
     */
    public void output(String outPath, Workbook wb) {
        try(FileOutputStream fos = new FileOutputStream(outPath);) {
            wb.write(fos);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(null != wb) {
                    wb.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     *
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于流<br>
     * <p>Title:exportObj2Excel<br>
     *
     * @param os 输出流
     * @param objs 数据实体
     * @param clz 类类型
     * @param sheetName sheet页名
     * @since JDK 1.8
     */
    public void exportObj2Excel(OutputStream os, List objs, Class clz, String sheetName) {
        try {
            Workbook wb = handleObj2Excel(objs, clz, "", sheetName);
            wb.write(os);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     * 从流中读取相应的Excel文件到对象列表<br>
     * <p>Title:readExcel2ObjsByStream<br>
     *
     * @param stream 文件输入流
     * @param fileName 文件名
     * @param sheetName sheet页名
     * @param clz 类类型
     * @param readLine 当前行
     * @param tailLine 最后行
     * @return List<Object>    返回类型
     * @since JDK 1.8
     */
    public List<Object> readExcel2ObjsByStream(InputStream stream, String fileName, String sheetName, Class clz, int readLine,
                                               int tailLine) {
        Workbook wb = null;
        try {
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            if (StringUtils.equalsIgnoreCase(suffix, ".xls")) {
                wb = new HSSFWorkbook(stream);
            } else if (StringUtils.equalsIgnoreCase(suffix, ".xlsx")) {
                wb = new XSSFWorkbook(stream);
            }
            return handlerExcel2Objs(wb, clz, readLine, tailLine,sheetName);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     *
     * 从文件流中读取相应的Excel文件到对象列表，标题行为0，没有尾行<br>
     * <p>Title:readExcel2ObjsByStream<br>
     *
     * @param stream 文件输入流
     * @param fileName 文件名
     * @param sheetName sheet页名称
     * @param clz 类类型
     * @return List<Object>    返回类型
     * @since JDK 1.8
     */
    public List<Object> readExcel2ObjsByStream(InputStream stream, String fileName, String sheetName, Class clz) {
        return this.readExcel2ObjsByStream(stream, fileName, sheetName, clz, 0, 0);
    }

    /**
     *
     * 获取单元格的值<br>
     * <p>Title:getCellValue<br>
     *
     * @param c 单元格
     * @return String    返回类型
     * @since JDK 1.8
     */
    private String getCellValue(Cell c) {
        String o = null;
        switch (c.getCellTypeEnum()) {
            case BLANK:
                o = "";
                break;
            case BOOLEAN:
                o = String.valueOf(c.getBooleanCellValue());
                break;
            case FORMULA:
                o = String.valueOf(c.getCellFormula());
                break;
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("#.00");
                o = df.format(c.getNumericCellValue());
                break;
            case STRING:
                o = c.getStringCellValue();
                break;
            default:
                o = "-";
                break;
        }
        return o;
    }

    /**
     *
     * excel文件转object<br>
     * <p>Title:handlerExcel2Objs<br>
     *
     * @param wb workbook
     * @param clz 类类型
     * @param readLine 当前行
     * @param tailLine 最后行
     * @param sheetName sheet页名
     * @return List<Object>    返回类型
     * @since JDK 1.8
     */
    private List<Object> handlerExcel2Objs(Workbook wb, Class clz, int readLine, int tailLine,String sheetName) {
        List<Object> objs = null;
        Sheet sheet = null;
        for(int i=0;i<wb.getNumberOfSheets();i++) {
            if(!StringUtils.equals(wb.getSheetName(i), sheetName)) {
                continue;
            }
            sheet = wb.getSheetAt(i);
        }

        if(null == sheet) {
            return objs;
        }

        try {
            Row row = sheet.getRow(readLine);
            objs = new ArrayList<>();
            Map<Integer, String> maps = getHeaderMap(row, clz);
            if (MapUtils.isEmpty(maps)) {
                throw new Exception("要读取的Excel的格式不正确，检查是否设定了合适的行");
            }
            for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
                row = sheet.getRow(i);
                Object obj = clz.newInstance();
                if(isEmptyRow(row) || row.getPhysicalNumberOfCells() == 0) {
                    continue;
                }
                for (Cell c : row) {
                    int ci = c.getColumnIndex();
                    String mn = maps.get(ci).substring(3);
                    mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
                    copyValue(obj, c, mn);
                }
                objs.add(obj);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
        return objs;
    }

    private void copyValue(Object obj, Cell c, String mn) {
        try {
            if(obj.getClass().getDeclaredField(mn).getType() == Integer.class) {
                BeanUtils.copyProperty(obj, mn, new BigDecimal(this.getCellValue(c)).intValue());
            } else {
                BeanUtils.copyProperty(obj, mn, this.getCellValue(c));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     * 判断是否空行<br>
     * <p>Title:isEmptyRow<br>
     *
     * @param row excel行
     * @return boolean    返回类型
     * @since JDK 1.8
     */
    private boolean isEmptyRow(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (null != cell && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * excel文件转object<br>
     * <p>Title:getHeaderList<br>
     *
     * @param clz 类类型
     * @return List<ExcelHeader>    返回类型
     * @since JDK 1.8
     */
    private List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<>();
        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            String mn = m.getName();
            if (mn.startsWith("get") && m.isAnnotationPresent(ExcelResources.class)) {
                ExcelResources er = m.getAnnotation(ExcelResources.class);
                headers.add(new ExcelHeader(er.title(), er.order(), mn));
            }
        }
        return headers;
    }

    /**
     *
     * 获取列和标头的映射关系<br>
     * <p>Title:getHeaderMap<br>
     *
     * @param titleRow 表头行
     * @param clz 类类型
     * @return Map<Integer,String>    返回类型
     * @since JDK 1.8
     */
    private Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, String> maps = new HashMap<>(titleRow.getLastCellNum());
        for (Cell c : titleRow) {
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headers) {
                if (eh.getTitle().equals(title.trim())) {
                    maps.put(c.getColumnIndex(), eh.getMethodName().replace("get", "set"));
                    break;
                }
            }
        }
        return maps;
    }
}
