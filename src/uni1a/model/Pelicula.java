package uni1a.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private final List<Actor> reparto;

    public Pelicula(int id, String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(id, titulo, duracionEnMinutos, genero);
        this.estudio = (estudio == null || estudio.isBlank()) ? "Independiente" : estudio.trim();
        this.reparto = new ArrayList<>();
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public void agregarActor(Actor actor) {
        if (actor != null) {
            reparto.add(actor);
        }
    }

    public List<Actor> getReparto() {
        return Collections.unmodifiableList(reparto);
    }

    @Override
    public String obtenerTipo() {
        return "PELICULA";
    }

    @Override
    public String toCsvRow() {
        return String.format("PELICULA;%d;%s;%d;%s;%s",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(), estudio);
    }

    @Override
    public String obtenerDetalleFormateado() {
        String actoresTexto = reparto.isEmpty() ? "Sin actores registrados"
                : reparto.stream().map(Actor::toString).collect(Collectors.joining(", "));
        return String.format("[Película #%d] %s | %d min | Género: %s | Estudio: %s | Actores: %s",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(), estudio, actoresTexto);
    }
}