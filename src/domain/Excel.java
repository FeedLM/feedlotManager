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
import static gui.Splash.formatoDateTime_11;
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

        /*ETIQUETA TABLA */
        HSSFFont FontNormal = wb.createFont();
        FontNormal.setFontName("Calibri");
        FontNormal.setFontHeightInPoints((short) 8);
        FontNormal.setColor(HSSFColor.BLACK.index);

        styleCenter = wb.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleCenter.setFont(FontNormal);

        styleRight = wb.createCellStyle();
        styleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        styleRight.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleRight.setFont(FontNormal);

        styleleft = wb.createCellStyle();
        styleleft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleleft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleleft.setFont(FontNormal);

        styleBorderCompletoMedio = wb.createCellStyle();
        styleBorderCompletoMedio.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleBorderCompletoMedio.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleBorderCompletoMedio.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        styleBorderCompletoMedio.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

        /*Name REPORT*/
        HSSFFont FontNameReport = wb.createFont();
        FontNameReport.setFontName("Calibri");
        FontNameReport.setFontHeightInPoints((short) 16);
        FontNameReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontNameReport.setColor(HSSFColor.BROWN.index);

        styleNameReport = wb.createCellStyle();
        styleNameReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleNameReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleNameReport.setFont(FontNameReport);

        /*DATE REPORT*/
        HSSFFont FontDateReport = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 8);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        styleDateReport = wb.createCellStyle();
        styleDateReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleDateReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDateReport.setFont(FontDateReport);

        //Etiqueta parametro
        HSSFFont FontParametroReport = wb.createFont();
        FontParametroReport.setFontName("Calibri");
        FontParametroReport.setFontHeightInPoints((short) 8);
        FontParametroReport.setColor(HSSFColor.BLACK.index);

        styleParamReport = wb.createCellStyle();
        styleParamReport.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleParamReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        /*TITULO TABLA */
        HSSFFont FontTituloTabla = wb.createFont();
        FontDateReport.setFontName("Calibri");
        FontDateReport.setFontHeightInPoints((short) 8);
        FontDateReport.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontDateReport.setColor(HSSFColor.BLACK.index);

        styleTituloTabla = wb.createCellStyle();
        styleTituloTabla.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleTituloTabla.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleTituloTabla.setFont(FontDateReport);

        /*ETIQUETA TABLA */
        HSSFFont FontEtiquetaTabla = wb.createFont();
        FontEtiquetaTabla.setFontName("Calibri");
        FontEtiquetaTabla.setFontHeightInPoints((short) 8);
        FontEtiquetaTabla.setColor(HSSFColor.BLACK.index);

        styleEtiquetaTabla = wb.createCellStyle();
        styleEtiquetaTabla.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleEtiquetaTabla.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleEtiquetaTabla.setFont(FontEtiquetaTabla);
    }

    public Cell agregarValor(Integer fila, Integer columna, String valor) {

        Row row = recuperarFila(fila);
        Cell cell = recuperarCelda(row, columna);

        cell.setCellValue(valor);
        return cell;
    }

    public void agregarValor(Integer fila, Integer columna, String valor, HSSFCellStyle style) {

        Cell cell;
        cell = agregarValor(fila, columna, valor);
        asignarEstilo(fila, columna, style);
    }

    public Cell agregarValor(Integer fila, Integer columna, Integer valor) {

        Row row = recuperarFila(fila);
        Cell cell = recuperarCelda(row, columna);

        cell.setCellValue(valor);
        return cell;
    }

    public void agregarValor(Integer fila, Integer columna, Integer valor, HSSFCellStyle style) {

        Cell cell;
        cell = agregarValor(fila, columna, valor);
        asignarEstilo(fila, columna, style);
    }

    public Cell agregarValor(Integer fila, Integer columna, Double valor) {

        Row row = recuperarFila(fila);
        Cell cell = recuperarCelda(row, columna);

        cell.setCellValue(valor);
        return cell;
    }

    public void agregarValor(Integer fila, Integer columna, Double valor, HSSFCellStyle style) {

        Cell cell;
        cell = agregarValor(fila, columna, valor);
        asignarEstilo(fila, columna, style);
    }

    public void asignarEstilo(Integer fila, Integer columna, HSSFCellStyle style) {

        Row row = recuperarFila(fila);
        Cell cell = recuperarCelda(row, columna);

        cell.setCellStyle(style);
    }

    public Row recuperarFila(Integer fila) {

        Row row;

        row = sheet.getRow(fila);

        if (row == null) {

            row = sheet.createRow(fila);
        }

        return row;
    }

    public Cell recuperarCelda(Row row, Integer columna) {

        Cell cell;

        cell = row.getCell(columna);

        if (cell == null) {

            cell = row.createCell(columna);
        }

        return cell;
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

        sheet.addMergedRegion(CellRangeAddress.valueOf(rango));
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

    private void cargarLogo() {

        cargarLogo1(0, 0, 0.22);
    }

    private void cargarLogo1(Integer fila, Integer columna, Double resize) {
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
            anchor.setCol1(columna);
            anchor.setRow1(fila);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //Reset the image to the original size
            pict.resize(resize);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarLogo2(Integer fila, Integer columna, Double resize) {
        InputStream inputStream;
        try {

            inputStream = getClass().getResourceAsStream("/resources/LOGO.png");//Tru-Test.jpg");

            byte[] bytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_PNG);
            inputStream.close();
            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            //set top-left corner for the image
            anchor.setCol1(columna);
            anchor.setRow1(fila);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //Reset the image to the original size
            pict.resize(resize);

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

    public void reporteTraspasos(Table aTabla, Integer tipo, Date fecha_ini, Date fecha_fin, Corral corral) {

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

        reporteTraspasos(tipo, fecha_ini, fecha_fin, corral);

        crearExcel();
    }

    private void reporteTraspasos(Integer tipo, Date fecha_ini, Date fecha_fin, Corral corral) {

        cargarLogo();
        combinarRango("A1:E1");
        combinarRango("A2:E2");
        combinarRango("A3:E4");

        agregarValor(0, 0, "REPORTE DE TRASPASOS", styleNameReport);
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

        String etiqueta_parametro = "";

        switch (tipo) {
            case 1:
                etiqueta_parametro = "Todos los traspasos";
                break;
            case 2:
            case 3:
                etiqueta_parametro = "Traspasos del día " + formatoDate.format(fecha_ini);
                break;
//            case 3: 4:
//                etiqueta_parametro = "Traspasos del día " + formatoDate.format(fecha_ini);
//                break;
            case 4:
                etiqueta_parametro = "Traspasos del día " + formatoDate.format(fecha_ini) + " al " + formatoDate.format(fecha_fin);
                break;
        }
        etiqueta_parametro += " del corral " + corral.nombre;
//        etiqueta_parametro = "Traspasos del dia " + formatoDate.format(fecha_ini);
//        if (tipo == 1) {
//
//            etiqueta_parametro = "Todos los traspasos";
//        }
//        if (tipo == 3)

        agregarValor(2, 0, etiqueta_parametro, styleParamReport);

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Arete Electronico");
        agregarValor(5, 2, "Fecha de Movimiento");
        agregarValor(5, 3, "Grupo Origen");
        agregarValor(5, 4, "Grupo Destino");

        tamañoColumna(0, 13);
        tamañoColumna(1, 16);
        tamañoColumna(2, 20);
        tamañoColumna(3, 27);
        tamañoColumna(4, 27);

        relleno("A6:E6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            for (int j = 0; j < 5; j++) {

                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j + 1).toString(), styleCenter);
            }
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

        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Fecha Entrada");
        agregarValor(5, 2, "Dias en Hospital");
        agregarValor(5, 3, "Causa Entrada");
        agregarValor(5, 4, "Observaciones");

        tamañoColumna(0, 13);
        tamañoColumna(1, 14);
        tamañoColumna(2, 15);
        tamañoColumna(3, 27);
        tamañoColumna(4, 14);

        relleno("A6:H6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = 6;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            for (int j = 0; j < 5; j++) {

                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j).toString(), styleCenter);
            }

            agregarValor(fila_inicial + i, 2, Integer.parseInt(t_tabla.getValueAt(i, 3).toString()));
            asignarEstilo(fila_inicial + i, 3, styleleft);
            asignarEstilo(fila_inicial + i, 4, styleleft);
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
        //combinarRango("A3:H4");

        agregarValor(0, 0, "REPORTE DE HISTORICO DE HOSPITAL", styleNameReport);
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Fecha Entrada");
        agregarValor(5, 2, "Fecha de Salida");
        agregarValor(5, 3, "Dias en Hospital");
        agregarValor(5, 4, "Causa Entrada");
        agregarValor(5, 5, "Observaciones");

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

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 1).toString(), styleCenter);

            // sheet.createRow(fila_inicial + i).createCell(0).setCellValue(t_tabla.getValueAt(i, 1).toString());
            // sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            for (int j = 1; j < 6; j++) {

                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j + 1).toString(), styleCenter);

                //sheet.getRow(fila_inicial + i).createCell(j).setCellValue(t_tabla.getValueAt(i, j + 1).toString());
                //sheet.getRow(fila_inicial + i).getCell(j).setCellStyle(styleCenter);
            }

            agregarValor(fila_inicial + i, 3, Integer.parseInt(t_tabla.getValueAt(i, 4).toString()));
            asignarEstilo(fila_inicial + i, 3, styleleft);
            asignarEstilo(fila_inicial + i, 5, styleleft);

            //sheet.getRow(fila_inicial + i).getCell(3).setCellValue(Integer.parseInt(t_tabla.getValueAt(i, 4).toString()));
            //sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleleft);
            //sheet.getRow(fila_inicial + i).getCell(5).setCellStyle(styleleft);
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

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 1).toString(), styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);

            for (int j = 1; j < 7; j++) {
                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j + 1).toString(), styleCenter);
            }

            agregarValor(fila_inicial + i, 4, t_tabla.getValueAt(i, 5).toString(), styleRight);
            asignarEstilo(fila_inicial + i, 5, styleRight);
            asignarEstilo(fila_inicial + i, 6, styleRight);

            //sheet.getRow(fila_inicial + i).getCell(4).setCellStyle(styleRight);
            //sheet.getRow(fila_inicial + i).getCell(5).setCellStyle(styleRight);
            //sheet.getRow(fila_inicial + i).getCell(6).setCellStyle(styleRight);
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

        cargarLogo();

        combinarRango("A1:I1");
        combinarRango("A2:I2");
        combinarRango("A3:I4");
        combinarRango("A5:I5");

        agregarValor(0, 0, "REPORTE DE ANIMAL", styleNameReport);

        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

        agregarValor(2, 0, "Arete Visual: " + animal.arete_visual + " Proveedor: " + animal.proveedor.descripcion
                + " Corral: " + animal.corral.nombre + " Fecha de Compra: " + formatoDate.format(animal.fecha_compra)
                + " Numero de Lote: " + animal.numero_lote, styleCenter);

        //sheet.createRow(4).createCell(0);
        agregarValor(4, 0, "Arete Electronico: " + animal.arete_electronico + " ID SINIIGA: " + animal.arete_siniiga
                + " Peso Actual: " + animal.peso_actual, styleCenter);

        Integer fila_encabezado = 5;

        agregarValor(5, 0, "Fecha");
        agregarValor(5, 1, "Peso");

        tamañoColumna(0, 18);
        tamañoColumna(1, 8);
        tamañoColumna(2, 11);
        tamañoColumna(3, 11);
        tamañoColumna(4, 11);
        tamañoColumna(5, 26);
        tamañoColumna(6, 11);
        tamañoColumna(8, 11);

        relleno("A6:I6", IndexedColors.BLUE.index, IndexedColors.WHITE.index);

        Integer fila_inicial = fila_encabezado + 1;
        Double peso;
        Integer fila_final = null;
        Date fecha = null;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            peso = Double.parseDouble(t_tabla.getValueAt(i, 1).toString().substring(0, t_tabla.getValueAt(i, 1).toString().length() - 2));

            try {
                fecha = formatoDate.parse(t_tabla.getValueAt(i, 0).toString().substring(0, 11));

            } catch (ParseException ex) {
                Logger.getLogger(Excel.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            agregarValor(fila_inicial + i, 0, formatoDate.format(fecha), styleCenter);
            //sheet.createRow(fila_inicial + i).createCell(0).setCellValue(formatoDate.format(fecha));
            agregarValor(fila_inicial + i, 1, peso, styleRight);
            //sheet.getRow(fila_inicial + i).createCell(1).setCellValue(peso);
            // sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(styleCenter);
            //sheet.getRow(fila_inicial + i).getCell(1).setCellStyle(styleRight);
            fila_final = fila_inicial + i;
        }

        graficar((short) 2, 6, (short) 9, 26);
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

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        agregarValor(0, 0, "REPORTE DE ENTRADA", styleNameReport);
        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime_11.format(new Date()), styleDateReport);
        agregarValor(2, 0, "Fecha de Entrada de Animales: " + formatoDate.format(this.fecha_ini), styleDateReport);

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Arete Electronico");
        agregarValor(5, 2, "Corral");
        agregarValor(5, 3, "Proveedor");
        agregarValor(5, 4, "Fecha de Compra");
        agregarValor(5, 5, "Arete Siniiga");
        agregarValor(5, 6, "Arete Campaña");
        agregarValor(5, 7, "Sexo");
        agregarValor(5, 8, "Fecha de Manejo");
        agregarValor(5, 9, "Numero de Lote");
        agregarValor(5, 10, "Compra");
        agregarValor(5, 11, "Peso Actual (Kg)");
        agregarValor(5, 12, "Peso de Compra");

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

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 0).toString(), styleCenter);

            for (int j = 1; j < 13; j++) {
                agregarValor(fila_inicial + i, j, t_tabla.getValueAt(i, j).toString(),styleCenter);
            }

            asignarEstilo(fila_inicial + i,11,styleRight);
            asignarEstilo(fila_inicial + i,12,styleRight);            
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

        Integer fila_encabezado = 18;

        //cargarLogo();
        cargarLogo2(1, 0, 1.0);

        cargarLogo1(1, 11, 0.22);

        combinarRango("A1:M4");
        //combinarRango("A2:M2");
        //combinarRango("A3:M4");

        agregarValor(0, 0, "REPORTE DE INVENTARIO", styleNameReport);

        /**
         * Tabla Datos informativos /*
         */
        Integer fila_tablas = 7;

        combinarRango("A" + (fila_tablas + 1) + ":C" + (fila_tablas + 1));
        relleno("A" + (fila_tablas + 1) + ":C" + (fila_tablas + 1), IndexedColors.DARK_RED.index, IndexedColors.WHITE.index);

        agregarValor(fila_tablas, 0, "DATOS INFORMATIVOS", styleTituloTabla);
        agregarValor(fila_tablas + 1, 0, "FECHA ELABORACIÓN", styleEtiquetaTabla);
        agregarValor(fila_tablas + 2, 0, "NOMBRE DEL CORRAL", styleEtiquetaTabla);
        agregarValor(fila_tablas + 3, 0, "TOTAL DE ALIMENTO INGRESADO", styleEtiquetaTabla);
        agregarValor(fila_tablas + 4, 0, "TOTAL KILOS INICIO DE CORRAL", styleEtiquetaTabla);
        agregarValor(fila_tablas + 5, 0, "TOTAL KILOS FINAL DE CORRAL", styleEtiquetaTabla);
        agregarValor(fila_tablas + 6, 0, "GANANCIA DE PESO DEL CORRAL", styleEtiquetaTabla);
        agregarValor(fila_tablas + 7, 0, "PESO MAXIMO", styleEtiquetaTabla);
        agregarValor(fila_tablas + 8, 0, "PESO MINIMO", styleEtiquetaTabla);
        agregarValor(fila_tablas + 9, 0, "PESO PROMEDIO", styleEtiquetaTabla);

        agregarValor(fila_tablas + 1, 2, formatoDateTime.format(new Date()), styleCenter);
        agregarValor(fila_tablas + 2, 2, corral.nombre, styleCenter);
        agregarValor(fila_tablas + 3, 2, new FormatoNumero(corral.alimento_ingresado.toString()).convierte(corral.alimento_ingresado), styleRight);
        agregarValor(fila_tablas + 4, 2, new FormatoNumero(corral.alimento_ingresado.toString()).convierte(corral.total_kilos_inicial), styleRight);
        agregarValor(fila_tablas + 5, 2, new FormatoNumero(corral.total_kilos.toString()).convierte(corral.total_kilos), styleRight);
        agregarValor(fila_tablas + 6, 2, new FormatoNumero(corral.peso_ganancia.toString()).convierte(corral.peso_ganancia), styleRight);
        agregarValor(fila_tablas + 7, 2, new FormatoNumero(corral.peso_maximo.toString()).convierte(corral.peso_maximo), styleRight);
        agregarValor(fila_tablas + 8, 2, new FormatoNumero(corral.peso_minimo.toString()).convierte(corral.peso_minimo), styleRight);
        agregarValor(fila_tablas + 9, 2, new FormatoNumero(corral.peso_promedio.toString()).convierte(corral.peso_promedio), styleRight);

        String rango;
        for (int i = fila_tablas + 2; i <= 17; i++) {

            rango = "A" + i + ":B" + i;

            combinarRango(rango);
            this.bordes(rango, CellStyle.BORDER_THIN);
            this.bordes("C" + i + ":C" + i, CellStyle.BORDER_THIN);
        }

        this.bordes("A" + (fila_tablas + 1) + ":C" + (fila_tablas + 1), CellStyle.BORDER_MEDIUM);
        this.bordes("A" + (fila_tablas + 1) + ":C" + (fila_tablas + 10), CellStyle.BORDER_MEDIUM);

        /**
         * Tabla Resultados y Rendimientos
         */
        combinarRango("E" + (fila_tablas + 1) + ":H" + (fila_tablas + 1));
        relleno("E" + (fila_tablas + 1) + ":H" + (fila_tablas + 1), IndexedColors.DARK_RED.index, IndexedColors.WHITE.index);

        agregarValor(fila_tablas, 4, "DATOS Y RENDIMIENTOS", styleTituloTabla);
        agregarValor(fila_tablas + 1, 4, "GANANCIA DE PESO X PRECIO DE CARNE", styleEtiquetaTabla);
        agregarValor(fila_tablas + 2, 4, "COSTO TOTAL DE MEDICAMENTOS INGRESADOS", styleEtiquetaTabla);
        agregarValor(fila_tablas + 3, 4, "COSTO TOTAL DE ALIMENTO INGRESADO", styleEtiquetaTabla);
        agregarValor(fila_tablas + 4, 4, "UTILIDAD IDEAL SIN GASTOS DE OPERACIÓN", styleEtiquetaTabla);
        agregarValor(fila_tablas + 5, 4, "SUELDOS", styleEtiquetaTabla);
        agregarValor(fila_tablas + 6, 4, "GASTOS FIJOS", styleEtiquetaTabla);
        agregarValor(fila_tablas + 7, 4, "GASTOS VARIOS", styleEtiquetaTabla);
        agregarValor(fila_tablas + 8, 4, "UTILIDAD FINAL", styleEtiquetaTabla);
        //agregarValor(14, 3, "PESO PROMEDIO", styleEtiquetaTabla);

        agregarValor(fila_tablas + 1, 7, new FormatoNumero(corral.ganancia_precio_carne.toString()).convierte(corral.ganancia_precio_carne), styleRight);
        agregarValor(fila_tablas + 2, 7, new FormatoNumero(corral.total_costo_medicina.toString()).convierte(corral.total_costo_medicina), styleRight);
        agregarValor(fila_tablas + 3, 7, new FormatoNumero(corral.costo_alimento.toString()).convierte(corral.costo_alimento), styleRight);
        agregarValor(fila_tablas + 4, 7, new FormatoNumero(corral.utilidad_s_gastos.toString()).convierte(corral.utilidad_s_gastos), styleRight);

        for (int i = fila_tablas + 2; i <= 17; i++) {

            rango = "E" + i + ":G" + i;

            combinarRango(rango);
            this.bordes(rango, CellStyle.BORDER_THIN);
            this.bordes("H" + i + ":H" + i, CellStyle.BORDER_THIN);
        }

        this.bordes("E" + (fila_tablas + 1) + ":H" + (fila_tablas + 1), CellStyle.BORDER_MEDIUM);
        this.bordes("E" + (fila_tablas + 1) + ":H" + (fila_tablas + 10), CellStyle.BORDER_MEDIUM);

        agregarValor(fila_encabezado, 0, "ID Visual", styleCenter);
        agregarValor(fila_encabezado, 1, "ID Electronico", styleCenter);
        agregarValor(fila_encabezado, 2, "Proveedor", styleCenter);
        //agregarValor(fila_encabezado, 2, "Corral");
        agregarValor(fila_encabezado, 3, ""); // Espacio

        agregarValor(fila_encabezado, 4, "Compra", styleCenter);
        agregarValor(fila_encabezado, 5, "ID Siniiga", styleCenter);
        agregarValor(fila_encabezado, 6, "ID Campaña", styleCenter);

        agregarValor(fila_encabezado, 7, "Sexo", styleCenter);
        agregarValor(fila_encabezado, 8, "Ingreso", styleCenter);
        agregarValor(fila_encabezado, 9, "# Lote", styleCenter);
        agregarValor(fila_encabezado, 10, "Compra", styleCenter);

        agregarValor(fila_encabezado, 11, "Peso Actual (Kg)", styleCenter);
        agregarValor(fila_encabezado, 12, "Peso de Compra", styleCenter);

        tamañoColumna(0, 9);//A
        tamañoColumna(1, 15);//B
        tamañoColumna(2, 14);//C
        tamañoColumna(3, 1);// Espacio D
        tamañoColumna(4, 13);//E        
        tamañoColumna(5, 8);//F
        tamañoColumna(6, 10);//G
        tamañoColumna(7, 8);//H
        tamañoColumna(8, 8);//I
        tamañoColumna(9, 5);//J
        tamañoColumna(10, 7);//K
        tamañoColumna(11, 14);//L
        tamañoColumna(12, 10);//M

        relleno("A" + (fila_encabezado + 1) + ":N" + (fila_encabezado + 1), IndexedColors.DARK_RED.index, IndexedColors.WHITE.index);

        Integer fila_inicial = fila_encabezado + 1;
        Integer columna;

        for (int i = 0; i < this.t_tabla.getRowCount(); i++) {

            if (i % 2 > 0) {

                relleno("A" + (fila_encabezado + 2 + i) + ":N" + (fila_encabezado + 2 + i), IndexedColors.LIGHT_GREEN.index, IndexedColors.BLACK.index);
            }

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 0).toString(), styleCenter);
            agregarValor(fila_inicial + i, 3, "");
            //sheet.getRow(fila_inicial + i).getCell(0).setCellStyle(bordes(cell.getCellStyle(), HSSFCellStyle.BORDER_THIN));
            for (int j = 1; j < 12; j++) {
                /// System.out.println(i + "," + j); 
                columna = j;

                if (j > 2) {
                    columna = j + 1;
                }

                agregarValor(fila_inicial + i, columna, t_tabla.getValueAt(i, j).toString(), styleCenter);
            }

            asignarEstilo(fila_inicial + i, 11, styleRight);
            asignarEstilo(fila_inicial + i, 12, styleRight);
        }

        agregarValor(fila_tablas, 8, "GRAFICA DE GANANCIA DE PESO", styleCenter);
        relleno("I" + (fila_tablas + 1) + ":M" + (fila_tablas + 1), IndexedColors.DARK_RED.index, IndexedColors.WHITE.index);
        bordes("I" + (fila_tablas + 1) + ":M" + (fila_tablas + 1), CellStyle.BORDER_MEDIUM);
        
        bordes("I" + (fila_tablas + 2) + ":M" + (fila_tablas + 10), CellStyle.BORDER_MEDIUM);

        /**
         * GRAFICA /*
         */
        combinarRango("I8:M8");

        graficar((short) 8, 8, (short) 13, 17);
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

        cargarLogo();

        combinarRango("A1:H1");
        combinarRango("A2:H2");
        combinarRango("A3:H4");

        agregarValor(0, 0, "REPORTE DE SALIDA", styleNameReport);

        agregarValor(1, 0, "FECHA DE REPORTE: " + formatoDateTime.format(new Date()), styleDateReport);

        if (this.fecha_ini != null) {

            agregarValor(2, 0, "Fecha de Salida de Animales: " + formatoDate.format(this.fecha_ini), styleDateReport);
        }

        agregarValor(5, 0, "Arete Visual");
        agregarValor(5, 1, "Arete Electronico");
        agregarValor(5, 2, "Arete Siniiga");
        agregarValor(5, 3, "Fecha de Movimiento");
        agregarValor(5, 4, "Clase de Movimiento");
        agregarValor(5, 5, "Numero de Pedido");
        agregarValor(5, 6, "Grupo de Origen");
        agregarValor(5, 7, "Peso (kg)");

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

            agregarValor(fila_inicial + i, 0, t_tabla.getValueAt(i, 0).toString());
            agregarValor(fila_inicial + i, 1, t_tabla.getValueAt(i, 1).toString());
            agregarValor(fila_inicial + i, 2, t_tabla.getValueAt(i, 2).toString());
            agregarValor(fila_inicial + i, 3, t_tabla.getValueAt(i, 3).toString());
            agregarValor(fila_inicial + i, 4, t_tabla.getValueAt(i, 4).toString());
            agregarValor(fila_inicial + i, 5, t_tabla.getValueAt(i, 5).toString());
            agregarValor(fila_inicial + i, 6, t_tabla.getValueAt(i, 6).toString());
            agregarValor(fila_inicial + i, 7, t_tabla.getValueAt(i, 7).toString());

            asignarEstilo(fila_inicial + i, 0, styleCenter);
            asignarEstilo(fila_inicial + i, 1, styleCenter);
            asignarEstilo(fila_inicial + i, 2, styleCenter);
            asignarEstilo(fila_inicial + i, 3, styleCenter);
            asignarEstilo(fila_inicial + i, 7, styleRight);
        }
    }
}
