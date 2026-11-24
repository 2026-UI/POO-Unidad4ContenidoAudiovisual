package uni1a.model;

public class Temporada {
    private final int numeroTemporada;
    private final int cantidadEpisodios;
    private final int anioEstreno;

    public Temporada(int numeroTemporada, int cantidadEpisodios, int anioEstreno) {
        if (numeroTemporada <= 0 || cantidadEpisodios <= 0) {
            throw new IllegalArgumentException("Número de temporada y episodios deben ser positivos.");
        }
        this.numeroTemporada = numeroTemporada;
        this.cantidadEpisodios = cantidadEpisodios;
        this.anioEstreno = anioEstreno;
    }

    public int getNumeroTemporada() {
        return numeroTemporada;
    }

    public int getCantidadEpisodios() {
        return cantidadEpisodios;
    }

    public int getAnioEstreno() {
        return anioEstreno;
    }

    public String toCsvRow(int serieId) {
        return String.format("%d;%d;%d;%d", serieId, numeroTemporada, cantidadEpisodios, anioEstreno);
    }

    @Override
    public String toString() {
        return "Temporada " + numeroTemporada + " (" + cantidadEpisodios + " eps, " + anioEstreno + ")";
    }
}