package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    /** the default initial bucket size of the HashMap. */
    private static final int DEFAULT_INITIAL_SIZE = 16;
    /** the default loadFactor of the HashMap. */
    private static final double DEFAULT_LOADFACTOR = 0.75;
    /** the num of the nodes in this HashMap. */
    private int size;
    /** the initial setting of the num of the buckets in this HashMap. */
    private final int initTableSize;
    /** the num of the buckets in this HashMap. */
    private int tableSize;
    /** the maximum load factor in this HashMap. */
    private final double loadFactor;

    /** Constructors */
    public MyHashMap() {
        this.initTableSize = DEFAULT_INITIAL_SIZE;
        this.tableSize = DEFAULT_INITIAL_SIZE;
        this.loadFactor = DEFAULT_LOADFACTOR;
        this.buckets = createTable(this.initTableSize);
    }

    public MyHashMap(int initialSize) {
        this.initTableSize = initialSize;
        this.tableSize = initialSize;
        this.loadFactor = DEFAULT_LOADFACTOR;
        this.buckets = createTable(this.initTableSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initTableSize = initialSize;
        this.tableSize = initialSize;
        this.loadFactor = maxLoad;
        this.buckets = createTable(this.initTableSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        // Reset the tableSize to the initTableSize.
        this.tableSize = this.initTableSize;
        this.buckets = createTable(this.initTableSize);
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node == null) { // if this key is not exist.
            this.size += 1;
            // if the size/tableSize is bigger than the loadFactor.
            if ((double) this.size / this.tableSize > this.loadFactor) {
                this.expandTable(this.tableSize * 2);
                Collection<Node> bucket = getBucketOfKey(key);
                bucket.add(createNode(key, value));
            }
        } else {
            node.value = value;
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {

        private MyHashMapIterator() {}

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public K next() {
            throw new UnsupportedOperationException();
        }
    }

    /** get the node of the given key, if failed, return null. */
    private Node getNode(K key) {
        return getNodeInBucket(getBucketOfKey(key), key);
    }

    /** get the right bucket of the given key.
     *  Be aware that the key may not exist yet. */
    private Collection<Node> getBucketOfKey(K key) {
        int hash = key.hashCode();
        int index = hash % this.tableSize;
        return buckets[index];
    }

    /** try to get a certain node with given key in the given bucket.
     *  if failed, return null. */
    private Node getNodeInBucket(Collection<Node> bucket, K key) {
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    /** expand the buckets to the given size. */
    private void expandTable(int newTableSize) {
        Collection<Node>[] oldBuckets = this.buckets;
        this.buckets = createTable(newTableSize);
        this.tableSize = newTableSize;
        // copy all the nodes from the old to the new one.
        for (Collection<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                Collection<Node> newBucket = getBucketOfKey(node.key);
                newBucket.add(node);
            }
        }
    }

}
