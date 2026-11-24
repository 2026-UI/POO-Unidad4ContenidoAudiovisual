package uni1a.model;

/**
 * Clase base abstracta que representa cualquier obra audiovisual del catálogo.
 * Aplica SRP (solo modela datos base), OCP (extensible por subclases) y LSP.
 */
public abstract class ContenidoAudiovisual {
    private final int id;
    private String titulo;
    private int duracionEnMinutos;
    private String genero;

    protected ContenidoAudiovisual(int id, String titulo, int duracionEnMinutos, String genero) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un entero positivo.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (duracionEnMinutos <= 0) {
            throw new IllegalArgumentException("La duración en minutos debe ser mayor a cero.");
        }
        this.id = id;
        this.titulo = titulo.trim();
        this.duracionEnMinutos = duracionEnMinutos;
        this.genero = (genero == null || genero.isBlank()) ? "Sin género" : genero.trim();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo.trim();
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        if (duracionEnMinutos <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a cero.");
        }
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = (genero == null || genero.isBlank()) ? "Sin género" : genero.trim();
    }

    public abstract String obtenerTipo();
    public abstract String toCsvRow();
    public abstract String obtenerDetalleFormateado();
}