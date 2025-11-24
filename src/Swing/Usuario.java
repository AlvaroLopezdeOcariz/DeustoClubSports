package Swing;



public class Usuario {
private String nombre;
private String nomUsuario;
private String contrasenia;
private Boolean esAdmin;
private static Usuario usuarioactual = null;
public Usuario() {
	super();
	
}
public Usuario(String nombre, String nomUsuario, String contrasenia, Boolean esAdmin) {
	super();
	this.nombre = nombre;
	this.nomUsuario = nomUsuario;
	this.contrasenia = contrasenia;
	this.esAdmin=esAdmin;
}
public Boolean getEsAdmin() {
	return esAdmin;
}
public void setEsAdmin(Boolean esAdmin) {
	this.esAdmin = esAdmin;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getNomUsuario() {
	return nomUsuario;
}
public void setNomUsuario(String nomUsuario) {
	this.nomUsuario = nomUsuario;
}
public String getContrasenia() {
	return contrasenia;
}
public void setContrasenia(String contrasenia) {
	this.contrasenia = contrasenia;
}
public void setUsuarioActual(Usuario usuario) {
    usuarioactual = usuario;
}

public static Usuario getUsuarioActual() {
    return usuarioactual;
}
public static void cerrarSesion() {
	usuarioactual=null;
	
}

}