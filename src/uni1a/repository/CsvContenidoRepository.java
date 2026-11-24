package uni1a.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uni1a.model.*;

/**
 * Implementación de persistencia en archivos CSV.
 * Cumple SRP al encargarse únicamente de la serialización y deserialización en disco.
 */
public class CsvContenidoRepository implements IContenidoRepository {
    private static final String DELIMITADOR = ";";
    private final Path archivoContenidos;
    private final Path archivoActores;
    private final Path archivoTemporadas;
    private final Path archivoInvestigadores;

    public CsvContenidoRepository(Path directorioBase) {
        this.archivoContenidos = directorioBase.resolve("contenidos.csv");
        this.archivoActores = directorioBase.resolve("actores.csv");
        this.archivoTemporadas = directorioBase.resolve("temporadas.csv");
        this.archivoInvestigadores = directorioBase.resolve("investigadores.csv");
    }

    @Override
    public List<ContenidoAudiovisual> cargarTodo() throws IOException {
        Map<Integer, ContenidoAudiovisual> mapaContenidos = new HashMap<>();
        List<ContenidoAudiovisual> listaOrdenada = new ArrayList<>();
        if (!Files.exists(archivoContenidos)) {
            return listaOrdenada;
        }
        cargarContenidosBase(mapaContenidos, listaOrdenada);
        cargarActores(mapaContenidos);
        cargarTemporadas(mapaContenidos);
        cargarInvestigadores(mapaContenidos);
        return listaOrdenada;
    }

    private void cargarContenidosBase(Map<Integer, ContenidoAudiovisual> mapa, List<ContenidoAudiovisual> lista)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(archivoContenidos, StandardCharsets.UTF_8)) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = reader.readLine()) != null) {
                if (primeraLinea && linea.startsWith("TIPO;")) {
                    primeraLinea = false;
                    continue;
                }
                primeraLinea = false;
                if (linea.isBlank()) continue;
                ContenidoAudiovisual contenido = parsearContenido(linea);
                if (contenido != null) {
                    mapa.put(contenido.getId(), contenido);
                    lista.add(contenido);
                }
            }
        }
    }

    private ContenidoAudiovisual parsearContenido(String linea) {
        String[] tokens = linea.split(DELIMITADOR, -1);
        if (tokens.length < 6) return null;
        String tipo = tokens[0].trim().toUpperCase();
        int id = Integer.parseInt(tokens[1].trim());
        String titulo = tokens[2].trim();
        int duracion = Integer.parseInt(tokens[3].trim());
        String genero = tokens[4].trim();
        String extra = tokens[5].trim();
        return switch (tipo) {
            case "PELICULA" -> new Pelicula(id, titulo, duracion, genero, extra);
            case "SERIE" -> new SerieDeTV(id, titulo, duracion, genero);
            case "DOCUMENTAL" -> new Documental(id, titulo, duracion, genero, extra);
            default -> throw new IllegalArgumentException("Tipo de contenido desconocido en CSV: " + tipo);
        };
    }

    private void cargarActores(Map<Integer, ContenidoAudiovisual> mapa) throws IOException {
        if (!Files.exists(archivoActores)) return;
        try (BufferedReader reader = Files.newBufferedReader(archivoActores, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isBlank() || linea.startsWith("ID_ACTOR;")) continue;
                String[] t = linea.split(DELIMITADOR, -1);
                int idActor = Integer.parseInt(t[0].trim());
                int idPelicula = Integer.parseInt(t[1].trim());
                ContenidoAudiovisual c = mapa.get(idPelicula);
                if (c instanceof Pelicula pelicula) {
                    pelicula.agregarActor(new Actor(idActor, t[2].trim(), t[3].trim()));
                }
            }
        }
    }

    private void cargarTemporadas(Map<Integer, ContenidoAudiovisual> mapa) throws IOException {
        if (!Files.exists(archivoTemporadas)) return;
        try (BufferedReader reader = Files.newBufferedReader(archivoTemporadas, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isBlank() || linea.startsWith("ID_SERIE;")) continue;
                String[] t = linea.split(DELIMITADOR, -1);
                int idSerie = Integer.parseInt(t[0].trim());
                ContenidoAudiovisual c = mapa.get(idSerie);
                if (c instanceof SerieDeTV serie) {
                    serie.agregarTemporada(new Temporada(
                            Integer.parseInt(t[1].trim()),
                            Integer.parseInt(t[2].trim()),
                            Integer.parseInt(t[3].trim())));
                }
            }
        }
    }

    private void cargarInvestigadores(Map<Integer, ContenidoAudiovisual> mapa) throws IOException {
        if (!Files.exists(archivoInvestigadores)) return;
        try (BufferedReader reader = Files.newBufferedReader(archivoInvestigadores, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isBlank() || linea.startsWith("ID_INV;")) continue;
                String[] t = linea.split(DELIMITADOR, -1);
                int idInv = Integer.parseInt(t[0].trim());
                int idDoc = Integer.parseInt(t[1].trim());
                ContenidoAudiovisual c = mapa.get(idDoc);
                if (c instanceof Documental doc) {
                    doc.agregarInvestigador(new Investigador(idInv, t[2].trim(), t[3].trim()));
                }
            }
        }
    }

    @Override
    public void guardarTodo(List<ContenidoAudiovisual> contenidos) throws IOException {
        Files.createDirectories(archivoContenidos.getParent());
        try (BufferedWriter bwCont = Files.newBufferedWriter(archivoContenidos, StandardCharsets.UTF_8);
             BufferedWriter bwAct = Files.newBufferedWriter(archivoActores, StandardCharsets.UTF_8);
             BufferedWriter bwTemp = Files.newBufferedWriter(archivoTemporadas, StandardCharsets.UTF_8);
             BufferedWriter bwInv = Files.newBufferedWriter(archivoInvestigadores, StandardCharsets.UTF_8)) {
            bwCont.write("TIPO;ID;TITULO;DURACION;GENERO;EXTRA\n");
            bwAct.write("ID_ACTOR;ID_PELICULA;NOMBRE;NACIONALIDAD\n");
            bwTemp.write("ID_SERIE;NUM_TEMPORADA;EPISODIOS;ANIO\n");
            bwInv.write("ID_INV;ID_DOCUMENTAL;NOMBRE;ESPECIALIDAD\n");
            for (ContenidoAudiovisual c : contenidos) {
                bwCont.write(c.toCsvRow() + "\n");
                guardarEntidadesAsociadas(c, bwAct, bwTemp, bwInv);
            }
        }
    }

    private void guardarEntidadesAsociadas(ContenidoAudiovisual c, BufferedWriter bwAct,
                                           BufferedWriter bwTemp, BufferedWriter bwInv) throws IOException {
        if (c instanceof Pelicula p) {
            for (Actor a : p.getReparto()) {
                bwAct.write(a.toCsvRow(p.getId()) + "\n");
            }
        } else if (c instanceof SerieDeTV s) {
            for (Temporada t : s.getTemporadas()) {
                bwTemp.write(t.toCsvRow(s.getId()) + "\n");
            }
        } else if (c instanceof Documental d) {
            for (Investigador i : d.getInvestigadores()) {
                bwInv.write(i.toCsvRow(d.getId()) + "\n");
            }
        }
    }
}