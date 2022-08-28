package sudoku.generator;

import java.io.PrintStream;

/**
 * Cette classe permet de generer une grille aleatoire de sudoku pleine et correcte 
 * 
 * @author Anais
 *
 */
public class GridPermut {
	
	/**
	 * critere: entier qui represente le nombre d'incoherences encore presente dans le sudoku
	 * cet attribut sera mis a jour lors des permutations
	 * critere=0 signifie que la grille est correcte
	 */
	static int critere;
	
	/**
	 * Permute des cases de la grille jusqu'à ce que la grille soit correcte
	 * @param grid Grille de depart fausse mais que l'on va permuter pour la rendre correcte
	 * @param outputStream Stream sur lequel on souhaite produire l'affichage de l'algorithme. Peut etre null pour ne produire aucun affichage.
	 */

	public static void permut(Grid grid, PrintStream outputStream){
		int incoh = calIncoh(grid); // on calcule le nombre initial d'incoherences de la grille
		critere=incoh; // on initialise le critere aux nombres d'incoherences
		Util.println("critere = " + critere, outputStream);
		
		//PRINCIPE
		// Le critere est initialement non nul du fait que la grille soit incorrecte
		// On va donc permuter les cases du sudoku pour diminuer le critere, jusqu'a le rendre nul
		//
		// On commence par choisir aleatoirement 2 cases à permuter entre elles
		// On rappelle qu'une case est definie par une ligne et une colonne
		// On applique la permutation a la grille de sudoku
		// On calcule le nouveau nombre d'incoherences de la grille
		// Si le nombre d'incoherences a diminuer et donc est inferieur au critere
		// alors on conserve la permutation et on met a jour la valeur du critere.
		// sinon on repermute les deux cases selectionnees.
		
		// On applique cet algorithme tant que le critere n'est pas nul
		while(critere!=0){
		int ligne1 = (int) (Math.random()*9 );  //tirage aleatoire de la ligne de la premiere case a permuter
		int colonne1 = (int) (Math.random()*9 ); // tirage aleatoire de la colonne de la premiere case a permuter
		int ligne2 = (int) (Math.random()*9 ); //tirage aleatoire de la ligne de la deuxieme case a permuter
		int colonne2 = (int) (Math.random()*9);// tirage aleatoire de la colonne de la deuxieme case a permuter
		
		permutcases(grid,ligne1,colonne1,ligne2,colonne2); // permutation des 2cases
		incoh = calIncoh(grid); // calcule du nombre d'incoherences de la grille permutee
		Util.println("critere = "+ critere, outputStream);
		if(incoh<=critere){
			critere=incoh;   // mise a jour du critere
		}else{
			permutcases(grid,ligne2,colonne2,ligne1,colonne1);  // annulation de la permutation, on revient a la grille precedente
		}
		
	}  
		Util.println("critere = "+ critere, outputStream);	
	}
	
	/**
	 * Permute 2 cases de la grille de sudoku
	 * @param grille : Grille que l'on va permuter
	 * @param ligne1 : ligne de la premiere case
	 * @param colonne1 colonne de la premiere case
	 * @param ligne2 ligne de la deuxieme case
	 * @param colonne2 colonne de la deuxieme case
	 */
	public static void permutcases(Grid grille, int ligne1, int colonne1, int ligne2, int colonne2){
		int temp= grille.getGrid()[ligne1][colonne1]; // on memorise la valeur de la case1
		grille.setCase(ligne1, colonne1, grille.getGrid()[ligne2][colonne2]);// on met la valeur de la case2 dans la case1
		grille.setCase(ligne2, colonne2, temp);// on met la valeur de la case1 dans la case2
	}
	 
	/**
	 * Calcule le nombre d'incoherences dans une ligne precise du sudoku vis a vis d'une case donnee
	 * @param grille Grille de travail
	 * @param ligne ligne de la case de reference
	 * @param col  colonne de la case de reference
	 * @return on retourne le nombre d'incoherences de la grille pour la ligne de la case etudiee
	 */
	 private static int checkLine( Grid grille, int ligne, int col) {
		 int incoh=0; // on initialise le nombre d'incoherence dans la ligne
		 int val = grille.getGrid()[ligne] [col]; // on memorise la valeur de la case etudiee
		 
		 // On verifie au niveau de la ligne de la case etudiee, qu'il n'y a aucune case suivante
		 // qui possede la meme valeur
		 // On verifie si la colonne de la case etudiee n'est pas la derniere du sudoku
		 // car si c'est la derniere il est inutile de verifier, on aura deja compte l'incoherence
		 // au niveau de la verification des cases precedentes
		 
         // on parcourt la ligne a partir de la case juste apres celle etudiee
		 // si la valeur est identique a val on incremente le nombre  d'incoherences
		 // sinon on passe a la case suivante
		 if(col!=8){
		   for( int j = col+1; j <9 ; j++) {
			   if( val == grille.getGrid()[ligne][j] ) {
				   incoh++;
			   }
		   }
		 }
		   return incoh; // on renvoie le nombre d'incoherences dans la ligne
	 }
	
	 /**
	  * Calcule le nombre d'incoherences dans une colonne precise du sudoku vis a vis d'une case donnee
	  * @param grille Grille de travail
	  * @param ligne ligne de la case de reference
	  * @param col  colonne de la case de reference
	  * @return on retourne le nombre d'incoherences de la grille pour  la colonne de la case etudiee
	  */
	 private static int checkCol( Grid grille, int ligne, int col) {
		 int incoh=0;// on initialise le nombre d'incoherence dans la colonne
		 int val = grille.getGrid()[ligne] [col]; // on memorise la valeur de la case etudiee
		 
		// On verifie au niveau de la colonne de la case etudiee, qu'il n'y a aucune case suivante
		 // qui possede la meme valeur
		 // On verifie si la colonne de la case etudiee n'est pas la derniere du sudoku
		 // car si c'est la derniere il est inutile de verifier, on aura deja compte l'incoherence
		 // au niveau de la verification des cases precedentes
		 
         // on parcourt la colonne a partir de la case juste apres celle etudiee
		 // si la valeur est identique a val on incremente  le nombre d'incoherences
		 // sinon on passe a la case suivante
		 if(ligne!=8){
		   for( int i = ligne+1; i <9 ; i++) {
			   if( val == grille.getGrid()[i][col] ) {
				   incoh++;
			   }
		   }
		 } 
		   return incoh; // on renvoie le nombre d'incoherences dans la colonne
	 }
	 
	 /**
	  * Calcule le nombre d'incoherences vis a vis d'une case donnee au sein du  carre où la case est presente
	  * Ici le sudoku possede 9carres
	  * @param grille Grille de travail
	  * @param ligne ligne de la case de reference
	  * @param col  colonne de la case de reference
	  * @return on retourne le nombre d'incoherences de la grille pour le carre de la case etudiee
	  */
	 private static int checkcarre( Grid grille, int ligne, int col) {
		 int incoh=0; //on initialise le nombre d'incoherence dans le carre
		 int carre=1; // on definit le carre , ici initialise a 1
		 
		 // On cherche selon la ligne et la colonne de la case dans quel carre du sudoku se trouve t-on.
		 // Par defaut j'ai defini les carres 
		 // carre1  | carre2   | carre3
		 // carre4  | carre5   | carre6
		 // carre7  | carre8   | carre9
		 
		 if (ligne<3) {
				 if(col<3){
					   carre = 1; // on est dans le carre 1
				 }	 
				 if(col>=3 && col<6){
					   carre = 2;  // on est dans le carre 2
				 }
				 
				 if(col>=6){
					 carre =3;  // on est dans le carre 3
				 }
			 }
		 if (ligne>=3 && ligne<6 ) {
			 if(col<3){
				   carre = 4;   // on est dans le carre 4
			 }	 
			 if(col>=3 && col<6){
				   carre = 5;    // on est dans le carre 5
			 }
			 
			 if(col>=6){
				 carre =6;   // on est dans le carre 6
			 }
		 }
		 if (ligne>=6  ) {
			 if(col<3){
				   carre = 7;   // on est dans le carre 7
			 }	 
			 if(col>=3 && col<6){
				   carre = 8;   // on est dans le carre 8
			 }
			 
			 if(col>=6){
				 carre =9;    // on est dans le carre 9
			 }
		 }
		 int val = grille.getGrid()[ligne] [col];  // valeur de la case etudiee
		 
		 // On verifie les cases du carre que l'on etudie
		 // on effectue un switch pour savoir dans quel carre on se trouve
		 // Du fait des methodes checkLine et checkCol il nous suffit de compter les incoherences des cases 
		 // appartenant au meme carre que la case de reference, mais de ligne et de colonne differente
		 // si la valeur est identique a val on incremente  le nombre d'incoherences
		 // sinon on passe a la case suivante
		 switch(carre){
		 	case 1: // on est dans le carre 1
		 		for(int i=0; i<3; i++){
		 			if (i!=ligne){
		 				for(int j=0; j<3; j++){
		 					if (j!=col){
		 						if( val == grille.getGrid()[i][j] ) {
		 						   incoh++;
		 					   }
		 					}
		 				}
		 			}
		 		}
		 		break;
		 		
		 	case 2: // on est dans le carre 2
		 		for(int i=0; i<3; i++){
		 			if (i!=ligne){
		 				for(int j=3; j<6; j++){
		 					if (j!=col){
		 						if( val == grille.getGrid()[i][j] ) {
		 						   incoh++;
		 					   }
		 					}
		 				}
		 			}
		 		}
		 		break;
		 		
		 	case 3: // on est dans le carre 3
		 		for(int i=0; i<3; i++){
		 			if (i!=ligne){
		 				for(int j=6; j<9; j++){
		 					if (j!=col){
		 						if( val == grille.getGrid()[i][j] ) {
		 						   incoh++;
		 					   }
		 					}
		 				}
		 			}
		 		}
		     break;
		 
	 	case 4: // on est dans le carre 4
	 		for(int i=3; i<6; i++){
	 			if (i!=ligne){
	 				for(int j=0; j<3; j++){
	 					if (j!=col){
	 						if( val == grille.getGrid()[i][j] ) {
	 						   incoh++;
	 					   }
	 					}
	 				}
	 			}
	 		}
	 		break;
	 		
	 	case 5:// on est dans le carre 5
	 		for(int i=3; i<6; i++){
	 			if (i!=ligne){
	 				for(int j=3; j<6; j++){
	 					if (j!=col){
	 						if( val == grille.getGrid()[i][j] ) {
	 						   incoh++;
	 					   }
	 					}
	 				}
	 			}
	 		}
	 		break;
	 		
	 	case 6: // on est dans le carre 6
	 		for(int i=3; i<6; i++){
	 			if (i!=ligne){
	 				for(int j=6; j<9; j++){
	 					if (j!=col){
	 						if( val == grille.getGrid()[i][j] ) {
	 						   incoh++;
	 					   }
	 					}
	 				}
	 			}
	 		}
	 		break;
	 		
	 	case 7: // on est dans le carre 7
	 		for(int i=6; i<9; i++){
	 			if (i!=ligne){
	 				for(int j=0; j<3; j++){
	 					if (j!=col){
	 						if( val == grille.getGrid()[i][j] ) {
	 						   incoh++;
	 					   }
	 					}
	 				}
	 			}
	 		}
	 		break;
	 		
	 	case 8: // on est dans le carre 8
	 		for(int i=6; i<9; i++){
	 			if (i!=ligne){
	 				for(int j=3; j<6; j++){
	 					if (j!=col){
	 						if( val == grille.getGrid()[i][j] ) {
	 						   incoh++;
	 					   }
	 					}
	 				}
	 			}
	 		}
	 		break;
	 		
	 	case 9: // on est dans le carre 9
	 		for(int i=6; i<9; i++){
	 			if (i!=ligne){
	 				for(int j=6; j<9; j++){
	 					if (j!=col){
	 						if( val == grille.getGrid()[i][j] ) {
	 						   incoh++;
	 					   }
	 					}
	 				}
	 			}
	 		}
	 		break;
	 }
		   return incoh; // on renvoie le nombre d'incoherences dans le carre
	 }
	 
	 /**
	  * Calcule du nombre d'incoherences dans le sudoku
	  * @param grille Grille de travail
	  * @return renvoie le nombre total d'incoherences
	  */
	public static int calIncoh(Grid grille) {
		int incoh1=0; // initialisation du nombre d'incoherences pour une ligne
		int incoh2=0; // initialisation du nombre d'incoherences pour une colonne
		int incoh3=0; // initialisation du nombre d'incoherences pourun carre
		
		// on parcourt toutes les cases et on calcule le nombre d'incoherences pour chaque case
		// on verifie la ligne, la colonne et le carre
		for(int ligne=0; ligne<9; ligne++){
			for(int col=0; col<9; col++){
		 incoh1= incoh1+checkLine(grille,  ligne,  col); // nombre d'incoherences dans la ligne 
		 incoh2 = incoh2+checkCol(grille,  ligne,  col); // nombre d'incoherences dans la colonne
		 incoh3= incoh3+checkcarre(grille,ligne,col); //nombre d'incoherences dans le carre
			}
		}
		int incoh=incoh1+incoh2+incoh3; // nombre d'incoherences total
		return incoh;  // on renvoie le nombre d'incoherences total
	}
}
