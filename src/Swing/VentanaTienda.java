package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import BD.BD;

public class VentanaTienda extends JFrame {

    private static final long serialVersionUID = 1L;
    private TablaProductosModelo modelo;

    public VentanaTienda() {

        Color colorFondo = new Color(245, 245, 245);       // gris claro 
        Color colorPrimario = new Color(30, 144, 255);     // azul deusto
        Color colorTexto = new Color(40, 40, 40);

        
    	Productos[] productos = BD.obtenerProductos();

        String[] header = {"TipoDeporte","Producto", "Precio", "Stock" };

      
        setBounds(400, 200, 800, 400);
        setTitle("DeustoClubSports");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(colorFondo);
        Productos[] ps= BD.obtenerProductos();
        
        modelo = new TablaProductosModelo(ps, header);
        JTable tablaProductos = new JTable(modelo);

     
        tablaProductos.setRowHeight(35);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Fuente guay, cambiable a gusto
        tablaProductos.setShowGrid(true);            
        tablaProductos.setGridColor(Color.BLACK);    

        
        JTableHeader headerTabla = tablaProductos.getTableHeader();
        headerTabla.setBackground(colorPrimario);
        headerTabla.setForeground(Color.WHITE);
        headerTabla.setOpaque(true);
        headerTabla.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Para centrar el contenido
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablaProductos.getColumnCount(); i++) {
            if (i == 0) {
                tablaProductos.getColumnModel().getColumn(i).setCellRenderer(new TipoDeporteRenderer());
            } else {
                tablaProductos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    
        JPanel tituloPanel = new JPanel();
        tituloPanel.setBackground(colorFondo);

        JLabel tituloLabel = new JLabel("Elige los productos por deporte: ");
        tituloLabel.setForeground(colorTexto);
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JComboBox<TipoDeporte> comboBox = new JComboBox<>(TipoDeporte.values());
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Listener
        comboBox.addActionListener(e -> {
            TipoDeporte seleccionado = (TipoDeporte) comboBox.getSelectedItem();

            if (seleccionado == TipoDeporte.TODOS) {
                modelo = new TablaProductosModelo(productos, header);
            } else {
                List<Productos> filtrados = new ArrayList<>();
                for (Productos p : productos) {
                    if (p.getDeporte() == seleccionado) {
                        filtrados.add(p);
                    }
                }
                modelo = new TablaProductosModelo(filtrados.toArray(new Productos[0]), header);
            }
            tablaProductos.setModel(modelo);
            tablaProductos.repaint();
        });

        tituloPanel.add(tituloLabel);
        tituloPanel.add(comboBox);
        
        
        
        JButton btnVolver = new JButton("Volver atrás");
        btnVolver.setFocusPainted(false);
        btnVolver.setBackground(colorPrimario);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(10),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Listener
        btnVolver.addActionListener(e -> {
            dispose();
            new VentanaPrincipal();
        });
        
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) { // doble click
                    int fila = tablaProductos.getSelectedRow();
                    if (fila != -1) {

                        // Recuperar el producto seleccionado desde el modelo
                        Productos producto = modelo.getProductoAt(fila);
                                             
                        
                        // Abrir ventana de compra
                        dispose();
                        new VentanaCompraProducto(VentanaTienda.this, producto).setVisible(true);
                    }
                }
            }
        });


        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelInferior.setBackground(colorFondo);
        panelInferior.add(btnVolver);

        this.add(tituloPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Código sacado de https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java	
    class RoundBorder extends AbstractBorder {
        
		private static final long serialVersionUID = 1L;
		private int radius;

        public RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
    
    
    
    class TipoDeporteRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            if (value instanceof TipoDeporte) {
                TipoDeporte deporte = (TipoDeporte) value;

                String ruta = "img/deportes/" + deporte.name().toLowerCase() + ".png";
                ImageIcon icon = cargarIcono(ruta, 30, 30);

                label.setIcon(icon);
                label.setHorizontalTextPosition(SwingConstants.RIGHT); // icono izquierda, texto derecha
                label.setIconTextGap(8); // separación icono-texto
            }

            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }
    private ImageIcon cargarIcono(String ruta, int w, int h) {
        try {
            ImageIcon original = new ImageIcon(ruta);
            if (original.getIconWidth() <= 0) return null;
            Image img = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) {
    
        new VentanaTienda();
    }
}


