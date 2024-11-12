package observer;

public class PaginaWeb implements HomePagePublisher, IObserver {

	@Override
	public void actuaSiBajaPrecio(Inmueble inmueble) {
		this.publish("No te pierdas esta oferta: Un inmueble" + inmueble.getTipoDeInmueble() + " a tan solo " + inmueble.getPrecioTotal() + " pesos.");
	}

	@Override
	public void actuaSiCancelarReserva(Inmueble inmueble) {
		// Sin implementación;
	}

	@Override
	public void actuaSiSeReserva() {
		// Sin implementación;
	}

	@Override
	public void publish(String message) {
		// Sin implementación;
	}

}
