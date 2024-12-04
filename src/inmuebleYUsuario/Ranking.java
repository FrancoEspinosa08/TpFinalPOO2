package inmuebleYUsuario;
import java.util.ArrayList;
import java.util.List;
import administrador.Categoria;

public abstract class Ranking {
	protected List<Categoria> categorias = new ArrayList<Categoria>();
	
    // Devuelve el puntaje promedio de todas las categorías juntas (único puntaje)
    public double promedioTotal(List<Categoria> categorias) {
        // Suma todos los puntajes de las categorías
    	double puntajeTotal = categorias.stream()
    		    .flatMap(cat -> cat.getPuntaje().stream()) // Aplanar todas las listas en un solo flujo de Integer
    		    .mapToInt(Integer::intValue)               // Convertir cada Integer a int
    		    .sum();                                    // Sumar todos los valores
    	
    	// Contamos el número total de puntajes en todas las categorías
        long totalPuntajes = categorias.stream()
                .flatMap(cat -> cat.getPuntaje().stream()) 
                .count();
        
    	return puntajeTotal / totalPuntajes;
    }
  
    // Implementación común para la clase base
    public List<Categoria> getCategorias() {
        return categorias; // Devuelve la lista de las categorías de los inmuebles
    }
}
