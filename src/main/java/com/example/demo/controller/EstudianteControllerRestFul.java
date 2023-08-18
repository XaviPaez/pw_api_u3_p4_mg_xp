package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.modelo.Estudiante;
import com.example.demo.service.IEstudianteService;
import com.example.demo.service.IMateriaService;
import com.example.demo.service.to.EstudianteTO;
import com.example.demo.service.to.MateriaTO;

@RestController
@RequestMapping("/estudiantes")
@CrossOrigin
public class EstudianteControllerRestFul {

	@Autowired
	private IEstudianteService estudianteService;

	@Autowired
	private IMateriaService iMateriaService;

	// GET
	@GetMapping(path = "/{cedula}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)

	public Estudiante consultarPorCedula(@PathVariable String cedula) {
		return this.estudianteService.consultarPorCedula(cedula);
	}

	@PostMapping(consumes = "application/json")
	// estudiante debe venir en el cuerpo del request
	public void guardar(@RequestBody Estudiante estudiante) {

		this.estudianteService.guardar(estudiante);
	}

	@PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Estudiante guardar2(@RequestBody Estudiante estudiante) {

		this.estudianteService.guardar(estudiante);
		return this.consultarPorCedula(estudiante.getCedula());
	}

	@PutMapping(path = "/{identificador}")
	public void actualizar(@RequestBody Estudiante estudiante, @PathVariable Integer identificador) {
		estudiante.setId(identificador);
		this.estudianteService.actualizar(estudiante);
	}

	@PatchMapping(path = "/{identificador}")
	public void actualizarParcial(@RequestBody Estudiante estudiante, @PathVariable Integer identificador) {
		Estudiante estu1 = this.estudianteService.consultarPorId(identificador);
		estu1.setCedula(estudiante.getCedula());

		this.estudianteService.actualizar(estu1);

	}

	@DeleteMapping(path = "/{id}")
	public  ResponseEntity<Estudiante> borrar(@PathVariable Integer id) {
		Estudiante estudiante= this.estudianteService.consultarPorId(id);
		this.estudianteService.eliminar(id);
		return new ResponseEntity<>(estudiante, null, 200);
	}
	

	@GetMapping
	public ResponseEntity<List<Estudiante>> consultarTodosPorProvincia(@RequestParam String provincia) {
		List<Estudiante> lista = this.estudianteService.consultarTodos(provincia);
		

		HttpHeaders cabeceras = new HttpHeaders();
		cabeceras.add("detallemensjae", "Ciudadanos consultados exitosamente");
		cabeceras.add("valorAPI", "incalculable");
		return new ResponseEntity<>(lista, cabeceras, 228);
		// return this.estudianteService.buscarTodos();
	}

	@GetMapping(path = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EstudianteTO>> consultarTodosHATEOAS() {
		List<EstudianteTO> lista = this.estudianteService.buscarTodos();
		for (EstudianteTO e : lista) {
			Link myLink = linkTo(methodOn(EstudianteControllerRestFul.class).buscarPorEstudiante(e.getCedula()))
					.withRel("materias");
			e.add(myLink);
		}
		return new ResponseEntity<>(lista, null, 200);
	}

	@GetMapping(path = "/{cedula}/materias" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MateriaTO>> buscarPorEstudiante(@PathVariable String cedula) {
		List<MateriaTO> lista = this.iMateriaService.buscarPorCedulaEstudiante(cedula);
		for (MateriaTO materiaTO : lista) {
			Link myLink =  linkTo(methodOn(MateriaControllerRestFul.class).consultarPorId(materiaTO.getId()))
					.withRel("materia");
			materiaTO.add(myLink);
		}
		return new ResponseEntity<>(lista, null, 200);
		
	}

}