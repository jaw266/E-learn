# Guide de test dans VS Code — E-Learn Pro V2

## 1. Prérequis

Installer :
- Java 17 ou plus
- Maven
- Node.js 18 ou plus
- VS Code

Extensions recommandées :
- Extension Pack for Java
- Spring Boot Extension Pack
- ESLint
- Prettier

## 2. Ouvrir le projet

```bash
code e-learn-dev-v2
```

## 3. Lancer le backend

Dans un terminal VS Code :

```bash
cd backend
mvn spring-boot:run
```

Backend : http://localhost:8080

Console H2 : http://localhost:8080/h2-console

Paramètres H2 :

```txt
JDBC URL: jdbc:h2:mem:elearnpro
User: sa
Password: vide
```

## 4. Lancer le frontend

Dans un deuxième terminal VS Code :

```bash
cd frontend
npm install
npm run dev
```

Frontend : http://localhost:5173

## 5. Comptes de test

```txt
Utilisateur: demo@elearnpro.com / password
Admin:       admin@elearnpro.com / admin123
```

## 6. Scénarios de test fonctionnel

### Authentification
1. Ouvrir `/login`.
2. Se connecter avec `demo@elearnpro.com`.
3. Vérifier la redirection vers l'accueil.
4. Se déconnecter.
5. Créer un nouveau compte dans `/register`.

### Catalogue
1. Aller dans `/catalogue`.
2. Rechercher un cours par mot-clé.
3. Filtrer par domaine.
4. Filtrer par niveau.

### Livres gratuits
1. Aller dans `/livres`.
2. Filtrer les livres par domaine.
3. Ajouter un livre.
4. Modifier un livre.
5. Supprimer un livre.

### Recommandations
1. Se connecter.
2. Vérifier la section recommandations sur l'accueil.
3. Changer le domaine dans un nouveau compte pour observer d'autres suggestions.

### Dashboard
1. Aller dans `/dashboard`.
2. Vérifier statistiques : nombre de cours, progression moyenne, notes.
3. S'inscrire à un cours puis vérifier l'évolution.

### Notifications
1. S'inscrire à un cours.
2. Vérifier la création d'une notification.
3. Marquer une notification comme lue.

## 7. Test API avec REST Client

Installer l'extension VS Code **REST Client** puis ouvrir :

```txt
backend/http-tests/e-learning-api.http
```

Cliquer sur **Send Request** pour tester les endpoints.

## 8. Erreurs fréquentes

### Port 8080 déjà utilisé
Changer le port dans `backend/src/main/resources/application.properties` :

```properties
server.port=8081
```

Puis modifier le frontend :

```txt
frontend/.env
VITE_API_URL=http://localhost:8081/api
```

### Frontend ne communique pas avec backend
Vérifier que le backend tourne et que l'URL API est correcte :

```txt
http://localhost:8080/api
```

### Maven non reconnu
Installer Maven et ajouter Maven au PATH Windows.

### Node/npm non reconnu
Installer Node.js LTS et redémarrer VS Code.
