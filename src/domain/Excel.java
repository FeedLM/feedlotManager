/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static domain.Animal.Animal;
import static domain.CorralAnimal.cargarAnimalesCorral;
import static domain.CorralAnimal.cargarAnimalesCorral_;
import static domain.Movimiento.cargarMovimientosEntrada;
import static domain.Movimiento.cargarMovimientosSalida;
import static domain.Movimiento.cargarMovimientosSalida;
import static domain.Movimiento.cargarMovimientosSalida;
import domain.FormatoNumero;
//import static gui.Splash.formatoDate;
//import static gui.Splash.formatoDateTime;
//import static gui.Login.gs_mensaje;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.record.CFRuleRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.ScatterChartData;
import org.apache.poi.ss.usermodel.charts.ScatterChartSerie;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ExtensionFileFilter;
import com.csvreader.CsvReader;
import static gui.Login.formatoDate;
import static gui.Login.formatoDateTime;
import static gui.Login.gs_mensaje;
import static gui.Splash.formatoDateTime_2;
//import static gui.Splash.formatoDateTime_2;

/**
 *
 * @author Developer GAGS
 */
public class Excel {

    private Animal animal;
    private Integer tipo;
    Integer Unidad = 275;
    // Integer id_corral;
    private JFrame parent;
    private String XLSFile;
    private Date fecha_ini;
    private Date fecha_fin;
    private Corral corral;
    private HSSFWorkbook wb;
    //private XSSFWorkbook wb;
    private JFreeChart grafica;
    private Sheet sheet;
    private Table t_tabla;
    private Table t_tabla2;

    private HSSFCellStyle styleCenter;
    private HSSFCellStyle styleRight;
    private HSSFCellStyle styleleft;

    public static List<RegistroSesion> leerArchivoSesionCSV(String archivoSesion) {

        List<RegistroSesion> regSesion = new ArrayList<RegistroSesion>();
        try {

            CsvReader reg_Sesion_import = new CsvReader(archivoSesion);
            reg_Sesion_import.readHeaders();

            while (reg_Sesion_import.readRecord()) {

                String arete_visual = reg_Sesion_import.get("IDV");
                Double peso;

                try {

                    peso = Double.parseDouble(reg_Sesion_import.get("Peso"));

                } catch (NumberFormatException ex) {

                    peso = Double.parseDouble(reg_Sesion_import.get("Peso") + ".00");
                }

                Date fecha = null;

                try {

                    fecha = formatoDateTime_2.parse(reg_Sesion_import.get(2) + " " + reg_Sesion_import.get(3));

                } catch (ParseException ex) {

                    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
                }

                String arete_electronico = reg_Sesion_import.get(4);

                regSesion.add(
                        new RegistroSesion(arete_visual, peso, fecha, arete_electronico));
            }

            reg_Sesion_import.close();
            /*
             for (RegistroSesion us : regSesion) {

             System.out.println(us.toString());
             } 
            */
             
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return regSesion;
    }

    public static void leerArchivoSesion(String archivoSesion) {

        List sheetData = new ArrayList();

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(archivoSesion);

            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()) {

                HSSFRow row = (HSSFRow) rows.next();

                Iterator cells = row.cellIterator();

                List data = new ArrayList();

                while (cells.hasNext()) {

                    HSSFCell cell = (HSSFCell) cells.next();
                    data.add(cell);
                }

                sheetData.add(data);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();

                } catch (IOException ex) {
                    Logger.getLogger(Excel.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        showExelData(sheetData);
    }

    private static void showExelData(List sheetData) {

        //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {

            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {

                Cell cell = (Cell) list.get(j);

                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

                    System.out.print(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

                    System.out.print(cell.getRichStringCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

                    System.out.print(cell.getBooleanCellValue());
                }
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }

    public void styles() {

        styleCenter = wb.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        styleRight = wb.createCellStyle();
        styleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        styleRight.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        styleleft = wb.createCellStyle();
        styleleft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleleft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    }

    public void reporteSesiones(Table TTabla1, Table TTabla2, Integer ITipo, Date DFechaIni, Date DFechaFin, Animal AAnimal) {

        t_tabla = TTabla1;
        t_tabla2 = TTabla2;

        animal = AAnimal;
        tipo = ITipo;
        this.fecha_ini = DFechaIni;
        this.fecha_fin = DFechaFin;

        if (t_tabla.getRowCount() <= 0 && t_tabla2.getRowCount() <= 0) {

            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }

        wb = new HSSFWorkbook();
        sheet = wb.createSheet("REPORTE DE SESIONES");

        styles();

        reporteSesiones();

        crearExcel();
    }

    private void crearExcel() {

        FileOutputStream out;

        try {

            out = new FileOutputStream(XLSFile);
            wb.write(out);
            out.close();
            Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + XLSFile);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reporteSesiones() {

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:N1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:N2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:N4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE SESIONES");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 9);
        FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        String etiqueta_parametro = "";

        switch (tipo) {
            case 1:
                etiqueta_parametro = "Sesion de dia " + formatoDate.format(fecha_ini);
                break;
            case 2:
                etiqueta_parametro = "Sesiones del " + formatoDate.format(fecha_ini) + " al " + formatoDate.format(fecha_fin);
                break;
            case 3:
                etiqueta_parametro = "Sesiones del animal " + animal.arete_visual;
                break;
        }

        cell.setCellValue(etiqueta_parametro);
        cell.setCellStyle(styleParamReport);

        /**/
        sheet.createRow(5).createCell(0).setCellValue("Id Animal");
        sheet.getRow(5).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(5).createCell(2).setCellValue("Fecha");
        sheet.getRow(5).createCell(3).setCellValue("Peso");
        sheet.getRow(5).createCell(4).setCellValue("Corral");
        sheet.getRow(5).createCell(5).setCellValue("");
        sheet.getRow(5).createCell(6).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(7).setCellValue("Codigo");
        sheet.getRow(5).createCell(8).setCellValue("Medicina");
        sheet.getRow(5).createCell(9).setCellValue("Fecha");
        sheet.getRow(5).createCell(10).setCellValue("Corral");
        sheet.getRow(5).createCell(11).setCellValue("Dosis");
        sheet.getRow(5).createCell(12).setCellValue("Costo");
        sheet.getRow(5).createCell(13).setCellValue("Importe");

        sheet.setColumnWidth(0, 13 * Unidad);
        sheet.setColumnWidth(1, 14 * Unidad);
        sheet.setColumnWidth(2, 12 * Unidad);
        sheet.setColumnWidth(3, 10 * Unidad);
        sheet.setColumnWidth(4, 14 * Unidad);

        sheet.setColumnWidth(5, 2 * Unidad);

        sheet.setColumnWidth(6, 14 * Unidad);
        sheet.setColumnWidth(7, 11 * Unidad);
        sheet.setColumnWidth(8, 14 * Unidad);
        sheet.setColumnWidth(9, 12 * Unidad);
        sheet.setColumnWidth(10, 14 * Unidad);
        sheet.setColumnWidth(11, 10 * Unidad);
        sheet.setColumnWidth(12, 10 * Unidad);
        sheet.setColumnWidth(13, 10 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:N6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 5; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(3).setCellValue(Double.parseDouble(t_tabla.getValueAt(i, 4).toString()));
            sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleRight);

        }

        Integer columna = 6;

        for (int i = 0; i < this.t_tabla2.getRowCount(); i++) {

            if (i >= t_tabla.getRowCount()) {

                sheet.createRow(fila_inicial + i);//.createCell(columna).setCellValue(t_tabla.getValueAt(i, 1).toString());
            }

            //sheet.getRow(fila_inicial + i).getCell(columna).setCellStyle(styleCenter);
            for (int j = 0; j < 8; j++) {

                System.out.println(i + ", " + j + " --- " + t_tabla2.getRowCount());
                sheet.getRow(fila_inicial + i).createCell(columna + j).setCellValue(t_tabla2.getValueAt(i, j + 1).toString());
                sheet.getRow(fila_inicial + i).getCell(columna + j).setCellStyle(styleCenter);
            }
        }

    }

    public void reporteMuertes(Table TTabla, Integer ITipo, Date DFechaIni, Date DFechaFin, Animal AAnimal) {

        t_tabla = TTabla;
        animal = AAnimal;
        tipo = ITipo;
        this.fecha_ini = DFechaIni;
        this.fecha_fin = DFechaFin;

        if (t_tabla.getRowCount() <= 0) {

            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }

        wb = new HSSFWorkbook();
        sheet = wb.createSheet("REPORTE DE MUERTES");

        styles();

        reporteMuertes();

        crearExcel();

    }

    private void reporteMuertes() {

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:H2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:H4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE MUERTES");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 9);
        FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        String etiqueta_parametro = "";

        switch (tipo) {
            case 1:
                etiqueta_parametro = "Muertes de dia " + formatoDate.format(fecha_ini);
                break;
            case 2:
                etiqueta_parametro = "Muertes del " + formatoDate.format(fecha_ini) + " al " + formatoDate.format(fecha_fin);
                break;
            case 3:
                etiqueta_parametro = "Muertes de animal " + animal.arete_visual;
                break;
        }

        cell.setCellValue(etiqueta_parametro);
        cell.setCellStyle(styleParamReport);
        /**/
        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Fecha de Muerte");
        sheet.getRow(5).createCell(2).setCellValue("Necropcia");
        sheet.getRow(5).createCell(3).setCellValue("Dx de Muerte");
        sheet.getRow(5).createCell(4).setCellValue("Dias Muerte");
        sheet.getRow(5).createCell(5).setCellValue("Etapa Reproductiva");
        sheet.getRow(5).createCell(6).setCellValue("");

        sheet.setColumnWidth(0, 13 * Unidad);
        sheet.setColumnWidth(1, 16 * Unidad);
        sheet.setColumnWidth(2, 20 * Unidad);
        //sheet.setColumnWidth(3, 15 * Unidad);
        sheet.setColumnWidth(3, 27 * Unidad);
        sheet.setColumnWidth(4, 14 * Unidad);
        sheet.setColumnWidth(5, 18 * Unidad);
        //  sheet.setColumnWidth(6, 18 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:H6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        /*
         HSSFCellStyle styleCenter = wb.createCellStyle();
         styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         //styleCenter.setFont(FontNameReport);
         HSSFCellStyle styleRight = wb.createCellStyle();
         styleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
         styleRight.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         //styleCenter.setFont(FontNameReport);
         HSSFCellStyle styleleft = wb.createCellStyle();
         styleleft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
         styleleft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         */
        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 6; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

          //  sheet.getRow(fila_inicial + i).getCell(2).setCellValue(Integer.parseInt(t_tabla.getValueAt(i, 3).toString()));
            //   sheet.getRow(fila_inicial + i).getCell(4).setCellValue(Double.parseDouble(t_tabla.getValueAt(i, 4).toString()));
            /*
             sheet.getRow(fila_inicial + i).createCell(2).setCellValue(t_tabla.getValueAt(i, 2).toString());
             sheet.getRow(fila_inicial + i).createCell(3).setCellValue(t_tabla.getValueAt(i, 3).toString());
             sheet.getRow(fila_inicial + i).createCell(4).setCellValue(t_tabla.getValueAt(i, 4).toString());
             sheet.getRow(fila_inicial + i).createCell(5).setCellValue(t_tabla.getValueAt(i, 5).toString());
             sheet.getRow(fila_inicial + i).createCell(6).setCellValue(t_tabla.getValueAt(i, 6).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             */
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(2).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleleft);
            //sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
            //sheet.getRow(fila_inicial + i).getCell(6).setCellStyle(styleRight);
            //sheet.getRow(fila_inicial + i).getCell(15).setCellStyle(styleRight);
        }
    }

    public void reporteTraspasos(Table aTabla, Integer tipo, Date fecha) {

        t_tabla = aTabla;

        if (t_tabla.getRowCount() <= 0) {

            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }

        wb = new HSSFWorkbook();
        sheet = wb.createSheet("REPORTE DE TRASPASOS");

        styles();

        reporteTraspasos(tipo, fecha);

        crearExcel();
    }

    private void reporteTraspasos(Integer tipo, Date fecha) {

        Integer Unidad = 275;
        //   SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
//        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:H2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:H4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE TRASPASOS");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 9);
        FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        String etiqueta_parametro = "";

        etiqueta_parametro = "Traspasos del dia " + formatoDate.format(fecha);
        if (tipo == 1) {

            etiqueta_parametro = "Todos los traspasos";
        }

        cell.setCellValue(etiqueta_parametro);
        cell.setCellStyle(styleParamReport);
        /**/

        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(5).createCell(2).setCellValue("Fecha de Movimiento");
        sheet.getRow(5).createCell(3).setCellValue("Grupo Origen");
        sheet.getRow(5).createCell(4).setCellValue("Grupo Destino");
        sheet.getRow(5).createCell(5).setCellValue("");
        sheet.getRow(5).createCell(6).setCellValue("");

        sheet.setColumnWidth(0, 13 * Unidad);
        sheet.setColumnWidth(1, 14 * Unidad);
        sheet.setColumnWidth(2, 20 * Unidad);
        //sheet.setColumnWidth(3, 15 * Unidad);
        sheet.setColumnWidth(3, 27 * Unidad);
        sheet.setColumnWidth(4, 14 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:H6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 5; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

          //  sheet.getRow(fila_inicial + i).getCell(2).setCellValue(Integer.parseInt(t_tabla.getValueAt(i, 3).toString()));
            //   sheet.getRow(fila_inicial + i).getCell(4).setCellValue(Double.parseDouble(t_tabla.getValueAt(i, 4).toString()));
            /*
             sheet.getRow(fila_inicial + i).createCell(2).setCellValue(t_tabla.getValueAt(i, 2).toString());
             sheet.getRow(fila_inicial + i).createCell(3).setCellValue(t_tabla.getValueAt(i, 3).toString());
             sheet.getRow(fila_inicial + i).createCell(4).setCellValue(t_tabla.getValueAt(i, 4).toString());
             sheet.getRow(fila_inicial + i).createCell(5).setCellValue(t_tabla.getValueAt(i, 5).toString());
             sheet.getRow(fila_inicial + i).createCell(6).setCellValue(t_tabla.getValueAt(i, 6).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             */
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(2).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleleft);
            sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
            //sheet.getRow(fila_inicial + i).getCell(6).setCellStyle(styleRight);
            //sheet.getRow(fila_inicial + i).getCell(15).setCellStyle(styleRight);
        }
    }

    public void reporteAnimalesHospital(Table aTabla) {

        t_tabla = aTabla;

        if (t_tabla.getRowCount() <= 0) {

            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }

        wb = new HSSFWorkbook();
        sheet = wb.createSheet("REPORTE DE ANIMALES EN HOSPITAL");

        styles();

        reporteAnimalesHospital();

        crearExcel();
    }

    private void reporteAnimalesHospital() {

        Integer Unidad = 275;
        // SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        //SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:H2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:H4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE ANIMALES EN HOSPITAL");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Fecha Entrada");
        //  sheet.getRow(5).createCell(2).setCellValue("Fecha de Salida");
        sheet.getRow(5).createCell(2).setCellValue("Dias en Hospital");
        sheet.getRow(5).createCell(3).setCellValue("Causa Entrada");
        sheet.getRow(5).createCell(4).setCellValue("Observaciones");
        sheet.getRow(5).createCell(5).setCellValue("");
        sheet.getRow(5).createCell(6).setCellValue("");

        sheet.setColumnWidth(0, 13 * Unidad);
        sheet.setColumnWidth(1, 14 * Unidad);
        sheet.setColumnWidth(2, 15 * Unidad);
        //sheet.setColumnWidth(3, 15 * Unidad);
        sheet.setColumnWidth(3, 27 * Unidad);
        sheet.setColumnWidth(4, 14 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:H6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 5; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(2).setCellValue(Integer.parseInt(t_tabla.getValueAt(i, 3).toString()));

         //   sheet.getRow(fila_inicial + i).getCell(4).setCellValue(Double.parseDouble(t_tabla.getValueAt(i, 4).toString()));
            /*
             sheet.getRow(fila_inicial + i).createCell(2).setCellValue(t_tabla.getValueAt(i, 2).toString());
             sheet.getRow(fila_inicial + i).createCell(3).setCellValue(t_tabla.getValueAt(i, 3).toString());
             sheet.getRow(fila_inicial + i).createCell(4).setCellValue(t_tabla.getValueAt(i, 4).toString());
             sheet.getRow(fila_inicial + i).createCell(5).setCellValue(t_tabla.getValueAt(i, 5).toString());
             sheet.getRow(fila_inicial + i).createCell(6).setCellValue(t_tabla.getValueAt(i, 6).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             */
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(2).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleleft);
            sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
            //sheet.getRow(fila_inicial + i).getCell(6).setCellStyle(styleRight);
            //sheet.getRow(fila_inicial + i).getCell(15).setCellStyle(styleRight);
        }
    }

    public void reporteHistoricoHospital(Table aTabla) {

        t_tabla = aTabla;

        if (t_tabla.getRowCount() <= 0) {

            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }

        wb = new HSSFWorkbook();
        sheet = wb.createSheet("REPORTE DE HISTORICO DE HOSPITAL");

        styles();

        Excel.this.reporteHistoricoHospital();

        crearExcel();
    }

    private void reporteHistoricoHospital() {

        Integer Unidad = 275;
        //  SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        // SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:H2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:H4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE HISTORICO DE HOSPITAL");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Fecha Entrada");
        sheet.getRow(5).createCell(2).setCellValue("Fecha de Salida");
        sheet.getRow(5).createCell(3).setCellValue("Dias en Hospital");
        sheet.getRow(5).createCell(4).setCellValue("Causa Entrada");
        sheet.getRow(5).createCell(5).setCellValue("Observaciones");
        sheet.getRow(5).createCell(6).setCellValue("");
        sheet.getRow(5).createCell(7).setCellValue("");
        sheet.getRow(5).createCell(8).setCellValue("");

        sheet.setColumnWidth(0, 13 * Unidad);
        sheet.setColumnWidth(1, 14 * Unidad);
        sheet.setColumnWidth(2, 15 * Unidad);
        sheet.setColumnWidth(3, 15 * Unidad);
        sheet.setColumnWidth(4, 27 * Unidad);
        sheet.setColumnWidth(5, 14 * Unidad);

        sheet.setColumnWidth(6, 13 * Unidad);
        sheet.setColumnWidth(7, 11 * Unidad);
        sheet.setColumnWidth(8, 11 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:H6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 6; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(3).setCellValue(Integer.parseInt(t_tabla.getValueAt(i, 4).toString()));

         //   sheet.getRow(fila_inicial + i).getCell(4).setCellValue(Double.parseDouble(t_tabla.getValueAt(i, 4).toString()));
            /*
             sheet.getRow(fila_inicial + i).createCell(2).setCellValue(t_tabla.getValueAt(i, 2).toString());
             sheet.getRow(fila_inicial + i).createCell(3).setCellValue(t_tabla.getValueAt(i, 3).toString());
             sheet.getRow(fila_inicial + i).createCell(4).setCellValue(t_tabla.getValueAt(i, 4).toString());
             sheet.getRow(fila_inicial + i).createCell(5).setCellValue(t_tabla.getValueAt(i, 5).toString());
             sheet.getRow(fila_inicial + i).createCell(6).setCellValue(t_tabla.getValueAt(i, 6).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             */
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(2).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
            sheet.getRow(fila_inicial + i).getCell(5).setCellStyle(styleleft);
            //sheet.getRow(fila_inicial + i).getCell(6).setCellStyle(styleRight);
            //sheet.getRow(fila_inicial + i).getCell(15).setCellStyle(styleRight);
        }
    }

    public void reporteMedicinasAnimal(Table aTabla, Animal Aanimal, Double costo) {

        t_tabla = aTabla;
        animal = Aanimal;
        if (t_tabla.getRowCount() <= 0) {

            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }

        wb = new HSSFWorkbook();
        sheet = wb.createSheet("REPORTE DE MEDICINAS");

        styles();

        reporteMedicinaAnimal(costo);

        crearExcel();
    }

    private void reporteMedicinaAnimal(Double costo) {

        Integer Unidad = 275;
        //    SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        //   SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:H2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:H4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE MEDICINA ANIMAL ID: " + animal.arete_visual);
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);
        /**/

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 9);
        FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.RED.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Costo Total en Medicinas: $ " + costo + " MXN");
        cell.setCellStyle(styleParamReport);
        /**/

        //  sheet.createRow(0).createCell(2).setCellValue("REPORTE DE SALIDA");
        //sheet.createRow(0).getCell(2).setCellStyle(estiloCelda);
        // sheet.getRow(0).getCell(2).setCellStyle(new CellStyle("CENTER"));
        sheet.createRow(5).createCell(0).setCellValue("Codigo");
        sheet.getRow(5).createCell(1).setCellValue("Medicina");
        sheet.getRow(5).createCell(2).setCellValue("Unidades");
        sheet.getRow(5).createCell(3).setCellValue("Fecha");
        sheet.getRow(5).createCell(4).setCellValue("Dosis");
        sheet.getRow(5).createCell(5).setCellValue("Costo");
        sheet.getRow(5).createCell(6).setCellValue("Importe");
        sheet.getRow(5).createCell(7).setCellValue("");
        sheet.getRow(5).createCell(8).setCellValue("");

        sheet.setColumnWidth(0, 9 * Unidad);
        sheet.setColumnWidth(1, 26 * Unidad);
        sheet.setColumnWidth(2, 11 * Unidad);
        sheet.setColumnWidth(3, 12 * Unidad);
        sheet.setColumnWidth(4, 10 * Unidad);
        sheet.setColumnWidth(5, 10 * Unidad);

        sheet.setColumnWidth(6, 13 * Unidad);
        sheet.setColumnWidth(7, 11 * Unidad);
        sheet.setColumnWidth(8, 11 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:H6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 7; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j+1).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(4).setCellValue(Double.parseDouble(t_tabla.getValueAt(i, 5).toString()));

            /*
             sheet.getRow(fila_inicial + i).createCell(2).setCellValue(t_tabla.getValueAt(i, 2).toString());
             sheet.getRow(fila_inicial + i).createCell(3).setCellValue(t_tabla.getValueAt(i, 3).toString());
             sheet.getRow(fila_inicial + i).createCell(4).setCellValue(t_tabla.getValueAt(i, 4).toString());
             sheet.getRow(fila_inicial + i).createCell(5).setCellValue(t_tabla.getValueAt(i, 5).toString());
             sheet.getRow(fila_inicial + i).createCell(6).setCellValue(t_tabla.getValueAt(i, 6).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());
             */
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(2).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleRight);
            sheet.getRow(fila_inicial + i).getCell(5).setCellStyle(styleRight);
            sheet.getRow(fila_inicial + i).getCell(6).setCellStyle(styleRight);
            //sheet.getRow(fila_inicial + i).getCell(15).setCellStyle(styleRight);
        }
    }

    public void reporteAnimalGrafica(Table aTabla, JFreeChart Agrafica, Animal Aanimal) {

        t_tabla = aTabla;
        grafica = Agrafica;

        animal = Aanimal;

        if (t_tabla.getRowCount() <= 0) {

            System.out.println("No hay Datos");
            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }
        wb = new HSSFWorkbook();
        sheet = wb.createSheet("REPORTE DE ANIMAL");

        styles();

        reporteAnimalGraficaCrear();

        crearExcel();
    }

    private void reporteAnimalGraficaCrear() {

        Integer Unidad = 275;
        SimpleDateFormat formatoDateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:I1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:I2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:I4"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A5:I5"));
        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE ANIMAL");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);
        /**/

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 1);
        // FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //   styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        //   cell.setCellValue("Fecha de Salida de Animales: " + formatoDate.format(this.fecha));
        cell.setCellStyle(styleDateReport);
        /**/

        /*Etiqueta parametro2*/
        row = sheet.createRow(3);
        cell = row.createCell(0);
        //   cell.setCellValue("Fecha de Salida de Animales: " + formatoDate.format(this.fecha));
        cell.setCellStyle(styleDateReport);
        /**/

        //  sheet.createRow(0).createCell(2).setCellValue("REPORTE DE SALIDA");
        //sheet.createRow(0).getCell(2).setCellStyle(estiloCelda);
        // sheet.getRow(0).getCell(2).setCellStyle(new CellStyle("CENTER"));
        sheet.createRow(5).createCell(0).setCellValue("Fecha");
        sheet.getRow(5).createCell(1).setCellValue("Peso");
        sheet.getRow(5).createCell(2).setCellValue("");
        sheet.getRow(5).createCell(3).setCellValue("");
        sheet.getRow(5).createCell(4).setCellValue("");
        sheet.getRow(5).createCell(5).setCellValue("");
        sheet.getRow(5).createCell(6).setCellValue("");
        sheet.getRow(5).createCell(7).setCellValue("");
        sheet.getRow(5).createCell(8).setCellValue("");

        sheet.setColumnWidth(0, 18 * Unidad);
        sheet.setColumnWidth(1, 8 * Unidad);
        sheet.setColumnWidth(2, 11 * Unidad);
        sheet.setColumnWidth(3, 11 * Unidad);
        sheet.setColumnWidth(4, 11 * Unidad);
        sheet.setColumnWidth(5, 26 * Unidad);
        sheet.setColumnWidth(6, 11 * Unidad);
        sheet.setColumnWidth(8, 11 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
        /*
         rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
         PatternFormatting fill1 = rule1.createPatternFormatting();
         fill1.setFillBackgroundColor(IndexedColors.WHITE.index);
         fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

         FontFormatting font = rule1.createFontFormatting();
         font.setFontStyle(false, true);
         font.setFontColorIndex(IndexedColors.BLACK.index);

         CellRangeAddress[] regions = {CellRangeAddress.valueOf("A7:A20")};
        
         sheetCF.addConditionalFormatting(regions, rule1, rule1);
         */
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions2 = {CellRangeAddress.valueOf("A6:I6")};

        sheetCF.addConditionalFormatting(regions2, rule1, rule1);

        Integer fila_inicial = 6;
        Double peso;
        Integer fila_final = null;
        Date fecha = null;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            peso = Double.parseDouble(t_tabla.getValueAt(i, 1).toString().substring(0, t_tabla.getValueAt(i, 1).toString().length() - 2));

            try {
                fecha = formatoDate1.parse(t_tabla.getValueAt(i, 0).toString().substring(0, 11));

            } catch (ParseException ex) {
                Logger.getLogger(Excel.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(formatoDate.format(fecha));
            sheet.getRow(fila_inicial + i).createCell(1).setCellValue(peso);
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleRight);
            fila_final = fila_inicial + i;
        }

        //Table t_animal;
        //t_animal = Animal(id_animal);

        /*
         "Id Animal",            "EiD",              "Categoria",  
         "Fecha de Entrada",     "Arete Siniiga",    "Arete Metalico Clave", 
         "Arete Metalico Numero","Ganado amedias",   "Color Arete", 
         "Fecha de Nacimiento",  "Numero de Lote",   "Compra",
         "Proveedor",            "Peso Actual",      "Temperatura",
         "Corral"
        
 
        
         try {
         fecha = formatoDate1.parse(animal.getValueAt(0, 9).toString().substring(0, 11));
         } catch (ParseException ex) {
         Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        sheet.createRow(2).createCell(0);
        System.out.println(animal.toString());
        sheet.getRow(2).createCell(0).setCellValue(
                "Arete Visual: " + animal.arete_visual + " Proveedor: " + animal.proveedor.descripcion//+ animal.categoria
                + " Corral: " + animal.corral.nombre + " Fecha de Compra: " + formatoDate.format(animal.fecha_compra)
                + " Numero de Lote: " + animal.numero_lote);
        sheet.getRow(2).getCell(0).setCellStyle(styleCenter);

        sheet.createRow(4).createCell(0);
        sheet.getRow(4).createCell(0).setCellValue(
                "Arete Electronico: " + animal.arete_electronico + " ID SINIIGA: " + animal.arete_siniiga
                + " Peso Actual: " + animal.peso_actual);
        sheet.getRow(4).getCell(0).setCellStyle(styleCenter);

        /**/
        final BufferedImage buffer = grafica.createBufferedImage(600, 200);
        //final FileOutputStream file = new FileOutputStream("ExcelPOIGrafica.xls");

        ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffer, "png", img_bytes);
            img_bytes.flush();

        } catch (IOException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 2, 6, (short) 9, 26);

        int index = wb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG);
        // HSSFSheet sheet = wb.getSheet(SHEET_NAME);
        Drawing patriarch = sheet.createDrawingPatriarch();
        patriarch.createPicture(anchor, index);
        /**/
        /*
         Drawing drawing = sheet.createDrawingPatriarch();
         XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) 2, 6, (short) 9, 26);
         Chart chart = drawing.createChart(anchor);
         ChartLegend legend = chart.getOrCreateLegend();
         legend.setPosition(LegendPosition.RIGHT);
         chart.getOrCreateLegend();

         ScatterChartData data = chart.getChartDataFactory().createScatterChartData();

         ValueAxis bottomAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
         ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
         leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

         ChartDataSource<String> xs = DataSources.fromStringCellRange(sheet, new CellRangeAddress(6, fila_final, 0, 0));
         ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(6, fila_final, 1, 1));
         //ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(2, 2, 0, NUM_OF_COLUMNS - 1));

         data.addSerie(xs, ys1);

         //data.addSerie(xs, ys2);
         chart.plot(data, bottomAxis, leftAxis);
         */
    }

    public void reporteEntrada(Date afecha) {

        fecha_ini = afecha;

        t_tabla = cargarMovimientosEntrada(afecha);

        if (t_tabla.getRowCount() <= 0) {

            System.out.println("No hay Datos");
            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }
        wb = new HSSFWorkbook();
        sheet = wb.createSheet("Reporte de Entrada");

        styles();

        reporteEntradaCrear();

        crearExcel();
    }

    private void reporteEntradaCrear() {

        Integer Unidad = 275;
        SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:H2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:H4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE ENTRADA");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);
        /**/

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 1);
        // FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //   styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Fecha de Entrada de Animales: " + formatoDate.format(this.fecha_ini));
        cell.setCellStyle(styleDateReport);
        /**/

        //  sheet.createRow(0).createCell(2).setCellValue("REPORTE DE SALIDA");
        //sheet.createRow(0).getCell(2).setCellStyle(estiloCelda);
        // sheet.getRow(0).getCell(2).setCellStyle(new CellStyle("CENTER"));
        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(5).createCell(2).setCellValue("Corral");
        sheet.getRow(5).createCell(3).setCellValue("Proveedor");
        sheet.getRow(5).createCell(4).setCellValue("Fecha de Compra");
        sheet.getRow(5).createCell(5).setCellValue("Arete Siniiga");
        sheet.getRow(5).createCell(6).setCellValue("Arete Campaña");
        //  sheet.getRow(5).createCell(7).setCellValue("Rancho");
        sheet.getRow(5).createCell(7).setCellValue("Sexo");
        sheet.getRow(5).createCell(8).setCellValue("Fecha de Manejo");
        sheet.getRow(5).createCell(9).setCellValue("Numero de Lote");
        sheet.getRow(5).createCell(10).setCellValue("Compra");
        sheet.getRow(5).createCell(11).setCellValue("Peso Actual (Kg)");
        sheet.getRow(5).createCell(12).setCellValue("Peso de Compra");

        sheet.setColumnWidth(0, 10 * Unidad);
        sheet.setColumnWidth(1, 14 * Unidad);
        sheet.setColumnWidth(2, 18 * Unidad);
        sheet.setColumnWidth(3, 10 * Unidad);
        sheet.setColumnWidth(4, 18 * Unidad);
        sheet.setColumnWidth(5, 14 * Unidad);
        sheet.setColumnWidth(6, 15 * Unidad);
        // sheet.setColumnWidth(7, 16 * Unidad);
        sheet.setColumnWidth(7, 10 * Unidad);
        sheet.setColumnWidth(8, 18 * Unidad);
        sheet.setColumnWidth(9, 16 * Unidad);
        sheet.setColumnWidth(10, 13 * Unidad);
        sheet.setColumnWidth(11, 16 * Unidad);
        sheet.setColumnWidth(12, 16 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:P6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 0).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 13; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(11).setCellStyle(styleRight);
            sheet.getRow(fila_inicial + i).getCell(12).setCellStyle(styleRight);
        }
    }

    public void reporteCorral(Corral aCorral) {

        corral = aCorral;

        t_tabla = cargarAnimalesCorral_(corral.id_corral);

        if (t_tabla.getRowCount() <= 0) {

            System.out.println("No hay Datos");
            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }
        wb = new HSSFWorkbook();
        sheet = wb.createSheet("Reporte de Inventario");

        styles();

        reporteCorralCrear();

        crearExcel();
    }

    private void reporteCorralCrear() {

        Double total_kilos, peso_minimo, peso_maximo,
                peso_promedio, alimento_ingresado, peso_ganancia;

        Integer Unidad = 275;

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:I1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:I2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:I4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE INVENTARIO CORRAL: " + corral.nombre);
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);
        /**/

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 1);
        // FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //   styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);

        cell.setCellValue(""
                + " Total Alimento Ingresado: " + new FormatoNumero(corral.alimento_ingresado.toString()).convierte(corral.alimento_ingresado) + " ;"
                + " Total de Kilos: " + new FormatoNumero(corral.total_kilos.toString()).convierte(corral.total_kilos) + " ;"
                + " Ganancia de Peso: " + new FormatoNumero(corral.peso_ganancia.toString()).convierte(corral.peso_ganancia) + " ;"
                + " Peso Mimo: " + new FormatoNumero(corral.peso_minimo.toString()).convierte(corral.peso_minimo) + " ;"
                + " Peso Maximo: " + new FormatoNumero(corral.peso_maximo.toString()).convierte(corral.peso_maximo) + " ;"
                + " Peso Promedio: " + new FormatoNumero(corral.peso_promedio.toString()).convierte(corral.peso_promedio));

        cell.setCellStyle(styleDateReport);
        /**/

        // sheet.createRow(0).createCell(2).setCellValue("REPORTE DE SALIDA");
        // sheet.createRow(0).getCell(2).setCellStyle(estiloCelda);
        // sheet.getRow(0).getCell(2).setCellStyle(new CellStyle("CENTER"));
        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(5).createCell(2).setCellValue("Corral");
        sheet.getRow(5).createCell(3).setCellValue("Proveedor");
        sheet.getRow(5).createCell(4).setCellValue("Fecha de Compra");
        sheet.getRow(5).createCell(5).setCellValue("Arete Siniiga");
        sheet.getRow(5).createCell(6).setCellValue("Arete Campaña");

        //sheet.getRow(5).createCell(7).setCellValue("Arete Arete Metalico Numero");
        //  sheet.getRow(5).createCell(7).setCellValue("Rancho");
        sheet.getRow(5).createCell(7).setCellValue("Sexo");
        sheet.getRow(5).createCell(8).setCellValue("Fecha de Manejo");
        sheet.getRow(5).createCell(9).setCellValue("Numerode Lote");
        sheet.getRow(5).createCell(10).setCellValue("Compra");

        //sheet.getRow(5).createCell(13).setCellValue("Proveedor");
        sheet.getRow(5).createCell(11).setCellValue("Peso Actual (Kg)");
        sheet.getRow(5).createCell(12).setCellValue("Peso de Compra");

        sheet.setColumnWidth(0, 15 * Unidad);
        sheet.setColumnWidth(1, 15 * Unidad);
        sheet.setColumnWidth(2, 18 * Unidad);
        sheet.setColumnWidth(3, 19 * Unidad);
        sheet.setColumnWidth(4, 17 * Unidad);
        sheet.setColumnWidth(5, 14 * Unidad);
        sheet.setColumnWidth(6, 21 * Unidad);
        sheet.setColumnWidth(7, 15 * Unidad);
        sheet.setColumnWidth(8, 9 * Unidad);
        sheet.setColumnWidth(9, 19 * Unidad);
        sheet.setColumnWidth(10, 16 * Unidad);
        sheet.setColumnWidth(11, 10 * Unidad);
        sheet.setColumnWidth(12, 17 * Unidad);
        sheet.setColumnWidth(13, 15 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:P6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 0).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 13; j++) {
                System.out.println(i + "," + j);
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(11).setCellStyle(styleRight);
            sheet.getRow(fila_inicial + i).getCell(12).setCellStyle(styleRight);
        }
    }

    public void reporteSalida(Table aTabla) {

        t_tabla = aTabla;

        if (t_tabla.getRowCount() <= 0) {

            System.out.println("No hay Datos");
            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }
        wb = new HSSFWorkbook();
        sheet = wb.createSheet("Reporte de Salidas");
        styles();
        reporteSalidaCrear();
        crearExcel();
    }

    public void reporteSalida(Date afecha) {

        fecha_ini = afecha;

        t_tabla = cargarMovimientosSalida(afecha);

        if (t_tabla.getRowCount() <= 0) {

            System.out.println("No hay Datos");
            JOptionPane.showMessageDialog(null, "No hay datos, para exportar", gs_mensaje, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!showOpenFileDialog()) {
            return;
        }
        wb = new HSSFWorkbook();
        sheet = wb.createSheet("Reporte de Salidas");
        styles();
        reporteSalidaCrear();
        crearExcel();
    }

    private void reporteSalidaCrear() {

        Integer Unidad = 275;
        SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:H2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:H4"));

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        HSSFCellStyle styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE SALIDA");
        cell.setCellStyle(styleNameReport);
        /**/

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);
        /**/

        /*Etiqueta parametro*/
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 1);
        // FontParametroReport.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //   styleParamReport.setFont(FontParametroReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);

        if (this.fecha_ini != null) {

            cell.setCellValue("Fecha de Salida de Animales: " + formatoDate.format(this.fecha_ini));
        }
        cell.setCellStyle(styleDateReport);
        /**/

        //  sheet.createRow(0).createCell(2).setCellValue("REPORTE DE SALIDA");
        //sheet.createRow(0).getCell(2).setCellStyle(estiloCelda);
        // sheet.getRow(0).getCell(2).setCellStyle(new CellStyle("CENTER"));
        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(5).createCell(2).setCellValue("Arete Siniiga");
        sheet.getRow(5).createCell(3).setCellValue("Fecha de Movimiento");
        sheet.getRow(5).createCell(4).setCellValue("Clase de Movimiento");
        sheet.getRow(5).createCell(5).setCellValue("Numero de Pedido");
        sheet.getRow(5).createCell(6).setCellValue("Grupo de Origen");
        sheet.getRow(5).createCell(7).setCellValue("Peso (kg)");

        sheet.setColumnWidth(0, 19 * Unidad);
        sheet.setColumnWidth(1, 15 * Unidad);
        sheet.setColumnWidth(2, 15 * Unidad);
        sheet.setColumnWidth(3, 21 * Unidad);
        sheet.setColumnWidth(4, 21 * Unidad);
        sheet.setColumnWidth(5, 18 * Unidad);
        sheet.setColumnWidth(6, 17 * Unidad);
        sheet.setColumnWidth(7, 11 * Unidad);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        //Condition 1: Cell Value Is   greater than  70   (Blue Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        //Condition 2: Cell Value Is  less than      50   (Green Fill)
        ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.LT, "50");
        PatternFormatting fill2 = rule2.createPatternFormatting();
        fill2.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf("A6:H6")};

        sheetCF.addConditionalFormatting(regions, rule1, rule2);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 0).toString());
            sheet.getRow(fila_inicial + i).createCell(1).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).createCell(2).setCellValue(t_tabla.getValueAt(i, 2).toString());
            sheet.getRow(fila_inicial + i).createCell(3).setCellValue(t_tabla.getValueAt(i, 3).toString());
            sheet.getRow(fila_inicial + i).createCell(4).setCellValue(t_tabla.getValueAt(i, 4).toString());
            sheet.getRow(fila_inicial + i).createCell(5).setCellValue(t_tabla.getValueAt(i, 5).toString());
            sheet.getRow(fila_inicial + i).createCell(6).setCellValue(t_tabla.getValueAt(i, 6).toString());
            sheet.getRow(fila_inicial + i).createCell(7).setCellValue(t_tabla.getValueAt(i, 7).toString());

            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(2).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleCenter);
            sheet.getRow(fila_inicial + i).getCell(7).setCellStyle(styleRight);
        }
    }

    private void cargarLogo() {
        InputStream inputStream;
        try {

            inputStream = getClass().getResourceAsStream("/resources/logo tru-test.png");//Tru-Test.jpg");

            byte[] bytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_PNG);
            inputStream.close();
            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            //set top-left corner for the image
            anchor.setCol1(0);
            anchor.setRow1(0);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //Reset the image to the original size
            pict.resize(0.22);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean showOpenFileDialog() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        FileFilter filter1 = new ExtensionFileFilter("XLS", "XLS");

        fileChooser.setFileFilter(filter1);

        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            XLSFile = fileToSave.getAbsolutePath() + ".xls";
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            return true;
        }
        return false;
    }
}
