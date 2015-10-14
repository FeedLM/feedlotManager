/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static gui.Desktop.manejadorBD;
import static gui.Splash.formatoDateTime;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
public class Recepcion {

    public String id_recepcion;
    public Proveedor proveedor;
    public Estado origen;
    public String folio;
    public Date fecha_compra;
    public Date fecha_recepcion;
    public Integer animales;
    public Double peso_origen;
    public Double limite_merma;
    public Double merma;
    public Double porcentaje_merma;
    public Double peso_recepcion;
    public String numero_lote;
    public Double costo_flete;
    public Integer devoluciones;
    public String causa_devolucion;
    public Double total_alimento;

    public Recepcion() {
        id_recepcion = "";
        proveedor = new Proveedor();
        origen = new Estado();
        folio = "";
        fecha_compra = null;
        fecha_recepcion = null;
        animales = 0;
        peso_origen = 0.0;
        limite_merma = 0.0;
        merma = 0.0;
        porcentaje_merma = 0.0;
        peso_recepcion = 0.0;
        numero_lote = "";
        costo_flete = 0.0;
        devoluciones = 0;
        causa_devolucion = "";
        total_alimento = 0.0;
    }

    public void cargarPorId(String id_recepcion) {
        manejadorBD.consulta(""
                + "SELECT \n"
                + "    id_recepcion,\n"
                + "    id_proveedor,\n"
                + "    id_origen,\n"
                + "    folio,\n"
                + "    fecha_compra,\n"
                + "    fecha_recepcion,\n"
                + "    animales,\n"
                + "    peso_origen,\n"
                + "    limite_merma,\n"
                + "    merma,\n"
                + "    porcentaje_merma,\n"
                + "    peso_recepcion,\n"
                + "    numero_lote,\n"
                + "    costo_flete,\n"
                + "    devoluciones,\n"
                + "    causa_devolucion,\n"
                + "    total_alimento\n"
                + "FROM\n"
                + "    recepcion"
                + "WHERE id_recepcion = '" + id_recepcion + "';");
        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        } else {
            this.id_recepcion = "";
        }
    }

    public void cargarPorLote(String lote) {
        manejadorBD.consulta(""
                + "SELECT \n"
                + "    id_recepcion,\n"
                + "    id_proveedor,\n"
                + "    id_origen,\n"
                + "    folio,\n"
                + "    fecha_compra,\n"
                + "    fecha_recepcion,\n"
                + "    animales,\n"
                + "    peso_origen,\n"
                + "    limite_merma,\n"
                + "    merma,\n"
                + "    porcentaje_merma,\n"
                + "    peso_recepcion,\n"
                + "    numero_lote,\n"
                + "    costo_flete,\n"
                + "    devoluciones,\n"
                + "    causa_devolucion,\n"
                + "    total_alimento\n"
                + "FROM\n"
                + "    recepcion"
                + "WHERE numero_lote = '" + lote + "';");
        if (manejadorBD.getRowCount() > 0) {
            asignarValores();
        } else {
            this.numero_lote = "";
        }
    }

    private void asignarValores() {
        id_recepcion = manejadorBD.getValorString(0, 0);
        proveedor.cargarPorId(manejadorBD.getValorString(0, 1));
        origen.cargarPorId(manejadorBD.getValorString(0, 2));
        folio = manejadorBD.getValorString(0, 3);
        try {
            fecha_compra = formatoDateTime.parse(manejadorBD.getValorString(0, 4));
        } catch (ParseException ex) {
            Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fecha_recepcion = formatoDateTime.parse(manejadorBD.getValorString(0, 5));
        } catch (ParseException ex) {
            Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        animales = manejadorBD.getValorInt(0, 6);
        peso_origen = manejadorBD.getValorDouble(0, 7);
        limite_merma = manejadorBD.getValorDouble(0, 8);
        merma = manejadorBD.getValorDouble(0, 9);
        porcentaje_merma = manejadorBD.getValorDouble(0, 10);
        peso_recepcion = manejadorBD.getValorDouble(0, 11);
        numero_lote = manejadorBD.getValorString(0, 12);
        costo_flete = manejadorBD.getValorDouble(0, 13);
        devoluciones = manejadorBD.getValorInt(0, 14);
        causa_devolucion = manejadorBD.getValorString(0, 15);
        total_alimento = manejadorBD.getValorDouble(0, 16);
    }

    static ArrayList cargarLotes() {

        ArrayList array = new ArrayList();

        array.add("");
        manejadorBD.consulta(""
                + "SELECT \n"
                + "    numero_lote\n"
                + "FROM\n"
                + "    recepcion\n"
                + "ORDER BY numero_lote;");

        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            array.add(manejadorBD.getValueAt(i, 0).toString());
        }
        return array;
    }
}
