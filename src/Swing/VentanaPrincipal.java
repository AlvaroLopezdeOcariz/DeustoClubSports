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

public class VentanaPrincipal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pNorte, pCentro;
	private JLabel lblPrueba, lblLogo;
	private JButton btnInciarSesion, btnResgitrar;
	
	public VentanaPrincipal() {
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
		//lblPrueba = new JLabel("Bienvenidos");
		btnInciarSesion = new JButton("Iniciar Sesión");
		btnResgitrar = new JButton("Resgistrarse");
		
		//Añadir paneles
		getContentPane().add(pNorte, BorderLayout.NORTH);
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
		    	
		    String usuarioValido = "Jenny";
            String passwordValido = "Lover";
            String usuario = campoUsuario.getText();
            String password = new String(campoPassword.getPassword());

            if (resultado == JOptionPane.OK_OPTION) {
                if (usuarioValido.equals(usuario) && passwordValido.equals(password)) {
                    JOptionPane.showMessageDialog(this, "Se ha iniciado sesión correctamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
		    
			
		});
		
		
		btnResgitrar.addActionListener((e)-> {
			dispose(); 
			new Registro();
		});
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaPrincipal();
	}
}