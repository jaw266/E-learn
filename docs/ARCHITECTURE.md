# Architecture technique — E-Learn Pro V2

## Vue globale

```txt
React + Vite  ----Axios/JSON---->  Spring Boot REST API  ----JPA----> H2/MySQL
Frontend 5173                     Backend 8080                       Database
```

## Backend

Architecture principale :

```txt
controller/    Reçoit les requêtes HTTP REST
service/       Logique métier : utilisateur, recommandation
repository/    Accès aux données JPA
model/         Entités JPA : User, Course, Book, Enrollment, Rating, Notification
dto/           Objets de transfert JSON
security/      JWT, filtre d'authentification, configuration Spring Security
config/        Sécurité et données de démo
```

## Frontend

Architecture principale :

```txt
api/           Axios + URL backend
state/         AuthContext : session utilisateur et JWT
components/    Logo, navbar, route protégée
pages/         Login, Register, Home, Catalogue, Livres, Dashboard
styles.css     Design system professionnel responsive
```

## Sécurité

- Login et register publics.
- Les autres endpoints nécessitent un token JWT.
- Le frontend stocke le token dans localStorage.
- Axios ajoute automatiquement `Authorization: Bearer <token>`.

## Modèle de recommandation simplifié

Le moteur actuel recommande les cours selon :

1. Le domaine d'intérêt de l'utilisateur.
2. Son niveau.
3. Les cours populaires par note moyenne.
4. Les cours non encore suivis.

Cette approche respecte la version MVP du cahier des charges sans filtrage collaboratif avancé.
