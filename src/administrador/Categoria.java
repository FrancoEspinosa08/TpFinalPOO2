package administrador;
import java.util.List;

public abstract class Categoria {
	private List<Integer> puntaje;  // Lista de puntajes
    private String nombre;          // Nombre de la categoría

    public Categoria(List<Integer> puntaje, String nombre) {
        this.puntaje = puntaje;
        this.nombre = nombre;
    }

    public List<Integer> getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(List<Integer> puntaje) {
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }
    
    public double promedio() {
        if (puntaje.isEmpty()) {
            return 0;
        }
        else{
        int suma = 0;
        for (int p : puntaje) {
            suma += p;
        }
        return (double) suma / puntaje.size();
        }
    }
    int cantidadQuePuntuaronCon(int puntaje){
    	return this.getPuntaje().stream().filter(p -> p == puntaje).mapToInt(Integer::intValue).sum();
    }
}
