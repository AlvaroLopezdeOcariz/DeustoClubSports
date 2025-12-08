package ui.ventanas;

import dominio.*;
import BD.BD;
import hilos.HiloGeneral;
import ui.modelos.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import BD.BD;

import java.awt.*;
import java.time.LocalDate;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;
    private Carrito carrito;

    public VentanaCarrito(Carrito carrito) {
        this.carrito = carrito;
        setTitle("Carrito de Compras");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Carrito de Compras", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        lblTitulo.setBorder(BorderStyle(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        DefaultTableModel modeloTabla = new DefaultTableModel(
                new Object[] { "Producto", "Precio (€)" }, 0);

        // Cargar productos del carrito
        for (ItemCarrito item : carrito.getItems()) {
            modeloTabla.addRow(new Object[] {
                    item.getProducto().getNombre(),
                    item.getSubtotal() // precio total según cantidad
            });
        }
        if (carrito.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
        }

        JTable tablaCarrito = new JTable(modeloTabla);
        tablaCarrito.setRowHeight(30);
        tablaCarrito.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        tablaCarrito.setFont(new Font("Arial", Font.PLAIN, 14));

        add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);

        // ---------------- PANEL INFERIOR ----------------
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderStyle(10, 10, 10, 10));

        // Total del carrito
        JLabel lblTotal = new JLabel(
                "Total: €" + String.format("%.2f", carrito.calcularTotal()),
                SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));

        panelInferior.add(lblTotal, BorderLayout.NORTH);

        // ---------------- BOTONES ----------------
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));

        // Botón limpiar
        JButton btnLimpiar = new JButton("Limpiar Carrito");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));

        btnLimpiar.addActionListener(e -> {
            carrito.clear();
            dispose();
            new VentanaCarrito(carrito).setVisible(true);
            JOptionPane.showMessageDialog(this, "Carrito vacío.");

        });

        // Botón volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));

        btnVolver.addActionListener(e -> {
            dispose();

            new VentanaPrincipal().setVisible(true);

        });
        // Botón pagar
        JButton btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 14));

        btnPagar.addActionListener(e -> {
            if (carrito.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            } else {
                mostrarVentanaPago();
            }
        });

        panelBotones.add(btnPagar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void mostrarVentanaPago() {
        JDialog ventanaPago = new JDialog(this, "Pago", true);
        ventanaPago.setSize(400, 350);
        ventanaPago.setLocationRelativeTo(this);
        ventanaPago.setLayout(new BorderLayout());

        // Etiqueta de título
        JLabel lblTituloPago = new JLabel("Detalles de Pago", SwingConstants.CENTER);
        lblTituloPago.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloPago.setForeground(new Color(50, 50, 150));
        lblTituloPago.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ventanaPago.add(lblTituloPago, BorderLayout.NORTH);

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTarjeta = new JLabel("Número de Tarjeta:");
        JTextField txtTarjeta = new JTextField();
        JLabel lblFecha = new JLabel("Fecha de Caducidad (MM/AA):");
        JTextField txtFecha = new JTextField();
        JLabel lblCVV = new JLabel("CVV:");
        JTextField txtCVV = new JTextField();

        panelCampos.add(lblTarjeta);
        panelCampos.add(txtTarjeta);
        panelCampos.add(lblFecha);
        panelCampos.add(txtFecha);
        panelCampos.add(lblCVV);
        panelCampos.add(txtCVV);

        ventanaPago.add(panelCampos, BorderLayout.CENTER);

        // Panel inferior para el botón de confirmación
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTotalPago = new JLabel("Total a Pagar: €" + String.format("%.2f", carrito.calcularTotal()));
        lblTotalPago.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalPago.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPago.setForeground(new Color(50, 50, 150));

        JButton btnConfirmar = new JButton("Confirmar Pago");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(50, 200, 50));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.addActionListener(e -> { // Ayudado de IAG en este listener (ChatGPT)
            if (txtTarjeta.getText().isEmpty() || txtFecha.getText().isEmpty() || txtCVV.getText().isEmpty()) {
                JOptionPane.showMessageDialog(ventanaPago, "Por favor, complete todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ventanaPago, "Pago realizado con éxito. ¡Gracias!");

                for (ItemCarrito item : carrito.getItems()) {
                    String n = item.getProducto().getNombre();
                    int idProducto = BD.ObtenerIdProducto(n);
                    String fecha = LocalDate.now().toString();
                    BD.insertarRestock(idProducto, item.getCantidad(), fecha, item.getProducto().getNombre());
                }
                carrito.clear();
                ventanaPago.dispose();
                dispose();
                new VentanaPrincipal().setVisible(true);

            }
        });
        panelInferior.add(btnConfirmar, BorderLayout.SOUTH);
        panelInferior.add(lblTotalPago, BorderLayout.NORTH);

        ventanaPago.add(panelInferior, BorderLayout.SOUTH);

        ventanaPago.setVisible(true);
    }

    // Utilidad para bordes
    private static Border BorderStyle(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
}
