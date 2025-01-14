# Application Aviron

Bienvenue dans la branche dédiée au développement de l'application Aviron. Ce document décrit la structure des fichiers et dossiers du projet, ainsi que leur rôle. 

## Structure du projet

Voici une description des principaux fichiers et dossiers de ce dépôt :

### **1. Dossiers principaux**

- **`app/`**
  - Contient le code source de l'application Android.
  - Structure typique :
    - `src/main/java/` : Contient le code Java de l'application.
    - `src/main/res/` : Contient les ressources (XML de mise en page, images, chaînes, etc.).
    - `src/main/AndroidManifest.xml` : Fichier de configuration décrivant les composants de l'application.

- **`gradle/`**
  - Contient les fichiers nécessaires pour la configuration et le fonctionnement de Gradle.
  - Modifiez rarement ces fichiers à moins de changer des paramètres globaux de Gradle.

- **`.idea/`**
  - Dossier généré par Android Studio contenant les paramètres spécifiques à l'environnement de développement.
  - **Ignoré par Git** via le fichier `.gitignore`.

---

### **2. Fichiers importants**

- **`build.gradle.kts`**
  - Fichier de configuration de Gradle pour le projet principal.
  - Définit les dépendances globales et les plugins utilisés.

- **`settings.gradle.kts`**
  - Fichier Gradle utilisé pour déclarer les sous-projets/modules inclus dans le build.

- **`gradle.properties`**
  - Contient des propriétés spécifiques au système de build Gradle.

- **`gradlew` et `gradlew.bat`**
  - Scripts permettant d'exécuter Gradle sans nécessiter une installation locale.
  - Utilisez `gradlew` sur Linux/macOS et `gradlew.bat` sur Windows.

- **`.gitignore`**
  - Liste des fichiers et dossiers ignorés par Git pour éviter de versionner des fichiers inutiles (par exemple : `.idea/`, `build/`, etc.).
---

## Instructions pour démarrer

1. **Cloner le projet :**
   ```bash
   git clone https://github.com/YannisHug/Aviron.git
   cd Aviron

   ---

## Jalon 1

### 1. Structure initiale de l'application

La première étape a consisté à établir les dépendances entre les différentes pages de l'application. À ce stade, l'objectif était de structurer l'application de manière fonctionnelle sans entrer dans les détails des fonctionnalités spécifiques. Cela a permis de poser les bases et d'assurer une navigation fluide entre les écrans principaux.

### 2. Focus sur la page de connexion

- Mise en place d'une **page de connexion** pour permettre l'accès à l'application.
- Création d'une page optionnelle pour **la création de compte**, facilitant l'inscription des utilisateurs.
- Gestion des **identifiants et mots de passe**, stockés de manière sécurisée dans un fichier texte local à la tablette. Chaque coach dispose ainsi de ses propres données spécifiques.

### 3. Focus sur la section "Réglages"

- Développement des **fonctionnalités de connexion avec les capteurs** via des boutons dédiés.
- Création d'un **service TCP Server** pour gérer les connexions réseau.
- Mise en place d'une **classe TcpClientHandler** pour gérer les connexions des clients.
- Conception d'une **classe SensorManager** permettant de différencier et de gérer les 4 capteurs utilisés dans l'application.

### 4. Application en fonctionnement

Lors du lancement de l'application, l'utilisateur suit le parcours suivant :

1. **Page de Connexion** : L'utilisateur arrive sur la première activité où il peut se connecter à son compte ou en créer un nouveau.
2. **Page Principale (MainActivity)** : Une fois connecté, l'utilisateur accède à la page principale qui lui permet de naviguer vers plusieurs sections :
    - **Réglages** : Permet de connecter, calibrer les capteurs et enregistrer les données.
    - **Visualisation des données** : Offre un accès aux données enregistrées pour une consultation détaillée.
    - **Coaching** : Fournit des outils et ressources pour accompagner l'utilisateur dans ses activités.
    - **Base de données** : Permet de consulter ou de gérer les données stockées dans l'application.
  
### Finis le 14/01/2025 😊

