package recursividad;

import java.util.ArrayList;
import java.util.List;

public class GeneradorTorneo {

    public static List<String> generarEmparejamientos(List<String> equipos) {
        List<String> cruces = new ArrayList<>();
        generarRecursivo(equipos, cruces);
        return cruces;
    }

    private static void generarRecursivo(List<String> equipos, List<String> cruces) {
        if (equipos.isEmpty()) {
            return;
        }

        if (equipos.size() == 1) {
            cruces.add(equipos.get(0) + " pasa automáticamente (BYE)");
            return;
        }

        String equipo1 = equipos.get(0);
        String equipo2 = equipos.get(equipos.size() - 1);

        cruces.add(equipo1 + " vs " + equipo2);

        List<String> restantes = new ArrayList<>(equipos.subList(1, equipos.size() - 1));
        generarRecursivo(restantes, cruces);
    }
}
