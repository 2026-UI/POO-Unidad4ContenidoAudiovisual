package uni1a.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import uni1a.model.ContenidoAudiovisual;
import uni1a.repository.IContenidoRepository;

/**
 * Servicio de negocio que gestiona el catálogo en memoria y coordina con el repositorio.
 * Depende de la abstracción IContenidoRepository (Principio DIP).
 */
public class ContenidoService {
    private final IContenidoRepository repository;
    private final List<ContenidoAudiovisual> catalogo;

    public ContenidoService(IContenidoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo.");
        }
        this.repository = repository;
        this.catalogo = new ArrayList<>();
    }

    public void cargarDesdeArchivo() throws IOException {
        catalogo.clear();
        catalogo.addAll(repository.cargarTodo());
    }

    public void guardarEnArchivo() throws IOException {
        repository.guardarTodo(catalogo);
    }

    public void agregarContenido(ContenidoAudiovisual contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("No se puede agregar un contenido nulo.");
        }
        if (buscarPorId(contenido.getId()).isPresent()) {
            throw new IllegalStateException("Ya existe un contenido con el ID: " + contenido.getId());
        }
        catalogo.add(contenido);
    }

    public Optional<ContenidoAudiovisual> buscarPorId(int id) {
        return catalogo.stream().filter(c -> c.getId() == id).findFirst();
    }

    public List<ContenidoAudiovisual> buscarPorTitulo(String texto) {
        if (texto == null || texto.isBlank()) {
            return Collections.emptyList();
        }
        String filtro = texto.trim().toLowerCase();
        return catalogo.stream()
                .filter(c -> c.getTitulo().toLowerCase().contains(filtro))
                .collect(Collectors.toList());
    }

    public List<ContenidoAudiovisual> obtenerTodos() {
        return Collections.unmodifiableList(catalogo);
    }

    public int generarSiguienteId() {
        return catalogo.stream().mapToInt(ContenidoAudiovisual::getId).max().orElse(0) + 1;
    }
}