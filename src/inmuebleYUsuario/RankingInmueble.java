package inmuebleYUsuario;
import java.util.List;
import administrador.Categoria;

public class RankingInmueble extends Ranking {


    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias; // Asigna la lista de categorías de los inmuebles
    }

    // Constructor
    public RankingInmueble(List<Categoria> categorias) {
        this.categorias = categorias;
    }

}
