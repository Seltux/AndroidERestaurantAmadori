# AndroidERestaurantAmadori
TD Android réalisé dans le cadre du cours de développement mobile, avec pour objectif de comprendre comment marche le développement Android.

# APK
Vous pouvez trouver l'APK dans le répertoire suivant du projet : AndroidERestaurantAmadori/app/release/

# Ce qui a été réalisé
- Toutes les parties du TD ont été faites
- Ajout d'un écran d'accueil animé au lancement de l'application
- Ajout d'une Google Map + lien URI
- Ajout d'un chiffrement AES "CBC", utilisant : PBKDF2WithHmacSHA1, pour chiffrer le contenu du document JSON
- Ajout d'un espace Utilisateur, permettant à celui-ci de voir le détail de l'historique de ses commandes
- Ajout d'un "pull to refresh" (TO DO: Ajouter le Cache et rendre fonctionnel le "pull to refresh")
- Ajout d'un "Swipe to delete" pour supprimer les éléments du panier
- Ajout d'un système de vérification pour l "Login" et "SignUp" reposant sur une librairie (https://github.com/ragunathjawahar/android-saripaar)
- Ajout de plusieurs animations Lottie

# Ce qui reste à faire
- Mettre en place le Cache + fonctionnement "Pull to refresh"
- Mettre en place SQLITE pour une sauvegarde à l'aide de la librairie Room.
- Tester Firebase
- Mettre en place Koin
- Ajout d'élément User Friendly + Améliorations design
- Ajout Licence Apache 2.0 -> 

```bash
Copyright 2021 Amadori Mattéo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
