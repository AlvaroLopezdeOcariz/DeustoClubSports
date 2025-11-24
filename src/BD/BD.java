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

import Swing.Productos;
import Swing.TipoDeporte;
import Swing.Usuario;





public class BD {

	private static final String DB_URL = "jdbc:sqlite:./Productos.db";
	
	 public void InicializarBD() {
	        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Productos ("
	                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
	                + "nombre TEXT NOT NULL,"         
	                + "precio REAL,"
	                + "stock INTEGER,"
	                + "deporte TEXT"
	                + ");";

	        String sqlCreateTableUsuarios="CREATE TABLE IF NOT EXISTS Usuarios("
	        		+"id INTEGER PRIMARY KEY AUTOINCREMENT,"
	        		+"nombre TEXT NOT NULL,"
	        		+"nomUsuario TEXT,"
	        		+"contrasenia TEXT,"
	        		+"admin BOOLEAN"
	        		+");";
	        
	       String sqlCreateTableCarritos="CREATE TABLE IF NOT EXISTS Carritos ("
	    		   +"id_Car INTEGER PRIMARY KEY AUTOINCREMENT,"
	    		   +"id_Usuario INTEGER NOT NULL,"
	    		   +"total REAL,"
	    		   +"FOREIGN KEY(id_Usuario) REFERENCES Usuarios(id) ON DELETE CASCADE); ";
	        		
	       String sqlCreateTableItemCarritos="CREATE TABLE IF NOT EXISTS ItemsCarrito ("
	       		+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
	       		+ "    carrito_id INTEGER NOT NULL,"
	       		+ "    nombre_item TEXT NOT NULL,"
	       		+ "    precio REAL NOT NULL,"
	       		
	       		+ "    FOREIGN KEY (carrito_id) REFERENCES Carritos(id)"
	       		+ ");";
	      

	        try (Connection conexion = DriverManager.getConnection(DB_URL);
	             Statement consulta = conexion.createStatement()) {
	            consulta.execute(sqlCreateTable);
	           
	            consulta.execute(sqlCreateTableUsuarios);
	            consulta.execute(sqlCreateTableCarritos);
	            consulta.execute(sqlCreateTableItemCarritos);
	          
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	    }
	 
	 
	 
	  public void insertarUsuarios() {
	    	
	    	File f= new File("src/UsuariosRegistrados.txt");
	    	ArrayList<Usuario> listaUsuarios= new ArrayList<>();
	    	if(!f.exists()) {
	    		System.err.println("El archivo productos.txt no existe.");
	    	}
	    	try {
	    		Scanner sc = new Scanner(f);
	    		
	    		while(sc.hasNext()) {
	    			String linea = sc.nextLine();
	    			String[] datos = linea.split(";");
	    			if (datos.length== 4) {
	    				String nom= datos[0];
	    				String nomUsuario= datos[1];
	    				String contra= datos[2];
	    				Boolean admin= Boolean.parseBoolean(datos[3]);
	    				listaUsuarios.add(new Usuario(nom,nomUsuario,contra, admin));
	    				
	    			}
	    			
	    			
	    		}
	    		sc.close();
	    	}catch (FileNotFoundException e) {
              e.printStackTrace();}
	    	String comprobacion= "SELECT COUNT(*) FROM Usuarios WHERE nomUsuario=?";
		    
		    for(Usuario usu: listaUsuarios ) 
		    {
		    	  try (Connection conexion = DriverManager.getConnection(DB_URL);
			    		   PreparedStatement pstmt = conexion.prepareStatement(comprobacion))
			        	
			        {
			 	    		pstmt.setString(1, usu.getNomUsuario() );           
			                ResultSet rs = pstmt.executeQuery();
			                rs.next();
			                int count = rs.getInt(1);
			                
			                if(count==0) {
			                	String insertarUsuario= "INSERT INTO Usuarios(nombre,nomUsuario,contrasenia,admin) VALUES(?,?,?,?)";
			                	PreparedStatement insertStmt= conexion.prepareStatement(insertarUsuario);
			                	insertStmt.setString(1, usu.getNombre());
			                	insertStmt.setString(2, usu.getNomUsuario());
			                	insertStmt.setString(3,usu.getContrasenia());
			                	insertStmt.setBoolean(4,usu.getEsAdmin());
			                	insertStmt.executeUpdate();  
			                	System.out.println("Usuario insertada exitosamente.");
			                }
			                
			                
			                
			        }catch (SQLException e) {
			            e.printStackTrace();
			        }
		    	  }
		  
		    
		    
		    
		    }
		    
	  
	  public void insertarProductos() {
		   
  		
  		 File f = new File("txt/productos.txt");
          ArrayList<Productos> lsProductos= new ArrayList<>();
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
                      Double precio =Double.parseDouble(datos[1]) ;
                      TipoDeporte deporte = TipoDeporte.valueOf(datos[2]) ;
                      int stock = Integer.parseInt(datos[3]);
                      
                    lsProductos.add(new Productos(nombre, precio, deporte, stock));
                      
                      
                   
                     
                  }
                 
              }sc.close();
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
              
              
              
              
              
              
              if(count==0) {
              
           
              	 String insertarProduct = "INSERT INTO Productos (nombre,precio,deporte,stock) VALUES (?,?,?,?)";
                   PreparedStatement insertStmt = conexion.prepareStatement(insertarProduct);
                   insertStmt.setString(1, p.getNombre());
                   insertStmt.setDouble(2, p.getPrecio());
                   insertStmt.setString(3, p.getDeporte().toString());
                   insertStmt.setInt(4, p.getStock());
                   
                   insertStmt.executeUpdate();  
                   System.out.println("Producto insertado exitosamente.");
              	
              }   }    catch (SQLException e) {
          e.printStackTrace();
      } }
  }
  
  public static int obtenerIdUsuario(String nombre) {
  	String query = "SELECT * FROM Usuarios WHERE nomUsuario=?";
  	try (Connection conexion = DriverManager.getConnection(DB_URL);
  			PreparedStatement pstmt = conexion.prepareStatement(query)){
  		
  		pstmt.setString(1, nombre);
  		
  		try (ResultSet rs = pstmt.executeQuery()) {
              if (rs.next()) {
                  return rs.getInt("id"); // Devolver el ID de la actividad
              } else {
                  System.out.println("Usuario no encontrada.");
              }
          }
  		
  			
  		
  		
  	}catch (SQLException e) {
          e.printStackTrace();
      }
  	
  	return 0;
  }
  	

	  
	  
	  public static  Productos[] obtenerProductos() {
	    	ArrayList<Productos> lsProductos= new ArrayList<>();
	    	String sql = "SELECT * FROM Productos";
	    	try (Connection conexion = DriverManager.getConnection(DB_URL);
	                Statement stmt = conexion.createStatement();
	                ResultSet rs = stmt.executeQuery(sql)){
	    		
	    		
	    		while(rs.next()) {
	    			TipoDeporte deporte= TipoDeporte.valueOf(rs.getString("deporte"));
	    			
	    			
	    			Productos produc = new Productos(rs.getString("nombre"), rs.getDouble("precio"), deporte, rs.getInt("stock"));
	    			lsProductos.add(produc);
	    		}
	    		
	    		}
	    
	    	catch (SQLException e) {
	            e.printStackTrace();
	        }
			return lsProductos.toArray(new Productos[0]);
	       
	    	
	    }
		    
}
