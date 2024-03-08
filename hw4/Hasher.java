package hw4;

public interface Hasher<K, V>{
	public int hash(K key);
	public void setTable(MapEntry<K, V>[] table);
}
