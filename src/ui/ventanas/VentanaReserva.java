package ui.ventanas;

import dominio.*;
import BD.BD;
import hilos.HiloGeneral;
import ui.modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class VentanaReserva extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Instalacion instalacion;
    private JComboBox<String> comboDia;
    private JComboBox<String> comboHoraInicio, comboHoraFin;
    private JSpinner spAsistentes;
    private JLabel lblPrecio, lblResumen;

    public VentanaReserva(Instalacion inst) {
        this.instalacion = inst;

        setTitle("Reserva de " + inst.getNombre());
        setBounds(350, 150, 700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pContent = new JPanel(new BorderLayout(10, 10));
        pContent.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        pContent.setBackground(Color.WHITE);
        setContentPane(pContent);

        // 1. Panel superior con título
        JPanel pNorte = new JPanel(new BorderLayout());
        pNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Reserva de instalación", SwingConstants.LEFT);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 20f));
        lblTitulo.setForeground(new Color(0, 35, 102));

        JLabel lblInstalacion = new JLabel(inst.getNombre(), SwingConstants.RIGHT);
        lblInstalacion.setFont(lblInstalacion.getFont().deriveFont(Font.BOLD, 16f));
        lblInstalacion.setForeground(new Color(0, 35, 102));

        pNorte.add(lblTitulo, BorderLayout.WEST);
        pNorte.add(lblInstalacion, BorderLayout.EAST);
        add(pNorte, BorderLayout.NORTH);

        // 2. Panel central -> izquierda info Instalación, derecha formulario
        JPanel pCentro = new JPanel(new GridLayout(1, 2, 10, 0));
        pCentro.setBackground(Color.WHITE);
        pCentro.add(crearPanelInfoInstalacion());
        pCentro.add(crearPanelFormulario());
        add(pCentro, BorderLayout.CENTER);

        // 3. Panel inferior -> resumen y botones
        pContent.add(crearPanelInferior(), BorderLayout.SOUTH);

        rellenarHoras();
        recalcularPrecio();

        setLocationRelativeTo(null);
        setVisible(true);

    }

    // Creo el panel con la información de la instalación
    private JPanel crearPanelInfoInstalacion() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBackground(Color.WHITE);

        // Imagen
        JLabel lblImagen = new JLabel("", SwingConstants.CENTER);
        ImageIcon imagen = cargarIconoSimple(instalacion.getRutaImagen(), 280, 160);
        if (imagen != null) {
            lblImagen.setIcon(imagen);
        } else {
            lblImagen.setText("Sin imagen");
        }
        p.add(lblImagen, BorderLayout.NORTH);

        // Datos
        JPanel pDatos = new JPanel(new GridLayout(4, 1, 5, 5));
        pDatos.setBackground(Color.WHITE);

        pDatos.add(new JLabel("Deporte: " + instalacion.getDeporte()));
        pDatos.add(new JLabel("Medidas: " + instalacion.getMedidas()));
        pDatos.add(new JLabel("Apertura: " + instalacion.getApertura()));
        pDatos.add(new JLabel("Cierre: " + instalacion.getCierre()));
        pDatos.add(new JLabel("Precio/hora: " + String.format("%.2f €", instalacion.getPrecioHora())));

        p.add(pDatos, BorderLayout.CENTER);

        return p;
    }

    // Funcion para cargar el icono
    private static ImageIcon cargarIconoSimple(String ruta, int w, int h) {
        // Comprueba ruta válida
        if (ruta == null || ruta.isEmpty()) {
            return null;
        }

        // Intenta cargar la imagen
        ImageIcon original = new ImageIcon(ruta);

        // Si no se carga correctamente, devuelve null
        if (original.getIconWidth() <= 0) {
            return null;
        }

        Image escalada = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }

    private JPanel crearPanelFormulario() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(new GridLayout(5, 2, 5, 5));

        p.add(new JLabel("Día:"));
        comboDia = new JComboBox<>();
        comboDia.setBackground(Color.WHITE);

        comboDia.addItem("Hoy");
        comboDia.addItem("Mañana");

        p.add(comboDia);

        p.add(new JLabel("Hora inicio:"));
        comboHoraInicio = new JComboBox<>();
        comboHoraInicio.setBackground(Color.WHITE);
        comboHoraInicio.addActionListener(e -> recalcularPrecio());

        p.add(comboHoraInicio);

        p.add(new JLabel("Hora fin:"));
        comboHoraFin = new JComboBox<>();
        comboHoraFin.setBackground(Color.WHITE);
        comboHoraFin.addActionListener(e -> recalcularPrecio());
        p.add(comboHoraFin);

        p.add(new JLabel("Asistentes:"));
        spAsistentes = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        p.add(spAsistentes);

        p.add(new JLabel("Precio estimado:"));
        lblPrecio = new JLabel("0.00 €");
        p.add(lblPrecio);

        // Listeners para actualizar horas y precio
        ActionListener recalc = e -> recalcularPrecio();
        comboHoraInicio.addActionListener(recalc);
        comboHoraFin.addActionListener(recalc);

        return p;
    }

    private JPanel crearPanelInferior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        lblResumen = new JLabel("Selecciona día y horas para ver el resumen.");
        lblResumen.setForeground(new Color(0, 35, 102));
        p.add(lblResumen, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setBackground(Color.WHITE);

        JButton btnConfirmar = new JButton("Confirmar reserva");
        btnConfirmar.setBackground(new Color(0, 35, 102));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnConfirmar.setFocusPainted(false);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(178, 34, 34)); // Firebrick
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);

        btnConfirmar.addActionListener(e -> confirmarReserva());
        btnCancelar.addActionListener(e -> {
            dispose();
            new VentanaInstalaciones().setVisible(true);
        });

        botones.add(btnConfirmar);
        botones.add(btnCancelar);
        p.add(botones, BorderLayout.EAST);

        return p;
    }

    private void recalcularPrecio() {
        try {
            // "12:00" -> "12" -> 12
            String inicioStr = comboHoraInicio.getSelectedItem().toString();
            String finStr = comboHoraFin.getSelectedItem().toString();

            int inicio = Integer.parseInt(inicioStr.substring(0, 2));
            int fin = Integer.parseInt(finStr.substring(0, 2));

            if (fin <= inicio) {
                lblPrecio.setText("0 €");
                return;
            }

            int horas = fin - inicio;
            double precio = horas * instalacion.getPrecioHora();

            lblPrecio.setText(precio + " €");

        } catch (Exception e) {
            lblPrecio.setText("0 €");
        }
    }

    private void confirmarReserva() {
        try {
            String inicioStr = comboHoraInicio.getSelectedItem().toString();
            String finStr = comboHoraFin.getSelectedItem().toString();

            int inicio = Integer.parseInt(inicioStr.substring(0, 2));
            int fin = Integer.parseInt(finStr.substring(0, 2));

            if (fin <= inicio) {
                JOptionPane.showMessageDialog(this, "La hora final debe ser mayor que la inicial.");
                return;
            }

            int horas = fin - inicio;
            double precio = horas * instalacion.getPrecioHora();

            // --- FECHA REAL (Hoy / Mañana) ---
            LocalDate fecha = LocalDate.now();
            if ("Mañana".equals(comboDia.getSelectedItem())) {
                fecha = fecha.plusDays(1);
            }

            int asistentes = (Integer) spAsistentes.getValue();

            // --- GUARDAR EN BD ---
            BD.insertarReserva(
                    instalacion.getNombre(),
                    fecha.toString(),
                    inicioStr,
                    finStr,
                    asistentes,
                    precio);

            String mensaje = "Reserva realizada:\n" +
                    "Instalación: " + instalacion.getNombre() + "\n" +
                    "Día: " + comboDia.getSelectedItem() + " (" + fecha + ")\n" +
                    "Inicio: " + inicioStr + "\n" +
                    "Fin: " + finStr + "\n" +
                    "Horas: " + horas + "\n" +
                    "Precio: " + precio + " €\n" +
                    "Asistentes: " + asistentes;

            HiloGeneral hiloReserva = new HiloGeneral(this, "Procesando Reserva", "Verificando disponibilidad...");

            new Thread(() -> {
                hiloReserva.iniciar();

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, mensaje,
                            "Reserva Confirmada", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new VentanaInstalaciones().setVisible(true);
                });
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al confirmar la reserva.");
        }
    }

    private void rellenarHoras() {
        try {
            String ap = instalacion.getApertura(); // "07:00"
            String ci = instalacion.getCierre(); // "22:00"

            int horaApertura = Integer.parseInt(ap.substring(0, 2)); // 07
            int horaCierre = Integer.parseInt(ci.substring(0, 2)); // 22

            comboHoraInicio.removeAllItems();
            comboHoraFin.removeAllItems();

            // Horas de inicio
            for (int h = horaApertura; h < horaCierre; h++) {
                String horaFormateada = String.format("%02d:00", h);
                comboHoraInicio.addItem(horaFormateada);
            }

            // Horas de fin
            for (int h = horaApertura + 1; h <= horaCierre; h++) {
                String horaFormateada = String.format("%02d:00", h);
                comboHoraFin.addItem(horaFormateada);
            }

        } catch (Exception e) {
            // Horario alternativo si falla
            comboHoraInicio.removeAllItems();
            comboHoraFin.removeAllItems();

            for (int h = 8; h < 22; h++) {
                comboHoraInicio.addItem(String.format("%02d:00", h));
            }
            for (int h = 9; h <= 22; h++) {
                comboHoraFin.addItem(String.format("%02d:00", h));
            }
        }
    }

}
