package com.example.demo.service.to;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

public class MateriaTO extends RepresentationModel<MateriaTO> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nombre;
	private Integer numeroCreditos;
	
	//SET Y GET
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getNumeroCreditos() {
		return numeroCreditos;
	}
	public void setNumeroCreditos(Integer numeroCreditos) {
		this.numeroCreditos = numeroCreditos;
	}
	
	@Override
	public String toString() {
		return "MateriaTO [id=" + id + ", nombre=" + nombre + ", numeroCreditos=" + numeroCreditos + "]";
	}

	
	
}
