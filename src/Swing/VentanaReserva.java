package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class VentanaReserva extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboDia;
    private JComboBox<String> comboHoraInicio, comboHoraFin;
    private JTextField txtAsistentes;
    private JLabel lblPrecio;
    private JButton btnConfirmar, btnCancelar;

    private int precioPorHora = 25;

    public VentanaReserva() {
        setTitle("Reserva");
        setBounds(350, 150, 450, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contenidos = new JPanel();
        contenidos.setLayout(new BoxLayout(contenidos, BoxLayout.Y_AXIS));
        contenidos.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Día
        JPanel pDia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pDia.add(new JLabel("Día:"));
        comboDia = new JComboBox<>();
        LocalDate hoy = LocalDate.now();
        for (int i=0; i<5; i++)
            comboDia.addItem(hoy.plusDays(i).toString());
        pDia.add(comboDia);
        contenidos.add(pDia);

        // Horas
        JPanel pHoras = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pHoras.add(new JLabel("Hora inicio:"));
        comboHoraInicio = new JComboBox<>(new String[]{"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00"});
        pHoras.add(comboHoraInicio);
        pHoras.add(new JLabel("Hora fin:"));
        comboHoraFin = new JComboBox<>(new String[]{"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"});
        pHoras.add(comboHoraFin);
        contenidos.add(pHoras);

        //Asistentes
        JPanel pAsistentes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pAsistentes.add(new JLabel("Asistentes (nombres separados por coma):"));
        txtAsistentes = new JTextField(20);
        pAsistentes.add(txtAsistentes);
        contenidos.add(pAsistentes);

        //Precio dinámico
        JPanel pPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pPrecio.add(new JLabel("Precio total:"));
        lblPrecio = new JLabel("0 €");
        pPrecio.add(lblPrecio);
        contenidos.add(pPrecio);

        //Botones
        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");
        pBotones.add(btnConfirmar);
        pBotones.add(btnCancelar);
        contenidos.add(pBotones);

        add(contenidos);

        //el precio según horas
        ActionListener recalculaPrecio = evt -> {
            int hIni = comboHoraInicio.getSelectedIndex()+8;
            int hFin = comboHoraFin.getSelectedIndex()+9;
            int horas = hFin - hIni;
            if (horas < 1) horas = 0;
            lblPrecio.setText((horas * precioPorHora) + " €");
        };
        comboHoraInicio.addActionListener(recalculaPrecio);
        comboHoraFin.addActionListener(recalculaPrecio);

        //Confirmar reserva
        btnConfirmar.addActionListener(e->{
            String info = "Reserva realizada:\nDía: " + comboDia.getSelectedItem() +
                "\nHoras: " + comboHoraInicio.getSelectedItem() + " a " + comboHoraFin.getSelectedItem() +
                "\nPrecio: " + lblPrecio.getText() +
                "\nAsistentes: " + txtAsistentes.getText();
            JOptionPane.showMessageDialog(this, info, "Reserva", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnCancelar.addActionListener((e) -> {
        	dispose();
        	new VentanaInstalaciones();
        });
        
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaReserva().setVisible(true));
    }
}
