package Swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaCarrito(Carrito carrito) {

        setTitle("Carrito de Compras");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

     
        JLabel lblTitulo = new JLabel("Carrito de Compras", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
     
        lblTitulo.setBorder(BorderStyle(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

      
        DefaultTableModel modeloTabla = new DefaultTableModel(
                new Object[]{"Producto", "Precio (€)"}, 0
        );

        // Cargar productos del carrito
        for (ItemCarrito item : carrito.getItems()) {
            modeloTabla.addRow(new Object[]{
                    item.getProducto().getNombre(),
                    item.getSubtotal()   // precio total según cantidad
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
                SwingConstants.RIGHT
        );
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));

        panelInferior.add(lblTotal, BorderLayout.NORTH);

        // ---------------- BOTONES ----------------
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));

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
   
        btnVolver.addActionListener(e -> {dispose();
        		
        		new VentanaPrincipal().setVisible(true);
        		
    });

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Utilidad para bordes
    private static Border BorderStyle(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
}
