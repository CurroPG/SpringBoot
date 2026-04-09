package com.example.alumnos.controller;

import com.example.alumnos.entity.Profesor;
import com.example.alumnos.repository.CursoRepository;
import com.example.alumnos.repository.ProfesorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfesorController {
    private final ProfesorRepository profesorRepository;
    private final CursoRepository cursoRepository;

    public ProfesorController(ProfesorRepository profesorRepository, CursoRepository cursoRepository) {
        this.profesorRepository = profesorRepository;
        this.cursoRepository = cursoRepository;
    }


    @GetMapping("/profesores")
    public String listarProfesores(Model model) {
        model.addAttribute("profesores", profesorRepository.findAll());
        return "lista-profesores";
    }

    @GetMapping("/profesores/nuevo")
    public String nuevoProfesor(Model model) {
        model.addAttribute("profesor", new Profesor());
        model.addAttribute("cursos", cursoRepository.findAll());
        return "formulario-profesor";
    }

    @PostMapping("/profesores/nuevo")
    public String guardarProfesor(@ModelAttribute Profesor profesor) {
        Profesor nuevo = new Profesor();
        nuevo.setNombre(profesor.getNombre());
        nuevo.setEmail(profesor.getEmail());
        nuevo.setDepartamento(profesor.getDepartamento());
        profesorRepository.save(nuevo);
        return "redirect:/profesores";
    }

    @GetMapping("/profesores/editar/{id}")
    public String editarProfesor(Model model, @PathVariable Long id){
        Profesor profesor = profesorRepository.findById(id).get();
        model.addAttribute("profesor", profesor);
        model.addAttribute("cursos", cursoRepository.findAll());
        return "formulario-editar-profesor";
    }

    @PostMapping("/profesores/editar/{id}")
    public String actualizarProfesor(@ModelAttribute Profesor profesorForm, @PathVariable Long id) {
        Profesor profesorExistente = profesorRepository.findById(id).get();
        profesorExistente.setNombre(profesorForm.getNombre());
        profesorExistente.setEmail(profesorForm.getEmail());
        profesorExistente.setDepartamento(profesorForm.getDepartamento());
        profesorRepository.save(profesorExistente);
        return "redirect:/profesores";
    }

    @PostMapping("/profesores/borrar/{id}")
    public String borrarProfesor(@PathVariable Long id){
        profesorRepository.deleteById(id);
        return "redirect:/profesores";
    }
}
