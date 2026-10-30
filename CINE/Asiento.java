public class Asiento {
    // TÉCNICA UTILIZADA: INTRODUCE PARAMETER OBJECT
    // REFACTOR: Clase Asiento creada para reemplazar los parámetros en crearReserva
    public final char fila;
    public final int numero;

    public Asiento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
    }

    public String codigo() {
        return String.format("%c%d", fila, numero);
    }
}