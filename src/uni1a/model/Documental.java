package uni1a.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Documental extends ContenidoAudiovisual {
    private String tema;
    private final List<Investigador> investigadores;

    public Documental(int id, String titulo, int duracionEnMinutos, String genero, String tema) {
        super(id, titulo, duracionEnMinutos, genero);
        this.tema = (tema == null || tema.isBlank()) ? "General" : tema.trim();
        this.investigadores = new ArrayList<>();
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void agregarInvestigador(Investigador investigador) {
        if (investigador != null) {
            investigadores.add(investigador);
        }
    }

    public List<Investigador> getInvestigadores() {
        return Collections.unmodifiableList(investigadores);
    }

    @Override
    public String obtenerTipo() {
        return "DOCUMENTAL";
    }

    @Override
    public String toCsvRow() {
        return String.format("DOCUMENTAL;%d;%s;%d;%s;%s",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(), tema);
    }

    @Override
    public String obtenerDetalleFormateado() {
        String invTexto = investigadores.isEmpty() ? "Sin investigadores"
                : investigadores.stream().map(Investigador::toString).collect(Collectors.joining(", "));
        return String.format("[Documental #%d] %s | %d min | Género: %s | Tema: %s | Investigadores: %s",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(), tema, invTexto);
    }
}