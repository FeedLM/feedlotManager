/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Desktop.manejadorBD;
import static gui.Desktop.rancho;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Developer GAGS
 */
public class Medicina {

    public String id_medicina;
    public Integer codigo;
    public String nombre;
    public Double costo;
    public UnidadMedida unidadMedida;
    //public Integer id_unidad;
    public Double presentacion;
    public Double costo_unitario;
    public String status;

    public Medicina() {

        id_medicina = "";
        codigo = 0;
        nombre = "";
        costo = 0.00;
        unidadMedida = new UnidadMedida();
        presentacion = 0.00;
        costo_unitario = 0.00;
        status = "";
    }

    public void cargarPorId(String id_medicina) {

        manejadorBD.consulta(""
                + "SELECT   id_medicina,    codigo,         nombre, costo,  \n"
                + "         id_unidad,      presentacion,   costo_unitario, \n"
                + "         status                                          \n"
                + "FROM     medicina                                        \n"
                + "WHERE    medicina.id_medicina = '" + id_medicina + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void cargarPorNombre(String nombre) {

        manejadorBD.consulta(""
                + "SELECT   id_medicina,    codigo,         nombre, costo,  \n"
                + "         id_unidad,      presentacion,   costo_unitario, \n"
                + "         status                                          \n"
                + "FROM     medicina                                        \n"
                + "WHERE    medicina.nombre = '" + nombre + "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }
    
    public void asignarValores() {

        Date fecha = null;
        String id_unidad;

        id_medicina = manejadorBD.getValorString(0, 0);
        codigo = manejadorBD.getValorInt(0, 1);
        nombre = manejadorBD.getValorString(0, 2);
        costo = manejadorBD.getValorDouble(0, 3);
        id_unidad = (String) manejadorBD.getValorString(0, 4);
        presentacion    =   manejadorBD.getValorDouble(0, 5);
        costo_unitario  =   manejadorBD.getValorDouble(0, 6);
        status = manejadorBD.getValorString(0, 7);

        unidadMedida.cargarPorId(id_unidad);        
    }

    public static ArrayList cargarCodigoMedicinas() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT codigo "
                + "FROM medicina "
                + "WHERE status =   'S' "
                + "ORDER BY codigo");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarMedicinas() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT nombre "
                + "FROM medicina "
                + "WHERE status =   'S' "
                + "ORDER BY nombre");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }

    public static ArrayList cargarMedicinasAnimal(Integer id_animal) {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT nombre "
                + "FROM medicina "
                + "WHERE status =   'S' "
                + "ORDER BY nombre");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        
        return array;
    }

   public static void leerMedicinaAnimal(Table tabla, Animal animal) {
                
        crearTablaMedicinaAnimal(tabla);

        manejadorBD.consulta(""
                + "SELECT   ma.id_medicina_animal, m.codigo,                   m.nombre,\n"
                + "         um.descripcion,             cast(fecha as Date),\n"
                + "         round(m.costo_unitario,2),  round(ma.dosis,2),\n"
                + "         round(round(m.costo_unitario,2) * ma.dosis,2)  "
                + "FROM     medicina_animal ma, medicina m, unidades_de_medida um "
                + "WHERE    ma.id_medicina  =   m.id_medicina "
                + "AND      m.id_unidad     =   um.id_unidad "
                + "AND      ma.id_rancho    =   '" + rancho.id_rancho + "' "
                + "AND      ma.id_animal    =   '" + animal.id_animal+"' ");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        
        int[] tamaños = new int[8];
        tamaños[0] = 10;//id_medicina aniaml
        tamaños[1] = 80;//id Codigo
        tamaños[2] = 200;//nombre
        tamaños[3] = 50;//uniad de medida
        tamaños[4] = 80;//Fecha Salida
        tamaños[5] = 100; //costo unitario
        tamaños[6] = 100;//dosis
        tamaños[7] = 100;//importe

        tabla.tamañoColumna(tamaños);
        
        tabla.ocultarcolumna(0);
      //  tabla.ocultarcolumna(2);
    }

    public static void crearTablaMedicinaAnimal(Table tabla) {

        if (tabla == null) {
            tabla = new Table();
        }

        String titulos[] = {"id medicina animal","Codigo", "Medicinas", "U.M.", "Fecha", "Costo Unitario", "Dosis", "Importe"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{0, 4, 0, 0, 3, 4, 4, 4});

    }

    public static Integer MedicinaCodigo(String medicina) {

        manejadorBD.consulta(""
                + "SELECT codigo "
                + "FROM medicina "
                + "WHERE nombre =   =   '" + medicina + "'");

        if (manejadorBD.getRowCount() > 0) {
            return manejadorBD.getValorInt(0, 0);
        }
        return 0;
    }

    public static String CodigoMedicina(String codigo) {

        manejadorBD.consulta(""
                + "SELECT nombre "
                + "FROM medicina "
                + "WHERE codigo =   =   " + codigo);

        if (manejadorBD.getRowCount() > 0) {
            return manejadorBD.getValorString(0, 0);
        }
        return "";
    }

    public static boolean leerMedicinaCodigo(String codigo) {

        manejadorBD.consulta(""
                + "SELECT m.codigo, m.nombre, "
                + "       m.costo, u.descripcion "
                + "FROM medicina m,  unidades_de_medida u "
                + "WHERE m.id_unidad = u.id_unidad "
                + "AND m.codigo =   " + codigo);

        if (manejadorBD.getRowCount() > 0) {

            return true;
        }
        return false;
    }
}
