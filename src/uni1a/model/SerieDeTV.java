package uni1a.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SerieDeTV extends ContenidoAudiovisual {
    private final List<Temporada> temporadas;

    public SerieDeTV(int id, String titulo, int duracionEnMinutos, String genero) {
        super(id, titulo, duracionEnMinutos, genero);
        this.temporadas = new ArrayList<>();
    }

    public void agregarTemporada(Temporada temporada) {
        if (temporada != null) {
            temporadas.add(temporada);
        }
    }

    public List<Temporada> getTemporadas() {
        return Collections.unmodifiableList(temporadas);
    }

    @Override
    public String obtenerTipo() {
        return "SERIE";
    }

    @Override
    public String toCsvRow() {
        return String.format("SERIE;%d;%s;%d;%s;%d",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(), temporadas.size());
    }

    @Override
    public String obtenerDetalleFormateado() {
        String tempTexto = temporadas.isEmpty() ? "Sin temporadas"
                : temporadas.stream().map(Temporada::toString).collect(Collectors.joining(", "));
        return String.format("[Serie TV #%d] %s | %d min/ep | Género: %s | Temporadas (%d): %s",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(), temporadas.size(), tempTexto);
    }
}