package uni1a;

import java.nio.file.Path;
import uni1a.controller.ContenidoController;
import uni1a.repository.CsvContenidoRepository;
import uni1a.repository.IContenidoRepository;
import uni1a.service.ContenidoService;
import uni1a.view.ConsolaView;
import uni1a.view.IContenidoView;

/**
 * Punto de entrada de la aplicación. Ensambla dependencias mediante DIP.
 */
public class Main {
    public static void main(String[] args) {
        IContenidoRepository repository = new CsvContenidoRepository(Path.of("data"));
        ContenidoService service = new ContenidoService(repository);
        IContenidoView view = new ConsolaView();
        ContenidoController controller = new ContenidoController(service, view);
        controller.iniciar();
    }
}