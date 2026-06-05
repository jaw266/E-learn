package com.elearnpro.controller;

import com.elearnpro.dto.RatingRequest;
import com.elearnpro.model.AppUser;
import com.elearnpro.model.Course;
import com.elearnpro.model.Rating;
import com.elearnpro.repository.CourseRepository;
import com.elearnpro.repository.RatingRepository;
import com.elearnpro.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;

    public RatingController(RatingRepository ratingRepository, CourseRepository courseRepository, UserService userService) {
        this.ratingRepository = ratingRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @PostMapping
    public Rating rate(@RequestBody RatingRequest request, Principal principal) {
        AppUser user = userService.currentUser(principal);
        Course course = courseRepository.findById(request.courseId()).orElseThrow();
        Rating rating = ratingRepository.save(new Rating(user, course, request.note(), request.commentaire()));
        double average = ratingRepository.findByCourseId(course.getId()).stream()
                .mapToInt(Rating::getNote)
                .average()
                .orElse(course.getNoteMoyenne());
        course.setNoteMoyenne(Math.round(average * 10.0) / 10.0);
        courseRepository.save(course);
        return rating;
    }
}
