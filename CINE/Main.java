import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SistemaReservas sistemaReservas = new SistemaReservas();
        Funcion funcion = new Funcion("Interestelar", 3, "20:30", 12.0);

        {
            Cliente cliente = new Cliente("Ana", "ana@mail.com", true);
            try {
                int reserva1 = sistemaReservas.crearReserva(cliente, funcion, Arrays.asList(new Asiento('F', 5),
                new Asiento('F', 6)));
                System.out.println("Reserva Ana: " + reserva1);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        {
            Cliente cliente = new Cliente("Luis", "luis@mail.com", false);
            try {
                int reserva2 = sistemaReservas.crearReserva(cliente, funcion, Arrays.asList(new Asiento('F', 7),
                new Asiento('F', 6)));
                System.out.println("Reserva Luis: " + reserva2);
            } catch(RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Ocupados: " + funcion.getListaAsientosOcupados());

        Reserva ana = sistemaReservas.buscar(1);
        System.out.println("Precio Ana (socio): " + ana.precio);

        System.out.print("Cancelar 1: ");
        try {
            sistemaReservas.cancelar(1);
            System.out.println("0");
        } catch (IllegalStateException e) {
            System.out.println("-1");
        }

        System.out.print("Cancelar 99: ");
        try {
            sistemaReservas.cancelar(99);
            System.out.println("0");
        } catch (IllegalStateException e) {
            System.out.println("-1");
        }
    }
}