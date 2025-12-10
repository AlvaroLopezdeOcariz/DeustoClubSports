package ui.ventanas;

import dominio.*;
import BD.BD;
import hilos.HiloGeneral;
import ui.modelos.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class VentanaAdministrador extends JFrame {
	// JTree
	private DefaultTreeModel modeloArbol;
	private JTree arbol;
	private JScrollPane scrollArbol;

	// Paneles
	private CardLayout cardLayout;
	private JPanel pNorte, pTablas, pRestock;

	private JButton btnVolver;

	// Tablas
	private JTable tablaProductos;
	private JTable tablaInscripciones;
	private JTable tablaCafeteria;
	private JTable tablaReservas;


	// JList
	private DefaultListModel<String> modeloRestock = new DefaultListModel<>();
	private JList<String> lRestock = new JList<>(modeloRestock);
	private JButton btnPeticion;

	private List<String> restockList = new ArrayList<>();

	String regAct;

	public VentanaAdministrador() {

		// Especificaciones de la ventana
		setBounds(400, 200, 800, 500);
		setTitle("Centro de Control");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		crearPanelNorte();

		// Creación de paneles

		getContentPane().add(pNorte, BorderLayout.NORTH);

		// Creación de componentes
		// Jtree
		crearArbol();

		// Panel de tablas
		crearPanelesTablas();

		// Panel sur con la lista
		crearPanelRestock();
		getContentPane().add(pRestock, BorderLayout.SOUTH);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollArbol, pTablas);
		splitPane.setDividerLocation(250);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		setVisible(true);
	}

	private void crearPanelNorte() {
		// FlowLayout.LEFT alinea el botón a la izquierda
		pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));

		btnVolver = new JButton("Volver atrás");
		btnVolver.addActionListener(e -> {
			dispose();
			new VentanaPrincipal();
		});

		pNorte.add(btnVolver);
	}

	private void crearArbol() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Registro de Actividad");

		// Los hijos
		DefaultMutableTreeNode productos = new DefaultMutableTreeNode("Productos Vendidos");
		DefaultMutableTreeNode inscripciones = new DefaultMutableTreeNode("Inscripciones al Club");
		DefaultMutableTreeNode cafeteria = new DefaultMutableTreeNode("Compras Cafetería");
		DefaultMutableTreeNode reservas = new DefaultMutableTreeNode("Reservas de Pistas");

		raiz.add(productos);
		raiz.add(inscripciones);
		raiz.add(cafeteria);
		raiz.add(reservas);

		modeloArbol = new DefaultTreeModel(raiz);
		arbol = new JTree(modeloArbol);
		scrollArbol = new JScrollPane(arbol);

		arbol.addTreeSelectionListener((e) -> {
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();

			if (nodo == null) {
				return;
			}

			regAct = nodo.toString();

			switch (regAct) {
				case "Productos Vendidos":
					cardLayout.show(pTablas, "Productos");
					break;

				case "Inscripciones al Club":
					cardLayout.show(pTablas, "Inscripciones");
					break;
				case "Compras Cafetería":
					cardLayout.show(pTablas, "Cafeteria");
					break;
				case "Reservas Instalaciones":
				    cardLayout.show(pTablas, "Reservas");
				    break;	

			}
		});
	}

	private void crearPanelesTablas() {
		cardLayout = new CardLayout();
		pTablas = new JPanel(cardLayout);

		pTablas.add(crearTablaProductos(), "Productos");
		pTablas.add(crearTablaInscripciones(), "Inscripciones");
		pTablas.add(crearTablaCafeteria(), "Cafeteria");
		pTablas.add(crearTablaReservas(), "Reservas");
	}

	private JScrollPane crearTablaProductos() {
		String[] columnas = { "ID", "Producto", "Cantidad", "Precio", "Fecha" };
		Object[][] datos = {
				{ 1, "Camiseta Deportiva", 15, "25.00€", "15/11/2025" },
				{ 2, "Balón Fútbol", 8, "18.50€", "16/11/2025" },
				{ 3, "Raqueta Tenis", 3, "65.00€", "17/11/2025" }
		};

		ModeloTablaAdministrador modeloTabla = new ModeloTablaAdministrador(BD.obtenerRestocks(), columnas);

		tablaProductos = new JTable(modeloTabla);
		tablaProductos.getTableHeader().setReorderingAllowed(false);

		tablaProductos.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int fila = tablaProductos.getSelectedRow();
					if (fila != -1) {
						String nombre = tablaProductos.getValueAt(fila, 0).toString();
						String unidades = tablaProductos.getValueAt(fila, 1).toString();
						String precio = tablaProductos.getValueAt(fila, 2).toString();
						String linea = "Producto [nombre=" + nombre + ", unidades=" + unidades + ", precio=" + precio
								+ "]";
						restockList.add(linea);
						modeloRestock.addElement(linea);

					}
				}

			}
		});

		return new JScrollPane(tablaProductos);
	}

	private JScrollPane crearTablaInscripciones() {
		String[] columnas = { "ID Socio", "Nombre", "Apellidos", "Fecha", "Membresía" };

		// Obtener datos de la BD
		ArrayList<Object[]> inscripciones = BD.obtenerInscripciones();
		Object[][] datos = new Object[inscripciones.size()][];
		for (int i = 0; i < inscripciones.size(); i++) {
			datos[i] = inscripciones.get(i);
		}

		DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaInscripciones = new JTable(modelo);
		tablaInscripciones.getTableHeader().setReorderingAllowed(false);
		return new JScrollPane(tablaInscripciones);
	}

	private JScrollPane crearTablaCafeteria() {
		String[] columnas = { "ID", "Producto", "Cantidad", "Total", "Fecha" };

		// Obtener datos de la BD
		ArrayList<Object[]> compras = BD.obtenerComprasCafeteria();
		Object[][] datos = new Object[compras.size()][];
		for (int i = 0; i < compras.size(); i++) {
			datos[i] = compras.get(i);
		}

		DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaCafeteria = new JTable(modelo);
		tablaCafeteria.getTableHeader().setReorderingAllowed(false);

		tablaCafeteria.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int fila = tablaCafeteria.getSelectedRow();
					if (fila != -1) {
						String nombre = tablaCafeteria.getValueAt(fila, 1).toString();
						String unidades = tablaCafeteria.getValueAt(fila, 2).toString();
						String precio = tablaCafeteria.getValueAt(fila, 3).toString();
						String linea = "Producto [nombre=" + nombre + ", unidades=" + unidades + ", precio=" + precio
								+ "]";
						restockList.add(linea);
						modeloRestock.addElement(linea);

					}
				}

			}
		});
		return new JScrollPane(tablaCafeteria);
	}
	
	private JScrollPane crearTablaReservas() {
	    String[] columnas = { "ID", "Instalación", "Fecha", "Inicio", "Fin", "Asistentes", "Precio" };

	    // Obtener datos de la BD
	    ArrayList<Object[]> reservas = BD.obtenerReservas();
	    Object[][] datos = new Object[reservas.size()][];

	    for (int i = 0; i < reservas.size(); i++) {
	        datos[i] = reservas.get(i);
	    }

	    DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tablaReservas = new JTable(modelo);
	    tablaReservas.getTableHeader().setReorderingAllowed(false);

	    return new JScrollPane(tablaReservas);
	}

	private void crearPanelRestock() {
		pRestock = new JPanel(new BorderLayout());
		JScrollPane spList = new JScrollPane(lRestock);
		btnPeticion = new JButton("Enviar petición de restock");
		btnPeticion.addActionListener(e -> {
			if (modeloRestock.getSize() == 0) {
				JOptionPane.showMessageDialog(this, "La lista está vacía.");
			} else {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < modeloRestock.getSize(); i++) {
					sb.append(modeloRestock.getElementAt(i)).append("\n");
				}
				JOptionPane.showMessageDialog(this, "Petición enviada:\n" + sb);
				modeloRestock.clear();
				restockList.clear();
				BD.eliminarRestock();
			}
		});
		pRestock.add(spList, BorderLayout.CENTER);
		pRestock.add(btnPeticion, BorderLayout.SOUTH);

		lRestock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Control + X
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
					int idx = lRestock.getSelectedIndex();
					if (idx != -1) {
						modeloRestock.remove(idx);
						restockList.remove(idx);
					}
				}
			}
		});

	}

	public static void main(String[] args) {
		new VentanaAdministrador();
	}
}