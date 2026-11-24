package uni1a.model;

public class Investigador {
    private final int id;
    private final String nombre;
    private final String especialidad;

    public Investigador(int id, String nombre, String especialidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del investigador es obligatorio.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.especialidad = (especialidad == null || especialidad.isBlank()) ? "General" : especialidad.trim();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String toCsvRow(int documentalId) {
        return String.format("%d;%d;%s;%s", id, documentalId, nombre, especialidad);
    }

    @Override
    public String toString() {
        return nombre + " [Esp: " + especialidad + "]";
    }
}