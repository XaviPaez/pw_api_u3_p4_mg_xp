package com.example.demo.repository;

import java.util.List;

import com.example.demo.repository.modelo.Estudiante;

public interface IEstudianteRepository {
	
	public Estudiante seleccionarPorCedula(String cedula);
	public void insertarEstudiante(Estudiante estudiante);
	public void actualizar(Estudiante estudiante);
	public void actualizarParcial(String cedulaActual, String cedulaNueva);
	public void borrar(Integer id);
	public Estudiante buscarPorId(Integer id);
	public List<Estudiante> buscarTodos(String provincia);
	public Estudiante insertarPorId();
}
