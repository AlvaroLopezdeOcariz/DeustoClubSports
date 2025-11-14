package Swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class VentanaTienda extends JFrame {

    private static final long serialVersionUID = 1L;
    private TablaProductosModelo modelo;
    public VentanaTienda() {
    	
    Productos[] productos = {
			new Productos("Balón de fútbol", 29.99, TipoDeporte.FUTBOL),
			new Productos("Raqueta de tenis", 89.99, TipoDeporte.TENIS),
			new Productos("Camiseta de baloncesto", 49.99, TipoDeporte.BALONCESTO),
			new Productos("Zapatillas de running", 79.99, TipoDeporte.RUNNING),
			
			new Productos("Guantes de boxeo", 39.99, TipoDeporte.BOXEO),
			new Productos("Palo de hockey", 59.99, TipoDeporte.HOCKEY),
			new Productos("Red de voleibol", 24.99, TipoDeporte.VOLEIBOL),
			new Productos("Zapatullas de fútbol", 69.99, TipoDeporte.FUTBOL)
		};
    	String[] header = {"Producto", "Precio", "TipoDeporte"};

        setBounds(400, 200, 800, 500);
        setTitle("DeustoClubSports");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        modelo=  new TablaProductosModelo(productos, header);
        JTable tablaProductos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        JPanel tituloPanel = new JPanel();
        JButton btnVolver = new JButton("Volver atrás");
        JComboBox<TipoDeporte> comboBox = new JComboBox<>(TipoDeporte.values());
        
        comboBox.addActionListener(e -> {
            TipoDeporte seleccionado = (TipoDeporte) comboBox.getSelectedItem();

            if (seleccionado == TipoDeporte.TODOS) {
                modelo = new TablaProductosModelo(productos, header);
                tablaProductos.setModel(modelo);
                tablaProductos.repaint();
            } else {
                List<Productos> filtrados = new ArrayList<>();
                for (Productos p : productos) {
                    if (p.getDeporte() == seleccionado) {
                        filtrados.add(p);
                    }
                }
                modelo= new TablaProductosModelo(filtrados.toArray(new Productos[0]), header);
                tablaProductos.setModel(modelo);
                tablaProductos.repaint();
            }
        });

        
        
        
        //Listener
        btnVolver.addActionListener(e -> {
            dispose();
            new VentanaPrincipal();
        });
        
        JLabel tituloLabel = new JLabel("Elige los productos por deporte: ", SwingConstants.CENTER);
        tituloPanel.setLayout(new FlowLayout());
        tituloPanel.add(tituloLabel);
        tituloPanel.add(comboBox);
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelInferior.add(btnVolver);
        this.add(panelInferior, BorderLayout.SOUTH);
        this.add(tituloPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        
        
       
    }

    public static void main(String[] args) {
		new VentanaTienda();
	}
}

