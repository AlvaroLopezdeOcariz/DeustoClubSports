package hilos;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HiloPublicidad extends Thread {
    private JLabel label;
    private JPanel panel;
    private String texto;
    private int x;
    private int anchoPanel;
    private volatile boolean running = true;

    public HiloPublicidad(JLabel label, JPanel panel, String texto) {
        this.label = label;
        this.panel = panel;
        this.texto = texto;
        this.x = 0;
        this.anchoPanel = panel.getWidth();
    }

    public void detener() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                anchoPanel = panel.getWidth(); // Actualizar por si se redimensiona
                if (anchoPanel == 0)
                    anchoPanel = 800; // Valor por defecto si aún no es visible

                x += 5; // Mover 5 píxeles a la derecha
                if (x > anchoPanel) {
                    x = -label.getWidth(); // Reiniciar cuando sale por la derecha
                }

                label.setBounds(x, label.getY(), label.getWidth(), label.getHeight());

                panel.repaint();

                Thread.sleep(50); // Velocidad de actualización
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
