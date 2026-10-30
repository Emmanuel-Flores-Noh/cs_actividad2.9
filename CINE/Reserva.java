import java.util.List;

public class Reserva {
    public int id;
    public Cliente cliente;
    public Funcion funcion;
    public List<String> asientos;
    public double precio;
    public boolean cancelada;
    public String canal;

    // TÉCNICA UTILIZADA: ADD PARAMETER
    // REFACTOR: se añadió el parámetro "canal" al constructor y el atributo "canal" a la clase.
    // Así se guarda el canal de venta ("App", "Taquilla").
    public Reserva(int id, Cliente cliente, Funcion funcion, List<String> asientos, String canal) {
        this.id = id;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asientos = asientos;
        this.canal = canal;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }
}