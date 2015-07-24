/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Animal;
import static domain.Animal.cargararete_visuals;
import static domain.Animal.cargararete_visualsMuertos;
import domain.Excel;
import static domain.Movimiento.cargarMuertes;
import static gui.Desktop.rancho;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;
import javax.swing.JFrame;

/**
 *
 * @author angelesygil
 */
public class VisualizacionMuertes extends javax.swing.JFrame {

    /**
     * Creates new form VisualizacionMuertes
     */
    public VisualizacionMuertes(Desktop parent) {
//        super(parent);
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null);
        Image i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
        setIconImage(i);
        tagIdSelector.addArray(cargararete_visualsMuertos());

        this.buttonGroup1.add(jrb_hoy);
        this.buttonGroup1.add(this.jrb_fecha);
        this.buttonGroup1.add(this.jrb_entre_fechas);
        this.buttonGroup1.add(this.jrb_tagId);

        tipo = 1;
        fecha_ini = new Date();
        fecha_fin = new Date();
        animal = new Animal();
        cargarTablaMuertes();

        this.setTitle(this.getTitle() + " " + rancho.descripcion);

        setResizable(false);

        fondo1.cargar(this.getSize());
    }

    private void cargarTablaMuertes() {

        cargarMuertes(table1, tipo, fecha_ini, fecha_fin, animal);

        btn_Reporte.setEnabled(false);

        if (table1.getRowCount() > 0) {
            btn_Reporte.setEnabled(true);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        etiqueta1 = new abstractt.Etiqueta();
        jrb_hoy = new javax.swing.JRadioButton();
        jrb_fecha = new javax.swing.JRadioButton();
        jrb_entre_fechas = new javax.swing.JRadioButton();
        jrb_tagId = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        calendar1 = new abstractt.Calendar();
        calendar2 = new abstractt.Calendar();
        calendar3 = new abstractt.Calendar();
        tagIdSelector = new abstractt.ComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        table1 = new abstractt.Table();
        btn_visualiza = new abstractt.Boton();
        btn_Reporte = new abstractt.Boton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        fondo1 = new abstractt.fondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Muertes");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Visualización de Muertes");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 48)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel1.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 80));

        jrb_hoy.setBackground(new java.awt.Color(255, 255, 255));
        jrb_hoy.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jrb_hoy.setForeground(new java.awt.Color(97, 84, 88));
        jrb_hoy.setSelected(true);
        jrb_hoy.setText("Hoy");
        jrb_hoy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jrb_hoy.setOpaque(false);
        jrb_hoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_hoyActionPerformed(evt);
            }
        });
        jPanel1.add(jrb_hoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 100, 20));

        jrb_fecha.setBackground(new java.awt.Color(255, 255, 255));
        jrb_fecha.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jrb_fecha.setForeground(new java.awt.Color(97, 84, 88));
        jrb_fecha.setText("Otra Fecha");
        jrb_fecha.setOpaque(false);
        jrb_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_fechaActionPerformed(evt);
            }
        });
        jPanel1.add(jrb_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 125, 100, 20));

        jrb_entre_fechas.setBackground(new java.awt.Color(255, 255, 255));
        jrb_entre_fechas.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jrb_entre_fechas.setForeground(new java.awt.Color(97, 84, 88));
        jrb_entre_fechas.setText("Entre Fechas");
        jrb_entre_fechas.setOpaque(false);
        jrb_entre_fechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_entre_fechasActionPerformed(evt);
            }
        });
        jPanel1.add(jrb_entre_fechas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 100, 20));

        jrb_tagId.setBackground(new java.awt.Color(255, 255, 255));
        jrb_tagId.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jrb_tagId.setForeground(new java.awt.Color(97, 84, 88));
        jrb_tagId.setText("Arete Visual");
        jrb_tagId.setOpaque(false);
        jrb_tagId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_tagIdActionPerformed(evt);
            }
        });
        jPanel1.add(jrb_tagId, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 175, 100, 20));

        jScrollPane2.setBorder(null);
        jScrollPane2.setOpaque(false);

        jTextPane1.setEditable(false);
        jTextPane1.setBorder(null);
        jTextPane1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jTextPane1.setText("Nota: Primero Visualizar la informacion y luego dar click en generar reporte.");
        jTextPane1.setOpaque(false);
        jScrollPane2.setViewportView(jTextPane1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 160, 310, -1));

        calendar1.setEnabled(false);
        calendar1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jPanel1.add(calendar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 125, 130, 20));

        calendar2.setEnabled(false);
        calendar2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jPanel1.add(calendar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 130, -1));

        calendar3.setEnabled(false);
        calendar3.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jPanel1.add(calendar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 130, 20));

        tagIdSelector.setEnabled(false);
        tagIdSelector.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tagIdSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagIdSelectorActionPerformed(evt);
            }
        });
        jPanel1.add(tagIdSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 175, 130, 20));

        table1.setForeground(new java.awt.Color(230, 225, 195));
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setResizable(false);
            table1.getColumnModel().getColumn(0).setPreferredWidth(100);
            table1.getColumnModel().getColumn(1).setResizable(false);
            table1.getColumnModel().getColumn(2).setResizable(false);
            table1.getColumnModel().getColumn(3).setResizable(false);
            table1.getColumnModel().getColumn(4).setResizable(false);
            table1.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 225, 800, 250));

        btn_visualiza.setText("Visualizar");
        btn_visualiza.setFont(new java.awt.Font("Trebuchet", 1, 12)); // NOI18N
        btn_visualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_visualizaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_visualiza, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 120, 30));

        btn_Reporte.setText("Reporte");
        btn_Reporte.setFont(new java.awt.Font("Trebuchet", 1, 12)); // NOI18N
        btn_Reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReporteActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Reporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, 120, 30));
        jPanel1.add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 475, 50, 25));

        fondo1.setText("fondo1");
        jPanel1.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarReporte() {

        Excel excel = new Excel();

        excel.reporteMuertes(table1, tipo, fecha_ini, fecha_fin, animal);

    }

    private void tagIdSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagIdSelectorActionPerformed

    }//GEN-LAST:event_tagIdSelectorActionPerformed

    private void jrb_hoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_hoyActionPerformed
        validarRadioButton();
    }//GEN-LAST:event_jrb_hoyActionPerformed

    private void jrb_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_fechaActionPerformed
        validarRadioButton();
    }//GEN-LAST:event_jrb_fechaActionPerformed

    private void jrb_entre_fechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_entre_fechasActionPerformed
        validarRadioButton();
    }//GEN-LAST:event_jrb_entre_fechasActionPerformed

    private void jrb_tagIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_tagIdActionPerformed
        validarRadioButton();
    }//GEN-LAST:event_jrb_tagIdActionPerformed

    private void btn_visualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_visualizaActionPerformed
        visualizarDatos();
    }//GEN-LAST:event_btn_visualizaActionPerformed

    private void btn_ReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReporteActionPerformed
        generarReporte();
    }//GEN-LAST:event_btn_ReporteActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void visualizarDatos() {

        // Integer tipo = 1;
        tipo = 1;
        fecha_ini = new Date();
        fecha_fin = new Date();
        animal = new Animal();

        if (this.jrb_fecha.isSelected()) {
            tipo = 1;
            fecha_ini = calendar1.getDate();
        }
        if (this.jrb_entre_fechas.isSelected()) {
            tipo = 2;
            fecha_ini = calendar2.getDate();
            fecha_fin = calendar3.getDate();
        }
        if (this.jrb_tagId.isSelected()) {
            tipo = 3;
            animal.cargarPorAreteVisual(this.tagIdSelector.getSelectedItem().toString(), "M");
        }

        cargarTablaMuertes();
    }

    private void validarRadioButton() {

        this.calendar1.setEnabled(false);
        this.calendar2.setEnabled(false);
        this.calendar3.setEnabled(false);
        this.tagIdSelector.setEnabled(false);

        if (this.jrb_fecha.isSelected()) {

            this.calendar1.setEnabled(true);
        }
        if (this.jrb_entre_fechas.isSelected()) {
            this.calendar2.setEnabled(true);
            this.calendar3.setEnabled(true);
        }
        if (this.jrb_tagId.isSelected()) {
            this.tagIdSelector.setEnabled(true);
        }

    }
    Desktop parent;
    private Integer tipo;
    private Date fecha_ini;
    private Date fecha_fin;
    private Animal animal;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton btn_Reporte;
    private abstractt.Boton btn_visualiza;
    private javax.swing.ButtonGroup buttonGroup1;
    private abstractt.Calendar calendar1;
    private abstractt.Calendar calendar2;
    private abstractt.Calendar calendar3;
    private abstractt.Etiqueta etiqueta1;
    private javax.swing.Box.Filler filler1;
    private abstractt.fondo fondo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JRadioButton jrb_entre_fechas;
    private javax.swing.JRadioButton jrb_fecha;
    private javax.swing.JRadioButton jrb_hoy;
    private javax.swing.JRadioButton jrb_tagId;
    private abstractt.Table table1;
    private abstractt.ComboBox tagIdSelector;
    // End of variables declaration//GEN-END:variables
}
