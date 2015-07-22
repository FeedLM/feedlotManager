/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import static gui.Splash.formatoDate;
import static gui.Splash.formatoDateTime;
import static gui.Desktop.rancho;
import static gui.Desktop.manejadorBD;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Cria {

    // public Integer id_rancho;
    public Animal madre;
    public String id_cria;
    public String arete;
    public Color sexo;//SEXO
    public Date fecha_nacimiento;
    public Raza raza;
    public String   status;

    public Cria() {

        madre = new Animal();
        id_cria = "";
        arete = "";
        sexo = new Color();
        fecha_nacimiento = new Date();
        raza = new Raza();
    }

    public void cargarPorIdCria(Integer AidCria) {

        manejadorBD.consulta(""
                + "SELECT   id_madre,           id_cria, \n"
                + "         arete,              id_sexo,\n"
                + "         fecha_nacimiento,   id_raza,\n"
                + "         status \n"
                + "FROM     cria \n"
                + "WHERE    cria.id_rancho = '" + rancho.id_rancho + "' \n"
                + "AND      cria.id_cria = '" + AidCria+ "'");

        if (manejadorBD.getRowCount() > 0) {

            asignarValores();
        }
    }

    public void asignarValores() {

        Integer id_color;
        String id_raza;
        String id_madre;

        id_madre = manejadorBD.getValorString(0, 0);
        id_cria = manejadorBD.getValorString(0, 1);
        arete = manejadorBD.getValorString(0, 2);
        id_color = manejadorBD.getValorInt(0, 3);

        try {

            fecha_nacimiento = formatoDateTime.parse(manejadorBD.getValorString(0, 4));
        } catch (ParseException ex) {

            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }

        id_raza = manejadorBD.getValorString(0, 5);
        status  = manejadorBD.getValorString(0, 6);

        madre.cargarPorId(id_madre);
        sexo.cargarPorId(id_color);
        raza.cargarPorId(id_raza);
    }

    public static Table leerCrias(Table tabla, Animal madre) {

        tabla = crearTablaCrias(tabla);

        manejadorBD.consulta(""
                + "SELECT   id_madre, id_cria, arete, fecha_nacimiento,\n"
                + "         color_arete.descripcion sexo, raza.descripcion raza\n"
                + "FROM     cria, color_arete, raza\n"
                + "WHERE    cria.id_sexo    = color_arete.id_color\n"
                + "AND      cria.id_raza    = raza.id_raza \n"
                + "AND      cria.id_rancho  = '" + rancho.id_rancho + "' \n"
                + "AND      cria.id_madre   = '" + madre.id_animal+ "' \n"
                + "AND      cria.status     =   'A'");

        if (manejadorBD.getRowCount() > 0) {

            manejadorBD.asignarTable(tabla);
        }

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tabla.ocultarcolumna(0);
        tabla.ocultarcolumna(1);

        return tabla;
    }

    private static Table crearTablaCrias(Table tabla) {

        if (tabla == null) {

            tabla = new Table();
        }

        String titulos[] = {"Id Madre", "Id Cria", "arete", "Sexo",
            "Fecha de Nacimiento", "Raza"};

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                titulos));

        tabla.setTitulos(titulos);
        tabla.cambiarTitulos();
        tabla.setFormato(new int[]{tabla.numero_entero, tabla.numero_entero,
            tabla.letra, tabla.letra, tabla.fecha, tabla.letra});

        int[] tamaños = new int[6];
        tamaños[0] = 80;//id Madre
        tamaños[1] = 100;//id_cria
        tamaños[2] = 80;//arete
        tamaños[3] = 120;//sexo
        tamaños[4] = 140;//fecha_de_Nacimiento
        tamaños[5] = 120;//Raza

        tabla.tamañoColumna(tamaños);

        return tabla;
    }

    public boolean grabar() {

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(madre.id_animal, "varIdMadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete, "varArete", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(sexo.id_color.toString(), "varIdSexo", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varIdRaza", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call agregarCria(?,?,?,?,?,?) }") == 0) {

            return true;
        } else {
            
            return false;
        }
    }
    
    public boolean actualizar() {

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(madre.id_animal, "varIdMadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_cria, "varIdCria", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete, "varArete", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(sexo.id_color.toString(), "varIdSexo", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(raza.id_raza, "varIdRaza", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call actualizarCria(?,?,?,?,?,?,?) }") == 0) {

            return true;
        } else {
            
            return false;
        }
    }

    public boolean eliminar() {

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(rancho.id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_cria, "varIdCria", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call eliminarCria(?,?) }") == 0) {

            return true;
        } else {

            return false;
        }
    }

}
