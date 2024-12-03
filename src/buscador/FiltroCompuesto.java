package buscador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import observer.Inmueble;

public class FiltroCompuesto extends Filtro {

    // Atributos
    private List<Filtro> filtros;

    // Constructor
    public FiltroCompuesto(List<Filtro> filtros, List<Filtro> filtrosObligatorios) {
        this.filtros = filtros;
        this.setFiltrosObligatorios(filtrosObligatorios); // Configura los filtros obligatorios
    }

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

        // Aplico los filtros obligatorios
        resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, altas);

        // Aplica los filtros compuestos
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    // Método que aplica los filtros compuestos sobre la lista de inmuebles
    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        List<Inmueble> resultado = new ArrayList<>(inmuebles); // Copio la lista de inmuebles a una variable

        // Aplica todos los filtros de la lista "filtros" a la variable resultado (List<Inmueble>)
        for (Filtro filtro : filtros) {
            resultado = filtro.filtrar(ciudad, checkIn, checkOut, resultado);
        }

        return resultado; // Retorno la lista completamente filtrada
    }
}
