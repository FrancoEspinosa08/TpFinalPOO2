package administrador;

import java.util.List;

public class Configuracion {
	
	//Atributos
	private List<Categoria> categoriasPropietario;
	private List<Categoria> categoriasInquilino;
	private List<Categoria> categoriasInmueble;
	
	private List<String> tiposInmuebles;
	private List<Servicio> serviciosValidos;
	 
	public Configuracion(List<Categoria> categoriasPropietario, 
						 List<Categoria> categoriasInquilino,
						 List<Categoria> categoriasInmueble,
						 List<String> tiposInmuebles, 
						 List<Servicio> serviciosValidos) {
		
		this.categoriasPropietario = categoriasPropietario;
		this.categoriasInquilino   = categoriasInquilino;
		this.categoriasInmueble    = categoriasInmueble;
		this.tiposInmuebles		   = tiposInmuebles;
		this.serviciosValidos	   = serviciosValidos;
	}

	//Getters and Setters
	public void setCategoriasPropietario(List<Categoria> categoriasPropietario) {
		
		this.categoriasPropietario = categoriasPropietario;
		
	}
	
	public List<Categoria> getCategoriasPropietario(){
		
		return this.categoriasPropietario;
		
	}
	
	public void setCategoriasInquilinos(List<Categoria> categoriasInquilino) {
		
		this.categoriasInquilino = categoriasInquilino;
		
	}
	
	public List<Categoria> getCategoriasInquilinos(){
		 return this.categoriasInquilino;
	}
	
	public List<Categoria> getCategoriasInmueble(){
		return this.categoriasInmueble;
	}
	
	public void setTiposInmueble(List<String> tiposInmueble) {
		 this.tiposInmuebles = tiposInmueble;
	}
	
	public void setSeviciosValidos(List<Servicio> servicios) {
		 this.serviciosValidos = servicios;
	}
	
	
	public List<String> getTipoInmuebles(){
		return this.tiposInmuebles;
	}
	public List<Servicio> getServiciosValidos(){
		return this.serviciosValidos;
	}

}
