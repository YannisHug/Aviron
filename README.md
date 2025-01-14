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
