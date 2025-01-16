# SnakeGame

Ce projet est le code du jeu Snake;

Le projet et le but de la conception du jeu snake est de comprendre comment utiliser un array a deux dimension pour arriver a reproduire un serpent qui peut bouger dans une grille en haut, à gauche, à droite, en bas et qui grandit à chaque fois qu'il mange de la nourriture, le jeu se termine lorsque le serpent touche un bord de la fenêtre graphique ou si sa tête touche une partie de son corps.


Pour comprendre comment déplacer le serpent sur la grille comme on le souhaite il faut savoir que le jeu du snake a plusieur partie principale pour bien fonctionner, il faut une variable pour la tete (égale a 1) et une variable pour le corps qui sera toujours plus grande ou egale que celle de la tete. Lorsque le serpent se deplace sa tête va se deplacer de 1 dans la grille dépendant de la direction choisie, son corps va se deplacer a la position précédente de la tete et le bout de sa queue va se faire enlever si sa valeur est plus grande que la taille entière du snake. La grille est remplie de valeur 0 pour faciliter le deplacement du serpent car lorsque le serpent se deplace par rapport au temps et instances de grille on compare la variable de la tête du serpent au variable qui vont se faire remplacer par la tête du serpent et c'est plus facile de comparer par des chiffre que par des charactère.   


Pour lancer le jeu et pour qu'il marche bien bouger le dossier src (du répertoire) au complet (avec tous les fichiers png et wav) puis lancez le fichier NewSnakeGame.scala





This is a project made by Filippo Gorini and Alexandre Raccurt 
