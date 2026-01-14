package ui.ventanas;

import dominio.*;
import BD.BD;
import hilos.HiloGeneral;
import ui.modelos.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class VentanaCompraProducto extends JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public VentanaCompraProducto(JFrame owner, Productos producto) {
		setTitle("Comprar producto");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		// Título
		JLabel lblTitulo = new JLabel("Producto seleccionado:", JLabel.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setForeground(new Color(0, 35, 102));

		JPanel panelCentro = new JPanel(new BorderLayout());
		panelCentro.setBackground(Color.WHITE);

		// Datos del producto
		JLabel lblProducto = new JLabel(
				"<html><center>" +
						producto.getNombre() +
						"<br>Precio: " + producto.getPrecio() + " €" +
						"<br>Deporte: " + producto.getDeporte() +
						"</center></html>",
				JLabel.CENTER);
		lblProducto.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		// Botón comprar
		JPanel panelSpinner = new JPanel();
		panelSpinner.setBackground(Color.WHITE);

		JLabel lblCantidad = new JLabel("Cantidad: ");

		JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

		panelSpinner.add(lblCantidad);
		panelSpinner.add(spinnerCantidad);

		panelCentro.add(lblProducto, BorderLayout.CENTER);
		panelCentro.add(panelSpinner, BorderLayout.SOUTH);

		// Botones
		JButton btnComprar = new JButton("Confirmar compra");
		btnComprar.setBackground(new Color(0, 35, 102));
		btnComprar.setForeground(Color.WHITE);
		btnComprar.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnComprar.setFocusPainted(false);

		btnComprar.addActionListener(e -> {

			int cantidad = (int) spinnerCantidad.getValue();
			double total = cantidad * producto.getPrecio();

			JOptionPane.showMessageDialog(
					this,
					"Compra realizada:\n" +
							cantidad + " unidad(es) de:\n" +
							producto.getNombre() +
							"\nPrecio total: " + String.format("%.2f", total) + " €");

			int c = producto.getStock() - cantidad;
			producto.setStock(c);
			Carrito carrito = Carrito.getInstance();
			carrito.add(producto, cantidad);

			JOptionPane.showMessageDialog(this, "Producto añadido al carrito.");
			dispose();
			new VentanaCarrito(carrito).setVisible(true);

		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(178, 34, 34)); // Firebrick
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnCancelar.setFocusPainted(false);

		btnCancelar.addActionListener(e -> dispose());

		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(Color.WHITE);

		panelBotones.add(btnComprar);
		panelBotones.add(btnCancelar);

		add(lblTitulo, BorderLayout.NORTH);
		add(panelCentro, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
	}
}
