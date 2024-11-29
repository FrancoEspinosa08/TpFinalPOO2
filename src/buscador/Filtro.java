package buscador;
import java.time.LocalDateTime;	
import java.util.List;
import observer.Inmueble;

public abstract class Filtro {
	
	private final Filtro filtroCiudad = new FiltroCiudad();
	private final Filtro filtroCheckIn = new FiltroCheckIn();
	private final Filtro filtroCheckOut = new FiltroCheckOut();

	
	// Método abstracto que debe ser implementado por las clases que extiendan Filtro
    public abstract List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas);
    public abstract List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles);
    
    
     


    public List<Inmueble> filtrosObligatorios(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        
    	List<Inmueble> resultado = this.getFiltroCiudad().filtrar(ciudad , checkIn, checkOut, inmuebles);  // 1ro filtro por ciudad
        resultado 				 = this.getFiltroCheckIn().filtrar(ciudad , checkIn, checkOut, resultado); // 2do filtro por checkIn
        resultado 				 = this.getFiltroCheckOut().filtrar(ciudad , checkIn, checkOut, resultado);// 3ro filtro por checkOut
       
        return resultado; // Devuelvo la lista completamente filtrada
    }

   
    public Filtro getFiltroCiudad() {
		return filtroCiudad;
	}

	public Filtro getFiltroCheckIn() {
		return filtroCheckIn;
	}

	public Filtro getFiltroCheckOut() {
		return filtroCheckOut;
	}
}