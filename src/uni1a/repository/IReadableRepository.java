package uni1a.repository;

import java.io.IOException;
import java.util.List;
import uni1a.model.ContenidoAudiovisual;

/**
 * Interfaz segregada exclusivamente para lectura de datos (Principio ISP).
 */
public interface IReadableRepository {
    List<ContenidoAudiovisual> cargarTodo() throws IOException;
}