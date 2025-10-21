package Swing;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registro extends JFrame {
	private JPanel pSur;
	private JLabel lblTitulo, lblUsuario, lblPassword, lblCorreo;
    private JTextField campoUsuario, campoCorreo;
    private JPasswordField campoPassword;
    private JButton btnAceptar, btnCancelar;
    
    
    public Registro() {
    	setTitle("Registro de Usuario");
    	setBounds(400, 200, 800, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        
        pSur = new JPanel();
        
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        
        btnAceptar.addActionListener((e) -> { 
        	String usuario = campoUsuario.getText();
            String correo = campoCorreo.getText();
            
            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente:\nUsuario: " + usuario + "\nCorreo: " + correo);
            
            new VentanaPrincipal();
        	
        	
        });
        
        
        btnCancelar.addActionListener((e) -> {
        	dispose();
        	new VentanaPrincipal();
        });
     
        
        getContentPane().add(pSur, BorderLayout.SOUTH);
        
        pSur.add(btnAceptar);
        pSur.add(btnCancelar);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
		new Registro();
	}
}
