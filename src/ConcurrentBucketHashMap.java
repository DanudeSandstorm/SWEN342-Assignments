package src;

import java.util.*;
import java.util.concurrent.* ;
import java.util.concurrent.locks.* ;

@SuppressWarnings("ALL")
public class ConcurrentBucketHashMap<K, V> {
    final int numberOfBuckets;
    final List<Bucket<K, V>> buckets;

    /*
     * Immutable Pairs of keys and values. Immutability means
     * we don't have to worry about the key or value changing
     * under our feet. However, when the mapping for a given key
     * changes, we need to create a new Pair object.
     *
     * This is a pure data holder class.
     */
    class Pair<K, V> {
        final K key;
        final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /*
     * A Bucket holds all the key/value pairs in the map that have
     * the same hash code (modulo the number of buckets). The
     * object consists of an extensible "contents" list protected
     * with a ReadWriteLock "rwl".
     */
    class Bucket<K, V> {
        private final List<Pair<K, V>> contents =
                new ArrayList<Pair<K, V>>();
        //Fairness is false by default
        private ReadWriteLock rwl = new ReentrantReadWriteLock();

        /*
         * Return the current Bucket size.
         */
        int size() {
            return contents.size();
        }

        /*
         * Get the Pair at location 'i' in the Bucket.
         */
        Pair<K, V> getPair(int i) {
            return contents.get(i);
        }

        /*
         * Replace the Pair at location 'i' in the Bucket.
         */
        void putPair(int i, Pair<K, V> pair) {
            contents.set(i, pair);
        }

        /*
         * Add a Pair to the Bucket.
         */
        void addPair(Pair<K, V> pair) {
            contents.add(pair);
        }

        /*
         * Remove a Pair from the Bucket by position.
         */
        void removePair(int index) {
            contents.remove(index);
        }

        /*
         * Aquire a read lock on the bucket
         */
        void readLock() {
            rwl.readLock().lock();
        }

        /*
         * Release read lock on the bucket
         */
        void readUnlock() {
            rwl.readLock().unlock();
        }

        /*
         * Aquire a write lock on the bucket
         */
        void writeLock() {
            rwl.writeLock().lock();
        }

        /*
         * Release write lock on the bucket
         */
        void writeUnlock() {
            rwl.writeLock().unlock();
        }
    }

    /*
     * Constructor for the ConcurrentBucketHashMap proper.
     */
    public ConcurrentBucketHashMap(int nbuckets) {
        numberOfBuckets = nbuckets;
        buckets = new ArrayList<Bucket<K, V>>(nbuckets);

        for (int i = 0; i < nbuckets; i++) {
            buckets.add(new Bucket<K, V>());
        }
    }

    /*
     * Does the map contain an entry for the specified
     * key?
     */
    public boolean containsKey(K key) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key));
        boolean contains;

        theBucket.readLock();
        try {
            contains = findPairByKey(key, theBucket) >= 0;
        } finally {
            theBucket.readUnlock();
        }

        return contains;
    }

    /*
     * How many pairs are in the map?
     */
    public int size() {
        int size = 0;

        //Acquire all read locks first
        for (int i = 0; i < numberOfBuckets; i++) {
            Bucket<K, V> theBucket = buckets.get(i);
            theBucket.readLock();
        }
        for (int i = 0; i < numberOfBuckets; i++) {
            Bucket<K, V> theBucket = buckets.get(i);
            try {
                size += theBucket.size();
            } finally {
                // as each bucket's size is accumulated,
                // release that bucket's lock.
                theBucket.readUnlock();
            }
        }
        return size;
    }

    /*
     * Return the value associated with the given Key.
     * Returns null if the key is unmapped.
     */
    public V get(K key) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key));
        Pair<K, V> pair = null;

        theBucket.readLock();
        try {
            int index = findPairByKey(key, theBucket);

            if (index >= 0) {
                pair = theBucket.getPair(index);
            }
        } finally {
            theBucket.readUnlock();
        }

        return (pair == null) ? null : pair.value;
    }

    /*
     * Associates the given value with the key in the
     * map, returning the previously associated value
     * (or none if the key was not previously mapped).
     */
    public V put(K key, V value) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key));
        Pair<K, V> newPair = new Pair<K, V>(key, value);
        V oldValue;

        theBucket.writeLock();
        try {
            int index = findPairByKey(key, theBucket);

            if (index >= 0) {
                Pair<K, V> pair = theBucket.getPair(index);

                theBucket.putPair(index, newPair);
                oldValue = pair.value;
            } else {
                theBucket.addPair(newPair);
                oldValue = null;
            }
        } finally {
            theBucket.writeUnlock();
        }
        return oldValue;
    }

    /*
     * Remove the mapping for the given key from the map, returning
     * the currently mapped value (or null if the key is not in
     * the map.
     */
    public V remove(K key) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key));
        V removedValue = null;

        theBucket.writeLock();
        try {
            int index = findPairByKey(key, theBucket);

            if (index >= 0) {
                Pair<K, V> pair = theBucket.getPair(index);

                theBucket.removePair(index);
                removedValue = pair.value;
            }
        } finally {
            theBucket.writeUnlock();
        }
        return removedValue;
    }

    /****** PRIVATE METHODS ******/

    /*
     * Given a key, return the index of the Bucket
     * where the key should reside.
     */
    private int bucketIndex(K key) {
        return key.hashCode() % numberOfBuckets;
    }

    /*
     * Find a Pair<K, V> for the given key in the given Bucket,
     * returnning the pair's index in the Bucket (or -1 if
     * unfound).
     *
     * Assumes the lock for the Bucket has been acquired.
     */
    private int findPairByKey(K key, Bucket<K, V> theBucket) {
        int size = theBucket.size();

        for (int i = 0; i < size; ++i) {
            Pair<K, V> pair = theBucket.getPair(i);

            if (key.equals(pair.key)) {
                return i;
            }
        }

        return (-1);
    }

    /* Main */
    public static void main(String[] args) {
        ConcurrentBucketHashMap<String, Integer> cbhm = new ConcurrentBucketHashMap<>(5);


    }

}