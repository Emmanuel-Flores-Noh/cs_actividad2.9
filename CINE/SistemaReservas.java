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
            // TÉCNICA UTILIZADA: Replace parameter with explicit method.
            // REFACTOR: El terceario ahora apunta a dos funciones dedicadas en lugar de una misma función con otro condiconal dentro
            r.precio = socio ? calcularPrecioSocio(r) : calcularPrecioGeneral(r);

            db.put(contador, r);
            contador++;
            return r.id;
        } catch (IllegalStateException e) {
            return -3;
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
    /*
        Técnica utilizada: Replace parameter with explicit method.
        REFACTOR: El parametro de tipo de socio que se usaba en un if condicional, ahora se reemplazo con funciones dedicadas.
        Calcular precio se dividio en 2 funciones, una para cada tipo de cliente.
    */
    public double calcularPrecioGeneral(Reserva reserva){
        return reserva.funcion.precio * reserva.asientos.size();
    }
    public double calcularPrecioSocio(Reserva reserva){
        double DESCUENTO_SOCIO = 0.15;
        double subtotal = reserva.funcion.precio * reserva.asientos.size();
        return descuento(subtotal, DESCUENTO_SOCIO);
    }

    /*
        Técnica utilizada: Parameterize method
        REFACTOR: Se agrego un parametro extra que permite eliminar misma función con dato diferente.
        Ahora el parametro es el porcentaje de descuento.
    */
    public double descuento(double precio, double porcentaje) {
        return precio - precio * porcentaje;
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
