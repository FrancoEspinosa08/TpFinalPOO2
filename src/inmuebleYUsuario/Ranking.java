package inmuebleYUsuario;
import java.util.List;
import administrador.Categoria;

public abstract class Ranking {
	protected List<Categoria> categorias;
	
    // Devuelve el puntaje promedio de todas las categorías juntas (único puntaje)
    public double promedioTotal(List<Categoria> categorias) {
        // Suma todos los puntajes de las categorías
    	double puntajeTotal = categorias.stream()
    		    .flatMap(cat -> cat.getPuntaje().stream()) // Aplanar todas las listas en un solo flujo de Integer
    		    .mapToInt(Integer::intValue)               // Convertir cada Integer a int
    		    .sum();                                    // Sumar todos los valores
    	int tamaño   = categorias.size();
    	return puntajeTotal / tamaño;
    }
    // Constructor
    public Ranking() {
        // Inicializa la lista de categorías (puedes hacerlo de manera predeterminada o en una subclase)
        this.categorias = null;  // O inicializar con una lista vacía si es necesario
    }
    
    // Implementación común para la clase base
    public List<Categoria> getCategorias() {
        return categorias; // Devuelve la lista de las categorías de los inmuebles
    }
}
