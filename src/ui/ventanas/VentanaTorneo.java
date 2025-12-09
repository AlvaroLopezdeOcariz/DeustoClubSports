package ui.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import BD.BD;

public class VentanaTorneo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pOeste, pSur, pCentro;
    private JTextField txtNombreEquipo;
    private JSpinner spNumeroJugadores;
    private JButton btnRegistrar, btnVolver, btnEliminar;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;

    public VentanaTorneo() {
        setTitle("Gestión de Torneo");
        setBounds(300, 200, 900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Registro
        pOeste = new JPanel(new GridLayout(6, 1, 10, 10));
        pOeste.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                new TitledBorder("Registrar Nuevo Equipo")));
        pOeste.setBackground(Color.WHITE);
        pOeste.setPreferredSize(new Dimension(300, 0));

        JLabel lblNombre = new JLabel("Nombre del Equipo:");
        txtNombreEquipo = new JTextField();

        JLabel lblJugadores = new JLabel("Número de Jugadores:");
        spNumeroJugadores = new JSpinner(new SpinnerNumberModel(5, 1, 25, 1));

        btnRegistrar = new JButton("REGISTRAR EQUIPO");
        btnRegistrar.setBackground(new Color(60, 179, 113));
        btnRegistrar.setForeground(Color.WHITE);

        pOeste.add(lblNombre);
        pOeste.add(txtNombreEquipo);
        pOeste.add(lblJugadores);
        pOeste.add(spNumeroJugadores);
        pOeste.add(new JLabel(""));
        pOeste.add(btnRegistrar);

        add(pOeste, BorderLayout.WEST);

        // Tabla
        pCentro = new JPanel(new BorderLayout());
        pCentro.setBorder(new EmptyBorder(20, 10, 20, 20));
        pCentro.setBackground(Color.WHITE);

        String[] columnas = { "ID", "Nombre Equipo", "Jugadores", "Fecha Registro" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEquipos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEquipos);

        pCentro.add(scrollTabla, BorderLayout.CENTER);
        add(pCentro, BorderLayout.CENTER);

        // Botones ---
        pSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pSur.setBackground(new Color(240, 240, 240));

        btnEliminar = new JButton("ELIMINAR SELECCIONADO");
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.WHITE);

        btnVolver = new JButton("VOLVER AL MENÚ");

        pSur.add(btnEliminar);
        pSur.add(btnVolver);

        add(pSur, BorderLayout.SOUTH);

        cargarTabla();

        // Listeners
        btnVolver.addActionListener(e -> {
            dispose();
            new VentanaPrincipal();
        });

        btnRegistrar.addActionListener(e -> {
            registrarEquipo();
        });

        btnEliminar.addActionListener(e -> {
            eliminarEquipo();
        });

        setVisible(true);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<Object[]> equipos = BD.obtenerEquiposTorneo();
        for (Object[] fila : equipos) {
            modeloTabla.addRow(fila);
        }
    }

    private void registrarEquipo() {
        String nombre = txtNombreEquipo.getText();
        int jugadores = (int) spNumeroJugadores.getValue();

        if (nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un nombre para el equipo.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        BD.insertarEquipoTorneo(nombre, jugadores, fecha);

        JOptionPane.showMessageDialog(this, "Equipo registrado correctamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        txtNombreEquipo.setText("");
        spNumeroJugadores.setValue(5);
        cargarTabla();
    }

    private void eliminarEquipo() {
        int filaSeleccionada = tablaEquipos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un equipo de la tabla para eliminar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres eliminar este equipo?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            BD.eliminarEquipoTorneo(id);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Equipo eliminado.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
