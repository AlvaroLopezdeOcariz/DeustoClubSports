package ui.modelos;

import java.util.List;

import BD.BD;
import dominio.Productos;

public class ModeloTablaAdministrador extends javax.swing.table.AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Productos> productos;
	private String[] header;

	public ModeloTablaAdministrador(List<Productos> productos, String[] header) {
		super();
		this.productos = productos;
		this.header = header;
	}

	public int getRowCount() {
		return productos.size();
	}

	public int getColumnCount() {
		return header.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Productos p = productos.get(rowIndex);
		int id = BD.ObtenerIdProducto(p.getNombre());

		String fecha = BD.obtenerFechaRestock(id);
		switch (columnIndex) {
			case 0:
				return id;
			case 1:
				return p.getNombre();

			case 2:
				return p.getStock();
			case 3:
				return fecha;
		}
		return "";
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}
}