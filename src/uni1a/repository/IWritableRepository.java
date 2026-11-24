package uni1a.repository;

import java.io.IOException;
import java.util.List;
import uni1a.model.ContenidoAudiovisual;

/**
 * Interfaz segregada exclusivamente para escritura de datos (Principio ISP).
 */
public interface IWritableRepository {
    void guardarTodo(List<ContenidoAudiovisual> contenidos) throws IOException;
}