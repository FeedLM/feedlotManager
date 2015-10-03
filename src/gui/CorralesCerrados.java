package gui;

import domain.Corral;
import static gui.Desktop.rancho;

public class CorralesCerrados extends javax.swing.JFrame {

    public CorralesCerrados() {
//        this.parent = aparent;
        initComponents();
        setLocationRelativeTo(null);
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grafica1 = new domain.Grafica();
        grafica2 = new domain.Grafica();
        grafica3 = new domain.Grafica();
        jPanel1 = new javax.swing.JPanel();
        etiqueta2 = new abstractt.Etiqueta();
        jLabel17 = new javax.swing.JLabel();
        t_alimentoIngresado = new abstractt.TextField();
        t_totalKilos = new abstractt.TextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        t_gananciaKilos = new abstractt.TextField();
        jtf_numAnimales = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        t_pesoMinimo = new abstractt.TextField();
        t_pesoMaximo = new abstractt.TextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        t_pesoPromedio = new abstractt.TextField();
        jta_Observaciones = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        corralSelector = new abstractt.ComboBox();
        jLabel2 = new javax.swing.JLabel();
        panelGrafica = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta2.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta2.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta2.setText("Corrales Cerrados");
        etiqueta2.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta2.setOpaque(true);
        jPanel1.add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 60));

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(95, 84, 88));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Alimento Ingresado:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 120, 22));

        t_alimentoIngresado.setText("0.00");
        t_alimentoIngresado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_alimentoIngresadoFocusLost(evt);
            }
        });
        t_alimentoIngresado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_alimentoIngresadoActionPerformed(evt);
            }
        });
        jPanel1.add(t_alimentoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 100, 22));

        t_totalKilos.setText("0.00");
        t_totalKilos.setFocusable(false);
        jPanel1.add(t_totalKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 100, 22));

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(95, 84, 88));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Total Kilos:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 110, 22));

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(95, 84, 88));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Ganancia Kilos:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 110, 22));

        t_gananciaKilos.setText("0.00");
        t_gananciaKilos.setFocusable(false);
        jPanel1.add(t_gananciaKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 100, 22));

        jtf_numAnimales.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jtf_numAnimales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtf_numAnimales.setFocusable(false);
        jPanel1.add(jtf_numAnimales, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 100, -1));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 84, 88));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("No. de Animales:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 110, 22));

        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(95, 84, 88));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Peso Minimo:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 110, 22));

        t_pesoMinimo.setText("0.00");
        t_pesoMinimo.setFocusable(false);
        jPanel1.add(t_pesoMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 100, 22));

        t_pesoMaximo.setText("0.00");
        t_pesoMaximo.setFocusable(false);
        jPanel1.add(t_pesoMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 100, 22));

        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(95, 84, 88));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Peso Maximo:");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 110, 22));

        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(95, 84, 88));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Peso Promedio:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 110, 22));

        t_pesoPromedio.setText("0.00");
        t_pesoPromedio.setFocusable(false);
        jPanel1.add(t_pesoPromedio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 100, 22));

        jta_Observaciones.setColumns(20);
        jta_Observaciones.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jta_Observaciones.setLineWrap(true);
        jta_Observaciones.setRows(3);
        jta_Observaciones.setToolTipText("");
        jta_Observaciones.setWrapStyleWord(true);
        jta_Observaciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jta_Observaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 250, 70));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(95, 84, 88));
        jLabel6.setText("Observaciones:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 93, 22));

        corralSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corralSelectorActionPerformed(evt);
            }
        });
        jPanel1.add(corralSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 140, 22));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(95, 84, 88));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Corral:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 80, 22));

        panelGrafica.setBackground(new java.awt.Color(0, 102, 102));
        panelGrafica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(panelGrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 400, 220));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void t_alimentoIngresadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoFocusLost

//        alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoFocusLost

    private void t_alimentoIngresadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_alimentoIngresadoActionPerformed

//        corral.alimento_ingresado = this.t_alimentoIngresado.getDouble();
    }//GEN-LAST:event_t_alimentoIngresadoActionPerformed

    private void corralSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corralSelectorActionPerformed
        corral.cargarPorNombre(corralSelector.getText(), rancho);
        
    }//GEN-LAST:event_corralSelectorActionPerformed
    
    private void cargarDatos(){
        
    }
            
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CorralesCerrados().setVisible(true);
            }
        });
    }
    
    Corral corral;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.ComboBox corralSelector;
    private abstractt.Etiqueta etiqueta2;
    private domain.Grafica grafica1;
    private domain.Grafica grafica2;
    private domain.Grafica grafica3;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea jta_Observaciones;
    private javax.swing.JTextField jtf_numAnimales;
    private javax.swing.JPanel panelGrafica;
    private abstractt.TextField t_alimentoIngresado;
    private abstractt.TextField t_gananciaKilos;
    private abstractt.TextField t_pesoMaximo;
    private abstractt.TextField t_pesoMinimo;
    private abstractt.TextField t_pesoPromedio;
    private abstractt.TextField t_totalKilos;
    // End of variables declaration//GEN-END:variables
}
