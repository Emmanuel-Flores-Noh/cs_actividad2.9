public class Cliente {
    private String nombre;
    private String email;
    private boolean socio;

    public Cliente(String nombre, String email, boolean socio) {
        this.nombre = nombre;
        this.email = email;
        this.socio = socio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSocio() {
        return socio;
    }

    public boolean getSocio() {
        return socio;
    }

    public void setSocio(boolean socio) {
        this.socio = socio;
    }
}
