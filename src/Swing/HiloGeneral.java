package Swing;

public class HiloGeneral {
	private Thread hilo;
    private volatile boolean ejecutando = false;
    private int tiempoPausa; 
    private Runnable tarea;
}
