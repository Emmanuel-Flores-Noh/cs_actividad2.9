import java.util.ArrayList;
import java.util.List;

public class Funcion {
    private String titulo;
    private int sala;
    private String hora;
    private double precio;
    private final List<String> ocupados = new ArrayList<>();

    public Funcion(String titulo, int sala, String hora, double precio) {
        this.titulo = titulo;
        this.sala = sala;
        this.hora = hora;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean disponibilidadAsiento(char fila, int numero) {
        // Me gusta mas esta manera de formatear strings es mas clara
        String codigoSilla = String.format("%c%d", fila, numero);
        return ! ocupados.contains(codigoSilla);
    }

    // Ocupa el asiento y lanza excepción si ya estaba ocupado
    // Ahora la funcion no modifica y hace query, sino hay una funcion separada para la query
    public void ocuparAsiento(char fila, int numero) {
        String codigoSilla = String.format("%c%d", fila, numero);
        if (! disponibilidadAsiento(fila, numero) ) {
            throw new IllegalStateException("Asiento ocupado: " + codigoSilla);
        }
        ocupados.add(codigoSilla);
    }

    public void desocuparAsiento(char fila, int numero) {
        String codigoSilla = String.format("%c%d", fila, numero);

        if( disponibilidadAsiento(fila, numero) ) {
            throw new IllegalStateException("Asiento no ocupado: " + codigoSilla);
        }

        ocupados.remove(codigoSilla);
    }

    public List<String> getListaAsientosOcupados() {
        return this.ocupados;
    }

    // no mas setPrecio
}
