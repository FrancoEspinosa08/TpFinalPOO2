package buscador;
import java.time.LocalDateTime;	 
import java.util.List;
import observer.Inmueble;

public abstract class Filtro {
    protected List<Filtro> filtrosObligatorios;

    // Constructor vacío
    public Filtro() {}

    // Método abstracto que deben implementar las subclases
    public abstract List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas);
    public abstract List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles);

    // Método que aplica todos los filtros obligatorios en orden
    public List<Inmueble> filtrosObligatorios(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        List<Inmueble> resultado = inmuebles;
        for (Filtro filtro : filtrosObligatorios) {
            resultado = filtro.filtrar(ciudad, checkIn, checkOut, resultado);
        }
        return resultado;
    }

    // Setter para los filtros obligatorios
    public void setFiltrosObligatorios(List<Filtro> filtrosObligatorios) {
        this.filtrosObligatorios = filtrosObligatorios;
    }
}