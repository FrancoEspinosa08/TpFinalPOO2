package buscador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import observer.Inmueble;

public abstract class Filtro {
	private final Filtro filtroCiudad = new FiltroCiudad();
	private final Filtro filtroCheckIn = new FiltroCheckIn();
	private final Filtro filtroCheckOut = new FiltroCheckOut();

	public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        return this.filtrosObligatorios(ciudad, checkIn, checkOut, altas);
    }

    public List<Inmueble> filtrosObligatorios(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        List<Inmueble> resultado = this.filtroCiudad(ciudad, inmuebles);
        resultado = this.filtroCheckIn(checkIn, resultado);
        resultado = this.filtroCheckOut(checkOut, resultado);
        return resultado;
    }

    // Método abstracto que debe ser implementado por las clases que extiendan Filtro
    public abstract List<Inmueble> filtro(List<Inmueble> inmuebles);
    public abstract List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles);
    // Filtra los inmuebles que están en la ciudad especificada
    List<Inmueble> filtroCiudad(String ciudad, List<Inmueble> inmuebles) {
        return inmuebles.stream()
                        .filter(inmueble -> inmueble.getCiudad().equals(ciudad)) // Filtra por ciudad
                        .toList();
    }

    // Filtra los inmuebles que tienen la fecha de check-in igual a la proporcionada
    List<Inmueble> filtroCheckIn(LocalDateTime checkIn, List<Inmueble> inmuebles) {
        return inmuebles.stream()
                        .filter(inmueble -> inmueble.getFechaCheckIn().equals(checkIn)) // Filtra por fecha de check-in
                        .toList();
    }

    // Filtra los inmuebles que tienen la fecha de check-out igual a la proporcionada
    List<Inmueble> filtroCheckOut(LocalDateTime checkOut, List<Inmueble> inmuebles) {
        return inmuebles.stream()
                        .filter(inmueble -> inmueble.getFechaCheckOut().equals(checkOut)) // Filtra por fecha de check-out
                        .toList();
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