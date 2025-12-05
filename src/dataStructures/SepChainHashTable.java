package dataStructures;
/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private Map<K,V>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings({})
    public SepChainHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));

        table = (MapSinglyList<K,V>[]) new MapSinglyList[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = new MapSinglyList<>();
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    // Returns the hash value of the specified key.
    protected int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
    }
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        int hash = hash(key);
        return table[hash].get(key);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        if (isFull())
            rehash();

        int hash = hash(key);
        V old = table[hash].put(key, value);

        if (old == null)
            currentSize++;
        return old;
    }


    @SuppressWarnings({})
    private void rehash() {
        Map<K,V>[] oldTable = table;

        int newCapacity = HashTable.nextPrime(table.length * 2);

        table = (MapSinglyList<K,V>[]) new MapSinglyList[newCapacity];
        for (int i = 0; i < newCapacity; i++)
            table[i] = new MapSinglyList<>();

        currentSize = 0;
        maxSize = (int)(newCapacity * MAX_LOAD_FACTOR);

        for (int i = 0; i < oldTable.length; i++) {
            Map<K,V> bucket = oldTable[i];

            Iterator<Entry<K,V>> it = bucket.iterator();

            while (it.hasNext()) {
                Entry<K,V> e = it.next();
                put(e.key(), e.value());
            }
        }
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        int hash = hash(key);
        V old = table[hash].remove(key);


        if (old != null)
            currentSize--;
        return old;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }

}
