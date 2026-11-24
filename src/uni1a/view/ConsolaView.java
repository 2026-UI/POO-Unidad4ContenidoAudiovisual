package uni1a.view;

import java.util.List;
import java.util.Scanner;
import uni1a.model.ContenidoAudiovisual;

/**
 * Vista de consola del patrón MVC. Solo maneja entrada y salida estándar.
 */
public class ConsolaView implements IContenidoView {
    private final Scanner scanner;

    public ConsolaView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int mostrarMenuPrincipal() {
        System.out.println("\n====================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE CONTENIDO AUDIOVISUAL");
        System.out.println("====================================================");
        System.out.println("1. Listar todos los contenidos audiovisuales");
        System.out.println("2. Registrar nueva Película (con Actor)");
        System.out.println("3. Registrar nueva Serie de TV (con Temporada)");
        System.out.println("4. Registrar nuevo Documental (con Investigador)");
        System.out.println("5. Buscar contenido por título");
        System.out.println("6. Guardar cambios en archivos CSV");
        System.out.println("7. Recargar datos desde archivos CSV");
        System.out.println("0. Salir");
        return solicitarEntero("Seleccione una opción: ");
    }

    @Override
    public void mostrarListaContenidos(List<ContenidoAudiovisual> contenidos) {
        System.out.println("\n--- CATÁLOGO AUDIOVISUAL (" + contenidos.size() + " registros) ---");
        if (contenidos.isEmpty()) {
            System.out.println("No hay contenidos registrados.");
            return;
        }
        for (ContenidoAudiovisual c : contenidos) {
            System.out.println(" • " + c.obtenerDetalleFormateado());
        }
    }

    @Override
    public String solicitarTexto(String etiqueta) {
        System.out.print(etiqueta);
        return scanner.nextLine().trim();
    }

    @Override
    public int solicitarEntero(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número entero válido.");
            }
        }
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("[INFO] " + mensaje);
    }

    @Override
    public void mostrarError(String error) {
        System.err.println("[ERROR] " + error);
    }
}