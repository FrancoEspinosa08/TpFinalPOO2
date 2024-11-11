package nucleo;

import java.time.LocalDateTime;
import java.util.List;

import administrador.Configuracion;
import buscador.Buscador;
import observer.Inmueble;
import observer.Usuario;

public class SitioWeb {
	
	//Atributos
	Sistema sistema;
	Buscador buscador;
	Configuracion configuracion;
	
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
		
		//AL REGISTRAR EL USUARIO SE LE ASIGNA UN RANKING CON CATEGORIAS VALIDAS SEGUN LA CONFIGURACION!
		// Ranking miRanking = new Ranking(configuracion.categoriaPropietario, configuracion.categoriaInquilino)
		// usuario.setRanking(miRanking);
		
		//TODO: Realizar el test de este metodo
		//TODO: Modificar los metodos de puntuar en checkOut para que puntuen sobre la lista de categorias
		// correspondiente "las del propietario" o "las del inquilino" en ranking.
		
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
	Boolean esTipoDeInmuebleValido(Inmueble inmueble){
		
		return this.getConfiguracion().getTipoDeInmuebles().stream()
									  .anyMatch(tipo -> tipo.equals(inmueble.getTipoDeInmueble()));
		
		

	}
	
	//Validamos segun configuracion los servicios del inmueble
	Boolean tieneServiciosValidos(Inmueble inmueble){
		List<Servicio> serviciosInmueble = inmueble.getServicios();
	    List<Servicio> serviciosValidos   = this.getConfiguracion().getServiciosValidos();
		
	return  serviciosValidos.containsAll(serviciosInmueble); //Si la configuracion contiene todos los servicios del inmueble, este es valido, y devuelve true.

	}

	//Reservar previo uso del buscador
	void reservar(Usuario usuario, int index){ //Recibe el resultado de la busqueda del buscador (Atributo resultadoBusqueda) y reserva el inmueble que este en la posicion index.
		//OBS: el parametro usuario es EL inquilino que utiliza este metodo

		Inmueble inmueble = this.getBuscador().getResultadoBusqueda().indexOf(index) // esto es el inmueble que se va a reservar

		this.logicaDeReserva(inmueble,usuario);
				
	}

	//Reservar si soy el siguiente en la lista de espera 
	void reservar(Usuario usuario, Inmueble inmueble){
		
		this.logicaDeReserva(inmueble,usuario);
		
	}

	
	public void logicaDeReserva(Inmueble inmueble,Usuario usuario){

		if(inmueble.esReservado()){
		     inmueble.encolarUsuario(usuario);
		}
		else if (inmueble.getPropietario().decidirSiReservar()){//True si el propietario decide hacer la reserva
			
	            //En caso de aprobarse la reserva se añade al manager
			inmueble.setInquilinoActivo(usuario); //Seteamos el usuario que alquila
			inmueble.setFormaDePago(usuario.seleccionarFormaDePago()) // Seteamos la forma de pago seleccionada por el usuario
			inmueble.setEsReservado(true); 
			usuario.getEmail().setInbox(“Su reserva fue aprobada!”); // Se envia mail de aviso al inquilino

			//Notificamos a los interesados sobre la realizacion de la reserva
			inmueble.notificarSeHaceReserva();
		

		}else{
			throw new IllegalArgumentException("El propietario ha decidido no aprobar la reserva"); // La reserva no fue aprobada. Excepcion!
		}


	}
	
	List<Inmueble> obtenerTodasLasReservasDe(Usuario usuario){
		
		return this.getSistema().getAltas().stream()
						        .filter(inmueble -> inmueble.getInquilinoActivo().equals(usuario)) // Filtramos las reservas del inquilino puntual
						         .toList();
	}

	
	List<Inmueble> obtenerReservasFuturasDe(Usuario usuario){
		
		LocalDateTime fechaActual     = this.getSistema().getFechaActual();

		return this.obtenerTodasLasReservaDe(usuario).stream()
												     .filter(reserva -> esFechaAnterior(fechaActual , reserva.getFechaCheckIn() ) )//aca estamos comparando si la fecha actual es anterior a la del checkIn
												     .toList();
	}



	boolean esFechaAnterior(LocalDateTime primerFecha, LocalDateTime segundaFecha){

		return primerFecha.isBefore(segundaFecha)
	}

	
	List<Inmueble> obtenerReservasEnCiudad(Usuario usuario, String ciudad){
		
		return this.obtenerTodasLasReservasDe(usuario).stream()
								         			  .filter(reserva -> reserva.getCiudad().equals(ciudad)) // Lista de reservas en una ciudad en particular
								         			  .toList();
	}


	List<String> obtenerCiudadesConReservaDe(Usuario usuario){
		
		return this.obtenerTodasLasReservasDe(usuario).stream()
								         		  	  .map(reserva -> reserva.getCiudad()) // Todas las ciudades donde tengo reservas
								         		  	  .toList();
	}

	
	void cancelarReserva(Inmueble reservaACancelar, LocalDateTime diaDeLaCancelacion){
		
	       //PRECONDICION: El parametro diaDeLaCancelacion es la fecha actual. Sistema.getFechaActual();	

	       //PRECONDICION: Este metodo es llamado por un inquilino para cancelar una reserva propia, no puede cancelar reservas de otros inquinos.
		
	       reservaACancelar.getPropietario().getEmail().setInbox(“Se ha cancelado la reserva!”, reservaACancelar); // Enviamos mail para notificar al propietario de la cancelacion
	    

	     //Notifico de la cancelacion del inmueble a los interesados
	      reservaACancelar.notificarCancelacionReserva();

	     //Cancelar reserva aplicando politica de cancelacion correspondiente.
	     reservaACancelar.getPoliticaDeCancelacion().aplicarPenalidad(reservaACancelar, diaDeLaCancelacion);	

	     reservaACancelar.setInquilinoActivo(NULL);
	     
	     this.reservar(reservaACancelar.getUsuariosEnEspera().indexOf(0) , reservaACancelar )
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
							      		       .filter(inmueble -> !inmueble.getEsReservado()) //Filtramos para obtener los NO reservados
							      		       .toList();
	}



	public double tasaDeOcupacion(){ // inmuebles alquilados sobre total de inmuebles
		
		   long totalDeInmuebles 			= this.getSistema().getAltas().size();
		   long cantidadInmueblesAlquilados = this.getSistema().getAltas().stream().filter(inmueble -> inmueble.getEsReservado()).count();

		return (double) cantidadInmueblesAlquilados / totalDeInmuebles;

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
