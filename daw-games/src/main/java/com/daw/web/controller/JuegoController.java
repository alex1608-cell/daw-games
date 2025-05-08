package com.daw.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.entities.Juego;
import com.daw.service.JuegoService;
import com.daw.service.exceptions.JuegoException;
import com.daw.service.exceptions.JuegoNotFoundException;

@RestController
@RequestMapping("/juego")
public class JuegoController {
	
	@Autowired
	private JuegoService juegoService;
	
	// Todos los juegos
	@GetMapping
	public ResponseEntity<List<Juego>> findAll() {
	    List<Juego> juegos = juegoService.findAll();
	    return ResponseEntity.ok(juegos);  
	}

	// Juego por ID
	
	@GetMapping("/{idJuego}")
	public ResponseEntity<?> findById(@PathVariable int idJuego) {
		try {
			return ResponseEntity.ok(this.juegoService.findById(idJuego));
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	// Crear un juego
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Juego juego) {
	    try {
	        Juego nuevoJuego = this.juegoService.create(juego);
	        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoJuego);
	    } catch (JuegoException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al crear el juego: " + ex.getMessage());
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + ex.getMessage());
	    }
	}

	// Modificar juego
	
	@PutMapping("/{idJuego}/modificar")
	public ResponseEntity<?> update(@PathVariable int idJuego, @RequestBody Juego juego) {
	    Juego juegoActualizado = this.juegoService.update(idJuego, juego);
	    return ResponseEntity.ok(juegoActualizado);
	}
	
	// Eliminar juego
	
	@DeleteMapping("/{idJuego}/eliminar")
	public ResponseEntity<?> delete(@PathVariable int idJuego) {
	    this.juegoService.deleteById(idJuego);
	    return ResponseEntity.ok().build();
	}
	
	// Marcar como completado
	@PutMapping("/{idJuego}/completar")
	public ResponseEntity<?> marcarCompletado(@PathVariable int idJuego) {
	    return ResponseEntity.ok(this.juegoService.marcarJuegoCompletado(idJuego));
	}

	// Como no completado
	@PutMapping("/{idJuego}/descompletar")
	public ResponseEntity<?> desmarcarCompletado(@PathVariable int idJuego) {
	    return ResponseEntity.ok(this.juegoService.desmarcarCompletado(idJuego));
	}

	// JUegos por genero
	
	@GetMapping("/genero/{genero}")
	public ResponseEntity<?> buscarGenero(@PathVariable String genero) {
	    if (genero == null || genero.trim().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El género no puede ser vacío.");
	    }
	    List<Juego> juegos = this.juegoService.buscarGenero(genero);
	    if (juegos.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron juegos para el género: " + genero);
	    }
	    return ResponseEntity.ok(juegos);
	}
	
	// POr plataforma
	
	@GetMapping("/plataforma/{plataforma}")
	public ResponseEntity<?> buscarPlataforma(@PathVariable String plataforma) {
	    if (plataforma == null || plataforma.trim().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La plataforma no puede ser vacía.");
	    }
	    List<Juego> juegos = this.juegoService.buscarPlataforma(plataforma);
	    if (juegos.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron juegos para la plataforma: " + plataforma);
	    }
	    return ResponseEntity.ok(juegos);
	}


	// Juegos por nombre
	
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
	    if (nombre == null || nombre.trim().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre no puede ser vacío.");
	    }
	    List<Juego> juegos = this.juegoService.buscarNombre(nombre);
	    if (juegos.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron juegos con el nombre: " + nombre);
	    }
	    return ResponseEntity.ok(juegos);
	}

	// Obtener expansiones
	
	@GetMapping("/expansiones")
	public ResponseEntity<?> obtenerExpansiones() {
	    List<Juego> expansiones = this.juegoService.obtenerExpansiones();
	    if (expansiones.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontraron expansiones.");
	    }
	    return ResponseEntity.ok(expansiones);
	}

	// Obtener DLC
	
	@GetMapping("/DLC")
	public ResponseEntity<?> obtenerDLCs() {
	    List<Juego> dlcs = this.juegoService.obtenerDLCs();
	    if (dlcs.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontraron DLCs.");
	    }
	    return ResponseEntity.ok(dlcs);
	}
	
	// Obtener juego base

	@GetMapping("/BASE")
	public ResponseEntity<?> obtenerJuegosBase() {
	    List<Juego> juegosBase = this.juegoService.obtenerJuegosBase();
	    if (juegosBase.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontraron juegos base.");
	    }
	    return ResponseEntity.ok(juegosBase);
	}
	
	// Rango de precios
	
	@GetMapping("/precio/{min}/{max}")
	public ResponseEntity<?> buscarRangoPrecio(@PathVariable int min, @PathVariable int max) {
	    try {
	        if (min > max) {
	            throw new IllegalArgumentException("El precio mínimo no puede ser mayor que el precio máximo.");
	        }
	        return ResponseEntity.ok(this.juegoService.buscarRangoPrecio(min, max));
	    } catch (IllegalArgumentException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }
	}
	
	public boolean existsById(int idJuego) {
		return this.juegoService.existById(idJuego);
	} 

	// Juegos con mas de 1000 descargas
	
	@GetMapping("/masdescargados")
	public ResponseEntity<?> juegosMasMilDescargas() {
	    List<Juego> juegos = this.juegoService.buscarMasMilDescargas();
	    if (juegos.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay juegos con más de 1000 descargas.");
	    }
	    return ResponseEntity.ok(juegos);
	}


}

