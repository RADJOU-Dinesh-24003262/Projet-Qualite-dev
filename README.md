# Projet-Qualite-dev :  ⚔️ Theater Invasion - Armorica & Lycanthropie


Bienvenue dans **Theater Invasion**, une application de simulation développée dans le cadre du module "Qualité de Développement". Ce projet fusionne deux concepts : une simulation stratégique d'occupation (Gaulois vs Romains) et une simulation biologique complexe d'une colonie de lycanthropes (Loups-Garous).

🔗 **Dépôt GitHub** : [https://github.com/RADJOU-Dinesh-24003262/Projet-Qualite-dev](https://github.com/RADJOU-Dinesh-24003262/Projet-Qualite-dev)

-----

## 📋 Table des Matières

1.  [Contexte du Projet](#-contexte-du-projet)
2.  [Fonctionnalités](#-fonctionnalit%C3%A9s)
3.  [Architecture & Conception](#-architecture--conception)
4.  [Installation & Lancement](#-installation--lancement)
5.  [Qualité du Code & Tests](#-qualit%C3%A9-du-code--tests)
6.  [Manuel d'Utilisation](#-manuel-dutilisation)
7.  [Auteurs](#-auteurs)

-----

## 🏫 Contexte du Projet

Ce projet répond aux exigences des **TD3 (Cas Pratique Java)** et **TD4 (Algorithmique Avancée)** de l'IUT d'Aix-Marseille (AMU). Il met en œuvre des concepts avancés de Programmation Orientée Objet (POO) et respecte des normes strictes de qualité logicielle.

**Objectifs pédagogiques :**

  * Utilisation de **JavaFX** pour l'interface graphique.
  * Application des principes **SOLID** et **Design Patterns**.
  * Intégration continue et analyse statique (**Checkstyle, PMD, SpotBugs**).
  * Gestion de versions et travail collaboratif.

-----

## 🌟 Fonctionnalités

### 1\. Théâtre d'Opérations (Gaulois vs Romains)

  * **Lieux gérés** : Village Gaulois, Camp Romain Fortifié, Champ de Bataille.
  * **Personnages** :
      * *Gaulois* : Druides (Potions), Forgerons, Aubergistes, Guerriers.
      * *Romains* : Légionnaires, Centurions, Préfets.
  * **Cycle de vie** : Gestion de la faim (avec péremption des aliments), de la santé, et des combats.
  * **Potions Magiques** : Système de recettes personnalisables (ingrédients standards ou magiques comme les "Poils d'Idéfix").

### 2\. Simulation Biologique (Colonie de Loups-Garous)

Une simulation autonome et complexe gérée dans `org.example.model.pack` :

  * **Hiérarchie Sociale** : Structure stricte avec Couple Alpha (α), Bêta (β), Gamma... jusqu'à Oméga (ω).
  * **Domination** : Algorithmes de défis et de soumission basés sur la force et l'impétuosité.
  * **Communication** : Système de hurlements (*Appartenance, Domination, Soumission, Agressivité*).
  * **Cycle Biologique** :
      * Reproduction (saison des amours).
      * Vieillissement (Jeune -\> Adulte -\> Vieux).
      * Transformation (Humain \<-\> Loup) avec impact sur les statistiques.
  * **Solitaires** : Gestion des loups quittant la meute ou devenant solitaires.

### 3\. Interface Graphique (GUI)

  * **Vue Carte** : Visualisation dynamique des lieux et des populations.
  * **Journal d'événements** : Logs stylisés (Combats ⚔️, Morts 💀, Naissances 👶).
  * **Panneau de Contrôle** : Actions des chefs de clan et pilotage du temps.

-----

## 🏗 Architecture & Conception

L'architecture respecte le modèle **MVC (Model-View-Controller)** adapté à JavaFX :

  * **Modèle (`org.example.model`)** : Logique métier pure. Utilisation intensive de l'héritage (`AbstractCharacter`) et d'interfaces (`Leader`, `Worker`, `Combatant`).
  * **Vue (`org.example.ui`)** : Classes `PlacesView` (Carte) et `GameLogger` (Logs).
  * **Contrôleur** : `ControlPanel` gère les interactions utilisateur et met à jour le modèle via `TheaterInvasion`.

### Patrons de Conception (Design Patterns) utilisés :

1.  **Composition / Delegation** : La classe `Werewolf` délègue ses comportements complexes à des managers (`WerewolfStats`, `WerewolfHowlManager`, `WerewolfTransformationManager`) pour éviter une "God Class".
2.  **Strategy** : Les comportements de combat et de travail varient selon l'implémentation des interfaces.
3.  **Observer** : Utilisé dans l'UI pour réagir aux changements de sélection (JavaFX Properties).
4.  **Factory Method** : Utilisée via `GameInitializer` et les méthodes de création dans `ClanLeader`.

-----

## 🛠 Installation & Lancement

### Prérequis

  * **Java JDK 17** ou supérieur.
  * **Maven** 3.x.

### 1\. Clonage et Compilation

```bash
git clone https://github.com/RADJOU-Dinesh-24003262/Projet-Qualite-dev.git
cd Projet-Qualite-dev
mvn clean install
```

### 2\. Lancement de l'Application

Pour éviter les conflits de modules JavaFX, lancez l'application via la classe `Launcher` :

**Via Maven :**

```bash
mvn javafx:run
```

**Via Java (après compilation) :**

```bash
java -cp target/classes org.example.Launcher
```

-----

## 🛡 Qualité du Code & Tests

Ce projet intègre plusieurs outils d'analyse statique pour garantir la robustesse et la lisibilité du code.

### Exécuter les Tests Unitaires (JUnit 5)

```bash
mvn test
```

*Couverture : Vérifie la logique des potions, les règles de combat, la hiérarchie des loups et l'instanciation des composants UI.*

### Vérifier la Qualité du Code

Nous utilisons 3 outils complémentaires configurés dans le `pom.xml` :

1.  **Checkstyle** (Respect des conventions de nommage et formatage) :
    ```bash
    mvn checkstyle:check
    ```
2.  **PMD** (Détection de code mort, complexité cyclomatique, mauvaises pratiques) :
    ```bash
    mvn pmd:check
    ```
3.  **SpotBugs** (Détection de bugs potentiels, null pointers, exposion interne) :
    ```bash
    mvn spotbugs:check
    ```
    *(Note : Un fichier `spotbugs-exclude.xml` est présent pour gérer les exceptions justifiées par l'architecture JavaFX).*

-----

## 📖 Manuel d'Utilisation

L'interface est divisée en trois zones :

### 1\. Zone Centrale (Carte)

Affiche les lieux (Village, Camp, Enclos). Les personnages sont listés avec leurs points de vie.

  * **Rouge** : PV \< 30 (Danger).
  * **Gris** : Lieu vide.

### 2\. Panneau de Droite (Contrôle)

C'est ici que vous agissez. Sélectionnez d'abord un **Chef de Clan** dans la liste déroulante.

| Catégorie | Action | Description |
| :--- | :--- | :--- |
| **Temps** | `▶ Next Turn` | Avance d'un tour (Mode Manuel). |
| | `⏩ Auto Mode` | Lance la simulation en temps réel (Mode Automatique). |
| **Gestion** | `➕ Recruit` | Crée un personnage (Gaulois, Romain, Loup) dans le lieu. |
| | `💚 Heal` | Soigne tous les personnages du lieu. |
| | `🍖 Banquet` | Nourrit les troupes avec les stocks disponibles. |
| **Magie** | `🧪 Ask Potion` | Demande une potion à un Druide présent. |
| | `🍺 Give Potion` | Donne la potion à un personnage (Buff de force). |
| **Logistique**| `🚚 Transfer` | Déplace un personnage vers le Champ de Bataille ou l'Enclos. |

### 3\. Gestion Spéciale : Colonie de Loups

Des boutons spécifiques apparaissent pour gérer la biologie de la meute :

  * **📊 Stats Meute** : Affiche la hiérarchie complète (Alpha -\> Oméga) dans la console/logs.
  * **💕 Forcer Repro.** : Déclenche artificiellement la saison des amours (naissances).
  * **📢 Hurlement** : Provoque des interactions sociales aléatoires (hurlements).

-----

## 👥 Auteurs

Projet réalisé par un trinôme d'étudiants de l'IUT d'Aix-Marseille :

  * **Dinesh RADJOU**
  * **Amir**
  * **Matis ROMBI**

-----

*Ce projet est sous licence Creative Commons Attribution - Pas d’Utilisation Commerciale - Partage dans les Mêmes Conditions (BY-NC-SA).*