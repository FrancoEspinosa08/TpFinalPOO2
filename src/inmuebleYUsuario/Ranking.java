package inmuebleYUsuario;
import java.util.ArrayList;
import java.util.List;
import administrador.Categoria;

public abstract class Ranking {
	protected List<Categoria> categorias = new ArrayList<Categoria>();
	
    // Devuelve el puntaje promedio de todas las categorías juntas (único puntaje)
    public double promedioTotal(List<Categoria> categorias) {
     // Verifica si hay puntajes para evitar división por cero
        long totalPuntajes = categorias.stream()
    	            				   .flatMap(cat -> cat.getPuntaje().stream()) // Obtener todos los puntajes en un solo flujo
    	            				   .count();

    	if (totalPuntajes == 0) {
    	    return 0.0; // Si no hay puntajes, devuelve 0 como promedio
    	}

     // Suma todos los puntajes
    	double puntajeTotal = categorias.stream()
    	            					.flatMap(cat -> cat.getPuntaje().stream())
    	            					.mapToInt(Integer::intValue)
    	            					.sum();

     // Calcula el promedio como un valor double
    	return puntajeTotal / totalPuntajes; 
    	
    	
    	/*// Suma todos los puntajes de las categorías
    	double puntajeTotal = categorias.stream()
    		    .flatMap(cat -> cat.getPuntaje().stream()) // Aplanar todas las listas en un solo flujo de Integer
    		    .mapToInt(Integer::intValue)               // Convertir cada Integer a int
    		    .sum();                                    // Sumar todos los valores
    	
    	// Contamos el número total de puntajes en todas las categorías
        long totalPuntajes = categorias.stream()
                .flatMap(cat -> cat.getPuntaje().stream()) 
                .count();
        
    	return puntajeTotal / totalPuntajes;
    	*/
    }
  
    // Implementación común para la clase base
    public List<Categoria> getCategorias() {
        return categorias; // Devuelve la lista de las categorías de los inmuebles
    }
}
