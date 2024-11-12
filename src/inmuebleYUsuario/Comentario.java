package inmuebleYUsuario;

import java.util.List;

public class Comentario {
	private List<String> cometarios;

    public Comentario(List<String> cometarios) {
        this.cometarios = cometarios;
    }

    public List<String> getCometarios() {
        return cometarios;
    }

    public void addComentario(String comentario) {
        this.cometarios.add(comentario);
    }

    public void removeCometario(String comentario) {
        this.cometarios.remove(comentario);
    }
}
