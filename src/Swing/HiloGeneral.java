package Swing;

import javax.swing.*;
import java.awt.*;

public class HiloGeneral implements Runnable {
    private JProgressBar barraProgreso;
    private JDialog dialogo;
    private JFrame ventanaPadre;
    private String titulo;       
    private String mensaje; 
    
    public HiloGeneral(JFrame ventanaPadre, String titulo, String mensaje) {
        this.ventanaPadre = ventanaPadre;
        this.titulo = titulo;          
        this.mensaje = mensaje;
        crearVentanaProgreso();
    }
    
    
    private void crearVentanaProgreso() {
        dialogo = new JDialog(ventanaPadre, titulo, true);  
        dialogo.setSize(450, 150);
        dialogo.setLocationRelativeTo(ventanaPadre);
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogo.setResizable(false);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        JPanel pCentro = new JPanel();
        pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
        pCentro.setBackground(Color.WHITE);
        
        JLabel lblMensaje = new JLabel(mensaje); 
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 14));
        lblMensaje.setForeground(new Color(0, 102, 204));
        lblMensaje.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        JLabel lblEspera = new JLabel("Por favor, espere");
        lblEspera.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEspera.setForeground(Color.GRAY);
        lblEspera.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        pCentro.add(lblMensaje);
        pCentro.add(Box.createVerticalStrut(5));
        pCentro.add(lblEspera);
        
        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setValue(0);
        barraProgreso.setStringPainted(true);
        barraProgreso.setPreferredSize(new Dimension(400, 25));
        barraProgreso.setForeground(new Color(34, 139, 34));
        barraProgreso.setFont(new Font("Arial", Font.BOLD, 12));
        
        panel.add(pCentro, BorderLayout.CENTER);
        panel.add(barraProgreso, BorderLayout.SOUTH);
        
        dialogo.add(panel);
    }
    


    
    
    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            barraProgreso.setValue(i); //Actualizar la barra
            try {
                Thread.sleep(30); //Esperar 30 milisegundos que son un total de 3 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dialogo.dispose(); 
    }
    
    //Método para iniciar el hilo y mostrar la ventana
    public void iniciar() {
        Thread hilo = new Thread(this);
        hilo.start(); 
        dialogo.setVisible(true); 
    }
}
