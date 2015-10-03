/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static domain.Movimiento.crearTablaEntrada;
import static gui.Desktop.manejadorBD;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Developer GAGS
 */
public class CorralAnimal {

    public static Table cargarAnimalesCorral_(String id_corral) {

        Table tabla = crearTablaAnimalesCorral();

        manejadorBD.consulta(""
                + "SELECT a.arete_visual,                a.arete_electronico,\n"
                + "       COALESCE(p.descripcion,''),\n"
                + "       COALESCE(DATE_FORMAT(a.fecha_ingreso, '%d/%m/%Y'), '00/00/0000'), \n"
                + "       a.arete_siniiga,\n"
                + "       a.arete_campaña,               COALESCE(s.descripcion,''),\n"
                //+ "       cast(a.fecha_compra as Date), "
                +"        COALESCE(DATE_FORMAT(fecha_compra, '%d/%m/%Y'), '00/00/0000'), \n"
                + "       a.numero_lote,\n"
                + "	  a.compra,                      round(a.peso_actual,2),\n"
                + "       round(a.temperatura,2)\n"
                + "FROM   animal a LEFT OUTER JOIN  proveedor p ON a.id_proveedor    =   p.id_proveedor\n"
                + "                LEFT OUTER JOIN sexo s ON a.id_sexo = s.id_sexo,       corral c, corral_animal ca  \n"
                + "WHERE  a.id_animal    = ca.id_animal  \n"
                + "AND    c.id_corral    = ca.id_corral  \n"
                + "AND    c.id_corral    = '" + id_corral + "' "
                + "AND      a.status = 'A'");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        return tabla;
    }
    
    public static Table crearTablaAnimalesCorral() {

        Table t_Salidas;

        t_Salidas = new Table();

        String titulos[] = {
            "Arete Visual", "Arete Electronico", 
            "Proveedor", "Fecha de Ingreso", "Arete Siniiga",
            "Arete Campaña", "Sexo", "Fecha de Compra",
            "Numero de Lote", "Compra", "Peso Actual (kg)",
            "Temperatura"};

        t_Salidas.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos
        ));

        t_Salidas.setTitulos(titulos);
        t_Salidas.cambiarTitulos();
        t_Salidas.setFormato(new int[]{
            Table.letra, Table.letra,
            Table.letra, Table.fecha, Table.letra,
            Table.letra, Table.letra, Table.fecha,
            Table.letra, Table.letra, Table.numero_double,
            Table.numero_double});

        return t_Salidas;
    }

    public static ArrayList cargarAnimalesCorral(String corral) {

        ArrayList array = new ArrayList();

        //Cambio por arete_visual
        manejadorBD.consulta(""
                //+ "SELECT   corral_animal.id_animal "
                + "SELECT   animal.arete_visual "
                + "FROM     corral_animal, corral, animal "
                + "WHERE    corral_animal.id_corral = corral.id_corral "
                + "AND      corral_animal.id_animal = animal.id_animal "
                + "AND      corral.nombre           = '" + corral + "' "
                + "AND      animal.status           = 'A'");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }

        return array;
    }

    public static Integer id_corralAnimal(Integer id_animal) {

        manejadorBD.consulta(""
                + "SELECT   id_corral "
                + "FROM     corral_animal "
                + "WHERE    corral_animal.id_animal =  " + id_animal);

        if (manejadorBD.getRowCount() > 0) {

            return manejadorBD.getValorInt(0, 0);

        }
        return 0;
    }

    public static String corralAnimal(Integer id_animal) {

        manejadorBD.consulta(""
                + "SELECT   corral.nombre "
                + "FROM     corral_animal, corral "
                + "WHERE    corral_animal.id_corral   =   corral.id_corral "
                + "AND      corral_animal.id_animal =  " + id_animal);

        if (manejadorBD.getRowCount() > 0) {

            return manejadorBD.getValorString(0, 0);

        }
        return "";
    }

}
