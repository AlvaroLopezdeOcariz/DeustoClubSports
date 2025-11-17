package Swing;

public class Productos {

	private String nombre;
	private double precio;
	private TipoDeporte deporte;
	private int stock;
	public Productos(String nombre, double precio, TipoDeporte deporte, int stock) {
		this.nombre = nombre;
		this.precio = precio;
		this.deporte = deporte;
		this.stock = stock;
	}
	
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getNombre() {
		return nombre;
	}
	public double getPrecio() {
		return precio;
	}
		public TipoDeporte getDeporte() {
			return deporte;
		}
		public void setDeporte(TipoDeporte deporte) {
			this.deporte = deporte;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
}
