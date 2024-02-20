package hw3;

public interface Hasher<T>{
	public int hash(String word);
	public void setTable(MapEntry[] table);
}
