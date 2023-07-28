package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.modelo.Materia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class MateriaRepositoryImpl implements IMateriaRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<Materia> buscarPorCedulaEstudiante(String cedula) {
		// TODO Auto-generated method stub
		TypedQuery<Materia> myQuery = this.entityManager.createQuery("SELECT m FROM Materia m WHERE m.estudiante.cedula= :datoCedula", Materia.class);
		myQuery.setParameter("datoCedula", cedula);
		return myQuery.getResultList();
	}
	@Override
	public Materia buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return this.entityManager.find(Materia.class, id);
	}

}
