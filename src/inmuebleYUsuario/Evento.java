package inmuebleYUsuario;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Evento {
	// Atributos
    private String nombre;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private float precioPorDia;

    // Constructor
    public Evento(String nombre, LocalDateTime fechaInicio, LocalDateTime fechaFin, float precioPorDia) {
        this.setNombre(nombre);
        this.setFechaInicio(fechaInicio);
        this.setFechaFin(fechaFin);
        this.precioPorDia = precioPorDia;
    }

    // Método para obtener el precio por día
    public float getPrecioPorDia() {
        return precioPorDia;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public float calcularPrecioTotal(){
	    return this.duracion() * this.getPrecioPorDia();
	}

	public long duracion(){
		return ChronoUnit.DAYS.between(fechaInicio,fechaFin);
	}
}
