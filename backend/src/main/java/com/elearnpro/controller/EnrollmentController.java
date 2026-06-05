package com.elearnpro.controller;

import com.elearnpro.dto.EnrollmentRequest;
import com.elearnpro.dto.ProgressRequest;
import com.elearnpro.model.AppUser;
import com.elearnpro.model.Course;
import com.elearnpro.model.Enrollment;
import com.elearnpro.model.Notification;
import com.elearnpro.repository.CourseRepository;
import com.elearnpro.repository.EnrollmentRepository;
import com.elearnpro.repository.NotificationRepository;
import com.elearnpro.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public EnrollmentController(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, NotificationRepository notificationRepository, UserService userService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @GetMapping("/me")
    public List<Enrollment> myEnrollments(Principal principal) {
        return enrollmentRepository.findByUser(userService.currentUser(principal));
    }

    @PostMapping
    public Enrollment enroll(@RequestBody EnrollmentRequest request, Principal principal) {
        AppUser user = userService.currentUser(principal);
        return enrollmentRepository.findByUserIdAndCourseId(user.getId(), request.courseId())
                .orElseGet(() -> {
                    Course course = courseRepository.findById(request.courseId()).orElseThrow();
                    notificationRepository.save(new Notification(user, "Vous êtes inscrit au cours : " + course.getTitre()));
                    return enrollmentRepository.save(new Enrollment(user, course));
                });
    }

    @PutMapping("/{id}")
    public Enrollment updateProgress(@PathVariable Long id, @RequestBody ProgressRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow();
        enrollment.setProgression(Math.max(0, Math.min(100, request.progression())));
        return enrollmentRepository.save(enrollment);
    }
}
