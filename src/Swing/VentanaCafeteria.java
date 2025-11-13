package Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaCafeteria extends JFrame {

	private static final long serialVersionUID = 1L;

	public void VentanaMenuCafeteria() {
        setTitle("Menú - Cafetería DeustoClubSports");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 50, 40));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("Menú - Cafetería DeustoClubSports", SwingConstants.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));

        JPanel textoHeader = new JPanel(new GridLayout(2, 1));
        textoHeader.setOpaque(false);
        textoHeader.add(titulo);
    
        headerPanel.add(textoHeader, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Panel principal con scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 240, 235));

        // Secciones del menú
        mainPanel.add(crearSeccion("Bocatas", new String[][] {
                {"Bocata Baconcheese", "Baguette pequeña, bacon, queso y salsa tártara.", "€4.00"},
                {"Bocata de atún ", "Baguette pequeña, atún, lechuga, mayonesa y cebolla.", "€4.00"},
                {"Choripan", "Baguette pequeña, chorizo y queso.", "€3.50"}
        }));

        mainPanel.add(crearSeccion("Bollería", new String[][] {
                {"Cruasán de chocolate", "Cruasán relleno de chocolate fundido.", "€1.50"},
                {"Cookie de chocolate", "Galleta casera con virutas de chocolate.", "€1.50"},
                {"Napolitana", "Bollo cilíndrico relleno de chocolate.", "€1.50"},
                {"DeustoBollo", "Bollo especial de la casa, relleno de chocolate. Opción vegetariana disponible.", "€1.50"}
        }));

        mainPanel.add(crearSeccion("Trozo de pizza", new String[][] {
                {"Margherita", "Tomate, mozzarella y albahaca fresca.", "€1.80"},
                {"Prosciutto e Funghi", "Jamón cocido, champiñones y mozzarella.", "€1.90"},
                {"Quattro Formaggi", "Mezcla de cuatro quesos italianos fundidos.", "€2.10"}
        }));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    // crearSeccion será un método privado que crea una sección del menú
	
}
    
    // Falta crear tarjetas entre otras cosas, será otro método privado


