package com.elearnpro.controller;

import com.elearnpro.model.Course;
import com.elearnpro.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) String categorie,
                                   @RequestParam(required = false) String niveau) {
        return courseRepository.findAll().stream()
                .filter(c -> keyword == null || keyword.isBlank()
                        || c.getTitre().toLowerCase().contains(keyword.toLowerCase())
                        || c.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .filter(c -> categorie == null || categorie.isBlank() || c.getCategorie().equalsIgnoreCase(categorie))
                .filter(c -> niveau == null || niveau.isBlank() || c.getNiveau().equalsIgnoreCase(niveau))
                .sorted(Comparator.comparingDouble(Course::getNoteMoyenne).reversed())
                .toList();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course body) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setTitre(body.getTitre());
        course.setDescription(body.getDescription());
        course.setCategorie(body.getCategorie());
        course.setNiveau(body.getNiveau());
        course.setNoteMoyenne(body.getNoteMoyenne());
        course.setDureeHeures(body.getDureeHeures());
        course.setGratuit(body.isGratuit());
        course.setImageUrl(body.getImageUrl());
        return courseRepository.save(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseRepository.deleteById(id);
    }
}
