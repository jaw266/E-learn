package com.elearnpro.service;

import com.elearnpro.model.AppUser;
import com.elearnpro.model.Course;
import com.elearnpro.repository.CourseRepository;
import com.elearnpro.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public RecommendationService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Course> recommendFor(AppUser user) {
        Set<Long> enrolled = enrollmentRepository.findByUser(user).stream()
                .map(e -> e.getCourse().getId())
                .collect(Collectors.toSet());

        return courseRepository.findAll().stream()
                .filter(c -> !enrolled.contains(c.getId()))
                .filter(c -> same(c.getCategorie(), user.getDomaineInteret()) || same(c.getNiveau(), user.getNiveau()))
                .sorted(Comparator.comparingDouble(Course::getNoteMoyenne).reversed())
                .limit(5)
                .toList();
    }

    private boolean same(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }
}
