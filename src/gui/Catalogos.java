/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static gui.Desktop.rancho;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author angelesygil
 */
public class Catalogos extends javax.swing.JInternalFrame {

    /**
     * Creates new form Catalogos
     */
    public Catalogos(Desktop aParent) {

        initComponents();
        parent = aParent;

        setClosable(true);

        this.pack();
        /*
         Image i = null;
         i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
         setFrameIcon((Icon) i);
         */
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/resources/logo tru-test.png")));

        /*
         URL url = new URL(getClass().getResource("/resources/logo tru-test.png"));
         ImageIcon icon = new ImageIcon(url);
         setFrameIcon(icon);
         */
        this.setTitle(this.getTitle() + " " + rancho.descripcion);
        //   setLocationRelativeTo(null);
        jPanel2.setLocation((jPanel1.getWidth() / 2) - (jPanel2.getWidth() / 2), (jPanel1.getHeight() / 2) - (jPanel2.getHeight() / 2));
        fondo1.cargar(this.getSize());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiqueta1 = new abstractt.Etiqueta();
        jPanel2 = new javax.swing.JPanel();
        btn_Cliente = new abstractt.Boton();
        btn_Proveedor = new abstractt.Boton();
        btn_Razas = new abstractt.Boton();
        btn_MedicamentosTratamientos = new abstractt.Boton();
        fondo1 = new abstractt.fondo();

        setTitle("Catalogos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiqueta1.setBackground(new java.awt.Color(95, 84, 88));
        etiqueta1.setForeground(new java.awt.Color(230, 225, 195));
        etiqueta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta1.setText("Catalógos");
        etiqueta1.setFont(new java.awt.Font("Trebuchet", 1, 36)); // NOI18N
        etiqueta1.setOpaque(true);
        jPanel1.add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 60));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(4, 1, 0, 20));

        btn_Cliente.setText("Cliente");
        btn_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClienteActionPerformed(evt);
            }
        });
        jPanel2.add(btn_Cliente);

        btn_Proveedor.setText("Proveedor");
        btn_Proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ProveedorActionPerformed(evt);
            }
        });
        jPanel2.add(btn_Proveedor);

        btn_Razas.setText("Razas");
        btn_Razas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RazasActionPerformed(evt);
            }
        });
        jPanel2.add(btn_Razas);

        btn_MedicamentosTratamientos.setText("Medicamentos / Tratamientos");
        btn_MedicamentosTratamientos.setPreferredSize(new java.awt.Dimension(200, 40));
        btn_MedicamentosTratamientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MedicamentosTratamientosActionPerformed(evt);
            }
        });
        jPanel2.add(btn_MedicamentosTratamientos);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 140, -1, -1));

        fondo1.setText("fondo1");
        jPanel1.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClienteActionPerformed
        if (catalogoCliente != null) {
            catalogoCliente.dispose();
        }
        catalogoCliente = new CatalogoCliente(parent);
        catalogoCliente.setVisible(true);
    }//GEN-LAST:event_btn_ClienteActionPerformed

    private void btn_ProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ProveedorActionPerformed
        if (catalogoProveedor != null) {
            catalogoProveedor.dispose();
        }
        catalogoProveedor = new CatalogoProveedor(parent);
        catalogoProveedor.setVisible(true);
    }//GEN-LAST:event_btn_ProveedorActionPerformed

    private void btn_RazasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RazasActionPerformed
        if (catalogoRaza != null) {
            catalogoRaza.dispose();
        }
        catalogoRaza = new CatalogoRaza(parent);
        catalogoRaza.setVisible(true);
    }//GEN-LAST:event_btn_RazasActionPerformed

    private void btn_MedicamentosTratamientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MedicamentosTratamientosActionPerformed
        if (administracionMedicamentos != null) {
            administracionMedicamentos.dispose();
        }
        administracionMedicamentos = new AdministracionMedicamentos(parent);
        administracionMedicamentos.setVisible(true);
    }//GEN-LAST:event_btn_MedicamentosTratamientosActionPerformed

    CatalogoCliente catalogoCliente;
    CatalogoProveedor catalogoProveedor;
    CatalogoRaza catalogoRaza;
    AdministracionMedicamentos administracionMedicamentos;
    Desktop parent;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abstractt.Boton btn_Cliente;
    private abstractt.Boton btn_MedicamentosTratamientos;
    private abstractt.Boton btn_Proveedor;
    private abstractt.Boton btn_Razas;
    private abstractt.Etiqueta etiqueta1;
    private abstractt.fondo fondo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
