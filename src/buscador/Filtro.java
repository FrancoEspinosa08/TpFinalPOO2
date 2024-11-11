package buscador;
import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public abstract class Filtro {

    // Método que filtra los inmuebles según la ciudad, fecha de check-in y check-out
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        List<Inmueble> resultado; 

        // Filtra los inmuebles por ciudad
        resultado = this.filtroCiudad(ciudad, altas);

        // Filtra los inmuebles por fecha de check-in
        resultado = this.filtroCheckIn(checkIn, resultado);

        // Filtra los inmuebles por fecha de check-out
        resultado = this.filtroCheckOut(checkOut, resultado);

        return resultado;
    }

    // Método abstracto que debe ser implementado por las clases que extiendan Filtro
    public abstract List<Inmueble> filtro(List<Inmueble> inmuebles);

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
}