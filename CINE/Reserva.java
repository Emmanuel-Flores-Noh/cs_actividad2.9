import java.util.List;

public class Reserva {
    private int id;
    private Cliente cliente;
    private Funcion funcion;
    private List<String> asientos;
    private double precio;
    private final boolean cancelada;

    public Reserva(int id, Cliente cliente, Funcion funcion, List<String> asientos, boolean cancelada) {
        this.id = id;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asientos = asientos;
        this.cancelada = cancelada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public List<String> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<String> asientos) {
        this.asientos = asientos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public boolean getCancelada() {
        return cancelada;
    }
}
