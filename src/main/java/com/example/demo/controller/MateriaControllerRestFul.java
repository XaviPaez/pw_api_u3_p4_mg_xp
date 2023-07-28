package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.IMateriaService;
import com.example.demo.service.to.MateriaTO;

@RestController
@RequestMapping("/materias")
public class MateriaControllerRestFul {
	

	@Autowired
	private IMateriaService iMateriaService;
	
	// GET
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MateriaTO> consultarPorId(@PathVariable Integer id) {
		return new ResponseEntity<>( this.iMateriaService.buscarPorId(id),null,200);
	}
	
}
