package observer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import administrador.Servicio;
import inmuebleYUsuario.Evento;
import inmuebleYUsuario.FormaDePago;
import inmuebleYUsuario.Ranking;
import politicasDeCancelacion.PoliticaDeCancelacion;

public class Inmueble extends Subject {
	private Usuario propietario;
	private Usuario InquilinoActivo;
	private List<Usuario> usuariosEnEspera = new ArrayList<>();
	private int vecesAlquilado;
	private String tipoDeInmueble;
	private int superficie;
	private String pais;
	private String ciudad;
	private String direccion;
	private List<Servicio> servicios = new ArrayList<>();
	private int capacidad;
	private List<String> fotos;
	private LocalDateTime horarioCheckIn;
	private LocalDateTime horarioCheckOut;
	private FormaDePago formaDePago;
	private List<String> comentarios;
	private Ranking ranking;
	private List<FormaDePago> formaPagoValidas;
	private float precioPorDia;
	private boolean esReservado;
	private List<Evento> eventos;
	private PoliticaDeCancelacion politicaDeCancelacion;
	
	
	
	public Inmueble(Usuario propietario, Usuario inquilinoActivo, int vecesAlquilado, String tipoDeInmueble, int superficie,
			String pais, String ciudad, String direccion, int capacidad, List<String> fotos, LocalDateTime horarioCheckIn,
			LocalDateTime horarioCheckOut, FormaDePago formaDePago, List<String> comentarios,
			Ranking ranking, List<FormaDePago> formaPagoValidas, float precioPorDia, boolean esReservado,
			List<Evento> eventos, PoliticaDeCancelacion politicaDeCancelacion) {
		this.propietario = propietario;
		InquilinoActivo = inquilinoActivo;
		this.vecesAlquilado = vecesAlquilado;
		this.tipoDeInmueble = tipoDeInmueble;
		this.superficie = superficie;
		this.pais = pais;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.capacidad = capacidad;
		this.fotos = fotos;
		this.horarioCheckIn = horarioCheckIn;
		this.horarioCheckOut = horarioCheckOut;
		this.formaDePago = formaDePago;
		this.comentarios = comentarios;
		this.ranking = ranking;
		this.formaPagoValidas = formaPagoValidas;
		this.precioPorDia = precioPorDia;
		this.esReservado = esReservado;
		this.eventos = eventos;
		this.politicaDeCancelacion = politicaDeCancelacion;
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public Usuario getInquilinoActivo() {
		return InquilinoActivo;
	}
	
	public void setInquilinoActivo(Usuario inquilinoActivo) {
		InquilinoActivo = inquilinoActivo;
	}
	
	public List<Usuario> getUsuariosEnEspera() {
		return usuariosEnEspera;
	}
	
	public void encolarUsuario(Usuario usuario) {
		this.usuariosEnEspera = usuariosEnEspera;
	}
	
	public FormaDePago getFormaDePago() {
		return formaDePago;
	}
	
	
	public List<FormaDePago> getFormasDePago() {
		return formaPagoValidas;
	}
	
	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}
	
	public List<String> getComentarios() {
		return comentarios;
	}

	public float getPrecioPorDia() {
		return precioPorDia;
	}
	
	public void setPrecioPorDia(float nuevoPrecio) {
		if (this.getPrecioPorDia() > nuevoPrecio) {
			this.notificarBajaDePrecio();
		}
		this.precioPorDia = nuevoPrecio;
	}
	
	public boolean getEsReservado() {
		return esReservado;
	}
	
	public void setEsReservado(boolean esReservado) {
		this.esReservado = esReservado;
	}
	
	public int getVecesAlquilado() {
		return vecesAlquilado;
	}
	
	public String getTipoDeInmueble() {
		return tipoDeInmueble;
	}

	public int getSuperficie() {
		return superficie;
	}

	public String getPais() {
		return pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}
	
	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public List<String> getFotos() {
		return fotos;
	}

	public LocalDateTime getFechaCheckIn() {
		return horarioCheckIn;
	}
	
	public LocalDateTime getFechaCheckOut() {
		return horarioCheckOut;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public PoliticaDeCancelacion getPoliticaDeCancelacion() {
		return politicaDeCancelacion;
	}

	public void setPoliticaDeCancelacion(PoliticaDeCancelacion politicaDeCancelacion) {
		this.politicaDeCancelacion = politicaDeCancelacion;
	}

	public float getPrecioTotal() { 
        // Se calcula el precio total para el periodo entre checkIn y checkOut, incluyendo eventos.

        // Obtenemos el periodo en días entre checkIn y checkOut.
        long periodoDeAlquiler = ChronoUnit.DAYS.between(this.getFechaCheckIn(), this.getFechaCheckOut());

        // Obtenemos el periodo en días que duran los eventos.
        long diasDeEventos = this.getEventos().stream().mapToLong(evento -> evento.duracion()).sum();

        // Restamos los días de eventos del periodo de alquiler.
        periodoDeAlquiler -= diasDeEventos;

        // Calculamos el costo total del periodo de alquiler.
        float costoTotalDeAlquiler = periodoDeAlquiler * this.getPrecioPorDia();

        // Calculamos el costo total de los eventos.
        float costoTotalDeEventos = (float) this.getEventos().stream().mapToDouble(evento -> evento.calcularPrecioTotal()).sum();

        return costoTotalDeAlquiler + costoTotalDeEventos;
    }

	public void incrementarVecesAlquilado() {
	    this.vecesAlquilado += 1;
	}
	
	public void añadirComentario(String comentario) {
	    comentarios.add(comentario);
	}
	
	public void notificarBajaDePrecio() {
		for (IObserver observer : getObservers()) {
			observer.actuaSiBajaPrecio(this);
		}
	}
	
	public void notificarCancelacionReserva() {
		for (IObserver observer : getObservers()) {
			observer.actuaSiCancelarReserva(this);
		}
	}
	
	public void notificarSeHaceReserva() {
		for (IObserver observer : getObservers()) {
			observer.actuaSiSeReserva();
		}
	}
}
