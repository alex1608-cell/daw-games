package com.daw.entities;

import java.time.LocalDate;

import com.daw.entities.enumerados.Tipo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

	@Entity
	@Table(name = "juego")
	
	public class Juego{
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	
		@Column(name = "nombre")
		private String nombre;
		
		@Column(name = "genero")
		private String genero;
		
		@Column(name = "plataforma")
		private String plataforma;
		
		@Column(name = "precio")
		private double precio;
		
		@Column(name = "descargas")
		private long descargas;
		
		@Column(name = "fecha_lanzamiento")
		private LocalDate fechaLanzamiento;
		
		@Enumerated(EnumType.STRING)
		private Tipo tipo;
		
		private boolean completado;
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getGenero() {
			return genero;
		}
		public void setGenero(String genero) {
			this.genero = genero;
		}
		public String getPlataforma() {
			return plataforma;
		}
		public void setPlataforma(String plataforma) {
			this.plataforma = plataforma;
		}
		public double getPrecio() {
			return precio;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		public long getDescargas() {
			return descargas;
		}
		public void setDescargas(long descargas) {
			this.descargas = descargas;
		}
		public LocalDate getFechaLanzamiento() {
			return fechaLanzamiento;
		}
		public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
			this.fechaLanzamiento = fechaLanzamiento;
		}
		public Tipo getTipo() {
			return tipo;
		}
		public void setTipo(Tipo tipo) {
			this.tipo = tipo;
		}
		public boolean esCompletado() {
			return completado;
		}
		public void setCompletado(boolean completado) {
			this.completado = completado;
		}
	
		
		
		
	}
	
	

