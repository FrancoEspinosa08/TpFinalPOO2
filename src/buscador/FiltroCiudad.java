package buscador;

import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroCiudad extends Filtro {

	

    // Implementación de la función filtrar que filtra según la ciudad
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
    	
        //Aplicamos los filtros obligatorios
        List<Inmueble> resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, inmuebles);

        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        return inmuebles.stream()
				 	    .filter(inmueble -> inmueble.getCiudad().equals(ciudad)) //Filtramos por ciudad
				 	    .toList();
    }
}