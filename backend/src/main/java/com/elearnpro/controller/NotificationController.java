package com.elearnpro.controller;

import com.elearnpro.model.Notification;
import com.elearnpro.repository.NotificationRepository;
import com.elearnpro.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationController(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @GetMapping
    public List<Notification> notifications(Principal principal) {
        return notificationRepository.findByUserOrderByDateCreationDesc(userService.currentUser(principal));
    }

    @PutMapping("/{id}/read")
    public Notification read(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setLu(true);
        return notificationRepository.save(notification);
    }
}
