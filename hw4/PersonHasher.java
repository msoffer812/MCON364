package hw4;
/**
 * 
 * @author mbrso
 * Returns hash value for a person object
 */
public class PersonHasher<K, V> implements Hasher<K, V> {
    private MapEntry<K, V>[] table;
    private int PRIME;

    public void setTable(MapEntry<K, V>[] table) {
        this.table = table;
        PRIME = getBiggestPrimeNumberWithCoolAlgorithm(table.length-1);
    }

    @Override
    public int hash(K key) {
    	Person person = (Person) key;
    	String word = person.getID();
    	int asciiVal = calcASCII(word), origin = hash1(asciiVal, table.length), i = 0, index = origin, maxProbes = table.length; // Maximum number of probes before we give up and chain
        while (i < maxProbes) {
            if (table[index] == null || table[index].contains(key)) {
                // Word found at the current index
                return index;
            }
            // Collision resolution using double hashing
            index = (origin + i * hash2(asciiVal)) % table.length;
            i++;
        }
        // Word not found or no more available slots
        // Return the current index (where it should be inserted)
        return index;
    }
    
    /**
     * Really cool new algorithm I learned to get the biggest prime number
     * next to the max!
     * Basically,
     * make a boolean array initialized to falses. Then go from 2(smallest prime) to the
     * square root of the max size(the biggest possible factor of the max size that doesn't 
     * match up to smaller factor below the square root) and mark all those multiples 
     * as true. The last true one standing wins!
     * @param MAX_SIZE
     * @return
     */
    private int getBiggestPrimeNumberWithCoolAlgorithm(int MAX_SIZE) {
    	/*
    	 * Initialize the array set to falses
    	 */
    	boolean[] isntPrime = new boolean[MAX_SIZE];
    	isntPrime[0] = true;
    	isntPrime[1] = true;
    	
    	for(int i=2;i<Math.sqrt(MAX_SIZE);i++) {
    		if(!isntPrime[i]) {
    			/*
    			 * Now loop through all multiples of 2 until MAXSize -
    			 * set each one in the array to true since it can be
    			 * divided by i
    			 */
    			 for (int j=i*i; j<MAX_SIZE; j+=i) {
    				 isntPrime[j] = true;
    			 }
    		}
    	}
    	/*
    	 * Now find the largest prime number starting from closest to maxSize
    	 */
    	for(int i=isntPrime.length-1; i>=2;i--) {
    		if(!isntPrime[i]) {
    			return i;
    		}
    	}
    	return 1; //Doesn't have one, so just return 1 
    }
    /**
     * Calculate and return total ASCII value of a word
     * @param word
     * @return
     */
    private int calcASCII(String word) {
    	int totalAsc = 0;
        for (int i = 0; i < word.length(); i++) {
            totalAsc += (int) word.charAt(i);
        }
        return totalAsc;
    }
    /**
     * Get value of a second hashing - this time using the biggest
     * prime number after the table size. 
     * So to prevent likely collisions since a non prime number is more divisible
     * @param asciiVal
     * @param arraySize
     * @return
     */
    private int hash2(int asciiVal) {
        return PRIME - (asciiVal % PRIME);
    }

    private int hash1(int asciiVal, int arraySize) {
        return asciiVal % arraySize;
    }
}
