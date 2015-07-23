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
import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime;
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
//import static gui.Login.formatoDate;
//import static gui.Login.formatoDateTime;
import static domain.CorralAnimal.cargarAnimalesCorral_;
import static domain.Movimiento.cargarMovimientosSalida;
import static gui.Login.gs_mensaje;
import static gui.Splash.formatoDateTime_2;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
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
    private HSSFCellStyle styleBorderCompletoMedio;
    private HSSFCellStyle styleNameReport;
    private HSSFCellStyle styleDateReport;
    private HSSFCellStyle styleParamReport;
    private HSSFCellStyle styleTituloTabla;
    private HSSFCellStyle styleEtiquetaTabla;

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

        styleBorderCompletoMedio = wb.createCellStyle();
        styleBorderCompletoMedio.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleBorderCompletoMedio.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleBorderCompletoMedio.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        styleBorderCompletoMedio.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 11);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.DARK_BLUE.index);

        styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        //Etiqueta parametro
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 1);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        /*TITULO TABLA */
        HSSFFont FontTituloTabla = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 10);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        styleTituloTabla = wb.createCellStyle();
        styleTituloTabla.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleTituloTabla.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleTituloTabla.setFont(FontDateReport);

        /*ETIQUETA TABLA */
        HSSFFont FontEtiquetaTabla = wb.createFont();
        FontEtiquetaTabla.setFontName("Calibri");
        FontEtiquetaTabla.setFontHeightInPoints((short) 10);
        FontEtiquetaTabla.setColor(HSSFColor.BLACK.index);

        styleEtiquetaTabla = wb.createCellStyle();
        styleEtiquetaTabla.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleEtiquetaTabla.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleEtiquetaTabla.setFont(FontEtiquetaTabla);
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

    public Cell agregarValor(Integer fila, Integer columna, String valor) {

        Row row;
        Cell cell;

        row = sheet.getRow(fila);

        if (row == null) {

            row = sheet.createRow(fila);
        }

        cell = row.getCell(columna);

        if (cell == null) {

            cell = row.createCell(columna);
        }

        cell.setCellValue(valor);
        return cell;
    }

    public void agregarValor(Integer fila, Integer columna, String valor, HSSFCellStyle style) {

        Cell cell;
        cell = agregarValor(fila, columna, valor);
        asignarEstilo(fila, columna, style);
    }
    
    public Cell agregarValor(Integer fila, Integer columna, Integer valor) {

        Row row;
        Cell cell;

        row = sheet.getRow(fila);

        if (row == null) {

            row = sheet.createRow(fila);
        }

        cell = row.getCell(columna);

        if (cell == null) {

            cell = row.createCell(columna);
        }

        cell.setCellValue(valor);
        return cell;
    }
    
    public void agregarValor(Integer fila, Integer columna, Integer valor, HSSFCellStyle style) {

        Cell cell;
        cell = agregarValor(fila, columna, valor);
        asignarEstilo(fila, columna, style);
    }

    public void asignarEstilo(Integer fila, Integer columna, HSSFCellStyle style){
      
        Row row;
        Cell cell;

        row = sheet.getRow(fila);

        if (row == null) {

            row = sheet.createRow(fila);
        }

        cell = row.getCell(columna);

        if (cell == null) {

            cell = row.createCell(columna);
        }

        cell.setCellStyle(style);
    }
    
    public void bordes(String rango, short borde) {

        HSSFRegionUtil.setBorderTop(borde, CellRangeAddress.valueOf(rango), (HSSFSheet) sheet, wb);
        HSSFRegionUtil.setBorderLeft(borde, CellRangeAddress.valueOf(rango), (HSSFSheet) sheet, wb);
        HSSFRegionUtil.setBorderRight(borde, CellRangeAddress.valueOf(rango), (HSSFSheet) sheet, wb);
        HSSFRegionUtil.setBorderBottom(borde, CellRangeAddress.valueOf(rango), (HSSFSheet) sheet, wb);
    }

    public void relleno(String rango, short color_relleno, short color_letra) {

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(color_relleno);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(color_letra);

        CellRangeAddress[] regions = {CellRangeAddress.valueOf(rango)};

        sheetCF.addConditionalFormatting(regions, rule1, rule1);
    }

    private void tamañoColumna(Integer columna, Integer tamaño) {

        sheet.setColumnWidth(columna, tamaño * Unidad);
    }

    private void combinarRango(String rango) {

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:N1"));
    }

    public void graficar(short columna_inicial, Integer fila_inicial, short columna_final, Integer fila_final) {

        final BufferedImage buffer = grafica.createBufferedImage(600, 200);

        ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
        try {

            ImageIO.write(buffer, "png", img_bytes);
            img_bytes.flush();

        } catch (IOException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) columna_inicial, fila_inicial, (short) columna_final, fila_final);
        int index = wb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG);
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

        combinarRango("A1:N1");
        combinarRango("A2:N2");
        combinarRango("A3:N4");

        agregarValor(0, 0, "REPORTE DE SESIONES", styleNameReport);
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

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

        agregarValor(2, 0, etiqueta_parametro, styleParamReport);
        agregarValor(5, 0, "Id Animal");
        agregarValor(5, 1, "Arete Electronico");
        agregarValor(5, 2, "Fecha");
        agregarValor(5, 3, "Peso");
        agregarValor(5, 4, "Corral");
        agregarValor(5, 5, "");
        agregarValor(5, 6, "Arete Visual");
        agregarValor(5, 7, "Codigo");
        agregarValor(5, 8, "Medicina");
        agregarValor(5, 9, "Fecha");
        agregarValor(5, 10, "Corral");
        agregarValor(5, 11, "Dosis");
        agregarValor(5, 12, "Costo");
        agregarValor(5, 13, "Importe");

        tamañoColumna(0, 13);
        tamañoColumna(1, 14);
        tamañoColumna(2, 12);
        tamañoColumna(3, 10);
        tamañoColumna(4, 14);

        tamañoColumna(5, 2);

        tamañoColumna(6, 14);
        tamañoColumna(7, 11);
        tamañoColumna(8, 14);
        tamañoColumna(9, 12);
        tamañoColumna(10, 14);
        tamañoColumna(11, 10);
        tamañoColumna(12, 10);
        tamañoColumna(13, 10);

        relleno("A6:N6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 1).toString(), styleCenter);

            for (int j = 1; j < 5; j++) {
                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j + 1).toString(), styleCenter);
            }

            agregarValor(fila_inicial + i, 3, t_tabla.getValueAt(i, 4).toString(), styleRight);
        }

        Integer columna = 6;

        for (int i = 0; i < this.t_tabla2.getRowCount(); i++) {

            for (int j = 0; j < 8; j++) {
                agregarValor(fila_inicial + i, columna + j, t_tabla2.getValueAt(i, j + 1).toString(), styleCenter);
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

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        agregarValor(0, 0, "REPORTE DE MUERTES", styleNameReport);
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

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

        agregarValor(2, 0, etiqueta_parametro, styleParamReport);

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Fecha de Muerte");
        agregarValor(5, 2, "Necropcia");
        agregarValor(5, 3, "Dx de Muerte");
        agregarValor(5, 4, "Dias Muerte");
        agregarValor(5, 5, "Etapa Reproductiva");

        tamañoColumna(0, 13);
        tamañoColumna(1, 16);
        tamañoColumna(2, 20);
        tamañoColumna(3, 27);
        tamañoColumna(4, 14);
        tamañoColumna(5, 18);

        relleno("A6:H6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 1).toString(), styleCenter);

            for (int j = 1; j < 6; j++) {

                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j + 1).toString(), styleCenter);
            }
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

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        agregarValor(0, 0, "REPORTE DE TRASPASOS", styleNameReport);
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

        String etiqueta_parametro = "";

        etiqueta_parametro = "Traspasos del dia " + formatoDate.format(fecha);
        if (tipo == 1) {

            etiqueta_parametro = "Todos los traspasos";
        }

        agregarValor(2, 0, etiqueta_parametro, styleParamReport);

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Arete Electronico");
        agregarValor(5, 2, "Fecha de Movimiento");
        agregarValor(5, 3, "Grupo Origen");
        agregarValor(5, 4, "Grupo Destino");

        tamañoColumna(0, 13);
        tamañoColumna(1, 14);
        tamañoColumna(2, 20);
        //tamañoColumna(3, 15);
        tamañoColumna(3, 27);
        tamañoColumna(4, 14);

        relleno("A6:H6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            //sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            //for (int j = 0; j < 5; j++) {
            for (int j = 0; j < 5; j++) {
                //agregarValor(fila_inicial + i, j, "Arete Visual");                
                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j).toString(), styleCenter);                
                //sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                //sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }
            
            asignarEstilo(fila_inicial + i, 3, styleleft);
            asignarEstilo(fila_inicial + i, 4, styleleft);

            //sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleleft);
            //sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
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

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        agregarValor(0, 0, "REPORTE DE ANIMALES EN HOSPITAL", styleNameReport);
        /*
         Row row = sheet.createRow(0);
         Cell cell = row.createCell(0);
         cell.setCellValue("REPORTE DE ANIMALES EN HOSPITAL");
         cell.setCellStyle(styleNameReport);
         */
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);
        /*
         row = sheet.createRow(1);
         cell = row.createCell(0);
         cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
         cell.setCellStyle(styleDateReport);
         */

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Fecha Entrada");
        agregarValor(5, 2, "Dias en Hospital");
        agregarValor(5, 3, "Causa Entrada");
        agregarValor(5, 4, "Observaciones");
        /*
         sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
         sheet.getRow(5).createCell(1).setCellValue("Fecha Entrada");
         sheet.getRow(5).createCell(2).setCellValue("Dias en Hospital");
         sheet.getRow(5).createCell(3).setCellValue("Causa Entrada");
         sheet.getRow(5).createCell(4).setCellValue("Observaciones");
         */

        tamañoColumna(0, 13);
        tamañoColumna(1, 14);
        tamañoColumna(2, 15);
        tamañoColumna(3, 27);
        tamañoColumna(4, 14);

        relleno("A6:H6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            //sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            //for (int j = 1; j < 5; j++) {
            for (int j = 0; j < 5; j++) {

                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j).toString(), styleCenter);
                //sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                //sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }
            
            agregarValor(fila_inicial + i , 2,Integer.parseInt(t_tabla.getValueAt(i, 3).toString()) );
            
            //sheet.getRow(fila_inicial + i).getCell(2).setCellValue(Integer.parseInt(t_tabla.getValueAt(i, 3).toString()));
            
            
            sheet.getRow(fila_inicial + i).getCell(3).setCellStyle(styleleft);
            sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
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

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE HISTORICO DE HOSPITAL");
        cell.setCellStyle(styleNameReport);

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

        tamañoColumna(0, 13);
        tamañoColumna(1, 14);
        tamañoColumna(2, 15);
        tamañoColumna(3, 15);
        tamañoColumna(4, 27);
        tamañoColumna(5, 14);
        tamañoColumna(6, 13);
        tamañoColumna(7, 11);
        tamañoColumna(8, 11);

        relleno("A6:H6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 6; j++) {
                sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(3).setCellValue(Integer.parseInt(t_tabla.getValueAt(i, 4).toString()));
            sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
            sheet.getRow(fila_inicial + i).getCell(5).setCellStyle(styleleft);
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

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        agregarValor(0, 0, "REPORTE DE MEDICINA ANIMAL ID: " + animal.arete_visual, styleNameReport);
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);
        agregarValor(2, 0, "Costo Total en Medicinas: $ " + costo + " MXN", styleParamReport);

        agregarValor(5, 0, "Codigo");
        agregarValor(5, 1, "Medicina");
        agregarValor(5, 2, "Unidades");
        agregarValor(5, 3, "Fecha");
        agregarValor(5, 4, "Dosis");
        agregarValor(5, 5, "Costo");
        agregarValor(5, 6, "Importe");

        tamañoColumna(0, 9);
        tamañoColumna(1, 26);
        tamañoColumna(2, 11);
        tamañoColumna(3, 12);
        tamañoColumna(4, 10);
        tamañoColumna(5, 10);
        tamañoColumna(6, 13);
        tamañoColumna(7, 11);
        tamañoColumna(8, 11);

        relleno("A6:H6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 1).toString());
            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 7; j++) {
                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j + 1).toString(), styleCenter);
            }

            agregarValor(fila_inicial + i, 4, t_tabla.getValueAt(i, 5).toString(), styleRight);

            sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleRight);
            sheet.getRow(fila_inicial + i).getCell(5).setCellStyle(styleRight);
            sheet.getRow(fila_inicial + i).getCell(6).setCellStyle(styleRight);
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

        SimpleDateFormat formatoDateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        combinarRango("A1:I1");
        combinarRango("A2:I2");
        combinarRango("A3:I4");
        combinarRango("A5:I5");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE ANIMAL");
        cell.setCellStyle(styleNameReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellStyle(styleDateReport);

        /*Etiqueta parametro2*/
        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellStyle(styleDateReport);

        Integer fila_encabezado = 5;

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

        tamañoColumna(0, 18);
        tamañoColumna(1, 8);
        tamañoColumna(2, 11);
        tamañoColumna(3, 11);
        tamañoColumna(4, 11);
        tamañoColumna(5, 26);
        tamañoColumna(6, 11);
        tamañoColumna(8, 11);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, "1000");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.BLUE.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        FontFormatting font = rule1.createFontFormatting();
        font.setFontStyle(false, true);
        font.setFontColorIndex(IndexedColors.WHITE.index);

        CellRangeAddress[] regions2 = {CellRangeAddress.valueOf("A6:I6")};

        sheetCF.addConditionalFormatting(regions2, rule1, rule1);

        Integer fila_inicial = fila_encabezado + 1;
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

        graficar((short) 2, 6, (short) 9, 26);

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

        SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE ENTRADA");
        cell.setCellStyle(styleNameReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Fecha de Entrada de Animales: " + formatoDate.format(this.fecha_ini));
        cell.setCellStyle(styleDateReport);

        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(5).createCell(2).setCellValue("Corral");
        sheet.getRow(5).createCell(3).setCellValue("Proveedor");
        sheet.getRow(5).createCell(4).setCellValue("Fecha de Compra");
        sheet.getRow(5).createCell(5).setCellValue("Arete Siniiga");
        sheet.getRow(5).createCell(6).setCellValue("Arete Campaña");
        sheet.getRow(5).createCell(7).setCellValue("Sexo");
        sheet.getRow(5).createCell(8).setCellValue("Fecha de Manejo");
        sheet.getRow(5).createCell(9).setCellValue("Numero de Lote");
        sheet.getRow(5).createCell(10).setCellValue("Compra");
        sheet.getRow(5).createCell(11).setCellValue("Peso Actual (Kg)");
        sheet.getRow(5).createCell(12).setCellValue("Peso de Compra");

        tamañoColumna(0, 10);
        tamañoColumna(1, 14);
        tamañoColumna(2, 18);
        tamañoColumna(3, 10);
        tamañoColumna(4, 18);
        tamañoColumna(5, 14);
        tamañoColumna(6, 15);
        // tamañoColumna(7, 16);
        tamañoColumna(7, 10);
        tamañoColumna(8, 18);
        tamañoColumna(9, 16);
        tamañoColumna(10, 13);
        tamañoColumna(11, 16);
        tamañoColumna(12, 16);

        relleno("A6:P6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

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

    public void reporteCorral(Corral aCorral, JFreeChart Agrafica) {

        corral = aCorral;
        grafica = Agrafica;

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
        sheet.setDisplayGridlines(false);
        styles();

        reporteCorralCrear();

        crearExcel();
    }

    private void reporteCorralCrear() {

        Double total_kilos, peso_minimo, peso_maximo,
                peso_promedio, alimento_ingresado, peso_ganancia;

        Integer fila_encabezado = 19;

        cargarLogo();

        combinarRango("A1:I1");
        combinarRango("A2:I2");
        combinarRango("A3:I4");

        agregarValor(0, 0, "REPORTE DE INVENTARIO", styleNameReport);

        /**
         * Tabla Datos informativos /*
         */
        combinarRango("A6:C6");

        agregarValor(5, 0, "DATOS INFORMATIVOS", styleTituloTabla);
        agregarValor(6, 0, "FECHA ELABORACIÓN", styleEtiquetaTabla);
        agregarValor(7, 0, "NOMBRE DEL CORRAL", styleEtiquetaTabla);
        agregarValor(8, 0, "TOTAL DE ALIMENTO INGRESADO", styleEtiquetaTabla);
        agregarValor(9, 0, "TOTAL KILOS INICIO DE CORRAL", styleEtiquetaTabla);
        agregarValor(10, 0, "TOTAL KILOS FINAL DE CORRAL", styleEtiquetaTabla);
        agregarValor(11, 0, "GANANCIA DE PESO DEL CORRAL", styleEtiquetaTabla);
        agregarValor(12, 0, "PESO MAXIMO", styleEtiquetaTabla);
        agregarValor(13, 0, "PESO MINIMO", styleEtiquetaTabla);
        agregarValor(14, 0, "PESO PROMEDIO", styleEtiquetaTabla);

        agregarValor(6, 2, formatoDateTime.format(new Date()), styleCenter);
        agregarValor(7, 2, corral.nombre, styleCenter);
        agregarValor(8, 2, new FormatoNumero(corral.alimento_ingresado.toString()).convierte(corral.alimento_ingresado), styleRight);
        agregarValor(9, 2, new FormatoNumero(corral.alimento_ingresado.toString()).convierte(corral.total_kilos_inicial), styleRight);
        agregarValor(10, 2, new FormatoNumero(corral.total_kilos.toString()).convierte(corral.total_kilos), styleRight);
        agregarValor(11, 2, new FormatoNumero(corral.peso_ganancia.toString()).convierte(corral.peso_ganancia), styleRight);
        agregarValor(12, 2, new FormatoNumero(corral.peso_maximo.toString()).convierte(corral.peso_maximo), styleRight);
        agregarValor(13, 2, new FormatoNumero(corral.peso_minimo.toString()).convierte(corral.peso_minimo), styleRight);
        agregarValor(14, 2, new FormatoNumero(corral.peso_promedio.toString()).convierte(corral.peso_promedio), styleRight);

        String rango;
        for (int i = 7; i < 16; i++) {

            rango = "A" + i + ":B" + i;

            combinarRango(rango);
            this.bordes(rango, CellStyle.BORDER_THIN);

            this.bordes("C" + i + ":C" + i, CellStyle.BORDER_THIN);
        }

        this.bordes("A6:C6", CellStyle.BORDER_MEDIUM);
        this.bordes("A6:C15", CellStyle.BORDER_MEDIUM);

        /**
         * Tabla Resultados y Rendimientos
         */
        combinarRango("E6:H6");

        agregarValor(5, 4, "DATOS Y RENDIMIENTOS", styleTituloTabla);
        agregarValor(6, 4, "GANANCIA DE PESO X precio DE CARNE", styleEtiquetaTabla);
        agregarValor(7, 4, "COSTO TOTAL DE MEDICAMENTOS INGRESADOS", styleEtiquetaTabla);
        agregarValor(8, 4, "COSTO TOTAL DE ALIMENTO INGRESADO", styleEtiquetaTabla);
        agregarValor(9, 4, "UTILIDAD IDEAL SIN GASTOS DE OPERACIÓN", styleEtiquetaTabla);
        agregarValor(10, 4, "SUELDOS", styleEtiquetaTabla);
        agregarValor(11, 4, "GASTOS FIJOS", styleEtiquetaTabla);
        agregarValor(12, 4, "GASTOS VARIOS", styleEtiquetaTabla);
        agregarValor(13, 4, "UTILIDAD FINAL", styleEtiquetaTabla);
        //agregarValor(14, 3, "PESO PROMEDIO", styleEtiquetaTabla);

        agregarValor(6, 7, new FormatoNumero(corral.ganancia_precio_carne.toString()).convierte(corral.ganancia_precio_carne), styleRight);
        agregarValor(7, 7, new FormatoNumero(corral.total_costo_medicina.toString()).convierte(corral.total_costo_medicina), styleRight);
        agregarValor(8, 7, new FormatoNumero(corral.costo_alimento.toString()).convierte(corral.costo_alimento), styleRight);
        agregarValor(9, 7, new FormatoNumero(corral.utilidad_s_gastos.toString()).convierte(corral.utilidad_s_gastos), styleRight);

        for (int i = 7; i < 16; i++) {

            rango = "E" + i + ":G" + i;

            combinarRango(rango);
            this.bordes(rango, CellStyle.BORDER_THIN);

            this.bordes("H" + i + ":H" + i, CellStyle.BORDER_THIN);

        }
        this.bordes("E6:H6", CellStyle.BORDER_MEDIUM);
        this.bordes("E6:H15", CellStyle.BORDER_MEDIUM);

        sheet.createRow(fila_encabezado).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(fila_encabezado).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(fila_encabezado).createCell(2).setCellValue("Corral");
        sheet.getRow(fila_encabezado).createCell(3).setCellValue(""); // Espacio
        sheet.getRow(fila_encabezado).createCell(4).setCellValue("Proveedor");
        sheet.getRow(fila_encabezado).createCell(5).setCellValue("Fecha de Compra");
        sheet.getRow(fila_encabezado).createCell(6).setCellValue("Arete Siniiga");
        sheet.getRow(fila_encabezado).createCell(7).setCellValue("Arete Campaña");

        sheet.getRow(fila_encabezado).createCell(8).setCellValue("Sexo");
        sheet.getRow(fila_encabezado).createCell(9).setCellValue("Fecha de Manejo");
        sheet.getRow(fila_encabezado).createCell(10).setCellValue("Numerode Lote");
        sheet.getRow(fila_encabezado).createCell(11).setCellValue("Compra");

        //sheet.getRow(5).createCell(13).setCellValue("Proveedor");
        sheet.getRow(fila_encabezado).createCell(12).setCellValue("Peso Actual (Kg)");
        sheet.getRow(fila_encabezado).createCell(13).setCellValue("Peso de Compra");

        tamañoColumna(0, 15);
        tamañoColumna(1, 15);
        tamañoColumna(2, 18);
        tamañoColumna(3, 1);// Espacio
        tamañoColumna(4, 19);
        tamañoColumna(5, 17);
        tamañoColumna(6, 14);
        tamañoColumna(7, 21);
        tamañoColumna(8, 15);
        tamañoColumna(9, 9);
        tamañoColumna(10, 19);
        tamañoColumna(11, 16);
        tamañoColumna(12, 10);
        tamañoColumna(13, 17);
        tamañoColumna(14, 15);

        relleno("A" + (fila_encabezado + 1) + ":N" + (fila_encabezado + 1), IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = fila_encabezado + 1;
        Integer columna;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 0).toString());

            sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(bordes(cell.getCellStyle(), HSSFCellStyle.BORDER_THIN));
            for (int j = 1; j < 13; j++) {
                /// System.out.println(i + "," + j); 
                columna = j;
                if (j > 2) {
                    columna = j + 1;
                }

                sheet.getRow(fila_inicial + i).createCell(columna).setCellValue(t_tabla.getValueAt(i, j).toString());
                sheet.getRow(fila_inicial + i).getCell(columna).setCellStyle(styleCenter);
            }

            sheet.getRow(fila_inicial + i).getCell(12).setCellStyle(styleRight);
            sheet.getRow(fila_inicial + i).getCell(13).setCellStyle(styleRight);
        }

        agregarValor(5, 8, "GRAFICA DE GANANCIA DE PESO", styleNameReport);

        /**
         * Tabla Datos informativos /*
         */
        combinarRango("I6:M6");

        graficar((short) 8, 6, (short) 13, 18);
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

        SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("REPORTE DE SALIDA");
        cell.setCellStyle(styleNameReport);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("FECHA DE REPORTE: " + formatoDateTime.format(new Date()));
        cell.setCellStyle(styleDateReport);

        row = sheet.createRow(2);
        cell = row.createCell(0);

        if (this.fecha_ini != null) {

            cell.setCellValue("Fecha de Salida de Animales: " + formatoDate.format(this.fecha_ini));
        }
        cell.setCellStyle(styleDateReport);

        sheet.createRow(5).createCell(0).setCellValue("Arete Visual");
        sheet.getRow(5).createCell(1).setCellValue("Arete Electronico");
        sheet.getRow(5).createCell(2).setCellValue("Arete Siniiga");
        sheet.getRow(5).createCell(3).setCellValue("Fecha de Movimiento");
        sheet.getRow(5).createCell(4).setCellValue("Clase de Movimiento");
        sheet.getRow(5).createCell(5).setCellValue("Numero de Pedido");
        sheet.getRow(5).createCell(6).setCellValue("Grupo de Origen");
        sheet.getRow(5).createCell(7).setCellValue("Peso (kg)");

        tamañoColumna(0, 19);
        tamañoColumna(1, 15);
        tamañoColumna(2, 15);
        tamañoColumna(3, 21);
        tamañoColumna(4, 21);
        tamañoColumna(5, 18);
        tamañoColumna(6, 17);
        tamañoColumna(7, 11);

        relleno("A6:H6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

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
