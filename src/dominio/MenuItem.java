package dominio;

import java.util.Locale;
import java.util.Objects;

public class MenuItem {
    private String nombre;
    private double precio;

    public MenuItem(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s  (%.2f €)", nombre, precio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MenuItem mi))
            return false;
        return Objects.equals(nombre, mi.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}