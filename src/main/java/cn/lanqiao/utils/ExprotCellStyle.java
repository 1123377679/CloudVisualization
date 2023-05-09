package cn.lanqiao.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * Excel文件操作的工具类
 */
public class ExprotCellStyle {

    /**
     * 得到表头样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createTableTitleStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = setRowCellCenter(workbook);//水平居中
        //设置字体
        HSSFFont font = setFontCellStyle(workbook, (short)15, HSSFColor.HSSFColorPredefined.BLUE.getIndex(), true,false, HSSFFont.U_NONE);
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建小标题的样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createSecondTitleStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = setRowCellCenter(workbook);//水平居中
        //设置字体
        HSSFFont font = setFontCellStyle(workbook, (short)18, HSSFColor.HSSFColorPredefined.GOLD.getIndex(), true,false, HSSFFont.U_NONE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建表头的样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createTitleCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = setRowCellCenter(workbook);
        //设置字体
        HSSFFont font = setFontCellStyle(workbook, (short)30, HSSFColor.HSSFColorPredefined.RED.getIndex(), true,false, HSSFFont.U_DOUBLE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     *
     * @param workbook  工作簿
     * @param fontSize  字体大小
     * @param colorIndex 字体颜色  @see HSSFColorPredefined
     * @param bold  是否加粗
     * @param italic  是否斜体
     * @param undderLine   下划线风格  @see HSSFFont.U_DOUBLE
     * @return
     */
    public static HSSFFont setFontCellStyle(HSSFWorkbook workbook,
                                            short fontSize, short colorIndex, boolean bold, boolean italic,
                                            byte undderLine) {
        HSSFFont font=workbook.createFont();
        font.setFontHeightInPoints(fontSize);//字体大小
        font.setColor(colorIndex);//设置字体颜色
        font.setBold(bold);//加粗
        font.setItalic(italic);//设置非斜体
        font.setUnderline(undderLine);//设置下划线
        return font;
    }

    /**
     * 创建水平和垂直居 的方法
     * @param workbook
     * @return
     */
    public static HSSFCellStyle setRowCellCenter(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        return cellStyle;
    }


}
