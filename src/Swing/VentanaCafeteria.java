package Swing;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;

public class VentanaCafeteria extends JFrame {

    private static final long serialVersionUID = 1L;

    private JList<MenuItem> listaProductos;
    private DefaultListModel<MenuItem> modeloProductos;

    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;

    private JLabel lblTotal;

    public VentanaCafeteria() {
        setTitle("Cafetería - DeustoClubSports");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        // Márgenes
        ((JComponent)getContentPane()).setBorder(new EmptyBorder(10,10,10,10));

        // Lista de productos a la izquierda
        add(crearPanelProductos(), BorderLayout.WEST);

        // Tabla del carrito en el centro
        add(crearPanelCarrito(), BorderLayout.CENTER);

        // Informacion de abajo (total y pagar)
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    // Apartado de crear los productos
    private JPanel crearPanelProductos() {
        JPanel p = new JPanel(new BorderLayout(5,5));
        p.setPreferredSize(new Dimension(260, 0));
        
        // Título
        JLabel titulo = new JLabel("Productos");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        p.add(titulo, BorderLayout.NORTH);

        // Lista de productos
        modeloProductos = new DefaultListModel<>();
        cargarCatalogo(modeloProductos);

        // JList con el modelo
        listaProductos = new JList<>(modeloProductos);
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane spLista = new JScrollPane(listaProductos);
        p.add(spLista, BorderLayout.CENTER);
        
        // Botón para añadir al carrito
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Añadir");
        btnAdd.addActionListener(e -> anadirSeleccionado());
        acciones.add(btnAdd);

        // Agregar al panel
        p.add(acciones, BorderLayout.SOUTH);
        return p;
    }

    // Apartado de crear el carrito
    private JPanel crearPanelCarrito() {
        JPanel p = new JPanel(new BorderLayout(5,5));
        
        // Título
        JLabel titulo = new JLabel("Carrito");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        p.add(titulo, BorderLayout.NORTH);

        // Modelo de la tabla: columnas -> Producto, Cantidad, Precio, Subtotal
        modeloCarrito = new DefaultTableModel(new Object[]{"Producto","Cant.","Precio","Subtotal"}, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
            
            // Define tipos de datos para cada columna
            // Pongo el ? para que no de error en versiones antiguas de Java
            @Override public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> Integer.class; // cantidad
                    case 2, 3 -> Double.class; // precio, subtotal
                    default -> String.class;
                };
            }
        }; 
        
        // Tabla del carrito
        tablaCarrito = new JTable(modeloCarrito);
        tablaCarrito.setFillsViewportHeight(true);
        JScrollPane spTabla = new JScrollPane(tablaCarrito);
        p.add(spTabla, BorderLayout.CENTER);

        // Botones del carrito
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnMas = new JButton("+");
        JButton btnMenos = new JButton("–");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVaciar = new JButton("Vaciar");
        
        // Listeners de los botones
        btnMas.addActionListener(e -> modificarCantidad(+1)); // aumentar cantidad
        btnMenos.addActionListener(e -> modificarCantidad(-1)); // disminuir cantidad
        btnEliminar.addActionListener(e -> eliminarSeleccionado()); // eliminar seleccionado
        btnVaciar.addActionListener(e -> {
            modeloCarrito.setRowCount(0);
            recalcularTotal();
        });
        
        // Añadir botones al panel
        botones.add(btnMenos);
        botones.add(btnMas);
        botones.add(btnEliminar);
        botones.add(btnVaciar);
        
        p.add(botones, BorderLayout.SOUTH);
        return p;
    }

    // Apartado inferior (total y pagar)
    private JPanel crearPanelInferior() {
        JPanel p = new JPanel(new BorderLayout());
        
        // Etiqueta del total
        lblTotal = new JLabel("Total: 0.00 €");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 16f));
        p.add(lblTotal, BorderLayout.WEST);
		
        // Botón de pagar
        JButton btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(e -> pagar());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.add(btnPagar);

        p.add(right, BorderLayout.EAST);
        return p;
    }

    // ---- Metodos de los listeners ----
    
    // Añadir producto seleccionado al carrito
    private void anadirSeleccionado() {
    	// Obtener producto seleccionado
        MenuItem seleccionado = listaProductos.getSelectedValue();
        if (seleccionado == null) return;

        // Si ya está en el carrito, incrementa cantidad
        int row = buscarProductoEnCarrito(seleccionado.getNombre()); // buscar por nombre
        if (row >= 0) {
            int cant = (Integer) modeloCarrito.getValueAt(row, 1); // obtener cantidad actual
            cant++; // incrementar cantidad
            modeloCarrito.setValueAt(cant, row, 1); // actualizar cantidad 
            double precio = (Double) modeloCarrito.getValueAt(row, 2); // obtener precio
            modeloCarrito.setValueAt(cant * precio, row, 3); // actualizar subtotal
        } else {
            modeloCarrito.addRow(new Object[]{seleccionado.getNombre(), 1, seleccionado.getNombre(), seleccionado.getNombre()}); // añadir nuevo producto
        }
        recalcularTotal(); // actualizar total
    }

    // Buscar producto en el carrito por el nombre
    private int buscarProductoEnCarrito(String nombre) {
    	// recorrer filas del modelo
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            String nom = (String) modeloCarrito.getValueAt(i, 0);
            if (nom.equals(nombre)) return i; // devolver fila si coincide
        }
        return -1; // -1 en caso de no encontrarlo
    }
    
    // Modificar cantidad del producto seleccionado en el carrito
    private void modificarCantidad(int valor) { 
    	// obtener fila seleccionada
        int row = tablaCarrito.getSelectedRow();
        if (row < 0) return;
        
        // modificar cantidad
        int cant = (Integer) modeloCarrito.getValueAt(row, 1);
        cant += valor;
        if (cant <= 0) {
            // si el usuario hace la resta cuando esta en 1, se elimina la fila
            modeloCarrito.removeRow(row);
        } else {
        	// actualizar cantidad y subtotal
            modeloCarrito.setValueAt(cant, row, 1);
            double precio = (Double) modeloCarrito.getValueAt(row, 2);
            modeloCarrito.setValueAt(cant * precio, row, 3);
        }
        recalcularTotal(); // actualizar total
    }
    
    // Eliminar producto seleccionado del carrito
    private void eliminarSeleccionado() {
    	// obtener fila seleccionada
        int row = tablaCarrito.getSelectedRow();
        // eliminar fila si hay selección
        if (row >= 0) {
            modeloCarrito.removeRow(row);
            recalcularTotal();
        }
    }
    
    // Recalcular el total del carrito
    private void recalcularTotal() {
    	// sumar subtotales
        double total = 0.0;
        // recorrer filas del modelo
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            total += (Double) modeloCarrito.getValueAt(i, 3); // sumar subtotal
        }
        lblTotal.setText(String.format(Locale.US, "Total: %.2f €", total));
        // el US en Locale es para forzar el punto decimal y no la coma
    }
    
    // Pagar el carrito - mas adelante se programara una venta para pagar
    private void pagar() {
        double total = 0.0;
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            total += (Double) modeloCarrito.getValueAt(i, 3);
        }
        if (total <= 0.0) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }
        JOptionPane.showMessageDialog(this,
                String.format(Locale.US, "¡Gracias por tu compra! Importe: %.2f €", total));
        modeloCarrito.setRowCount(0);
        recalcularTotal(); // actualizar total
    }

    // Datos de la cafeteria
    private void cargarCatalogo(DefaultListModel<MenuItem> m) {
        m.addElement(new MenuItem("Café solo", 1.50));
        m.addElement(new MenuItem("Cortado", 1.60));
        m.addElement(new MenuItem("Capuccino", 2.10));
        m.addElement(new MenuItem("Bocadillo tortilla", 3.50));
        m.addElement(new MenuItem("Bocadillo vegetal", 3.90));
        m.addElement(new MenuItem("Plato de pasta", 6.50));
        m.addElement(new MenuItem("Ensalada César", 6.90));
        m.addElement(new MenuItem("Brownie", 2.80));
        m.addElement(new MenuItem("Yogur con frutas", 2.20));
    }

    // Para pruebas locales
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaCafeteria().setVisible(true));
    }
}
