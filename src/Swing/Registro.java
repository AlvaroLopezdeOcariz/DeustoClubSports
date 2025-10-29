package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registro extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pSur, pCentro, pFecha;
    private JLabel lblNombre, lblApellido, lblPassword, lblCorreo, lblErrorCorreo, lblFechaNacimiento;
    private JTextField txtNombre, txtCorreo, txtApellido;
    private JPasswordField txtPassword;
    private JButton btnAceptar, btnCancelar;
    private JComboBox<String> cbDia, cbMes, cbAnio;

    public Registro() {
        super();

        //Propiedades de la ventana
        setTitle("Registro de Usuario");
        setBounds(400, 200, 800, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Creación de paneles
        pSur = new JPanel();
        //fila extra se usará para el mensaje de error
        pCentro = new JPanel(new GridLayout(7, 2, 10, 10));
        pFecha = new JPanel(new GridLayout(1, 3, 5, 5));

        getContentPane().add(pCentro, BorderLayout.CENTER);
        getContentPane().add(pSur, BorderLayout.SOUTH);

        //Creación de componentes
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBackground(Color.GREEN);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Color.RED);

        lblNombre = new JLabel("Nombre:", JLabel.CENTER);
        lblApellido = new JLabel("Apellido:", JLabel.CENTER);
        lblPassword = new JLabel("Contraseña:", JLabel.CENTER);
        lblCorreo = new JLabel("Correo electrónico:", JLabel.CENTER);
        lblErrorCorreo = new JLabel("", JLabel.LEFT);
        lblErrorCorreo.setForeground(Color.RED); 

        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtCorreo = new JTextField(20);
        txtPassword = new JPasswordField(20);
        
        lblFechaNacimiento = new JLabel("Fecha de nacimiento: ", JLabel.CENTER);
        
        cbDia = new JComboBox<String>();
        cbMes = new JComboBox<String>();
        cbAnio = new JComboBox<String>();
     
        for (int i = 1; i <= 31; i++) {
            cbDia.addItem(String.valueOf(i));
        }
        
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        
        for (String m : meses) {
			cbMes.addItem(m);
		}
        
        for (int i = 2008; i >= 1930; i--) {
        	cbAnio.addItem(String.valueOf(i));
			
		}
        
        //Añadir los componentes al panel 
        pCentro.add(lblNombre);
        pCentro.add(txtNombre);

        pCentro.add(lblApellido);
        pCentro.add(txtApellido);

        pCentro.add(lblPassword);
        pCentro.add(txtPassword);

        pCentro.add(lblCorreo);
        pCentro.add(txtCorreo);

       
        pCentro.add(new JLabel("")); 
        pCentro.add(lblErrorCorreo);  

       
        pSur.add(btnAceptar);
        pSur.add(btnCancelar);
        
        pCentro.add(lblFechaNacimiento);
        pCentro.add(pFecha);
        pFecha.add(cbDia);
        pFecha.add(cbMes);
        pFecha.add(cbAnio);
        

        //Listeners
        btnAceptar.addActionListener((e) -> {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String apellido = txtApellido.getText();
            String contrasena = txtPassword.getText();
            
            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JOptionPane.showMessageDialog(null,
                "Usuario registrado correctamente:\nNombre: " + nombre + "\nCorreo: " + correo);
            
            

            dispose();
            new VentanaPrincipal();
        });

        btnCancelar.addActionListener((e) -> {
            dispose();
            new VentanaInicio();
        });

       
        txtCorreo.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                String email = txtCorreo.getText();
                if (email.contains("@")) {
                    lblErrorCorreo.setText("");
                } else {
                    lblErrorCorreo.setText("El correo electrónico debe contener un '@'");
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                //Nada
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Registro();
    }
}

