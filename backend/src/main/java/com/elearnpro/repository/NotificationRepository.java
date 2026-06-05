package com.elearnpro.repository;

import com.elearnpro.model.AppUser;
import com.elearnpro.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByDateCreationDesc(AppUser user);
}
