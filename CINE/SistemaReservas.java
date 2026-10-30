import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaReservas {
    public Map<Integer, Reserva> dbReservas = new HashMap<>();
    public int contadorReservas = 1;

    public int reservarAsientos(Cliente cliente, Funcion funcion, char[] filas, int[] numeros) {
        if (filas.length == 0 || filas.length != numeros.length) {
            throw new RuntimeException("El numero de filas o");
        }

        for(int i = 0; i < filas.length; i++) {
            if( ! funcion.disponibilidadAsiento(filas[i], numeros[i]) ) {
                throw new RuntimeException("No se han encontrado los asientos disponibles");
            }
        }

        List<String> lista = new ArrayList<>();

        for (int i = 0; i < filas.length; i++) {
            funcion.ocuparAsiento(filas[i], numeros[i]);
            lista.add("" + filas[i] + numeros[i]);
        }

        Reserva reserva = new Reserva(contadorReservas, cliente, funcion, lista, false);
        reserva.precio = calcularPrecio(reserva, cliente.socio ? "socio" : "general");
        dbReservas.put(contadorReservas, reserva);
        contadorReservas++;

        return reserva.id;
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
    public void cancelar(int id) throws IllegalStateException {
        Reserva reserva = dbReservas.get(id);
        if (reserva == null) {
            throw new IllegalStateException("No existe la reserva con id: " + id);
        }
        if (reserva.cancelada) {
            throw new IllegalStateException("La reserva con id " + id + " ya ha sido cancelada");
        }

        for (String asiento : reserva.asientos) {
            char fila = asiento.charAt(0);
            int numero = Integer.parseInt(asiento.substring(1));

            reserva.funcion.desocuparAsiento(fila, numero);
        }
    }

    public Object buscar(int id) {
        return dbReservas.get(id);
    }
}
