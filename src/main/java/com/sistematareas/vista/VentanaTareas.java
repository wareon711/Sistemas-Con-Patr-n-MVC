package com.sistematareas.vista;

import com.sistematareas.controlador.ControladorTareas;
import com.sistematareas.modelo.ObservadorTareas;
import com.sistematareas.modelo.Tarea;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class VentanaTareas extends javax.swing.JFrame implements VistaTareas, ObservadorTareas {

    private ControladorTareas controlador;
    private TablaTareas modeloTabla;

    public VentanaTareas(ControladorTareas controlador) {
        this.controlador = controlador;
        initComponents();
        this.modeloTabla = new TablaTareas();
        tblTareas.setModel(modeloTabla);
        tblTareas.setRowHeight(24);
        tblTareas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tblTareas.getSelectedRow();
                if (fila != -1) {
                    Tarea t = modeloTabla.obtenerTarea(fila);
                    if (t != null) {
                        txtTitulo.setText(t.getTitulo());
                        cbPrioridad.setSelectedItem(t.getPrioridad());
                    }
                }
            }
        });
        tblTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    completarSeleccionada();
                }
            }
        });
        txtTitulo.addActionListener(evt -> btnAgregarActionPerformed(evt));
        setLocationRelativeTo(null);
    }

    private void completarSeleccionada() {
        int fila = tblTareas.getSelectedRow();
        if (fila == -1) {
            return;
        }
        Tarea t = modeloTabla.obtenerTarea(fila);
        if (t != null && t.esPendiente()) {
            int r = JOptionPane.showConfirmDialog(
                    this,
                    "¿Marcar como completada la tarea '" + t.getTitulo() + "'?",
                    "Completar tarea",
                    JOptionPane.YES_NO_OPTION
            );
            if (r == JOptionPane.YES_OPTION) {
                controlador.completarTarea(t.getId(), this);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lblPrioridad = new javax.swing.JLabel();
        cbPrioridad = new javax.swing.JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        btnAgregar = new javax.swing.JButton();
        btnCompletar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTareas = new javax.swing.JTable();
        lblEstado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestor de Tareas");

        lblTitulo.setText("Titulo:");

        lblPrioridad.setText("Prioridad:");

        cbPrioridad.setSelectedItem("Media");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnCompletar.setText("Completar");
        btnCompletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompletarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tblTareas);

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
                            .addComponent(lblTitulo)
                            .addComponent(lblPrioridad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTitulo)
                            .addComponent(cbPrioridad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCompletar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrioridad)
                    .addComponent(cbPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnCompletar)
                    .addComponent(btnEliminar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        controlador.agregarTarea(txtTitulo.getText(), (String) cbPrioridad.getSelectedItem(), this);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCompletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompletarActionPerformed
        int fila = tblTareas.getSelectedRow();
        if (fila == -1) {
            mostrarError("Seleccione una tarea de la tabla para completarla.");
            return;
        }
        Tarea t = modeloTabla.obtenerTarea(fila);
        if (t != null) {
            controlador.completarTarea(t.getId(), this);
        }
    }//GEN-LAST:event_btnCompletarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblTareas.getSelectedRow();
        if (fila == -1) {
            mostrarError("Seleccione una tarea de la tabla para eliminarla.");
            return;
        }
        Tarea t = modeloTabla.obtenerTarea(fila);
        if (t != null) {
            int r = JOptionPane.showConfirmDialog(
                    this,
                    "¿Eliminar la tarea '" + t.getTitulo() + "'?",
                    "Eliminar",
                    JOptionPane.YES_NO_OPTION
            );
            if (r == JOptionPane.YES_OPTION) {
                controlador.eliminarTarea(t.getId(), this);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    @Override
    public void mostrarTareas(List<Tarea> tareas) {
        modeloTabla.actualizar(tareas);
        long pendientes = tareas.stream().filter(Tarea::esPendiente).count();
        lblEstado.setText("Tareas: " + tareas.size() + " | Pendientes: " + pendientes + " | Completadas: " + (tareas.size() - pendientes));
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        txtTitulo.setText("");
        cbPrioridad.setSelectedItem("Media");
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        txtTitulo.selectAll();
        txtTitulo.requestFocus();
    }

    @Override
    public void tareasCambiaron() {
        SwingUtilities.invokeLater(() -> controlador.actualizarVistas());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCompletar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JComboBox<String> cbPrioridad;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblPrioridad;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblTareas;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
