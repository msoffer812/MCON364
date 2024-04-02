package hw9;

public interface Hasher<T>{
	public int hash(String word);
	public void setTable(MapEntry[] table);
}
