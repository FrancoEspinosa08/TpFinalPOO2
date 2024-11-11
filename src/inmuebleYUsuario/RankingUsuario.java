package inmuebleYUsuario;
import java.util.List;
import administrador.Categoria;

public class RankingUsuario extends Ranking {

    // Atributos
    private List<Categoria> categoriasInquilino; // Lista de categorías del Inquilino
    private List<Categoria> categoriasPropietario; // Lista de categorías del Propietario

    // Métodos
    public List<Categoria> getCategoriasInquilino() {
        return categoriasInquilino; // Devuelve la lista de categorías del Inquilino
    }

    public List<Categoria> getCategoriasPropietario() {
        return categoriasPropietario; // Devuelve la lista de categorías del Propietario
    }

    // Métodos set
    public void setCategoriasInquilino(List<Categoria> categorias) {
        this.categoriasInquilino = categorias; // Asigna la lista de categorías del Inquilino
    }

    public void setCategoriasPropietario(List<Categoria> categorias) {
        this.categoriasPropietario = categorias; // Asigna la lista de categorías del Propietario
    }

    // Constructor
    public RankingUsuario(List<Categoria> categoriasInquilino, List<Categoria> categoriasPropietario) {
        this.categoriasInquilino = categoriasInquilino;
        this.categoriasPropietario = categoriasPropietario;
    }
}