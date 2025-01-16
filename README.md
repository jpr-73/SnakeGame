# SnakeGame



Ce projet est le code du jeu Snake;

Le projet et le but de la conception du jeu snake est de comprendre comment utiliser un array a deux dimension pour arriver a reproduire un serpent qui peut bouger dans une grille en haut, à gauche, à droite, en bas et qui grandit à chaque fois qu'il mange de la nourriture, le jeu se termine lorsque le serpent touche un bord de la fenêtre graphique ou si sa tête touche une partie de son corps.


Pour comprendre comment déplacer le serpent sur la grille comme on le souhaite il faut savoir que le jeu du snake a plusieur partie principale pour bien fonctionner. Il faut une variable pour la tete (égale a 1) et une variable pour le corps qui sera toujours plus grande que celle de la tete. Lorsque le serpent se deplace sa tête va se deplacer de 1 dans la grille dépendant de la direction choisie, son corps va se deplacer a la position précédente de la tete et le bout de sa queue va se faire enlever si sa valeur est plus grande que la taille entière du serpent. La grille est remplie de valeur égale à 0 pour faciliter le deplacement du serpent car lorsque le serpent se deplace par rapport au temps et instances de la grille on compare la variable de la tête du serpent au variable qui vont se faire remplacer par la tête du serpent et c'est plus facile de comparer un numero avec un chiffre qu'avec des charactère.

Pour que le serpent se déplace sur la grille il faut faire une variable qui enregistre les actions passé dans un interval de temps choisis pour que le mouvement sois fluide à nos yeux nous avons choisis 120 ms. Nous avons ajouté une fonction qui place le serpent a la direction enregistré (choisi). Nous avons fais une fonction qui déplace le serpent dépandent de la direction enregistré (choisi) avec le clavier. Nous avons aussi fais une fonction qui change la direction si la direction précédente du serpent a changé

Nous avons implémenté une fonction qui dessine le serpent lorsqu'il meurt, qui lui dessine des croix pour les yeux, nous avons ajouter des fonctions qui reinitialise la position initiale du serpent lorsqu'il meurt et remet les variable qui verifie certaine conditions à leurs valeur orinale. 

Nous avons ajouté une fonction qui enregistre les touche du clavier appuyer, lorsqu'une touche valide est appuyer c'est-à-dire les flèches du clavier ou wasd alors le serpent se déplace accordement dans le sens de ses flèche ou en haut pour 'w' en bas pour 's' à droite pour 'd' et à gauche pour 'a'. 

Pour déssiner les yeux et la langue du serpent il fallait faire une fonction qui met les yeux à la position avant de la tête et qu'ils suivent le mouvement que la tête va faire et la même chose pour la langue, mettre la langue devant la tête du serpent et ce fait placé toujours a l'avant de la tête dépendant du mouvement que le serpent va faire

Ensuite nous avons ajouter une fonction qui place de la nourriture dans la grille dans un emplacement au hasard au début d'une partie et lorsque la tête du serpent atteint la position de la nourriture le serpent grandie en taille de 1 et la pomme va se faire placer encore une fois dans une position au hasard qui n'est pas une position déjà occupé par la tête du serpent ou par son corps. La pomme prend une valeur de -1 pour des raison de comparaison aussi et nous avons fais une autre fonction qui déssine la pomme dépendant de la position au hasard choisis par la fonction d'avant.


Avec la librairie FunGraphics nous avons aussi ajouté une fonction qui dessine une grille graphique de couleur vert clair et vert nous avons aussi ajouter une marge délimité par un trait bleu foncé de couleur vert clair en haut de la page pour afficher le score et le meilleur score des parties joué lorsque le programme est éxecuté. Nous avons aussi ajouté une fonction de "menu" ou on peut voir le serpent dessiner sur la grille avec la pomme en face de lui, le titre du jeu un bouton pour jouer et une musique en arrière plan. 

Nous avons ajouté une fonction qui fait un bouton où lorsque l'on appuie dessus le jeu se lance pour cela nous avons ajouté une condition qui vérifie si la souris a été appuyé dans la zone où le bouton ce situe dans cette même zone nous avons déssiné un rectangle bleu avec le mot "PLAY" ou "REPLAY" dépendant de si c'est la première partie ou si le joueur veut rejouer une partie. 

Nous avons ajouté une fonction qui commence le jeu au moment ou le bouton "PLAY" ou "REPLAY" est appuyer, cette fonction va arrêter la musique en arrière-plan du menu et jouer une musique lors du jeu et va placer la nourriture. Nous avons aussi une fonction restart qui permet de rejouer cette fonction remet a 0 les variable importante qui vérifie certaines condition du jeu. 


Nous avons implémenté une classe qui nous aide à utiliser des fichiers .wav pour mettre de la musique en arrière-plan et pour ajouter des bruitage à certaines actions par exemple lorsque le serpent meurt, ou lorsque le serpent mange de la nourriture. 


## Règle Du Jeu

quand le code est lancé on arrive sur la page de menu du jeu. 
Pour commencer à jouer il suffit de clicker sur le bouton "PLAY" et la partie commence. 
Le serpent avance tout seul mais pour changer de direction il faut appuyer sur les flèches du clavier ou les touches WASD.
Le but du jeu est de manger le plus de pommes possible sans mourir.
Plus le serpent mange de pommes plus il grandit.
Attention! Si le serpent se rentre dedans ou s'il fonce sur un mur tu as perdu et le jeu s'arrête.
Quand le jeu s'arrête on arrive sur la page de fin mais si tu veux rejouer il suffit de clicker sur la touche "REPLAY".




## Voici une image lorsque on lance le programe

![image](https://github.com/user-attachments/assets/3e767879-d29d-4b3b-8780-ddbe5c327c25)

## Voici une image lorsque l'on appuye sur le bouton "PLAY"
![image](https://github.com/user-attachments/assets/3375d33e-5b7d-411d-bbab-580e67ac0837)

## Voici une image lorsque l'on a perdu
![image](https://github.com/user-attachments/assets/2868ce80-03ae-4850-81a0-047ab14d8bfb)



## Structure du code

Premierement nous avons une class Audio pour jouer tout les fichiers de son que nous avons voulu ajouter pour faciliter les moment distinct dont on voulais jouer certains son, avec une fonction play et stop qui joue et arrête le fichier audio. Ensuite nous avons toutes les variable importante que nous avons besoins d'utiliser pour certaines condition ou pour faire le serpent ou la nourriture, ou jouer certains son. Nous avons les fonctions pour le score du jeu enregistrant le score et le meilleure score. Un resetsnake qui va repositionner le serpent a sa position initiale. 

Une fonction qui dessine l'arrière plan du jeu et une marge, ensuite une fonction qui dessine l'arrière plan du menu en utilisant la fonction précédente, une fonction qui dessine le menu avec tout les détails ; titre, serpent, nourriture et arrière-plan. 

Une fonction qui dessine le serpent dépendant de sa position et de sa direction (sa tête change de sens dépendant du sens du serpent), just en dessous nous avons une fonction qui dessine le serpent au moment d'une colision, ensuite une fonction qui dessine la nourriture dans la page. Une fonction qui affiche la grille dans la et affiche le tout (grille, serpent, pomme,..) sur la fenêtre FunGraphics pour les tests.

Une fonction qui deplace le serpent dans la direction enregistré, une fonction qui place la nourriture à un endroit aléatoire qui n'est pas occupé par le serpent, une fonction qui change la direction du serpent, un timer pour enregistrer les déplacement du serpent toutes les 115 ms, une fonction qui capte les touches (flèches et WASD) pour changer la direction du serpent.

Une fonction qui crée le du bouton (avec certaines dimensions) et le place à un certain endroit, une fonction qui crée un rectangle avec le string indiqué à la même position que le bouton, une fonction qui commence le jeu, une fonction qui recommence le jeu et le programme principal qui appel les fonctions importantes. 


Pour lancer le jeu et pour qu'il marche bien bouger le dossier src (du répertoire) au complet (avec tous les fichiers png et wav) puis lancez le fichier NewSnakeGame.scala




This is a project made by Filippo Gorini and Alexandre Raccurt 
