package buscador;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import administrador.Categoria;
import nucleo.SitioWeb;
import observer.Inmueble;

public class Buscador {
    // Atributos
    private Filtro filtro; // Filtro a aplicar en la búsqueda
    private SitioWeb sitioWeb;

    // Constructor
    public Buscador(SitioWeb sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public SitioWeb getSitioWeb() {
        return sitioWeb;
    }

    public Filtro getFiltro() {
        return filtro;
    }

    public void addFiltro(Filtro filtro) { // Agrega un filtro para realizar la búsqueda
    	
    	//PRECONDICION: El Filtro filtro que se va a agregar no debe ser uno de los filtros obligatorios.
    	
        this.filtro = filtro;
    }

    public void removeFiltro() { // Remueve el filtro que se utiliza en la búsqueda
        this.filtro = null;
    }

    public List<Inmueble> buscar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut) {
        // PRECONDICIÓN: Antes de ejecutar este método se debe haber seteado un filtro.
        if (this.filtro == null) {
            throw new IllegalStateException("No se ha configurado un filtro para realizar la búsqueda.");
        }

        // Realiza la búsqueda aplicando el filtro y devuelve el resultado
        return this.getFiltro().filtrar(ciudad, checkIn, checkOut, this.getSitioWeb().getSistema().getAltas());
    }

    public String visualizar(int index, String ciudad, LocalDateTime checkIn, LocalDateTime checkOut) {
        // Obtiene los resultados de la búsqueda
        List<Inmueble> inmuebles = this.buscar(ciudad, checkIn, checkOut);

        // Verificar si el índice es válido
        if (index < 0 || index >= inmuebles.size()) {
            return "Índice fuera de rango. No se puede visualizar el inmueble.";
        }

        // Obtener el inmueble seleccionado
        Inmueble inmueble = inmuebles.get(index);

        // Construir la información del inmueble
        return "Tipo de Inmueble: " + inmueble.getTipoDeInmueble() + "\n" +
               "Superficie: " + inmueble.getSuperficie() + "\n" +
               "País: " + inmueble.getPais() + "\n" +
               "Ciudad: " + inmueble.getCiudad() + "\n" +
               "Dirección: " + inmueble.getDireccion() + "\n" +
               "Servicios: " + inmueble.getServicios() + "\n" +
               "Capacidad: " + inmueble.getCapacidad() + "\n" +
               "Fotos: " + inmueble.getFotos() + "\n" +
               "Fecha de ingreso: " + inmueble.getFechaCheckIn() + "\n" +
               "Fecha de egreso: " + inmueble.getFechaCheckOut() + "\n" +
               "Precio: " + inmueble.getPrecioPorDia() + "\n" +
               "Comentarios: " + inmueble.getComentarios() + "\n" +
               "Puntajes por categoría: " + this.puntajePorCategoria(inmueble.getRanking().getCategorias()) + "\n" +
               "Promedio Total: " + inmueble.getRanking().promedioTotal(inmueble.getRanking().getCategorias()) + "\n" +
               "Promedio por Categoría: " + this.promedioPorCategoria(inmueble.getRanking().getCategorias()) + "\n" +
               "<----------Información del dueño---------->\n" +
               "Nombre: " + inmueble.getPropietario().getNombre() + "\n" +
               "Teléfono: " + inmueble.getPropietario().getTelefono() + "\n" +
               "Email: " + inmueble.getPropietario().getEmail().toString() + "\n" +
               "Puntajes por categoría: " + this.puntajePorCategoria(inmueble.getPropietario().getRanking().getCategoriasPropietario()) + "\n" +
               "Promedio por categoría: " + this.promedioPorCategoria(inmueble.getPropietario().getRanking().getCategoriasPropietario()) + "\n" +
               "Antigüedad: " + this.antiguedad(inmueble.getPropietario().getFechaDeInscripcion(), LocalDateTime.now()) + "\n" +
               "Cantidad de veces que se alquiló el inmueble: " + inmueble.getVecesAlquilado() + "\n" +
               "Cantidad de veces que alquiló inmuebles: " + inmueble.getPropietario().cantidadTotalDeAlquileres() + "\n" +
               "Inmuebles Alquilados: " + this.tiposDeInmueblesEn(inmueble.getPropietario().getInmuebles()) + "\n";
    }

    public String tiposDeInmueblesEn(List<Inmueble> inmuebles) {
        String resultado = "";

        for (Inmueble i : inmuebles) {
            resultado += i.getTipoDeInmueble() + "\n";
        }

        return resultado;
    }

    public long antiguedad(LocalDateTime fechaAnterior, LocalDateTime fechaActual) {
        return ChronoUnit.DAYS.between(fechaAnterior, fechaActual);
    }

    public String puntajePorCategoria(List<Categoria> categorias) {
        String salida = "";

        for (Categoria c : categorias) {
            salida += c.getNombre() + ":\n" +
                      "5 Puntos: " + c.cantidadQuePuntuaronCon(5) + "\n" +
                      "4 Puntos: " + c.cantidadQuePuntuaronCon(4) + "\n" +
                      "3 Puntos: " + c.cantidadQuePuntuaronCon(3) + "\n" +
                      "2 Puntos: " + c.cantidadQuePuntuaronCon(2) + "\n" +
                      "1 Punto: " + c.cantidadQuePuntuaronCon(1) + "\n";
        }

        return salida;
    }

    public String promedioPorCategoria(List<Categoria> categorias) {
        String salida = "";

        for (Categoria c : categorias) {
            salida += c.getNombre() + ": " + c.promedio() + "\n";
        }

        return salida;
    }
}
