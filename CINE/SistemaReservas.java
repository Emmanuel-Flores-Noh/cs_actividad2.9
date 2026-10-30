import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaReservas {
    private static final int CONTADOR_INICIAL = 1;
    private static final double DESCUENTO_SOCIO = 0.15;
    private static final int INDICE_FILA_ASIENTO = 0;
    private static final int INDICE_NUMERO_ASIENTO = 1;

    private final Map<Integer, Reserva> dbReservas = new HashMap<>();
    private int contadorReservas = CONTADOR_INICIAL;

    public int getContadorReservas() {
        return contadorReservas;
    }

    // TÉCNICA UTILIZADA: RENAME METHOD
    // REFACTOR: el método "hacer" se renombró a "crearReserva", porque "hacer" no indicaba qué hace el método.

    // TÉCNICA UTILIZADA: REMOVE PARAMETER
    // REFACTOR: se eliminó el parámetro "boolean imprimir", que no se usaba en el cuerpo del método.

    // TÉCNICA UTILIZADA: PRESERVE WHOLE OBJECT
    // REFACTOR: Se reemplazaron los parámetros nombre, email y socio por un objeto Cliente,
    // que agrupa esos datos y se pasa completo al método.

    // TÉCNICA UTILIZADA: INTRODUCE PARAMETER OBJECT
    // REFACTOR: Se reemplazaron los arrays paralelos de filas y números por una lista de objetos Asiento,
    // que agrupa ambos datos y evita depender de que coincidan por posición.
    public int crearReserva(Cliente cliente, Funcion funcion, List<Asiento> asientos) {
        if (asientos.isEmpty()) {
            throw new RuntimeException("La reserva debe incluir al menos un asiento");
        }

        for (Asiento asiento : asientos) {
            if (!funcion.disponibilidadAsiento(asiento.getFila(), asiento.getNumero())) {
                throw new RuntimeException("No se han encontrado los asientos disponibles");
            }
        }

        List<String> codigosAsientos = new ArrayList<>();

        for (Asiento asiento : asientos) {
            funcion.ocuparAsiento(asiento.getFila(), asiento.getNumero());
            codigosAsientos.add(asiento.codigo());
        }

        Reserva reserva = new Reserva(contadorReservas, cliente, funcion, codigosAsientos, false);

        // TÉCNICA UTILIZADA: ADD PARAMETER
        // REFACTOR: el parámetro "canal" se recibía pero se ignoraba. Se añadió como parámetro al
        // constructor de Reserva para que la reserva registre por qué canal se hizo.
        // TÉCNICA UTILIZADA: Replace parameter with explicit method.
        // REFACTOR: El terceario ahora apunta a dos funciones dedicadas en lugar de una misma función con otro condiconal dentro
        reserva.setPrecio(cliente.isSocio() ? calcularPrecioSocio(reserva) : calcularPrecioGeneral(reserva));
        dbReservas.put(contadorReservas, reserva);
        contadorReservas++;

        return reserva.getId();
    }

    

    // Tipo de tarifa como String y if/else
    /*
        Técnica utilizada: Replace parameter with explicit method.
        REFACTOR: El parametro de tipo de socio que se usaba en un if condicional, ahora se reemplazo con funciones dedicadas.
        Calcular precio se dividio en 2 funciones, una para cada tipo de cliente.
    */

    // TÉCNICA UTILIZADA: REPLACE PARAMETER WITH METHOD 
    // REFACTOR: Se eliminó el parámetro del precio y se obtiene directamente de reserva.funcion.precio.
    public double calcularPrecioGeneral(Reserva reserva){
        return reserva.getFuncion().getPrecio() * reserva.getAsientos().size();
    }
    public double calcularPrecioSocio(Reserva reserva){
        double subtotal = reserva.getFuncion().getPrecio() * reserva.getAsientos().size();
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
    public void cancelar(int idReserva) throws IllegalStateException {
        Reserva reserva = dbReservas.get(idReserva);
        if (reserva == null) {
            throw new IllegalStateException("No existe la reserva con id: " + idReserva);
        }
        if (reserva.isCancelada()) {
            throw new IllegalStateException("La reserva con id " + idReserva + " ya ha sido cancelada");
        }

        for (String codigoAsiento : reserva.getAsientos()) {
            char fila = codigoAsiento.charAt(INDICE_FILA_ASIENTO);
            int numero = Integer.parseInt(codigoAsiento.substring(INDICE_NUMERO_ASIENTO));

            reserva.getFuncion().desocuparAsiento(fila, numero);
        }
    }

    public Reserva buscar(int idReserva) {
        return dbReservas.get(idReserva);
    }
}
