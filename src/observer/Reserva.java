package observer;

import java.time.LocalDateTime;

import inmuebleYUsuario.FormaDePago;

public class Reserva {

	//Atributos
	private Usuario inquilino;
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	private FormaDePago formaDePago;
	private Inmueble inmueble;
	
	
	//Constructor
	public Reserva(Usuario inquilino, LocalDateTime checkIn, LocalDateTime checkOut, Inmueble inmueble) {
		this.inquilino 	 = inquilino;
		this.checkIn 	 = checkIn;
		this.checkOut 	 = checkOut;
		this.inmueble    = inmueble;
	}



	
	//Metodos
	
	
	
	
	
	
	// Getters and Setters
	
	public Usuario getInquilino() {
		return this.inquilino;
	}



	public LocalDateTime getCheckIn() {
		return this.checkIn;
	}



	public LocalDateTime getCheckOut() {
		return this.checkOut;
	}



	public FormaDePago getFormaDePago() {
		return this.formaDePago;
	}

	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}


	public Inmueble getInmueble() {
		return inmueble;
	}


	
	
}
