package observer;

import java.time.LocalDateTime;	
import java.util.List;
import java.util.Random;

import inmuebleYUsuario.Comentario;
import inmuebleYUsuario.Email;
import inmuebleYUsuario.FormaDePago;
import inmuebleYUsuario.IInquilino;
import inmuebleYUsuario.IPropietario;
import inmuebleYUsuario.RankingUsuario;

public class Usuario implements IPropietario, IInquilino, IObserver {
	private String nombre;
	private String telefono;
	private Email email;
	private LocalDateTime fechaDeInscripcion;
	private RankingUsuario ranking;
	private List<Inmueble> inmuebles;
	private Comentario comentario;
	private int vecesQueAlquilo;
	private AppMobile appMobile;
	
	
	
	public Usuario(String nombre, String telefono, Email email, LocalDateTime fechaDeInscripcion,
			RankingUsuario ranking, List<Inmueble> inmuebles, Comentario comentario, int vecesQueAlquilo,
			AppMobile appMobile) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.fechaDeInscripcion = fechaDeInscripcion;
		this.ranking = ranking;
		this.inmuebles = inmuebles;
		this.comentario = comentario;
		this.vecesQueAlquilo = vecesQueAlquilo;
		this.appMobile = appMobile;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public Email getEmail() {
		return email;
	}
	
	public LocalDateTime getFechaDeInscripcion() {
		return fechaDeInscripcion;
	}
	public void setFechaDeInscripcion(LocalDateTime fechaDeInscripcion) {
		this.fechaDeInscripcion = fechaDeInscripcion;
	}
	
	public RankingUsuario getRanking() {
		return ranking;
	}

	public void setRanking(RankingUsuario ranking) {
		this.ranking = ranking;
	}

	public List<Inmueble> getInmuebles() {
		return inmuebles;
	}

	public int getVecesQueAlquilo() {
		return vecesQueAlquilo;
	}
	
	public AppMobile getAppMobile() {
		return appMobile;
	}

	public void incrementarVecesQueAlquilo() {
		this.vecesQueAlquilo += 1;
	}
	
	public int puntuar() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }
	
	@Override
	public int cantidadTotalDeAlquileres() {
		int sumaTotal = 0;
		for (Inmueble i : inmuebles) {
			sumaTotal += i.getVecesAlquilado();
		}
		return sumaTotal;
	}
	
	public String generarComentario() {
		List<String> listaComentarios = comentario.getCometarios();
        
        Random random = new Random();
        int indiceAleatorio = random.nextInt(listaComentarios.size()); //  Índice aleatorio en el rango de la lista
        return listaComentarios.get(indiceAleatorio);                  // Devuelve el comentario aleatorio
	}

	@Override
	public FormaDePago seleccionarFormaDePago(List<FormaDePago> formas) {
	    int indiceAleatorio = (int) (Math.random() * formas.size()); // Genera un índice aleatorio
	    return formas.get(indiceAleatorio);                          // Devuelve una forma de pago aleatoria
	}

	@Override
	public boolean decidirSiReserva() {
		Random random = new Random();
		return random.nextBoolean();
	}

	@Override
	public void actuaSiBajaPrecio(Inmueble inmueble) {
		// Sin implementación
	}

	@Override
	public void actuaSiCancelarReserva(Inmueble inmueble) {
		this.getAppMobile().popUp("El/la " + inmueble.getTipoDeInmueble() + " que te interesa se ha liberado! Corre a reservarlo!", "Rojo", 14);
	}

	@Override
	public void actuaSiSeReserva() {
		// Sin implementación
	}

}
