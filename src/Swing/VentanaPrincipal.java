package Swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel pNorte, pCentro, pSur;
	private JButton btnCafeteria, btnTienda, btnInstalaciones, btnVolver;
	private JLabel lblImagen1, lblImagen2, lblImagen3;
	public VentanaPrincipal() {
		
		//Propiedades de la Ventana
		setBounds(400, 200, 800, 500);
		
		
		//Creación de componentes
		pNorte = new JPanel(new BorderLayout(150, 30)); 
		pCentro = new JPanel();
		pSur = new JPanel();
		
		btnCafeteria = new JButton("Cafetería");
		btnInstalaciones = new JButton("Instalaciones");
		btnInstalaciones.setBounds(4,4,4,4);
		btnTienda = new JButton("Tienda");
		btnVolver = new JButton("Volver");
		
		
        lblImagen1 = new JLabel();
        lblImagen2 = new JLabel();
        lblImagen3 = new JLabel();
        
       
        ImageIcon imgOriginal1 = new ImageIcon("img/img1.jpg");
        Image imgEscalada1 = imgOriginal1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon img1 = new ImageIcon(imgEscalada1);
        
        ImageIcon imgOriginal2 = new ImageIcon("img/img2.jpg");
        Image imgEscalada2 = imgOriginal2.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(imgEscalada2);
        
        ImageIcon imgOriginal3 = new ImageIcon("img/img3.jpg");
        Image imgEscalada3 = imgOriginal3.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon img3 = new ImageIcon(imgEscalada3);
        
        lblImagen1.setIcon(img1);
        lblImagen2.setIcon(img2);
        lblImagen3.setIcon(img3);

		//Añadir 
		pNorte.add(btnCafeteria, BorderLayout.WEST);       
        pNorte.add(btnInstalaciones, BorderLayout.CENTER); 
        pNorte.add(btnTienda, BorderLayout.EAST);          
		
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		
		pCentro.add(lblImagen1);
		pCentro.add(lblImagen2);
		pCentro.add(lblImagen3);
		pSur.add(btnVolver);
		
		
		//LISTENERS
		btnInstalaciones.addActionListener((e) -> {
			dispose();
			new VentanaInstalaciones();
			
		});
		
		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaPrincipal();
	}
}
