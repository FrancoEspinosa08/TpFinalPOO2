package buscador;

import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroCheckIn extends Filtro {

	
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Aplica los filtros obligatorios antes de aplicar el filtro de check-in
        List<Inmueble> resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, inmuebles);

        // Luego aplica el filtro específico de check-in
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Filtra por check-in
        return inmuebles.stream()
                .filter(inmueble -> inmueble.getFechaCheckIn().equals(checkIn))
                .toList();
    }
}
