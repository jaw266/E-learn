# Plan de présentation — E-Learn Pro V2

## Slide 1 — Titre
E-Learn Pro : plateforme e-learning avec système de recommandation.

## Slide 2 — Problématique
Les utilisateurs ont beaucoup de contenus disponibles et ont besoin d'une aide pour choisir les cours adaptés à leur niveau, domaine et progression.

## Slide 3 — Objectifs
- Créer une application web full-stack.
- Gérer inscription et connexion sécurisée.
- Afficher un catalogue de cours.
- Proposer des recommandations personnalisées.
- Suivre la progression dans un dashboard.
- Ajouter une bibliothèque de livres gratuits.

## Slide 4 — Technologies utilisées
- Frontend : React, Vite, TypeScript, Axios.
- Backend : Spring Boot, Spring Security, JWT, JPA.
- Base de données : H2 pour test, MySQL possible.

## Slide 5 — Architecture
Présenter le schéma : React -> API REST -> Spring Boot -> JPA -> Database.

## Slide 6 — Fonctionnalités principales
- Login/register.
- Catalogue et filtres.
- Recommandations.
- Dashboard.
- Notifications.
- Gestion des livres.

## Slide 7 — Modèle de données
Présenter User, Course, Enrollment, Rating, Notification, Book.

## Slide 8 — API REST
Présenter les endpoints principaux : `/api/auth`, `/api/courses`, `/api/recommendations`, `/api/enrollments`, `/api/ratings`, `/api/notifications`, `/api/books`.

## Slide 9 — Démonstration
1. Connexion.
2. Accueil.
3. Catalogue filtré.
4. Livres gratuits.
5. Dashboard.
6. Recommandations.

## Slide 10 — Conclusion
Le projet respecte le MVP demandé et peut évoluer vers un moteur IA avancé, un back-office admin complet, certificats et rapports automatisés.
