package ui.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import BD.BD;
import dominio.*;
import hilos.HiloGeneral;
import ui.modelos.*;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pNorte, pCentro, pSur;
	private JButton btnCafeteria, btnTienda, btnInstalaciones, btnCarrito;
	private JLabel lblImagen1, lblImagen2, lblImagen3;

	public VentanaPrincipal() {

		// Propiedades de la Ventana
		setBounds(400, 200, 800, 500);
		setTitle("DeustoClubSports");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Carrito carrito = Carrito.getInstance();
		// Panel Norte
		pNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		pNorte.setBorder(new EmptyBorder(10, 10, 10, 10));

		btnCafeteria = new JButton("CAFETERÍA");
		btnCafeteria.setFocusPainted(false);
		btnCafeteria.setBackground(new Color(230, 230, 230));
		btnCafeteria.setFont(new Font("SansSerif", Font.PLAIN, 14));

		btnInstalaciones = new JButton("INSTALACIONES");
		btnInstalaciones.setBounds(10, 10, 100, 30);
		btnInstalaciones.setFocusPainted(false);
		btnInstalaciones.setBackground(new Color(230, 230, 230));
		btnInstalaciones.setFont(new Font("SansSerif", Font.PLAIN, 14));

		btnTienda = new JButton("TIENDA");
		btnTienda.setFocusPainted(false);
		btnTienda.setBackground(new Color(230, 230, 230));
		btnTienda.setFont(new Font("SansSerif", Font.PLAIN, 14));

		btnCarrito = new JButton("CARRITO");
		btnCarrito.setFocusPainted(false);
		btnCarrito.setBackground(new Color(230, 230, 230));
		btnCarrito.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JButton btnTorneo = new JButton("TORNEO");
		btnTorneo.setFocusPainted(false);
		btnTorneo.setBackground(new Color(255, 215, 0)); // Color dorado para destacar
		btnTorneo.setFont(new Font("SansSerif", Font.BOLD, 14));

		// Panel Centro
		pCentro = new JPanel();

		lblImagen1 = new JLabel();
		lblImagen2 = new JLabel();
		lblImagen3 = new JLabel();

		ImageIcon imgOriginal1 = new ImageIcon("img/img1.jpg");
		Image imgEscalada1 = imgOriginal1.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
		ImageIcon img1 = new ImageIcon(imgEscalada1);

		ImageIcon imgOriginal2 = new ImageIcon("img/img2.jpg");
		Image imgEscalada2 = imgOriginal2.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
		ImageIcon img2 = new ImageIcon(imgEscalada2);

		ImageIcon imgOriginal3 = new ImageIcon("img/img3.jpg");
		Image imgEscalada3 = imgOriginal3.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
		ImageIcon img3 = new ImageIcon(imgEscalada3);

		lblImagen1.setIcon(img1);
		lblImagen2.setIcon(img2);
		lblImagen3.setIcon(img3);

		// Panel Sur
		pSur = new JPanel();

		// Añadir
		pNorte.add(btnCafeteria);
		pNorte.add(btnCarrito);
		pNorte.add(btnInstalaciones);
		pNorte.add(btnTienda);
		pNorte.add(btnTorneo); // Variable local, necesita ser accesible o declarada final/efectivamente final
								// si se usa dentro de listener,
								// pero aquí btnTorneo es local. Espera, necesito el listener.

		// ... (rest of add calls) ...

		// LISTENERS
		// ... (other listeners) ...

		btnTorneo.addActionListener(e -> {
			dispose();
			new VentanaTorneo();
		});

		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pSur, BorderLayout.SOUTH);

		pCentro.add(lblImagen1);
		pCentro.add(lblImagen2);
		pCentro.add(lblImagen3);

		// LISTENERS
		btnInstalaciones.addActionListener((e) -> {
			dispose();
			new VentanaInstalaciones().setVisible(true);

		});
		btnTienda.addActionListener((e) -> {
			dispose();
			new VentanaTienda();

		});
		btnCarrito.addActionListener((e) -> {
			dispose();
			new VentanaCarrito(carrito);

		});
		btnCafeteria.addActionListener((e) -> {
			dispose();
			new VentanaCafeteria().setVisible(true);

		});

		setVisible(true);
	}

	public static void main(String[] args) {

		new VentanaPrincipal();
	}
}
