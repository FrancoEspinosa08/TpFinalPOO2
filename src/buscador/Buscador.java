package buscador;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import administrador.Categoria;
import nucleo.SitioWeb;
import observer.Inmueble;

public class Buscador {
	//Atributos
	private List<Inmueble> resultadoBusqueda; //Aca se almacena el resultado de cada busqueda
	private Filtro filtro; // Filtro a aplicar en la busqueda
	private SitioWeb sitioWeb;
	
	//Metodos
	
	public List<Inmueble> getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	public SitioWeb getSitioWeb() {
		return sitioWeb;
	}
	
	public String visualizar(int index) { 
	    // Visualiza los datos correspondientes al inmueble de la lista resultadoBusqueda[index]

	    Inmueble inmueble = resultadoBusqueda.get(index); // Obtiene el inmueble seleccionado
	    return "Tipo de Inmueble : " + inmueble.getTipoInmueble() + "\n" +
	           "Superficie : " + inmueble.getSuperficie() + "\n" +
	           "Pais: " + inmueble.getPais() + "\n" +
	           "Ciudad: " + inmueble.getCiudad() + "\n" +
	           "Direccion: " + inmueble.getDireccion() + "\n" +
	           "Servicios: " + inmueble.getServicios() + "\n" +  
	           "Capacidad: " + inmueble.getCapacidad() + "\n" +  
	           "Fotos: " + inmueble.getFotos() + "\n" +         
	           "Horarios: " + inmueble.getHorarios() + "\n" +   
	           "Precio: " + inmueble.getPrecio() + "\n" +      
	           "Comentarios: " + inmueble.getComentarios() + "\n" + // Devuelve todos los comentarios del inmueble
	           "Puntajes por categoria : " + this.puntajesPorCategoria(inmueble, inmueble.getRanking().getCategorias()) + "\n" +
	           "Promedio Total : " + inmueble.getRanking().promedioTotal(inmueble.getRanking().getCategorias()) + "\n" +
	           "Promedio Por Categoria: " + this.promedioPorCategoria(inmueble, inmueble.getRanking().getCategorias()) + "\n" +
	           "<----------Informacion del dueño---------->" + "\n" +
	           "Nombre: " + inmueble.getPropietario().getNombre() + "\n" +
	           "Telefono: " + inmueble.getPropietario().getTelefono() + "\n" + 
	           "Email: " + inmueble.getPropietario().getEmail() + "\n" +       
	           "Puntajes por categoria : " + this.puntajePorCategoria(inmueble.getPropietario(), inmueble.getPropietario().getRanking().getCategoriasPropietario()) + "\n" +
	           "Promedio por categoria: " + this.promedioPorCategoria(inmueble.getPropietario(), inmueble.getPropietario().getRanking().getCategoriasPropietario()) + "\n" +
	           "Antiguedad : " + this.antiguedad(inmueble.getPropietario().getFechaDeInscripcion(), LocalDateTime.now()) + "\n" +
	           "Cantidad de veces se alquiló el inmueble: " + inmueble.getVecesAlquilado() + "\n" +
	           "Cantidad de veces que alquiló inmuebles: " + inmueble.getPropietario().cantidadTotalDeAlquileres() + "\n" +
	           "Inmuebles Alquilados: " + this.tiposDeInmueblesEn(inmueble.getPropietario().getInmuebles()) + "\n";
	}

	public String tiposDeInmueblesEn(List<Inmueble> inmuebles) { //contiene los tipos de inmuebles presentes en la lista inmuebles
	    String resultado = "";	

	    for(Inmueble i : inmuebles) {
	        resultado += i.getTipoInmueble() + "\n";
	    }

	    return resultado;
	}
	
	long antiguedad(LocalDateTime fechaAnterior, LocalDateTime fechaActual){ //indica cuántos días han pasado entre las dos fechas dadas.
		//PRECONDICION: “fechaAnterior” es una fecha anterior a “fechaActual”
		return 	ChronoUnit.DAYS.between(fechaAnterior, fechaActual);
	}
	
	public String puntajePorCategoria(Puntuable elemento, List<Categoria> categorias) { //contiene, para cada categoría, la cantidad de votos que tiene para cada puntaje. 
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
	
	public String promedioPorCategoria(Puntuable elemento, List<Categoria> categorias) { //muestra el nombre de cada categoría junto con su puntaje promedio
	    String salida = "";

	    for (Categoria c : categorias) {
	        salida += c.getNombre() + ": " + c.promedio() + "\n";
	    }

	    return salida;
	}
	
	public void buscar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut) {
	    // PRECONDICIÓN: Antes de ejecutar este método se debe haber seteado un filtro.
		
	    // Realiza la búsqueda aplicando el filtro definido y guarda el resultado en resultadoBusqueda
	    this.resultadoBusqueda = this.getFiltro().filtrar(ciudad, checkIn, checkOut, this.getSitioWeb().getSistema().getAltas());
	}

	public void addFiltro(Filtro filtro) { // Agrega un filtro para realizar la búsqueda
	    this.filtro = filtro; 
	}

	public void removeFiltro() { // Remueve el filtro que se utiliza en la búsqueda
	    this.filtro = null;
	}

}
