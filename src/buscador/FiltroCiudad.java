package buscador;

import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroCiudad extends Filtro {

    // Constructor que llama al constructor de la clase base
    public FiltroCiudad() {
        super();
    }

    // Implementación de la función filtrar que filtra según la ciudad
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Filtrar inmuebles por ciudad
        List<Inmueble> resultado = inmuebles.stream()
            .filter(inmueble -> inmueble.getCiudad().equals(ciudad))
            .toList();

        // Aplicar los filtros obligatorios (CheckIn, CheckOut, etc.) si existen
        if (filtrosObligatorios != null && !filtrosObligatorios.isEmpty()) {
            // Llamamos a los filtros obligatorios, pasándoles también checkIn y checkOut
            resultado = filtrosObligatorios(ciudad, checkIn, checkOut, resultado);
        }
        return resultado;
    }

    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        return filtrar(ciudad, checkIn, checkOut, inmuebles);
    }
}