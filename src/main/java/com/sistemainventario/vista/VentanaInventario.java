package com.sistemainventario.vista;

import com.sistemainventario.controlador.ControladorInventario;
import com.sistemainventario.modelo.Observador;
import com.sistemainventario.modelo.Producto;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class VentanaInventario extends javax.swing.JFrame implements Vista, Observador {

    private ControladorInventario controlador;
    private TablaProductos modeloTabla;

    public VentanaInventario(ControladorInventario controlador) {
        this.controlador = controlador;
        initComponents();
        this.modeloTabla = new TablaProductos();
        tblProductos.setModel(modeloTabla);
        tblProductos.setRowHeight(24);
        tblProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tblProductos.getSelectedRow();
                if (fila != -1) {
                    Producto p = modeloTabla.obtenerProducto(fila);
                    if (p != null) {
                        txtNombre.setText(p.getNombre());
                        txtCantidad.setText(String.valueOf(p.getCantidad()));
                        txtPrecio.setText(String.valueOf(p.getPrecio()));
                    }
                }
            }
        });
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnVenderActionPerformed(null);
                }
            }
        });
        txtPrecio.addActionListener(evt -> btnAgregarActionPerformed(evt));
        setLocationRelativeTo(null);
    }

    public VentanaInventario() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnVender = new javax.swing.JButton();
        btnTotal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        lblEstado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Inventario");

        lblNombre.setText("Nombre:");

        lblCantidad.setText("Cantidad:");

        lblPrecio.setText("Precio:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnVender.setText("Vender");
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });

        btnTotal.setText("Valor Total");
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tblProductos);

        lblEstado.setText("Listo.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblCantidad)
                            .addComponent(lblPrecio))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre)
                            .addComponent(txtCantidad)
                            .addComponent(txtPrecio)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVender, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(btnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecio)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnVender)
                    .addComponent(btnTotal))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        controlador.agregarProducto(txtNombre.getText(), txtCantidad.getText(), txtPrecio.getText(), this);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            int fila = tblProductos.getSelectedRow();
            if (fila != -1) {
                Producto p = modeloTabla.obtenerProducto(fila);
                if (p != null) {
                    nombre = p.getNombre();
                    txtNombre.setText(nombre);
                    txtCantidad.setText(String.valueOf(p.getCantidad()));
                    txtPrecio.setText(String.valueOf(p.getPrecio()));
                }
            } else {
                nombre = JOptionPane.showInputDialog(this, "Nombre del producto a vender:", "Vender", JOptionPane.QUESTION_MESSAGE);
                if (nombre == null || nombre.trim().isEmpty()) {
                    return;
                }
                nombre = nombre.trim();
                txtNombre.setText(nombre);
            }
        }

        String cantidad = JOptionPane.showInputDialog(this, "Cantidad a vender de " + nombre + ":", "1");
        if (cantidad != null && !cantidad.trim().isEmpty()) {
            controlador.venderProducto(nombre, cantidad, this);
        }
    }//GEN-LAST:event_btnVenderActionPerformed

    private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
        controlador.calcularValorTotal(this);
    }//GEN-LAST:event_btnTotalActionPerformed

    @Override
    public void mostrarProductos(List<Producto> productos) {
        modeloTabla.actualizar(productos);
        double total = 0.0;
        int unidades = 0;
        for (Producto p : productos) {
            total += p.getCantidad() * p.getPrecio();
            unidades += p.getCantidad();
        }
        lblEstado.setText("Productos: " + productos.size() + " | Unidades en stock: " + unidades + " | Valor total: " + Formato.moneda(total));
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        if (mensaje.equals("Agregado.")) {
            txtNombre.setText("");
            txtCantidad.setText("");
            txtPrecio.setText("");
        }
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void mostrarValorTotal(double total) {
        lblEstado.setText("Valor total del inventario: " + Formato.moneda(total));
        String mensaje = "Valor total del inventario en existencia:\n"
                + "  " + Formato.moneda(total) + "\n\n"
                + "Productos registrados en tabla: " + modeloTabla.getRowCount();
        JOptionPane.showMessageDialog(this, mensaje, "Valor Total del Inventario", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void inventarioCambio() {
        SwingUtilities.invokeLater(() -> controlador.cargarProductos(this));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnTotal;
    private javax.swing.JButton btnVender;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
