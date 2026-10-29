import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaReservas {
    public Map<Integer, Object> db = new HashMap<>();
    public int contador = 1;

    // TÉCNICA UTILIZADA: RENAME METHOD
    // REFACTOR: el método "hacer" se renombró a "crearReserva", porque "hacer" no indicaba qué hace el método.

    // TÉCNICA UTILIZADA: REMOVE PARAMETER
    // REFACTOR: se eliminó el parámetro "boolean imprimir", que no se usaba en el cuerpo del método.
    public int crearReserva(String nombre, String email, boolean socio, Funcion f,
                            char[] filas, int[] numeros, String canal) {
        if (nombre == null || f == null) return -1;
        if (filas.length == 0 || filas.length != numeros.length) return -2;
        try {
            List<String> lista = new ArrayList<>();
            for (int i = 0; i < filas.length; i++) {
                f.ocupar(filas[i], numeros[i]);   // si falla a mitad, los anteriores ya quedaron ocupados
                lista.add("" + filas[i] + numeros[i]);
            }
            Cliente c = new Cliente(nombre, email, socio);
            // TÉCNICA UTILIZADA: ADD PARAMETER
            // REFACTOR: el parámetro "canal" se recibía pero se ignoraba. Se añadió como parámetro al
            // constructor de Reserva para que la reserva registre por qué canal se hizo.
            Reserva r = new Reserva(contador, c, f, lista, canal);
            r.precio = calcularPrecio(r, socio ? "socio" : "general");
            db.put(contador, r);
            contador++;
            return r.id;
        } catch (IllegalStateException e) {
            return -3;
        }
    }

    // Tipo de tarifa como String y if/else
    public double calcularPrecio(Reserva r, String tipo) {
        double base = r.funcion.precio * r.asientos.size();
        if (tipo.equals("general")) {
            return base;
        } else if (tipo.equals("socio")) {
            return descuentoDel15(base);
        }
        return base;
    }

    public double descuentoDel15(double p) {
        return p - p * 0.15;
    }

    public double descuentoDel25(double p) {   // duplicado, sin uso
        return p - p * 0.25;
    }

    // Downcast repetido en cada llamada
    public int cancelar(int id) {
        Reserva r = (Reserva) db.get(id);
        if (r == null) return -1;
        if (r.cancelada) return -2;
        for (String a : r.asientos) {
            r.funcion.ocupados.remove(a);
        }
        r.setCancelada(true);
        return 0;
    }

    public Object buscar(int id) {
        return db.get(id);
    }
}
