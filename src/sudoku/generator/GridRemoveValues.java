package sudoku.generator;

import java.io.PrintStream;
import java.util.ArrayList;

import sudoku.solveur.SudokuSolver;

/**
 * Cette classe permet de retirer des valeurs d'une grille de sudoku, jusqu'a obtenir un nombre minimal de valeurs
 * @author Anthony
 *
 */
public class GridRemoveValues {

	/**
	 * Retire des valeurs de la grille en utilisant le methode du recuit simule
	 * @param grid Grille dont on souhaite retirer des valeurs
	 * @param NbVal Nombre de valeurs que l'on souhaite avoir dans la grille finale
	 * @param outputStream Stream sur lequel on souhaite produire l'affichage de l'algorithme. Peut etre null pour ne produire aucun affichage.
	 */
	public static void removeValuesRecuit(Grid grid, int NbVal, PrintStream outputStream){
		
		Grid currentGrid = grid.clone(); // La grille actuelle en cours de calcul
		Grid bestGrid = grid.clone(); // La meilleur grille calculee, c'est a dire celle possedant le moins de cases
		
		ArrayList<Modification> modifs = new ArrayList<Modification>(); // La liste des modifications possibles
		ArrayList<Modification> testedModifs = new ArrayList<Modification>(); // La liste des modifications inutiles testees pour la grille courante. Une modification inutile ne peut etre qu'un masquage.
		
		// PRINCIPE :
		// La liste modifs contient la liste de toutes les modifications qui peuvent etre appliquees a la grille pour
		// une iteration donnee. Une modification est representee par les indices d'une case et la modification a lui appliquer (masquer = supprimer, ou demasquer = ajouter la valeur)
		// Initialement la liste contient 81 modifications, une par case, chacune de ses modifications representant le masquage de la case concernee.
		// On tire une modification au sort dans cette liste. On applique la modification sur la grille et on verifie le critere.
		// On verifie egalement si la nouvelle grille obtenue suite a cette modification possede une solution unique.
		// Si ce n'est pas le cas et qu'il s'agit d'un masquage, alors on retire la modification de la liste modifs et on la transfert dans la liste testedModifs, qui contient les modifications qui ne sont pas applicables pour la grille actuelle.
		// Cela nous permet de ne pas retirer au sort une modification qui serait inutile pour la grille en cours de calcul.
		// Et on annule ensuite la modification qui a ete appliquee sur la grille.
		// Si au contraire toutes les conditions sont verifiees, alors la modification est appliquee difinivement.
		// Si une case a ete demasquee (ajoutee), toutes les modifications inutiles pour la grille precedentes, stockees dans testedModifs, sont transferees dans modifs et peuvent
		// ainsi de nouveau etre appliquees pour la nouvelle grille obtenue.
		// Si une case a ete masquee (supprimee), les modifications inutiles pour la grille precedentes sont conservees pour la grille actuelle, car si les modifications inutiles de la grille precedente
		// engendraient plusieurs solutions, alors cela sera toujours le cas pour la grille precedente a laquelle on aura supprime une valeur, donnant ainsi la grille actuelle.
		// L'usage de ces deux listes permet d'optimiser l'algorithme en evitant les calculs inutiles.
		
		
		Modification currentModif; // La modification que l'on souhaite appliquer a la grille
		
		int currentCritere; // Le critere de la grille actuelle
		int oldCritere = getCritere(currentGrid); // Le critere de la grille precedante
		double temperature = getTemperature(-1); // Temperature de l'iteration actuelle
		double probabilite; // Probabilite d'appliquer le demasquage d'une case
		
		// On itinialise la liste des modifications.
		// Toutes les cases peuvent etre masquees.
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				modifs.add(new Modification(i,j,Modification.MASK));
			}
		}
		
		// Tant qu'une grille correcte nest pas obtenue
		do{
			// On tire au hasard une modif a effectuer
			currentModif = getModif(modifs);

			// On applique la modification
			apply(currentGrid, currentModif);
						
			// On verifie le critere
			currentCritere = getCritere(currentGrid);
			
			// On regarde si le critere au augmente ou diminue
			if(currentCritere>oldCritere){
				// Le critere a augmente
				// On souhaite demasquer une valeur (ajouter une case)
				
				// On recupere la nouvelle temperature
				temperature = getTemperature(temperature);
				
				// Probabilite d'appliquer la modification
				probabilite = Math.exp(-(currentCritere-oldCritere)/temperature);

				// On verifie si la modification est appliquee grace a la probabilite
				if(!(Math.random()<probabilite)){
					// On n'applique pas la modification
					// On annule donc la modification effectuee
					// Etant donne qu'il s'agit d'un demasquage, on conserve la modification dans la liste
					unapply(currentGrid,currentModif);
				}
				else{
					// Sinon la modification est appliquee
					// On ajoute dans la liste la modification inverse. Si la case a ete masquee, elle peut maintenant etre demasquee, et inversement
					currentModif.inverseModification();
					
					// La grille a ete modifiee, les modifications deja testees avec la grille precedente peuvent de nouveau etre testees
					modifs.addAll(testedModifs);
					testedModifs.clear();
				}
			}
			else{
				// On souhaite masquer une valeur
				// Etant donne que l'on supprimer une case, on doit verifier que la nouvelle grille possede une solution unique
				if(!sudokuCanBeResolved(currentGrid)){
					// La grille ne possede pas de solution unique
					// On n'applique pas la modification
					// On annule donc la modification effectuee
					unapply(currentGrid,currentModif);		
					// On enleve la modification des modifications a tester, et on l'ajoute dans la liste des modifs deja testees pour la grille actuelle
					modifs.remove(currentModif);
					testedModifs.add(currentModif);
				}
				else{
					// On applique la modification
					// On ajoute dans la liste la modification inverse. Si la case a ete masquee, elle peut maintenant etre demasquee, et inversement
					currentModif.inverseModification();
					
					// On ne transfert pas le contenu de testesModifs dans modifs, car si des modification etaient inutiles pour la grille precedente
					// elles le seront egalement pour cette meme grille a laquelle on aura retire une case
					
					// Le critere a diminue, on verifie s'il s'agit de la meilleure grille trouvee jusqu'a maintenant
					if(currentGrid.getnNbMaskedVal()>bestGrid.getnNbMaskedVal()) bestGrid = currentGrid.clone();
				}	
			}
			// On sauvegarde le critere de la grille actuelle
			oldCritere = getCritere(currentGrid);
			
			// On effectue une trace
			Util.println("Temperature " + temperature + " \t| meilleure grille : " + (bestGrid.getnNbUnmaskedVal()) + " \t| grille actuelle " + (currentGrid.getnNbUnmaskedVal()), outputStream );

		}while(temperature>=0.03 && (bestGrid.getnNbUnmaskedVal())>NbVal);
		
		// On recopie la meilleure grille obtenue dans la grille passee en parametre
		grid.setGrid(bestGrid.getGrid());
		grid.setMaskedValues(bestGrid.getMaskedValues());
	}
	
	/**
	 * Retire des valeurs de la grille sans utiliser de recuit
	 * @param grid Grille dont on souhaite retirer des valeurs
	 * @param NbVal Nombre de valeurs que l'on souhaite avoir dans la grille finale
	 * @param outputStream Stream sur lequel on souhaite produire l'affichage de l'algorithme. Peut etre null pour ne produire aucun affichage.
	 */
	public static void removeValuesSimple(Grid grid, int NbVal, PrintStream outputStream){
		
		ArrayList<Modification> modifs = new ArrayList<Modification>(); // Liste des modifications possibles a appliquer
		Modification currentModif; // La modification que l'on souhaite appliquer a la grille
		
		// On itinialise la liste des modifications.
		// Toutes les cases peuvent etre masquees.
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				modifs.add(new Modification(i,j,Modification.MASK));
			}
		}
		
		do{
			// On tire au hasard une modif a effectuer
			currentModif = getModif(modifs);

			// On applique la modification
			apply(grid, currentModif);
						
			// On souhaite masquer une valeur
			// Etant donne que l'on supprimer une case, on doit verifier que la nouvelle grille possede une solution unique
			if(!sudokuCanBeResolved(grid)){
				// On n'applique pas la modification
				// On annule donc la modification effectuee
				unapply(grid,currentModif);	
				// La derniere grille obtenue possede plusieurs solutions
				// On la supprime dans la liste des modifications
				modifs.remove(currentModif);

			}
			else{
				// On applique la modification
				// On la supprime dans la liste des modifications
				modifs.remove(currentModif);
			}	
			
			// On affiche une trace
			Util.println("grille actuelle " + (grid.getnNbUnmaskedVal()) + " " + modifs.size(), outputStream );

		}while(modifs.size()>0);

	}
	
	/**
	 * Verifie si une grille possede une ou plusieurs solutions
	 * @param grid La grille a verifier
	 * @return true si le sudoku possede exactement une solution, false s'il possede plus d'une solution
	 */
	private static boolean sudokuCanBeResolved(Grid grid){
		int[][] cells = grid.getGridCopy();
		// On fait appelle au solveur externe
		int nbsol = SudokuSolver.nbSolutions(cells);
		
		if(nbsol==0){
			throw new RuntimeException("La grille de sudoku recue par le programme est incorrecte");
		}
		
		return nbsol==1;
	}
	
	/**
	 * Retourne la temperature du systeme d'apres la temperature de l'iteration precedente.
	 * Pour obtenir la valeur initiale de la temperature, la premiere valeur de tmp passee
	 * en parametre doit etre -1.0.
	 * @param tmp la temperature precedente. -1.0 pour le premier appel de la méthode
	 * @return la temperature a l'iteration k+1
	 */
	private static double getTemperature(double tmp){
		double delta = 1.11; // 1.10
		int eps = 100;
		if(tmp==-1.0) return eps; // Valeur initiale
		else return tmp / (1+ ((Math.log(delta))/(eps))*tmp) ;
	}
	
	/**
	 * retourne le critere de la grille passe en parametre
	 * @param grid la grille a evaluer
	 * @return le critere de la grille
	 */
	private static int getCritere(Grid grid){
		// Ici le critere est le nombre de valeurs non masquees
		return grid.getnNbUnmaskedVal();
	}
	
	/**
	 * Applique la modification passee en parametre a la grille
	 * @param grid Grille sur laquelle appliquer la modification
	 * @param modif Modification a appliquer
	 */
	private static void apply(Grid grid, Modification modif){

		if(modif.getModif()==Modification.UNMASK) grid.unmask(modif.getLine(), modif.getColumn());
		else grid.mask(modif.getLine(), modif.getColumn());
	}
	
	/**
	 * Annule la modification passee en parametre a la grille
	 * @param grid Grille sur laquelle appliquer la modification
	 * @param modif Modification a appliquer
	 */
	private static void unapply(Grid grid,  Modification modif){

		if(modif.getModif()==Modification.UNMASK) grid.mask(modif.getLine(), modif.getColumn());
		else grid.unmask(modif.getLine(), modif.getColumn());
	}
	
	/**
	 * Tire une modification au hasard dans la liste passee en parametre
	 * @param modifs La liste contenant les modifications candidates
	 * @return une modification tiree au hasard dans la liste
	 */
	private static Modification getModif(ArrayList<Modification> modifs){
		
		int modif = (int) Math.floor(Math.random()*modifs.size());
		return modifs.get(modif);
		
	}	
}
