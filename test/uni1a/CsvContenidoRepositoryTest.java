package uni1a;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import uni1a.model.*;
import uni1a.repository.CsvContenidoRepository;

class CsvContenidoRepositoryTest {

    @TempDir
    Path tempDir;

    private CsvContenidoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CsvContenidoRepository(tempDir);
    }

    @Test
    void guardarYLeerGeneraArchivosCsvYRecuperaRegistros() throws IOException {
        Pelicula pelicula = new Pelicula(1, "Inception", 148, "Ciencia Ficción", "Warner Bros");
        pelicula.agregarActor(new Actor(101, "Leonardo DiCaprio", "Estadounidense"));

        SerieDeTV serie = new SerieDeTV(2, "Breaking Bad", 47, "Drama");
        serie.agregarTemporada(new Temporada(1, 7, 2008));

        Documental doc = new Documental(3, "Planeta Tierra", 50, "Naturaleza", "Ecosistemas");
        doc.agregarInvestigador(new Investigador(301, "David Attenborough", "Biología"));

        repository.guardarTodo(List.of(pelicula, serie, doc));

        assertTrue(Files.exists(tempDir.resolve("contenidos.csv")));
        assertTrue(Files.exists(tempDir.resolve("actores.csv")));
        assertTrue(Files.exists(tempDir.resolve("temporadas.csv")));
        assertTrue(Files.exists(tempDir.resolve("investigadores.csv")));

        List<ContenidoAudiovisual> recuperados = repository.cargarTodo();
        assertEquals(3, recuperados.size());
    }

    @Test
    void leerTodosConDirectorioVacioRetornaListaVacia() throws IOException {
        List<ContenidoAudiovisual> recuperados = repository.cargarTodo();
        assertNotNull(recuperados);
        assertTrue(recuperados.isEmpty());
    }
}