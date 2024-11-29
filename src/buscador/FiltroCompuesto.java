package buscador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import observer.Inmueble;

public class FiltroCompuesto extends Filtro {

    // Atributos
    List<Filtro> filtros;

    // Constructor
    public FiltroCompuesto(List<Filtro> filtros) {
        this.filtros = filtros;
    }
    
    public FiltroCompuesto() {} //No deberia ser este el UNICO constructor? Por que recibe parametros?
    
    public void addFiltro(Filtro filtro) {
    	this.filtros.add(filtro);
    }
    
    public void removeFiltro(Filtro filtro) {
    	this.filtros.remove(filtro);
    }

    // Método que filtra los inmuebles por ciudad, fecha de check-in, check-out y aplicando filtros compuestos
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        List<Inmueble> resultado;

        // Aplico los tres filtros obligatorios (ciudad, checkIn y checkOut) a List<Inmueble> altas;
        resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, altas);

        // Aplica los filtros compuestos
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    // Método que aplica los filtros compuestos sobre la lista de inmuebles
    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        
    	List<Inmueble> resultado = new ArrayList<>(inmuebles); //Copio la lista de inmuebles a una variable

        // Aplica todos los filtros de la lista "filtros" a la variable resultado (List<Inmueble>)
        for (Filtro filtro : filtros) {
            resultado = filtro.filtrar(ciudad, checkIn, checkOut, resultado);
        }

        return resultado; //Retorno la lista completamente filtrada
    }

	
}
