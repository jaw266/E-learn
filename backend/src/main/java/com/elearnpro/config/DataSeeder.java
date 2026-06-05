package com.elearnpro.config;

import com.elearnpro.model.*;
import com.elearnpro.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedData(UserRepository userRepository,
                               CourseRepository courseRepository,
                               BookRepository bookRepository,
                               EnrollmentRepository enrollmentRepository,
                               NotificationRepository notificationRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            AppUser demo = userRepository.save(new AppUser(
                    "Demo User",
                    "demo@elearnpro.com",
                    passwordEncoder.encode("password"),
                    "Débutant",
                    "Data & IA",
                    "USER"
            ));
            userRepository.save(new AppUser(
                    "Admin",
                    "admin@elearnpro.com",
                    passwordEncoder.encode("admin123"),
                    "Avancé",
                    "Développement Web",
                    "ADMIN"
            ));

            Course c1 = courseRepository.save(new Course("Python pour Data Science", "Bases Python, pandas, nettoyage de données et visualisation.", "Data & IA", "Débutant", 4.8, 18, true, ""));
            Course c2 = courseRepository.save(new Course("Machine Learning pratique", "Régression, classification et évaluation des modèles.", "Data & IA", "Intermédiaire", 4.7, 24, true, ""));
            Course c3 = courseRepository.save(new Course("React Frontend Pro", "Créer une interface moderne avec React, composants et routing.", "Développement Web", "Débutant", 4.6, 15, true, ""));
            courseRepository.save(new Course("Spring Boot REST API", "Backend Java, JPA, Security, JWT et architecture Controller-Service-Repository.", "Développement Web", "Intermédiaire", 4.9, 22, true, ""));
            courseRepository.save(new Course("IoT avec capteurs", "Acquisition de données, MQTT, architecture cloud et edge.", "IoT", "Débutant", 4.5, 16, true, ""));
            courseRepository.save(new Course("Cybersecurity Fundamentals", "Authentification, permissions, chiffrement et bonnes pratiques.", "Cybersecurity", "Débutant", 4.4, 12, true, ""));
            courseRepository.save(new Course("UX/UI pour plateformes éducatives", "Design system, wireframes et expérience utilisateur.", "Design", "Intermédiaire", 4.5, 10, true, ""));

            Enrollment e1 = new Enrollment(demo, c1);
            e1.setProgression(68);
            enrollmentRepository.save(e1);
            Enrollment e2 = new Enrollment(demo, c3);
            e2.setProgression(35);
            enrollmentRepository.save(e2);

            bookRepository.save(new Book("Python Data Handbook", "Jake VanderPlas", "Data & IA", "Débutant", "Guide pratique pour manipuler et analyser les données avec Python.", "https://jakevdp.github.io/PythonDataScienceHandbook/", "", true));
            bookRepository.save(new Book("Dive Into Deep Learning", "Aston Zhang et al.", "Data & IA", "Intermédiaire", "Livre interactif gratuit pour deep learning.", "https://d2l.ai/", "", true));
            bookRepository.save(new Book("Eloquent JavaScript", "Marijn Haverbeke", "Développement Web", "Débutant", "Livre gratuit pour apprendre JavaScript moderne.", "https://eloquentjavascript.net/", "", true));
            bookRepository.save(new Book("Spring Boot Reference Notes", "E-Learn Pro", "Développement Web", "Intermédiaire", "Notes structurées pour API REST, JPA et sécurité.", "https://spring.io/projects/spring-boot", "", true));
            bookRepository.save(new Book("IoT From Scratch", "Open Learning", "IoT", "Débutant", "Introduction aux objets connectés et capteurs.", "https://www.raspberrypi.com/documentation/", "", true));
            bookRepository.save(new Book("Web Security Basics", "Open Web", "Cybersecurity", "Débutant", "Concepts de base pour sécuriser les applications web.", "https://owasp.org/www-project-top-ten/", "", true));
            bookRepository.save(new Book("Design Systems Guide", "Open UX", "Design", "Débutant", "Principes pour construire une interface cohérente et professionnelle.", "https://www.designsystems.com/", "", true));

            notificationRepository.save(new Notification(demo, "Nouveau cours recommandé : Machine Learning pratique"));
            notificationRepository.save(new Notification(demo, "Rappel : terminez votre cours Python pour Data Science"));
            notificationRepository.save(new Notification(demo, "Nouveau livre gratuit ajouté dans Data & IA"));
        };
    }
}
