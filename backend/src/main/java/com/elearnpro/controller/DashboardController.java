package com.elearnpro.controller;

import com.elearnpro.model.AppUser;
import com.elearnpro.model.Enrollment;
import com.elearnpro.model.Rating;
import com.elearnpro.repository.EnrollmentRepository;
import com.elearnpro.repository.RatingRepository;
import com.elearnpro.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/me")
public class DashboardController {
    private final UserService userService;
    private final EnrollmentRepository enrollmentRepository;
    private final RatingRepository ratingRepository;

    public DashboardController(UserService userService, EnrollmentRepository enrollmentRepository, RatingRepository ratingRepository) {
        this.userService = userService;
        this.enrollmentRepository = enrollmentRepository;
        this.ratingRepository = ratingRepository;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard(Principal principal) {
        AppUser user = userService.currentUser(principal);
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        List<Rating> ratings = ratingRepository.findByUserId(user.getId());
        double progression = enrollments.stream().mapToInt(Enrollment::getProgression).average().orElse(0);
        double note = ratings.stream().mapToInt(Rating::getNote).average().orElse(0);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCours", enrollments.size());
        data.put("coursCompletes", enrollments.stream().filter(Enrollment::isCompleted).count());
        data.put("progressionMoyenne", Math.round(progression));
        data.put("noteMoyenneDonnee", Math.round(note * 10.0) / 10.0);
        data.put("enrollments", enrollments);
        return data;
    }
}
