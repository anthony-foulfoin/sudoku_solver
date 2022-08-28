package sudoku.generator;

import java.io.PrintStream;
import java.sql.Time;

/**
 * Classe qui permet de generer une nouvelle grille de sudoku
 * @author Anthony
 *
 */
public class SudokuGen {

	private static final int NB_VAL_DEF = 22; // Nombre par defaut de valeurs composant la grille que l'algorithme cherche a obtenir
	
	/**
	 * Permet de generer une nouvelel grille de sudoku
	 * @param nbVal Nombre de valeurs que l'on souhaite avoir dans le sudoku
	 * @param outputStream Stream sur lequel imprimer des traces. S'il est null, aucune trace n'est imprimee
	 * @return Une grille representant le sudoku genere par l'algorithme
	 */
	public static Grid genNewGrid(int nbVal, PrintStream outputStream){
		
		Grid gridSol; // Grille solution
		
		long tempsDebut = System.currentTimeMillis();
		
		Util.println("****************PROGRAMME DE GENERATION DE GRILLES DE SUDOKU****************", outputStream);
		
		// Grille par defaut
		Util.println("-GENERATION DE LA GRILLE PAR DEFAUT - DEBUT-", outputStream);
		gridSol = new Grid();
		Util.println("-GENERATION DE LA GRILLE PAR DEFAUT - FIN-", outputStream);
		
		// On effectue des permutations aleatoires pour obtenir une nouvelle grille
		Util.println("\n-PERMUTATION DE LA GRILLE PAR DEFAUT - DEBUT-", outputStream);
		GridPermut.permut(gridSol,outputStream);
		Util.println("-PERMUTATION DE LA GRILLE PAR DEFAUT - FIN-", outputStream);

		// On retire des valeurs de la grille du probleme
		Util.println("\n-RETRAIT DE " + nbVal + " VALEURS " + " - DEBUT-", outputStream);
		//GridRemoveValues.removeValuesSimple(gridSol, nbVal, outputStream);
		GridRemoveValues.removeValuesRecuit(gridSol, nbVal, outputStream);
		Util.println("-RETRAIT DE " + nbVal + " VALEURS " + " - FIN-", outputStream);
		
		Util.println("\n-RESULTAT DE L ALGORITHME - DEBUT-", outputStream);
		Util.println("-Une grille de " + nbVal + " valeurs etait recherchee" +"-", outputStream);
		Util.println("-L'algorithme est parvenu a generer une grille possedant " + gridSol.getnNbUnmaskedVal() + " valeurs-", outputStream);
		Util.println("-Resultat genere en " + (new Time(System.currentTimeMillis()-tempsDebut).getSeconds()) + " secondes-", outputStream);
		Util.println("\n*GRILLE GENEREE*", outputStream);
		Util.println(gridSol.toString(), outputStream);
		Util.println("\n*GRILLE SOLUTION*", outputStream);
		Util.println(gridSol.getSolution().toString(), outputStream);
		Util.println("\n-RESULTAT DE L ALGORITHME - FIN-", outputStream);
		
		Util.println("\n****************FIN DU PROGRAMME DE GENERATION DE GRILLES DE SUDOKU****************", outputStream);
		
		//System.out.println(System.currentTimeMillis()-tempsDebut);
		
		return gridSol;
	}
	
	public static Grid genNewGrid(){
		return SudokuGen.genNewGrid(NB_VAL_DEF, null);
	}
	
	public static Grid genNewGrid(int nbVal){	
		return SudokuGen.genNewGrid(nbVal, null);
	}
	
	public static Grid genNewGrid(PrintStream outputStream){	
		return SudokuGen.genNewGrid(NB_VAL_DEF, outputStream);
	}
}
