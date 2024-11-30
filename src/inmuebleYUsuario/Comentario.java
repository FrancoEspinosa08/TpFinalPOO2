package inmuebleYUsuario;

import java.util.List;

public class Comentario {
	private List<String> comentarios;

    public Comentario(List<String> cometarios) {
        this.comentarios = cometarios;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void addComentario(String comentario) {
        this.comentarios.add(comentario);
    }

    public void removeComentario(String comentario) {
        this.comentarios.remove(comentario);
    }
}
