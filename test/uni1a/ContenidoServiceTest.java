package uni1a;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import uni1a.model.*;
import uni1a.repository.CsvContenidoRepository;
import uni1a.repository.IContenidoRepository;
import uni1a.service.ContenidoService;

class ContenidoServiceTest {

    @TempDir
    Path tempDir;

    private ContenidoService service;

    @BeforeEach
    void setUp() {
        IContenidoRepository repository = new CsvContenidoRepository(tempDir);
        service = new ContenidoService(repository);
    }

    @Test
    void agregarYBuscarContenidoPorIdYTitulo() {
        Pelicula pelicula = new Pelicula(1, "Matrix", 136, "Ciencia Ficción", "Warner Bros");
        pelicula.agregarActor(new Actor(101, "Keanu Reeves", "Canadiense"));
        service.agregarContenido(pelicula);

        assertEquals(1, service.obtenerTodos().size());
        Optional<ContenidoAudiovisual> encontrada = service.buscarPorId(1);
        assertTrue(encontrada.isPresent());
        assertEquals("Matrix", encontrada.get().getTitulo());

        List<ContenidoAudiovisual> porTitulo = service.buscarPorTitulo("mat");
        assertEquals(1, porTitulo.size());
    }

    @Test
    void rechazarIdDuplicado() {
        Pelicula p1 = new Pelicula(1, "Película A", 90, "Drama", "Estudio A");
        Pelicula p2 = new Pelicula(1, "Película B", 100, "Acción", "Estudio B");
        service.agregarContenido(p1);

        assertThrows(IllegalStateException.class, () -> service.agregarContenido(p2));
    }

    @Test
    void guardarYCargarDesdeCsvPreservaEntidades() throws IOException {
        SerieDeTV serie = new SerieDeTV(10, "Dark", 50, "Misterio");
        serie.agregarTemporada(new Temporada(1, 10, 2017));

        Documental doc = new Documental(20, "Cosmos", 45, "Ciencia", "Astrofísica");
        doc.agregarInvestigador(new Investigador(201, "Carl Sagan", "Astronomía"));

        service.agregarContenido(serie);
        service.agregarContenido(doc);
        service.guardarEnArchivo();

        ContenidoService nuevoServicio = new ContenidoService(new CsvContenidoRepository(tempDir));
        nuevoServicio.cargarDesdeArchivo();

        assertEquals(2, nuevoServicio.obtenerTodos().size());
        ContenidoAudiovisual cargadoSerie = nuevoServicio.buscarPorId(10).orElseThrow();
        assertTrue(cargadoSerie instanceof SerieDeTV);
        assertEquals(1, ((SerieDeTV) cargadoSerie).getTemporadas().size());

        ContenidoAudiovisual cargadoDoc = nuevoServicio.buscarPorId(20).orElseThrow();
        assertTrue(cargadoDoc instanceof Documental);
        assertEquals(1, ((Documental) cargadoDoc).getInvestigadores().size());
    }
}