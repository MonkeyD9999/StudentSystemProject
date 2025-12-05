package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    //Methods for handling collisions.
    // Returns the hash value of the specified key.
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }
    /**
     * Linear Proving
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        int hash = Math.abs(key.hashCode()) % table.length;

        int index = hash;
        int i = 0;

        while (true) {
            Entry<K, V> entry = table[index];

            if (entry == null)
                return NOT_FOUND;

            if (entry != REMOVED_CELL && entry.key().equals(key))
                return index;

            i++;
            if (i == table.length)
                return NOT_FOUND;
            index = (hash + i) % table.length;
        }
    }

    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        int index = searchLinearProving(key);

        if(index == NOT_FOUND)
            return null;

        Entry<K, V> entry = table[index];
        return entry.value();

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
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();

        int index = searchLinearProving(key);
        if(index == NOT_FOUND){

            int hash = Math.abs(key.hashCode()) % table.length;
            while(table[hash] != null && table[hash]!=REMOVED_CELL){

                hash = (hash +1) % table.length;

            }
            currentSize++;
            table[hash] = new Entry<>(key,value);
            return null;
        }

        else {
            Entry<K, V> old = table[index];
            table[index] = new Entry<>(key,value);
            currentSize++;
            return old.value();
        }
    }

     private void rehash(){
         Entry<K,V>[] oldTable = table;

         int newCapacity = HashTable.nextPrime(table.length * 2);

         @SuppressWarnings("unchecked")
         Entry<K,V>[] newTable = (Entry<K,V>[]) new Entry[newCapacity];

         table = newTable;
         currentSize = 0;
         maxSize = (int) (newCapacity * MAX_LOAD_FACTOR);
         for (int i = 0; i < oldTable.length; i++) {
             Entry<K,V> e = oldTable[i];

             if (e != null && e != REMOVED_CELL) {

                 int hash = Math.abs(e.key().hashCode()) % table.length;
                 int j = 0;

                 while (table[hash] != null) {
                     j++;
                     hash = (hash + j) % table.length;
                 }

                 table[hash] = new Entry<>(e.key(), e.value());
                 currentSize++;
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
    @Override
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        int hash = searchLinearProving(key);
        if(hash == NOT_FOUND){
            return null;
        }
        Entry<K, V> removed = table[hash];
        table[hash] = (Entry<K, V>) REMOVED_CELL;
        currentSize--;
        return removed.value();
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Iterator<Entry<K, V>> iterator() {
        return new FilterIterator<>(new ArrayIterator(table,table.length-1), m -> m!=null && m!= REMOVED_CELL);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();

        oos.writeInt(currentSize);

        for (Entry<K,V> entry : table) {
            if (entry != null && entry != REMOVED_CELL) {
                oos.writeObject(entry);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        int size = ois.readInt();

        int arraySize = HashTable.nextPrime((int) (size / IDEAL_LOAD_FACTOR) + 1);

        table = (Entry<K,V>[]) new Entry[arraySize];
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
        currentSize = 0;

        for (int i = 0; i < size; i++) {
            Entry<K,V> entry  = (Entry<K,V>) ois.readObject();

            int hash = Math.abs(entry.key().hashCode()) % table.length;
            int j = 0;

            while (table[hash] != null) {
                j++;
                hash = (hash + j) % table.length;
            }

            table[hash] = entry;
            currentSize++;
        }
    }
}
