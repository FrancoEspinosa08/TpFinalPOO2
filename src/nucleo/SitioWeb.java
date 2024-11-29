package nucleo;

import java.time.LocalDateTime;
import java.util.List;

import administrador.Configuracion;
import administrador.Servicio;
import buscador.Buscador;
import inmuebleYUsuario.RankingUsuario;
import observer.Inmueble;
import observer.Reserva;
import observer.Usuario;

public class SitioWeb {
	
	//Atributos
	private Sistema sistema;
	private Buscador buscador;
	private Configuracion configuracion;
	
	
	//Constructor
	public SitioWeb(Sistema sistema, Buscador buscador, Configuracion configuracion) {
		this.sistema = sistema;
		this.buscador = buscador;
		this.configuracion = configuracion;
	}
	
	
	//Metodos
	//Registrar un usuario en el sistema
	public void registrar(Usuario usuario) {
		//le setea la fechaActual del sistema a la fechaDeInscripcion del usuario. 
		
		usuario.setFechaDeInscripcion(this.getSistema().getFechaActual());
		
		//Asignar al usuario un ranking con categorias actualizadas segun configuracion
		RankingUsuario ranking = new RankingUsuario(this.getConfiguracion().getCategoriasInquilinos(),
													this.getConfiguracion().getCategoriasPropietario());
		usuario.setRanking(ranking);
		
		// Añade el usuario a usuariosRegistrados
		this.getSistema().addUsuario(usuario);

	}
	
	//Publicar / dar de alta un inmueble en el sistema
	public void publicar(Inmueble inmueble){ 
		
		//Validamos que el inmueble tenga los parametros validos seteados por el administrador en Configuracion
		if(esTipoDeInmuebleValido(inmueble) && tieneServiciosValidos(inmueble)){
			this.getSistema().addAlta(inmueble); // Lo registramos en altas publicadas
		}
		else{
		    throw new IllegalArgumentException("El inmueble que se quiere publicar es invalido");
		}


	}

	//Validamos segun configuracion el tipo de inmueble
	public Boolean esTipoDeInmuebleValido(Inmueble inmueble){
		
		return this.getConfiguracion().getTipoInmuebles().stream()
									  .anyMatch(tipo -> tipo.equals(inmueble.getTipoDeInmueble()));
		
		

	}
	
	//Validamos segun configuracion los servicios del inmueble
	public Boolean tieneServiciosValidos(Inmueble inmueble){
		List<Servicio> serviciosInmueble = inmueble.getServicios();
	    List<Servicio> serviciosValidos   = this.getConfiguracion().getServiciosValidos();
		
	return  serviciosValidos.containsAll(serviciosInmueble); //Si la configuracion contiene todos los servicios del inmueble, este es valido, y devuelve true.

	}

	//Reservar previo uso del buscador
	public void reservar(Usuario usuario, int index){ //Recibe el resultado de la busqueda del buscador (Atributo resultadoBusqueda) y reserva el inmueble que este en la posicion index.
		//OBS: el parametro usuario es EL inquilino que utiliza este metodo

		Inmueble inmueble = this.getBuscador().getResultadoBusqueda().get(index); // esto es el inmueble que se va a reservar

		
		//this.logicaDeReserva(reservaPendiente , usuario);		VER!
		this.logicaDeReserva(inmueble,usuario);
		
	}

	//Reservar si soy el siguiente en la lista de espera 
	public void reservar(Usuario usuario, Reserva reservaPendiente){
		
		this.logicaDeReserva(reservaPendiente , usuario);
		
	}

	//La logica de reserva se encarga de manejar los controles
	//necesarios antes de poder reservar un inmueble.
	public void logicaDeReserva(Reserva reserva , Usuario usuario){

		if(reserva.getInmueble().hayReservaActivaEntre(reserva.getCheckIn(), reserva.getCheckOut())){ //Si no hay vacante
			
		     reserva.getInmueble().addReservaPendiente(reserva); //Si no hay lugar, pasa a ser reserva pendiente
		}
		else if (reserva.getInmueble().getPropietario().decidirSiReserva()){//True si el propietario decide hacer la reserva
			
	        
			reserva.setFormaDePago(usuario.seleccionarFormaDePago(reserva.getInmueble().getFormasDePago())); // Seteamos la forma de pago seleccionada por el usuario

			usuario.getEmail().setInbox("Su reserva fue aprobada!"); // Se envia mail de aviso al inquilino

			//Notificamos a los interesados sobre la realizacion de la reserva
			reserva.getInmueble().notificarSeHaceReserva();
		

		}else{
			throw new IllegalArgumentException("El propietario ha decidido no aprobar la reserva"); // La reserva no fue aprobada. Excepcion!
		}


	}
	
	public List<Reserva> obtenerTodasLasReservasDe(Usuario usuario){
		
		return this.getSistema().getAltas().stream()
						        .map(inmueble -> inmueble.getReservasActivas()) // lista de lista de reservas
						        .flatMap(r -> r.stream())						// stream de lista unica
						        .filter(r -> r.getInquilino().equals(usuario))	// filtramos solo las reservas del usuario que queremos
						        .toList();										// Transformamos en lista
	}

	
	public List<Reserva> obtenerReservasFuturasDe(Usuario usuario){
		
		LocalDateTime fechaActual     = this.getSistema().getFechaActual();

		return this.obtenerTodasLasReservasDe(usuario).stream()
												      .filter(reserva -> fechaActual.isBefore(reserva.getCheckIn())) // Comparamos si la fecha actual es anterior a la del checkIn
												      .toList();
	}



	
	public List<Reserva> obtenerReservasEnCiudad(Usuario usuario, String ciudad){
		
		return this.obtenerTodasLasReservasDe(usuario).stream()
								         			  .filter(reserva -> reserva.getInmueble().getCiudad().equals(ciudad)) // Lista de reservas en una ciudad en particular
								         			  .toList();
	}


	public List<String> obtenerCiudadesConReservaDe(Usuario usuario){
		
		return this.obtenerTodasLasReservasDe(usuario).stream()
								         		  	  .map(reserva -> reserva.getInmueble().getCiudad()) // Todas las ciudades donde tengo reservas
								         		  	  .toList();
	}

	
	public void cancelarReserva(Reserva reservaACancelar, LocalDateTime diaDeLaCancelacion){
		
	       //PRECONDICION: El parametro diaDeLaCancelacion es la fecha actual. Sistema.getFechaActual();	

	       //PRECONDICION: Este metodo es llamado por un inquilino para cancelar una reserva propia, no puede cancelar reservas de otros inquinos.
		
	       reservaACancelar.getInmueble().getPropietario().getEmail().setInbox("Se ha cancelado la reserva!", reservaACancelar); // Enviamos mail para notificar al propietario de la cancelacion
	    

	     //Notifico de la cancelacion del inmueble a los interesados
	      reservaACancelar.getInmueble().notificarCancelacionReserva();

	     //Cancelar reserva aplicando politica de cancelacion correspondiente.
	     reservaACancelar.getInmueble().getPoliticaDeCancelacion().aplicarPenalidad(reservaACancelar, diaDeLaCancelacion);	

	     
	     if(reservaACancelar.getInmueble().hayReservaPendienteEntre(reservaACancelar.getCheckIn(), reservaACancelar.getCheckOut())) {
	    	 
	    	 Reserva reservaPendiente = reservaACancelar.getInmueble().reservaPendienteEntre(reservaACancelar.getCheckIn(), reservaACancelar.getCheckOut()).getFirst();
	    	 
	    	//Pasa a reservar el siguiente usuario en la lista de espera
	    	 this.reservar(reservaPendiente.getInquilino() , reservaPendiente);
	     }
	     
	     
	     
	   
	}

	
	//Metodos de gestion que utilizaria el administrador del sitio

	public List<Usuario> topTenUsuariosConMasAlquileres(){

		return this.getSistema().getUsuariosRegistrados().stream()
								           				   .filter(usuario -> usuario.getVecesQueAlquilo() >= 1)
								           				   .sorted((a , b) ->  b.getVecesQueAlquilo() - a.getVecesQueAlquilo()) // Ordena en orden descendente
								           				   .limit(10) // Toma los primeros 10 elementos
								           				   .toList();
	}




	public List<Inmueble> obtenerTodosLosInmueblesLibres(){

			return this.getSistema().getAltas().stream()
							      		       .filter(inmueble -> !inmueble.esReservado()) //Filtramos para obtener los NO reservados
							      		       .toList();
	}



	public double tasaDeOcupacion(){ // inmuebles alquilados sobre total de inmuebles
		
		   double totalDeInmuebles 			  = this.getSistema().getAltas().size();
		   double cantidadInmueblesAlquilados = this.getSistema().getAltas().stream().filter(inmueble -> inmueble.esReservado()).count();

		return  cantidadInmueblesAlquilados / totalDeInmuebles;

	}

	
	
	
	
	
	//Getters and Setters
	public Buscador getBuscador() {
		return this.buscador;
	}
	
	public Sistema getSistema() {
		return this.sistema;
	}
	
	public Configuracion getConfiguracion() {
		return this.configuracion;
	}
}
