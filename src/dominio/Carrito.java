package dominio;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrito {

    private static Carrito instancia;
    private final List<ItemCarrito> items;

    private Carrito() {
        items = new ArrayList<>();
    }

    public static Carrito getInstance() {
        if (instancia == null)
            instancia = new Carrito();
        return instancia;
    }

    // Añade el producto (si ya existe, suma la cantidad)
    public void add(Productos producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().equals(producto)) {
                item.setCantidad(item.getCantidad() + Math.max(1, cantidad));

            }
        }
        // Si no existe
        items.add(new ItemCarrito(producto, Math.max(1, cantidad)));
    }

    // Elimina producto por índice
    public void removeAt(int index) {
        if (index >= 0 && index < items.size())
            items.remove(index);
    }

    public void clear() {
        items.clear();
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public double calcularTotal() {
        for (ItemCarrito item : items) {
            double total = 0;
            for (ItemCarrito i : items) {
                total += item.getSubtotal();
            }
            return total;
        }
        return 0;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
