package ui.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import BD.BD;

public class VentanaTorneo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pCentro, pSur;
    private JTextField txtNombreEquipo;
    private JSpinner spNumeroJugadores;
    private JButton btnRegistrar, btnVolver;

    public VentanaTorneo() {
        setTitle("Registro de Torneo");
        setBounds(400, 200, 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Centro
        pCentro = new JPanel(new GridLayout(3, 2, 20, 20));
        pCentro.setBorder(new EmptyBorder(50, 50, 50, 50));
        pCentro.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel("Nombre del Equipo:");
        txtNombreEquipo = new JTextField();

        JLabel lblJugadores = new JLabel("Número de Jugadores:");
        spNumeroJugadores = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));

        pCentro.add(lblNombre);
        pCentro.add(txtNombreEquipo);
        pCentro.add(lblJugadores);
        pCentro.add(spNumeroJugadores);

        // Panel Sur
        pSur = new JPanel();
        btnRegistrar = new JButton("REGISTRAR EQUIPO");
        btnVolver = new JButton("VOLVER");

        pSur.add(btnVolver);
        pSur.add(btnRegistrar);

        add(pCentro, BorderLayout.CENTER);
        add(pSur, BorderLayout.SOUTH);

        // Listeners
        btnVolver.addActionListener(e -> {
            dispose();
            new VentanaPrincipal();
        });

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombreEquipo.getText();
            int jugadores = (int) spNumeroJugadores.getValue();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un nombre para el equipo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            BD.insertarEquipoTorneo(nombre, jugadores, fecha);

            JOptionPane.showMessageDialog(this, "Equipo registrado correctamente en el torneo.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new VentanaPrincipal();
        });

        setVisible(true);
    }
}
