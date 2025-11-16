package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
        
        // Panel superior
        JPanel top = new JPanel(new BorderLayout());
        top.add(titulo, BorderLayout.WEST);

        JButton btnVolver = new JButton("Volver atrás");
        btnVolver.addActionListener(e -> {
            dispose();
            new VentanaPrincipal().setVisible(true); // vuelvo a la ventana Principal
        });

        top.add(btnVolver, BorderLayout.EAST);

        // Colocacion del Panel superior
        add(top, BorderLayout.NORTH);



        // Centro: grid 3 por fila
        grid = new JPanel(new GridLayout(0, 3, 16, 16));
        add(new JScrollPane(grid), BorderLayout.CENTER);

        // Rellena el grid con tarjetas
        addCard("Cancha de Fútbol 7", "img/instalaciones/futbol.jpg");
        addCard("Piscina Cubierta", "img/instalaciones/piscina.jpg");
        addCard("Pista de Pádel", "img/instalaciones/padel.jpg");
        addCard("Gimnasio", "img/instalaciones/gimnasio.jpg");
        addCard("Pista de Tenis", "img/instalaciones/tenis.jpg");
        addCard("Pista de Baloncesto", "img/instalaciones/baloncesto.jpg"); 
        addCard("Pista de VolleyBall", "img/instalaciones/volley.jpg");
        addCard("Mesa de PingPong", "img/instalaciones/pingpong.jpg");
        addCard("Ring de Boxeo", "img/instalaciones/boxeo.jpg");
        addCard("Pista de Atletismo", "img/instalaciones/atletismo.jpg");
        addCard("Pista de Hockey", "img/instalaciones/hockey.jpg");
        addCard("Campo de Golf", "img/instalaciones/golf.jpg");
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
