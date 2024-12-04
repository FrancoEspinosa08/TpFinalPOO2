package buscador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import observer.Inmueble;

public abstract class Filtro {
   
	private List<Filtro> filtrosObligatorios = new ArrayList<Filtro>();

    

    // Método abstracto que deben implementar las subclases
    public abstract List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas);
    public abstract List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles);

    // Método que aplica todos los filtros obligatorios en orden
    public List<Inmueble> filtrosObligatorios(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
    	
    	/* Esta validacion AQUI da siempre error sin importar si llamamos a setFiltrosObligatorios 
    	 * 
    	//Validamos que se hayan seteado al menos 1 filtro obligatorio
    		if(this.filtrosObligatorios.isEmpty()) {
    			throw new IllegalArgumentException("No se han seteado filtros obligatorios");
    		}
    	 * 
    	 */
    	
    	List<Inmueble> resultado = inmuebles;
        for (Filtro filtro : filtrosObligatorios) {
            resultado = filtro.filtrar(ciudad, checkIn, checkOut, resultado);
        }
        return resultado;
    }

    // Setter para los filtros obligatorios
    public void setFiltrosObligatorios(List<Filtro> filtrosObligatorios) {
    	
    	if (filtrosObligatorios == null || filtrosObligatorios.isEmpty()) {
            throw new IllegalArgumentException("La lista de filtros obligatorios no puede estar vacía.");
        }
    	
        this.filtrosObligatorios = filtrosObligatorios;
    }
    
    public List<Filtro> getFiltrosObligatorios(){
    	return this.filtrosObligatorios;
    }
    
}