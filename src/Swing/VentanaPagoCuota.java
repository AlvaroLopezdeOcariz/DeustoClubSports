package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class VentanaPagoCuota extends JFrame {
    
	private static final long serialVersionUID = 1L;
    private JPanel pCentro, pSur;
    private JLabel lblTipoSocio, lblFrecuencia, lblMetodoPago, lblTotalSimple;
    private JComboBox<String> cbTipoSocio, cbFrecuencia, cbMetodoPago;
    private JRadioButton rbConEntrada, rbSinEntrada;
    private ButtonGroup bgEntrada;
    private JButton btnPagar, btnCancelar;
    
    //Cuotas base tipo socio
    private final double CUOTA_TITULAR = 60.0;
    private final double CUOTA_FAMILIAR = 40.0;
    private final double CUOTA_ESTUDIANTE = 30.0;
    private final double CUOTA_SENIOR = 35.0;
    private final double CUOTA_ENTRADA = 150.0;
    
    public VentanaPagoCuota() {
        super();
        
        //Propiedades de la ventana
        setTitle("Pago de Cuota");
        setBounds(350, 150, 450, 300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //Creación de paneles
        pCentro = new JPanel(new GridLayout(5,2,10,10));
        pSur = new JPanel();

        getContentPane().add(pCentro, BorderLayout.CENTER);
        getContentPane().add(pSur, BorderLayout.SOUTH);
        
      //Creación de componentes
        //Tipo de socio
        lblTipoSocio = new JLabel("Tipo socio:", JLabel.RIGHT);
        cbTipoSocio = new JComboBox<>(new String[]{
            "Titular (60€/mes)",
            "Familiar (40€/mes)",
            "Estudiante (30€/mes)",
            "Senior +65 (35€/mes)"
        });

        //Frecuencia pago
        lblFrecuencia = new JLabel("Frecuencia:", JLabel.RIGHT);
        cbFrecuencia = new JComboBox<>(new String[]{
            "Mensual",
            "Trimestral (-5%)",
            "Semestral (-10%)",
            "Anual (-15%)"
        });

        
        JLabel lblEntrada = new JLabel("Cuota de entrada:", JLabel.RIGHT);
        JPanel pEntrada = new JPanel();
        rbConEntrada = new JRadioButton("Sí (150€)");
        rbSinEntrada = new JRadioButton("No", true);
        bgEntrada = new ButtonGroup();
        
        //Añadir la cuota d eentrada
        bgEntrada.add(rbConEntrada);
        bgEntrada.add(rbSinEntrada);
        pEntrada.add(rbConEntrada);
        pEntrada.add(rbSinEntrada);

        //Metodo
        lblMetodoPago = new JLabel("Método pago:", JLabel.RIGHT);
        cbMetodoPago = new JComboBox<>(new String[]{
            "Tarjeta",
            "Transferencia",
            "Domiciliación"
        });

        //Total
        lblTotalSimple = new JLabel("Total: 0.00€", JLabel.CENTER);
        lblTotalSimple.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotalSimple.setForeground(new Color(0, 153, 0));
        pSur.add(lblTotalSimple);

        //Botones 
        btnPagar = new JButton("Pagar");
        btnCancelar = new JButton("Cancelar");
        pSur.add(btnPagar);
        pSur.add(btnCancelar);

        //Añadir componentes al panel
        pCentro.add(lblTipoSocio); 
        pCentro.add(cbTipoSocio);
        pCentro.add(lblFrecuencia); 
        pCentro.add(cbFrecuencia);
        pCentro.add(lblEntrada); 
        pCentro.add(pEntrada);
        pCentro.add(lblMetodoPago); 
        pCentro.add(cbMetodoPago);

        
        cbTipoSocio.addActionListener(e -> actualizarTotalSimple());
        cbFrecuencia.addActionListener(e -> actualizarTotalSimple());
        rbConEntrada.addActionListener(e -> actualizarTotalSimple());
        rbSinEntrada.addActionListener(e -> actualizarTotalSimple());

       
        btnPagar.addActionListener(e -> {
            HiloGeneral hiloPago = new HiloGeneral(this, "Procesando Pago", "Verificando datos bancarios...");
            
            new Thread(() -> {
                hiloPago.iniciar(); 
                
                //cuando termina
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                        "Pago realizado correctamente\nTotal pagado: " + 
                        lblTotalSimple.getText().replace("Total:", ""),
                        "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new VentanaPrincipal();
                });
            }).start();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
            new VentanaInicio();
        });


        btnCancelar.addActionListener(e -> {
            dispose();
            new VentanaInicio();
        });

        
        actualizarTotalSimple();
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                    VentanaPagoCuota.this,
                    "¿Seguro que quieres cancelar el pago?",
                    "Confirmar cancelación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    dispose();
                    new VentanaInicio();
                }
                
            }
        });

        setVisible(true);
    }

    private void actualizarTotalSimple() {
        double cuotaMensual = 0;
        int tipo = cbTipoSocio.getSelectedIndex();
        switch (tipo) {
            case 0: cuotaMensual = CUOTA_TITULAR; break;
            case 1: cuotaMensual = CUOTA_FAMILIAR; break;
            case 2: cuotaMensual = CUOTA_ESTUDIANTE; break;
            case 3: cuotaMensual = CUOTA_SENIOR; break;
        }
        double cuotaPeriodica = cuotaMensual;
        int frecuencia = cbFrecuencia.getSelectedIndex();
        int meses = 1;
        double descuento = 0;
        switch (frecuencia) {
            case 0: meses = 1; descuento = 0; break;
            case 1: meses = 3; descuento = 0.05; break;
            case 2: meses = 6; descuento = 0.10; break;
            case 3: meses = 12; descuento = 0.15; break;
        }
        cuotaPeriodica = cuotaMensual * meses * (1-descuento);
        double cuotaEntrada;
        if (rbConEntrada.isSelected()) {
            cuotaEntrada = CUOTA_ENTRADA;
        } else {
            cuotaEntrada = 0;
        }

        double total = cuotaPeriodica + cuotaEntrada;
        lblTotalSimple.setText("Total: " + String.format("%.2f€", total));
    }

    public static void main(String[] args) {
        new VentanaPagoCuota();
    }
}
