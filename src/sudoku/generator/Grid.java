package sudoku.generator;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Permet de modeliser une grille de sudoku
 *
 */
public class Grid {

	private int[][] grid; // La grille de sudoku.
	
	private HashMap<Point,Integer> maskedValues; // Ce hashmap memorise toutes les valeurs qui ont ete masquees dans la grille de jeu.
	
	public Grid(){
		this.grid = new int[9][9]; // initialisee a 0 par defaut
		maskedValues = new HashMap<Point, Integer>();
		
		// On genere une grille par defaut
		genGrilleDep();
	
	}
	
	/**
	 * Cree une nouvelle grille de sudoku initialisee avec la grille passee en parametre
	 * @param grid grille permettant d'initialiser la grille de sudoku
	 */
	public Grid(int[][] grid){
		this.grid = grid; // initialisee a 0 par defaut
		maskedValues = new HashMap<Point, Integer>();
	}
	
	/**
	 * 
	 * @return La grille solution de la grille actuelle.
	 */
	public Grid getSolution(){
		int[][] grid = this.grid;
		
		for(Iterator<Point> i = maskedValues.keySet().iterator();i.hasNext();){
			Point p = i.next();
			grid[p.x][p.y] = maskedValues.get(p);
		}
		
		return new Grid(grid);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Permet de realiser une copie de la grille actuelle
	 */
	public Grid clone(){
		Grid grid = new Grid();
		grid.grid = this.getGridCopy();
		
		grid.maskedValues = (HashMap<Point, Integer>) this.maskedValues.clone();
		return grid;
	}
	
	/**
	 * 
	 * @return le nombre de valeurs qui ont ete masquees dans la grille
	 */
	public int getnNbMaskedVal(){
		return maskedValues.size();
	}
	
	/**
	 * 
	 * @return le nombre de valeurs qui n'ont pas ete masquees dans la grille
	 */
	public int getnNbUnmaskedVal(){
		return (81-maskedValues.size());
	}
	
	/**
	 * 
	 * @return une copie de la grille de sudoku 
	 */
	public int[][] getGridCopy(){
		int[][] grid = new int[9][9];
		
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				grid[i][j] = this.grid[i][j];
			}
		}
		
		return grid;
	}
	
	/**
	 * Permet de masquer la case i,j du sudoku
	 * @param i la ligne a masquer
	 * @param j la colonne a masquer
	 */
	public void mask(int i, int j){
		// On souhaite masquer la valeur
		// On l'ajoute dans maskedValues
		if(!maskedValues.containsKey(new Point(i,j))) {
			maskedValues.put(new Point(i,j), grid[i][j]);
			// On remplace la valeur masquee par 0 dans la grille
			grid[i][j] = 0;
		}
	}
	
	/**
	 * Permet de demasquer la case i,j du sudoku
	 * @param i la ligne a masquer
	 * @param j la colonne a masquer
	 */
	public void unmask(int i, int j){
		// On souhaite demasquer la valeur
		// On l'ajoute dans grid
		
		if(maskedValues.containsKey(new Point(i,j))) {
			grid[i][j] = maskedValues.get(new Point(i,j));
			maskedValues.remove(new Point(i,j));
		}
	}
	
	private void genGrilleDep(){
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				grid[i][j]=j+1;
			}
		}
	}
	
	public String toString(){
		String s="";
		for (int i = 0; i < 9; ++i) {
		    if (i % 3 == 0)
			s+= " -----------------------\n";
		    for (int j = 0; j < 9; ++j) {
			if (j % 3 == 0) s+=("| ");
			s+= grid[i][j] == 0
					 ? "."
					 : Integer.toString(grid[i][j]);
			
			s+=' ';
		    }
		    s+="|\n";
		}
		s+=" -----------------------\n";
		return s;
	}
	
	public void printGrid() {
		System.out.println(this.toString());
	    }

	public int[][] getGrid() {
		return grid;
	}
	
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
	
	public HashMap<Point, Integer> getMaskedValues() {
		return maskedValues;
	}

	public void setMaskedValues(HashMap<Point, Integer> maskedValues) {
		this.maskedValues = maskedValues;
	}

	public void setCase(int ligne, int colonne, int val){
		grid[ligne][colonne]=val;
	}
}
