L'id�e pour les mouvements :
- Chaque pi�ce peut d�terminer les positions � partir de sa position actuelle (elle ne connait pas le Chessboard, donc la position des autres pi�ces)
- Le Chessboard d�termine si les positions renvoy�es sont viables
	Traduction : si elles sont dans le tableau
- Le Game v�rifie que la pi�ce peut bouger (c'est le tour du joueur correspondant, la case d'arriv�e n'est pas d�j� occup�e par une pi�ce du m�me joueur)

Pour les sp�cial moves, le Game v�rifie si c'est un coup sp�cial
	-> Si oui, il v�rifie quel type de pi�ce demande ce coup sp�cial et il teste les diff�rentes conditions pour le valider
		(Si Roi : tour pas d�plac�e, pas ligne de vue, pas d'�chec sur le chemin)
		(Si ...)

==========================
Id�e annexe : la m�thode Move de Game
Avoir une m�thode priv�e qui prend en param�tre une Pi�ce et sa nouvelle position
-> Permet de bouger une seconde pi�ce dans le m�me "mouvement"

==========================
GameController n'a pas besoin de m�thodes compliqu�es pour dessiner ou non les pi�ces.
Il suffit de lui passer un tableau contenant directement les pi�ces du damier, et il redessine tout � chaque fois.