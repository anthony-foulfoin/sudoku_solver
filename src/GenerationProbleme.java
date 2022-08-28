import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Time;

import sudoku.generator.Grid;
import sudoku.generator.GridRemoveValues;
import sudoku.generator.Util;
import sudoku.solveur.SudokuSolver;


public class GenerationProbleme {

	private static final int NB_VAL_DEF = 22; // Nombre maximal de valeurs que l'on souhaite avoir par defaut dans le sudoku
	
	public static void main(String[] args) {
		
		// On recoit le nom du fichier en parametre
		if(args.length>2 || args.length==0) {
			System.out.println("java GenerationProbleme <nomdufichier> [nbCases]");
			System.exit(0);
		}
		
		File f = new File(args[0]);
		int nbCases = args.length == 2 ? Integer.parseInt(args[1]) : NB_VAL_DEF;
		Grid gridSol;
		Grid gridPb;
		long tempsDebut;
		int[][] cells = new int[9][9];
		String line;
		int l = 0;
		try {
			BufferedReader b = new BufferedReader(new FileReader(f));
			
			while((line=b.readLine())!=null){
				
				System.out.println("\nline " + line);
				
				if(line.length()!=9){
					System.out.println("La matrice du sudoku est mal formee");
					System.exit(0);
				}
				
				for(int c=0;c<line.length();c++){
					cells[l][c] = Integer.parseInt(""+line.charAt(c));
				}
				l++;
			}
			
			if(l!=9){
				System.out.println("La matrice du sudoku est mal formee");
				System.exit(0);
			}
			
			gridSol = new Grid();
			gridSol.setGrid(cells);
			gridPb = gridSol.clone();
			
			System.out.println(gridSol.toString());
			
			tempsDebut = System.currentTimeMillis();
			
			System.out.println("\n-RETRAIT DE " + nbCases + " VALEURS " + " - DEBUT-");
			GridRemoveValues.removeValuesRecuit(gridPb, nbCases, System.out);
			System.out.println("-RETRAIT DE " + nbCases + " VALEURS " + " - FIN-");
			
			System.out.println("\n-RESULTAT DE L ALGORITHME - DEBUT-");
			System.out.println("-Une grille de " + nbCases + " valeurs etait recherchee" +"-");
			System.out.println("-L'algorithme est parvenu a generer une grille possedant " + gridPb.getnNbUnmaskedVal() + " valeurs-");
			System.out.println("-Resultat genere en " + (new Time(System.currentTimeMillis()-tempsDebut).getSeconds()) + " secondes-");
			System.out.println("\n*GRILLE GENEREE*");
			System.out.println(gridPb.toString());
			System.out.println("\n*GRILLE SOLUTION*");
			System.out.println(gridSol.toString());
			System.out.println("\n-RESULTAT DE L ALGORITHME - FIN-");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
