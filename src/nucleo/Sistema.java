package nucleo;

import java.time.LocalDateTime;
import java.util.List;

import observer.Inmueble;
import observer.Usuario;

public class Sistema {

	//Atributos
	private List<Usuario> usuariosRegistrados; //Lista de usuarios registrados en el sistema
	private List<Inmueble> altas;
	private LocalDateTime fechaActual = LocalDateTime.now();

	
	
	//Constructor
	
	
	
	//Metodos
	public void Actualizar() {

		//Filtramos las reservas que esten vencidas y llamamos a checkOut() con cada una
		//OBS: Se asume que este metodo se llama regularmente.	
		this.getTodasLasReservas().stream() // Transformamos la lista de reservas en un stream
		                          .filter(reserva -> reserva.getFechaCheckOut().equals(this.fechaActual)) // Filtramos para obtener las reservas vencidas
						          .forEach(reserva -> this.checkOut(reserva.getInquilinoActivo(), reserva)); // Hacemos checkout por cada una

	}
	
	//Se realiza el checkOut, se puntua y se comenta.
	public void checkOut(Usuario usuario, Inmueble inmueble){ 

		this.puntuarInmueble(inmueble, usuario);
		this.puntuarPropietario(inmueble.getPropietario(), usuario);
		this.puntuarInquilino(inmueble.getPropietario(), usuario);
		this.añadirComentario(usuario.generarComentario(), inmueble);
		this.incrementarVecesAlquiladoDe(inmueble, inmueble.getPropietario());
	    usuario.incrementarVecesQueAlquilo(); // Feo pero no queda otra
		this.removeAlta(inmueble); //Eliminar reserva
	} 

	public void puntuarInmueble(Inmueble inmueble, Usuario usuario) //El inquilino puntua un inmueble en todas las categorías
	{
		inmueble.getRanking().getCategorias().streams().forEach(c-> c.setPuntaje(usuario.puntuar()));	
	}

	public void puntuarPropietario(Usuario propietario, Usuario inquilino)//El inquilino puntua a un propietario en todas las categorías
	{ 
		propietario.getRanking().getCategoriasPropietario().streams().forEach(categoria -> categoria.setPuntaje(inquilino.puntuar()));
	}


	public void puntuarInquilino(Usuario propietario,Usuario  inquilino) //El propietario puntua a un inquilino en todas las categorías
	{
		inquilino.getRanking().getCategoriasInquilino().streams().forEach(categoria -> categoria.setPuntaje(propietario.puntuar()));
	} 

	public void añadirComentario(String comentario, Inmueble inmueble){ // comentario = inquilino.generarComentario() dentro del checkout
		inmueble.añadirComentario(comentario);
	}

	public void incrementarVecesAlquiladoDe(Inmueble inmuebleDeReserva, Usuario propietario){
	//Busca el inmueble en la lista de inmuebles del propietario y le incrementa el contador de veces alquilado
	//Precondicion: El inmueble existe en la lista de inmuebles del propietario
		buscarInmuebleEn( inmuebleDeReserva , propietario.getInmuebles()).incrementarVecesAlquilado();
	}

	public Inmueble buscarInmuebleEn(Inmueble inmuebleABuscar, List<Inmueble> inmuebles){ //Busca un inmueble en particular en la lista de inmuebles.
		//Devuelve un inmueble o null si no lo encuentra
        return inmuebles.stream()
        .filter( i -> i.equals(inmuebleABuscar))
        .findFirst()
        .orElse(null);
	}

	public List<Inmueble> getTodasLasReservas(){ // Devuelve la lista de todos los inmuebles que estan reservados
		
		return this.getAltas().stream()
        .filter(inmueble -> inmueble.getEsReservado())
        .toList();
	}

	
	public void removeAlta(Inmueble inmueble) {
		this.getAltas().remove(inmueble);
	}
	
	public void addAlta(Inmueble inmueble) {
		this.getAltas().add(inmueble);
	}
	
	
	//Getters and Setters
	
	public LocalDateTime getFechaActual() {
		 return this.fechaActual;
	}
	
	public List<Inmueble> getAltas(){
		return this.altas;
	}
	
	
	
	
	
	
}
