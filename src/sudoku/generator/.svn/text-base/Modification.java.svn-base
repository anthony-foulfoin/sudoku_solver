package sudoku.generator;

/**
 * Modelise une modification a appliquer a une grille de sudoku
 * Une modificaiton est represente par une case et la modification a lui appliquee : masquer ou demasquer la case
 *
 */
public class Modification {

	public static final int MASK = 1;
	public static final int UNMASK = 0;
	
	private int line;
	private int column;
	private int modif;
	
	/**
	 * COnstruit une nouvelle modification
	 * @param line ligne de la case
	 * @param column colonne de la case
	 * @param modif modification a applqiquer : Modification.MASK pour masquer la case, Modification.UNMASK pour demasquer la case
	 */
	public Modification(int line, int column, int modif){
		
		this.line = line;
		this.column = column;
		this.modif = modif;
		
	}
	
	/**
	 * Inverse la modification. Si la modification etait MASK, celle ci devient UNMASK et inversement
	 */
	public void inverseModification(){
		if(modif==Modification.MASK) modif = Modification.UNMASK;
		else modif = Modification.MASK;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getModif() {
		return modif;
	}

	public void setModif(int modif) {
		this.modif = modif;
	}
}
