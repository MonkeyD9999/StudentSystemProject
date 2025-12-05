package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
 abstract class HashTable<K,V> implements Map<K,V>{

     // Default size of the hash table.
     static final int DEFAULT_CAPACITY = 50;

     // Number of entries in the hash table.
     protected int currentSize;

     // predicted number of elements
     private int size;

     // Number of entries to reach MAX_LOAD_FACTOR.
     protected int maxSize;

     public HashTable(int capacity) {
         currentSize=0;
         size=capacity;
     }

     public static int hash( String key ){

         int a = 127; // a is a prime number.
         int b = 2147483647; // b is a prime number.
         int hashCode = 0;

         for ( int i = 0; i < key.length(); i++ )
             hashCode = ( hashCode * a + key.charAt(i) ) % b;
         return hashCode;
     }

     protected static int nextPrime( int number ){
         if (number <= 2) return 2;

         int candidate = (number % 2 == 0) ? number + 1 : number;

         while (candidate > 0) { // overflow
             if (isPrime(candidate))
                 return candidate;

             if (candidate >= Integer.MAX_VALUE - 2)
                 return 0;

             candidate += 2;
         }

         return 0;
     }

     protected boolean isFull( ){
         return currentSize == maxSize;
     }
     /**
      * Returns true iff the dictionary contains no entries.
      *
      * @return true if dictionary is empty
      */
     public boolean isEmpty() {
         return currentSize==0;
     }

     /**
      * Returns the number of entries in the dictionary.
      *
      * @return number of elements in the dictionary
      */
     public int size() {
         return currentSize;
     }

     /**
      * If there is an entry in the dictionary whose key is the specified key,
      * returns its value; otherwise, returns null.
      *
      * @param key whose associated value is to be returned
      * @return value of entry in the dictionary whose key is the specified key,
      * or null if the dictionary does not have an entry with that key
      */
     public abstract V get(K key) ;

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
     public abstract V put(K key, V value);

     /**
      * If there is an entry in the dictionary whose key is the specified key,
      * removes it from the dictionary and returns its value;
      * otherwise, returns null.
      *
      * @param key whose entry is to be removed from the map
      * @return previous value associated with key,
      * or null if the dictionary does not an entry with that key
      */
     public abstract V remove(K key);

     /**
      * Returns an iterator of the entries in the dictionary.
      *
      * @return iterator of the entries in the dictionary
      */
     public abstract Iterator<Entry<K, V>> iterator();

     /**
      * Returns an iterator of the values in the dictionary.
      *
      * @return iterator of the values in the dictionary
      */
	@SuppressWarnings({"unchecked","rawtypes"})
     public Iterator<V> values() {
         return new ValuesIterator(iterator());
     }

     /**
      * Returns an iterator of the keys in the dictionary.
      *
      * @return iterator of the keys in the dictionary
      */
	@SuppressWarnings({"unchecked","rawtypes"})
     public Iterator<K> keys() {
         return new KeysIterator(iterator());
     }

     private static boolean isPrime(int n){
         if (n < 2) return false;
         if (n % 2 == 0) return n == 2;

         for (int i = 3; i*i <= n; i += 2) {
             if (n % i == 0)
                 return false;
         }
         return true;
     }

 }
