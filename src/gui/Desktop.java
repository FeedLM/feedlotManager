/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ManejadorBD;
import domain.Rancho;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.io.IOException;
import javax.swing.JInternalFrame;

/**
 *
 * @author angelesygil
 */
public class Desktop extends javax.swing.JFrame {

    /**
     * Creates new form Desktop
     */
    public Desktop() {

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();

        cargarcomponentes();

        rancho = new Rancho();
        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);
    }

    public void cargarcomponentes() {

        fondo21.cargar(this.getSize());
        //  boton1.cargar();

        Dimension pantallaTamano = this.jDesktopPane1.getSize();

        logo1.cargar();

        logo1.setLocation(pantallaTamano.width - logo1.getWidth() - 50, 25);

        this.logoTruTest1.cargar();

        logoTruTest1.setLocation(pantallaTamano.width - logo1.getWidth() - logoTruTest1.getWidth() - 75, 40);

        jPanel2.setSize(pantallaTamano.width - 50, 200);

        jPanel2.setLocation(25, (pantallaTamano.height / 2) - (jPanel2.getHeight() / 2));

        jPanel1.setLocation((jPanel2.getWidth() / 2) - (jPanel1.getWidth() / 2), (jPanel2.getHeight() / 2) - (jPanel1.getHeight() / 2));

        this.plecaInferior1.cargar();

        plecaInferior1.setLocation((pantallaTamano.width / 2) - (plecaInferior1.getWidth() / 2), pantallaTamano.height - 75);

        this.setResizable(false);
    }

    public void cargarTitulo() {
        this.jLabel1.setText(rancho.descripcion);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        btn_ayuda = new abstractt.Boton();
        btn_administrar = new abstractt.Boton();
        plecaInferior1 = new abstractt.PlecaInferior();
        logoTruTest1 = new abstractt.LogoTruTest();
        logo1 = new abstractt.Logo();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btn_ReporteTraslados = new abstractt.Boton();
        btn_salidaGanado = new abstractt.Boton();
        btn_trasladoGanado = new abstractt.Boton();
        btn_administracionGrupos = new abstractt.Boton();
        btn_ingresoAnimales = new abstractt.Boton();
        btn_Muertes = new abstractt.Boton();
        btn_reporteSesiones = new abstractt.Boton();
        btn_catalogos = new abstractt.Boton();
        btn_Hospital = new abstractt.Boton();
        btn_configuracion = new abstractt.Boton();
        jLabel1 = new javax.swing.JLabel();
        plecaInferior2 = new abstractt.PlecaInferior();
        fondo21 = new abstractt.Fondo2();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBackground(new java.awt.Color(204, 204, 204));
        jDesktopPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jDesktopPane1ComponentResized(evt);
            }
        });

        btn_ayuda.setText("Recepción");
        btn_ayuda.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btn_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ayudaActionPerformed(evt);
            }
        });
        jDesktopPane1.add(btn_ayuda);
        btn_ayuda.setBounds(60, 120, 180, 30);

        btn_administrar.setText("Cambiar de Finca");
        btn_administrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_administrarActionPerformed(evt);
            }
        });
        jDesktopPane1.add(btn_administrar);
        btn_administrar.setBounds(60, 80, 180, 30);

        plecaInferior1.setText("plecaInferior1");
        jDesktopPane1.add(plecaInferior1);
        plecaInferior1.setBounds(10, 120, 930, 50);

        logoTruTest1.setText("logoTruTest1");
        jDesktopPane1.add(logoTruTest1);
        logoTruTest1.setBounds(290, 20, 130, 60);

        logo1.setText("logo1");
        jDesktopPane1.add(logo1);
        logo1.setBounds(530, 10, 220, 110);

        jPanel2.setBackground(new java.awt.Color(238, 217, 170));
        jPanel2.setForeground(new java.awt.Color(238, 217, 170));
        jPanel2.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(238, 217, 170));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btn_ReporteTraslados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reporte de traslados ICONO.png"))); // NOI18N
        btn_ReporteTraslados.setToolTipText("Reporte de Traslados");
        btn_ReporteTraslados.setOpaque(false);
        btn_ReporteTraslados.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_ReporteTraslados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReporteTrasladosActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ReporteTraslados);

        btn_salidaGanado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/salida de ganado ICONO.png"))); // NOI18N
        btn_salidaGanado.setToolTipText("Salida de Ganado");
        btn_salidaGanado.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_salidaGanado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salidaGanadoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_salidaGanado);

        btn_trasladoGanado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/traslado de ganado ICONO.png"))); // NOI18N
        btn_trasladoGanado.setToolTipText("Traslado de Ganado");
        btn_trasladoGanado.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_trasladoGanado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_trasladoGanadoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_trasladoGanado);

        btn_administracionGrupos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/finca  ICONO.png"))); // NOI18N
        btn_administracionGrupos.setToolTipText("Administración de potreros");
        btn_administracionGrupos.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_administracionGrupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_administracionGruposActionPerformed(evt);
            }
        });
        jPanel1.add(btn_administracionGrupos);

        btn_ingresoAnimales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ingreso animal ICONO.png"))); // NOI18N
        btn_ingresoAnimales.setToolTipText("Ingreso de Animal");
        btn_ingresoAnimales.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_ingresoAnimales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresoAnimalesActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ingresoAnimales);

        btn_Muertes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/muerte animal ICONO.png"))); // NOI18N
        btn_Muertes.setToolTipText("Muertes de Animales");
        btn_Muertes.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_Muertes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MuertesActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Muertes);

        btn_reporteSesiones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reporte de seciones ICONO.png"))); // NOI18N
        btn_reporteSesiones.setToolTipText("Reportes de seciones");
        btn_reporteSesiones.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_reporteSesiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporteSesionesActionPerformed(evt);
            }
        });
        jPanel1.add(btn_reporteSesiones);

        btn_catalogos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icono CATALOGO.png"))); // NOI18N
        btn_catalogos.setToolTipText("Catalogos");
        btn_catalogos.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_catalogos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_catalogosActionPerformed(evt);
            }
        });
        jPanel1.add(btn_catalogos);

        btn_Hospital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/hospitalICONO.png"))); // NOI18N
        btn_Hospital.setToolTipText("Hospital");
        btn_Hospital.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_Hospital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HospitalActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Hospital);

        btn_configuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/configuración  ICONO.png"))); // NOI18N
        btn_configuracion.setToolTipText("Configuración");
        btn_configuracion.setPreferredSize(new java.awt.Dimension(90, 90));
        btn_configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configuracionActionPerformed(evt);
            }
        });
        jPanel1.add(btn_configuracion);

        jPanel2.add(jPanel1);
        jPanel1.setBounds(20, 10, 1000, 110);

        jDesktopPane1.add(jPanel2);
        jPanel2.setBounds(10, 170, 1050, 160);

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(95, 84, 88));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");
        jDesktopPane1.add(jLabel1);
        jLabel1.setBounds(16, 34, 270, 42);

        plecaInferior2.setText("plecaInferior2");
        jDesktopPane1.add(plecaInferior2);
        plecaInferior2.setBounds(10, 300, 1050, 50);

        fondo21.setText("fondo21");
        jDesktopPane1.add(fondo21);
        fondo21.setBounds(0, 0, 107, 69);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1111, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jDesktopPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDesktopPane1ComponentResized

        // System.out.println("resized");
        cargarcomponentes();
    }//GEN-LAST:event_jDesktopPane1ComponentResized

    private void btn_configuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configuracionActionPerformed

        if (administracionConfiguracion == null) {
            administracionConfiguracion = new AdministracionConfiguracion(this);
        }
        if (estacerrado(administracionConfiguracion)) {
            this.jDesktopPane1.add(administracionConfiguracion);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = administracionConfiguracion.getSize();
            administracionConfiguracion.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            administracionConfiguracion.moveToFront();
            administracionConfiguracion.show();
        }
    }//GEN-LAST:event_btn_configuracionActionPerformed

    private void btn_trasladoGanadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_trasladoGanadoActionPerformed
        if (traslados == null) {
            traslados = new Traslados(this);
        }

        if (estacerrado(traslados)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(traslados);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = traslados.getSize();
            traslados.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            traslados.moveToFront();
            traslados.show();
        }
    }//GEN-LAST:event_btn_trasladoGanadoActionPerformed

    private void btn_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ayudaActionPerformed
//        try {
//            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Ayuda.pdf");
//        } catch (IOException e) {
//            //e.printStackTrace();
//            System.out.println("Sin ayuda existente...");
//        }
        Captura_Recepcion captura_recepcion = new Captura_Recepcion();
        captura_recepcion.setVisible(true);
    }//GEN-LAST:event_btn_ayudaActionPerformed

    public boolean estacerrado(Object obj) {

        JInternalFrame[] activos = jDesktopPane1.getAllFrames();
        boolean cerrado = true;
        int i = 0;
        while (i < activos.length && cerrado) {
            if (activos[i] == obj) {
                cerrado = false;
            }
            i++;
        }
        return cerrado;
    }


    private void btn_catalogosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_catalogosActionPerformed

        if (catalogos == null) {
            catalogos = new Catalogos(this);
        }

        if (estacerrado(catalogos)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(catalogos);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = catalogos.getSize();
            catalogos.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            catalogos.moveToFront();
            catalogos.show();
        }
    }//GEN-LAST:event_btn_catalogosActionPerformed

    private void btn_administracionGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_administracionGruposActionPerformed
//        if (administracionGrupos == null) {
//            administracionGrupos = new AdministracionGrupos(this);
//        }
//
//        if (estacerrado(administracionGrupos)) {
//            this.jDesktopPane1.add(administracionGrupos);
//            Dimension desktopSize = jDesktopPane1.getSize();
//            Dimension FrameSize = administracionGrupos.getSize();
//            administracionGrupos.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
//            administracionGrupos.moveToFront();
//            administracionGrupos.show();
//    }
        administracionGrupos = new AdministracionGrupos(this);
        administracionGrupos.setVisible(true);
    }//GEN-LAST:event_btn_administracionGruposActionPerformed

    private void btn_HospitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HospitalActionPerformed
        if (visualizacionHospital == null) {
            visualizacionHospital = new VisualizacionHospital(this);
        }

        if (estacerrado(visualizacionHospital)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(visualizacionHospital);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = visualizacionHospital.getSize();
            visualizacionHospital.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            visualizacionHospital.moveToFront();
            visualizacionHospital.show();
        }
    }//GEN-LAST:event_btn_HospitalActionPerformed
    
    private void btn_ReporteTrasladosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReporteTrasladosActionPerformed
        if (trasladoGanado == null) {
            trasladoGanado = new RegistroTrasladoGanado(this);
        }

        if (estacerrado(trasladoGanado)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(trasladoGanado);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = trasladoGanado.getSize();
            trasladoGanado.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            trasladoGanado.moveToFront();
            trasladoGanado.show();
        }
    }//GEN-LAST:event_btn_ReporteTrasladosActionPerformed

    private void btn_salidaGanadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salidaGanadoActionPerformed
        if (salidaGanadoGrupo == null) {
            salidaGanadoGrupo = new SalidaGanadoGrupo(this);
        }

        if (estacerrado(salidaGanadoGrupo)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(salidaGanadoGrupo);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = salidaGanadoGrupo.getSize();
            salidaGanadoGrupo.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            salidaGanadoGrupo.moveToFront();
            salidaGanadoGrupo.show();
        }
    }//GEN-LAST:event_btn_salidaGanadoActionPerformed

    private void btn_MuertesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MuertesActionPerformed
        if (visualizacionMuertes == null) {
            visualizacionMuertes = new VisualizacionMuertes(this);
        }

        if (estacerrado(visualizacionMuertes)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(visualizacionMuertes);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = visualizacionMuertes.getSize();
            visualizacionMuertes.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            visualizacionMuertes.moveToFront();
            visualizacionMuertes.show();
        }
    }//GEN-LAST:event_btn_MuertesActionPerformed

    private void btn_reporteSesionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteSesionesActionPerformed
        if (reporteSesiones == null) {
        reporteSesiones = new ReporteSesiones(this);
        }

        if (estacerrado(reporteSesiones)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(reporteSesiones);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = reporteSesiones.getSize();
            reporteSesiones.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            reporteSesiones.moveToFront();
            reporteSesiones.show();
        }
    }//GEN-LAST:event_btn_reporteSesionesActionPerformed

    private void btn_administrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_administrarActionPerformed
        seleccionarRancho = new SeleccionarRancho();
        this.setVisible(false);
        seleccionarRancho.setVisible(true);
    }//GEN-LAST:event_btn_administrarActionPerformed

    private void btn_ingresoAnimalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresoAnimalesActionPerformed
        if (administracionAnimales == null) {
            administracionAnimales = new AdministracionAnimales(this);
        }

        if (estacerrado(administracionAnimales)) {
            // catalogos.setVisible(true);
            this.jDesktopPane1.add(administracionAnimales);
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = administracionAnimales.getSize();
            administracionAnimales.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            administracionAnimales.moveToFront();
            administracionAnimales.show();
        }        
    }//GEN-LAST:event_btn_ingresoAnimalesActionPerformed

    public ManejadorBD getManejador() {
        return manejadorBD;
    }

    public void setManejador(ManejadorBD aManejadorBD) {
        manejadorBD = aManejadorBD;
    }

    public static ManejadorBD manejadorBD = null;
    public static Rancho rancho;

    public AdministracionAnimales administracionAnimales;
    public static BusquedaAnimalSelector busquedaAnimalSelector;
    public Sementales sementales;
    public PartosAbortos partos;
    public AdministracionGrupos administracionGrupos;
    public MedicinasAnimalGrupo medicinasAnimalGrupo;
    public SalidaGanadoGrupo salidaGanadoGrupo;
    public SalidaGanado salidaGanado;
    public Traslados traslados;
    public BajasMuerte bajasMuerte;
    public BusquedaAnimal busquedaAnimal;
    public CargarArchivoSesion cargarArchivoSesion;
    public EspecificacionesAnimal especificacionesAnimal;
    //  public SeleccionarRancho    seleccionarRancho;
    public SeleccionarRancho seleccionarRancho;
    private Catalogos catalogos;
    private VisualizacionHospital visualizacionHospital;
    private VisualizacionMuertes visualizacionMuertes;
    private AdministracionConfiguracion administracionConfiguracion;
    private RegistroTrasladoGanado trasladoGanado;
    private ReporteSesiones reporteSesiones;
    private Compras compras;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton btn_Hospital;
    private abstractt.Boton btn_Muertes;
    private abstractt.Boton btn_ReporteTraslados;
    private abstractt.Boton btn_administracionGrupos;
    private abstractt.Boton btn_administrar;
    private abstractt.Boton btn_ayuda;
    private abstractt.Boton btn_catalogos;
    private abstractt.Boton btn_configuracion;
    private abstractt.Boton btn_ingresoAnimales;
    private abstractt.Boton btn_reporteSesiones;
    private abstractt.Boton btn_salidaGanado;
    private abstractt.Boton btn_trasladoGanado;
    private abstractt.Fondo2 fondo21;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private abstractt.Logo logo1;
    private abstractt.LogoTruTest logoTruTest1;
    private abstractt.PlecaInferior plecaInferior1;
    private abstractt.PlecaInferior plecaInferior2;
    // End of variables declaration//GEN-END:variables
}
