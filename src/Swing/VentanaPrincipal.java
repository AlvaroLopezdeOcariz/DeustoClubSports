package Swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pNorte;
	private JLabel lblPrueba;
	
	public VentanaPrincipal() {
		super();
		
		//Especificaciones de la ventana
		setBounds(500, 200, 500, 500);
		setTitle("DeustoClubSports");
		
		//Instanciar
		pNorte = new JPanel();
		lblPrueba = new JLabel("Bienvenidos");
		
		//Añadir paneles
		getContentPane().add(pNorte, BorderLayout.NORTH);
		
		
		
		//Añadir componentes
		pNorte.add(lblPrueba);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaPrincipal();
	}
}
