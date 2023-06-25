# fireSimulator (Français)

## Display

L'affichage montre des carrés de difféentes couleurs représentant l'état d'une case. Si elle est vert, elle est neutre,
si elle est orange, cela veut dire qu'au moins un feu peut se propager sur cette case à la prochaine itération
si elle est rouge, c'est qu'un feu y est présent et enfin, si c'est un carré vide, c'est que la case est devenue de la cendre

# Back

Pour résumer la logique métier, il y a 4 classes dans le package model : 
  - Une classe "Status" qui est un énum de tous les états possibles d'une case
  - Une classe "Coord" qui définit ce qu'est une coordonnée pour le programme
  - Une classe "Cell" qui définit ce qu'est une case ainsi que des méthodes associés
  - Une classe "Grid" qui définit la grille qui est composée de "Cell" et qui gère les intéractions entre celles-ci
La classe Grid initie sa grille à l'aide de l'outil CellsFactory (design pattern Factory) qui se charge de créer les bons objets Cell.
On fait de l'inversion de contrôle pour que la classe ne s'occupe pas de tout.
Enfin, nous avons le launcher dans le package Launcher qui s'occupe de lancer le programme et de faire l'appel à l'update toute les x secondes (2 pour l'instant)
