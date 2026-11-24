package uni1a.controller;

import java.io.IOException;
import uni1a.model.*;
import uni1a.service.ContenidoService;
import uni1a.view.IContenidoView;

/**
 * Controlador MVC que coordina las interacciones entre ConsolaView y ContenidoService.
 */
public class ContenidoController {
    private final ContenidoService service;
    private final IContenidoView view;

    public ContenidoController(ContenidoService service, IContenidoView view) {
        this.service = service;
        this.view = view;
    }

    public void iniciar() {
        cargarDatosIniciales();
        boolean ejecutando = true;
        while (ejecutando) {
            int opcion = view.mostrarMenuPrincipal();
            try {
                switch (opcion) {
                    case 1 -> view.mostrarListaContenidos(service.obtenerTodos());
                    case 2 -> registrarPelicula();
                    case 3 -> registrarSerie();
                    case 4 -> registrarDocumental();
                    case 5 -> buscarPorTitulo();
                    case 6 -> guardarDatos();
                    case 7 -> cargarDatosIniciales();
                    case 0 -> {
                        guardarDatos();
                        view.mostrarMensaje("Sistema finalizado correctamente.");
                        ejecutando = false;
                    }
                    default -> view.mostrarError("Opción no reconocida.");
                }
            } catch (Exception e) {
                view.mostrarError("Operación fallida: " + e.getMessage());
            }
        }
    }

    private void cargarDatosIniciales() {
        try {
            service.cargarDesdeArchivo();
            view.mostrarMensaje("Datos cargados desde CSV (" + service.obtenerTodos().size() + " registros).");
        } catch (IOException e) {
            view.mostrarError("No se pudieron leer los archivos CSV: " + e.getMessage());
        }
    }

    private void guardarDatos() {
        try {
            service.guardarEnArchivo();
            view.mostrarMensaje("Datos guardados exitosamente en la carpeta data/.");
        } catch (IOException e) {
            view.mostrarError("Error al guardar archivos CSV: " + e.getMessage());
        }
    }

    private void registrarPelicula() {
        int id = service.generarSiguienteId();
        String titulo = view.solicitarTexto("Título de la película: ");
        int duracion = view.solicitarEntero("Duración (minutos): ");
        String genero = view.solicitarTexto("Género: ");
        String estudio = view.solicitarTexto("Estudio productor: ");
        Pelicula pelicula = new Pelicula(id, titulo, duracion, genero, estudio);
        String nombreActor = view.solicitarTexto("Nombre del actor principal: ");
        String nacionalidad = view.solicitarTexto("Nacionalidad del actor: ");
        pelicula.agregarActor(new Actor(id * 10 + 1, nombreActor, nacionalidad));
        service.agregarContenido(pelicula);
        view.mostrarMensaje("Película registrada con ID #" + id);
    }

    private void registrarSerie() {
        int id = service.generarSiguienteId();
        String titulo = view.solicitarTexto("Título de la serie: ");
        int duracion = view.solicitarEntero("Duración promedio por episodio (min): ");
        String genero = view.solicitarTexto("Género: ");
        SerieDeTV serie = new SerieDeTV(id, titulo, duracion, genero);
        int numTemp = view.solicitarEntero("Número de temporada: ");
        int episodios = view.solicitarEntero("Cantidad de episodios: ");
        int anio = view.solicitarEntero("Año de estreno: ");
        serie.agregarTemporada(new Temporada(numTemp, episodios, anio));
        service.agregarContenido(serie);
        view.mostrarMensaje("Serie de TV registrada con ID #" + id);
    }

    private void registrarDocumental() {
        int id = service.generarSiguienteId();
        String titulo = view.solicitarTexto("Título del documental: ");
        int duracion = view.solicitarEntero("Duración (minutos): ");
        String genero = view.solicitarTexto("Género: ");
        String tema = view.solicitarTexto("Tema de investigación: ");
        Documental documental = new Documental(id, titulo, duracion, genero, tema);
        String nombreInv = view.solicitarTexto("Nombre del investigador: ");
        String especialidad = view.solicitarTexto("Especialidad del investigador: ");
        documental.agregarInvestigador(new Investigador(id * 10 + 1, nombreInv, especialidad));
        service.agregarContenido(documental);
        view.mostrarMensaje("Documental registrado con ID #" + id);
    }

    private void buscarPorTitulo() {
        String criterio = view.solicitarTexto("Ingrese texto a buscar en el título: ");
        view.mostrarListaContenidos(service.buscarPorTitulo(criterio));
    }
}