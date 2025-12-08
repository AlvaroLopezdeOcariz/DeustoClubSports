package ui.modelos;

import java.util.ArrayList;
import java.util.List;

import dominio.Productos;

public class TablaProductosModelo extends javax.swing.table.AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Productos[] productos;
	private String[] header;

	public TablaProductosModelo(Productos[] productos, String[] header) {
		super();
		this.productos = productos;
		this.header = header;
	}

	@Override
	public int getRowCount() {
		return productos.length;
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Productos p = productos[rowIndex];
		switch (columnIndex) {
			case 0:
				return p.getDeporte();
			case 1:
				return p.getNombre();

			case 2:
				return p.getPrecio() + " €";
			case 3:
				return p.getStock();
		}
		return "";
	}

	public Productos getProductoAt(int fila) {
		return productos[fila];
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}
}
