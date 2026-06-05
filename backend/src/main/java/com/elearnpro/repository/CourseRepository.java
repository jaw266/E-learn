package com.elearnpro.repository;

import com.elearnpro.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategorieContainingIgnoreCase(String categorie);
    List<Course> findByNiveauContainingIgnoreCase(String niveau);
    List<Course> findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titre, String description);
}
