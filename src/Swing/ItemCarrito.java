package Swing;

public class ItemCarrito {
    private Productos producto;
    private int cantidad;

    public ItemCarrito(Productos producto, int cantidad) {
        this.producto = producto;
        this.cantidad = Math.max(1, cantidad);
    }

    public Productos getProducto() { 
    	return producto; 
    	}
    public int getCantidad() { 
    	return cantidad; 
    	}
    public void setCantidad(int cantidad) { 
    	this.cantidad = Math.max(1, cantidad); 
    	}

    public double getPrecio() { 
    	return producto.getPrecio(); 
    	}
    public double getSubtotal() {
    	return getPrecio() * getCantidad(); 
    	}
}
