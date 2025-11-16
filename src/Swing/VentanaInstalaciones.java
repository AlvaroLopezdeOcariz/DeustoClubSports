package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class VentanaInstalaciones extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel grid;
	
	public VentanaInstalaciones() {
		
		setTitle("Instalaciones - DeustoClubSports");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
        ((JComponent)getContentPane()).setBorder(new EmptyBorder(10,10,10,10));

        // Título arriba
        JLabel titulo = new JLabel("Nuestras Instalaciones");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        add(titulo, BorderLayout.NORTH);

        // Centro: grid 3 por fila
        grid = new JPanel(new GridLayout(0, 3, 16, 16));
        add(new JScrollPane(grid), BorderLayout.CENTER);

        // Rellena el grid con tarjetas
        addCard("Pista de Fútbol 7", "img/instalaciones/football-pitch-320100_640.jpg");
        addCard("Piscina Climatizada", "img/instalaciones/piscina.png");
        addCard("Pista de Pádel", "img/instalaciones/padel.png");
        addCard("Gimnasio", "img/instalaciones/gimnasio.png");
        addCard("Sala Multifunción", "img/instalaciones/sala.png");
        addCard("Pista de Baloncesto", null); 
        addCard("Pista de Fútbol 7", "img/instalaciones/football-pitch-320100_640.jpg");
        addCard("Piscina Climatizada", "img/instalaciones/piscina.png");
        addCard("Pista de Pádel", "img/instalaciones/padel.png");
        addCard("Gimnasio", "img/instalaciones/gimnasio.png");
        addCard("Sala Multifunción", "img/instalaciones/sala.png");
        addCard("Pista de Baloncesto", null); 
    }
	
	// Añade una tarjeta al grid
    private void addCard(String nombre, String rutaImagen) {
        JPanel card = new JPanel(new BorderLayout()); // Tarjeta con BorderLayout
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)), // Borde gris claro
                new EmptyBorder(10,10,10,10) // Margen interno
        ));

        // Imagen arriba
        JLabel img = new JLabel("", SwingConstants.CENTER); 
        ImageIcon icon = cargarIcono(rutaImagen, 220, 140);
        
        // Si no se carga la imagen, muestra un texto alternativo
        if (icon != null) {
			img.setIcon(icon);
		} else {
			img.setText("Sin imagen");
		}
        
        card.add(img, BorderLayout.CENTER); // Imagen en el centro

        // Nombre debajo
        JLabel lbl = new JLabel(nombre, SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 14f));
        card.add(lbl, BorderLayout.SOUTH);

        // Añade la tarjeta al grid
        grid.add(card);
    }

    // Carga y escala un icono desde la ruta dada
    private ImageIcon cargarIcono(String ruta, int w, int h) {
    	// Comprueba ruta válida
    	if (ruta == null || ruta.isEmpty()) {
    		return null;
    	}
        
    	// Intenta cargar la imagen
        ImageIcon original = new ImageIcon(ruta);
        
        // Si no se carga correctamente, devuelve null
        if (original.getIconWidth() <= 0) {
			return null;
		}
        
        Image escalada = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VentanaInstalaciones().setVisible(true));
	}
}
