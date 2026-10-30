import java.util.Arrays;

public class Main {
    private static final int SALA_FUNCION = 3;
    private static final double PRECIO_FUNCION = 12.0;
    private static final int ID_RESERVA_ANA = 1;
    private static final int ID_RESERVA_INEXISTENTE = 99;
    private static final String CODIGO_EXITO = "0";
    private static final String CODIGO_ERROR = "-1";

    public static void main(String[] args) {
        SistemaReservas sistemaReservas = new SistemaReservas();
        Funcion funcionInterestelar = new Funcion("Interestelar", SALA_FUNCION, "20:30", PRECIO_FUNCION);

        {
            Cliente clienteAna = new Cliente("Ana", "ana@mail.com", true);
            try {
                int idReservaAna = sistemaReservas.crearReserva(clienteAna, funcionInterestelar, Arrays.asList(
                    new Asiento('F', 5),
                    new Asiento('F', 6)
                ));
                System.out.println("Reserva Ana: " + idReservaAna);
            } catch (IllegalStateException excepcion) {
                System.out.println(excepcion.getMessage());
            }
        }

        {
            Cliente clienteLuis = new Cliente("Luis", "luis@mail.com", false);
            try {
                int idReservaLuis = sistemaReservas.crearReserva(clienteLuis, funcionInterestelar, Arrays.asList(
                    new Asiento('F', 7),
                    new Asiento('F', 6)
                ));
                System.out.println("Reserva Luis: " + idReservaLuis);
            } catch (RuntimeException excepcion) {
                System.out.println(excepcion.getMessage());
            }
        }

        System.out.println("Ocupados: " + funcionInterestelar.getListaAsientosOcupados());

        Reserva reservaAna = sistemaReservas.buscar(ID_RESERVA_ANA);
        System.out.println("Precio Ana (socio): " + reservaAna.getPrecio());

        System.out.print("Cancelar 1: ");
        try {
            sistemaReservas.cancelar(ID_RESERVA_ANA);
            System.out.println(CODIGO_EXITO);
        } catch (IllegalStateException excepcion) {
            System.out.println(CODIGO_ERROR);
        }

        System.out.print("Cancelar 99: ");
        try {
            sistemaReservas.cancelar(ID_RESERVA_INEXISTENTE);
            System.out.println(CODIGO_EXITO);
        } catch (IllegalStateException excepcion) {
            System.out.println(CODIGO_ERROR);
        }
    }
}
