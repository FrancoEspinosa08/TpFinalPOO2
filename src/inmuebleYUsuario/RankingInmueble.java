package inmuebleYUsuario;
import java.util.List;
import administrador.Categoria;

public class RankingInmueble extends Ranking {

    // Atributo
    private List<Categoria> categoriasInmueble; // Lista de categorías de los inmuebles

    // Métodos
    public List<Categoria> getCategoriasInmueble() {
        return categoriasInmueble; // Devuelve la lista de categorías de los inmuebles
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias; // Asigna la lista de categorías de los inmuebles
    }

    // Constructor
    public RankingInmueble(List<Categoria> categorias) {
        this.categorias = categorias;
    }

}
