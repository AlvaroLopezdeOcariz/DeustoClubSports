package Swing;

import Swing.Instalacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
	
	// Datos de las instalaciones
	private final Instalacion[] datos = {
	        new Instalacion("Cancha de Fútbol 7","60x40 m","08:00","22:00","Fútbol 7",25.0,"img/instalaciones/futbol.jpg"),
	        new Instalacion("Piscina Cubierta","25x12,5 m","07:00","22:00","Natación",6.0,"img/instalaciones/piscina.jpg"),
	        new Instalacion("Pista de Pádel","10x20 m","08:00","23:00","Pádel",12.0,"img/instalaciones/padel.jpg"),
	        new Instalacion("Gimnasio","Sala fitness","06:30","23:00","Fitness",5.0,"img/instalaciones/gimnasio.jpg"),
	        new Instalacion("Pista de Tenis","23,77x10,97 m","08:00","22:00","Tenis",10.0,"img/instalaciones/tenis.jpg"),
	        new Instalacion("Pista de Baloncesto","28x15 m","08:00","22:00","Baloncesto",15.0,"img/instalaciones/baloncesto.jpg"),
	        new Instalacion("Pista de Voleibol","18x9 m","08:00","22:00","Voleibol",10.0,"img/instalaciones/volley.jpg"),
	        new Instalacion("Ring de Boxeo","6x6 m","10:00","20:00","Boxeo",20.0,"img/instalaciones/boxeo.jpg"),
	        new Instalacion("Pista de Hockey","40x20 m","08:00","22:00","Hockey",18.0,"img/instalaciones/hockey.jpg"),
	        new Instalacion("Campo de Golf","9 hoyos","09:00","19:00","Golf",30.0,"img/instalaciones/golf.jpg"),
	        new Instalacion("Mesa de Ping Pong","2,74x1,525 m","08:00","22:00","Ping Pong",8.0,"img/instalaciones/pingpong.jpg"),
	        new Instalacion("Pista de Atletismo","400 m","06:00","21:00","Atletismo",7.0,"img/instalaciones/atletismo.jpg")
	    };
	
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
        JScrollPane sp = new JScrollPane(grid);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp, BorderLayout.CENTER);
        
        // Crear tarjetas
        for (Instalacion inst : datos) {
            grid.add(crearTarjeta(inst));
        }
    }
	
	// Añade una tarjeta al grid
    private JPanel crearTarjeta(Instalacion inst) {
        JPanel card = new JPanel(new BorderLayout()); // Tarjeta con BorderLayout
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)), // Borde gris claro
                new EmptyBorder(10,10,10,10) // Margen interno
        ));
        card.setPreferredSize(new Dimension(260, 180)); // 

        // Imagen arriba
        JLabel img = new JLabel("", SwingConstants.CENTER); 
        ImageIcon icon = cargarIcono(inst.getRutaImagen(), 220, 140);
        
        // Si no se carga la imagen, muestra un texto alternativo
        if (icon != null) {
			img.setIcon(icon);
		} else {
			img.setText("Sin imagen");
		}
        
        card.add(img, BorderLayout.CENTER); // Imagen en el centro

        // Nombre debajo
        JLabel lbl = new JLabel(inst.getNombre(), SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 14f));
        card.add(lbl, BorderLayout.SOUTH);

        // Click -> abre detalle
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new DetalleDialog(VentanaInstalaciones.this, inst).setVisible(true);
            }
        });
        
        return card;
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
    
    // Diálogo de detalle de instalación
    private static class DetalleDialog extends JDialog {
        private static final long serialVersionUID = 1L;
        
        public DetalleDialog(JFrame owner, Instalacion inst) {
            super(owner, inst.getNombre(), true); // modal
            setSize(520, 420);
            setLocationRelativeTo(owner);
            setLayout(new BorderLayout(10,10));
            ((JComponent)getContentPane()).setBorder(new EmptyBorder(12,12,12,12)); // margen

            // Título
            JLabel titulo = new JLabel(inst.getNombre());
            titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
            add(titulo, BorderLayout.NORTH);

            // Centro -> imagen y datos
            JPanel centro = new JPanel(new BorderLayout(8,8));
            
            // Imagen arriba
            JLabel lblImg = new JLabel("", SwingConstants.CENTER);
            ImageIcon imagen = cargarIconoSimple(inst.getRutaImagen(), 480, 190);
            if (imagen != null) {
            	lblImg.setIcon(imagen);
            } else {
            	lblImg.setText("Sin imagen");
            }
            centro.add(lblImg, BorderLayout.NORTH);
            
            // Datos en grid
            JPanel datos = new JPanel(new GridLayout(0,2,6,6));
            datos.add(new JLabel("Deporte: " + inst.getDeporte())); 
            datos.add(new JLabel("Medidas: " + inst.getMedidas()));
            datos.add(new JLabel("Apertura: " + inst.getApertura()));
            datos.add(new JLabel("Cierre: " + inst.getCierre()));
            datos.add(new JLabel("Precio/hora: " + String.format("%.2f €", inst.getPrecioHora())));
            
            centro.add(datos, BorderLayout.CENTER); // Datos en el centro

            add(centro, BorderLayout.CENTER); // Centro

            // Abajo: botones
            JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnReservar = new JButton("Reservar");
            JButton btnCerrar = new JButton("Cerrar");
            
            // Listener Reservar -> pendiente de implementar
            btnReservar.addActionListener(e -> {
                
                
                dispose();
                new VentanaReserva().setVisible(true);
            });
            
            // Listener Cerrar
            btnCerrar.addActionListener(e -> dispose());

            // Añadir botones
            sur.add(btnReservar);
            sur.add(btnCerrar);
            add(sur, BorderLayout.SOUTH);
        }

        private static ImageIcon cargarIconoSimple(String ruta, int w, int h) {
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
    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VentanaInstalaciones().setVisible(true));
	}
}
