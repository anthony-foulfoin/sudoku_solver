package sudoku.generator;

import java.io.PrintStream;
/**
 * 
 * Une classe utilitaire fournissant des methodes utiles pour le programme
 *
 */
public class Util {

	/**
	 * Imprime le contenu de la chaine de caracteres sur le printstream si celui-ci est non nul
	 * @param s Chaine de caractere a imprimer
	 * @param p PrintStream sur lequel imprimer la chaine
	 */
	public static void println(String s, PrintStream p){
		if(p!=null) p.println(s);
	}
}
