# E-Learn Pro V2 — Full-Stack Learning Platform

Projet e-learning développé selon le cahier des charges : application web full-stack avec **React + Vite** côté frontend et **Spring Boot + JWT + JPA** côté backend.

## Objectif

Créer une plateforme qui permet à un utilisateur de :

- créer un compte ;
- se connecter avec email et mot de passe ;
- consulter un catalogue de cours ;
- recevoir des recommandations selon son niveau et son domaine ;
- suivre sa progression ;
- noter les cours ;
- recevoir des notifications in-app ;
- consulter des livres gratuits par domaine ;
- gérer les livres disponibles.

## Stack

### Frontend
- React + Vite + TypeScript
- React Router
- Axios
- CSS responsive professionnel

### Backend
- Java 17
- Spring Boot 3
- Spring Security + JWT stateless
- Spring Data JPA / Hibernate
- H2 Database pour tester rapidement
- MySQL possible

## Structure du projet

```txt
e-learn-dev-v2/
  backend/
    src/main/java/com/elearnpro/
      config/        # SecurityConfig, DataSeeder
      controller/    # REST controllers
      dto/           # Request/response DTO
      model/         # Entités JPA
      repository/    # Spring Data repositories
      security/      # JWT service + filter
      service/       # Logique métier
    http-tests/      # Tests API REST Client VS Code
  frontend/
    src/
      api/           # Axios client
      components/    # Navbar, logo, protected route
      pages/         # Login, Register, Home, Catalogue, Livres, Dashboard
      state/         # AuthContext
      styles.css     # Design system
  docs/
    ARCHITECTURE.md
    TESTER_SUR_VSCODE.md
    CAHIER_TESTS.md
    PLAN_PRESENTATION.md
```

## Lancer dans VS Code

### 1. Backend

```bash
cd backend
mvn spring-boot:run
```

Backend : `http://localhost:8080`

Console H2 : `http://localhost:8080/h2-console`

```txt
JDBC URL: jdbc:h2:mem:elearnpro
User: sa
Password: vide
```

### 2. Frontend

Dans un deuxième terminal :

```bash
cd frontend
npm install
npm run dev
```

Frontend : `http://localhost:5173`

## Comptes de test

```txt
Utilisateur : demo@elearnpro.com / password
Admin       : admin@elearnpro.com / admin123
```

## Endpoints principaux

| Méthode | Endpoint | Rôle |
|---|---|---|
| POST | `/api/auth/register` | Inscription |
| POST | `/api/auth/login` | Connexion JWT |
| GET | `/api/courses` | Liste des cours |
| GET | `/api/courses/{id}` | Détail d'un cours |
| GET | `/api/recommendations` | Recommandations personnalisées |
| POST | `/api/enrollments` | Inscription à un cours |
| PUT | `/api/enrollments/{id}` | Mise à jour progression |
| POST | `/api/ratings` | Noter un cours |
| GET | `/api/users/me/dashboard` | Dashboard utilisateur |
| GET | `/api/notifications` | Notifications |
| GET | `/api/books` | Liste livres |
| POST | `/api/books` | Ajouter livre |
| PUT | `/api/books/{id}` | Modifier livre |
| DELETE | `/api/books/{id}` | Supprimer livre |

## Documentation ajoutée

- `docs/TESTER_SUR_VSCODE.md` : étapes complètes de test.
- `docs/ARCHITECTURE.md` : architecture frontend/backend.
- `docs/CAHIER_TESTS.md` : scénarios de test.
- `docs/PLAN_PRESENTATION.md` : plan pour présentation.
- `backend/http-tests/e-learning-api.http` : tests API avec extension REST Client.

## Remarque

La version utilise H2 par défaut pour faciliter le test local. Pour MySQL, lancer `docker-compose up -d` puis adapter `application.properties`.
