public class Asiento {
    // TÉCNICA UTILIZADA: INTRODUCE PARAMETER OBJECT
    // REFACTOR: Clase Asiento creada para reemplazar los parámetros en crearReserva
    private final char fila;
    private final int numero;

    public Asiento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
    }

    public char getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public String codigo() {
        return String.format("%c%d", fila, numero);
    }
}
