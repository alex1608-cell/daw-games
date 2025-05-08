package com.daw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.entities.Juego;
import com.daw.entities.enumerados.Tipo;
import com.daw.repositories.JuegoRepository;
import com.daw.service.exceptions.JuegoException;
import com.daw.service.exceptions.JuegoNotFoundException;

@Service
public class JuegoService {

	
	@Autowired
	private JuegoRepository juegoRepository;
	
	// Mostrar todos juegos
	public List<Juego> findAll(){
		return this.juegoRepository.findAll();
	}
	
	// Mostrar juego por ID
	public Juego findById(int idJuego) {
		if(!this.juegoRepository.existsById(idJuego)) {
			throw new JuegoNotFoundException("El ID del juego no existe");
		}
		return this.juegoRepository.findById(idJuego).get();
	}
	
	public boolean existById(int idJuego) {
		return this.juegoRepository.existsById(idJuego);
	}

	// Crear juego
	public Juego create(Juego juego) {
		juego.setId(0);
		juego.setFechaLanzamiento(LocalDate.now());
		
		return this.juegoRepository.save(juego);
	}
	
	// Eliminar juego
	
	public void deleteById(int idJuego) {
		if (!existById(idJuego)) {
			throw new JuegoNotFoundException("El ID no existe");
		}
		this.juegoRepository.deleteById(idJuego);

	}
	
	// Modificar el juego
	
	public Juego update(int idJuego, Juego juego) {
	    if (idJuego != juego.getId()) {
	        throw new JuegoException("El ID  y el body no concuerdan");
	    }

	    Juego juegoBD = this.juegoRepository.findById(idJuego)
	            .orElseThrow(() -> new JuegoException("No existe el juego con ID: " + idJuego));

	    if (juego.esCompletado() != juegoBD.esCompletado()) {
	        throw new JuegoException("No se puede modificar el atributo");
	    }
	    
	    // Actualizar atributos
	    
	    juegoBD.setNombre(juego.getNombre());
	    juegoBD.setGenero(juego.getGenero());
	    juegoBD.setPlataforma(juego.getPlataforma());
	    juegoBD.setPrecio(juego.getPrecio());
	    juegoBD.setDescargas(juego.getDescargas());
	    juegoBD.setTipo(juego.getTipo());
	    juegoBD.setFechaLanzamiento(juego.getFechaLanzamiento());

	    return this.juegoRepository.save(juegoBD);
	}	
	
	// Marcar como completado
	
	public Juego marcarJuegoCompletado(int idJuego) {
	    return this.juegoRepository.findById(idJuego)
	        .map(juego -> {
	            juego.setCompletado(true);
	            return this.juegoRepository.save(juego);
	        })
	        .orElseThrow(() -> new JuegoException("No existe el juego con ID: " + idJuego));
	}
	
	// Desmarcar como completado

	public Juego desmarcarCompletado(int idJuego) {
	    return juegoRepository.findById(idJuego)
	        .map(juego -> {
	            juego.setCompletado(false);
	            return juegoRepository.save(juego);
	        })
	        .orElseThrow(() -> new JuegoException("No existe el juego con ID: " + idJuego));
	}
	
	// Buscar juego por genero
	
	public List<Juego> buscarGenero(String genero) {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getGenero() != null &&
	                         juego.getGenero().toLowerCase().contains(genero.toLowerCase()))
	        .collect(Collectors.toList());
	}

	//  Buscar juegos por nombre (que contenga el nombre que se pasa por parámetros).
	
	public List<Juego> buscarNombre(String nombre) {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getNombre() != null &&
	                         juego.getNombre().toLowerCase().contains(nombre.toLowerCase()))
	        .collect(Collectors.toList());
	}

	
	// Buscar juegos por plataforma (que contenga la plataforma que se pasa por parámetros).

	public List<Juego> buscarPlataforma(String plataforma) {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getPlataforma() != null &&
	                         juego.getPlataforma().toLowerCase().contains(plataforma.toLowerCase()))
	        .collect(Collectors.toList());
	}
	
	// Obtener expansiones

	public List<Juego> obtenerExpansiones() {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getTipo() == Tipo.EXPANSION)
	        .collect(Collectors.toList());
	}

	// Obtener DLC
	
	public List<Juego> obtenerDLCs() {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getTipo() == Tipo.DLC)
	        .collect(Collectors.toList());
	}
	
	// Obntener Base
	
	public List<Juego> obtenerJuegosBase() {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getTipo() == Tipo.BASE)
	        .collect(Collectors.toList());
	}

	// Buscar por rango de precio

	public List<Juego> buscarRangoPrecio(double min, double max) {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getPrecio() >= min && juego.getPrecio() <= max)
	        .collect(Collectors.toList());
	}

	// Mostrar los juegos que tengan más de 1000 descargas.

	public List<Juego> buscarMasMilDescargas() {
	    return juegoRepository.findAll().stream()
	        .filter(juego -> juego.getDescargas() > 1000)
	        .collect(Collectors.toList());
	}


}
