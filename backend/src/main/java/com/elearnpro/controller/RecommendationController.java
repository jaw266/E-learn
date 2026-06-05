package com.elearnpro.controller;

import com.elearnpro.model.Course;
import com.elearnpro.service.RecommendationService;
import com.elearnpro.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final UserService userService;

    public RecommendationController(RecommendationService recommendationService, UserService userService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
    }

    @GetMapping
    public List<Course> recommendations(Principal principal) {
        return recommendationService.recommendFor(userService.currentUser(principal));
    }
}
