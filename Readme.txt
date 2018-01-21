L'idée pour les mouvements :
- Chaque pièce peut déterminer les positions à partir de sa position actuelle (elle ne connait pas le Chessboard, donc la position des autres pièces)
- Le Chessboard détermine si les positions renvoyées sont viables
	Traduction : si elles sont dans le tableau
- Le Game vérifie que la pièce peut bouger (c'est le tour du joueur correspondant, la case d'arrivée n'est pas déjà occupée par une pièce du même joueur)

Pour les spécial moves, le Game vérifie si c'est un coup spécial
	-> Si oui, il vérifie quel type de pièce demande ce coup spécial et il teste les différentes conditions pour le valider
		(Si Roi : tour pas déplacée, pas ligne de vue, pas d'échec sur le chemin)
		(Si ...)

==========================
Idée annexe : la méthode Move de Game
Avoir une méthode privée qui prend en paramètre une Pièce et sa nouvelle position
-> Permet de bouger une seconde pièce dans le même "mouvement"

==========================
GameController n'a pas besoin de méthodes compliquées pour dessiner ou non les pièces.
Il suffit de lui passer un tableau contenant directement les pièces du damier, et il redessine tout à chaque fois.