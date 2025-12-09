package BD;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import dominio.Productos;
import dominio.TipoDeporte;
import dominio.Usuario;

public class BD {

	private static final String DB_URL = "jdbc:sqlite:./DeustoClubSports.db";

	public void InicializarBD() {
		String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Productos ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nombre TEXT NOT NULL,"
				+ "precio REAL,"
				+ "stock INTEGER,"
				+ "deporte TEXT"
				+ ");";

		String sqlCreateTableUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nombre TEXT NOT NULL,"
				+ "nomUsuario TEXT,"
				+ "contrasenia TEXT,"
				+ "admin BOOLEAN"
				+ ");";

		String sqlCreateTableCarritos = "CREATE TABLE IF NOT EXISTS Carritos ("
				+ "id_Car INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "id_Usuario INTEGER NOT NULL,"
				+ "total REAL,"
				+ "FOREIGN KEY(id_Usuario) REFERENCES Usuarios(id) ON DELETE CASCADE); ";

		String sqlCreateTableItemCarritos = "CREATE TABLE IF NOT EXISTS ItemsCarrito ("
				+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "    carrito_id INTEGER NOT NULL,"
				+ "    nombre_item TEXT NOT NULL,"
				+ "    precio REAL NOT NULL,"

				+ "    FOREIGN KEY (carrito_id) REFERENCES Carritos(id)"
				+ ");";

		String sqlCreateTableComprasCafeteria = "CREATE TABLE IF NOT EXISTS ComprasCafeteria ("
				+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "    producto TEXT NOT NULL,"
				+ "    cantidad INTEGER NOT NULL,"
				+ "    total REAL NOT NULL,"
				+ "    fecha TEXT NOT NULL"
				+ ");";

		String sqlCreateTableInscripciones = "CREATE TABLE IF NOT EXISTS Inscripciones ("
				+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "    nombre TEXT NOT NULL,"
				+ "    apellidos TEXT NOT NULL,"
				+ "    correo TEXT NOT NULL,"
				+ "    fecha_inscripcion TEXT NOT NULL,"
				+ "    membresia TEXT NOT NULL"
				+ ");";
		String sqlCreateTableRestock = "CREATE TABLE IF NOT EXISTS Restock ("
				+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "    producto_id INTEGER NOT NULL,"
				+ "    cantidad INTEGER NOT NULL,"
				+ "    fecha_restock TEXT NOT NULL,"
				+ "    FOREIGN KEY (producto_id) REFERENCES Productos(id)"
				+ ");";

		String sqlCreateTableEquiposTorneo = "CREATE TABLE IF NOT EXISTS EquiposTorneo ("
				+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "    nombre_equipo TEXT NOT NULL,"
				+ "    numero_jugadores INTEGER NOT NULL,"
				+ "    fecha_registro TEXT NOT NULL"
				+ ");";

		try (Connection conexion = DriverManager.getConnection(DB_URL);
				Statement consulta = conexion.createStatement()) {
			consulta.execute(sqlCreateTable);

			consulta.execute(sqlCreateTableUsuarios);
			consulta.execute(sqlCreateTableCarritos);
			consulta.execute(sqlCreateTableItemCarritos);
			consulta.execute(sqlCreateTableComprasCafeteria);
			consulta.execute(sqlCreateTableInscripciones);
			consulta.execute(sqlCreateTableRestock);
			consulta.execute(sqlCreateTableEquiposTorneo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertarUsuarios() {

		File f = new File("txt/usuarios.txt");
		ArrayList<Usuario> listaUsuarios = new ArrayList<>();
		if (!f.exists()) {
			System.err.println("El archivo productos.txt no existe.");
		}
		try {
			Scanner sc = new Scanner(f);

			while (sc.hasNext()) {
				String linea = sc.nextLine();
				String[] datos = linea.split(";");
				if (datos.length == 4) {
					String nom = datos[0];
					String nomUsuario = datos[1];
					String contra = datos[2];
					Boolean admin = Boolean.parseBoolean(datos[3]);
					listaUsuarios.add(new Usuario(nom, nomUsuario, contra, admin));

				}

			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String comprobacion = "SELECT COUNT(*) FROM Usuarios WHERE nomUsuario=?";

		for (Usuario usu : listaUsuarios) {
			try (Connection conexion = DriverManager.getConnection(DB_URL);
					PreparedStatement pstmt = conexion.prepareStatement(comprobacion))

			{
				pstmt.setString(1, usu.getNomUsuario());
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				int count = rs.getInt(1);

				if (count == 0) {
					String insertarUsuario = "INSERT INTO Usuarios(nombre,nomUsuario,contrasenia,admin) VALUES(?,?,?,?)";
					PreparedStatement insertStmt = conexion.prepareStatement(insertarUsuario);
					insertStmt.setString(1, usu.getNombre());
					insertStmt.setString(2, usu.getNomUsuario());
					insertStmt.setString(3, usu.getContrasenia());
					insertStmt.setBoolean(4, usu.getEsAdmin());
					insertStmt.executeUpdate();
					System.out.println("Usuario insertada exitosamente.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void insertarProductos() {

		File f = new File("txt/productos.txt");
		ArrayList<Productos> lsProductos = new ArrayList<>();
		if (!f.exists()) {
			System.err.println("El archivo productos.txt no existe.");
		}
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				String linea = sc.nextLine();
				String[] datos = linea.split(";");
				if (datos.length == 4) {
					String nombre = datos[0];
					Double precio = Double.parseDouble(datos[1]);
					TipoDeporte deporte = TipoDeporte.valueOf(datos[2]);
					int stock = Integer.parseInt(datos[3]);

					lsProductos.add(new Productos(nombre, precio, deporte, stock));

				}

			}
			sc.close();
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String comprobacion = "SELECT COUNT(*) FROM Productos WHERE nombre=?";

		for (Productos p : lsProductos) {
			try (Connection conexion = DriverManager.getConnection(DB_URL);
					PreparedStatement pstmt = conexion.prepareStatement(comprobacion))

			{

				pstmt.setString(1, p.getNombre());
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				int count = rs.getInt(1);

				if (count == 0) {

					String insertarProduct = "INSERT INTO Productos (nombre,precio,deporte,stock) VALUES (?,?,?,?)";
					PreparedStatement insertStmt = conexion.prepareStatement(insertarProduct);
					insertStmt.setString(1, p.getNombre());
					insertStmt.setDouble(2, p.getPrecio());
					insertStmt.setString(3, p.getDeporte().toString());
					insertStmt.setInt(4, p.getStock());

					insertStmt.executeUpdate();
					System.out.println("Producto insertado exitosamente.");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static int obtenerIdUsuario(String nombre) {
		String query = "SELECT * FROM Usuarios WHERE nomUsuario=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(query)) {

			pstmt.setString(1, nombre);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id"); // Devolver el ID de la actividad
				} else {
					System.out.println("Usuario no encontrada.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static int ObtenerIdProducto(String nombre) {
		String query = "SELECT * FROM Productos WHERE nombre=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(query)) {

			pstmt.setString(1, nombre);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id"); // Devolver el ID de la actividad
				} else {
					System.out.println("Producto no encontrado.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String obtenerUsuario(String nombre) {
		String query = "SELECT * FROM Usuarios WHERE nomUsuario=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(query)) {

			pstmt.setString(1, nombre);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("nombre"); // Devolver el nombre de usuario
				} else {
					System.out.println("Usuario no encontrada.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String ContraseñaUsuario(String nombre) {
		String query = "SELECT * FROM Usuarios WHERE nomUsuario=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(query)) {

			pstmt.setString(1, nombre);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("contrasenia"); // Devolver el ID de la actividad
				} else {
					System.out.println("Usuario no encontrada.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static Productos[] obtenerProductos() {
		ArrayList<Productos> lsProductos = new ArrayList<>();
		String sql = "SELECT * FROM Productos";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				TipoDeporte deporte = TipoDeporte.valueOf(rs.getString("deporte"));

				Productos produc = new Productos(rs.getString("nombre"), rs.getDouble("precio"), deporte,
						rs.getInt("stock"));
				lsProductos.add(produc);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return lsProductos.toArray(new Productos[0]);

	}

	public static boolean esAdministrador(String usuario) {
		String query = "SELECT admin FROM Usuarios WHERE nomUsuario=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(query)) {

			pstmt.setString(1, usuario);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getBoolean("admin"); // Devolver si es administrador
				} else {
					System.out.println("Usuario no encontrado.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void insertarComprasCafeteria(String producto, int cantidad, double total, String fecha) {
		String insert = "INSERT INTO ComprasCafeteria (producto, cantidad, total, fecha) VALUES (?, ?, ?, ?)";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(insert)) {
			pstmt.setString(1, producto);
			pstmt.setInt(2, cantidad);
			pstmt.setDouble(3, total);
			pstmt.setString(4, fecha);
			pstmt.executeUpdate();
			System.out.println("Compra de cafetería insertada exitosamente.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Object[]> obtenerComprasCafeteria() {
		ArrayList<Object[]> compras = new ArrayList<>();
		String query = "SELECT id, producto, cantidad, total, fecha FROM ComprasCafeteria";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Object[] fila = new Object[5];
				fila[0] = rs.getInt("id");
				fila[1] = rs.getString("producto");
				fila[2] = rs.getInt("cantidad");
				fila[3] = String.format("%.2f€", rs.getDouble("total"));
				fila[4] = rs.getString("fecha");
				compras.add(fila);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compras;
	}

	public static void insertarInscripcion(String nombre, String apellidos, String correo, String fecha,
			String membresia) {
		String insert = "INSERT INTO Inscripciones (nombre, apellidos, correo, fecha_inscripcion, membresia) VALUES (?, ?, ?, ?, ?)";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(insert)) {
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellidos);
			pstmt.setString(3, correo);
			pstmt.setString(4, fecha);
			pstmt.setString(5, membresia);
			pstmt.executeUpdate();
			System.out.println("Inscripción insertada exitosamente.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Object[]> obtenerInscripciones() {
		ArrayList<Object[]> inscripciones = new ArrayList<>();
		String query = "SELECT id, nombre, apellidos, fecha_inscripcion, membresia FROM Inscripciones ORDER BY id DESC";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Object[] fila = new Object[5];
				fila[0] = rs.getInt("id");
				fila[1] = rs.getString("nombre");
				fila[2] = rs.getString("apellidos");
				fila[3] = rs.getString("fecha_inscripcion");
				fila[4] = rs.getString("membresia");
				inscripciones.add(fila);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inscripciones;
	}

	public static int obtenerStockProducto(int productoId) {
		String query = "SELECT stock FROM Productos WHERE id=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(query)) {

			pstmt.setInt(1, productoId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("stock"); // Devolver el stock del producto
				} else {
					System.out.println("Producto no encontrado.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return 0;
	}

	public static void insertarRestock(int productoId, int cantidad, String fecha, String nombreProducto) {

		String insert = "INSERT INTO Restock (producto_id, cantidad, fecha_restock) VALUES (?, ?, ?)";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(insert)) {
			pstmt.setInt(1, productoId);
			pstmt.setInt(2, cantidad);
			pstmt.setString(3, fecha);
			pstmt.executeUpdate();
			System.out.println("Restock insertado exitosamente.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void eliminarRestock(int restockId) {
		String delete = "DELETE FROM Restock WHERE id=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(delete)) {
			pstmt.setInt(1, restockId);
			pstmt.executeUpdate();
			System.out.println("Restock eliminado exitosamente.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String obtenerFechaRestock(int restockId) {
		String query = "SELECT fecha_restock FROM Restock WHERE id=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(query)) {

			pstmt.setInt(1, restockId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("fecha_restock"); // Devolver la fecha de restock
				} else {
					System.out.println("Restock no encontrado.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return "";
	}

	public static ArrayList<Productos> obtenerRestocks() {
		ArrayList<Productos> restocks = new ArrayList<>();
		String query = "SELECT producto_id, cantidad, fecha_restock FROM Restock";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				int productoId = rs.getInt("producto_id");
				int cantidad = rs.getInt("cantidad");

				// Obtener el producto asociado al restock
				for (Productos p : obtenerProductos()) {
					int id = ObtenerIdProducto(p.getNombre());
					if (id == productoId) {
						restocks.add(new Productos(p.getNombre(), p.getPrecio(), p.getDeporte(), cantidad));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restocks;
	}

	public static void insertarEquipoTorneo(String nombreEquipo, int numJugadores, String fecha) {
		String insert = "INSERT INTO EquiposTorneo (nombre_equipo, numero_jugadores, fecha_registro) VALUES (?, ?, ?)";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(insert)) {
			pstmt.setString(1, nombreEquipo);
			pstmt.setInt(2, numJugadores);
			pstmt.setString(3, fecha);
			pstmt.executeUpdate();
			System.out.println("Equipo inscrito exitosamente en el torneo.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Object[]> obtenerEquiposTorneo() {
		ArrayList<Object[]> equipos = new ArrayList<>();
		String query = "SELECT id, nombre_equipo, numero_jugadores, fecha_registro FROM EquiposTorneo";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Object[] fila = new Object[4];
				fila[0] = rs.getInt("id");
				fila[1] = rs.getString("nombre_equipo");
				fila[2] = rs.getInt("numero_jugadores");
				fila[3] = rs.getString("fecha_registro");
				equipos.add(fila);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipos;
	}

	public static void eliminarEquipoTorneo(int id) {
		String delete = "DELETE FROM EquiposTorneo WHERE id=?";
		try (Connection conexion = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conexion.prepareStatement(delete)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			System.out.println("Equipo eliminado exitosamente del torneo.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}