package Swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registro extends JFrame {
	private JPanel pSur,pCentro;
	private JLabel lblNombre, lblApellido, lblPassword, lblCorreo, lblErrorCorreo;
    private JTextField txtNombre, txtCorreo, txtApellido;
    private JPasswordField txtPassword;
    private JButton btnAceptar, btnCancelar;
    
    
    public Registro() {
    	super();
    	
    	//Propiedades de la ventana
    	setTitle("Registro de Usuario");
    	setBounds(400, 200, 800, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //Creación de los paneles
        pSur = new JPanel();
        pCentro = new JPanel(new GridLayout(6, 2, 10, 10));
        
        //Añadir los paneles a la ventana 
        getContentPane().add(pCentro, BorderLayout.CENTER);
        getContentPane().add(pSur, BorderLayout.SOUTH);
        
        //Creación de los componentes
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        
        lblNombre = new JLabel("Nombre: ", JLabel.CENTER);
        lblApellido = new JLabel("Apellido: ", JLabel.CENTER);
        lblPassword = new JLabel("Contraseña: ", JLabel.CENTER);
        lblCorreo = new JLabel("Correo electronico", JLabel.CENTER);
        JLabel lblVacio = new JLabel();
        lblErrorCorreo = new JLabel("", JLabel.RIGHT);
        
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtCorreo = new JTextField(20);
        txtPassword = new JPasswordField(20);
          
        //Añadimos los componentes a los paneles
        pCentro.add(lblNombre);
        pCentro.add(txtNombre);
        
        pCentro.add(lblApellido);
        pCentro.add(txtApellido);
        
        pCentro.add(lblPassword);
        pCentro.add(txtPassword);
        
        pCentro.add(lblCorreo);
        pCentro.add(txtCorreo);
        
        pCentro.add(lblErrorCorreo);
        
        pSur.add(btnAceptar);
        pSur.add(btnCancelar);
        
      //Listeners
        btnAceptar.addActionListener((e) -> { 
        	String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            
            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente:\nNombre: " + nombre + "\nCorreo: " + correo);
            
            dispose();
            new VentanaPrincipal();
        	
        	
        });
        
        
        btnCancelar.addActionListener((e) -> {
        	dispose();
        	new VentanaPrincipal();
        });
        
        
        txtCorreo.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				String email = txtCorreo.getText();
				if (email.contains("@")) {
					lblErrorCorreo.setText("");
				} else {
					lblErrorCorreo.setText("El correo electronico debe contener un @");
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
        
        
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
		new Registro();
	}
}
