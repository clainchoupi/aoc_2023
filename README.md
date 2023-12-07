# Advent Of Code 2023

Ce repo est un template pour initier des projets pour résoudre les puzzles de l'Advent Of Code : https://adventofcode.com

## How To
- Télécharger les inputs du jour souhaité et les mettre dans `resources\DAY\xx.txt`
  - Où trouver l'input ? Exemple : https://adventofcode.com/2023/day/5 
- Faire un modele ou traiter directement ?
  - Certains exercices nécessitent de modéliser la structure des objets. D'autres peuvent être traités directement au moment de la lecture du fichier


## Résultats
| Jour 	| Réussite ?	| Temps d'exécution 	| A retenir 	|
|---	|---	|---	|---	|
| J01 	| ✅ / ✅ 	| 84 ms 	| Très dur pour un Jour 1 de AOC<br> * Pattern avec ?= pour gérer les chevauchements 	|
| J02 	| ✅ / ✅  	| 71 ms 	| Simple, pattern D+ pour gérer les numériques 	|
| J03 	| ✅ / ✅  	| 31 ms 	| Compliqué, formule pour trouver les valeurs adjacentes intéressante 	|
| J04 	| ✅ / ✅  	| 78 ms 	| Simple, juste à gérer la logique de la part2 avec la répétition des n cartes 	|
| J05 	| ✅ / ✅  	| part 1 : 50 ms<br>part 2 : 24 minutes 	| 🤯<br> Utilisation de long car int trop petit, 2 milliards d'itérations en brutforce<br> * part 2 : en utilisant 10 threads car il y a 10 groupes de seeds<br>* puis amélioration à **4 minutes 40 sec**, en passant à 20 threads et en divisant chaque groupe de seeds par 2<br>* tester en Java 21 ?<br>* tester avec VirtualThreads ? <br><br>* Inspiré par la solution de [Daniel Persson](https://github.com/kalaspuffar) : https://github.com/kalaspuffar/advent2023/blob/main/src/main/java/org/ea/Day5.java 	|
| J06 	| ✅ / ✅  	| 88 ms 	| Vraiment simple 	|  	|
| J07 	| ✅ / ✅  	| 174 ms 	| Part 2 pour gérer toutes les combinaisons à améliorer<br> **Utilisation de Comparator custom pour faire un tri** 	|  	|
