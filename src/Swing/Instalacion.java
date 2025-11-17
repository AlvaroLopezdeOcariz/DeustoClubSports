package Swing;

public class Instalacion {
    private String nombre;
    private String medidas;       
    private String apertura;      
    private String cierre;        
    private String deporte;       
    private double precioHora;    
    private String rutaImagen;    

    public Instalacion(String nombre, String medidas, String apertura, String cierre, String deporte, double precioHora, String rutaImagen) {
        this.nombre = nombre;
        this.medidas = medidas;
        this.apertura = apertura;
        this.cierre = cierre;
        this.deporte = deporte;
        this.precioHora = precioHora;
        this.rutaImagen = rutaImagen;
    }

    public String getNombre() { 
    	return nombre; 
    }
    
    public String getMedidas() { 
    	return medidas; 
    }
    
    public String getApertura() { 
    	return apertura; 
    }
    
    public String getCierre() { 
    	return cierre; 
    }
    
    public String getDeporte() { 
    	return deporte; 
    }
    
    public double getPrecioHora() { 
    	return precioHora; 
    }
    
    public String getRutaImagen() { 
    	return rutaImagen; 
    }
}