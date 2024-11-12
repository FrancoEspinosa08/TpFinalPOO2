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

    // Método que filtra los inmuebles por ciudad, fecha de check-in, check-out y aplicando filtros compuestos
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        List<Inmueble> resultado;

        // Filtra los inmuebles por ciudad
        resultado = this.filtroCiudad(ciudad, altas);

        // Filtra los inmuebles por fecha de check-in
        resultado = this.filtroCheckIn(checkIn, resultado);

        // Filtra los inmuebles por fecha de check-out
        resultado = this.filtroCheckOut(checkOut, resultado);

        // Aplica los filtros compuestos
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    // Método que aplica los filtros compuestos sobre la lista de inmuebles
    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        List<Inmueble> resultado = new ArrayList<>(inmuebles);

        // Aplica todos los filtros de la lista "filtros"
        for (Filtro filtro : filtros) {
            resultado = filtro.filtrar(ciudad, checkIn, checkOut, resultado);
        }

        return resultado;
    }

	@Override
	public List<Inmueble> filtro(List<Inmueble> inmuebles) {
		// TODO Auto-generated method stub
		return null;
	}
}
