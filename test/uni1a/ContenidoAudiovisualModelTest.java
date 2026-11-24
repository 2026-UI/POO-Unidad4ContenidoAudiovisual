package uni1a;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import uni1a.model.*;

class ContenidoAudiovisualModelTest {

    @Test
    void testCreacionPeliculaYActores() {
        Pelicula pelicula = new Pelicula(1, "Inception", 148, "Sci-Fi", "Warner Bros");
        pelicula.agregarActor(new Actor(10, "Leonardo DiCaprio", "Estadounidense"));

        assertEquals("PELICULA", pelicula.obtenerTipo());
        assertEquals(1, pelicula.getReparto().size());
        assertTrue(pelicula.obtenerDetalleFormateado().contains("Leonardo DiCaprio"));
    }

    @Test
    void testCreacionSerieYTemporadas() {
        SerieDeTV serie = new SerieDeTV(2, "Dark", 55, "Misterio");
        serie.agregarTemporada(new Temporada(1, 10, 2017));
        serie.agregarTemporada(new Temporada(2, 8, 2019));

        assertEquals("SERIE", serie.obtenerTipo());
        assertEquals(2, serie.getTemporadas().size());
    }

    @Test
    void testCreacionDocumentalEInvestigadores() {
        Documental doc = new Documental(3, "Cosmos", 45, "Ciencia", "Astrofísica");
        doc.agregarInvestigador(new Investigador(20, "Carl Sagan", "Astronomía"));

        assertEquals("DOCUMENTAL", doc.obtenerTipo());
        assertEquals(1, doc.getInvestigadores().size());
    }

    @Test
    void testValidacionDatosInvalidosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Pelicula(0, "Test", 100, "Acción", "Fox"));
        assertThrows(IllegalArgumentException.class, () -> new Pelicula(1, "", 100, "Acción", "Fox"));
        assertThrows(IllegalArgumentException.class, () -> new Pelicula(1, "Test", -10, "Acción", "Fox"));
    }
}