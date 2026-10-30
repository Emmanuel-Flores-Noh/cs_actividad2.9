public class Asiento {
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