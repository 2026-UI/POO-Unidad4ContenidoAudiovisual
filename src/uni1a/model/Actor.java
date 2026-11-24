package uni1a.model;

public class Actor {
    private final int id;
    private final String nombre;
    private final String nacionalidad;

    public Actor(int id, String nombre, String nacionalidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del actor es obligatorio.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.nacionalidad = (nacionalidad == null || nacionalidad.isBlank()) ? "Desconocida" : nacionalidad.trim();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String toCsvRow(int peliculaId) {
        return String.format("%d;%d;%s;%s", id, peliculaId, nombre, nacionalidad);
    }

    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ")";
    }
}