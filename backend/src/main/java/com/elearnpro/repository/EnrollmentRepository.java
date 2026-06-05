package com.elearnpro.repository;

import com.elearnpro.model.AppUser;
import com.elearnpro.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser(AppUser user);
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
}
