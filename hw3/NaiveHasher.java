package hw3;

/**
 * Hashes the given string in a very naive algorithm
 * of calculating total ASCII value , 
 * then taking the remainder divided by the length of the array
 * @author mbrso
 *
 */
public class NaiveHasher implements Hasher
{
	private MapEntry[] table;
	
	public void setTable(MapEntry[] table) {
		this.table = table;
	}
	
	@Override
	public int hash(String word) {
		int totalAsc = 0;
		/*
		 * Count up the total ASCII numerical value
		 */
		for(int i=0;i<word.length();i++) {
			totalAsc += (int)word.charAt(i);
		}
		/*
		 * return the remainder when divided by the 
		 * length of the array so it spits out a valid index
		 */
		return totalAsc % table.length;
	}
}
