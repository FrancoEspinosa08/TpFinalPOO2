package nucleo;

import java.time.LocalDateTime;	
import java.util.ArrayList;
import java.util.List;

import observer.Inmueble;
import observer.Reserva;
import observer.Usuario;

//Sistema principal

public class Sistema {

	//Atributos
	private List<Usuario> usuariosRegistrados= new ArrayList<>(); //Lista de usuarios registrados en el sistema
	private List<Inmueble> altas = new ArrayList<>();
	private LocalDateTime fechaActual = LocalDateTime.now();

	
	
	//Constructor
	
	
	
	//Metodos
	public void Actualizar() {
		
		
		//Filtramos las reservas que esten vencidas y llamamos a checkOut() con cada una
		//OBS: Se asume que este metodo se llama regularmente.	
		this.getTodasLasReservasActivas().stream() 
		                          .filter(reserva -> reserva.getCheckOut().equals(this.fechaActual)) // Filtramos para obtener las reservas vencidas
						          .forEach(reserva -> this.checkOut(reserva.getInquilino(), reserva)); // Hacemos checkout por cada una

	}
	
	//Se realiza el checkOut, se puntua y se comenta.
	public void checkOut(Usuario usuario, Reserva reserva){ //El usuario es el inquilino de la reserva

		this.puntuarInmueble(reserva.getInmueble(), usuario);
		this.puntuarPropietario(reserva.getInmueble().getPropietario(), usuario);
		this.puntuarInquilino(reserva.getInmueble().getPropietario(), usuario);
		this.añadirComentario(usuario.generarComentario(), reserva.getInmueble());
		this.incrementarVecesAlquiladoDe(reserva.getInmueble(), reserva.getInmueble().getPropietario());
	    usuario.incrementarVecesQueAlquilo(); // Feo pero no queda otra

	    //Eliminar reserva
	    reserva.getInmueble().removeReservaActiva(reserva);
	} 

	public void puntuarInmueble(Inmueble inmueble, Usuario usuario) //El inquilino puntua un inmueble en todas las categorías
	{
		inmueble.getRanking().getCategorias().stream().forEach(c-> c.addPuntaje(usuario.puntuar()));	
	}

	public void puntuarPropietario(Usuario propietario, Usuario inquilino)//El inquilino puntua a un propietario en todas las categorías
	{ 
		propietario.getRanking().getCategoriasPropietario().stream().forEach(categoria -> categoria.addPuntaje(inquilino.puntuar()));
	}


	public void puntuarInquilino(Usuario propietario,Usuario  inquilino) //El propietario puntua a un inquilino en todas las categorías
	{
		inquilino.getRanking().getCategoriasInquilino().stream().forEach(categoria -> categoria.addPuntaje(propietario.puntuar()));
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

	public List<Reserva> getTodasLasReservasActivas(){ // Devuelve la lista de todos los inmuebles que estan reservados
		
		return this.getAltas().stream()
				   .map(inmueble -> inmueble.getReservasActivas()) //Obtengo cada lista de reservas
				   .flatMap(lista -> lista.stream())			   //Fusiono todo en un solo stream
				   .toList();									   //List<Reserva>
        
	}

	
	public void removeAlta(Inmueble inmueble) {
		this.getAltas().remove(inmueble);
	}
	
	public void addAlta(Inmueble inmueble) {
		this.getAltas().add(inmueble);
	}
	
	public void addUsuario(Usuario usuario) {
		this.usuariosRegistrados.add(usuario);
	}
	
	//Getters and Setters
	
	public LocalDateTime getFechaActual() {
		 return this.fechaActual;
	}
	
	//Permite setear la fecha para poder testear 
	public void setFechaActual(LocalDateTime nuevaFecha) {
		 this.fechaActual = nuevaFecha;
	}
	
	
	public List<Inmueble> getAltas(){
		return this.altas;
	}

	public List<Usuario> getUsuariosRegistrados() {
		return this.usuariosRegistrados;
	}
	
	
	
	
	
	
}
