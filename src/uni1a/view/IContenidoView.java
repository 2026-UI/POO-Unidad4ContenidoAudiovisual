package uni1a.view;

import java.util.List;
import uni1a.model.ContenidoAudiovisual;

public interface IContenidoView {
    int mostrarMenuPrincipal();
    void mostrarListaContenidos(List<ContenidoAudiovisual> contenidos);
    String solicitarTexto(String etiqueta);
    int solicitarEntero(String etiqueta);
    void mostrarMensaje(String mensaje);
    void mostrarError(String error);
}