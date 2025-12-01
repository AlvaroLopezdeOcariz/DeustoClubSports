package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import BD.BD;

public class VentanaInicio extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pNorte, pCentro, pNorteIzq, pSuperior;
	private JLabel lblPrueba, lblLogo;
	private JButton btnInciarSesion, btnResgitrar, btnCafeteria;
	
	public VentanaInicio() {
		super();
		
		//Especificaciones de la ventana
		setBounds(400, 200, 800, 500);
		setTitle("DeustoClubSports");
		

		
		
		ImageIcon img;
		java.net.URL iconURL = getClass().getResource("/img/logo_DeustoClubSports.png");
		if (iconURL != null) {
			img = new ImageIcon(iconURL);
		} else {
			img = new ImageIcon("img/logo_DeustoClubSports.png");
		}
		setIconImage(img.getImage());
		
		//Instanciar		
		pNorte = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pNorte.setBackground(Color.WHITE);
		pCentro = new JPanel(new BorderLayout());
		pCentro.setBackground(Color.WHITE);
		pNorteIzq = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pNorteIzq.setBackground(Color.WHITE);
		//lblPrueba = new JLabel("Bienvenidos");
		btnInciarSesion = new JButton("Iniciar Sesión");
		btnResgitrar = new JButton("Resgistrarse");
		btnCafeteria = new JButton("Cafetería");
		
		pSuperior = new JPanel(new BorderLayout());
		pSuperior.setBackground(Color.WHITE);
		
		pSuperior.add(pNorte, BorderLayout.EAST);
		pSuperior.add(pNorteIzq, BorderLayout.WEST);
		
		//Añadir paneles
		getContentPane().add(pSuperior, BorderLayout.NORTH);
	    getContentPane().add(pCentro, BorderLayout.CENTER);
	    
		//logo
		ImageIcon logoIcon = null;
		java.net.URL logoURL = getClass().getResource("/img/logo_DeustoClubSports.png");
		if (logoURL != null) {
			logoIcon = new ImageIcon(logoURL);
		} else {
			logoIcon = new ImageIcon("img/logo_DeustoClubSports.png");
		}
		
		Image img1 = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		lblLogo = new JLabel(new ImageIcon(img1));
		lblLogo.setHorizontalAlignment(JLabel.CENTER);
		pCentro.add(lblLogo, BorderLayout.CENTER);
		
		//Añadir componentes
		//pNorte.add(lblPrueba);
		pNorte.add(btnInciarSesion);
		pNorte.add(btnResgitrar);
		pNorteIzq.add(btnCafeteria);
		
		
		//listeners
		btnInciarSesion.addActionListener((e) -> { 
			
			//Temporal porque no tenemos base de datos
			JPanel panelInicio = new JPanel();
		    JLabel labelUsuario = new JLabel("Usuario:");
		    JTextField campoUsuario = new JTextField(10);
		    JLabel labelPassword = new JLabel("Contraseña:");
		    JPasswordField campoPassword = new JPasswordField(10);
		    
		    panelInicio.add(labelUsuario);
		    panelInicio.add(campoUsuario);
		    panelInicio.add(labelPassword);
		    panelInicio.add(campoPassword);
		    
		    int resultado = JOptionPane.showConfirmDialog(null, panelInicio,
		            "Iniciar Sesión",
		            JOptionPane.OK_CANCEL_OPTION,
		            JOptionPane.PLAIN_MESSAGE
		        );
		    	
		  
            String usuario = campoUsuario.getText();
            String password = new String(campoPassword.getPassword());
            
            String usuarioValido = BD.obtenerUsuario(usuario);
            String passwordValido = BD.ContraseñaUsuario(password);
            boolean esAdmin = BD.esAdministrador(usuario);

            	if (resultado == JOptionPane.OK_OPTION) {
            	if(esAdmin) {
            							if (usuarioValido.equals(usuario) && passwordValido.equals(password)) {
						JOptionPane.showMessageDialog(this, "Se ha iniciado sesión correctamente como administrador");
						dispose();
						new VentanaAdministrador();
					} 
            	}	
            	else if (usuarioValido.equals(usuario) && passwordValido.equals(password)) {
                    JOptionPane.showMessageDialog(this, "Se ha iniciado sesión correctamente");
                    dispose();
                    new VentanaPrincipal();
                } else if (usuario.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            
		    
			
		});
		
		
		btnResgitrar.addActionListener((e)-> {
			dispose(); 
			new Registro();
		});
		
		btnCafeteria.addActionListener((e)-> {
			dispose(); 
			new VentanaCafeteria().setVisible(true);
		});
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		BD baseDatos= new BD();
    	baseDatos.InicializarBD();
    	baseDatos.insertarProductos();
    	baseDatos.insertarUsuarios();
		new VentanaInicio();
	}
}